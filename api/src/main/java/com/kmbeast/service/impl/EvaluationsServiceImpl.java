package com.kmbeast.service.impl;

import com.kmbeast.context.LocalThreadHolder;
import com.kmbeast.mapper.EvaluationsMapper;
import com.kmbeast.mapper.EvaluationsUpvoteMapper;
import com.kmbeast.mapper.UserMapper;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.EvaluationsQueryDto;
import com.kmbeast.pojo.entity.Evaluations;
import com.kmbeast.pojo.entity.EvaluationsUpvote;
import com.kmbeast.pojo.entity.User;
import com.kmbeast.pojo.vo.CommentChildVO;
import com.kmbeast.pojo.vo.CommentParentVO;
import com.kmbeast.pojo.vo.EvaluationsVO;
import com.kmbeast.service.EvaluationsService;
import com.kmbeast.utils.AhoCorasickFilter;
import com.kmbeast.utils.AssertUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论服务实现类
 */
@Service
public class EvaluationsServiceImpl implements EvaluationsService {

    @Resource
    private EvaluationsMapper evaluationsMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private EvaluationsUpvoteMapper evaluationsUpvoteMapper;

    /**
     * 评论
     *
     * @return Result<String>
     */
    @Override
    public Result<Object> insert(Evaluations evaluations) {
        // 原始评论文本
        String content = evaluations.getContent();
        // ====================Trie树敏感词过滤算法起始===============
        AhoCorasickFilter ahoCorasickFilter = new AhoCorasickFilter(); // 创建过滤器实例
        for (String sensitiveWord : AhoCorasickFilter.sensitiveWords) { // 添加词典
            ahoCorasickFilter.addWord(sensitiveWord);
        }
        ahoCorasickFilter.buildFailurePointer(); // 构建失败指针
        String filteredContent = ahoCorasickFilter.filter(content); // 过滤敏感词
        // ====================结束======================================
        evaluations.setContent(filteredContent); // 将过滤后的敏感词替换原始文本内容
        evaluations.setCommenterId(LocalThreadHolder.getUserId());
        User user = userMapper.getUserById(LocalThreadHolder.getUserId());
        evaluations.setCreateTime(LocalDateTime.now());
        evaluationsMapper.save(evaluations);
        return ApiResult.success("评论成功");
    }

    /**
     * 查询全部评论
     *
     * @return Result<String>
     */
    @Override
    public Result<Object> list(Integer contentId, String contentType) {
        List<CommentParentVO> parentComments = evaluationsMapper.getParentComments(
                contentId,
                LocalThreadHolder.getUserId(),
                contentType
        );
        Integer count = evaluationsMapper.totalCount(contentId, contentType);
        return ApiResult.success(new EvaluationsVO(count, parentComments));
    }

    /**
     * 分页查询评论
     *
     * @return Result<String>
     */
    @Override
    public Result<Object> query(EvaluationsQueryDto evaluationsQueryDto) {
        List<CommentChildVO> list = evaluationsMapper.query(evaluationsQueryDto);
        Integer totalPage = evaluationsMapper.queryCount(evaluationsQueryDto);
        return ApiResult.success(list, totalPage);
    }

    /**
     * 评论删除
     *
     * @return Result<String>
     */
    @Override
    public Result<String> deleteById(Integer id) {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        evaluationsMapper.batchDelete(ids);
        return ApiResult.success();
    }

    /**
     * 评论修改
     *
     * @return Result<String>
     */
    @Override
    public Result<Void> update(Evaluations evaluations) {
        evaluationsMapper.update(evaluations);
        return ApiResult.success();
    }

    @Override
    public Result<Object> upvoteOperation(Evaluations evaluations) {
        Integer userId = LocalThreadHolder.getUserId();
        Integer evaluationsId = evaluations.getId();

        // 创建查询条件对象
        EvaluationsUpvote query = new EvaluationsUpvote();
        query.setUserId(userId);
        query.setEvaluationsId(evaluationsId);

        // 检查用户是否已点赞
        boolean hasUpvote = evaluationsUpvoteMapper.queryCount(query) > 0;

        if (hasUpvote) {
            // 取消点赞 - 必须同时设置userId和evaluationsId
            EvaluationsUpvote deleteCondition = new EvaluationsUpvote();
            deleteCondition.setUserId(userId);
            deleteCondition.setEvaluationsId(evaluationsId);
            evaluationsUpvoteMapper.delete(deleteCondition);
        } else {
            // 添加点赞
            EvaluationsUpvote newUpvote = new EvaluationsUpvote();
            newUpvote.setUserId(userId);
            newUpvote.setEvaluationsId(evaluationsId);
            evaluationsUpvoteMapper.save(newUpvote);
        }

        // 获取更新后的总点赞数
        EvaluationsUpvote countQuery = new EvaluationsUpvote();
        countQuery.setEvaluationsId(evaluationsId);
        int total = evaluationsUpvoteMapper.queryCount(countQuery);

        // 构建返回结果
        Map<String, Object> rep = new HashMap<>();
        rep.put("count", total);
        rep.put("haveUpvote", !hasUpvote); // 返回操作后的状态

        return ApiResult.success(rep);
    }
}
