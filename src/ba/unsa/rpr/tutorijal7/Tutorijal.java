package ba.unsa.rpr.tutorijal7;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal implements Serializable {

    static ArrayList<Grad> ucitajGradove()  {
        Scanner ulaz=null;
        ArrayList<Grad> gradovi = new ArrayList<>();
        try {
            ulaz = new Scanner(new FileReader("mjerenja.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka mjerenja.txt ne postoji ili se ne može otvoriti");
        }
        try {
            while (ulaz.hasNext()) {
                Grad grad = new Grad();
                grad.setBrojStanovnika(0);
                String ime = "";
                String text = ulaz.nextLine();
                String[] podaci = text.split(",");
                grad.setNaziv(podaci[0]);
                int i = 0;
                double[] temperature = new double[1000];
                for (i = 0; i < podaci.length; i++) {
                    if (i <= 1000) temperature[i] = Double.parseDouble(podaci[i+1]);
                    else break;
                }
                grad.setTemperature(temperature);
                grad.setVelicina(i - 1);
                gradovi.add(grad);
            }
        } catch (Exception e) {
            System.out.println("Problem pri čitanju/pisanju podataka.");
            System.out.println("Greška: " + e);
        } finally {
            ulaz.close();
        }
        return gradovi;
            }

    static UN ucitajXml(ArrayList<Grad> gradovi) {
        Document xmldoc = null;
        UN un = new UN();
        ArrayList<Drzava> listaDrzava = new ArrayList<>();
        try {
            DocumentBuilder docReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc = docReader.parse(new File("drzave.xml"));
        } catch (Exception e) {
            System.out.println("drzave.xml nije validan XML dokument");
        }
        try {
            Element korijen = xmldoc.getDocumentElement();
            NodeList drzave = korijen.getChildNodes(); //sve drzave
            for (int i = 0; i < drzave.getLength(); i++) {
                Node dijete = drzave.item(i); //drzava
                if (dijete instanceof Element) {
                    Element drzava = (Element) dijete;
                    Drzava d = new Drzava();
                    d.setBrojStanovnika(Integer.parseInt(drzava.getAttribute("stanovnika")));
                    NodeList djecaDrzave = drzava.getChildNodes(); //tagovi unutar jedne drzave
                    for (int j = 0; j < djecaDrzave.getLength(); j++) {
                        Node dijete2 = djecaDrzave.item(j);
                        if (dijete2 instanceof Element) {
                            Element dijeteDrzave = (Element) dijete2;
                            if (dijeteDrzave.getTagName().equals("naziv")) {
                                d.setNaziv(dijeteDrzave.getTextContent());
                            } else if (dijeteDrzave.getTagName().equals("glavnigrad")) {
                                Grad glavniGrad = new Grad();
                                NodeList djecaGrada = dijeteDrzave.getChildNodes();
                                glavniGrad.setBrojStanovnika(Integer.parseInt(dijeteDrzave.getAttribute("stanovnika")));
                                for (int k = 0; k < djecaGrada.getLength(); k++) {
                                    Node dijete3 = djecaGrada.item(k);
                                    if (dijete3 instanceof Element) {
                                        Element dijeteGrada = (Element) dijete3;
                                        if (dijeteGrada.getTagName().equals("naziv")) {
                                            glavniGrad.setNaziv(dijeteGrada.getTextContent().trim());
                                        }
                                    }
                                }
                                for (Grad g : gradovi) {
                                    if (g.getNaziv().equals(glavniGrad.getNaziv())) {
                                        glavniGrad.setVelicina(g.getVelicina());
                                        glavniGrad.setTemperature(g.getTemperature());
                                    }
                                }
                                d.setGlavniGrad(glavniGrad);
                            } else if (dijeteDrzave.getTagName().equals("povrsina")) {
                                d.setPovrsina(Double.parseDouble(dijeteDrzave.getTextContent()));
                                d.setJedinicaPovrsine(dijeteDrzave.getAttribute("jedinica"));
                            }
                        }
                    }
                    listaDrzava.add(d);
                }
            }
            un.setLista(listaDrzava);
        } catch (Exception e) {
            System.out.println("Greška: " + e);
        }
        return un;
    }

    public static void zapisiXml(UN un){
        try {
            XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("drzave.xml"));
            izlaz.writeObject(un);
            izlaz.close();
        } catch(Exception e) {
            System.out.println("Greška: " + e);
        }
    }

    public static void main(String[] args) {
    }

        }