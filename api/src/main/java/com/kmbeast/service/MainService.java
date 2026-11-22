package com.kmbeast.service;

import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.vo.ChartVO;

import java.util.List;

/**
 * 首页业务逻辑接口
 */
public interface MainService {

    Result<List<ChartVO>> staticData();

    Result<List<ChartVO>> petTypeCount();

    Result<List<ChartVO>> petAdoptOrderCount(Integer days);


}
