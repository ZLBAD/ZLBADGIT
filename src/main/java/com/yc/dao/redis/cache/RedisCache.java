package com.yc.dao.redis.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

public class RedisCache implements Cache {

	private static Logger logger=LogManager.getLogger(RedisCache.class);
	private String id;
	private Jedis redisClient=createRedis();
	private ReadWriteLock readWriteLock=new ReentrantReadWriteLock();
	
	public static Jedis createRedis() {
		Jedis jedis=RedisPool.getPool().getResource();
		return jedis;
	}
	
	public RedisCache(String id){
		if(id==null){
			throw new IllegalArgumentException("Cache instance requires an Id");
		}
		logger.debug("create an cache instance with id:"+id);
		this.id=id;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void putObject(Object key, Object value) {
		byte[] keybyte=SerializableUtil.serialize(key);
		byte[] valuebyte=SerializableUtil.serialize(value);
		this.redisClient.set(keybyte, valuebyte);
	}

	/**
	 * 通过key缓存到redis中
	 */
	@Override
	public Object getObject(Object key) {
		byte[] values=this.redisClient.get(SerializableUtil.serialize(key));
		if(values==null){
			return null;
		}
		Object obj=SerializableUtil.Unserialize(values);
		return obj;
	}

	@Override
	public Object removeObject(Object key) {
		byte[] keybyte=SerializableUtil.serialize(key);
		return this.redisClient.expire(keybyte, 0);
	}

	@Override
	public void clear() {
		logger.info("redis begin to clear...");
		this.redisClient.flushDB();
	}

	@Override
	public int getSize() {
		Long size=this.redisClient.dbSize();
		int s=Integer.valueOf(size+"");
		return s;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}

}
