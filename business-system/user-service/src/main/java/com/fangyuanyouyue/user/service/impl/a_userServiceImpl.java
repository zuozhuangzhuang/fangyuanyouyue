package com.fangyuanyouyue.user.service.impl;

import com.fangyuanyouyue.user.dao.a_userMapper;
import com.fangyuanyouyue.user.model.a_user;
import com.fangyuanyouyue.user.service.a_userService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "a_userService")
public class a_userServiceImpl  implements a_userService {
    @Autowired
    private a_userMapper a_userMapper;


    @Override
    public List<a_user> getList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return a_userMapper.getList();
    }


    @Override
    public a_user selectByPrimaryKey(Integer id) {
        return a_userMapper.selectByPrimaryKey(id);
    }

    @Override
    public a_user getUserByPhone(String phone) {
        return a_userMapper.getUserByPhone(phone);
    }
}
