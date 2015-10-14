package CrawlerSoup;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Crawler.BasicCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;


public class crawlersoup {
	public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "home//data/crawl/root";
        int numberOfCrawlers = 7;
        int maxDepthOfCrawling = 4;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxDepthOfCrawling(maxDepthOfCrawling);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */

        controller.addSeed("http://news.livedoor.com/");

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(BasicCrawler.class, numberOfCrawlers);
        
        String str;
	    String title = null;
	    String time = null;
	    String text = null;
	    
        File readfile = new File("/home/fujii-lab/data/url.txt");
        File writefile = new File("/home/fujii-lab/data/news-text.txt");
        BufferedReader br = new BufferedReader(new FileReader(readfile));
        FileWriter fw = new FileWriter(writefile, true);
        
        while((str=br.readLine()) !=null){
      	  if(str.startsWith("http")){ 
	      	  System.out.println(str);
	      	  String url = str;
	      	  Document doc = null;
	      	  try {
	      		  doc = Jsoup.connect(url).timeout(0).get();
	      	  } catch (IOException e) {
	      		  e.printStackTrace();
	      	  }
			

		       Element content = doc.select("article").first();
		       if(content != null){
					title = content.select("h1").first().text();
					time = content.select("time").first().text();
					text = content.select("p").text().replace("　", "\n");
					fw.write("title:\n" + title + "\n");
					fw.write("time:\n" + time + "\n");
					fw.write("text:" + text + "\n");
					fw.write("-------------------------\n");
		       }
		       else{
		    	   System.out.println("記事がありません。");
		       }
      	  }
        }
        br.close();
        fw.close();        
    }
}
