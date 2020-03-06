package com.example.itesco.cetisapp;

public class uploadPDF {
    public String name;
    public  String url;
    public String nDescripcion;

   public uploadPDF(){}

    public uploadPDF(String name,String url,String nDescripcion){
       this.name=name;
       this.nDescripcion=nDescripcion;
       this.url=url;

    }

    public String getName() {
        return name;
    }


    public String getnDescripcion() {
        return nDescripcion;
    }

    public String getUrl() {
        return url;
    }


}
