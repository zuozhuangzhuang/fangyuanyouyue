package com.fangyuanyouyue.message.client;//package com.fangyuanyouyue.sms.client;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.redis;
//import org.springframework.stereotype.Component;
//
///**
// * 就一个小Demo 随便写下
// *
// */
//@Component
//public class RedisConfig  {
//
//    @Autowired
//    private JedisPool jedisPool;
//
//    public void set(String key, String value) throws Exception {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            jedis.set(key, value);
//        } finally {
//            //返还到连接池
//            jedis.close();
//        }
//    }
//
//    public String get(String key) throws Exception  {
//
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.get(key);
//        } finally {
//            //返还到连接池
//            jedis.close();
//        }
//    }
//
//}
