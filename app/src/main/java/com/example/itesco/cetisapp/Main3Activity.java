package com.example.itesco.cetisapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itesco.cetisapp.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main3Activity extends AppCompatActivity implements registroFechaFragment.OnFragmentInteractionListener,informacionFragment.OnFragmentInteractionListener {
    Menu menu;
    private AppBarConfiguration mAppBarConfiguration;
    static  public String usuario;
    static public boolean actualizar=true;
    static public String especialidad="";
    static public String nombre="";
    static public String correo="";
    static public String nivel="";

    static MenuItem  perfil,calendario,documentos,convenio,contacto,informacion;
   static  MenuItem  agregarFecha,agregarDocumentos,agregarConvenio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = findViewById(R.id.toolbar);

        final Bundle bundle = getIntent().getExtras();

        usuario = bundle.getString("user");



        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        
        menu=navigationView.getMenu();

        perfil=menu.findItem(R.id.nav_home);
        calendario=menu.findItem(R.id.nav_gallery);
        documentos=menu.findItem(R.id.nav_share);
        convenio=menu.findItem(R.id.nav_slideshow);
        contacto=menu.findItem(R.id.nav_tools);
        informacion=menu.findItem(R.id.nav_informacion);

        agregarFecha=menu.findItem(R.id.nav_addDate);
        agregarDocumentos=menu.findItem(R.id.nav_send);
        agregarConvenio=menu.findItem(R.id.nav_addConvenio);
        MenuItem menuItem=menu.findItem(R.id.nav_tools2);



        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                actualizar=true;
                System.exit(0);
              finish();

                return false;
            }
        });



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send,R.id.nav_addDate,R.id.nav_addConvenio,R.id.nav_configuracion,R.id.nav_informacion)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }



public static void administrador(){


    agregarConvenio.setVisible(true);
    agregarDocumentos.setVisible(true);
    agregarFecha.setVisible(true);

        calendario.setVisible(false);
        documentos.setVisible(false);
        informacion.setVisible(false);
        convenio.setVisible(false);
        contacto.setVisible(false);
}
public  static void alumno(){
        agregarConvenio.setVisible(false);
        agregarDocumentos.setVisible(false);
        agregarFecha.setVisible(false);

    calendario.setVisible(true);
    documentos.setVisible(true);
    informacion.setVisible(true);
    convenio.setVisible(true);
    contacto.setVisible(true);

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
        
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
