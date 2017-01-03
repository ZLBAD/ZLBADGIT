package com.zl.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zl.bean.Tag;
import com.zl.biz.TagBiz;
import com.zl.biz.impl.TagBizImpl;
import com.zl.model.JsonModel;

public class TagServlet extends BasicServlet{

	private static final long serialVersionUID = 1L;
	
	private TagBiz tb=new TagBizImpl();
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response){
		if("selectTagAll".equals(op)){
			try {
				SelectTagAllop(request,response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void SelectTagAllop(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JsonModel jm=new  JsonModel();
		List<Tag> list=tb.findAllTag();
		try{
			jm.setCode(1);
			jm.setObj(list);
		}catch(Exception e){
			e.printStackTrace();
			jm.setCode(0);
			jm.setErrorMsg("查询tag出错");
		}
		super.outJson(jm, response);
	}
}
