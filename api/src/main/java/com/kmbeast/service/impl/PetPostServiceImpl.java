package com.kmbeast.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kmbeast.context.LocalThreadHolder;
import com.kmbeast.mapper.ActiveNetMapper;
import com.kmbeast.mapper.PetPostMapper;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.ActiveNetQueryDto;
import com.kmbeast.pojo.dto.PetPostQueryDto;
import com.kmbeast.pojo.em.ActiveNetType;
import com.kmbeast.pojo.em.IsAuditEnum;
import com.kmbeast.pojo.entity.ActiveNet;
import com.kmbeast.pojo.entity.PetPost;
import com.kmbeast.pojo.vo.PetPostListItemVO;
import com.kmbeast.pojo.vo.PetPostSelectedVO;
import com.kmbeast.pojo.vo.PetPostVO;
import com.kmbeast.pojo.vo.ScoreVO;
import com.kmbeast.service.PetPostService;
import com.kmbeast.utils.AssertUtils;
import com.kmbeast.utils.UserBasedCFUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 宠物经验帖子业务逻辑接口实现类
 */
@Service
public class PetPostServiceImpl extends ServiceImpl<PetPostMapper, PetPost> implements PetPostService {

    @Resource
    private ActiveNetMapper activeNetMapper;
    @Autowired
    private PetPostMapper petPostMapper;

    /**
     * 经验帖子列表查询
     *
     * @param petPostQueryDto 查询条件
     * @return Result<List < PetPostListItemVO>> 后台通用响应
     */
    @Override
    public Result<List<PetPostListItemVO>> list(PetPostQueryDto petPostQueryDto) {
        List<PetPostListItemVO> petPostListItemVOS = this.baseMapper.list(petPostQueryDto);
        Integer count = this.baseMapper.listCount(petPostQueryDto);
        return ApiResult.success(petPostListItemVOS, count);
    }

    /**
     * 通过ID查询宠物帖子信息
     *
     * @param id 宠物帖子主键ID
     * @return Result<PetPostVO>
     */
    @Override
    public Result<PetPostVO> getById(Integer id) {
        PetPostVO petPostVO = this.baseMapper.getById(id);
        // 浏览逻辑实现
        ActiveNetQueryDto activeNetQueryDto = new ActiveNetQueryDto();
        activeNetQueryDto.setUserId(LocalThreadHolder.getUserId()); // 设置上用户ID
        activeNetQueryDto.setContentId(id); // 设置内容ID
        activeNetQueryDto.setContentType("PET-POST"); // 标识查的是宠物模块类型
        activeNetQueryDto.setType(ActiveNetType.VIEW.getStatus()); // 声明为浏览类型
        Integer count = activeNetMapper.queryCount(activeNetQueryDto);
        if (count == 0) { // 证明用户没有针对宠物模块下面的宠物信息浏览过
            ActiveNet activeNet = new ActiveNet();
            activeNet.setUserId(LocalThreadHolder.getUserId());
            activeNet.setContentId(id);
            activeNet.setContentType("PET-POST");
            activeNet.setType(ActiveNetType.VIEW.getStatus());
            activeNet.setCreateTime(LocalDateTime.now());
            activeNetMapper.insert(activeNet); // 浏览记录新增
        }
        return ApiResult.success(petPostVO);
    }

    /**
     * 经验帖子新增
     *
     * @param petPost 实体数据
     * @return Result<String>
     */
    @Override
    public Result<String> saveEntity(PetPost petPost) {
        judgeParam(petPost); // 先做参数校验
        petPost.setUserId(LocalThreadHolder.getUserId()); // 设置发布者用户ID
        petPost.setCreateTime(LocalDateTime.now()); // 设置发布时间
        petPost.setIsAudit(IsAuditEnum.NO_AUDIT.getStatus()); // 发布时，初始是未审核的
        save(petPost);
        return ApiResult.success("宠物经验帖子新增成功");
    }

    private void judgeParam(PetPost petPost) {
        AssertUtils.notNull(petPost.getPetTypeId(), "宠物类型不为空哦");
        AssertUtils.hasText(petPost.getTitle(), "标题不为空哦");
        AssertUtils.hasText(petPost.getCover(), "封面要上传哦");
        AssertUtils.hasText(petPost.getSummary(), "请补充摘要");
        AssertUtils.isTrue(petPost.getTitle().length() < 30, "标题长度最多30个字哦");
        AssertUtils.isTrue(petPost.getSummary().length() < 200, "摘要长度要控制在200个字以内哦");
    }

    /**
     * 宠物经验帖子修改
     *
     * @param petPost 实体信息
     * @return Result<String>
     */
    @Override
    public Result<String> updateEntity(PetPost petPost) {
        judgeParam(petPost); // 先做参数校验
        updateById(petPost);
        return ApiResult.success("宠物经验帖子修改成功");
    }

    /**
     * 宠物经验帖子审核
     *
     * @param id 主键ID
     * @return Result<String> 通用返回封装类
     */
    @Override
    public Result<String> audit(Integer id) {
        PetPost petPost = new PetPost();
        petPost.setId(id);
        petPost.setIsAudit(IsAuditEnum.AUDIT.getStatus());
        updateById(petPost);
        return ApiResult.success("审核成功");
    }

    /**
     * 智能推荐宠物经验帖子信息
     *
     * @param count 期望拿到的条数
     * @return Result<List < PetPostListItemVO>> 通用返回封装类
     */
    @Override
    public Result<List<PetPostListItemVO>> autoRecommend(Integer count) {
        List<Integer> petPostIds = this.baseMapper.queryAllIds(); // 获取全部的宠物经验ID列表
        // 兴趣评分 = 浏览 * 1 + 收藏 * 3 + 喜欢 * 1
        List<ScoreVO> scoreVOS = activeNetMapper.queryScore("PET-POST");
        // 期望用到的评分数据集
        List<UserBasedCFUtil.Score> scoreList = scoreVOS.stream().map(scoreVO -> new UserBasedCFUtil.Score(
                scoreVO.getUserId(),
                scoreVO.getContentId(),
                scoreVO.getScore()
        )).collect(Collectors.toList());
        // 构建用户对于物品评分的矩阵
        Map<Integer, Map<Integer, Double>> userItemMatrix = UserBasedCFUtil.buildUserItemMatrix(petPostIds, scoreList);
        UserBasedCFUtil userBasedCFUtil = new UserBasedCFUtil(userItemMatrix);
        List<Integer> recommendItems = userBasedCFUtil.recommendItems(LocalThreadHolder.getUserId(), count);
        System.out.println("为用户「" + LocalThreadHolder.getUserId() + "」推荐的宠物经验帖子ID列表: " + recommendItems);
        // “冷启动”
        if (recommendItems.isEmpty()) {
            List<ScoreVO> scoreVOList = activeNetMapper.queryAllIds(
                    "PET-POST",
                    ActiveNetType.VIEW.getStatus(),
                    count
            );
            if (scoreVOList.isEmpty()) {
                return ApiResult.success(new ArrayList<>());
            }
            List<Integer> petNetIds = scoreVOList.stream()
                    .map(ScoreVO::getContentId)
                    .collect(Collectors.toList());
            List<PetPostListItemVO> petListItemVOS = this.baseMapper.queryListItemByIds(petNetIds);
            return ApiResult.success(petListItemVOS);
        }
        List<PetPostListItemVO> petListItemVOS = this.baseMapper.queryListItemByIds(recommendItems);
        return ApiResult.success(petListItemVOS);
    }

    /**
     * 查询用户收藏的宠物经验帖子信息
     *
     * @return Result<List < PetListItemVO>> 通用返回封装类
     */
    @Override
    public Result<List<PetPostListItemVO>> saveList() {
        // 先去查收藏了哪些？
        ActiveNetQueryDto activeNetQueryDto = new ActiveNetQueryDto();
        activeNetQueryDto.setUserId(LocalThreadHolder.getUserId()); // 设置用户的ID
        activeNetQueryDto.setContentType("PET-POST"); // 设置查询的模块 - PET - 宠物模块
        activeNetQueryDto.setType(ActiveNetType.SAVE.getStatus()); // 查询宠物模块下面的是收藏的类型
        List<ActiveNet> activeNetList = activeNetMapper.query(activeNetQueryDto);
        if (activeNetList.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        List<Integer> petIds = activeNetList.stream()
                .map(ActiveNet::getContentId)
                .collect(Collectors.toList());
        List<PetPostListItemVO> petPostListItemVOS = petPostMapper.queryListItemByIds(petIds);
        return ApiResult.success(petPostListItemVOS);
    }

    /**
     * 查询用户名下帖子数据下拉选择器
     *
     * @return Result<List < PetPostSelectedVO>> 通用返回封装类
     */
    @Override
    public Result<List<PetPostSelectedVO>> listPetPostSelect() {
        List<PetPostSelectedVO> petPostSelectedVOS = this.baseMapper.querySelectedVO(LocalThreadHolder.getUserId());
        return ApiResult.success(petPostSelectedVOS);
    }
}
