package search;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import cn.xm.yss.HttpUtils;
import cn.xm.yss.WebUtils;
import core.IWebSearch;
import core.SearchResult;

/**
 * Xclient【http://xclient.info】
 * 
 * @author guan
 * @btw
 */
public class XclientSearch implements IWebSearch {
	private String site = "http://xclient.info/?_=cc9be0cb8eeba839d501c5ef633dc1d5";
	private String searchUrl = "http://xclient.info/search/s/<%k>/";
	private String pageUrl = "http://xclient.info/search/s/be/<%p>";
	private ArrayList<SearchResult> arrayList = new ArrayList<>();

	@Override
	public ArrayList<SearchResult> search(String url, String keyword) {
		String encode = WebUtils.getInstance().encode(keyword, "utf-8");
//		%E4%B8%AD%E5%9B%BD
		// System.err.println(encode);
		Document document = getDocument(getFirstPage(url, encode));
		parse(document);
		int total = getTotalPage(document);
		 System.err.println("总页数：" + total);
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
			Element element2 = element.select("a").first();
			String link = element2.attr("href");
			String title = element2.attr("title");
			String source = "[Xclient]";
			System.err.println(source + " /" + title + " /" + link);

			SearchResult result = new SearchResult();
			result.setSource(source);
			result.setTitle(title);
			result.setUrl(link);
			arrayList.add(result);
		}
	}

	// 特殊处理这里的获取总页数
	public int getTotalPage(Document document) {
		int total = 1;
		Element element = document.getElementsByAttributeValue("class", "page-navigator").first();
		if (element==null) {
			return total;
		}
		Elements elements = element.select("li");
		for (Element element2 : elements) {
			String numText = element2.text();
			if (StringUtil.isNumeric(numText)) {
				total = Math.max(total, Integer.valueOf(numText));
			}
		}
		return total;
	}
}
