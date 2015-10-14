package Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Jsoup.HtmlParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Crawler.TestCrawling;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class TestProgram {
	public static void main(String[] args) throws Exception{
		long start = System.currentTimeMillis();

		//クローリング
		String crawlStorageFolder = "data/crawl/root";
		int numberOfCrawlers = 1;
		int maxDepthOfCrawling=2;

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxDepthOfCrawling(maxDepthOfCrawling);
		config.setMaxPagesToFetch(1000);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		controller.addSeed("http://news.livedoor.com/");
		controller.start(TestCrawling.class, numberOfCrawlers);

	    //HTMLパース
		try{
			String doc = "URL";
			ArrayList<String> URL_Forder = new ArrayList<String>();


			String filePath = "/home/fujii-lab/git/CrawlSoup/CrawlSoup/data/test.txt";
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String readText = null;
			while ( (readText = br.readLine()) != null ){
				if(readText.startsWith(doc)){
					readText = readText.replace("URL: ","");
		        	URL_Forder.add(readText);
		        }

			}
			br.close();

			File file = new File("/home/fujii-lab/git/CrawlSoup/CrawlSoup/data/article.txt");
			FileWriter pw = new FileWriter(file,true);
			for(int i=0;i<URL_Forder.size();i++){
		        String url = URL_Forder.get(i);
		        Document document = Jsoup.connect(url).timeout(0).get();
				Element content = document.select("article").first();
				if(content!=null){
					String title = content.select("h1").first().text();
					String time = content.select("time").first().text();
					String article = content.select("[itemprop=articleBody]").text().replace(" ", "\r\n");
					pw.write("title:" + title + "\r\n");
					pw.write("time:" + time + "\r\n");
					pw.write("article:" + article + "\r\n");
					pw.write("---------------------------\n" + "\r\n\n\r");
				}
		    }
			pw.close();
	    }

	    catch(FileNotFoundException e){
	        	  System.out.println(e);
	    }
	    catch(IOException e){
	    	System.out.println(e);
	    }

		long end = System.currentTimeMillis();
		System.out.println((end - start)  + "ms\n");
		
		//Yahoo!ニュース クローリング
		controller.addSeed("http://news.yahoo.co.jp/");
		controller.start(TestCrawling.class, numberOfCrawlers);
		
		
		
	}
}


