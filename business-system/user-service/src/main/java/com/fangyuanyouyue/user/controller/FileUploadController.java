package com.fangyuanyouyue.user.controller;

import com.fangyuanyouyue.user.client.BaseController;
import com.fangyuanyouyue.user.param.UserParam;
import com.fangyuanyouyue.user.service.FileUploadService;
import com.fangyuanyouyue.user.utils.ReCode;
import com.fangyuanyouyue.user.utils.ResultUtil;
import com.fangyuanyouyue.user.utils.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/file")
@Api(description = "文件上传系统Controller")
@RefreshScope
public class FileUploadController extends BaseController {
    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private FileUploadService fileUploadService;

    @ApiOperation(value = "图片上传", notes = "(String)图片上传",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imgFile", value = "图片，格式为：jpeg，png，jpg",dataType = "file", paramType = "form")
    })
    @PostMapping(value = "/uploadPic")
    @ResponseBody
    public String uploadPic(UserParam param) throws IOException {
        try {
            log.info("----》图片上传《----");
            log.info("参数：" + param.toString());
            if(param.getImgFile() == null){
                return toError(ReCode.FAILD.getValue(),"图片为空");
            }
            String name = param.getImgFile().getOriginalFilename().toLowerCase();
            if(!(name.endsWith("jpeg") || name.endsWith("png")
                    || name.endsWith("jpg"))){
                return toError("请上传JPEG/PNG/JPG格式化图片！");
            }
            //图片上传
            String url= fileUploadService.uploadFile(param.getImgFile());
            return toSuccess(url,"图片上传成功");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError(ReCode.FAILD.getValue(),"系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "视频上传", notes = "(String)视频上传",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoFile", value = "视频",dataType = "file", paramType = "form")
    })
    @PostMapping(value = "/uploadVideo")
    @ResponseBody
    public String uploadVideo(UserParam param) throws IOException {
        try {
            log.info("----》视频上传《----");
            log.info("参数：" + param.toString());
            if(param.getVideoFile() == null){
                return toError(ReCode.FAILD.getValue(),"文件为空");
            }
            //视频上传
            String url= fileUploadService.uploadVideo(param.getVideoFile());
            return toSuccess(url,"视频上传成功");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError(ReCode.FAILD.getValue(),"系统繁忙，请稍后再试！");
        }
    }
}
