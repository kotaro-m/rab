package Jsoup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HtmlParser {
	public void HtmlParser(){
			try{
			ArrayList<String> URL_Forder = new ArrayList<String>();
	
	
			String filePath = "/home/fujii-lab/git/CrawlSoup/CrawlSoup/data/test.txt";
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String readText = null;
			while ( (readText = br.readLine()) != null ){
				if(readText.startsWith("URL")){
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
	}
}
