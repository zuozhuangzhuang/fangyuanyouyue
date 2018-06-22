package com.fangyuanyouyue.user.dao;

import com.fangyuanyouyue.user.model.UserThirdParty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserThirdPartyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserThirdParty record);

    int insertSelective(UserThirdParty record);

    UserThirdParty selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserThirdParty record);

    int updateByPrimaryKey(UserThirdParty record);

    /**
     * 根据第三方唯一ID和类型获取第三方登录信息
     * @param unionId
     * @param type
     * @return
     */
    UserThirdParty getUserByThirdNoType(@Param("unionId") String unionId, @Param("type") int type);
}