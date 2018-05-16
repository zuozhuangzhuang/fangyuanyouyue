package com.fangyuanyouyue.user.service;

import com.fangyuanyouyue.user.model.a_user;

import java.util.List;

public interface a_userService {
    List<a_user> getList(int pageNum, int pageSize);

    a_user selectByPrimaryKey(Integer id);

    a_user getUserByPhone(String phone);
}
