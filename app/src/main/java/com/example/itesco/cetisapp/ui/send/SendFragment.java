package com.example.itesco.cetisapp.ui.send;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.provider.DocumentFile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.itesco.cetisapp.R;

import com.example.itesco.cetisapp.uploadPDF;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    Button btn_upload;
    EditText editPDFName;
    EditText descripcion;


    StorageReference storageReference;
    DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        editPDFName=(EditText)root.findViewById(R.id.txt_pdfName);
        btn_upload=(Button)root.findViewById(R.id.upload);
        descripcion=(EditText)root.findViewById(R.id.descripcion);

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!descripcion.getText().toString().isEmpty()&& !editPDFName.getText().toString().isEmpty())
                selectPDFFile();
                else
                    Toast.makeText(getContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        });



        return root;
    }
    private void selectPDFFile(){

        Intent intent =new Intent();

        intent.setType("*/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleccione un archivo"),1);


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 &&
                data !=null && data.getData()!=null){
            uploadPDFFile(data.getData());


        }
    }
    private void uploadPDFFile(Uri data) {

        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Cargando...");
        progressDialog.show();


       final DocumentFile documentFile= DocumentFile.fromSingleUri(getContext(),data);



        StorageReference reference=storageReference.child("uploads/"+documentFile.getName());
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Uri url=uri.getResult();

                        uploadPDF uploadPDF=new uploadPDF(editPDFName.getText().toString(),url.toString(),descripcion.getText().toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(uploadPDF);
                        Toast.makeText(getContext(), "¡¡Archivo "+documentFile.getName()+" subido con exito!!", Toast.LENGTH_SHORT).show();
                        editPDFName.getText().clear();
                        descripcion.getText().clear();

                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Subiendo: "+(int)progress+"%");

            }
        });
    }

}