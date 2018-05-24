package com.fangyuanyouyue.user.service;

import com.fangyuanyouyue.user.model.User;
import com.fangyuanyouyue.user.param.UserParam;

import java.util.List;

public interface UserService {


    /**
     * 根据ID获取用户
     * @param id
     * @return
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 根据手机获取用户
     * @param phone
     * @return
     */
    User getUserByPhone(String phone);

    /**
     * 根据token获取用户
     * @param token
     * @return
     */
    User getUserByToken(String token);

    /**
     * 修改用户信息
     * @param param
     * @return
     */
    void updateByPrimaryKey(UserParam param);
}
