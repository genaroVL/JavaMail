package com.example.itesco.cetisapp.ui.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.itesco.cetisapp.R;
import com.example.itesco.cetisapp.uploadPDF;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ShareFragment extends Fragment {
    ListView myPDFListView;
    DatabaseReference databaseReference;
    List<com.example.itesco.cetisapp.uploadPDF> uploadPDFS;
    FirebaseListAdapter adapter;
    uploadPDF uploadPDF;

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);



        Query query= FirebaseDatabase.getInstance().getReference().child("uploads");

        myPDFListView=(ListView)root.findViewById(R.id.myListView);
        FirebaseListOptions<uploadPDF> options=new FirebaseListOptions.Builder<uploadPDF>()
                .setLayout(R.layout.document)
                .setQuery(query,uploadPDF.class).build();
        adapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {

                TextView name=v.findViewById(R.id.name);
                TextView descripcion=v.findViewById(R.id.descripcion);

                uploadPDF =(uploadPDF) model;
                uploadPDFS.add(uploadPDF);
                name.setText("Archivo:"+uploadPDF.getName().toString());
                descripcion.setText("Descripcion: "+uploadPDF.getnDescripcion());





            }
        };
        myPDFListView.setAdapter(adapter);







        uploadPDFS=new ArrayList<>();
/*

        viewAllFiles();


*/

        myPDFListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uploadPDF uploadPDF =uploadPDFS.get(position);


                Intent intent = new Intent(Intent.ACTION_VIEW);
                if (uploadPDF.getUrl().contains(".doc") || uploadPDF.getUrl().contains(".docx")) {
                    // Word document
                   intent.setData(Uri.parse(uploadPDF.getUrl()));
                    startActivity(intent);



                } else if(uploadPDF.getUrl().contains(".pdf")) {
                    // PDF file
                    intent.setDataAndType(Uri.parse(uploadPDF.getUrl()), "application/pdf");
                    startActivity(intent);
                }
                /*Intent intent =new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uploadPDF.getUrl()));
                startActivity(intent);*/

            }
        });

        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}