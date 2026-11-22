package com.kmbeast.controller;

import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.vo.ChartVO;
import com.kmbeast.service.MainService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 首页控制器
 */
@RestController
@RequestMapping("/main")
public class MainController {

    @Resource
    private MainService mainService;

    /**
     * 查询静态数据
     *
     * @return Result<v> 通用返回封装类
     */
    @ResponseBody
    @GetMapping(value = "/staticData")
    public Result<List<ChartVO>> staticData() {
        return mainService.staticData();
    }

    /**
     * 通过宠物类别下的宠物数
     *
     * @return Result<List < ChartVO>> 通用返回封装类
     */
    @ResponseBody
    @GetMapping(value = "/petTypeCount")
    public Result<List<ChartVO>> petTypeCount() {
        return mainService.petTypeCount();
    }

    /**
     * 宠物领养订单数目
     *
     * @param days 查询期限天数
     * @return Result<List < ChartVO>> 通用返回封装类
     */
    @ResponseBody
    @GetMapping(value = "/petAdoptOrderCount/{days}")
    public Result<List<ChartVO>> petAdoptOrderCount(@PathVariable Integer days) {
        return mainService.petAdoptOrderCount(days);
    }

}

