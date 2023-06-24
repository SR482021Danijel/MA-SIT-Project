package com.ftn.ma_sit_project.Model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    private String image;
    @SerializedName("koZnaZna")
    private int koZnaZna;

    @SerializedName("spojnice")
    private int spojnice;

    @SerializedName("asocijacije")
    private int asocijacije;

    @SerializedName("skocko")
    private int skocko;

    @SerializedName("korakPoKorak")
    private int korakPoKorak;

    @SerializedName("mojBroj")
    private int mojBroj;
    @SerializedName("partije")
    private int partije;

    @SerializedName("pobede")
    private int pobede;

    @SerializedName("porazi")
    private int porazi;

//    public User(String id, String username, String password, String email) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//    }

    public User(String id, String username, String password, String email, int koZnaZna, int spojnice, int asocijacije, int skocko, int korakPoKorak, int mojBroj, int partije, int pobede, int porazi) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.koZnaZna = koZnaZna;
        this.spojnice = spojnice;
        this.asocijacije = asocijacije;
        this.skocko = skocko;
        this.korakPoKorak = korakPoKorak;
        this.mojBroj = mojBroj;
        this.partije = partije;
        this.pobede = pobede;
        this.porazi = porazi;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getKoZnaZna() {
        return koZnaZna;
    }

    public void setKoZnaZna(int koZnaZna) {
        this.koZnaZna = koZnaZna;
    }
    public int getSpojnice() {
        return spojnice;
    }
    public void setSpojnice(int spojnice) {
        this.spojnice = spojnice;
    }
    public int getAsocijacije() {
        return asocijacije;
    }
    public void setAsocijacije(int asocijacije) {
        this.asocijacije = asocijacije;
    }
    public int getSkocko() {
        return skocko;
    }
    public void setSkocko(int skocko) {
        this.skocko = skocko;
    }
    public int getKorakPoKorak() {
        return korakPoKorak;
    }
    public void setKorakPoKorak(int korakPoKorak) {
        this.korakPoKorak = korakPoKorak;
    }
    public int getMojBroj() {
        return mojBroj;
    }
    public void setMojBroj(int mojBroj) {
        this.mojBroj = mojBroj;
    }
    public int getPartije() {
        return partije;
    }

    public void setPartije(int partije) {
        this.partije = partije;
    }

    public int getPobede() {
        return pobede;
    }

    public void setPobede(int pobede) {
        this.pobede = pobede;
    }

    public int getPorazi() {
        return porazi;
    }

    public void setPorazi(int porazi) {
        this.porazi = porazi;
    }
}
