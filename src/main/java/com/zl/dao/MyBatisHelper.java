package com.zl.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisHelper {
	private static SqlSessionFactory sqlSessionFactory;
	
	static{   //静态块只执行一次
		try {
			String resource = "mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);//流的操作
			//sqlSessionFactory针对整个应用
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//每调用一次gtesession()获取独立的session
	public static SqlSession getSession() throws IOException{
		SqlSession session = sqlSessionFactory.openSession();
		return session;
	}
}
