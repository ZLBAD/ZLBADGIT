package com.zl.test;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.zl.bean.Favorite;
import com.zl.bean.Tag;
import com.zl.biz.FavoriteBiz;
import com.zl.biz.TagBiz;
import com.zl.biz.impl.FavoriteBizImpl;
import com.zl.biz.impl.TagBizImpl;
import com.zl.dao.MyBatisHelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	
	public AppTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	public void testApp1() throws IOException {
		TagBiz tb=new TagBizImpl();
		List<Tag> list=tb.findAllTag();
		System.out.println(list);
		
	}
	
	public void testApp2() throws IOException {
		Favorite f=new Favorite();
		f.setFdesc("一个好网站");
		f.setFlabel("yahoo");
		f.setFtags("门户,娱乐,java");
		f.setFurl("www.yahoo.com");
		FavoriteBiz fb=new FavoriteBizImpl();
		
		fb.addFavorite(f);
	}
	
	public void testApp3() throws IOException {
		SqlSession session=MyBatisHelper.getSession();
		try {
			List<Tag> list=session.selectOne("com.zl.mapper.TagMapper.selectTagByName","oracle");
			System.out.println(list);
		} finally{
			session.close();
		}
		
	}
	public void testApp4() throws IOException {
		FavoriteBiz fb=new FavoriteBizImpl();
		List<Favorite> list=fb.findFavoriteAll();
		System.out.println(list);
	}
}
