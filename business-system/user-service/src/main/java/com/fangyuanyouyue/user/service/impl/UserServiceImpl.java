package com.fangyuanyouyue.user.service.impl;

import com.fangyuanyouyue.user.dao.UserMapper;
import com.fangyuanyouyue.user.model.User;
import com.fangyuanyouyue.user.param.UserParam;
import com.fangyuanyouyue.user.service.UserService;
import com.fangyuanyouyue.user.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public User getUserByToken(String token) {
        return userMapper.getUserByToken(token);
    }

    @Override
    public void updateByPrimaryKey(UserParam param) {
        //根据手机号获取用户，修改密码
        User user = userMapper.getUserByPhone(param.getPhone());
        if(user!=null){
            user.setLoginPwd(MD5Util.getMD5String(param.getNewPwd()));
            userMapper.updateByPrimaryKey(user);
        }
        return ;
    }
}
