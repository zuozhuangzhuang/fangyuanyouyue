package com.fangyuanyouyue.user.service;

import com.fangyuanyouyue.user.utils.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    /**
     * 上传图片文件，获取下载url
     * @param file
     * @return
     * @throws ServiceException
     */
    String uploadFile(MultipartFile file) throws ServiceException;

    /**
     * 上传视频文件
     * @param file
     * @return
     * @throws ServiceException
     */
    String uploadVideo(MultipartFile file) throws ServiceException;
}
