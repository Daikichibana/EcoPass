package com.tecnoupsa.ecopass_betav10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText usuario;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.txtNombreUsuario);
        btnlogin = findViewById(R.id.btnLogin);

        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);

        boolean estado = preferencias.getBoolean("datos", false);


            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    conectar();
                }
            });

    }

    public void conectar(){
        if(isValidarCampos()) {
            String id = usuario.getText().toString();
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
                                String estado = response.getString("estado");
                                Toast.makeText(LoginActivity.this, estado, Toast.LENGTH_SHORT).show();
                                if(estado.equals("Se obtuvieron los datos correctamente")){
                                    iniciarSesion();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(LoginActivity.this, "Error: " + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean isValidarCampos(){
        return !usuario.getText().toString().trim().isEmpty();
    }

    public void iniciarSesion(){
        Intent i = new Intent(this, MainActivity.class);
        guardarSesion();
        i.putExtra("id",usuario.getText().toString());
        startActivity(i);
    }

    public void guardarSesion(){
        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor =  preferencias.edit();
        Obj_editor.putBoolean("datos", true);
        Obj_editor.commit();
    }
}