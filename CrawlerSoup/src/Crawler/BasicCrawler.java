package Crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
public class BasicCrawler extends WebCrawler {

        private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
                        + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

        /**
         * You should implement this function to specify whether the given url
         * should be crawled or not (based on your crawling logic).
         */
        @Override
        public boolean shouldVisit(WebURL url) {
                String href = url.getURL().toLowerCase();
                return !FILTERS.matcher(href).matches() && href.startsWith("");
        }

        /**
         * This function is called when a page is fetched and ready to be processed
         * by your program.
         */
        @Override
        public void visit(Page page) {
        	
                int docid = page.getWebURL().getDocid();
                String url = page.getWebURL().getURL();
                
                
                try{
                    File file = new File("/home/fujii-lab/data/url.txt");
                    FileWriter filewriter = new FileWriter(file, true);
                    
                    if(url.startsWith("http://news.livedoor.com/article/detail/"))
                    {
	                    filewriter.write(docid + "\n");
	                    filewriter.write(url + "\n");
	                    filewriter.write("=============\n");
	
	                    filewriter.close();
                    }
                      
                      
                      
                  }catch(IOException e){
                    System.out.println(e);
                  }
        }
}

