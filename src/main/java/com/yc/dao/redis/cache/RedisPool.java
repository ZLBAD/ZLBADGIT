package com.yc.dao.redis.cache;

import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
	private static JedisPool pool;
	private static Logger logger=LogManager.getLogger(RedisCache.class);
	public synchronized static JedisPool getPool(){
		if(pool==null){
			new RedisPool();
		}
		return pool;
	}
	private RedisPool(){
		logger.info("redis pool begin to start..");
		ResourceBundle bundle=ResourceBundle.getBundle("redis");
		if(bundle==null){
			throw new IllegalArgumentException("[redis.properties] is not found!");
		}
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxTotal(Integer.valueOf(bundle.getString("redis.pool.maxActive")));
		config.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));
		config.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait")));
		config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
		config.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));
		pool=new JedisPool(config,bundle.getString("redis.ip"),Integer.valueOf(bundle.getString("redis.port")));
		logger.info("redis pool is running...");
	}
}
