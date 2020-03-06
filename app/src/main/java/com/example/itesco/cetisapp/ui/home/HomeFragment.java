package com.example.itesco.cetisapp.ui.home;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.itesco.cetisapp.Main3Activity;
import com.example.itesco.cetisapp.R;
import com.example.itesco.cetisapp.User;
import com.example.itesco.cetisapp.VolleySingleton;
import com.example.itesco.cetisapp.mensaje;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    TextView nombre,especialidad,correo;
    TextView EoPuesto;
    ProgressDialog progress;
    private HomeViewModel homeViewModel;
    String usuario;
    JSONArray  json;
    JSONObject jsonObject;
    JsonObjectRequest jsonObjectRequest;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        usuario=Main3Activity.usuario;


        nombre=(TextView)root.findViewById(R.id.name);
        especialidad=(TextView)root.findViewById(R.id.especialidad);
        correo=(TextView)root.findViewById(R.id.email);
        EoPuesto=(TextView)root.findViewById(R.id.EoPuesto);

        if(Main3Activity.actualizar) {
            SpannableString ss2=  new SpannableString(mensaje.s);
            ss2.setSpan(new RelativeSizeSpan(1.5f), 0, ss2.length(), 0);
            progress=new ProgressDialog(getActivity());
            progress.setCancelable(false);
            progress.setMessage(ss2);
            progress.show();
            new Thread(new Runnable() {
                @Override
                public void run() {




                    cargarWebService();
                    Main3Activity.actualizar = false;
                }
            }).start();
        }else{
            ponerDatos();
        }
        return root;
    }
public void ponerDatos(){
        especialidad.setText(Main3Activity.especialidad);
        nombre.setText(Main3Activity.nombre);
        correo.setText(Main3Activity.correo);
}
    private void cargarWebService() {



        String url="https://martinlanceroac.000webhostapp.com/consultarDatos.php?user="+usuario;

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

        User usuario=new User();

        json=response.optJSONArray("datos");

        try {



                jsonObject = null;
                jsonObject = json.getJSONObject(0);

                usuario.setNombre(jsonObject.optString("nombre"));
                usuario.setApellidoP(jsonObject.optString("apellidoP"));
                usuario.setApellidoM(jsonObject.optString("apellidoM"));
                usuario.setEspecialidad(jsonObject.optString("especialidad"));
                usuario.setCorreo(jsonObject.optString("correo"));
                usuario.setNivel(jsonObject.optString("nivel"));
             String nombreU=" "+usuario.getNombre()+" "+usuario.getApellidoP()+" "+usuario.getApellidoM();
                nombre.setText(nombreU);
                especialidad.setText(usuario.getEspecialidad());
                correo.setText(usuario.getCorreo());
                progress.dismiss();
            if(usuario.getNivel().equals("1")){

            Main3Activity.administrador();
            EoPuesto.setText("Puesto: ");
            }else{
                EoPuesto.setText("Especialidad: ");
                Main3Activity.alumno();
            }
            Main3Activity.especialidad=usuario.getEspecialidad();
            Main3Activity.nombre=nombreU;
            Main3Activity.correo=usuario.getCorreo();


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " " + response, Toast.LENGTH_LONG).show();

        }
    }
}