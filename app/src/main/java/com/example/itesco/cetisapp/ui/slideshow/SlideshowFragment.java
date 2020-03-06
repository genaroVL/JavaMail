package com.example.itesco.cetisapp.ui.slideshow;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itesco.cetisapp.Main3Activity;
import com.example.itesco.cetisapp.R;
import com.example.itesco.cetisapp.UsuariosAdapter;
import com.example.itesco.cetisapp.VolleySingleton;
import com.example.itesco.cetisapp.convenio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment   implements Response.Listener<JSONObject>,Response.ErrorListener{
    RecyclerView recyclerUsuarios;
    ArrayList<convenio> listaUsuarios;
    String usuario= Main3Activity.usuario;
    String especialidad=Main3Activity.especialidad;

    ProgressDialog progress;

    // RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        listaUsuarios=new ArrayList<>();

        recyclerUsuarios= (RecyclerView) root.findViewById(R.id.idRecycler);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerUsuarios.setHasFixedSize(true);

        // request= Volley.newRequestQueue(getContext());

        cargarWebService();
        return root;
    }

    private void cargarWebService() {

        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando...");
        progress.show();



       // String url="https://martinlanceroac.000webhostapp.com/obtenerConvenioP.php?user="+usuario;
        String url="https://martinlanceroac.000webhostapp.com/obtenerConvenio2.php?especialidad="+especialidad;
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        convenio convenio=null;

        JSONArray json=response.optJSONArray("convenio");

        try {

            for (int i=0;i<json.length();i++){
                convenio=new convenio();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                convenio.setNombre(jsonObject.optString("nombre"));
                convenio.setTelefono(jsonObject.optString("telefono"));
                convenio.setDescripcion(jsonObject.optString("descripcion"));
                listaUsuarios.add(convenio);
            }
            progress.dismiss();
            UsuariosAdapter adapter=new UsuariosAdapter(listaUsuarios);
            recyclerUsuarios.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            progress.dismiss();
        }

    }
}