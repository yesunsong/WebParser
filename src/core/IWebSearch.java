package core;

import java.util.ArrayList;

public interface IWebSearch {
	/***/
	public abstract ArrayList<SearchResult> search(String url,String keyword);
}
