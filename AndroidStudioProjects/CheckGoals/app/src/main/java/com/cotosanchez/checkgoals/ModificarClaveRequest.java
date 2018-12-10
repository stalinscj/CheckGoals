package com.cotosanchez.checkgoals;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stalincj on 02/07/2016.
 */
public class ModificarClaveRequest extends StringRequest {
    private static String hostname = ConfiguracionActivity.hostname;
    private static String url = "http://"+hostname+"/checkgoals/controladores/modificar_clave.php";
    private Map<String, String> parametros;

    public ModificarClaveRequest(String correo, String claveActual, String claveNueva, Response.Listener<String> listener){
        super(Method.POST, url, listener, null );
        parametros = new HashMap<>();
        parametros.put("correo", correo);
        parametros.put("claveActual", claveActual);
        parametros.put("claveNueva", claveNueva);
    }


    @Override
    public Map<String, String> getParams() {
        return parametros;
    }
}
