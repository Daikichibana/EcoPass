package com.tecnoupsa.ecopass_betav10.Clases;

import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.tecnoupsa.ecopass_betav10.LoginActivity;
import com.tecnoupsa.ecopass_betav10.constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Usuario implements Serializable {
    String id;
    String nombre;
    String apellido;
    String clave;

    public Usuario() {
        id = "";
        nombre = "";
        apellido = "";
        clave = "";
    }

    public Usuario(String id, String nombre, String apellido, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.clave = clave;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void cargarDatosUsuario(String ID){
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
                            id = datos.getString("id");
                            nombre = datos.getString("nombre");
                            apellido = datos.getString("apellido");
                            clave = datos.getString("clave");

                        } catch (JSONException e) {
                            //Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //Toast.makeText(LoginActivity.this, "Error: " + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
