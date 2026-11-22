package com.kmbeast.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kmbeast.mapper.NoticeMapper;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.NoticeQueryDto;
import com.kmbeast.pojo.entity.Notice;
import com.kmbeast.pojo.vo.NoticeListItemVO;
import com.kmbeast.service.NoticeService;
import com.kmbeast.utils.AssertUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 公告业务逻辑实现类
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    /**
     * 公告新增
     *
     * @param notice 实体
     * @return Result<String>
     */
    @Override
    public Result<String> saveEntity(Notice notice) {
        AssertUtils.hasText(notice.getTitle(), "公告标题不能为空");
        AssertUtils.isTrue(notice.getTitle().length() < 30, "标题长度最多30个字哦");
        notice.setCreateTime(LocalDateTime.now()); // 设置公告新增时间
        save(notice);
        return ApiResult.success("公告新增成功");
    }

    /**
     * 公告修改
     *
     * @param notice 实体
     * @return Result<String>
     */
    @Override
    public Result<String> update(Notice notice) {
        AssertUtils.hasText(notice.getTitle(), "公告标题不能为空");
        AssertUtils.isTrue(notice.getTitle().length() < 30, "标题长度最多30个字哦");
        notice.setCreateTime(LocalDateTime.now()); // 设置公告新增时间
        updateById(notice);
        return ApiResult.success("公告修改成功");
    }


    /**
     * 公告查询
     *
     * @param noticeQueryDto 查询参数条件
     * @return Result<List < NoticeListItemVO>>
     */
    @Override
    public Result<List<NoticeListItemVO>> query(NoticeQueryDto noticeQueryDto) {
        List<NoticeListItemVO> noticeList = this.baseMapper.list(noticeQueryDto);
        Integer count = this.baseMapper.listCount(noticeQueryDto);
        return ApiResult.success(noticeList, count);
    }


}
