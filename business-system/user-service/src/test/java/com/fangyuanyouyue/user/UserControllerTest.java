package com.fangyuanyouyue.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@WebAppConfiguration
@ContextConfiguration
@Rollback
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }


    /*@Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new TestController()).build();
    }*/

//    perform：执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
//    andExpect：添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确；
//    andDo：添加ResultHandler结果处理器，比如调试时打印结果到控制台；
//    andReturn：最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理；
//    accept：指定请求的Accept头信息；

    /**
     * 注册
     * @throws Exception
     */
    @Test
    @Transactional
    public void regist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/regist")
                .param("phone","18103966057")
                .param("loginPwd","123456")
                .param("nickName","昵称")
//                .param("headImgUrl","123456")
                .param("gender","1")
                .param("regPlatform","1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 登录
     * @throws Exception
     */
    @Test
    @Transactional
    public void login() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/login")
                .param("phone","18103966057")
                .param("loginPwd","123456")
                .param("lastLoginPlatform","1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 三方注册
     * @throws Exception
     */
    @Test
    @Transactional
    public void thirdRegister() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/thirdRegister")
                .param("thirdNickName","偷看")
                .param("thirdHeadImgUrl","http://app.fangyuanyouyue.com/static/pic/2018/03/05/HEADIMG_180305120832068.jpg")
                .param("gender","0")
                .param("unionId","oJ9SjwtB9Yqh_6pvlAaoIP3QvhwE")
                .param("regType","2")
                .param("regPlatform","3")
                .param("type","1")
//                .param("phone","18103966057")
//                .param("loginPwd","123456")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * APP三方登录
     * @throws Exception
     */
    @Test
    @Transactional
    public void thirdLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/thirdLogin")
                .param("unionId","oJ9SjwsSoaWNMR_xflHnyaRnUf2Q")
                .param("type","1")
                .param("lastLoginPlatform","1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 三方绑定
     * @throws Exception
     */
    @Test
    @Transactional
    public void thirdBind() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/thirdBind")
                .param("thirdNo","oJ9SjwtB9Yqh_6pvlAaoIP3QvhwE")
                .param("type","1")
                .param("token","6008FY1525397451364")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 实名认证
     * @throws Exception
     */
    @Test
    @Transactional
    public void certification() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/certification")
                .param("token","6008FY1525397451364")
                .param("cardNo","41282419940411771X")
                .param("realName","左壮壮")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 上传头像
     * @throws Exception
     */
//    @Test
//    @Transactional
//    public void headImg() throws Exception {
//        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
//        mvc.perform(MockMvcRequestBuilders.post("/user/headImg")
//                .param("token","6008FY1525397451364")
//
//                .contentType(MediaType.IMAGE_PNG)
//                .param("headImg","")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//    }

    /**
     * 完善资料
     * @throws Exception
     */
    @Test
    @Transactional
    public void modify() throws Exception {
        FileInputStream headImg = new FileInputStream("E:\\pic\\1.jpg");
        FileInputStream bgImg = new FileInputStream("E:\\pic\\2.jpg");
        mvc.perform(MockMvcRequestBuilders.multipart("/user/modify")
//                .file(new MockMultipartFile("headImg", "E:\\pic\\1.jpg", ",multipart/form-data", "hello upload".getBytes("UTF-8")))
//                .file(new MockMultipartFile("bgImg", "E:\\pic\\2.jpg", ",multipart/form-data", "hello upload".getBytes("UTF-8")))
                .file(new MockMultipartFile("headImg",headImg))
                .file(new MockMultipartFile("bgImg",bgImg))
                .param("userId","1")
                .param("phone","18103966057")
                .param("email","zuozhuang_zzz@163.com")
                .param("nickName","偷看看哟")
                .param("gender","1")
                .param("signature","个性签名")
                .param("contact","13333333333")
                .param("identity","41282419940411772X")
                .param("name","左壮壮")
                .param("payPwd","123456")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 找回密码
     * @throws Exception
     */
    @Test
    @Transactional
    public void resetPwd() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/resetPwd")
                .param("phone","18103966057")
                .param("newPwd","123456")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 修改密码
     * @throws Exception
     */
    @Test
    @Transactional
    public void updatePwd() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/updatePwd")
                .param("userId","1")
                .param("loginPwd","123456")
                .param("newPwd","654321")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 添加收货地址
     * @throws Exception
     */
    @Test
    @Transactional
    public void addAddress() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/addAddress")
                .param("userId","1")
                .param("receiverName","左壮壮")
                .param("receiverPhone","18103966057")
                .param("province","广东省")
                .param("city","深圳市")
                .param("area","罗湖区")
                .param("address","世界金融中心B座1015")
                .param("postCode","450000")
                .param("type","2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 修改收货地址
     * @throws Exception
     */
    @Test
    @Transactional
    public void updateAddress() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/updateAddress")
                .param("userId","1")
                .param("addressId","1")
                .param("receiverName","左壮壮")
                .param("receiverPhone","18103966057")
                .param("province","广东省")
                .param("city","深圳市")
                .param("area","罗湖区")
                .param("address","世界金融中心B座1015")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    /**
     * 删除收货地址
     * @throws Exception
     */
    @Test
    @Transactional
    public void deleteAddress() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/deleteAddress")
                .param("userId","1")
                .param("addressId","1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    /**
     * 设置默认收货地址
     * @throws Exception
     */
    @Test
    @Transactional
    public void defaultAddress() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/defaultAddress")
                .param("userId","1")
                .param("addressId","1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    /**
     * 修改绑定手机
     * @throws Exception
     */
    @Test
    @Transactional
    public void updatePhone() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/updatePhone")
                .param("userId","1")
                .param("phone","18103966056")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


//
//    /**
//     * 我的粉丝
//     * @throws Exception
//     */
//    @Test
//    @Transactional
//    public void myFans() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.post("/user/myFans")
//                .param("token","6008FY1525397451364")
//                .param("start","1")
//                .param("limit","10")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//    }
//
//    /**
//     * 添加/取消关注
//     * @throws Exception
//     */
//    @Test
//    @Transactional
//    public void fansFollow() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.post("/user/fansFollow")
//                .param("token","6008FY1525397451364")
//                .param("userId","6008")
//                .param("type","0")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//    }
//
//    /**
//     * 我的关注
//     * @throws Exception
//     */
//    @Test
//    @Transactional
//    public void myFollows() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.post("/user/myFollows")
//                .param("token","6008FY1525397451364")
//                .param("start","1")
//                .param("limit","10")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//    }
//
//    /**
//     * 好友列表
//     * @throws Exception
//     */
//    @Test
//    @Transactional
//    public void friendList() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.post("/user/friendList")
//                .param("token","6008FY1525397451364")
//                .param("start","1")
//                .param("limit","10")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//    }
//
//    /**
//     * 签到
//     * @throws Exception
//     */
//    @Test
//    @Transactional
//    public void sign() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.post("/user/sign")
//                .param("token","6008FY1525397451364")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//    }
//


}
