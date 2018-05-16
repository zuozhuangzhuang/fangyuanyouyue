package com.fangyuanyouyue.auth.dao;


import com.fangyuanyouyue.auth.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
public interface UserDao {


    int insert(User record);



    List<User> selectUsers();
}