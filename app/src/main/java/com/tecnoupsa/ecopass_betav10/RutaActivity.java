package com.tecnoupsa.ecopass_betav10;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tecnoupsa.ecopass_betav10.Grafos.Grafo;
import com.tecnoupsa.ecopass_betav10.Grafos.Vertice;

public class RutaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Grafo G1;
    Grafo G2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Cargar los grafos
        G1 = crearVertices(new Grafo());
        G1 = crearArcosRutaA(G1);
        G2 = crearVertices(new Grafo());
        G2 = crearArcosRutaB(G2);

        cargarMarkers();
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 14.0f ) );
    }

    public void cargarMarkers(){
        int i = 0;
        while(i<G1.LVertices.dim()){
            Vertice v = (Vertice) G1.LVertices.getElem(i);
            double lat = v.getLatitud();
            double log = v.getLongitud();
            LatLng parada = new LatLng(lat,log);
            mMap.addMarker(new MarkerOptions().position(parada).title(v.getNombre()));
            i++;
        }
        LatLng Centro = new LatLng(-17.783237, -63.182255); //Enfoca la camara hacia el centro de la ciudad
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Centro));
    }

    public Grafo crearVertices(Grafo g){
        g.crearVertice("A",-17.774456, -63.179932);
        g.crearVertice("B",-17.775604, -63.174968);
        g.crearVertice("C",-17.780845, -63.172669);
        g.crearVertice("D",-17.785371, -63.172036);
        g.crearVertice("E",-17.790801, -63.172240);
        g.crearVertice("F",-17.791587, -63.176678);
        g.crearVertice("G",-17.792529, -63.182657);
        g.crearVertice("H",-17.793086, -63.186906);
        g.crearVertice("I",-17.788674, -63.187739);
        g.crearVertice("J",-17.782174, -63.188790);
        g.crearVertice("K",-17.778394, -63.188269);
        g.crearVertice("L",-17.775703, -63.184577);
        return g;
    }

    public Grafo crearArcosRutaA(Grafo g){
        g.insertarArco("A","B",5);
        g.insertarArco("B","C",0);
        g.insertarArco("C","D",0);
        g.insertarArco("D","E",0);
        g.insertarArco("E","F",0);
        g.insertarArco("F","G",0);
        g.insertarArco("G","H",0);
        g.insertarArco("H","I",0);
        g.insertarArco("I","J",0);
        g.insertarArco("J","K",0);
        g.insertarArco("K","L",0);
        g.insertarArco("L","A",0);

        return g;
    }

    public Grafo crearArcosRutaB(Grafo g){
        g.insertarArco("A","L",5);
        g.insertarArco("L","K",0);
        g.insertarArco("K","J",0);
        g.insertarArco("J","I",0);
        g.insertarArco("I","H",0);
        g.insertarArco("H","G",0);
        g.insertarArco("G","F",0);
        g.insertarArco("F","E",0);
        g.insertarArco("E","D",0);
        g.insertarArco("D","C",0);
        g.insertarArco("C","B",0);
        g.insertarArco("B","A",0);
        return g;
    }

}