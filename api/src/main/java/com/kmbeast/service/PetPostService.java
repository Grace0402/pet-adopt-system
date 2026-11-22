package com.kmbeast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.ActiveNetQueryDto;
import com.kmbeast.pojo.dto.PetPostQueryDto;
import com.kmbeast.pojo.entity.ActiveNet;
import com.kmbeast.pojo.entity.PetPost;
import com.kmbeast.pojo.vo.PetPostListItemVO;
import com.kmbeast.pojo.vo.PetPostSelectedVO;
import com.kmbeast.pojo.vo.PetPostVO;

import java.util.List;

/**
 * 宠物经验帖子业务逻辑接口
 */
public interface PetPostService extends IService<PetPost> {

    Result<List<PetPostListItemVO>> list(PetPostQueryDto petPostQueryDto);

    Result<PetPostVO> getById(Integer id);

    Result<String> saveEntity(PetPost petPost);

    Result<String> updateEntity(PetPost petPost);

    Result<String> audit(Integer id);

    Result<List<PetPostListItemVO>> autoRecommend(Integer count);

    Result<List<PetPostListItemVO>> saveList();

    Result<List<PetPostSelectedVO>> listPetPostSelect();

}
