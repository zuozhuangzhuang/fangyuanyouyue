package com.fangyuanyouyue.user.user.impl;

import com.fangyuanyouyue.user.dao.a_userMapper;
import com.fangyuanyouyue.user.model.a_user;
import com.fangyuanyouyue.user.user.a_userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "a_userService")
public class a_userServiceImpl  implements a_userService{
    @Autowired
    private a_userMapper a_userMapper;


    @Override
    public List<a_user> getList() {
        return a_userMapper.getList();
    }
}
