package com.cotosanchez.checkgoals;

import android.widget.AutoCompleteTextView;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stalincj on 26/06/2016.
 */
public class LoginRequest extends StringRequest{
    private static String hostname = ConfiguracionActivity.hostname;
    private static String url = "http://"+hostname+"/checkgoals/controladores/login.php";
    private Map<String, String> parametros;

    public LoginRequest(String correo, String clave, Response.Listener<String> listener){
        super(Method.POST, url, listener, null );
        parametros = new HashMap<>();
        parametros.put("correo", correo);
        parametros.put("clave", clave);
    }

    @Override
    public Map<String, String> getParams() {
        return parametros;
    }

}
