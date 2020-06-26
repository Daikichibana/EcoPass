package com.tecnoupsa.ecopass_betav10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.tecnoupsa.ecopass_betav10.Clases.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity  {

    EditText usuario;
    EditText clave;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario = findViewById(R.id.txtNombreUsuario);
        clave = findViewById(R.id.txtClave);
        btnlogin = findViewById(R.id.btnLogin);

            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = usuario.getText().toString();
                    Conectar(id);
                }
            });

    }

    public void Conectar(String ID){
        Map<String, String> datos = new HashMap<>();
        datos.put("id", ID);
        AndroidNetworking.post(constantes.URL_OBTENERUSUARIOPORID)
                .addJSONObjectBody(new JSONObject(datos))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject datos = response.getJSONObject("data");
                            String id = datos.getString("id");
                            String nombre = datos.getString("nombre");
                            String apellido = datos.getString("apellido");
                            String clave = datos.getString("clave");

                            Usuario us = new Usuario(id,nombre,apellido,clave);

                            if(verificarCuenta(us))
                                iniciarSesion(us);

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

    public Boolean verificarCuenta(Usuario us) {
        if (isValidarCampos()) {
            String IDText = usuario.getText().toString();
            String CLAVEText = clave.getText().toString();

            if (us.getId().equals(IDText) && us.getClave().equals(CLAVEText)) {
                return true;
            } else {
                Toast.makeText(LoginActivity.this, "Intente de nuevo", Toast.LENGTH_SHORT).show();
                return false;
            }

        } else {
            return false;
        }
    }

    private boolean isValidarCampos(){
        return !usuario.getText().toString().trim().isEmpty() && !clave.getText().toString().trim().isEmpty();
    }

    public void iniciarSesion(Usuario us){
        Intent i = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", us);
        i.putExtras(bundle);
        startActivity(i);
    }

}