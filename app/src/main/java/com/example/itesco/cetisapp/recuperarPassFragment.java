package com.example.itesco.cetisapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itesco.cetisapp.ui.home.HomeViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import java.util.Properties;

import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link recuperarPassFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link recuperarPassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recuperarPassFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText email,matricula;
    Button recuperar;

    ProgressDialog progress;


    JSONArray json;
    JSONObject jsonObject;
    JsonObjectRequest jsonObjectRequest;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private OnFragmentInteractionListener mListener;

    public recuperarPassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recuperarPassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static recuperarPassFragment newInstance(String param1, String param2) {
        recuperarPassFragment fragment = new recuperarPassFragment();
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
        View view= inflater.inflate(R.layout.fragment_recuperar_pass, container, false);
        email=(EditText)view.findViewById(R.id.email);
        matricula=(EditText)view.findViewById(R.id.matricula);

        recuperar=(Button)view.findViewById(R.id.recuperar);
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().isEmpty() || matricula.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Debe proporcionar todos los datos para recuperar su contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    progress=new ProgressDialog(getContext());
                    progress.show();
                    progress.setCancelable(false);
                    progress.setTitle("Espere un momento");
                    progress.setMessage("Comprobando datos...");
                    cargarWebService();
                }

            }
        });
        return view;
    }
    private void cargarWebService() {



        String url="https://martinlanceroac.000webhostapp.com/consultarPass.php?user="+matricula.getText().toString()+"&correo="+email.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    private void sendMail(String nombre,String ap,String am,String p ,String matricula){
        progress.dismiss();
        String mail = email.getText().toString().trim();
        String message="Hola "+nombre+" "+ap+" "+am+" Hemos recibido y corroborado de que sus datos han sido correctos," +
                " la contraseña asignada al numero de matricula: "+matricula+" es: "+p+", Le recomendamos que por seguridad guarde su contraseña , posteriormente borre este mensaje";




        String subject = "Recuperacion de contraseña";

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(getContext(),mail,subject,message);

        javaMailAPI.execute();

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
            usuario.setNivel(jsonObject.optString("correcto"));
            usuario.setApellidoP(jsonObject.optString("apellidoP"));
            usuario.setApellidoM(jsonObject.optString("apellidoM"));
            usuario.setEspecialidad(jsonObject.optString("especialidad"));
            usuario.setEspecialidad(jsonObject.optString("contraseña"));
            usuario.setNumeroControl(jsonObject.optString("user"));

            if(usuario.getNivel().equals("no")){
                Toast.makeText(getContext(), "Los datos proprocionados no son correctos", Toast.LENGTH_LONG).show();

            }else{
            sendMail(usuario.getNombre(),usuario.getApellidoP(),usuario.getApellidoM(),usuario.getEspecialidad(),usuario.getNumeroControl());
            email.setText("");
            matricula.setText("");
            }
            progress.dismiss();
            Fragment nuevoFragmento = new SesionFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, nuevoFragmento);
            transaction.addToBackStack(null);
            transaction.commit();

        } catch (JSONException e) {
            progress.dismiss();
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " " + response, Toast.LENGTH_LONG).show();

        }
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
