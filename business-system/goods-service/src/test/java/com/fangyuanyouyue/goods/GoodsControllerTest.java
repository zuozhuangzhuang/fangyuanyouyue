package com.fangyuanyouyue.goods;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
        mvc.perform(MockMvcRequestBuilders.get("/goods/goodsList")
                .param("classify","0")
                .param("start","0")
                .param("limit","10")
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
        mvc.perform(MockMvcRequestBuilders.post("/goods/addGoods")
                .param("token","0")
                .param("catalogId","0")
                .param("title","10")
                .param("price","10")
                .param("file","10")
                .param("imgWidth","10")
                .param("imgHeight","10")
                .param("description","10")
                .param("isSpecial","10")
                .param("postage","10")
                .param("type","10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void deleteGoods() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/goods/deleteGoods")
                .param("token","0")
                .param("catalogId","0")
                .param("title","10")
                .param("price","10")
                .param("file","10")
                .param("imgWidth","10")
                .param("imgHeight","10")
                .param("description","10")
                .param("isSpecial","10")
                .param("postage","10")
                .param("type","10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
    /**
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void similarGoods() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/goods/similarGoods")
                .param("token","0")
                .param("catalogId","0")
                .param("title","10")
                .param("price","10")
                .param("file","10")
                .param("imgWidth","10")
                .param("imgHeight","10")
                .param("description","10")
                .param("isSpecial","10")
                .param("postage","10")
                .param("type","10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
