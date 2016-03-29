package wordNetThesaurus;

import java.util.ArrayList;
import java.util.List;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class WordNet {
	public static String findDefn(String word){
		String[] defn;
		List<String> syn = new ArrayList<String>();
		System.setProperty("wordnet.database.dir", "/home/manoj/Public/wn3.1.dict/dict");
		WordNetDatabase database = WordNetDatabase.getFileInstance(); 
		Synset[] synsets = database.getSynsets(word,SynsetType.NOUN); 
		for(Synset a:synsets){
			syn.add(a.getDefinition().toString());
		}
		defn = syn.toArray(new String[synsets.length]);
		if(synsets.length == 0){
			return "";
		}
		else
		return defn[0];
		
	}
	public static void main(String args[]){
		String l =findDefn("fertilizer");
		System.out.print(l);
	}
}
