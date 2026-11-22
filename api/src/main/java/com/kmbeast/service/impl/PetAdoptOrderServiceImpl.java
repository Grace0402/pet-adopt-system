package com.kmbeast.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kmbeast.context.LocalThreadHolder;
import com.kmbeast.mapper.AddressMapper;
import com.kmbeast.mapper.PetAdoptOrderMapper;
import com.kmbeast.mapper.PetMapper;
import com.kmbeast.mapper.UserMapper;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetAdoptOrderQueryDto;
import com.kmbeast.pojo.em.IsAgainPostEnum;
import com.kmbeast.pojo.em.PetAdoptOrderStatus;
import com.kmbeast.pojo.entity.Address;
import com.kmbeast.pojo.entity.PetAdoptOrder;
import com.kmbeast.pojo.entity.User;
import com.kmbeast.pojo.vo.PetAdoptOrderVO;
import com.kmbeast.pojo.vo.PetVO;
import com.kmbeast.service.PetAdoptOrderService;
import com.kmbeast.utils.AssertUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 宠物领养订单业务逻辑实现类
 */
@Service
public class PetAdoptOrderServiceImpl extends ServiceImpl<PetAdoptOrderMapper, PetAdoptOrder> implements PetAdoptOrderService {

    @Resource
    private PetMapper petMapper;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 宠物领养订单生成
     *
     * @param petAdoptOrder 实体信息
     * @return Result<String>
     */
    @Override
    public Result<String> saveEntity(PetAdoptOrder petAdoptOrder) {
        AssertUtils.notNull(petAdoptOrder.getPetId(), "宠物ID不能为空");
        AssertUtils.notNull(petAdoptOrder.getAddressId(), "收货地址不能为空");
        AssertUtils.hasText(petAdoptOrder.getDetail(), "证明材料不能为空");
        // 宠物信息校验
        PetVO petVO = petMapper.getById(petAdoptOrder.getPetId());
        AssertUtils.notNull(petVO, "宠物信息异常");
        AssertUtils.isTrue(!petVO.getIsAdopt(), "宠物已被领养");
        // 收货地址校验
        Address address = addressMapper.selectById(petAdoptOrder.getAddressId());
        AssertUtils.notNull(address, "收货地址信息异常");
        // 宠物现在绑定的订单
        PetAdoptOrderQueryDto petAdoptOrderQueryDto = new PetAdoptOrderQueryDto();
        petAdoptOrderQueryDto.setPetId(petAdoptOrder.getPetId());
        orderStatusConfirm(petAdoptOrderQueryDto);
        // 创建订单信息
        petAdoptOrder.setStatus(PetAdoptOrderStatus.REPLYING.getStatus()); // 初始领养订单生成，是申请中状态
        petAdoptOrder.setUserId(LocalThreadHolder.getUserId()); // 设置用户ID
        petAdoptOrder.setCreateTime(LocalDateTime.now()); // 设置创建时间
        petAdoptOrder.setPostNumber(1); // 初次提交1次
        petAdoptOrder.setIsAgainPost(IsAgainPostEnum.ORIGIN_REPLY.getStatus()); // 设置为初次提交状态
        save(petAdoptOrder);
        return ApiResult.success("宠物订单领养成功，请耐心等待审核");
    }

    private void orderStatusConfirm(PetAdoptOrderQueryDto petAdoptOrderQueryDto) {
        List<PetAdoptOrderVO> petAdoptOrderVOS = this.baseMapper.list(petAdoptOrderQueryDto);
        if (!petAdoptOrderVOS.isEmpty()) {
            for (PetAdoptOrderVO petAdoptOrderVO : petAdoptOrderVOS) {
                AssertUtils.isTrue(
                        !Objects.equals(petAdoptOrderVO.getStatus(), PetAdoptOrderStatus.REPLYING.getStatus()),
                        "已有用户正在申请领养该宠物，请关注后续情况"
                );
            }
        }
    }

    /**
     * 宠物领养订单修改
     * 1、 管理员审核用户创建的订单（申请中...）
     * 2、如果是审核不通过，用户调用这一块逻辑进行材料补充
     *
     * @param petAdoptOrder 实体信息
     * @return Result<String>
     */
    @Override
    public Result<String> update(PetAdoptOrder petAdoptOrder) {
        updateById(petAdoptOrder);
        return ApiResult.success("操作成功");
    }

    /**
     * 宠物领养订单查询
     *
     * @param petAdoptOrderQueryDto 宠物领养信息查询条件拓展类
     * @return Result<List < PetAdoptOrderVO>>
     */
    @Override
    public Result<List<PetAdoptOrderVO>> query(PetAdoptOrderQueryDto petAdoptOrderQueryDto) {
        List<PetAdoptOrderVO> petAdoptOrderVOS = this.baseMapper.list(petAdoptOrderQueryDto);
        Integer count = this.baseMapper.listCount(petAdoptOrderQueryDto);
        return ApiResult.success(petAdoptOrderVOS, count);
    }

    /**
     * 管理员审核用户创建的订单
     *
     * @param petAdoptOrder 宠物领养订单信息
     * @return Result<String>
     */
    @Override
    public Result<String> audit(PetAdoptOrder petAdoptOrder) {
        // 判断当前审核者是不是管理员
        User user = userMapper.getUserById(LocalThreadHolder.getUserId());
        AssertUtils.notNull(user, "用户信息查询异常");
        AssertUtils.isTrue(user.getRole() == 1, "无操作权限");
        return update(petAdoptOrder);
    }

    /**
     * 如果是审核不通过，用户调用这一块逻辑进行材料补充
     *
     * @param petAdoptOrder 宠物领养订单信息
     * @return Result<String>
     */
    @Override
    public Result<String> againReply(PetAdoptOrder petAdoptOrder) {
        AssertUtils.notNull(petAdoptOrder.getId(), "订单ID不能为空");
        AssertUtils.hasText(petAdoptOrder.getDetail(), "证明材料不能为空");
        PetAdoptOrder adoptOrder = getById(petAdoptOrder.getId());
        AssertUtils.notNull(adoptOrder, "查询订单信息异常");
        // 宠物现在绑定的订单
        PetAdoptOrderQueryDto petAdoptOrderQueryDto = new PetAdoptOrderQueryDto();
        petAdoptOrderQueryDto.setPetId(petAdoptOrder.getPetId());
        orderStatusConfirm(petAdoptOrderQueryDto);
        adoptOrder.setIsAgainPost(IsAgainPostEnum.AGAIN_REPLY.getStatus()); // 设置为再次提交状态\
        adoptOrder.setPostNumber(adoptOrder.getPostNumber() + 1);
        adoptOrder.setDetail(petAdoptOrder.getDetail());
        return update(adoptOrder);
    }
}
