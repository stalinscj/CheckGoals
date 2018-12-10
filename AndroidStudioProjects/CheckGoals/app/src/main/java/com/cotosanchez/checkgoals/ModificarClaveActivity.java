package com.cotosanchez.checkgoals;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ModificarClaveActivity extends AppCompatActivity implements Validator.ValidationListener{

    String correo;


    EditText txtClaveActual;
    @Length(min = 6, max = 15, message = "La Clave debe tener entre 6 y 15 caracteres")
    @Password(scheme = Password.Scheme.ALPHA_NUMERIC, message = "La Clave debe contener letras y números")
    @Order(1)
    EditText txtClaveNueva;
    @NotEmpty(message = "Confirme su Clave")
    @ConfirmPassword(message = "Las Claves no son iguales")
    @Order(2)
    EditText txtClaveNueva2;
    Validator validador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_clave);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        Toast.makeText(this, "Conectando con el servidor...", Toast.LENGTH_SHORT).show();
        txtClaveActual = (EditText) findViewById(R.id.txtClaveActual);
        txtClaveNueva = (EditText) findViewById(R.id.txtClaveNueva);
        txtClaveNueva2 = (EditText) findViewById(R.id.txtClaveNueva2);
        correo = getIntent().getStringExtra("correo");

        validador = new Validator(this);
        validador.setValidationListener(this);

        Button btnAceptar = (Button) findViewById(R.id.btnAceptar);
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);

        if(btnAceptar!=null)
            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validador.validate();
                }
            });

        if(btnCancelar!=null)
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bienvenidoIntent = new Intent(ModificarClaveActivity.this, BienvenidoActivity.class);
                    bienvenidoIntent.putExtra("correo", correo);
                    startActivity(bienvenidoIntent);
                }
            });
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Conectando con el servidor...", Toast.LENGTH_SHORT).show();
        final String claveActual = txtClaveActual.getText().toString();
        final String claveNueva = txtClaveNueva.getText().toString();
        final String claveNueva2 = txtClaveNueva2.getText().toString();

        Response.Listener<String> respuestaListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String respuesta){
                try {
                    JSONObject respuestaJSON = new JSONObject(respuesta);
                    boolean error = respuestaJSON.getBoolean("error");

                    if(!error){
                        Intent bienvenidoIntent = new Intent(ModificarClaveActivity.this, BienvenidoActivity.class);
                        bienvenidoIntent.putExtra("correo", correo);
                        bienvenidoIntent.putExtra("claveModificada", true);
                        startActivity(bienvenidoIntent);
                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ModificarClaveActivity.this);
                        alerta.setMessage("Error al modificar clave: "+respuestaJSON.getString("msj"))
                                .setNegativeButton("Aceptar", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ModificarClaveRequest modificarClaveRequest = new ModificarClaveRequest(correo, claveActual, claveNueva, respuestaListener);
        RequestQueue cola = Volley.newRequestQueue(ModificarClaveActivity.this);
        cola.add(modificarClaveRequest);

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