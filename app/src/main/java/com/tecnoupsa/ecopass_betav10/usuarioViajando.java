package com.tecnoupsa.ecopass_betav10;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class usuarioViajando extends AppCompatActivity {

    Usuario us;
    String id = "";
    ImageView QR;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comienzo_viaje);
        QR = findViewById(R.id.QR2);
        time task = new time();
        task.execute();
        us = obtenerDatosUsuario();
        if(us != null) {
            id = us.getId();
            generarQR(id);
        }
        Toast.makeText(usuarioViajando.this, "Que tengas un buen viaje!", Toast.LENGTH_SHORT).show();

        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        TextView Hora = findViewById(R.id.txtHora);
        Hora.setText(currentDateTimeString);

    }

    public void generarQR(String id){
        String texto = id + "," + "Finalizar";
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

                                if(estadoViaje.equals("false")){
                                    if(us != null)
                                        cambiarVentana(us);
                                } else {
                                    ejecutar();
                                }


                            } else {
                                Toast.makeText(usuarioViajando.this, "No existen datos.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(usuarioViajando.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(usuarioViajando.this, "Error: " + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void cambiarVentana(Usuario us){
        Intent i = new Intent(getApplicationContext(), DestinoFinal.class);
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




}
