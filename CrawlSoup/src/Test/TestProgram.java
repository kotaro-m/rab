package Test;

import Jsoup.HtmlParser;

import Crawler.TestCrawling;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class TestProgram {
	public static void main(String[] args) throws Exception{
		long start = System.currentTimeMillis();
		
		//livedoorクローリング
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

	    //livedoorHTMLパース
		HtmlParser.HtmlParser();
		
		//時間計測
		long end = System.currentTimeMillis();
		System.out.println((end - start)  + "ms\n");
		/*
		//Yahoo!ニュース クローリング
		
		controller.addSeed("http://news.yahoo.co.jp/");
		controller.start(ynCrawling.class, numberOfCrawlers);
		
		HtmlParser.HtmlParser();*/
	}
}


