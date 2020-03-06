package com.example.itesco.cetisapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText campoNc,campoNombre,campoNip,campoApellidoP,campoApellidoM,campoEspecialidad,campoEmail;
    Button botonRegistro,btnCancelar;
    ProgressDialog progreso;

    String conducta;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    private OnFragmentInteractionListener mListener;

    public RegistroFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_registro, container, false);

        campoNc=(EditText) view.findViewById(R.id.txtNc);
        campoNip=(EditText) view.findViewById(R.id.txtNip);

        campoApellidoP=(EditText)view.findViewById(R.id.txtApellidoP);
        campoApellidoM=(EditText) view.findViewById(R.id.txtApellidoM);
        campoNombre= (EditText) view.findViewById(R.id.txtNombre);
        campoEspecialidad=(EditText)view.findViewById(R.id.especialidad);
        campoEmail=(EditText)view.findViewById(R.id.email);


        botonRegistro=(Button) view.findViewById(R.id.aceptarRegistro);
        btnCancelar=(Button)view.findViewById(R.id.cancelarReg);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nuevoFragmento = new SesionFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, nuevoFragmento);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        request= Volley.newRequestQueue(getContext());

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(campoNombre.getText().toString().isEmpty() || campoNip.getText().toString().isEmpty() || campoNombre.getText().toString().isEmpty() || campoEspecialidad.getText().toString().isEmpty() || campoEmail.getText().toString().isEmpty() ){
                    Toast.makeText(getContext(),"Debes llenar todos los campos", Toast.LENGTH_LONG).show();
                }
                else {
                    cargarWebServies();
                }
            }
        });
        return view;
    }

    private void cargarWebServies(){
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();

        String url="https://martinlanceroac.000webhostapp.com/registro2.php?nc="+campoNc.getText().toString()+
                "&nip="+campoNip.getText().toString()+ "&nombre="+campoNombre.getText().toString()+
                "&apellidoP="+campoApellidoP.getText().toString()+"&apellidoM="+campoApellidoM.getText().toString()+ "&especialidad="+campoEspecialidad.getText().toString()+ "&email="+campoEmail.getText().toString()+ "&nivel=0";
        url.replace(" ","%20");
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }



    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(),"Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
        progreso.dismiss();


        campoNombre.setText("");
        campoNc.setText("");
        campoNip.setText("");
        campoApellidoM.setText("");
        campoApellidoP.setText("");
        Fragment nuevoFragmento = new SesionFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, nuevoFragmento);
        transaction.addToBackStack(null);

        transaction.commit();


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.dismiss();
        Toast.makeText(getContext(),"Numero de control ya registrado", Toast.LENGTH_SHORT).show();
        Log.i("error:",error.toString());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
