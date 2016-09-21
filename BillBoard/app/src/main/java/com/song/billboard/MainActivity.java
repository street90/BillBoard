package com.song.billboard;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.song.billboardlibrary.BillTextView;
import com.song.billboardlibrary.BillViewClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button btChange;
    BillTextView btv;
    List<String> mStrings;
    Button btAuto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mStrings = new ArrayList<String>();

        mStrings.add("Haha");
        mStrings.add("dfasdfas");
        mStrings.add("sdfasdfasf");

        btv = (BillTextView) findViewById(R.id.btv);

        btv.setBillText(mStrings);


        btAuto = (Button) findViewById(R.id.bt_auto);

        btAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btv.startSmooth();
            }
        });


        btChange = (Button) findViewById(R.id.bt_change);

        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btv.start();
            }
        });

        btv.setOnBillViewCllickListener(new BillViewClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(MainActivity.this,mStrings.get(position),Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
