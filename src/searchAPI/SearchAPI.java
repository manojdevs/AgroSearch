package searchAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import lucenePkg.Searcher;

public class SearchAPI {
	public static List<String> search(String qsearch,int choice) throws IOException{
		SearchAPI obj = new SearchAPI();
		List<String> result = new ArrayList<String>();
		switch(choice){
		case 1:
			result = obj.getDataFromGoogle(qsearch);
			break;
		case 2:
			result=obj.getDataFromBing(qsearch);
			break;
		case 3:
			result=obj.getDataFromYahoo(qsearch);
			break;
		case 4:
			result = obj.metaSearch(qsearch);
			break;
		case 5:
			result = obj.docSearch(qsearch);
			break;
		default:
			System.out.println("Invalid selection");
			break;
		}
		return result;
		}
	private List<String> getDataFromGoogle(String query) {	
		List<String> result = new ArrayList<String>();	
		String request = "https://www.google.com/search?q=" + query + "&num=20";
		System.out.println("Sending request..." + request);
		try {
			Document doc = Jsoup
				.connect(request)
				.userAgent(
				  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
				.timeout(5000).get();
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String temp = link.attr("href");		
				if(temp.startsWith("/url?q=") && !(temp.startsWith("/url?q=/")) && !(temp.startsWith("/url?q=http://webcache")) && !(link.text().equals(""))){
					//System.out.println(link.text());
					temp.replace("https", "http");
	                temp = temp.replace("/url?q=", "");
	                temp = temp.replace("%3F", "?");
	                temp = temp.replace("%3D", "=");
	                String temp1 = temp.substring(temp.lastIndexOf("&sa=U"), temp.length());
	                temp = temp.replace(temp1, "");
	                if(!(temp.startsWith("http://scholar.google.co.in")))
	                result.add(temp);
					//System.out.println(temp);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return result;
	  }
	private List<String> getDataFromBing(String query) {	
		List<String> result = new ArrayList<String>();	
		String request = "https://www.bing.com/search?q=" + query + "&num=20";
		System.out.println("Sending request..." + request);
		try {
			Document doc = Jsoup
				.connect(request)
				.userAgent(
				  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
				.timeout(5000).get();
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String temp = link.attr("href");		
				if(temp.startsWith("http://") && !(temp.startsWith("http://go."))&& !(temp.contains("r.msn.com")) && !(link.text().equals(""))){
					temp.replace("https", "http");
					result.add(temp);
					//System.out.println(temp);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return result;
	  }
	private List<String> getDataFromYahoo(String query) {	
		List<String> result = new ArrayList<String>();	
		String request = "https://search.yahoo.com/search/?p="+ query+"&num=100";
		System.out.println("Sending request..." + request);
		try {
			Document doc = Jsoup
				.connect(request)
				.userAgent(
				  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
				.timeout(5000).get();
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String temp = link.attr("href");		
				if(temp.startsWith("http://") && !(temp.startsWith("http://r.")) && !(temp.startsWith("http://208")) && !(temp.contains("yahoo"))){
					//System.out.println(link.text());	                
					//System.out.println(temp);
					temp.replace("https", "http");
					result.add(temp);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return result;
	  }
	private List<String> metaSearch(String query){
		List<String> result = new ArrayList<String>();
		result.addAll(getDataFromGoogle(query));
		result.addAll(getDataFromBing(query));
		result.addAll(getDataFromYahoo(query));
		return result;
		
	}
	private List<String> docSearch(String query) throws IOException{
		List<String> result = new ArrayList<String>();
		String stemQuery = QueryStemmer.qStem(query);
		result.addAll(Searcher.search(stemQuery));
		return result;
	}
	public static void main(String args[]) throws IOException{
		List<String> re = search("agriculture",5);
		for(String r : re){
			System.out.println(r);
		}
	}
}