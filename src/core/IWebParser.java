package core;

public interface IWebParser {
	//获取总页数
	public abstract int getTotalPage(String url);
	
	//解析第一层页面
	public abstract void parseFirstLayer(String URL,int pageNO);

	//解析第二层页面
	public abstract void parseSecondLayer(String URL);
}
