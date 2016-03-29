package taggerPOS;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Tagger {
	public static List<String> getTag(String query){
		List<String> nouns = new ArrayList<String>();
		MaxentTagger tagger = new MaxentTagger("/home/manoj/Public/tagger/english-left3words-distsim.tagger");
    	String[] tokens = query.split("\\s+");
    	List<HasWord> sent = Sentence.toWordList(tokens);
    	List<TaggedWord> taggedSent = tagger.tagSentence(sent);
    	//System.out.println(taggedSent);
    	for (TaggedWord tw : taggedSent) {
    	      if (tw.tag().startsWith("NN")) {
    	        //System.out.println(tw.word());
    	        nouns.add(tw.word());
    	      }
    	    } 
    	return nouns;
	}
	public static void main(String args[]){
		List<String> n = getTag("This sentence contains two noun phrases");
		for(String a:n){
			System.out.println(a);
		}
	}
}
