package com.kmbeast.controller;

import com.kmbeast.aop.Pager;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.NoticeQueryDto;
import com.kmbeast.pojo.entity.Notice;
import com.kmbeast.pojo.vo.NoticeListItemVO;
import com.kmbeast.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告控制器
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     * 公告新增
     *
     * @param notice 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody Notice notice) {
        return noticeService.saveEntity(notice);
    }

    /**
     * 公告修改
     *
     * @param notice 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PostMapping(value = "/update")
    public Result<String> update(@RequestBody Notice notice) {
        return noticeService.update(notice);
    }

    /**
     * 公告删除
     *
     * @param id 主键ID
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public Result<String> deleteById(@PathVariable Integer id) {
        noticeService.removeById(id);
        return ApiResult.success("公告删除成功");
    }

    /**
     * 通过ID查询公告数据
     *
     * @param id 主键ID
     * @return Result<Notice> 通用返回封装类
     */
    @ResponseBody
    @GetMapping(value = "/getById/{id}")
    public Result<Notice> getById(@PathVariable Integer id) {
        return ApiResult.success(noticeService.getById(id));
    }

    /**
     * 公告查询
     *
     * @param noticeQueryDto 实体数据
     * @return Result<List < NoticeListItemVO>> 通用返回封装类
     */
    @Pager
    @ResponseBody
    @PostMapping(value = "/list")
    public Result<List<NoticeListItemVO>> query(@RequestBody NoticeQueryDto noticeQueryDto) {
        return noticeService.query(noticeQueryDto);
    }

}

