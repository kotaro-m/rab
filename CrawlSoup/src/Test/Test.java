package Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Crawler.TestCrawling;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Test {
	public static void main(String[] args) throws Exception{
		//クローリング
        String crawlStorageFolder = "/data/crawl/root";
        int numberOfCrawlers = 1;
        int maxDepthOfCrawling=2;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxDepthOfCrawling(maxDepthOfCrawling);

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


	        String filePath = "/data/test.txt";
	        BufferedReader br = new BufferedReader(new FileReader(filePath));
	        String readText = null;
	        //１行の文字列を読み込む
	        while ( (readText = br.readLine()) != null ){
	        	if(readText.startsWith(doc)){
	        		readText = readText.replace("URL: ","");
	        		URL_Forder.add(readText);
	        	}

	        }
	        //文字ストリームを閉じる
	        br.close();


	        for(int i=0;i<URL_Forder.size();i++){
	        	String url = URL_Forder.get(i);
		        Document document = Jsoup.connect(url).get();
				Element content = document.select("article").first();
				if(content!=null){
					String title = content.select("h1").first().text();
					String time = content.select("time").first().text();
					String article = content.select("p").text().replace("　", "\n");
					System.out.println("title:" + title);
					System.out.println("time:" + time);
					System.out.println("article:" + article);
					System.out.println("---------------------------\n");
				}
	        }
        }

    	catch(FileNotFoundException e){
        	  System.out.println(e);
        }
    	catch(IOException e){
        	  System.out.println(e);
        }


	}

}
