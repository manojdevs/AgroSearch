package corpusBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JaccardSim {
	public static double calcSim(String w,String c){
		String[] wo = w.split("\\s+");
		String[] co = c.split("\\s+");
		List<String> wl = new ArrayList<String>(Arrays.asList(wo));
		List<String> cl = new ArrayList<String>(Arrays.asList(co));
		Set<String> s1 = new HashSet<String>();
		Set<String> s2 = new HashSet<String>();
		s2.addAll(cl);
		s2.addAll(wl);
		s1.addAll(wl);
		s1.retainAll(cl);
		double inter = s1.size();
		double uni = s2.size();
		double sim = inter/uni;
		return sim;
		
	}
	public static void main(String args[]){
		System.out.print(calcSim("any animal plant material used  fertilize land especially animal excreta usually litter material manure"," fertilize any substance such manure mixture nitrates used make soil more fertile"));
	}
}
