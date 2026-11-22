package com.kmbeast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.NoticeQueryDto;
import com.kmbeast.pojo.dto.PetTypeQueryDto;
import com.kmbeast.pojo.entity.Notice;
import com.kmbeast.pojo.entity.PetType;
import com.kmbeast.pojo.vo.NoticeListItemVO;

import java.util.List;

/**
 * 公告业务逻辑接口
 */
public interface NoticeService extends IService<Notice> {

    Result<String> saveEntity(Notice notice);

    Result<String> update(Notice notice);

    Result<List<NoticeListItemVO>> query(NoticeQueryDto noticeQueryDto);

}
