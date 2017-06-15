package site;
/**
 * 中国绿色网--佛教--网址解析
 * #site : http://www.lvse.com/search/site/佛/p1
 */
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import core.IWebParser;

public class BuddhaWebParser implements IWebParser {

	public void start() {
//		openUrl("");
		
		String url = "http://www.lvse.com/search/site/%E4%BD%9B/p5/";
		int totalPage = getTotalPage(url);
		String fistUrRL = "http://www.lvse.com/search/site/佛/p";
		for (int i = 1; i < totalPage; i++) {
			parseFirstLayer(fistUrRL, i);
			// break;
		}
	}

	@Override
	public int getTotalPage(String url) {
		int total = 0;		
		try {
			Document document = Jsoup.connect(url).get();
			Element elements = document.getElementsByAttributeValue("class", "page-Article").first();
			Elements elements2 = elements.select("a");
			for (Element element : elements2) {
				if (element.text().equals("最后一页")) {
					String pages = element.attr("page");
					return Integer.valueOf(pages);//
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public void parseFirstLayer(String URL, int pageNO) {
		String newUrl = URL + pageNO;
		try {
			Document document = Jsoup.connect(newUrl).get();
			Element elements = document.getElementsByAttributeValue("class", "lvse_border_lt fff_bg").first();
			// System.out.println(elements);
			Elements elements2 = elements.getElementsByAttributeValue("id", "slisting");
			// System.out.println(elements2);
			for (Element element : elements2) {
				// System.out.println(element+"\n\n");
				// 获取h2中的第一条a
				Elements elements4 = element.select("h2");
				Element webName = elements4.select("a[href]").first();
				System.out.println(webName.text());

				Element webUrl = element.select("a[class]").first();
				System.out.println(webUrl.attr("href"));

				Element img = element.select("img[src]").first();
				System.out.println(img.attr("src"));
			}
			System.out.println("-------------------end");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void parseSecondLayer(String URL) {

	}


	
//	/**
//     * 发送get请求
//     * @param url    路径
//     * @return
//     */
//    public static JSONObject httpGet(String url){
//        //get请求返回结果
//        JSONObject jsonResult = null;
//        try {
//            DefaultHttpClient client = new DefaultHttpClient();
//            //发送get请求
//            HttpGet request = new HttpGet(url);
//            HttpResponse response = client.execute(request);
// 
//            /**请求发送成功，并得到响应**/
//            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                /**读取服务器返回过来的json字符串数据**/
//                String strResult = EntityUtils.toString(response.getEntity());
//                /**把json字符串转换成json对象**/
//                jsonResult = JSONObject.fromObject(strResult);
//                url = URLDecoder.decode(url, "UTF-8");
//            } else {
//                logger.error("get请求提交失败:" + url);
//            }
//        } catch (IOException e) {
//            logger.error("get请求提交失败:" + url, e);
//        }
//        return jsonResult;
//    }

}