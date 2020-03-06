package com.example.itesco.cetisapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class updateFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {



    EditText correo;
    TextView matricula,especialidad;
    Button actualizar;


    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;



    public updateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_update, container, false);
       correo=(EditText)view.findViewById(R.id.correo);
       matricula=(TextView)view.findViewById(R.id.matricula);
       especialidad=(TextView)view.findViewById(R.id.especialidad);
       actualizar=(Button)view.findViewById(R.id.actualizar);
        request= Volley.newRequestQueue(getContext());
       actualizar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(!correo.getText().toString().isEmpty()){
                   cargarWebService();
               }else
                   Toast.makeText(getContext(), "No puede dejar el campo bacio", Toast.LENGTH_SHORT).show();
           }
       });

       matricula.setText(Main3Activity.usuario);
       especialidad.setText(Main3Activity.especialidad);
       correo.setHint(Main3Activity.correo);

       return view;
    }
    private void cargarWebService() {

        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();

        String url="https://martinlanceroac.000webhostapp.com/updateDatos.php?user="+matricula.getText().toString()+
                "&correo="+correo.getText().toString();
        url.replace(" ","%20");
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);


    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(getContext(),"Se ha actualizado correctamente", Toast.LENGTH_SHORT).show();
        Main3Activity.correo=correo.getText().toString();
        progreso.dismiss();
    }
}
