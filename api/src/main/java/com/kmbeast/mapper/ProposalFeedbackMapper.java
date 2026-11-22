package com.kmbeast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kmbeast.pojo.dto.ProposalFeedbackQueryDto;
import com.kmbeast.pojo.entity.ProposalFeedback;
import com.kmbeast.pojo.vo.ProposalFeedbackVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 意见与反馈持久化接口
 */
@Mapper
public interface ProposalFeedbackMapper extends BaseMapper<ProposalFeedback> {

    /**
     * 查询意见与反馈信息
     *
     * @param proposalFeedbackQueryDto 查询条件类
     * @return List<ProposalFeedbackVO>
     */
    List<ProposalFeedbackVO> list(ProposalFeedbackQueryDto proposalFeedbackQueryDto);

    /**
     * 查询符合条件的记录数 - 配合前端做分页
     *
     * @param proposalFeedbackQueryDto 查询条件类
     * @return Integer 记录数
     */
    Integer listCount(ProposalFeedbackQueryDto proposalFeedbackQueryDto);

}
