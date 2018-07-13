package com.fangyuanyouyue.user.service;

import com.fangyuanyouyue.user.utils.ServiceException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 实名认证相关接口
 */
public interface UserInfoExtService {
    /**
     * 实名认证
     * @param token
     * @param name
     * @param identity
     * @param identityImgCoverUrl
     * @param identityImgBackUrl
     * @throws ServiceException
     */
    void certification(String token, String name, String identity, String identityImgCoverUrl, String identityImgBackUrl) throws ServiceException;
}
