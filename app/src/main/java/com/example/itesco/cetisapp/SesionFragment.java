package com.example.itesco.cetisapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SesionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SesionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SesionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    RequestQueue rq;
    JsonRequest jrq;
    EditText cajaUser,cajaPwd;
    TextView registro;
    Button btmConsultar;
    ProgressDialog progress;
    JSONArray ja;
    String usuario1;
    TextView recuperar;

    private OnFragmentInteractionListener mListener;

    public SesionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SesionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SesionFragment newInstance(String param1, String param2) {
        SesionFragment fragment = new SesionFragment();
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
      View vista= inflater.inflate(R.layout.fragment_sesion, container, false);
        cajaUser=(EditText) vista.findViewById(R.id.txtUser);
        cajaPwd=(EditText) vista.findViewById(R.id.txtPass);
        btmConsultar=(Button)vista.findViewById(R.id.boton);
        registro=(TextView)vista.findViewById(R.id.registro);
        progress=new ProgressDialog(getActivity());
        recuperar=(TextView)vista.findViewById(R.id.recup);

        SpannableString ss2=  new SpannableString(mensaje.s);
        ss2.setSpan(new RelativeSizeSpan(1.5f), 0, ss2.length(), 0);
        progress.setCancelable(false);

        progress.setMessage(ss2);
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment nuevoFragmento = new recuperarPassFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, nuevoFragmento);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nuevoFragmento = new RegistroFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, nuevoFragmento);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });
        rq= Volley.newRequestQueue(getContext());


        btmConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btmConsultar.setEnabled(false);
                if(cajaUser.getText().toString().isEmpty() || cajaPwd.getText().toString().isEmpty()){

                    Toast.makeText(getContext(),"Debe llenar todos los campos",Toast.LENGTH_SHORT).show();
                    btmConsultar.setEnabled(true);
                }
                else {
                    ConsultaPass("https://martinlanceroac.000webhostapp.com/consultarusuario2.php?user="+cajaUser.getText().toString());

                }

            }
        });

      return vista;
    }
    private void ConsultaPass(String URL) {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        progress.show();
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    User usuario=new User();
                    ja = new JSONArray(response);
                    String contra = ja.getString(0);

                    if(contra.equals(cajaPwd.getText().toString()) ){
                        usuario.setNumeroControl(cajaUser.getText().toString() );

                        String usuario12=cajaUser.getText().toString();
                        Intent intencion= new Intent(getContext(),Main3Activity.class);
                        intencion.putExtra("user",usuario12);

                        startActivity(intencion);
                        progress.dismiss();
                        getActivity().finish();



                    }else{
                        Toast.makeText(getContext(),"verifique su contraseña",Toast.LENGTH_SHORT).show();
                        btmConsultar.setEnabled(true);
                        progress.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progress.dismiss();
                    btmConsultar.setEnabled(true);
                    Toast.makeText(getContext(),"El numero de control no existe en la base de datos",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btmConsultar.setEnabled(true);

            }
        });

        queue.add(stringRequest);



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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
