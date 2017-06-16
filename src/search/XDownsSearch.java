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
 * 绿盟【http://www.xdowns.com/】
 * 
 * @author guan
 * @btw 关键字要大于2小于20个字节
 */
public class XDownsSearch implements IWebSearch {
	private String site = "http://www.xdowns.com";
	private String searchUrl = "http://www.xdowns.com/so.asp?keyword=<%k>";
	private String searchUrl1 = "http://tag.xdowns.com/tag.asp?keyword=<%k>";
	private String pageUrl = "http://www.xdowns.com/tag.asp?page=<%p>&act=&classid=&keyword=<%k>";
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
		Elements elements = document.getElementsByAttributeValue("id", "searchpageTitle");
		for (Element element : elements) {
			Element element2 = element.select("span").first();
			Element element3 = element2.select("a[href]").first();
			String link = site + element3.attr("href");
			String title = element3.attr("title");
			String source = "[绿盟]";
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
		Elements element = document.getElementsByTag("script");
		for (Element element2 : element) {
			if (element.toString().contains("ShowListPage")) {
				String[] array = element.toString().split(",");
				total = Integer.valueOf(array[1]);
				break;
			}
		}
		return total;
	}
}
