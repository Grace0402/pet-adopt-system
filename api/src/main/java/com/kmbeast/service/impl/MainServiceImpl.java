package com.kmbeast.service.impl;

import com.kmbeast.mapper.*;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.*;
import com.kmbeast.pojo.vo.ChartVO;
import com.kmbeast.pojo.vo.PetAdoptOrderVO;
import com.kmbeast.service.MainService;
import com.kmbeast.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 首页业务逻辑接口实现类
 */
@Service
public class MainServiceImpl implements MainService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private PetMapper petMapper;
    @Resource
    private PetPostMapper petPostMapper;
    @Resource
    private PetAdoptOrderMapper petAdoptOrderMapper;
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private PetTypeMapper petTypeMapper;

    /**
     * 统计静态数据
     *
     * @return Result<List < ChartVO>>
     */
    @Override
    public Result<List<ChartVO>> staticData() {
        List<ChartVO> chartVOS = new ArrayList<>();
        // 统计用户数
        int userCount = userMapper.queryCount(new UserQueryDto());
        ChartVO chartVOUser = new ChartVO("用户数", userCount);
        chartVOS.add(chartVOUser);
        // 统计宠物信息
        int petCount = petMapper.queryCount(new PetQueryDto());
        ChartVO chartVOPet = new ChartVO("宠物数", petCount);
        chartVOS.add(chartVOPet);
        // 统计宠物经验帖子
        int petPostCount = petPostMapper.queryAllIds().size();
        ChartVO chartVOPetPost = new ChartVO("宠物经验帖子数", petPostCount);
        chartVOS.add(chartVOPetPost);
        // 统计宠物领养订单
        int petAdoptOrderCount = petAdoptOrderMapper.listCount(new PetAdoptOrderQueryDto());
        ChartVO chartVOPetAdoptOrder = new ChartVO("宠物领养订单数", petAdoptOrderCount);
        chartVOS.add(chartVOPetAdoptOrder);
        // 统计公告数
        int noticeCount = noticeMapper.listCount(new NoticeQueryDto());
        ChartVO chartVONotice = new ChartVO("公告数", noticeCount);
        chartVOS.add(chartVONotice);
        return ApiResult.success(chartVOS);
    }

    /**
     * 通过宠物类别下的宠物数
     *
     * @return Result<List < ChartVO>> 通用返回封装类
     */
    @Override
    public Result<List<ChartVO>> petTypeCount() {
        List<ChartVO> chartVOS = petTypeMapper.petTypeCount();
        return ApiResult.success(chartVOS);
    }

    /**
     * 宠物领养订单数目
     *
     * @return Result<List < ChartVO>> 通用返回封装类
     */
    @Override
    public Result<List<ChartVO>> petAdoptOrderCount(Integer days) {
        QueryDto queryDto = DateUtil.startAndEndTime(days);
        PetAdoptOrderQueryDto petAdoptOrderQueryDto = new PetAdoptOrderQueryDto();
        petAdoptOrderQueryDto.setStartTime(queryDto.getStartTime());
        petAdoptOrderQueryDto.setEndTime(queryDto.getEndTime());
        List<PetAdoptOrderVO> petAdoptOrderVOS = petAdoptOrderMapper.list(petAdoptOrderQueryDto);
        if (petAdoptOrderVOS.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        List<LocalDateTime> dateTimeList = petAdoptOrderVOS.stream()
                .map(PetAdoptOrderVO::getCreateTime)
                .collect(Collectors.toList());
        List<ChartVO> chartVOS = DateUtil.countDatesWithinRange(days, dateTimeList);
        return ApiResult.success(chartVOS);
    }
}
