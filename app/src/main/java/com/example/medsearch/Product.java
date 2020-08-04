package com.example.medsearch;

public class Product {
    private int kid;
    private String kname;
    private String kstreet;
    private String kplace;
    private String kpno;

    public Product(int id, String name, String street, String place, String pno) {
        this.kid = id;
        this.kname = name;
        this.kstreet = street;
        this.kplace = place;
        this.kpno = pno;
    }

    public int getid() {
        return kid;
    }

    public void setid(int id) {
        this.kid = id;
    }

    public String getName() {
        return kname;
    }

    public void setName(String name) {
        this.kname = name;
    }

    public String getStreet() {
        return kstreet;
    }

    public void setStreet(String street) {
        this.kstreet = street;
    }

    public String getPlace() {
        return kplace;
    }

    public void setPlace(String place) {
        this.kplace = place;
    }

    public String getPno() {
        return kpno;
    }

    public void setPno(String pno) {
        this.kpno = pno;
    }
}
