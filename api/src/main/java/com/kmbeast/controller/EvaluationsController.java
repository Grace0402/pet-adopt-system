package com.kmbeast.controller;

import com.kmbeast.aop.Pager;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.EvaluationsQueryDto;
import com.kmbeast.pojo.entity.Evaluations;
import com.kmbeast.service.EvaluationsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 评论 Controller
 */
@RestController
@RequestMapping("/evaluations")
public class EvaluationsController {

    @Resource
    private EvaluationsService evaluationsService;

    /**
     * 评论
     *
     * @return Result<String>
     */
    @PostMapping(value = "/insert")
    @ResponseBody
    public Result<Object> insert(@RequestBody Evaluations evaluations) {
        return evaluationsService.insert(evaluations);
    }

    /**
     * 点赞或取消点赞
     *
     * @return Result<String>
     */

    @PostMapping(value = "/upvoteOperation")
    @ResponseBody
    public Result<Object> upvoteOperation(@RequestBody Evaluations evaluations) {
        return evaluationsService.upvoteOperation(evaluations);
    }

    /**
     * 评论修改
     *
     * @return Result<String>
     */

    @PutMapping(value = "/update")
    @ResponseBody
    public Result<Void> update(@RequestBody Evaluations evaluations) {
        return evaluationsService.update(evaluations);
    }

    /**
     * 查询内容下的全部评论
     *
     * @return Result<String>
     */
    @GetMapping(value = "/list/{contentId}/{contentType}")
    @ResponseBody
    public Result<Object> list(@PathVariable Integer contentId,
                               @PathVariable String contentType) {
        return evaluationsService.list(contentId, contentType);
    }

    /**
     * 分页查询评论
     *
     * @return Result<String>
     */
    @Pager
    @PostMapping(value = "/query")
    @ResponseBody
    public Result<Object> query(@RequestBody EvaluationsQueryDto evaluationsQueryDto) {
        return evaluationsService.query(evaluationsQueryDto);
    }

    /**
     * 通过ID删除评论信息
     *
     * @return Result<String>
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        return evaluationsService.deleteById(id);
    }

}

