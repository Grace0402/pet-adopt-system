package com.kmbeast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.ProposalFeedbackQueryDto;
import com.kmbeast.pojo.entity.ProposalFeedback;
import com.kmbeast.pojo.vo.ProposalFeedbackVO;

import java.util.List;

/**
 * 意见与反馈业务逻辑接口
 */
public interface ProposalFeedbackService extends IService<ProposalFeedback> {

    Result<String> saveEntity(ProposalFeedback proposalFeedback);

    Result<String> update(ProposalFeedback proposalFeedback);

    Result<List<ProposalFeedbackVO>> query(ProposalFeedbackQueryDto proposalFeedbackQueryDto);

}
