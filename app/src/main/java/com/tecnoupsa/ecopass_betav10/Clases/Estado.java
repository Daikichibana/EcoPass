package com.tecnoupsa.ecopass_betav10.Clases;

import java.io.Serializable;


public class Estado implements Serializable{
    String id;
    String puntos;
    Boolean estado;

    public Estado() {
        this.id = "";
        this.puntos ="";
        this.estado = false;
    }

    public Estado(String id, String puntos, Boolean estado) {
        this.id = id;
        this.puntos = puntos;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

}
