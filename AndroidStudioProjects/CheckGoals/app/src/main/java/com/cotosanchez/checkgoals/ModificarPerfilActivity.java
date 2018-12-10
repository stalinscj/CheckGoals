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

public class ModificarPerfilActivity extends AppCompatActivity implements Validator.ValidationListener{

    private String correoActual;

    @Length(min = 2, max = 20, message = "El Nombre debe tener entre 2 y 20 caracteres")
    @Pattern(regex = "[A-Za-z]+", message = "El Nombre debe contener solo letras")
    @Order(1)
    EditText txtNombre;
    @Length(min = 2, max = 20, message = "El Apellido debe tener entre 2 y 20 caracteres")
    @Pattern(regex = "[A-Za-z]+", message = "El Apellido debe contener solo letras")
    @Order(2)
    EditText txtApellido;
    @Min(value=1900, message = "No pudo haber nacido antes de 1900")
    @Max(value = 2016, message = "No pudo haber nacido despues del 2016")
    @Order(3)
    EditText txtAnioNacimiento;
    @Email(message = "Ingrese un Correo Válido")
    @Order(4)
    EditText txtCorreo;
    EditText txtClave;
    Validator validador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_perfil);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        Toast.makeText(this, "Conectando con el servidor...", Toast.LENGTH_SHORT).show();
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtAnioNacimiento = (EditText) findViewById(R.id.txtAnioNacimeinto);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtClave = (EditText) findViewById(R.id.txtClave);
        correoActual = getIntent().getStringExtra("correoActual");

        Response.Listener<String> respuestaListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String respuesta){
                Log.d("JSON--->", respuesta);
                try {
                    JSONObject respuestaJSON = new JSONObject(respuesta);
                    boolean error = respuestaJSON.getBoolean("error");

                    if(!error){
                        JSONObject usuarioJSON = respuestaJSON.getJSONObject("usuario");
                        String nombre =usuarioJSON.getString("nombre");
                        String apellido =usuarioJSON.getString("apellido");
                        int anioNacimiento =usuarioJSON.getInt("anio_nacimiento");
                        String correo =usuarioJSON.getString("correo");

                        txtNombre.setText(nombre);
                        txtApellido.setText(apellido);
                        txtAnioNacimiento.setText(anioNacimiento+"");
                        txtCorreo.setText(correo);

                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ModificarPerfilActivity.this);
                        alerta.setMessage("Error al recuperar datos actuales: "+respuestaJSON.getString("msj"))
                                .setNegativeButton("Aceptar", null)
                                .create()
                                .show();

                        Intent bienvenidoIntent = new Intent(ModificarPerfilActivity.this, BienvenidoActivity.class);
                        bienvenidoIntent.putExtra("correo", correoActual);
                        startActivity(bienvenidoIntent);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ModificarPerfilRequest modificarPerfilRequest = new ModificarPerfilRequest(correoActual, respuestaListener);
        RequestQueue cola = Volley.newRequestQueue(ModificarPerfilActivity.this);
        cola.add(modificarPerfilRequest);

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
                    Intent bienvenidoIntent = new Intent(ModificarPerfilActivity.this, BienvenidoActivity.class);
                    bienvenidoIntent.putExtra("correo", correoActual);
                    startActivity(bienvenidoIntent);
                }
            });
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Conectando con el servidor...", Toast.LENGTH_SHORT).show();
        final String nombre = txtNombre.getText().toString();
        final String apellido = txtApellido.getText().toString();
        final int anioNacimiento = Integer.valueOf(txtAnioNacimiento.getText().toString());
        final String correo = txtCorreo.getText().toString();
        final String clave = txtClave.getText().toString();

        Response.Listener<String> respuestaListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String respuesta){
                try {
                    JSONObject respuestaJSON = new JSONObject(respuesta);
                    boolean error = respuestaJSON.getBoolean("error");

                    if(!error){
                        Intent bienvenidoIntent = new Intent(ModificarPerfilActivity.this, BienvenidoActivity.class);
                        bienvenidoIntent.putExtra("correo", correo);
                        bienvenidoIntent.putExtra("perfilModificado", true);
                        startActivity(bienvenidoIntent);
                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ModificarPerfilActivity.this);
                        alerta.setMessage("Error al modificar perfil: "+respuestaJSON.getString("msj"))
                                .setNegativeButton("Aceptar", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ModificarPerfilRequest modificarPerfilRequest = new ModificarPerfilRequest(correoActual, nombre, apellido, anioNacimiento, correo, clave, respuestaListener);
        RequestQueue cola = Volley.newRequestQueue(ModificarPerfilActivity.this);
        cola.add(modificarPerfilRequest);

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

    public static int getAnioActual() {

        Calendar calendario = new GregorianCalendar();
        int anio = calendario.get(Calendar.YEAR);
        return anio;
    }



}
