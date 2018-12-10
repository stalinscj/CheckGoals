package com.cotosanchez.checkgoals;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Stalincj on 04/07/2016.
 */
public class MetaAdapter extends RecyclerView.Adapter<MetaAdapter.MetasViewHolder> {
    private ArrayList<Meta> metas;
    private Context contexto;
    private String correo;

    public MetaAdapter(ArrayList<Meta> metas, Context contexto, String correo) {
        this.metas = metas;
        this.contexto = contexto;
        this.correo = correo;
    }

    @Override
    public MetasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_meta, parent, false);
        MetasViewHolder metasViewHolder = new MetasViewHolder(vista, contexto, metas);
        return metasViewHolder;
    }

    @Override
    public void onBindViewHolder(MetasViewHolder metasViewHolder, int i) {
        String nombre = metas.get(i).getNombre();
        String fecha = metas.get(i).getFecha();
        int notaAcumulada = metas.get(i).getNotaAcumulada();
        int notaEvaluada = metas.get(i).getNotaEvaluada();
        int notaMinima = metas.get(i).getNotaMinima();
        boolean lograda = metas.get(i).isLograda();
        boolean finalizada = metas.get(i).isFinalizada();


        metasViewHolder.lblNombre.setText(nombre);
        metasViewHolder.lblFecha.setText("Creada el "+fecha);
        metasViewHolder.lblProgreso.setText("("+notaAcumulada+"/"+notaEvaluada+")");
        metasViewHolder.progresBar.setProgress(notaAcumulada);

        int notaPorEvaluar = 100-notaEvaluada;
        int color = 0;
        int colores[] = {
                Color.rgb(142, 194, 106),   //[0] Verde
                Color.rgb(253, 110, 99),    //[1] Rojo
                Color.rgb(139, 184, 225),   //[2] Azul
                Color.rgb(247, 244, 105),   //[3] Amarillo
                Color.rgb(241,153,65)       //[4] Naranja
        };

        if(!ConfiguracionActivity.colorFondo){
            colores[0] = Color.GREEN;
            colores[1] = Color.RED;
            colores[2] = Color.BLUE;
            colores[3] = Color.rgb(255, 215, 0);
            colores[4] = Color.rgb(255,140,0);
        }

        if(finalizada){
            if(lograda){
                //Verde
                color = colores[0];
                metasViewHolder.imgEstado.setImageResource(R.drawable.lograda);
            }
            else{
                //Rojo
                color = colores[1];
                metasViewHolder.imgEstado.setImageResource(R.drawable.no_lograda);
            }
        }else{
            metasViewHolder.imgEstado.setImageResource(R.drawable.no_finalizada);
            if(notaAcumulada>=notaMinima) {
                //Azul
                color = colores[2];
            }
            else{
                if (notaAcumulada + notaPorEvaluar >= notaMinima){
                    //Amarillo
                    color = colores[3];
                }else{
                    //Naranja
                    color = colores[4];
                }
            }
        }
        if(ConfiguracionActivity.colorFondo)
            metasViewHolder.layoutFondo.setBackgroundColor(color);
        else
            metasViewHolder.progresBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);


    }

    @Override
    public int getItemCount() {
        return metas.size();
    }

    public class MetasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView lblNombre;
        TextView lblFecha;
        TextView lblProgreso;
        ImageView imgEstado;
        ProgressBar progresBar;
        RelativeLayout layoutFondo;
        ArrayList<Meta> metas;
        Context contexto;

        public MetasViewHolder(View itemView, Context contexto, ArrayList<Meta> metas){
            super(itemView);
            this.metas = metas;
            this.contexto = contexto;
            itemView.setOnClickListener(this);
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            lblFecha = (TextView) itemView.findViewById(R.id.lblFecha);
            lblProgreso = (TextView) itemView.findViewById(R.id.lblProgreso);
            imgEstado = (ImageView) itemView.findViewById(R.id.imgEstado);
            progresBar = (ProgressBar) itemView.findViewById(R.id.progresBar);
            layoutFondo = (RelativeLayout) itemView.findViewById(R.id.layoutFondo);

        }

        @Override
        public void onClick(View v) {

            int i = getAdapterPosition();
            Meta meta = this.metas.get(i);

            Intent actualizarMetaIntent = new Intent(contexto, ActualizarMetaActivity.class);

            actualizarMetaIntent.putExtra("idUsuario", meta.getIdUsuario());
            actualizarMetaIntent.putExtra("nombre", meta.getNombre());
            actualizarMetaIntent.putExtra("notaAcumulada", meta.getNotaAcumulada());
            actualizarMetaIntent.putExtra("notaEvaluada", meta.getNotaEvaluada());
            actualizarMetaIntent.putExtra("notaMinima", meta.getNotaMinima());
            actualizarMetaIntent.putExtra("lograda", meta.isLograda());
            actualizarMetaIntent.putExtra("finalizada", meta.isFinalizada());
            actualizarMetaIntent.putExtra("fecha", meta.getFecha());
            actualizarMetaIntent.putExtra("correo", correo);

            this.contexto.startActivity(actualizarMetaIntent);
        }
    }
}
