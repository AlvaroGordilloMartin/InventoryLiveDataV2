package com.example.inventorylivedata.iu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorylivedata.R;
import com.example.inventorylivedata.data.model.Dependency;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.Collections;
import java.util.List;

public class DependencyAdapter extends RecyclerView.Adapter<DependencyAdapter.ViewHolder> {

    private List<Dependency> list;
    private OnDepedencyClickListener depedencyListener;


    public interface OnDepedencyClickListener extends  View.OnClickListener,View.OnLongClickListener{
        void onClick(View v);

        boolean onLongClick(View v);

    }

    public DependencyAdapter(List<Dependency> list) {
        this.list = list;
    }

    public DependencyAdapter(List<Dependency> list, OnDepedencyClickListener depedencyListener) {
        this.list = list;
        this.depedencyListener = depedencyListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dependency, parent, false);
        view.setOnClickListener(depedencyListener);
        view.setOnLongClickListener(depedencyListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.iconLetter.setLetter(list.get(position).getShortname());
        holder.tvShortName.setText(list.get(position).getName());

    }

    //IMPORTANTE DEVOLVER EL LIST.SIZE PARA EVITAR ERRORES
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Método que actualiza los datos del RecyclerView y se debe llamar al método
     * notifyDataSetChanged() para que la vista se anule y se vuelva a dibujar
     */
    public void update(List<Dependency> list) {
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }


    public void delete(Dependency deleted) {
        list.remove(deleted);
        this.notifyDataSetChanged();
    }


    public void add(Dependency deleted) {
        list.add(deleted);
        Collections.sort(list);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        MaterialLetterIcon iconLetter;
        TextView tvShortName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconLetter = itemView.findViewById(R.id.iconLetter);
            tvShortName = itemView.findViewById(R.id.tvShortName);
        }

    }
    /**
     * Método que devuelve el usuario de una posición
     * @param position
     * @return
     */
    public Dependency getItem(int position){
        return list.get(position);
    }
}
