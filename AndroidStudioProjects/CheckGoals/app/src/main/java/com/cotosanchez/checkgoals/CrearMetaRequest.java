package com.cotosanchez.checkgoals;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stalincj on 03/07/2016.
 */
public class CrearMetaRequest extends StringRequest {
    private static String hostname = ConfiguracionActivity.hostname;
    private static String url = "http://"+hostname+"/checkgoals/controladores/crear_meta.php";
    private Map<String, String> parametros;

    CrearMetaRequest(String correo, String nombre, int notaAcumulada, int notaEvaluada, int notaMinima, String fecha, Response.Listener<String> listener){
        super(Method.POST, url, listener, null);
        parametros = new HashMap<>();
        parametros.put("correo", correo);
        parametros.put("nombre", nombre);
        parametros.put("notaAcumulada", notaAcumulada+"");
        parametros.put("notaEvaluada", notaEvaluada+"");
        parametros.put("notaMinima", notaMinima+"");
        parametros.put("fecha", fecha);
    }

    @Override
    public Map<String, String> getParams() {
        return parametros;
    }
}
