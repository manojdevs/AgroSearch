package QueryExpansion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import wordNetThesaurus.WordNet;
import jenaInterface.TDBquery;
import taggerPOS.Tagger;
import corpusBuilder.JaccardSim;

public class ExpandQry{
	public static Map<String,List<String>> dictFrom(String qry){
		List<String> ar1 = new ArrayList<String>();
		Map<String,List<String>> map2 = new HashMap<String,List<String>>();
		List<String> nouns = Tagger.getTag(qry);
		String[] ax = nouns.toArray(new String[nouns.size()]);
		int len = ax.length; 
		//System.out.print(len);
		int a;
		if(len<3){
			a = len;
 		}
		else{
			a=3;
		}
		int b = 0;
		String refqry = new String();
		refqry = "";
		l1:while(a<=len){
			for(int i=b;i<a;i++){
			refqry+=ax[i]+" ";
			
			
			if(i == a-1){
				refqry = refqry.trim();
				
				ar1 = TDBquery.queryTdb(refqry);
				 
				
			
				  if(!(ar1.isEmpty())){
					map2.put(refqry, ar1);
					b=b+a;
					a=a+(len-a);
					refqry="";
					continue l1;   
				}
				else {
					a=a-1;
					refqry="";
					continue l1;
					
				}
	}
			}
			break;
		}
		return map2;
	}
	public static Map<String,HashMap<String,Double>> expQuery(Map<String,List<String>> dict){
		Map<String,HashMap<String,Double>> scoremap = new HashMap<String,HashMap<String,Double>>();
		HashMap<String, Double> pair =new HashMap<String,Double>();
		for (Entry<String, List<String>> entry : dict.entrySet())
		{	String wdef = WordNet.findDefn(entry.getKey());
		    List<String> val = entry.getValue();
		    for(String v : val){
		    	String cdef = WordNet.findDefn(v);
		    	Double score = JaccardSim.calcSim(wdef, cdef);
		    	pair.put(v,score);
		    	//s.add(score);
		    	HashMap<String,Double> temp = new HashMap<String,Double>(pair);
			    scoremap.put(entry.getKey(),temp);   
		    }
		    pair.clear();
		}
    	System.out.print(scoremap);
    	return scoremap;
	}
	public static Map<String,List<String>> rankConc(Map<String,HashMap<String,Double>> jsdict){
		List<String> filtcon = new ArrayList<String>();
		Map<String,List<String>> filrank =new HashMap<String,List<String>>();
		for (Entry<String, HashMap<String,Double>> entry : jsdict.entrySet()){
			String con = entry.getKey();
			HashMap<String,Double> jsclist = entry.getValue();
			for (Entry<String,Double> ent : jsclist.entrySet()){
				//Double sc = ent.getValue();
				//if(sc>0){
					String agcon = ent.getKey();
					filtcon.add(agcon);
				//}
				List<String> temp = new ArrayList<String>(filtcon);
				filrank.put(con, temp);
			}
			filtcon.clear();
			System.out.println(filrank);
		}
		return filrank;
	}
	public static List<String> resExpQry(Map<String,List<String>> simCo,String query){
		String temp=query;
		List<String> queries = new ArrayList<String>();
		queries.add(query);
		List<String> value=new ArrayList<String>();
		for(Entry<String,List<String>> e : simCo.entrySet()){
			String key=e.getKey();
			value=e.getValue();
			if(temp.contains(key))
			{
				for(String g : value)
				{
				String x=	temp.replace(key, g);
					queries.add(x);
				}
				}
			
			}
			
		
		return queries;
	}
	public static void main(String[] args){
		Map<String,List<String>> m = dictFrom("weed control in plants");
		//System.out.println(m);
		/*Map<String,List<String>> m1 = new HashMap<String,List<String>>();
		List<String> mw = new ArrayList<String>();
		List<String> val = new ArrayList<String>();
		for(Entry<String,List<String>> e : m.entrySet()){
			String key = e.getKey().toString();
			val = e.getValue();
		    for(String v : val){
		    	if(!(v.split("\\s+").length>1)){
		    		mw.add(v);
		    	}
		    	
		    }
		    m1.put(key, mw);
		   // mw.removeAll(val);
		}*/
		//System.out.println(m1);
		Map<String,HashMap<String,Double>> jsc = expQuery(m);
		Map<String,List<String>> filist = rankConc(jsc);
		System.out.print(filist);
	}
}
