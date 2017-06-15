package core;

/**
 * 搜索结果类
 * @author guan
 *
 */
public class SearchResult {
	/**结果来源*/
	private String source;	

	/**链接地址*/
	private String url;
	
	/**标题*/
	private String title;
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
