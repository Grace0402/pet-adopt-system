package com.kmbeast.controller;

import com.kmbeast.aop.Pager;
import com.kmbeast.context.LocalThreadHolder;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.ProposalFeedbackQueryDto;
import com.kmbeast.pojo.entity.ProposalFeedback;
import com.kmbeast.pojo.vo.ProposalFeedbackVO;
import com.kmbeast.service.ProposalFeedbackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 意见与反馈控制器
 */
@RestController
@RequestMapping("/proposal-feedback")
public class ProposalFeedbackController {

    @Resource
    private ProposalFeedbackService proposalFeedbackService;

    /**
     * 意见与反馈新增
     *
     * @param proposalFeedback 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody ProposalFeedback proposalFeedback) {
        return proposalFeedbackService.saveEntity(proposalFeedback);
    }

    /**
     * 意见与反馈修改
     *
     * @param proposalFeedback 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PostMapping(value = "/update")
    public Result<String> update(@RequestBody ProposalFeedback proposalFeedback) {
        return proposalFeedbackService.update(proposalFeedback);
    }

    /**
     * 意见与反馈删除
     *
     * @param id 主键ID
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public Result<String> deleteById(@PathVariable Integer id) {
        proposalFeedbackService.removeById(id);
        return ApiResult.success("意见与反馈删除成功");
    }

    /**
     * 查询用户自己的意见与反馈
     *
     * @param proposalFeedbackQueryDto 实体数据
     * @return Result<List < ProposalFeedbackVO>> 通用返回封装类
     */
    @Pager
    @ResponseBody
    @PostMapping(value = "/listUser")
    public Result<List<ProposalFeedbackVO>> listUser(@RequestBody ProposalFeedbackQueryDto proposalFeedbackQueryDto) {
        proposalFeedbackQueryDto.setUserId(LocalThreadHolder.getUserId()); // 设置用户ID，以便数据隔离
        return proposalFeedbackService.query(proposalFeedbackQueryDto);
    }

    /**
     * 意见与反馈查询
     *
     * @param proposalFeedbackQueryDto 实体数据
     * @return Result<List < ProposalFeedbackVO>> 通用返回封装类
     */
    @Pager
    @ResponseBody
    @PostMapping(value = "/list")
    public Result<List<ProposalFeedbackVO>> list(@RequestBody ProposalFeedbackQueryDto proposalFeedbackQueryDto) {
        return proposalFeedbackService.query(proposalFeedbackQueryDto);
    }

}

