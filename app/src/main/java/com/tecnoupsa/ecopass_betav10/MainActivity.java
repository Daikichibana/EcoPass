package com.tecnoupsa.ecopass_betav10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;
    Button iniciarViaje;
    Button verRutas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarViaje = (Button) findViewById(R.id.btnIniciarViaje);
        verRutas = (Button) findViewById(R.id.btnVerRuta);

        mostrarDatosporID();

        verRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RutaActivity.class);
                startActivity(i);
            }
        });

        iniciarViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), IniciarViaje.class);
                startActivity(i);
            }
        });

    }

    public void mostrarDatosporID(){
        String id = getIntent().getStringExtra("id");
        tv1 = (TextView) findViewById(R.id.txtMainNombre);
        tv2 = (TextView) findViewById(R.id.txtPuntos);
        MostrarDatosUsuario(id);
        obtenerPuntosUsuario(id);
    }

    public void MostrarDatosUsuario(String id){
            Map<String, String> datos = new HashMap<>();
            datos.put("id", id);

            AndroidNetworking.post(constantes.URL_OBTENERPORID)
                    .addJSONObjectBody(new JSONObject(datos))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String respuesta = response.getString("respuesta");
                                if(respuesta.equals("200")){
                                    JSONObject datos = response.getJSONObject("data");
                                    String nombre = datos.getString("nombre");
                                    String apellido = datos.getString("apellido");
                                    tv1.setText(nombre + " " + apellido);
                                } else {
                                    Toast.makeText(MainActivity.this, "No existen datos.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(MainActivity.this, "Error: " + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        }
                    });

    }

    public void obtenerPuntosUsuario(String id) {
        Map<String, String> datos = new HashMap<>();
        datos.put("id", id);

        AndroidNetworking.post(constantes.URL_OBTENERESTADO_PUNTOS)
                .addJSONObjectBody(new JSONObject(datos))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respuesta = response.getString("respuesta");
                            if(respuesta.equals("200")){
                                JSONObject datos = response.getJSONObject("data");
                                String puntos = String.valueOf(datos.getInt("puntos"));
                                tv2.setText(puntos);
                            } else {
                                Toast.makeText(MainActivity.this, "No existen datos.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MainActivity.this, "Error: " + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}