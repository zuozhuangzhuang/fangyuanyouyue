package com.fangyuanyouyue.user.service.impl;

import com.fangyuanyouyue.user.dao.*;
import com.fangyuanyouyue.user.model.IdentityAuthApply;
import com.fangyuanyouyue.user.model.UserInfoExt;
import com.fangyuanyouyue.user.service.UserInfoExtService;
import com.fangyuanyouyue.user.utils.DateStampUtils;
import com.fangyuanyouyue.user.utils.DateUtil;
import com.fangyuanyouyue.user.utils.FileUtil;
import com.fangyuanyouyue.user.utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service(value = "userInfoExtService")
public class UserInfoExtServiceImpl implements UserInfoExtService {
    @Value("${pic_server:errorPicServer}")
    private String PIC_SERVER;// 图片服务器

    @Value("${pic_path:errorPicPath}")
    private String PIC_PATH;// 图片存放路径


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
    @Autowired
    protected RedisTemplate redisTemplate;

    @Override
    public void certification(String token, String name, String identity, MultipartFile identityImgCover, MultipartFile identityImgBack) throws ServiceException {
        Integer userId = (Integer)redisTemplate.opsForValue().get(token);
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
                String date = DateUtil.getCurrentDate("/yyyy/MM/dd/");
                FileUtil util = new FileUtil();
                String fileName;
                if(identityImgCover != null){
                    try {
                        fileName = util.getFileName(identityImgCover, "IDENTITY_IMG_COVER");
                        String picName = fileName.toLowerCase();
                        if (picName.endsWith("jpeg") || picName.endsWith("png") || picName.endsWith("jpg")) {
                            util.saveFile(identityImgCover, PIC_PATH + date, fileName);
                            identityAuthApply.setIdentityImgCover(PIC_SERVER + date+fileName);
                        } else {
                            throw new ServiceException("请上传JPEG/PNG/JPG格式化图片！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException("保存身份证正面照片出错！");
                    }
                }
            }
            if(identityImgBack != null){
                String date = DateUtil.getCurrentDate("/yyyy/MM/dd/");
                FileUtil util = new FileUtil();
                String fileName;
                try {
                    fileName = util.getFileName(identityImgBack, "IDENTITY_IMG_BACK");
                    String picName = fileName.toLowerCase();
                    if (picName.endsWith("jpeg") || picName.endsWith("png") || picName.endsWith("jpg")) {
                        util.saveFile(identityImgBack, PIC_PATH + date, fileName);
                        identityAuthApply.setIdentityImgBack(PIC_SERVER + date+fileName);
                    } else {
                        throw new ServiceException("请上传JPEG/PNG/JPG格式化图片！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException("保存身份证背面照片出错！");
                }
            }
            identityAuthApplyMapper.insert(identityAuthApply);
        }
    }

}
