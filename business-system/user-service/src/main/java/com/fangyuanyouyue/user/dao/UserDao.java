package com.fangyuanyouyue.user.dao;


import com.fangyuanyouyue.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {


    int insert(User record);



    List<User> selectUsers();
}