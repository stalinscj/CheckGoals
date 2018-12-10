package com.cotosanchez.checkgoals;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class VerMetasActivity extends AppCompatActivity {

    private RecyclerView reciclador;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adaptador;

    private ArrayList<Meta> metas;

    private TextView lblNumLogradas;
    private TextView lblNumEnCurso;
    private TextView lblNumFracasos;

    private String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_metas);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        Toast.makeText(this, "Conectando con el servidor...", Toast.LENGTH_SHORT).show();

        correo = getIntent().getStringExtra("correo");

        lblNumLogradas = (TextView) findViewById(R.id.lblNumLogradas);
        lblNumEnCurso = (TextView) findViewById(R.id.lblNumEnCurso);
        lblNumFracasos = (TextView) findViewById(R.id.lblNumFracasos);


        metas = new ArrayList<Meta>();

        Response.Listener<String> respuestaListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String respuesta){
                try {

                    JSONObject respuestaJSON = new JSONObject(respuesta);
                    boolean error = respuestaJSON.getBoolean("error");

                    if(!error){
                        JSONArray metasJSON = respuestaJSON.getJSONArray("metas");

                        if(metasJSON.length()==0){
                            Toast.makeText(VerMetasActivity.this, "Usted no ha creado ninguna Meta.", Toast.LENGTH_SHORT).show();
                            Intent bienvenidoIntent = new Intent(VerMetasActivity.this, BienvenidoActivity.class);
                            bienvenidoIntent.putExtra("correo", correo);
                            startActivity(bienvenidoIntent);
                        }

                        boolean lograda;
                        boolean finalizada;
                        int logradas = 0;
                        int enCurso = 0;
                        int fracasos = 0;
                        String fechaSplit[];
                        String fecha;
                        for (int i=0; i<metasJSON.length(); i++){
                            metas.add(new Meta(metasJSON.getJSONObject(i)));

                            fechaSplit = metas.get(i).getFecha().split("-");
                            fecha = fechaSplit[2] +"-"+ fechaSplit[1] +"-"+ fechaSplit[0];
                            metas.get(i).setFecha(fecha);

                            if(metasJSON.getJSONObject(i).getInt("lograda")==0)
                                lograda = false;
                            else
                                lograda = true;

                            if(metasJSON.getJSONObject(i).getInt("finalizada")==0)
                                finalizada = false;
                            else
                                finalizada = true;

                            if(finalizada)
                                if(lograda)
                                    logradas++;
                                else
                                    fracasos++;
                            else
                                enCurso++;
                        }

                        lblNumLogradas.setText(logradas+"");
                        lblNumFracasos.setText(fracasos+"");
                        lblNumEnCurso.setText(enCurso+"");

                        reciclador = (RecyclerView) findViewById(R.id.metasRecyclerView);
                        layoutManager = new LinearLayoutManager(VerMetasActivity.this, LinearLayoutManager.VERTICAL,false);
                        reciclador.setLayoutManager(layoutManager);
                        adaptador = new MetaAdapter(metas, VerMetasActivity.this, correo);
                        reciclador.setAdapter(adaptador);

                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(VerMetasActivity.this);
                        alerta.setMessage("Error al cargar las metas: "+respuestaJSON.getString("msj"))
                                .setNegativeButton("Aceptar", null)
                                .create()
                                .show();

                        Intent bienvenidoIntent = new Intent(VerMetasActivity.this, BienvenidoActivity.class);
                        bienvenidoIntent.putExtra("correo", correo);
                        startActivity(bienvenidoIntent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        VerMetasRequest verMetasRequest = new VerMetasRequest(correo, respuestaListener);
        RequestQueue cola = Volley.newRequestQueue(VerMetasActivity.this);
        cola.add(verMetasRequest);

        /*final boolean metaActualizada = getIntent().getBooleanExtra("metaActualizada", false);
        final boolean metaBorrada = getIntent().getBooleanExtra("metaCreada", false);

        if(metaActualizada)
            Toast.makeText(this, "La Meta ha sido actualizada exitosamente", Toast.LENGTH_SHORT).show();
        else
            if(metaBorrada)
                Toast.makeText(this, "La Meta ha sido borrada exitosamente", Toast.LENGTH_SHORT).show();*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_ver_metas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.icCrearMeta) {
            Intent crearMetaIntent = new Intent(this, CrearMetaActivity.class);
            crearMetaIntent.putExtra("correo", correo);
            startActivity(crearMetaIntent);

            return true;
        } else if(id==R.id.icVolver){
            Intent bienvenidoIntent = new Intent(this, BienvenidoActivity.class);
            bienvenidoIntent.putExtra("correo", correo);
            startActivity(bienvenidoIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
