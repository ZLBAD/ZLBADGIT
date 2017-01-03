package com.zl.biz.impl;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.zl.bean.Tag;
import com.zl.biz.TagBiz;
import com.zl.dao.MyBatisHelper;

public class TagBizImpl implements TagBiz{

	@Override
	public Tag findTagByName(String name) {
		return null;
	}

	@Override
	public List<Tag> findAllTag() throws IOException {
		SqlSession s=MyBatisHelper.getSession();
		List<Tag> list=s.selectList("com.zl.mapper.TagMapper.selectAll");
		s.commit();
		return list;
	}

}
