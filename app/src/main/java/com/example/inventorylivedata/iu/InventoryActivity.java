package com.example.inventorylivedata.iu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.inventorylivedata.R;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

public class InventoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    NavController navController;
    private  int itemChecked = 0;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolBar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        setSupportActionBar(toolBar);

        //Tiene como base el grafico nav_graph y en nuestro caso no es correcto
        //AppBarConfiguration configuration = new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(drawerLayout).build();

        Set<Integer> topLevelDestinantions = new HashSet<>();
        topLevelDestinantions.add(R.id.listDependencyFragment);
        topLevelDestinantions.add(R.id.productFragment);
        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinantions).setOpenableLayout(drawerLayout).build();

        //Sin esta linea de codigo no se gestiona el evento Click del icono hamburguesa
        //NavigationUI.setupWithNavController(toolBar,navController,appBarConfiguration);

        //Gestiona que icono se visualiza
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);


       /* getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu_foreground);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }

    /*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    /**
     * En este metodo se recoge el evento del click en el icono hamburguesa y decimos explicitamente que navegue hacia arriba, segun el esquema appBarConfiguration y nav_graph
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController,appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.close();

        switch (item.getItemId()){
            case R.id.action_dependency:
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.listDependencyFragment);
                break;
                //itemChecked = 0;

            case R.id.action_section:
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.listSeccionesFragment);
                break;

            case R.id.action_settings:

                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.settingFragment);
                break;

            case R.id.action_product:
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.productFragment);
                break;
        }
        return true;
    }


    /*
    Opcion para gestionar nostros como programadores el elemento seleccionado del menu
    @Override
    protected void onResume() {
        super.onResume();
        if (itemChecked != -1)
            navigationView.getMenu().getItem(itemChecked).setChecked(true);
    }*/

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    /**
     * Este metodo sobreescribe la pulsacion del boton atras para que si el navigation drawer esta abierto lo cierre y sino haga la accion normal del boton
     */
    @Override
    public void onBackPressed() {
        if(drawerLayout.isOpen())
            drawerLayout.close();
        else
            super.onBackPressed();
    }
}