package com.cotosanchez.checkgoals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class ConfiguracionActivity extends AppCompatActivity {

    public static String hostname = "192.168.0.100";
    public static boolean colorFondo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        final EditText txtHostname = (EditText) findViewById(R.id.txtHostname);
        final RadioButton rdoFondo = (RadioButton) findViewById(R.id.rdoFondo);

        txtHostname.setText(ConfiguracionActivity.hostname);

        Button btnAceptar = (Button) findViewById(R.id.btnAceptar);
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);

        if(btnAceptar!=null)
            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConfiguracionActivity.hostname= txtHostname.getText().toString();
                    if(rdoFondo.isChecked())
                        ConfiguracionActivity.colorFondo=true;
                    else
                        ConfiguracionActivity.colorFondo=false;

                    Intent loginIntent = new Intent(ConfiguracionActivity.this, LoginActivity.class);
                    loginIntent.putExtra("configuracion",true);
                    startActivity(loginIntent);
                }
            });

        if(btnCancelar!=null)
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent loginIntent = new Intent(ConfiguracionActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
            });

    }
}
