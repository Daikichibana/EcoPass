package com.tecnoupsa.ecopass_betav10;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.tecnoupsa.ecopass_betav10.Clases.Usuario;


public class DestinoFinal extends AppCompatActivity {

  //  Button btn;
  //  Usuario us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destino_final);
/*
        btn = (Button) findViewById(R.id.btn);
        us = new Usuario();
        us = obtenerDatosUsuario();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });


    }
    public void openNewActivity(){
        Intent i = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", us);
        i.putExtras(bundle);
        startActivity(i);
    }

    public Usuario obtenerDatosUsuario(){
        Bundle objetoEnviado = getIntent().getExtras();

        if(objetoEnviado != null)
            return (Usuario) objetoEnviado.getSerializable("usuario");
        else
            return null;
    }

*/
    }
    }
