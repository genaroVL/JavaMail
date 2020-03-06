package com.example.itesco.cetisapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class addConvenioFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
EditText telefono,nombre,descripcion,especialidad;
Button registrar,limpiar;
    JSONObject jsonObject;
    String correcto;
ProgressDialog progreso;
    JSONArray json;

JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    public addConvenioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View view= inflater.inflate(R.layout.fragment_add_convenio, container, false);

       telefono=(EditText)view.findViewById(R.id.telefonoIns);
       nombre=(EditText)view.findViewById(R.id.nombreIns);
       descripcion=(EditText)view.findViewById(R.id.descripcionIns);
       registrar=(Button)view.findViewById(R.id.registrar);
       limpiar=(Button)view.findViewById(R.id.limpiar);
       especialidad=(EditText)view.findViewById(R.id.especialidadIns);


       limpiar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               limpiarAll();
           }
       });


       registrar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(telefono.getText().toString().isEmpty()||nombre.getText().toString().isEmpty() ||descripcion.getText().toString().isEmpty() || especialidad.getText().toString().isEmpty()){
                   Toast.makeText(getContext(), "No puede dejar campos vacios", Toast.LENGTH_SHORT).show();
               }else{




                   cargarWebServies();
               }
           }
       });
        request= Volley.newRequestQueue(getContext());
       return view;
    }
    public void limpiarAll(){
        especialidad.getText().clear();
        nombre.getText().clear();
        descripcion.getText().clear();
        telefono.getText().clear();
    }

    private void cargarWebServies(){

        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.setCancelable(false);
        progreso.show();





        String url="https://martinlanceroac.000webhostapp.com/registroConvenio2.php?nombre="+nombre.getText().toString()+
                "&descripcion="+descripcion.getText().toString()+ "&telefono="+telefono.getText().toString()+ "&especialidad="+especialidad.getText().toString();
        url.replace(" ","%20");
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.dismiss();
        System.out.println(error.getMessage());
        Toast.makeText(getContext(),"No se pudo registrar Correctamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        json=response.optJSONArray("datos");
        jsonObject = null;
        try {
            jsonObject = json.getJSONObject(0);
            correcto=jsonObject.optString("disponible");
            if(correcto.equals("no")) {
                Toast.makeText(getContext(), "Nombre de convenio ya registrado", Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getContext(), "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                limpiarAll();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        progreso.dismiss();
    }
}
