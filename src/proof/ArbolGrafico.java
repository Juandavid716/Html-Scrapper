/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proof;

/**
 *
 * @author CORE I5 6TA
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.xml.transform.TransformerException;

public class ArbolGrafico extends JFrame {

    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    File f;
    Document d;
    JTree tree; 
    JScrollPane scroll; 

    static ArrayList<Integer> hijos = new ArrayList();
    static ArrayList<String> padre = new ArrayList();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new ArbolGrafico();

                } catch (TransformerException ex) {
                    Logger.getLogger(ArbolGrafico.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public ArbolGrafico() throws TransformerException {
        try {
            this.setMinimumSize(new Dimension(600, 600));
            this.setLocationRelativeTo(null);
            JFrame frame = new JFrame();

        JPanel middlePanel = new JPanel ();




    JTextArea display = new JTextArea (0,30);
    display.setEditable ( false ); 
    JScrollPane scroll2 = new JScrollPane ( display );
    scroll2.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
scroll2.setSize(600,600);
   
    middlePanel.add ( scroll2 );


    JFrame frame2 = new JFrame ();
    frame2.add ( middlePanel );
    frame2.pack ();
   
    frame2.setVisible ( true );
    frame2.setMinimumSize(new Dimension(300, 600));
            BuscadorHTML.hola();
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            f = new File("archivo.xml");
            d = builder.parse(f);
            String Arbol = "Arbol";
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(Arbol);
            tree = new JTree(node);
            scroll = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            Element e = d.getDocumentElement();

            if (e.hasChildNodes()) {
                DefaultMutableTreeNode root = new DefaultMutableTreeNode(e.getTagName());
                NodeList children = e.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    Node child = children.item(i);
                    visit(child, node); 
                }
            }

            CantidadRepetidos(display);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        this.add(scroll);
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void visit(Node child, DefaultMutableTreeNode parent) {
        short type = child.getNodeType();
        if (type == Node.ELEMENT_NODE) {
            Element e = (Element) child;

            DefaultMutableTreeNode node = new DefaultMutableTreeNode(e.getTagName());
            parent.add(node);
            AgregarNodos(node);
            if (e.hasChildNodes()) {
                NodeList list = e.getChildNodes();
                for (int i = 0; i < list.getLength(); i++) {
                    visit(list.item(i), node);
                }
            }

        }

    }

    public void AgregarNodos(DefaultMutableTreeNode nodo) {

        padre.add(nodo.toString());

    }
void append(JTextArea area, String newText){
        area.setText(area.getText() + " "+newText+"\n");
}
    public void CantidadRepetidos(JTextArea area) {

        Set<String> Identacion = new HashSet<String>(padre);
        System.out.println("---------------------------------------");
        for (String string : Identacion) {
            if (Collections.frequency(padre, string) == 1) {
                String y =  "La etiqueta " + string + " se repite " + Collections.frequency(padre, string) + " vez";
              
         
  append(area,y);
            } else {
                 String y =  "La etiqueta " + string + " se repite " + Collections.frequency(padre, string) + " veces";
              
         
  append(area,y);
                
            }

        }
        System.out.println("-----------------------------------------");
    }
}
