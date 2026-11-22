package com.kmbeast.pojo.vo;

import com.kmbeast.pojo.entity.PetAdoptOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 宠物领养订单信息VO类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PetAdoptOrderVO extends PetAdoptOrder {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 宠物名称
     */
    private String petName;
    /**
     * 宠物封面
     */
    private String cover;
    /**
     * 收件人
     */
    private String addressee;
    /**
     * 联系电话
     */
    private String concatPhone;
    /**
     * 详细收货地址
     */
    private String detailAddress;
}
