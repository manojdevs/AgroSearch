package corpusBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.regex.Pattern;

public class JunkWordReplacer {
	public static void main(String args[]) throws IOException{
		File f = new File("/home/manoj/corpus");
		for(File fi:f.listFiles()){
		BufferedReader br = new BufferedReader(new FileReader(f+fi.getName()));
		File nf = new File("/home/manoj/alphanumcorpus/"+fi.getName());
		Writer wr = new FileWriter(nf,true);
		BufferedWriter bw = new BufferedWriter(wr);
		String line = br.readLine();
		Pattern p = Pattern.compile("[^a-zA-Z0-9\\s]");
		while(line!=null){
			line = p.matcher(line).replaceAll("");
			bw.write(line);
			line = br.readLine();
			
		}
		bw.close();
		br.close();
		}
		//String x = "Colección de libros electrónicos de la FAO | FAO | Organización de las Naciones Unidas para la Alimentación y la Agricultura العربية ﻿中文 english français italiano Ру�?�?кий Español FAO.org Acerca de En acción Países Temas Medios Publicaciones Estadísticas Asociaciones Imprimir Enviar Inicio  > Publicaciones  > Colección de libros electrónicos de la FAO El estado de los mercados de productos básicos agrícolas 2015-16 (SOCO) Comercio y seguridad alimentaria: lograr un mayor equilibrio entre las prioridades nacionales y el bien colectivo El objetivo de la presente edición de El estado de los merca";
		 
	        //String hasSpecialChar = p.matcher(x).replaceAll("");
	        //System.out.println(hasSpecialChar);
	}
}
