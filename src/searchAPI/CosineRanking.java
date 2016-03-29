package searchAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CosineRanking {
	public static double consineTextSimilarity(String query, String doc) throws IOException {
		String[] qterms = query.split("\\s+");
		BufferedReader br = new BufferedReader(new FileReader(doc));
		StringBuilder sb = new StringBuilder();
		String l = br.readLine();
		while(l!=null){
			sb.append(l);
			sb.append("\n");
			l=br.readLine();
		}
		String docnt = sb.toString();
		String[] dterms = docnt.split("\\s+");
		br.close();
	    Map<String, Integer> qWordCountMap = new HashMap<String, Integer>();
	    Map<String, Integer> docWordCountMap = new HashMap<String, Integer>();
	    Set<String> uniqueSet = new HashSet<String>();
	    Integer temp = null;
	    for (String qWord : qterms) {
	        temp = qWordCountMap.get(qWord);
	        if (temp == null) {
	            qWordCountMap.put(qWord, 1);
	            uniqueSet.add(qWord);
	        } else {
	            qWordCountMap.put(qWord, temp + 1);
	        }
	    }
	    for (String docWord : dterms) {
	        temp = docWordCountMap.get(docWord);
	        if (temp == null) {
	            docWordCountMap.put(docWord, 1);
	            uniqueSet.add(docWord);
	        } else {
	            docWordCountMap.put(docWord, temp + 1);
	        }
	    }
	    int[] qVector = new int[uniqueSet.size()];
	    int[] docVector = new int[uniqueSet.size()];
	    int index = 0;
	    Integer tempCount = 0;
	    for (String uniqueWord : uniqueSet) {
	        tempCount = qWordCountMap.get(uniqueWord);
	        qVector[index] = tempCount == null ? 0 : tempCount;
	        tempCount = docWordCountMap.get(uniqueWord);
	        docVector[index] = tempCount == null ? 0 : tempCount;
	        index++;
	    }
	    return consineVectorSimilarity(qVector, docVector);
	    
	}

	/**
	 * The resulting similarity ranges from −1 meaning exactly opposite, to 1
	 * meaning exactly the same, with 0 usually indicating independence, and
	 * in-between values indicating intermediate similarity or dissimilarity.
	 * 
	 * For text matching, the attribute vectors A and B are usually the term
	 * frequency vectors of the documents. The cosine similarity can be seen as
	 * a method of normalizing document length during comparison.
	 * 
	 * In the case of information retrieval, the cosine similarity of two
	 * documents will range from 0 to 1, since the term frequencies (tf-idf
	 * weights) cannot be negative. The angle between two term frequency vectors
	 * cannot be greater than 90°.
	 * 
	 * @param leftVector
	 * @param rightVector
	 * @return
	 */
	private static double consineVectorSimilarity(int[] qVector,
	        int[] docVector) {
	    if (qVector.length != docVector.length)
	        return 1;
	    double dotProduct = 0;
	    double leftNorm = 0;
	    double rightNorm = 0;
	    for (int i = 0; i < qVector.length; i++) {
	        dotProduct += qVector[i] * docVector[i];
	        leftNorm += qVector[i] * qVector[i];
	        rightNorm += docVector[i] * docVector[i];
	    }

	    double result = dotProduct
	            / (Math.sqrt(leftNorm) * Math.sqrt(rightNorm));
	    return result;
	}

	public static void main(String[] args) throws IOException {
	    String query = "weed control";
	    String doc = "/home/manoj/stemmedcorpus/doc_100803.txt";
	    System.out.println(consineTextSimilarity(query,doc));
	}
}
