package com.kmbeast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kmbeast.pojo.dto.ActiveNetQueryDto;
import com.kmbeast.pojo.entity.ActiveNet;
import com.kmbeast.pojo.vo.ScoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 行为互动持久化接口
 */
@Mapper
public interface ActiveNetMapper extends BaseMapper<ActiveNet> {

    /**
     * 查询行为互动信息
     *
     * @param activeNetQueryDto 查询条件类
     * @return List<ActiveNet>
     */
    List<ActiveNet> query(ActiveNetQueryDto activeNetQueryDto);

    /**
     * 通过指定指标降序，查询指定指标热度下的内容ID
     *
     * @param contentType 所属内容模块
     * @param type        互动类型
     * @param count       期望的条数
     * @return List<Integer>
     */
    List<ScoreVO> queryAllIds(@Param(value = "contentType") String contentType,
                              @Param(value = "type") Integer type,
                              @Param(value = "count") Integer count);

    /**
     * 查询符合条件的记录数 - 配合前端做分页
     *
     * @param activeNetQueryDto 查询条件类
     * @return Integer 记录数
     */
    Integer queryCount(ActiveNetQueryDto activeNetQueryDto);

    /**
     * 查询用户对于物品的评分矩阵数据
     *
     * @param contentType 所属模块
     * @return List<ScoreVO>
     */
    List<ScoreVO> queryScore(@Param(value = "contentType") String contentType);

    List<ActiveNet> listChart(ActiveNetQueryDto activeNetQueryDto);

}
