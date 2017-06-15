package site;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Xclient--网址解析
 * #site : http://xclient.info/s/
 */
import core.IWebParser;

public class XclientWebParser implements IWebParser {
	public HashMap<String, String> items = new HashMap<>();

	public void start() {
		getItems("http://xclient.info/s/");

		Iterator<Entry<String, String>> entry = items.entrySet().iterator();
		while (entry.hasNext()) {
			Entry<String, String> entry2 = entry.next();
			// System.out.println(entry2.getKey()+" "+entry2.getValue());
			String url = entry2.getValue();
			int totalPage = getTotalPage(url);
		    parseFirstLayer(url, totalPage);
		}
	}

	@Override
	public int getTotalPage(String url) {
		int total = 0;
		try {
			Document document = Jsoup.connect(url).get();
			Element elements = document.getElementsByAttributeValue("class", "page-navigator").first();
			if (elements == null) {
				total = 1;
				return total;
			}
			Elements elements2 = elements.select("a");
			for (Element element : elements2) {
				// System.out.println(element);
				String numText = element.text();
				if (StringUtil.isNumeric(numText)) {
					total = Math.max(total, Integer.valueOf(numText));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public void parseFirstLayer(String URL, int pageNO) {
		try {
			Document document = null;
			if (pageNO == 1) {
				document = Jsoup.connect(URL).get();
			} else {
				document = Jsoup.connect(URL + pageNO).get();
			}

			for (int i = 0; i < pageNO; i++) {
				System.out.println("第"+i+"页");
				Element element = document.getElementsByAttributeValue("class", "post_list").first();
				Elements elements = element.getElementsByAttributeValue("class", "main");

				for (Element element2 : elements) {
					Element elements3 = element2.select("a[href]").first();
					System.out.println(elements3.attr("href"));

					Element img = elements3.select("img[src]").first();
					System.out.println(img.attr("src"));

					Element name = elements3.select("h3").first();
					System.out.println(name.text());

					Element intro = elements3.select("p").first();
					System.out.println(intro.text());
					System.out.println("============");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void parseSecondLayer(String URL) {
	}

	public void getItems(String url) {
		try {
			Document document = Jsoup.connect(url).get();
			Element element = document.getElementsByAttributeValue("id", "lim-cate-list").first();
			// System.out.println(elements);
			Elements elements = element.select("a[href]");
			for (Element element1 : elements) {
				// System.out.println(element1);
				items.put(element1.text(), element1.attr("href"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
