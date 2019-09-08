/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proof;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author jbojato
 */
public class BuscadorHTML {

    ArrayList<String> hijos = new ArrayList();
    static ArrayList<String> nodos = new ArrayList();

    public BuscadorHTML(String dato) {
        this.hijos.add(dato);
    }

    public static void hola() throws ParserConfigurationException, TransformerConfigurationException, TransformerException, MalformedURLException, IOException {

        java.net.URL url = new java.net.URL("https://www.trivantis.com/help/Saba/18/SPA/Lectora_Help/Content/2897.html");
        BufferedReader arch = new BufferedReader(new InputStreamReader(url.openStream()));

        BufferedWriter escribir = new BufferedWriter(new FileWriter("archivo.xml"));
        String linea;
        while ((linea = arch.readLine()) != null) {
  

    
String[] parts =linea.split(">");

String part1 = parts[0]; // 004-
String res ="";
for (int i = 0; i < parts.length; i++) {
            if(!formatear(parts[i]).equals("<![CDATA[>")){
                  res = res+ formatear(parts[i]);
                
           }
        }
           System.out.println(res);
            escribir.write(linea.equals("") ? "" : res);
           
            escribir.newLine();

        }
        arch.close();
        escribir.close();
    }

    public void imprimir() throws TransformerException, ParserConfigurationException {
        for (int i = 0; i < hijos.size(); i++) {
            nodos.add(hijos.get(i));

        }

        String personXMLStringValue = null;
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        // Create Person root element 
        Element personRootElement = doc.createElement("raiz");
        doc.appendChild(personRootElement);
        // Create First Name Element

        // Create Last Name Element
        for (int i = 0; i < nodos.size(); i++) {

            Element firstNameElement = doc.createElement(nodos.get(i));

            Element y = doc.createElement(nodos.get(i));

            personRootElement.appendChild(firstNameElement);
            personRootElement = firstNameElement;
        }

        // Transform Document to XML String
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult s = new StreamResult(new File("data.xml"));
        // Get the String value of final xml document
        transformer.transform(source, s);
    }

    public static String formatear(String texto){
        if (texto.trim().isEmpty()) return "";
        texto = quitarEspaciosPrincipio(quitarCosasPrincipio(texto)); 
        boolean esTagDeCierre = texto.startsWith("</");
        if (esTagDeCierre){
            texto = "<" + texto.substring(2);
            texto = quitarEspaciosPrincipio(texto);
        }
        String primeraPalabra = obtenerLabel(texto);
        if (esTagDeCierre){
            texto = "</" + primeraPalabra + ">";
        } else if (texto.contains("</")){
            texto = "<" + primeraPalabra + ">" + "</" + primeraPalabra + ">";
        }else if (texto.endsWith("/>")){
            texto = "<" + primeraPalabra + "/>";
        }else{
            texto = "<" + primeraPalabra + ">";
        }
        
        if (texto.equals("<!doctype>") || texto.equals("<!DOCTYPE>")) texto = "";
        if(texto.equals("<!Converted>")) texto ="<html>";
        if (texto.equals("<meta>")) texto = "<meta/>";
        if (texto.equals("<img>")) texto = "<img/>";
        if (texto.equals("<link>")) texto = "<link/>";
        if (texto.equals("<input>")) texto = "<input/>";
        if (texto.equals("<P>")) texto = "<P/>";
        if (texto.equals("<BR>")) texto = "<BR/>";
        if (texto.startsWith("<!--")) texto = "";
         if (texto.equals("<br>")) texto = "<br/>";
           if (texto.equals("<col>")) texto = "<col/>";
        if(texto.contains("<![CDATA[>)")) texto ="";
               if (texto.startsWith("<![CDATA[*>")) texto = "";
   
        return texto.equals("<!-->") || texto.equals("<>") ? "" : texto;
    }
    
    public static String quitarEspaciosPrincipio(String texto){
        if (texto.trim().isEmpty()) return "";
        texto = texto.substring(1);
        int i;
        for (i = 0; texto.substring(i, i+1).equals(" "); i++){}
        texto = "<"+ texto.substring(i);
        return texto;
    }
    
    public static String quitarCosasPrincipio(String texto){
        int i;
        for (i = 0; i < texto.length() && (!texto.substring(i, i+1).equals("<")); i++){}
        texto = texto.substring(i);
        return texto;
    }

    private static String obtenerLabel(String texto) {
        if (texto.trim().isEmpty()) return "";
        texto = texto.substring(1);
        String res = "";
        for (int i = 0; i < texto.length() && !texto.substring(i, i+1).equals(" ")
                && !texto.substring(i, i+1).equals("/")&& !texto.substring(i, i+1).equals(">"); i++) {
            res+=texto.substring(i, i+1);
        }
        return res;
    }
}
