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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CrearMetaActivity extends AppCompatActivity implements Validator.ValidationListener{

    private String correo;

    @Length(min = 2, max = 20, message = "El Nombre debe tener entre 2 y 20 caracteres")
    @Pattern(regex = "[A-Za-z]+", message = "El Nombre debe contener solo letras")
    @Order(1)
    EditText txtNombre;
    @Min(value=0, message = "Su nota acumulada no puede ser menor que 0")
    @Max(value = 100, message = "Su nota acumulada no puede mayor que 100")
    @Order(2)
    EditText txtNotaAcumulada;
    @Min(value=0, message = "La nota evaluada no puede ser menor que 0")
    @Max(value = 100, message = "La nota evaluada no puede mayor que 100")
    @Order(3)
    EditText txtNotaEvaluada;
    @Min(value=1, message = "La nota mínima no puede ser menor que 1")
    @Max(value = 100, message = "La nota mínima no puede mayor que 100")
    @Order(4)
    EditText txtNotaMinima;

    TextView lblFecha;

    Validator validador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_metas);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        correo = getIntent().getStringExtra("correo");

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtNotaAcumulada = (EditText) findViewById(R.id.txtNotaAcumulada);
        txtNotaEvaluada = (EditText) findViewById(R.id.txtNotaEvaluada);
        txtNotaMinima = (EditText) findViewById(R.id.txtNotaMinima);

        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblFecha.setText("Fecha de Creación: "+getFechaStr(false));

        Button btnAceptar = (Button) findViewById(R.id.btnAceptar);
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);

        validador = new Validator(this);
        validador.setValidationListener(this);

        if(btnAceptar!=null)
            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int notaAcumulada = Integer.valueOf(txtNotaAcumulada.getText().toString());
                    final int notaEvaluada = Integer.valueOf(txtNotaEvaluada.getText().toString());
                    if(notaAcumulada>notaEvaluada){
                        Toast.makeText(CrearMetaActivity.this, "La Nota Acumulada no puede ser mayor a la Nota Evaluada", Toast.LENGTH_SHORT).show();
                    }else{
                        validador.validate();
                    }

                }
            });

        if (btnCancelar!=null)
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bienvenidoIntent = new Intent(CrearMetaActivity.this, BienvenidoActivity.class);
                    bienvenidoIntent.putExtra("correo", correo);
                    startActivity(bienvenidoIntent);
                }
            });
    }



    @Override
    public void onValidationSucceeded(){
        Toast.makeText(this, "Conectando con el servidor...", Toast.LENGTH_SHORT).show();
        final String nombre = txtNombre.getText().toString();
        final int notaAcumulada = Integer.valueOf(txtNotaAcumulada.getText().toString());
        final int notaEvaluada = Integer.valueOf(txtNotaEvaluada.getText().toString());
        final int notaMinima = Integer.valueOf(txtNotaMinima.getText().toString());
        final String fechaStr = getFechaStr(true);

        Response.Listener<String> respuestaListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String respuesta) {
                try {
                    JSONObject respuestaJSON = new JSONObject(respuesta);
                    boolean error = respuestaJSON.getBoolean("error");

                    if (!error){
                        Intent bienvenidoIntent = new Intent(CrearMetaActivity.this, BienvenidoActivity.class);
                        bienvenidoIntent.putExtra("correo", correo);
                        bienvenidoIntent.putExtra("metaCreada", true);
                        startActivity(bienvenidoIntent);
                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(CrearMetaActivity.this);
                        alerta.setMessage("Error al crear ueva Meta: "+respuestaJSON.getString("msj"))
                                .setNegativeButton("Aceptar", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest crearMetaRequest = new CrearMetaRequest(correo, nombre, notaAcumulada, notaEvaluada, notaMinima, fechaStr, respuestaListener);
        RequestQueue cola = Volley.newRequestQueue(CrearMetaActivity.this);
        cola.add(crearMetaRequest);
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

    //cambiarFormato = false -> DD-MM-AAAA
    //cambiarFormato = true  -> AAAA-MM-DD
    public String getFechaStr(boolean cambiarFormato){

        Date fecha = new Date();
        SimpleDateFormat formatoFecha;

        if(cambiarFormato)
            formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        else
            formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

        String fechaStr = formatoFecha.format(fecha);

        return fechaStr;
    }
}
