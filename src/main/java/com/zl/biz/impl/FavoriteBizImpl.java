package com.zl.biz.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.zl.bean.Favorite;
import com.zl.bean.Tag;
import com.zl.biz.FavoriteBiz;
import com.zl.dao.MyBatisHelper;

public class FavoriteBizImpl implements FavoriteBiz{

	//1.取出f_tags
	//2.截取,    ->String[]
	//3.循环这个String[]中所有的标签，查看这个标签是否存在
	//                         存在，更新t_count ,数量+1
	//						  不存在，则添加一个tag，数量为1到tag表中
	// 再添加favorite
	@Override
	public boolean addFavorite(Favorite favorite) throws IOException {
		boolean flag=false;
		SqlSession s=MyBatisHelper.getSession();
		try {
			String tags=favorite.getFtags();
			if(null==tags || "".equals(tags)){
				favorite.setFtags("未分类");
			}else{
				String regex=",|，|\\s+";      //正则表达式，英文和中文的','
				String[] tag=tags.split(regex);
				List<Tag> lt=s.selectList("com.zl.mapper.TagMapper.selectAll");
				Map<String,String> map=new HashMap<String,String>();
				for(int i=0,len=lt.size();i<len;i++){
					map.put(lt.get(i).getTname(), null);
				}
				//循环当前的网址中所有的tag标签，查看这些标签在map是否存在
				for(int i=0,len=tag.length;i<len;i++){
					boolean exist=map.containsKey(tag[i]);
					if(exist){   //存在则更新数量
						s.update("com.zl.mapper.TagMapper.updateTag",tag[i]);
					}else{  //不存在则插入值
						s.insert("com.zl.mapper.TagMapper.addTag",tag[i]);
					}
				}
			}
			s.insert("com.zl.mapper.FavoriteMapper.addFavorite",favorite);
			flag=true;
			s.commit();    //为了二级缓存
		} catch (Exception e) {
			s.rollback();
			e.printStackTrace();
			throw e;
		}
		return flag;
	}

	@Override
	public List<Favorite> findFavoriteByTagName(String tagName) throws IOException {
		SqlSession s=MyBatisHelper.getSession();
		if(null!=tagName|| !"".equals(tagName)){
			tagName="%"+tagName+"%";
		}
		List<Favorite> list=s.selectList("com.zl.mapper.FavoriteMapper.selectFavoriteOne",tagName);
		s.commit();  //二级缓存
		return list;
	}

	@Override
	public List<Favorite> findFavoriteAll() throws IOException {
		SqlSession s=MyBatisHelper.getSession();
		List<Favorite> list=s.selectList("com.zl.mapper.FavoriteMapper.searchfavorite");
		s.commit();
		return list;
	}

}
