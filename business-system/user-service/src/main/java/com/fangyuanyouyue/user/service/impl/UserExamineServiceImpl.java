package com.fangyuanyouyue.user.service.impl;

import com.fangyuanyouyue.user.dao.*;
import com.fangyuanyouyue.user.model.UserExamine;
import com.fangyuanyouyue.user.model.UserInfo;
import com.fangyuanyouyue.user.service.UserExamineService;
import com.fangyuanyouyue.user.utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userExamineService")
public class UserExamineServiceImpl implements UserExamineService{

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserThirdPartyMapper userThirdPartyMapper;
    @Autowired
    private IdentityAuthApplyMapper identityAuthApplyMapper;
    @Autowired
    private UserInfoExtMapper userInfoExtMapper;
    @Autowired
    private UserAddressInfoMapper userAddressInfoMapper;
    @Autowired
    private UserVipMapper userVipMapper;
    @Autowired
    private UserExamineMapper userExamineMapper;


    @Override
    public UserExamine getUserExamineByUserId(Integer userId) throws ServiceException {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo == null){
            throw new ServiceException("用户不存在！");
        }else{
            UserExamine userExamine = userExamineMapper.getUserExamineByUserId(userId);
            if(userExamine == null){
                throw new ServiceException("该用户无昵称申请记录！");
            }else{
                return userExamine;
            }
        }
    }
}
