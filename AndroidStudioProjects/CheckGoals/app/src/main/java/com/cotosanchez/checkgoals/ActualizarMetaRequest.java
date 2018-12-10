package com.cotosanchez.checkgoals;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stalincj on 05/07/2016.
 */
public class ActualizarMetaRequest extends StringRequest{
    private static String hostname = ConfiguracionActivity.hostname;
    private static String url = "http://"+hostname+"/checkgoals/controladores/actualizar_meta.php";
    private Map<String, String> parametros;

    ActualizarMetaRequest(String correo, String nombreActual, String nombre, int notaAcumulada, int notaEvaluada, int notaMinima, Response.Listener<String> listener){
        super(Method.POST, url, listener, null);
        parametros = new HashMap<>();
        parametros.put("correo", correo);
        parametros.put("nombreActual", nombreActual);
        parametros.put("nombre", nombre);
        parametros.put("notaAcumulada", notaAcumulada+"");
        parametros.put("notaEvaluada", notaEvaluada+"");
        parametros.put("notaMinima", notaMinima+"");
    }

    ActualizarMetaRequest(String correo, String nombre, String borrar ,Response.Listener<String> listener){
        super(Method.POST, url, listener, null);
        parametros = new HashMap<>();
        parametros.put("correo", correo);
        parametros.put("nombre", nombre);
        parametros.put("borrar", borrar);
    }

    @Override
    public Map<String, String> getParams() {
        return parametros;
    }

}
