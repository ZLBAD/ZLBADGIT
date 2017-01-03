package com.zl.biz;

import java.io.IOException;
import java.util.List;

import com.zl.bean.Favorite;

public interface FavoriteBiz {
	public boolean addFavorite(Favorite favorite) throws IOException;
	
	public List<Favorite> findFavoriteByTagName(String tagName) throws IOException;
	
	public List<Favorite> findFavoriteAll() throws IOException;
}
