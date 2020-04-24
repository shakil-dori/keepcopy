package com.example.bolirepeat.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
//bujchos
//ek jaygay method banai
//sob jaygay call dae
public class MainActivity extends AppCompatActivity implements OnFragmentChangeListener{
    InputFragment inputFragment;
    ListFrament listFrament;
   SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


//        changeFragment(fragmenttestclass);
//eta ki dekhi loii khara...done..
        listFrament = new ListFrament(this);
        //changeFragment(listFrament);
        //wait vul
        //ata back stack chara
        //cz 1st er ta back stack chara lagbe, naile back korle sada hya thakakbe ...valo to
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame,listFrament)
                .commit();
        //OnfragmentChange(listFrament);


    }

    // ai method ta sob class a inject korbo...oi su
    @Override
    public void OnfragmentChange(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame,fragment)
                .addToBackStack(null)//ata dite hbe, cz item view main activity te ata k call dae
                .commit();
    }


}