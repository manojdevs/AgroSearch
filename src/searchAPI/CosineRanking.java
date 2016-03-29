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
