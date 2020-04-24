package com.example.bolirepeat;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.bolirepeat.test.InputFragment;
import com.example.bolirepeat.test.OnFragmentChangeListener;

import java.util.List;

public class Adaptor extends RecyclerView.Adapter<ViewHolder> {
    List<Data> list;
    Context context;
    OnFragmentChangeListener onFragmentChangeListener;


    public Adaptor(List<Data> list, Context context,OnFragmentChangeListener onFragmentChangeListener) {
        this.context=context;
        this.list = list;
        this.onFragmentChangeListener=onFragmentChangeListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(list.get(position).title);
        holder.note.setText(list.get(position).note);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Delete().execute(list.get(position));
                list.remove(position);
                notifyItemRemoved(position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFragmentChangeListener.OnfragmentChange(new InputFragment(list.get(position).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Delete extends AsyncTask<Data,Void,Void>{

    @Override
    protected Void doInBackground(Data... data) {
        Database database= Room.databaseBuilder(context,Database.class,"Data").build();
        database.dataDao().delete(data[0]);
        return null;
    }

}

}

    class ViewHolder extends RecyclerView.ViewHolder{
TextView title,note;
ImageView img;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        title=itemView.findViewById(R.id.title);
        note=itemView.findViewById(R.id.note);
        img=itemView.findViewById(R.id.img);
    }
}
