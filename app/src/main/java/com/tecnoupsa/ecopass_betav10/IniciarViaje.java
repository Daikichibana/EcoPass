package com.tecnoupsa.ecopass_betav10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.tecnoupsa.ecopass_betav10.Clases.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class IniciarViaje extends AppCompatActivity {

    ImageView QR;
    private TextView estado;
    String id;
    Usuario us;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_viaje);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });

        QR = findViewById(R.id.QR);
        estado = findViewById(R.id.estado);
        time task = new time();
        task.execute();

        us = obtenerDatosUsuario();
        id = us.getId();

        if(us!=null)
            generarQR(id);
    }


    //Genera el codigo QR
    public void generarQR(String id){
        String texto = id + "," + "Iniciar";
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(texto, BarcodeFormat.QR_CODE,240,240);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            QR.setImageBitmap(bitmap);
        } catch(WriterException e) {
            e.printStackTrace();
        }
    }


    public void hilo(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void ejecutar(){
        time task = new time();
        task.execute();
    }

    public class time extends AsyncTask<Void,Integer,Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... voids) {
            for(int i = 1; i<=3; i++) {
                hilo();
            }
            return true;
        }


        @Override

        protected void onPostExecute(Boolean aBoolean) {

            consultaEstado(id);
        }

    }

    public void consultaEstado(String ID){
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
                                String estadoViaje = datos.getString("viajando");

                                estado.setText(estadoViaje);

                                if(estado.getText().equals("true")){
                                    if(us != null)
                                        cambiarVentana(us);
                                } else {
                                    ejecutar();
                                }


                            } else {
                                Toast.makeText(IniciarViaje.this, "No existen datos.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(IniciarViaje.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(IniciarViaje.this, "Error: " + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void cambiarVentana(Usuario us){
        Intent i = new Intent(getApplicationContext(), usuarioViajando.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", us);
        i.putExtras(bundle);
        startActivity(i);
    }

    //Obtiene datos del usuario enviado por el otro activity
    public Usuario obtenerDatosUsuario(){
        Bundle objetoEnviado = getIntent().getExtras();

        if(objetoEnviado != null)
            return (Usuario) objetoEnviado.getSerializable("usuario");
        else
            return null;
    }
    public void openNewActivity(){
        Intent intent = new Intent(this, DestinoFinal.class);
        startActivity(intent);
       }


}