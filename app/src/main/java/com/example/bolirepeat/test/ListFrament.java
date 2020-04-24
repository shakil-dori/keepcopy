package com.example.bolirepeat.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bolirepeat.Adaptor;
import com.example.bolirepeat.Data;
import com.example.bolirepeat.Database;
import com.example.bolirepeat.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

//main activity te to ijjmplement korsi e

public class ListFrament extends Fragment {
    RecyclerView recyclerView;
    EditText searchView;
    Context context;
    Button searchbtn,layoutbtn;
    List<Data> list;
    Database database;
    Adaptor adaptor;
    FloatingActionButton fab;
    Fragment fragment;
    int id,n;
    SharedPreferences sharedPreferences;

    //ok r kothao maybe lage nai fragment..fragment valoi..
    //ata main activity theke astase
    //ohh.na
    OnFragmentChangeListener onFragmentChangeListener;

    public ListFrament(OnFragmentChangeListener onFragmentChangeListener) {
        this.onFragmentChangeListener = onFragmentChangeListener;

    }

    //constractor bana onfragment disa
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_frament, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView=view.findViewById(R.id.searchviewid);
        searchbtn=view.findViewById(R.id.searchbtn);
        layoutbtn=view.findViewById(R.id.layoutbtn);
        fab=view.findViewById(R.id.fab);
        recyclerView=view.findViewById(R.id.recyclerviewxml);
        context=getContext();
        if(context!=null){
        database= Room.databaseBuilder(context,Database.class,"Data").build();}
        list=new ArrayList<>();
        adaptor=new Adaptor(list,context,onFragmentChangeListener);

//         n=sharedPreferences.getInt("layout",0);
        //1 r 2 same, 1 default, ai jonno perameter deya lage na. kor ala ki ki korbi
        //1 recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //ata vertical// ko are hala dara
        //2 recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        //ata hbe horizontal
        // recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        sharedPreferences=getActivity().getSharedPreferences("layout",0);
         n=sharedPreferences.getInt("layout",0);


        if(n==0) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        }
        layoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(n==0){
                    Toast.makeText(context, "first", Toast.LENGTH_SHORT).show();
                     recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                    n=1;
                     layout(n);

                }else {

                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                    n=0;
                    layout(n);
                    Toast.makeText(context,"2222222222",Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView.setAdapter(adaptor);
        list.add(new Data("shakil","dori")) ;
        adaptor.notifyDataSetChanged();
        if(database!=null) {
            new GetDataTask().execute();
        }
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search= searchView.getText().toString();
                new GetDataTask().execute(search);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=-1;
              fragment =new InputFragment(id);
               onFragmentChangeListener.OnfragmentChange(fragment);
            }
        });
    }

    public void layout(int n){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("layout",n);
        editor.commit();

    }

    class GetDataTask extends AsyncTask<String ,Void,List<Data>> {
        @Override
        protected List<Data> doInBackground(String... strings) {
            if (strings.length ==0 ) {
                return database.dataDao().getalldata();
            } else {
                return database.dataDao().searchdata(strings[0]);
            }
        }
        @Override
        protected void onPostExecute(List<Data> data) {
            super.onPostExecute(data);
            list.clear();
            list.addAll(data);
            adaptor.notifyDataSetChanged();
        }
    }
}
