package com.zl.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zl.bean.Favorite;
import com.zl.biz.FavoriteBiz;
import com.zl.biz.impl.FavoriteBizImpl;
import com.zl.model.JsonModel;

public class FavoriteServlet extends BasicServlet{

	private static final long serialVersionUID = 1L;
	private FavoriteBiz fb=new FavoriteBizImpl();
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		if("SelectFavAll".equals(op)){
			SelectFavAll(request,response);
		}else if("SelectOne".equals(op)){
			SelectOne(request,response);
		}else if("addFav".equals(op)){
			addFav(request,response);
		}
	}

	private void addFav(HttpServletRequest request, HttpServletResponse response) throws IOException {
		/*
		String label=request.getParameter("label");
		String url=request.getParameter("url");
		String tags=request.getParameter("tags");
		String desc=request.getParameter("desc");
		Favorite fv=new Favorite();
		fv.setFlabel(label);
		fv.setFurl(url);
		fv.setFtags(tags);
		fv.setFdesc(desc);
		*/
		Favorite fav=(Favorite) super.parseRequest(request, Favorite.class);
		boolean flag=fb.addFavorite(fav);
		JsonModel jm=new JsonModel();
		if(flag==false){
			jm.setCode(0);
		}else{
			jm.setCode(1);
		}
		super.outJson(jm, response);
	}

	private void SelectOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JsonModel jm=new JsonModel();
		try {
			String tagname=request.getParameter("tagname");
			List<Favorite> list=fb.findFavoriteByTagName(tagname);
			jm = new JsonModel();
			jm.setCode(1);
			jm.setObj(list);
		} catch (IOException e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setObj("查询Fav出错");
		}
		super.outJson(jm, response);
	}

	private void SelectFavAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Favorite> list=fb.findFavoriteAll();
		JsonModel jm=new JsonModel();
		try{
			jm.setCode(1);
			jm.setObj(list);
		}catch(Exception e){
			e.printStackTrace();
			jm.setCode(0);
			jm.setErrorMsg("查询Fav出错");
		}
		super.outJson(jm, response);
	}
}
