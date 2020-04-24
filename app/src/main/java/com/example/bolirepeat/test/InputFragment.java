package com.example.bolirepeat.test;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bolirepeat.Data;
import com.example.bolirepeat.Database;
import com.example.bolirepeat.R;

import java.util.Date;

public class InputFragment extends Fragment {
    Database database;
    EditText edittexttitleid, edittextnoteid;
    Button savebtnid;
private int id;
    public InputFragment(int id) {
        this.id = id;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edittextnoteid = view.findViewById(R.id.edittextnoteid);
        edittexttitleid = view.findViewById(R.id.edittexttitleid);
        savebtnid = view.findViewById(R.id.savebtnid);
        database = Room.databaseBuilder(getContext(), Database.class, "Data").build();

        if (id != -1) {
            new GetsingleDataTask().execute(id);
        }
        savebtnid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edittexttitleid.getText().toString();
                String note = edittextnoteid.getText().toString();
                edittexttitleid.setText("");
                edittextnoteid.setText("");
                if (id != -1) {

                    new InsertDataTask().execute(new Data(id, title, note));
                } else {
                    new InsertDataTask().execute(new Data(title, note));
                }
            }
        });
    }
    class GetsingleDataTask extends AsyncTask<Integer, Void, Data> {

        @Override
        protected Data doInBackground(Integer... integers) {

            return database.dataDao().getsingledata(integers[0]);
        }

        @Override
        protected void onPostExecute(Data data) {
            super.onPostExecute(data);
            edittexttitleid.setText(data.getTitle());
            edittextnoteid.setText(data.getNote());
        }
    }

    class InsertDataTask extends AsyncTask<Data, Void, Void> {

        @Override
        protected Void doInBackground(Data... data) {
            database.dataDao().insert(data[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (getActivity()!=null) {
                getActivity().getSupportFragmentManager().popBackStack();
                //adaptor ta notify ditte hve
            }
        }
    }
    //thik kor adi.. eta kmn method ?? String??
    public String getcurrenttime(){
        Date date=new Date();
        //ata data te add koris , pore
        return date.toString();
    }

}
