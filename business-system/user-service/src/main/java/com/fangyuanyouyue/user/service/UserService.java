package com.fangyuanyouyue.user.service;

import com.fangyuanyouyue.user.model.User;

import java.util.List;

public interface UserService {

    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);
}
