import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import search.XDownsSearch;

public class Main {

	public static void main(String[] args) {
		// BuddhaWebParser parser = new BuddhaWebParser();
		// parser.start();

		// XclientWebParser parser = new XclientWebParser();
		// parser.start();

//		XDownsSearch xDownsSearch = new XDownsSearch();
//		xDownsSearch.search("", "奈末Office批量转PDF");

		String url = "https://www.macappstore.net/e/search/index.php";
		// json
		// JsonObject
		// HttpUtils.getInstance().httpPost(url, jsonParam);

		// http://www.mac123.vip/pr.jsp?keyword=qq&_pp=0_544
		// http://www.macgood.com/search.php?mod=forum&searchid=80&orderby=lastpost&ascdesc=desc&searchsubmit=yes&kw=cleanmymac
		// http://www.7do.net/search.php?mod=forum&searchid=338&orderby=lastpost&ascdesc=desc&searchsubmit=yes&kw=cleanmymac
		// http://zhannei.baidu.com/cse/search?s=5424759519068759688&entry=1&ie=gbk&q=cleanmymac
		// https://www.waitsun.com/?s=cleanmymac
		// http://soft.macx.cn/index.asp?keyword=cleanmymac
		// https://www.macpeers.com/?s=cleanmymac
		// http://www.cncrk.com/search.asp?keyword=cleanmymac
		
		try {
			doPost();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 通过HttpUrlConnection的方式发送Post请求
	@SuppressWarnings("unused")
	public static void doPost() throws IllegalStateException, IOException {
		try {
			// 创建连接
			URL url = new URL("https://www.macappstore.net/e/search/index.php");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/json");// 以json的方式传递参数
			connection.setInstanceFollowRedirects(false);
			// connection.setConnectTimeout(2000);
			// connection.setReadTimeout(3000);
			connection.setRequestProperty("Charsert", "UTF-8");
			connection.connect();

			// POST请求
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.write("{\"keyboard\",\"cleanmymac\"}".getBytes("UTF-8"));// 参数需要json格式(其实就是一个字符串)
			out.write("{\"show\",\"title\"}".getBytes("UTF-8"));
			out.write("{\"tempid\",\"1\"}".getBytes("UTF-8"));
			out.write("{\"tbname\",\"download\"}".getBytes("UTF-8"));
//			keyboard:cleanmymac
//			show:title
//			tempid:1
//			tbname:download
			out.flush();
			out.close();

			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String result = ConvertStream2Json(connection.getInputStream());
			System.out.println(result);
			reader.close();
			// 断开连接
			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将流转换成String
	 * 
	 * @param inputStream
	 * @return
	 */
	private static String ConvertStream2Json(InputStream inputStream) {
		String jsonStr = "";
		// ByteArrayOutputStream相当于内存输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// 将输入流转移到内存输出流中
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			jsonStr = new String(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

}
