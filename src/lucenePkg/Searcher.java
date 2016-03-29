package lucenePkg;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;



public class Searcher{
	 

	public static List<String> search(String queryString) {
		String index = "/home/manoj/Public/LuceneIndex";
	    String field = "contents";
	    String queries = null;
	    List<String> resdoc = new ArrayList<String>();
	    boolean raw = false;
		int hitsPerPage = 30;
	    try {
	    IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
	    IndexSearcher searcher = new IndexSearcher(reader);
	    Analyzer analyzer = new StandardAnalyzer();
	    BufferedReader in = null;
	    QueryParser parser = new QueryParser(field, analyzer);
	      Query query = parser.parse(queryString);
	      String ans= query.toString(field);
	      System.out.println("Searching for: " + ans);
	      resdoc = doSearch(in, searcher, query, hitsPerPage, raw, queries == null && queryString == null);
	      reader.close();
	      
	    }catch (Exception e1) {
	    System.out.println(e1);
	}
	    return resdoc;
	}
	

public static void main(String args[]){
	List<String> res = search("agricultur");
	for(String r : res){
		System.out.println(r);
	}
}
	
	public static List<String> doSearch(BufferedReader in, IndexSearcher searcher, Query query, 
            int hitsPerPage, boolean raw, boolean interactive) throws IOException {
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents");
		int start = 0;
		int end = Math.min(numTotalHits, hitsPerPage);
		List<String> docres = new ArrayList<String>();
		for (int i = start; i < end; i++) {
				Document doc = searcher.doc(hits[i].doc);
				String path = doc.get("path");
				if (path != null) {
						docres.add(path);
						//System.out.println((i+1) + ". " + path);
						String title = doc.get("title");
						if (title != null) {
							//System.out.println("   Title: " + doc.get(title));
						}
				} else {
					System.out.println((i+1) + ". " + "No path for this document");
				}

		}
		return docres;
	}		
	}

