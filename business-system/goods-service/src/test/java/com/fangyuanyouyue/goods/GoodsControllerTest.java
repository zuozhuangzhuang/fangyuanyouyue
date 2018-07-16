package com.fangyuanyouyue.goods;

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
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoodsServiceApplication.class)
@WebAppConfiguration
@ContextConfiguration
@Rollback
public class GoodsControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    /**
     * 获取商品列表
     * @throws Exception
     */
    @Test
    @Transactional
    public void goodsList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/goods/goodsList")
//                .param("userId","1")
                //商品状态 普通商品 1出售中 2已售出 5删除
//                .param("status","1")
                .param("start","0")
                .param("limit","10")
//                .param("search","")
                //综合 1：综合排序 2：信用排序 3：价格升序 4：价格降序
                .param("synthesize","2")
//                .param("priceMin","10")
//                .param("priceMax","1000")
                //品质 1：认证店铺 2：官方保真 3：高信誉度 4.我的关注
//                .param("quality","1")
                //类型 1普通商品 2抢购商品
                .param("type","1")
                .param("goodsCategoryIds", "23,121,5")

                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 添加商品
     * @throws Exception
     */
    @Test
    @Transactional
    public void addGoods() throws Exception {
        mvc.perform(MockMvcRequestBuilders.multipart("/goods/addGoods")
                .file(new MockMultipartFile("file1", "1.jpg", ",multipart/form-data", "hello upload".getBytes("UTF-8")))
                .param("userId","1")
                .param("goodsInfoName","123")
                .param("goodsCategoryIds","10")
                .param("description","10")
                .param("price","10")
                .param("postage","10")
                .param("sort","1")
                .param("label","1")
                .param("type","10")
                .param("status","10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 批量删除商品
     * @throws Exception
     */
    @Test
    @Transactional
    public void deleteGoods() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/goods/deleteGoods")
                .param("userId","0")
                .param("goodsInfoIds","0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
    /**
     * 商品详情
     * @throws Exception
     */
    @Test
    @Transactional
    public void goodsInfo() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/goods/goodsInfo")
                .param("goodsId","1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

//    /**
//     *
//     * @throws Exception
//     */
//    @Test
//    @Transactional
//    public void similarGoods() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.post("/goods/similarGoods")
//                .param("token","0")
//                .param("catalogId","0")
//                .param("title","10")
//                .param("price","10")
//                .param("file","10")
//                .param("imgWidth","10")
//                .param("imgHeight","10")
//                .param("description","10")
//                .param("isSpecial","10")
//                .param("postage","10")
//                .param("type","10")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//    }

    /**
     * 获取分类列表
     * @throws Exception
     */
    @Test
    @Transactional
    public void categoryList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/goods/categoryList")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 同类推荐
     * @throws Exception
     */
    @Test
    @Transactional
    public void similarGoods() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/goods/similarGoods")
                .param("goodsId","6")
                .param("start","0")
                .param("limit","10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 热门搜索
     * @throws Exception
     */
    @Test
    @Transactional
    public void hotSearch() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/goods/hotSearch")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 我的购物车
     * @throws Exception
     */
    @Test
    @Transactional
    public void getCart() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/goods/getCart")
                .param("token","10022FY1531365304072")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
