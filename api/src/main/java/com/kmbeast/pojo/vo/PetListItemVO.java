package com.kmbeast.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 宠物列表VO类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetListItemVO {
    /**
     * 宠物信息表主键ID
     */
    private Integer id;
    /**
     * 宠物名
     */
    private String name;
    /**
     * 宠物封面图
     */
    private String cover;
    /**
     * 宠物所在地
     */
    private String address;
    /**
     * 宠物年龄
     */
    private Integer age;
    /**
     * 宠物性别（0：公；1：母）
     */
    private Integer gender;
    /**
     * 宠物类别ID
     */
    private Integer petTypeId;
    /**
     * 宠物类别名
     */
    private String petTypeName;
    /**
     * 是否已经接种疫苗(0:未接种；1：已接种)
     */
    private Boolean isVaccine;
    /**
     * 是否推荐（0：未推荐；1：推荐）
     */
    private Boolean isRecommend;
    /**
     * 是否领养（0：未领养；1：已领养）
     */
    private Boolean isAdopt;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
