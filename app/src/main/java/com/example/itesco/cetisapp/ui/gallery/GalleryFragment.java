package com.example.itesco.cetisapp.ui.gallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itesco.cetisapp.HomeCollection;
import com.example.itesco.cetisapp.HwAdapter;
import com.example.itesco.cetisapp.R;
import com.example.itesco.cetisapp.VolleySingleton;
import com.example.itesco.cetisapp.fechaC;
import com.example.itesco.cetisapp.mensaje;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class GalleryFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    private GalleryViewModel galleryViewModel;
    public GregorianCalendar cal_month, cal_month_copy;
    private HwAdapter hwAdapter;
    private TextView tv_month,tv_year;

    private String mes,año="";
    private boolean band=false;



    RecyclerView recyclerUsuarios;

    fechaC fechac;
    JSONArray json;
    JSONObject jsonObject;
    ProgressDialog progress;

    // RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        HomeCollection.date_collection_arr=new ArrayList<HomeCollection>();






        progress=new ProgressDialog(getActivity());
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        hwAdapter = new HwAdapter(getActivity(), cal_month,HomeCollection.date_collection_arr);
        tv_year=(TextView)root.findViewById(R.id.tv_year);
        tv_month = (TextView) root.findViewById(R.id.tv_month);

        español();
        tv_year.setText(mes);

        tv_month.setText(android.text.format.DateFormat.format("yyyy", cal_month));

        SpannableString ss2=  new SpannableString(mensaje.s);
        ss2.setSpan(new RelativeSizeSpan(1.5f), 0, ss2.length(), 0);
        progress.setMessage(ss2);
        progress.show();
        cargarWebService();



        ImageButton previous = (ImageButton) root.findViewById(R.id.ib_prev);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 4&&cal_month.get(GregorianCalendar.YEAR)==2017) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
                    //   Toast.makeText(Main6Activity.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setPreviousMonth();
                    refreshCalendar();
                }


            }
        });
        ImageButton next = (ImageButton) root.findViewById(R.id.Ib_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 5&&cal_month.get(GregorianCalendar.YEAR)==2018) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
                    //    Toast.makeText(Main6Activity.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setNextMonth();
                    refreshCalendar();
                }
            }
        });
        GridView gridview = (GridView) root.findViewById(R.id.gv_calendar);
        gridview.setAdapter(hwAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedGridDate = HwAdapter.day_string.get(position);

                ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, getActivity());
                if(HomeCollection.date_collection_arr==null){

                    band=false;
                }else{
                    for(int i=0;i<HomeCollection.date_collection_arr.size();i++){


                        if(HomeCollection.date_collection_arr.get(i).date.equals(selectedGridDate)){
                            Toast.makeText(getContext(),"se encontro evento",Toast.LENGTH_LONG).show();
                            band=true;

                        }


                    }

                }

                if(band==false){
                    Toast.makeText(getContext(),"No se encontro evento",Toast.LENGTH_LONG).show();

                }

                band=false;

            }

        });
        return root;
    }
    private void cargarWebService() {
/*
        progress=new ProgressDialog(getApplicationContext());
        progress.setMessage("Cargando...");
        progress.show();*/



        String url="https://martinlanceroac.000webhostapp.com/obtenerCalendario2.php";

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }


    public void español(){
        if (cal_month.get(GregorianCalendar.MONTH) == 0) {
            mes="Enero ";
        }
        if (cal_month.get(GregorianCalendar.MONTH) == 1) {
            mes="Febrero ";
        }
        if (cal_month.get(GregorianCalendar.MONTH) == 2) {
            mes="Marzo ";
        }
        if (cal_month.get(GregorianCalendar.MONTH) == 3) {
            mes="Abril ";
        }
        if (cal_month.get(GregorianCalendar.MONTH) == 4) {
            mes="Mayo ";
        }
        if (cal_month.get(GregorianCalendar.MONTH) == 5) {
            mes="Junio ";
        }
        if (cal_month.get(GregorianCalendar.MONTH) == 6) {
            mes="Julio ";
        }
        if (cal_month.get(GregorianCalendar.MONTH) == 7) {
            mes="Agosto ";
        }
        if (cal_month.get(GregorianCalendar.MONTH) == 8) {
            mes="Septiembre ";
        }
        if (cal_month.get(GregorianCalendar.MONTH) == 9) {
            mes="Octubre ";
        }
        if (cal_month.get(GregorianCalendar.MONTH) == 10) {
            mes="Noviembre ";
        }
        if (cal_month.get(GregorianCalendar.MONTH) == 11) {
            mes="Diciembre ";
        }


    }




    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    public void refreshCalendar() {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
        español();
        tv_year.setText(mes);

        tv_month.setText(android.text.format.DateFormat.format("yyyy", cal_month));
    }



    @Override
    public void onErrorResponse(VolleyError error) {
        progress.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {
        fechac=null;

        json=response.optJSONArray("calendario");

        try {

            for (int i = 0; i < json.length(); i++) {
                fechac = new fechaC();
                jsonObject = null;
                jsonObject = json.getJSONObject(i);

                fechac.setFecha(jsonObject.optString("fecha"));
                fechac.setTitulo(jsonObject.optString("titulo"));
                fechac.setSubtitulo(" ");
                fechac.setContenido(jsonObject.optString("contenido"));


                HomeCollection.date_collection_arr.add( new HomeCollection(fechac.getFecha(),fechac.getTitulo(),fechac.getSubtitulo(),fechac.getContenido()));


            }
            refreshCalendar();

            progress.dismiss();


        } catch (JSONException e) {
            e.printStackTrace();
            progress.dismiss();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " " + response, Toast.LENGTH_LONG).show();

        }
        // progress.hide();
    }
}