package com.cotosanchez.checkgoals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BienvenidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //final EditText txtNombre = (EditText) findViewById(R.id.txtNombre);
        //final EditText txtApellido = (EditText) findViewById(R.id.txtApellido);
        //final EditText txtEdad = (EditText) findViewById(R.id.txtEdad);
        final TextView lblCorreo = (TextView) findViewById(R.id.lblCorreo);

        final Button btnVerMetas = (Button) findViewById(R.id.btnVerMetas);
        final Button btnCrearMeta = (Button) findViewById(R.id.btnCrearMeta);
        final Button btnModificarPerfil = (Button) findViewById(R.id.btnModificarPerfil);
        final Button btnModificarClave = (Button) findViewById(R.id.btnModificarClave);
        final Button btnSalir = (Button) findViewById(R.id.btnSalir);

        Intent thisIntent = getIntent();
        final String correo = thisIntent.getStringExtra("correo");
        final boolean perfilModificado = thisIntent.getBooleanExtra("perfilModificado", false);
        final boolean claveModificada = thisIntent.getBooleanExtra("claveModificada", false);
        final boolean metaCreada = thisIntent.getBooleanExtra("metaCreada", false);

        if(perfilModificado)
            Toast.makeText(this, "El Perfil ha sido modificado exitosamente", Toast.LENGTH_SHORT).show();
        else
            if(claveModificada)
                Toast.makeText(this, "La clave ha sido modificada exitosamente", Toast.LENGTH_SHORT).show();
            else
                if(metaCreada)
                    Toast.makeText(this, "La nueva meta ha sido creada exitosamente", Toast.LENGTH_SHORT).show();


        lblCorreo.setText(correo);

        if(btnVerMetas!=null)
            btnVerMetas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent verMetasIntent = new Intent(BienvenidoActivity.this, VerMetasActivity.class);
                    verMetasIntent.putExtra("correo", correo);
                    startActivity(verMetasIntent);
                }
            });

        if(btnCrearMeta!=null)
            btnCrearMeta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent crearMetaIntent = new Intent(BienvenidoActivity.this, CrearMetaActivity.class);
                    crearMetaIntent.putExtra("correo", correo);
                    startActivity(crearMetaIntent);
                }
            });

        if(btnModificarPerfil!=null)
            btnModificarPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent modificarPerfilIntent = new Intent(BienvenidoActivity.this, ModificarPerfilActivity.class);
                    modificarPerfilIntent.putExtra("correoActual", correo);
                    startActivity(modificarPerfilIntent);
                }
            });

        if(btnModificarClave!=null)
            btnModificarClave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent modificarClaveIntent = new Intent(BienvenidoActivity.this, ModificarClaveActivity.class);
                    modificarClaveIntent.putExtra("correo", correo);
                    startActivity(modificarClaveIntent);
                }
            });

        if(btnSalir!=null)
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(BienvenidoActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }
}
