package com.fangyuanyouyue.order;

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
@SpringBootTest(classes = OrderServiceApplication.class)
@WebAppConfiguration
@ContextConfiguration
@Rollback
public class OrderControllerTest {
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
     * 商品下单
     * @throws Exception
     */
    @Test
    @Transactional
    public void saveOrder() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/order/saveOrder")
                .param("token","10025FY1532544458673")
                .param("goodsIds","18,19,20")
                .param("addressId","27")
                .param("type","1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 取消订单
     * @throws Exception
     */
    @Test
    @Transactional
    public void cancelOrder() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/order/cancelOrder")
                .param("token","10025FY1532136940739")
                .param("orderId","6")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 订单详情
     * @throws Exception
     */
    @Test
    @Transactional
    public void orderDetail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/order/orderDetail")
                .param("token","10025FY1532544458673")
                .param("orderId","41")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 我的订单列表
     * @throws Exception
     */
    @Test
    @Transactional
    public void myOrderList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/order/myOrderList")
                .param("token","10025FY1532677840708")
                .param("start","0")
                .param("limit","10")
                .param("type","1")
                .param("status","1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
