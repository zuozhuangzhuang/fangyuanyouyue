package com.fangyuanyouyue.forum.controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.fangyuanyouyue.forum.service.SchedualUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fangyuanyouyue.forum.client.BaseController;
import com.fangyuanyouyue.forum.dto.ForumInfoDto;
import com.fangyuanyouyue.forum.param.ForumParam;
import com.fangyuanyouyue.forum.service.ForumInfoService;
import com.fangyuanyouyue.forum.utils.ReCode;
import com.fangyuanyouyue.forum.utils.ResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/forum")
@Api( description = "论坛Controller")
@RefreshScope
public class ForumController extends BaseController {
    protected Logger log = Logger.getLogger(this.getClass());
    
    @Autowired
    private ForumInfoService forumInfoService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SchedualUserService schedualUserService;//调用其他service时用

    @ApiOperation(value = "论坛详情", notes = "根据id获取论坛详情",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "forumId", value = "帖子id",required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/detail")
    @ResponseBody
    public String forumDetail(ForumParam param) throws IOException {
        try {
            log.info("----》获取帖子详情《----");
            log.info("参数：" + param.toString());
            
            
            ForumInfoDto dto = forumInfoService.getForumInfoById(param.getForumId());
            
            return toSuccess(dto,"");
        } catch (Exception e) {
            e.printStackTrace();
            return toError(ReCode.FAILD.getValue(),"系统繁忙，请稍后再试！");
        }
    }


    @ApiOperation(value = "获取帖子/视频列表", notes = "获取帖子/视频列表",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "start", value = "起始页数",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "每页个数",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型 1图文 2视频",required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/forumList")
    @ResponseBody
    public String forumList(ForumParam param) throws IOException {
        try {
            log.info("----》获取帖子/视频列表《----");
            log.info("参数：" + param.toString());
            //TODO 判断非空参数
            //TODO 获取帖子/视频列表
            return toSuccess("获取帖子/视频列表成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return toError(ReCode.FAILD.getValue(),"系统繁忙，请稍后再试！");
        }
    }


    @ApiOperation(value = "发布帖子/视频", notes = "发布帖子/视频",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token",required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "标题",required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "内容描述",required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "videoUrl", value = "视频地址",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "label", value = "标签",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "帖子类型 1帖子 2视频",required = true,dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/addForum")
    @ResponseBody
    public String addForum(ForumParam param) throws IOException {
        try {
            log.info("----》发布帖子/视频《----");
            log.info("参数：" + param.toString());
            //验证用户
            if(StringUtils.isEmpty(param.getToken())){
                return toError(ReCode.FAILD.getValue(),"用户token不能为空！");
            }
            Integer userId = (Integer)redisTemplate.opsForValue().get(param.getToken());
            String verifyUser = schedualUserService.verifyUserById(userId);
            JSONObject jsonObject = JSONObject.parseObject(verifyUser);
            if((Integer)jsonObject.get("code") != 0){
                return toError(jsonObject.getString("report"));
            }
            redisTemplate.expire(param.getToken(),7, TimeUnit.DAYS);
            //TODO 非空字段判断
            //TODO 发布帖子/视频
            return toSuccess("发布帖子/视频成功");
        } catch (Exception e) {
            e.printStackTrace();
            return toError(ReCode.FAILD.getValue(),"系统繁忙，请稍后再试！");
        }
    }

    public static void main(String[] args) {
    	
    }


}
