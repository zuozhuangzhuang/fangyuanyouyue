package com.fangyuanyouyue.user.service;

import com.fangyuanyouyue.user.model.User;

import java.util.List;

public interface UserService {
    List<User> getList(int pageNum, int pageSize);

    User selectByPrimaryKey(Integer id);

    User getUserByPhone(String phone);

    User getUserByToken(String token);
}
