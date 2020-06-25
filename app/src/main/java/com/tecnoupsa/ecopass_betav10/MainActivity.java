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
import com.tecnoupsa.ecopass_betav10.Clases.Estado;
import com.tecnoupsa.ecopass_betav10.Clases.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;
    Button iniciarViaje;
    Button verRutas;
    Usuario us;
    Estado estPuntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarViaje = (Button) findViewById(R.id.btnIniciarViaje);
        verRutas = (Button) findViewById(R.id.btnVerRuta);
        estPuntos = new Estado();
        tv1 = (TextView) findViewById(R.id.txtMainNombre);
        tv2 = (TextView) findViewById(R.id.txtPuntos);
        us = obtenerDatosUsuario();

        String id = us.getId();
        obtenerDatos(id);

        verRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RutaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", us);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        iniciarViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), IniciarViaje.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", us);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

    }

    public Usuario obtenerDatosUsuario(){
        Bundle objetoEnviado = getIntent().getExtras();

        if(objetoEnviado != null)
            return (Usuario) objetoEnviado.getSerializable("usuario");
        else
            return null;
    }

    public void obtenerDatos(String ID) {
        Map<String, String> datos = new HashMap<>();
        datos.put("id", ID);

        AndroidNetworking.post(constantes.URL_OBTENERESTADO)
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
                                String puntos = datos.getString("puntos");

                                //Usuario
                                tv1.setText(us.getId());

                                //Puntos
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