package jenaInterface;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.core.ResultBinding;
import org.apache.jena.tdb.TDBFactory;

public class TDBquery {
	public static List<String> queryTdb(String concept){
		List<String> words = new ArrayList<String>();
		String directory = "/home/manoj/Public/JenaTDB" ;
	    Dataset dataset = TDBFactory.createDataset(directory) ;
	    String sparqlQueryString = "prefix skos: <http://www.w3.org/2004/02/skos/core#>"+
				  "SELECT DISTINCT ?x ?rm "+
				  "WHERE "+
				  "{ ?x skos:prefLabel ?y ."+
				  "?x skos:altLabel ?rm ."+	
				  "FILTER (regex(?y, \""+concept+"\", \"i\") && (lang(?rm)=\"en\"))}";

	    Query query = QueryFactory.create(sparqlQueryString) ;
	    QueryExecution qexec = QueryExecutionFactory.create(query, dataset) ;
	    ResultSet rs = qexec.execSelect() ;
	    while( rs.hasNext() ) {
	        //System.out.println(ResultSetFormatter.asText(rs));
	    	ResultBinding b = (ResultBinding) rs.next();
	    	String rel = b.get("rm").toString().replace("@en", "");
	    	//System.out.println(rel);
	    	words.add(rel);
	    }
	    return words;
	}
	public static void main(String args[]){
		List<String> c = queryTdb("farming");
		for(String h : c){
			System.out.println(h);
		}
	}
}
