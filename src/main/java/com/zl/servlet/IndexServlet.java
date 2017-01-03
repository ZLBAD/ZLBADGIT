package com.zl.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zl.bean.Favorite;
import com.zl.bean.Tag;
import com.zl.biz.FavoriteBiz;
import com.zl.biz.TagBiz;
import com.zl.biz.impl.FavoriteBizImpl;
import com.zl.biz.impl.TagBizImpl;
import com.zl.model.JsonModel;

public class IndexServlet extends BasicServlet{

	private static final long serialVersionUID = 1L;
	private TagBiz tb=new TagBizImpl();
	private FavoriteBiz fb=new FavoriteBizImpl();
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		if("SelectIndex".equals(op)){
				SelectIndexOp(request,response);
		}
	}
	private void SelectIndexOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JsonModel jm=new JsonModel();
		try{
			List<Tag> list1=tb.findAllTag();
			List<Favorite> list2=fb.findFavoriteAll();
			
			List<Object> list=new ArrayList<Object>();
			list.add(list1);
			list.add(list2);
			jm.setCode(1);
			jm.setObj(list);
		}catch(Exception e){
			e.printStackTrace();
			jm.setCode(0);
			jm.setErrorMsg("查询Fav出错");
		}
	}
}
