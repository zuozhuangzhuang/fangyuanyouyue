package com.fangyuanyouyue.user.service.impl;

import com.aliyun.oss.OSSClient;
import com.fangyuanyouyue.user.service.FileUploadService;
import com.fangyuanyouyue.user.utils.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service(value = "uploadFileService")
public class FileUploadServiceImpl implements FileUploadService{
    @Value("${access_key_id}")
    private String accessKeyId;

    @Value("${access_key_secret}")
    private String accessKeySecret;

    @Value("${endpoint}")
    private String endpoint;

    @Value("${oss_path}")
    private String ossPath;

    @Value("${bucket}")
    private String bucket;

    @Override
    public List<String> uploadFile(MultipartFile[] files){
        List<String> firleUrls = new ArrayList<>();
        for(MultipartFile file:files){
            String fileUrl = null;
            String fileName = getFileName(file.getOriginalFilename());
            fileName = "pic" + fileName;
            try{
                OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
                // 上传文件流file
                ossClient.putObject(bucket, fileName, file.getInputStream());
                // 关闭client
                ossClient.shutdown();

                fileUrl = ossPath+fileName;
                firleUrls.add(fileUrl);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return firleUrls;
    }

    /**
     * 获取文件名
     * @param fileName
     * @return
     */
    private String getFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        String suffix = fileName.substring(dotIndex);
        fileName = UUID.randomUUID()+suffix;
        String date = DateUtil.getCurrentDate("/yyyy/MM/dd/");
        return date + fileName;
    }
}
