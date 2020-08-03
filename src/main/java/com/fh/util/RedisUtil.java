package com.fh.util;


import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisUtil {
    /**
     * 设置
     * @param key
     * @param value
     */
    public static void set(String key,String value){
        Jedis jedisPool=null;
        try {
             jedisPool = RedisPool.getResource();
            jedisPool.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisPool!=null){
               jedisPool.close();
            }
        }
    }

    /**
     * 获取
     * @param key
     * @return
     */
    public static  String get(String key){
        Jedis pool=null;
        String s=null;
        try {
            pool = RedisPool.getResource();
             s = pool.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pool!=null){
                pool.close();
            }
        }
        return s;
    }

    /**
     * 删除
     * @param key
     */
    public static Long del(String key){
        Jedis pool =null;
        Long del = null;
        try {
            pool = RedisPool.getResource();
            del = pool.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pool!=null){
                pool.close();
            }
        }
        return del;
    }
    /**
     * 删除
     * @param key
     */
    public static void dell(String key){
        Jedis pool =null;
        try {
            pool = RedisPool.getResource();
            pool.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pool!=null){
                pool.close();
            }
        }
    }
    /**
     * 删除
     * @param key
     */
    public static void hdel(String key,String field){
        Jedis pool =null;
        try {
            pool = RedisPool.getResource();
            pool.hdel(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pool!=null){
                pool.close();
            }
        }
    }


    /**
     * 给string类型
     * 每个key 设置过期时间
     * @param key
     * @param value
     * @param time
     */
    public static void  setEx(String key,String value,Integer time){
        Jedis pool =null;
        try {
             pool = RedisPool.getResource();
             pool.setex(key,time,value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (pool!=null){
                pool.close();
            }
        }
    }

    //设置 hash
    public static void hset(String key,String value,String field){
        Jedis jedis =null;
        try {
            jedis = RedisPool.getResource();
            jedis.hset(key,value,field);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    };
    //获取 hash
    public static String hget(String key,String field){
        Jedis jedis =null;
        String s =null;
        try {
            jedis = RedisPool.getResource();
            s = jedis.hget(key,field);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return s;
    };

    public static List<String> hget(String key){
        Jedis jedis =null;
        List<String> hvals=null;
        try {
            jedis = RedisPool.getResource();
            hvals = jedis.hvals(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return hvals;
    }

    //判断key是否存在
    public static Boolean exists(String key,String id){
        Jedis jedis =null;
        Boolean s =null;
        try {
            jedis = RedisPool.getResource();
             s = jedis.hexists(key, id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return s;
    };
    //是否存在
    public static Boolean exists1(String key){
        Jedis jedis =null;
        try {
            jedis = RedisPool.getResource();
            jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return true;
    }

}
