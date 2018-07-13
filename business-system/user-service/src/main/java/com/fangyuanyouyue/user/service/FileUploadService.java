package com.fangyuanyouyue.user.service;

import com.fangyuanyouyue.user.utils.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    /**
     * 上传文件，获取下载url
     * @param files
     * @return
     * @throws ServiceException
     */
    List<String> uploadFile(MultipartFile[] files) throws ServiceException;
}
