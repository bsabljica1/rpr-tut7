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
    public static void main(String[] args) {
        // write your code here
    }

    static ArrayList<Grad> ucitajGradove() throws FileNotFoundException {
        Scanner ulaz=null;
        ArrayList<Grad> gradovi = new ArrayList<>();
        try {
            ulaz = new Scanner(new FileReader("ulaz.txt"));
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
                for (i = 1; i < podaci.length; i++) {
                    if (i <= 1000) temperature[i - 1] = Double.parseDouble(podaci[i]);
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

    public static UN ucitajXml(ArrayList<Grad> gradovi){
        Document xmldoc = null;
        try {
            DocumentBuilder docReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc = docReader.parse(new File("drzave.xml"));
        }
        catch (Exception e) {
            System.out.println("drzave.xml nije validan XML dokument");
            return null;
        }
        Element korijen = xmldoc.getDocumentElement();
        NodeList djeca = korijen.getChildNodes();
        String brojStanovnika = new String();
        String naziv = new String();
        for(int i = 0; i < djeca.getLength(); i++) {
            Node dijete = djeca.item(i);
            if (dijete instanceof Element) {
                Element element = (Element) dijete;
                element.getAttribute(brojStanovnika);
                NodeList djecaDjece = element.getChildNodes();
                if(djecaDjece.getLength() == 1) continue;
                for(int j = 0; j < djecaDjece.getLength(); j++) {
                    Node dijeteDjeteta = djecaDjece.item(j);
                    if (dijeteDjeteta instanceof Element) {
                        element = (Element) dijeteDjeteta;
                        element.getAttribute(naziv);
                        NodeList djecaDjeceDjece = element.getChildNodes();
                        if(djecaDjeceDjece.getLength() == 1) continue;
                        for(int k = 0; k < djecaDjece.getLength(); k++){
                            Node dijeteDjetetaDjeteta = djecaDjeceDjece.item(i);
                            if (dijeteDjetetaDjeteta instanceof Element) {
                                element = (Element) dijeteDjeteta;
                                element.getAttribute(naziv);
                            }
                        }
                    }
                }
            }
        }
        UN un = new UN();
        return un;
    }

    public static void zapisiXml(UN un){
        try {
            XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("fakultet.xml"));
            izlaz.writeObject(un);
            izlaz.close();
        } catch(Exception e) {
            System.out.println("Greška: " + e);
        }
    }

        }