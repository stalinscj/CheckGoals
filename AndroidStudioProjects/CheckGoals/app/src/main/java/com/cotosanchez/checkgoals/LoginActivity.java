package com.cotosanchez.checkgoals;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginActivity extends AppCompatActivity  implements Validator.ValidationListener{

    //@NotEmpty(message = "Debe ingresar su Correo")
    @Email(message = "Correo inválido")
    EditText txtCorreo;
    @NotEmpty(message = "Debe ingresar su Clave")
    EditText txtClave;
    Validator validador;

    TextView lblweb;
    TextView lblConfiguracion;

    Button btnIngresar;
    Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtClave = (EditText) findViewById(R.id.txtClave);

        lblweb = (TextView) findViewById(R.id.lblWeb);
        lblConfiguracion = (TextView) findViewById(R.id.lblConfiguracion);

        validador = new Validator(this);
        validador.setValidationListener(this);

        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);

        Intent thisIntent = getIntent();
        boolean configuracion = thisIntent.getBooleanExtra("configuracion", false);

        lblweb.setText(ConfiguracionActivity.hostname+"/checkgoals");
        if (configuracion){
            Toast.makeText(this, "Configuracion modificada exitosamente", Toast.LENGTH_SHORT).show();
        }

        if(btnIngresar!=null)
            btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validador.validate();
            }
        });

        if(btnRegistrarse!=null)
            btnRegistrarse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registroIntent = new Intent(LoginActivity.this, RegistroActivity.class);
                    startActivity(registroIntent);
                }
            });

        if (lblConfiguracion !=null)
            lblConfiguracion.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent configuracionIntent = new Intent(LoginActivity.this, ConfiguracionActivity.class);
                    startActivity(configuracionIntent);
                }
            });
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Conectando con el servidor...", Toast.LENGTH_SHORT).show();
        String correo = txtCorreo.getText().toString();
        String clave = txtClave.getText().toString();

        Response.Listener<String> respuestaListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String respuesta){
                try{
                    JSONObject respuestaJSON = new JSONObject(respuesta);
                    boolean error = respuestaJSON.getBoolean("error");

                    if(!error){
                        JSONObject usuarioJSON = respuestaJSON.getJSONObject("usuario");
                        //String nombre = usuarioJSON.getString("nombre");
                        //String apellido = usuarioJSON.getString("apellido");
                        //int edad = RegistroActivity.getAnioActual()-usuarioJSON.getInt("anio_nacimiento");
                        String correo = usuarioJSON.getString("correo");

                        Intent bienvenidoIntent = new Intent(LoginActivity.this, BienvenidoActivity.class);
                        //bienvenidoIntent.putExtra("nombre", nombre);
                        //bienvenidoIntent.putExtra("apellido", apellido);
                        //bienvenidoIntent.putExtra("edad", edad);
                        bienvenidoIntent.putExtra("correo", correo);
                        startActivity(bienvenidoIntent);
                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(LoginActivity.this);
                        alerta.setMessage("Error al loguearse: "+respuestaJSON.getString("msj"))
                                .setNegativeButton("Aceptar", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(correo, clave, respuestaListener);
        RequestQueue cola = Volley.newRequestQueue(LoginActivity.this);
        cola.add(loginRequest);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errores) {
        for (ValidationError error : errores) {
            View vista = error.getView();
            String msj = error.getCollatedErrorMessage(this);


            if (vista instanceof EditText) {
                ((EditText) vista).setError(msj);
            } else {
                Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
            }
        }
    }
}
