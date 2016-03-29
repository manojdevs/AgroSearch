package jenaInterface;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;

public class TDBLoader {
	private static void loader(){
		String directory = "JenaTDB";
		Dataset dataset = TDBFactory.createDataset(directory);
		Model tdb = dataset.getDefaultModel();
		// read the input file
		String source = "KnowledgeBase/agrovoc.xml";
		FileManager.get().readModel( tdb, source);
		tdb.close();
		dataset.close();
	}
	public static void main(String args[]){
		loader();
	}
}
