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
//                .param("status","1")
                .param("start","0")
                .param("limit","10")
                .param("search","小宝")
                .param("synthesize","3")
                .param("priceMin","10")
                .param("priceMax","1000")
//                .param("quality","1")
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
                .param("goodsId","6")
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
}
