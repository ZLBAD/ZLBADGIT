package com.zl.biz;

import java.io.IOException;
import java.util.List;

import com.zl.bean.Tag;

public interface TagBiz {
	public Tag findTagByName(String name);
	
	public List<Tag> findAllTag() throws IOException;
}
