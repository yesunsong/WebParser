package search;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.xm.yss.WebUtils;
import core.IWebSearch;
import core.SearchResult;

/**
 * Pshezi【 http://www.pshezi.com 】
 * 
 * @author yesunsong
 * @btw 
 */
public class PsheziSearch implements IWebSearch {
	private String site = "http://www.pshezi.com";
	private String searchUrl = "http://www.pshezi.com/search.php?mod=forum&searchid=46&orderby=lastpost&ascdesc=desc&searchsubmit=yes&kw=<%k>";
	private String pageUrl = "http://www.pshezi.com/search.php?mod=forum&searchid=47&orderby=lastpost&ascdesc=desc&searchsubmit=yes&page=<%p>";
	private ArrayList<SearchResult> arrayList = new ArrayList<>();
	
	@Override
	public ArrayList<SearchResult> search(String url, String keyword) {
		String encode = WebUtils.getInstance().encode(keyword, "GBK");
		// System.err.println(encode);
		Document document = getDocument(getFirstPage(url, encode));
		parse(document);
		int total = getTotalPage(document);
		// System.err.println("总页数：" + total);
		for (int i = 2; i <= total; i++) {
			document = getDocument(i, keyword);
			parse(document);
		}
		return arrayList;
	}
	
	public String getFirstPage(String url, String keyword) {
		if (url.equals("")) {
			url = searchUrl;
		}
		url = url.replace("<%k>", keyword);
		System.err.println(url);
		return url;
	}

	public Document getDocument(String url) {
		Document document = null;
		try {
			document = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}

	public Document getDocument(int page, String keyword) {
		String newurl = pageUrl.replace("<%k>", keyword).replace("<%p>", String.valueOf(page));
		return getDocument(newurl);
	}

	public void parse(Document document) {
		Elements elements = document.getElementsByAttributeValue("class", "list-item-title");
		for (Element element : elements) {
			String link = "";
			String title = "";
			String source = "";
			System.err.println(source + " /" + title + " /" + link);

			SearchResult result = new SearchResult();
			result.setSource(source);
			result.setTitle(title);
			result.setUrl(link);
			arrayList.add(result);
		}
	}

	// 获取总页数
	public int getTotalPage(Document document) {
		int total = 1;
		Elements element = document.getElementsByAttributeValue("class","page-navigator");
		for (Element element2 : element) {
		}
		return total;
	}
}
