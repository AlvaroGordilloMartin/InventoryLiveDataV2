package com.example.inventorylivedata.iu.producto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.inventorylivedata.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProductFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView = view.findViewById(R.id.bottomNavigation);
        initialice();
    }

    /**
     * Metodo que inicializa el componente BottomNaviugationView y su listener
     */
    private void initialice() {
        //Cargar el primer fragment
        listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                switch (item.getItemId()){
                    case R.id.action_info:
                        fragment = ProductInfoFragment.newInstance(null);
                        break;
                    case R.id.action_map:
                        fragment = ProductMapFragment.newInstance(null);
                        break;
                    case R.id.action_description:
                        fragment = ProductDescriptionFragment.newInstance(null);
                        break;
                }
                return loadManager(fragment);
            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        bottomNavigationView.setSelectedItemId(R.id.action_info);

    }

    /**
     * Se utiliza el metodo getChildFragmentManager para obtener un FragmentManager privado o interno al fragment de forma que es el que va a gestionar o administrar los Fragments dentro de otro fragment
     * Info, Mapa y Descripcion dependen del fragment product
     * No son accesibles desde el menu de NavigationDrawer, por tanto no estan en el grafico del componente Navigation
     * @param fragment
     * @return
     */
    private boolean loadManager(Fragment fragment) {

        if(fragment!=null){
            getChildFragmentManager().beginTransaction().replace(R.id.content_fragment,fragment).commit();
            return true;
        }

        return false;
    }
}
