package corpusBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CorpusFileOperation {
	private static void appender(){
		try{
    		File count = new File("AgroData/dump");
    		int fcount = count.listFiles().length;
    		for(int i=1;i<=fcount;i++){
    			File f = new File("AgroData/dump/dump"+i+"/dump.txt");
    		BufferedReader br=new BufferedReader(new FileReader(f)); 
			StringBuilder sb = new StringBuilder();                    
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
				}
	        sb.append("\n");
    		File file =new File("AgroData/appended_dump.txt");
    		
    		
    		if(!file.exists()){
    			file.createNewFile();
    		}
    		
    		
    		FileWriter fileWritter = new FileWriter(file.getName(),true);
    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	        bufferWritter.write(sb.toString());
    	        bufferWritter.close();
    	    br.close();
	        System.out.println("Done");
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	}
	}
	private static void splitter() throws IOException{
		StringBuilder record = new StringBuilder();
		BufferedReader brFile = new BufferedReader(new FileReader("AgroData/appended_dump.txt"));
		String line = null;
		Pattern pattern = Pattern.compile("ParseText::[\n](.+)");
		while ((line = brFile.readLine()) != null) {
		    record.append(line).append("\n");
		}
	    Matcher matcher = pattern.matcher(record.toString());
	    int count=1;
	    while(matcher.find()){
	    	PrintWriter out = new PrintWriter("AgroData/corpus/doc_"+(count++)+".txt");
	    	out.write(matcher.group(1));
	    	out.close();
	    }
	    System.out.println("Write Over!");
	    brFile.close();	
	}
	public static void main(String args[]) throws IOException{
		appender();
		splitter();
	}
}
