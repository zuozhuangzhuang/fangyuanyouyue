package com.fangyuanyouyue.auth.service.user;

import com.fangyuanyouyue.auth.model.User;

import java.util.List;

public interface UserService {

    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);
}
