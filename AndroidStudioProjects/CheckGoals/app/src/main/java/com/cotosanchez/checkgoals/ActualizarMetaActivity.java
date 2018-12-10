package com.cotosanchez.checkgoals;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class ActualizarMetaActivity extends AppCompatActivity implements Validator.ValidationListener {

    @Length(min = 2, max = 20, message = "El Nombre debe tener entre 2 y 20 caracteres")
    @Pattern(regex = "[A-Za-z]+", message = "El Nombre debe contener solo letras")
    @Order(1)
    private EditText txtNombre;
    @Min(value=0, message = "Su nota acumulada no puede ser menor que 0")
    @Max(value = 100, message = "Su nota acumulada no puede mayor que 100")
    @Order(2)
    private EditText txtNotaAcumulada;
    @Min(value=0, message = "La nota evaluada no puede ser menor que 0")
    @Max(value = 100, message = "La nota evaluada no puede mayor que 100")
    @Order(3)
    private EditText txtNotaEvaluada;
    @Min(value=1, message = "La nota mínima no puede ser menor que 1")
    @Max(value = 100, message = "La nota mínima no puede mayor que 100")
    @Order(4)
    private EditText txtNotaMinima;
    private TextView lblFecha;
    private ImageView imgEstado;
    private TextView lblNombre;
    private TextView lblNota;
    private LinearLayout layoutFondo;
    private Button btnActualizar;
    private Button btnVolver;
    private Button btnBorrar;
    private String correo;
    Validator validador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_meta);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        imgEstado = (ImageView) findViewById(R.id.imgEstado);
        lblNombre = (TextView) findViewById(R.id.lblNombre);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtNotaAcumulada = (EditText) findViewById(R.id.txtNotaAcumulada);
        txtNotaEvaluada = (EditText) findViewById(R.id.txtNotaEvaluada);
        txtNotaMinima = (EditText) findViewById(R.id.txtNotaMinima);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblNota = (TextView) findViewById(R.id.lblNota);
        layoutFondo = (LinearLayout) findViewById(R.id.layoutFondo);
        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnVolver = (Button) findViewById(R.id.btnVolver);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);

        validador = new Validator(this);
        validador.setValidationListener(this);

        correo = getIntent().getStringExtra("correo");

        String notasLogradas[] = {
                "Todos pueden alcanzar el éxito pero pocos se atreven, tú lo hiciste y hoy eres uno de los mejores",
                "¡Así se hace! Todo el trabajo duro valió la pena",
                "Felicitaciones por esta nueva meta que has alcanzado y deseo que puedas lograr muchísimas más",
                "Así como hoy tienes este gran logro en tus manos, día tras día y con esfuerzo obtendrás más"
        };

        String notasEnCursoLogradas[] = {
                "El éxito se alcanza convirtiendo cada paso en una meta y cada meta en un paso",
                "Los logros de tu trabajo son justo merecimiento a tu esfuerzo diario",
                "Tu duro trabajo finalmente está dando sus frutos",
                "Felicitaciones y que los éxitos sigan de tu lado, ahora trázate nuevas metas pues los ganadores nunca se detienen"
        };

        String notasEnCursoConOp[] = {
                "Los ganadores nunca se rinden y los perdedores nunca ganan",
                "No te conformes con lo que necesitas, lucha por lo que te mereces",
                "A veces, la adversidad es lo que necesitas encarar para ser exitoso",
                "Todo parece imposible hasta que se hace"
        };

        String notasEnCursoSinOp[] = {
                "Si tu barco no viene a salvarte, nada hacia él para encontrarlo",
                "El único lugar en el cual ‘éxito’ viene antes de ‘trabajo’ es en el diccionario",
                "Es duro fracasar, pero es todavía peor no haber intentado nunca triunfar",
                "Es mejor fracasar buscando el triunfo, que dejar de triunfar por miedo al fracaso"
        };

        String notasFracasos[] = {
                "Nuestra mayor gloria no esta en caer, sino en levantarnos cada vez que caemos",
                "Cáete siete veces, levántate ocho",
                "No es más grande el que nunca falla, sino el que nunca se da por vencido",
                "El fracaso es una gran oportunidad para empezar otra vez con más inteligencia"
        };

        String nota="";
        int i = new Random().nextInt(4);
        int color = 0;
        boolean lograda = getIntent().getBooleanExtra("lograda", false);
        boolean finalizada = getIntent().getBooleanExtra("finalizada", false);
        final int notaAcumulada = getIntent().getIntExtra("notaAcumulada",0);
        final int notaEvaluada = getIntent().getIntExtra("notaEvaluada",0);
        final int notaMinima = getIntent().getIntExtra("notaMinima",0);
        String nombre = getIntent().getStringExtra("nombre");
        int notaPorEvaluar = 100 - notaEvaluada;

        if(finalizada){
            if(lograda){
                color = Color.rgb(142, 194, 106);
                imgEstado.setImageResource(R.drawable.lograda2);
                nota = notasLogradas[i];
            }
            else{
                color = Color.rgb(253, 110, 99);
                imgEstado.setImageResource(R.drawable.no_lograda2);
                nota = notasFracasos[i];
            }
        }else{
            imgEstado.setImageResource(R.drawable.no_finalizada2);
            if(notaAcumulada>=notaMinima) {
                color = Color.rgb(139, 184, 225);
                nota = notasEnCursoLogradas[i];
            }
            else{
                if (notaAcumulada + notaPorEvaluar >= notaMinima){
                    color = Color.rgb(247, 244, 105);
                    nota = notasEnCursoConOp[i];
                }else{
                    color = Color.rgb(241,153,65);
                    nota = notasEnCursoSinOp[i];
                }
            }
        }

        layoutFondo.setBackgroundColor(color);
        lblNota.setText(nota);

        lblNombre.setText(nombre);
        txtNombre.setText(nombre);
        txtNotaAcumulada.setText(notaAcumulada+"");
        txtNotaEvaluada.setText(notaEvaluada+"");
        txtNotaMinima.setText(notaMinima+"");
        lblFecha.setText("Creada el "+getIntent().getStringExtra("fecha"));

        if(btnActualizar!=null)
            btnActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(notaAcumulada>notaEvaluada){
                        Toast.makeText(ActualizarMetaActivity.this, "La Nota Acumulada no puede ser mayor a la Nota Evaluada", Toast.LENGTH_SHORT).show();
                    }else{
                        validador.validate();
                    }
                }
            });

        if(btnVolver!=null)
            btnVolver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent verMetasIntent = new Intent(ActualizarMetaActivity.this, VerMetasActivity.class);
                    verMetasIntent.putExtra("correo", correo);
                    startActivity(verMetasIntent);
                }
            });

        if(btnBorrar!=null)
            btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    borrarMeta();
                }
            });



    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Conectando con el servidor...", Toast.LENGTH_SHORT).show();
        final String nombreActual = lblNombre.getText().toString();
        final String nombre = txtNombre.getText().toString();
        final int notaAcumulada = Integer.valueOf(txtNotaAcumulada.getText().toString());
        final int notaEvaluada = Integer.valueOf(txtNotaEvaluada.getText().toString());
        final int notaMinima = Integer.valueOf(txtNotaMinima.getText().toString());

        Response.Listener<String> respuestaListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String respuesta) {
                Log.e("Error: ", respuesta);
                try {
                    JSONObject respuestaJSON = new JSONObject(respuesta);
                    boolean error = respuestaJSON.getBoolean("error");

                    if (!error){
                        Intent verMetasIntent = new Intent(ActualizarMetaActivity.this, VerMetasActivity.class);
                        verMetasIntent.putExtra("correo", correo);
                        verMetasIntent.putExtra("metaActualizada", true);
                        startActivity(verMetasIntent);
                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActualizarMetaActivity.this);
                        alerta.setMessage("Error al actualizar Meta: "+respuestaJSON.getString("msj"))
                                .setNegativeButton("Aceptar", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest actualizarMetaRequest = new ActualizarMetaRequest(correo, nombreActual, nombre, notaAcumulada, notaEvaluada, notaMinima, respuestaListener);
        RequestQueue cola = Volley.newRequestQueue(ActualizarMetaActivity.this);
        cola.add(actualizarMetaRequest);

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

    public void borrarMeta(){
         new AlertDialog.Builder(ActualizarMetaActivity.this)
                .setTitle("Borrar Meta")
                .setMessage("¿Realmente desea BORRAR esta META?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ActualizarMetaActivity.this, "Conectando con el servidor...", Toast.LENGTH_SHORT).show();
                        final String nombre = txtNombre.getText().toString();

                        Response.Listener<String> respuestaListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String respuesta) {
                                try {
                                    JSONObject respuestaJSON = new JSONObject(respuesta);
                                    boolean error = respuestaJSON.getBoolean("error");

                                    if (!error){
                                        Intent verMetasIntent = new Intent(ActualizarMetaActivity.this, VerMetasActivity.class);
                                        verMetasIntent.putExtra("correo", correo);
                                        verMetasIntent.putExtra("metaBorrada", true);
                                        startActivity(verMetasIntent);
                                    }else{
                                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActualizarMetaActivity.this);
                                        alerta.setMessage("Error al actualizar Meta: "+respuestaJSON.getString("msj"))
                                                .setNegativeButton("Aceptar", null)
                                                .create()
                                                .show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        String borrar = "true";
                        StringRequest actualizarMetaRequest = new ActualizarMetaRequest(correo, nombre, borrar, respuestaListener);
                        RequestQueue cola = Volley.newRequestQueue(ActualizarMetaActivity.this);
                        cola.add(actualizarMetaRequest);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
