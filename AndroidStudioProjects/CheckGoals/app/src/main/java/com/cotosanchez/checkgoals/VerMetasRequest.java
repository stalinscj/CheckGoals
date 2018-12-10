package com.cotosanchez.checkgoals;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stalincj on 04/07/2016.
 */
public class VerMetasRequest extends StringRequest {
    private static String hostname = ConfiguracionActivity.hostname;
    private static String url = "http://"+hostname+"/checkgoals/controladores/ver_metas.php";
    private Map<String, String> parametros;
    int i;

    public VerMetasRequest(String correo, Response.Listener<String> listener){
        super(Method.POST, url, listener, null );
        parametros = new HashMap<>();
        parametros.put("correo", correo);

    }

    @Override
    public Map<String, String> getParams() {
        return parametros;
    }

}
