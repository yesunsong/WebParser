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
//				System.out.println("//");

				Element webUrl = element.select("a[class]").first();
				System.out.println(webUrl.attr("href"));
//				System.out.println("//");

				Element img = element.select("img[src]").first();
				System.out.println(img.attr("src"));
//				 System.out.println("\n");
//				System.out.println("=============");
				// break;
			}
			System.out.println("-------------------end");
			// System.out.println(elements2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void parseSecondLayer(String URL) {

	}

	public void openUrl(String url) {
		// 判断当前系统是否支持Java AWT Desktop扩展
		if (java.awt.Desktop.isDesktopSupported()) {
			try {
				// 创建一个URI实例,注意不是URL
				java.net.URI uri = java.net.URI.create("http://www.jb51.net");
				// 获取当前系统桌面扩展
				java.awt.Desktop dp = java.awt.Desktop.getDesktop();
				// 判断系统桌面是否支持要执行的功能
				if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
					// 获取系统默认浏览器打开链接
					dp.browse(uri);
				}
			} catch (java.lang.NullPointerException e) {
				// 此为uri为空时抛出异常
			} catch (java.io.IOException e) {
				// 此为无法获取系统默认浏览器
			}
		}
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

// div id="slisting">
// <div class="slist">
// <div class="pic">
// <a target="_blank" href="http://www.lvse.com/site/fjdh-com-6386.html"
// rel="nofollow"><img
// src="http://img.lvse.com/screenshot/www_fjdh_com/15e1cb58f5ea3f229083_w156_h100_t1326101060/佛教导航.png"
// width="156" height="100" alt="佛教导航"></a>
// </div>
// <div class="info">
// <h2><a target="_blank"
// href="http://www.lvse.com/site/fjdh-com-6386.html">佛教导航</a> <a
// target="_blank" class="visit out_link" site_id="2621"
// href="http://www.fjdh.com">(http://www.fjdh.com)
// <!-- img width="14" height="12" src="http://img.lvse.com/images/visit.gif"
// --></a> </h2>
// <p>佛教网址导航是中国佛教综合信息门户，专注于提供佛教新闻信息、大悲咒等佛教音乐歌曲在线收听下载、佛教电影、经典佛教故事文章、佛教网址、法师讲经开示、佛教图书资源的分类检索下载等，上佛教网，从佛教导航开始<span>[<a
// target="_blank" href="http://www.lvse.com/site/fjdh-com-6386.html"
// rel="nofollow">更多</a>]</span></p>
// <p class="l"><a target="_blank"
// href="http://www.lvse.com/site/fjdh-com-6386.html" rel="nofollow">详情</a> | <a
// href="javascript:void(0);"
// onclick="add_fav({&quot;site_id&quot;:&quot;2621&quot;,&quot;site_name&quot;:&quot;\u4f5b\u6559\u5bfc\u822a&quot;,&quot;type&quot;:&quot;site&quot;});">收藏</a>
// (<span class="site_stat"> <span>评分：5</span> <span>人气：1394</span>
// <span>点出：0</span> <span>Alexa：3966378</span> <span>收藏：0</span> <span>评论：<a
// target="_blank" href="http://www.lvse.com/site/fjdh-com-6386.html"
// rel="nofollow" onclick="lvse.cookie('comment','true')">0</a></span>
// </span>)</p>
// </div>
// </div>
// </div>