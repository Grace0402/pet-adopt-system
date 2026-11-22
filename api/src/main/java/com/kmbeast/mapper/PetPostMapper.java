package com.kmbeast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kmbeast.pojo.dto.PetPostQueryDto;
import com.kmbeast.pojo.entity.PetPost;
import com.kmbeast.pojo.vo.PetPostListItemVO;
import com.kmbeast.pojo.vo.PetPostSelectedVO;
import com.kmbeast.pojo.vo.PetPostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 宠物经验帖子持久化接口
 */
@Mapper
public interface PetPostMapper extends BaseMapper<PetPost> {

    /**
     * 查询宠物经验帖子信息
     *
     * @param petPostQueryDto 查询条件类
     * @return List<PetPostListItemVO>
     */
    List<PetPostListItemVO> list(PetPostQueryDto petPostQueryDto);

    /**
     * 查询符合条件的记录数 - 配合前端做分页
     *
     * @param petPostQueryDto 查询条件类
     * @return Integer 记录数
     */
    Integer listCount(PetPostQueryDto petPostQueryDto);

    /**
     * 通过主键ID查询宠物帖子信息
     * @param id 主键ID
     * @return PetPostVO
     */
    PetPostVO getById(@Param(value = "id") Integer id);

    /**
     * 查询全部的宠物经验帖子ID列表
     * @return List<Integer>
     */
    List<Integer> queryAllIds();

    /**
     * 通过宠物经验帖子的ID集合查询帖子数据
     * @param ids ID列表
     * @return List<PetPostListItemVO>
     */
    List<PetPostListItemVO> queryListItemByIds(@Param(value = "ids") List<Integer> ids);

    List<PetPostSelectedVO> querySelectedVO(@Param(value = "userId") Integer userId);

}
