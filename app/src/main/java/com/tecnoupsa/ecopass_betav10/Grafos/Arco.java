package com.tecnoupsa.ecopass_betav10.Grafos;


public class Arco {
    private int costo;
    private Vertice verticeD;

    public Arco(Vertice vd, int co) {
        this.costo = co;
        this.verticeD = vd;
    }

    public void setCosto(int co) {
        this.costo = co;
    }

    public int getCosto()  {
        return costo;
    }

    //Devuelve el nombre del vertice destino
    public String getNombreVertD() {
        return verticeD.getNombre();
    }

    public void setNombreVertD(Vertice vd) {
        this.verticeD = vd;
    }

    //Holi pozoli
}
