package com.kmbeast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kmbeast.pojo.dto.NoticeQueryDto;
import com.kmbeast.pojo.entity.Notice;
import com.kmbeast.pojo.vo.NoticeListItemVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 公告持久化接口
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 查询公告信息
     *
     * @param noticeQueryDto 查询条件类
     * @return List<NoticeListItemVO>
     */
    List<NoticeListItemVO> list(NoticeQueryDto noticeQueryDto);

    /**
     * 查询符合条件的记录数 - 配合前端做分页
     *
     * @param noticeQueryDto 查询条件类
     * @return Integer 记录数
     */
    Integer listCount(NoticeQueryDto noticeQueryDto);

}
