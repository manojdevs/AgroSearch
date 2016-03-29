package corpusBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StopWordRemoval {
	public static void main(String args[]) throws IOException{
		File f = new File("/home/manoj/stopwords.txt");
		List<String> stopword = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = br.readLine();
		while(line!=null){
			stopword.add(line);
			line = br.readLine();
		}
		br.close();
		File fc = new File("/home/manoj/alphanumcorpus");
		for (File fsc : fc.listFiles()){
			System.out.println("processing"+fsc.getName());
			BufferedReader br1 = new BufferedReader(new FileReader("/home/manoj/alphanumcorpus/"+fsc.getName()));
			StringBuilder sb =new StringBuilder();
			String l = br1.readLine();
			while(l!=null){
				sb.append(l);
				sb.append("\n");
				l=br1.readLine();
			}
			String content = sb.toString().toLowerCase();
			String[] words = content.split("\\s+");
			List<String> wo = new ArrayList<String>();
			for(String wa : words){
				wo.add(wa);
			}
		for(String word:stopword){
			for(String w : words){
				if(word.matches(w)){
					wo.remove(w);
				}
			}
		}
		String m="";
		for(String q : wo){
			m+=q+" ";
		}
		FileWriter fw = new FileWriter("/home/manoj/nostopword/"+fsc.getName());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(m);
		br1.close();
		bw.close();
		}
		
	}
}
