package ba.unsa.rpr.tutorijal7;

public class Grad {
    private String naziv;
    private int brojStanovnika;
    private double[] temperature=new double[1000];
    private int velicina;

    Grad () {}
    public String getNaziv() {return naziv;}
    public int getBrojStanovnika() {return brojStanovnika;}
    public double[] getTemperature() {return temperature;}

    public int getVelicina() {
        return velicina;
    }

    public void setNaziv(String naziv) {
        this.naziv=naziv;
    }
    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika=brojStanovnika;
    }
    public void setTemperature(double[] temperature) {
        this.temperature=temperature;
    }

    public void setVelicina(int velicina) {
        this.velicina = velicina;
    }
}
