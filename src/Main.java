import search.XDownsSearch;
import search.XclientSearch;
import site.XclientWebParser;

public class Main {

	public static void main(String[] args) {
//		BuddhaWebParser parser = new BuddhaWebParser();
//		parser.start();
		
//		XclientWebParser parser = new XclientWebParser();
//		parser.start();
		

		long t1 = System.currentTimeMillis();
		
//		XDownsSearch xDownsSearch = new XDownsSearch();
//		xDownsSearch.search("","test");
		
		XclientSearch xclientSearch = new XclientSearch();
		xclientSearch.search("", "beyond");
		
		System.out.println("time:"+(System.currentTimeMillis() - t1));
	}

}
