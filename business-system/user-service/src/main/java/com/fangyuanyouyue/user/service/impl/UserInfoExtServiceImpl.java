package com.fangyuanyouyue.user.service.impl;

import com.fangyuanyouyue.user.dao.*;
import com.fangyuanyouyue.user.model.IdentityAuthApply;
import com.fangyuanyouyue.user.model.UserInfoExt;
import com.fangyuanyouyue.user.service.UserInfoExtService;
import com.fangyuanyouyue.user.utils.DateStampUtils;
import com.fangyuanyouyue.user.utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userInfoExtService")
public class UserInfoExtServiceImpl implements UserInfoExtService {

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
    public void certification(Integer userId, String name, String identity, String identityImgCover, String identityImgBack) throws ServiceException {
        IdentityAuthApply identityAuthApply = identityAuthApplyMapper.selectByUserId(userId);
        if(identityAuthApply != null){
            if(identityAuthApply.getStatus() == 1){
                throw new ServiceException("您已提交过实名认证，请耐心等待！");
            }else if(identityAuthApply.getStatus() == 2){
                throw new ServiceException("您的实名认证已通过，请勿重复提交！");
            }
        }else{
            //用户扩展信息表
            UserInfoExt userInfoExt = userInfoExtMapper.selectByUserId(userId);
            userInfoExt.setUpdateTime(DateStampUtils.getTimesteamp());
            userInfoExt.setIdentity(identity);
            userInfoExt.setName(name);
            userInfoExt.setUserId(userId);
            userInfoExt.setStatus(2);
            userInfoExtMapper.insert(userInfoExt);

            //实名认证申请表
            identityAuthApply.setAddTime(DateStampUtils.getTimesteamp());
            identityAuthApply.setUpdateTime(DateStampUtils.getTimesteamp());
            identityAuthApply.setIdentity(identity);
            identityAuthApply.setName(name);
            identityAuthApply.setStatus(1);
            if(identityImgCover != null && !identityImgCover.equals("")){
                identityAuthApply.setIdentityImgCover(identityImgCover);
            }
            if(identityImgBack != null && !identityImgBack.equals("")){
                identityAuthApply.setIdentityImgBack(identityImgBack);
            }
            identityAuthApplyMapper.insert(identityAuthApply);
        }
    }

}
