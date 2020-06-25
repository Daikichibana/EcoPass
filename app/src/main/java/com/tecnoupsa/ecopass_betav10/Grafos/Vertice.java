package com.tecnoupsa.ecopass_betav10.Grafos;

import com.tecnoupsa.ecopass_betav10.Grafos.Lista;

public class Vertice
{
    String nombre;
    Double latitud;
    Double longitud;
    public Lista LArcos;
    boolean marcado;

    public Vertice(String nom, Double lat, Double log)   {
        nombre = nom;
        latitud = lat;
        longitud = log;
        LArcos = new Lista();
        this.marcado=false;
    }

    public void setNombre(String nom) {
        nombre = nom;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setLatitud(Double lat){ latitud = lat;}

    public Double getLatitud(){return latitud;}

    public void setLongitud(Double lg){longitud = lg;}

    public Double getLongitud(){return longitud;}

    public int getCantArcos() {
        return LArcos.dim();
    }

    //Inserta el arco q ya viene creado
    public void insertarArco(Arco arco) {
        LArcos.insertarUlt(arco);
    }

}
