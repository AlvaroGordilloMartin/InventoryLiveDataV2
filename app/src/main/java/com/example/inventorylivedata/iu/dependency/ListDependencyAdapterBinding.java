package com.example.inventorylivedata.iu.dependency;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.example.inventorylivedata.data.model.Dependency;

import java.util.List;

public class ListDependencyAdapterBinding {

    @BindingAdapter("app:emptyState")
    public static void show(View v, boolean show) {
        v.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    
}
