package com.fangyuanyouyue.auth.controller;

import com.fangyuanyouyue.auth.AuthServiceApplication;
import com.fangyuanyouyue.auth.client.BaseClientResult;
import com.fangyuanyouyue.auth.client.BaseController;
import com.fangyuanyouyue.auth.model.User;
import com.fangyuanyouyue.auth.param.BaseParam;
import com.fangyuanyouyue.auth.service.user.UserService;
import com.fangyuanyouyue.auth.utils.Status;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController{

    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @ApiOperation(value="新增用户", notes="新增用户接口")
    @ResponseBody
    @PostMapping("/add")
    public int addUser(User user){
        return userService.addUser(user);
    }

    @ApiOperation(value="分页获取用户列表", notes="获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "pageNum", dataType = "int", name = "pageNum", value = "页数", required = true),
            @ApiImplicitParam(paramType = "pageSize", dataType = "int", name = "pageSize", value = "每页数量", required = true)
    })
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求成功",response = String.class)})
    @ResponseBody
    @GetMapping("/all")
    public Object findAllUser(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize){
        //开始分页
        PageHelper.startPage(pageNum,pageSize);
        return userService.findAllUser(pageNum,pageSize);
    }



//    @ApiOperation(value="index", notes="index")
    @ApiOperation(value="hello", notes="hello")
    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello World";
    }

    //    }
    @ApiOperation(value="getUser", notes="getUser")
    @GetMapping(value = "/getUser")
    public User getUser(BaseParam param) {
        User user=new User();
        user.setUserName("小明");
        return user;
    }

    //        return userService.index();
    @ApiOperation(value="获取信息", notes="根据type获取信息")
    @ApiImplicitParam(name = "type", value = "类型", required = true, dataType = "String",paramType = "query")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求成功",response = String.class)})
    @PostMapping(value = "/getResult")
    @ResponseBody
    public String getResult(BaseParam param) throws IOException {
        try{
            log.info("----》测试获取结果《----");
            log.info("参数："+param.toString());
            System.out.println(param.toString());
            System.out.println(param.getType());
            if(StringUtils.isEmpty(param.getType())){
                return toError("类型为空！");
            }
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(),"哟，宝贝儿，被你发现我了！");
            if(StringUtils.isNotEmpty(param.getName())) {
                result.put("user",new User());
            }
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    //    public String index(){

    //    @GetMapping(value = "/index")
    @Value("${service_name}")
    String service_name;
    @Value("${server.port}")
    String port;
    @ApiOperation(value="auth", notes="auth测试")
    @ApiImplicitParam(name = "name", value = "名称", dataType = "String",paramType = "query")
    @PostMapping("/auth")
    @ResponseBody
    public String auth(@RequestParam String name) {
        log.debug("calling trace based-business  ");
        return service_name + ",auth "+name+",i am from port:" +port;
    }

}
