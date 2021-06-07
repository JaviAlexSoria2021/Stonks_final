package com.bryansoria.socialappv4.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bryansoria.socialappv4.Model.Game;
import com.bryansoria.socialappv4.R;

import java.util.ArrayList;


public class AdapterGame extends RecyclerView.Adapter<AdapterGame.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<Game>model;

    //Listener
    private View.OnClickListener listener;

    public AdapterGame(Context context, ArrayList<Game> model){
      this.inflater=LayoutInflater.from(context);
      this.model=model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.cardviewjuegos, parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);

    }

    public void setOnClickLister(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String tit = model.get(position).getName();
        String gender =model.get(position).getGender();
        String aut = model.get(position).getDeveloper();
        int img = model.get(position).getImg();
        holder.titulo.setText(tit);
        holder.autor.setText(aut);
        holder.genero.setText(gender);
        holder.img.setImageResource(img);

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public void startListening() {
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo, genero, autor;


            ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo =itemView.findViewById(R.id.tituloBiblioteca);
            genero=itemView.findViewById(R.id.generosBiblioteca);
            autor=itemView.findViewById(R.id.autorBiblioteca);
            img= itemView.findViewById(R.id.portada_biblioteca);

        }

    }


}
