package com.tecnoupsa.ecopass_betav10;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
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
        Button verCamino;
        Spinner spinner;
        Spinner spinner2;
        //Cargar los grafos
        G1 = crearVertices(new Grafo());
        G1 = crearArcosRutaA(G1);


        cargarMarkers();
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 14.0f ) );
        verCamino = findViewById(R.id.verCamino);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        String[] datos = new String[] {"A", "B", "C", "D", "E","F","G","H","I","J","K","L"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, datos);

        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        verCamino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String origen = spinner.getSelectedItem().toString();
                String destino = spinner2.getSelectedItem().toString();
                mMap.clear();
                cargarMarkers();
                graficarMapa(origen,destino);
            }
        });
    }
//comentario
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
        g.insertarArco("A","B",584);
        g.insertarArco("B","C",555);
        g.insertarArco("C","D",640);
        g.insertarArco("D","E",511);
        g.insertarArco("E","F",490);
        g.insertarArco("F","G",528);
        g.insertarArco("G","H",722);
        g.insertarArco("H","I",347);
        g.insertarArco("I","J",633);
        g.insertarArco("J","K",719);
        g.insertarArco("K","L",411);
        g.insertarArco("L","A",511);

        g.insertarArco("A","L",511);
        g.insertarArco("L","K",411);
        g.insertarArco("K","J",719);
        g.insertarArco("J","I",633);
        g.insertarArco("I","H",347);
        g.insertarArco("H","G",722);
        g.insertarArco("G","F",528);
        g.insertarArco("F","E",490);
        g.insertarArco("E","D",511);
        g.insertarArco("D","C",640);
        g.insertarArco("C","B",555);
        g.insertarArco("B","A",584);


        return g;
    }


    public String Dijkstra(String o,String d)
    {
        String c=new String();
        String[] caminos;
        String cc;
        c= G1.caminoCortoDFS(o,d);
        caminos= G1.caminos(c,o,d);
        cc= G1.comparar(caminos,o,d);
        return cc;
    }

    public int DevCosto(String o,String d)
    {
        String c="";
        String[] caminos;
        String cc;
        c= G1.caminoCortoDFS(o,d);
        caminos= G1.caminos(c,o,d);
        int x= G1.devolverCosto(caminos,o,d);
        return x;
    }

    public void graficarMapa(String origen, String destino){
        String dest = Dijkstra(origen,destino);
        int i;
        char[] aux=dest.toCharArray();
        String[] s=new String[aux.length];

        for(i=0;i<aux.length;i++)
        {
            s[i]=String.valueOf(aux[i]);
        }

        mostrarCamino(s);
    }

    public void mostrarCamino(String[] x)
    {

        for(int i=0;i<x.length-1;i++)
        {
            Vertice v1=G1.buscarVertice(x[i]);
            Vertice v2=G1.buscarVertice(x[i+1]);

            Double lat1= v1.getLatitud();
            Double lon1= v1.getLongitud();
            Double lat2= v2.getLatitud();
            Double lon2= v2.getLongitud();

            trazarLineaMapa(lat1, lon1, lat2, lon2);
        }

    }

    public void trazarLineaMapa(Double lat1, Double log1, Double lat2, Double log2) {
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(lat1, log1), new LatLng(lat2, log2))
                .width(5)
                .color(Color.RED));
    }

}