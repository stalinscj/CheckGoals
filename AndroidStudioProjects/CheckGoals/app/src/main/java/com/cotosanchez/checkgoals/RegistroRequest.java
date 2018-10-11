package com.cotosanchez.checkgoals;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stalincj on 25/06/2016.
 */
public class RegistroRequest extends StringRequest{
    private static String hostname = ConfiguracionActivity.hostname;
    private static String url = "http://"+hostname+"/checkgoals/controladores/registrarse.php";
    private Map<String, String> parametros;

    public RegistroRequest(String nombre, String apellido, int anioNacimiento, String correo, String clave, Response.Listener<String> listener){
        super(Method.POST, url, listener, null );
        parametros = new HashMap<>();
        parametros.put("nombre", nombre);
        parametros.put("apellido", apellido);
        parametros.put("anioNacimiento", anioNacimiento+"");
        parametros.put("correo", correo);
        parametros.put("clave", clave);
    }

    @Override
    public Map<String, String> getParams() {
        return parametros;
    }

}
