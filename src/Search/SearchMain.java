package Search;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import QueryExpansion.ExpandQry;
import searchAPI.SearchAPI;
import searchAPI.CosineRanking;
import searchAPI.JsoupPR;
public class SearchMain {
	public static List<String> search(String query,int ch) throws IOException{
		Map<String,List<String>> m = ExpandQry.dictFrom(query);
		Map<String,HashMap<String,Double>> jsc = ExpandQry.expQuery(m);
		Map<String,List<String>> filist = ExpandQry.rankConc(jsc);
		Map<String,Double> cscore = new HashMap<String,Double>();
		List<String> queries = ExpandQry.resExpQry(filist,query);
		List<String> results = new ArrayList<String>();
		List<String> rankedres = new ArrayList<String>();
		Map<String,Integer> prs = new HashMap<String,Integer>();
		for(String q : queries){
			results.addAll(SearchAPI.search(q,ch));
		}
		if(ch!=5){
		for(String qu : results){
			Integer prscore = new Integer(JsoupPR.getPR(qu)); 
			if(!(prscore.equals("")))
			prs.put(qu, prscore);
		}
		Set<Map.Entry<String, Integer>> set = prs.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        for(Map.Entry<String, Integer> entry:list){
            rankedres.add(entry.getKey());
            System.out.println(entry.getValue());
        }
		return rankedres;
		}
		else
		{	
			String s = "";
			for(String qstr : queries){
				s+=qstr+"\t";
			}
			for(String x : results){
				Double coscore = CosineRanking.consineTextSimilarity(s,x);
				cscore.put(x,coscore);
			}
			Set<Map.Entry<String, Double>> set = cscore.entrySet();
			List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(set);
	        Collections.sort( list, new Comparator<Map.Entry<String, Double>>()
	        {
	            public int compare( Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 )
	            {
	                return (o2.getValue()).compareTo( o1.getValue() );
	            }
	        } );
	        for(Map.Entry<String, Double> entry:list){
	            rankedres.add(entry.getKey());
	            System.out.println(entry.getValue());
	        }
			return rankedres;
		}
	}
	public static void main(String args[]) throws IOException{
		List<String> r = new ArrayList<String>();
		Scanner scan =new Scanner(System.in);
		final String query = scan.nextLine();
		int ch = scan.nextInt();
		r = search(query,ch);
		for(String x : r){
			System.out.println(x);
		}
		scan.close();
	}
}
