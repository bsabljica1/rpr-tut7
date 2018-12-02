package ba.unsa.rpr.tutorijal7;

public class Drzava {
    private String naziv;
    private int brojStanovnika;
    private double povrsina;
    private String jedinicaPovrsine;
    private Grad glavniGrad;

    Drzava() {}
    public String getNaziv () {return naziv;}
    public int getBrojStanovnika() {return brojStanovnika;}
    public double getPovrsina() {return povrsina;}
    public String getJedinicaPovrsine() {return jedinicaPovrsine;}
    public Grad getGlavniGrad() {return glavniGrad;}

    public void setNaziv(String naziv) {
        this.naziv=naziv;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public void setGlavniGrad(Grad glavniGrad) {
        this.glavniGrad = glavniGrad;
    }

    public void setJedinicaPovrsine(String jedinicaPovrsine) {
        this.jedinicaPovrsine = jedinicaPovrsine;
    }

    public void setPovrsina(double povrsina) {
        this.povrsina = povrsina;
    }
}
