package com.example.itesco.cetisapp.ui.tools;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.itesco.cetisapp.R;

public class ToolsFragment extends Fragment {
ImageView facebook,llamada,twiter;
    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        facebook=(ImageView)root.findViewById(R.id.facebook);
        llamada=(ImageView)root.findViewById(R.id.llamada);
       // twiter=(ImageView)root.findViewById(R.id.twiter);
       /* twiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        llamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamadaCetis();
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paginadeFacebook();
            }
        });
        return root;
    }
    public void twiter(){

        String twiterID = "twitter://user?user_id=USERID";
        String urlPage = "https://twitter.com/PROFILENAME";
        try{
            // Get Twitter app
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twiterID )));

        } catch (Exception e) {
            // If no Twitter app found, open on browser
            Toast.makeText(getContext(), "Aplicacion no instalada", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
        }
    }
    public  void llamadaCetis(){
        Intent intent = new Intent(Intent.ACTION_DIAL);

        intent.setData(Uri.parse("tel:921-218-5524"));
        startActivity(intent);
    }
    public void paginadeFacebook(){

        String facebookId = "fb://page/112472699227923";
        String urlPage = "https://www.facebook.com/cetis79/";

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId )));
        } catch (Exception e) {
            Toast.makeText(getContext(), "Aplicacion de facebook no instalada, Redireccionando", Toast.LENGTH_SHORT).show();
            //Abre url de pagina.
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
        }

    }
}