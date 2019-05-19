package com.redudant.memorableplacesobjectserialez;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    static ArrayList<String> places = new ArrayList<> ();
    static ArrayList<LatLng> locations = new ArrayList<> (); //membuat dafar list lokasi

    static ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        listView = findViewById ( R.id.listView );

        SharedPreferences sharedPreferences = this.getSharedPreferences ( "com.redudant.memorableplaces", Context.MODE_PRIVATE );

        ArrayList<String> latitude = new ArrayList<> ();
        ArrayList<String> longitudes = new ArrayList<> ();

        places.clear ();
        latitude.clear ();
        longitudes.clear ();
        locations.clear ();

        try {

            places = (ArrayList<String>) ObjectSerializer.deserialize ( sharedPreferences.getString ( "places", ObjectSerializer.serialize ( new ArrayList<String> () ) ) );

            latitude = (ArrayList<String>) ObjectSerializer.deserialize ( sharedPreferences.getString ( "latitude", ObjectSerializer.serialize ( new ArrayList<String> () ) ) );

            longitudes = (ArrayList<String>) ObjectSerializer.deserialize ( sharedPreferences.getString ( "longitudes", ObjectSerializer.serialize ( new ArrayList<String> () ) ) );

        } catch (IOException e) {

            e.printStackTrace ();
        }

        //
        if (places.size () > 0 && latitude.size () > 0 && longitudes.size () > 0) {

            if (places.size () == latitude.size () && latitude.size () == longitudes.size ()) {

                for (int i = 0; i < latitude.size (); i++) {

                    locations.add ( new LatLng ( Double.parseDouble ( latitude.get ( i ) ), Double.parseDouble ( longitudes.get ( i ) ) ) );
                }
            }
        } else {

            places.add ( "Add a new places..." );

            locations.add ( new LatLng ( 0, 0 ) ); //menambahkan
        }
        arrayAdapter = new ArrayAdapter<> ( this, android.R.layout.simple_list_item_1, places );

        listView.setAdapter ( arrayAdapter );

        listView.setOnItemClickListener ( new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent ( getApplicationContext (), MapsActivity.class );
                intent.putExtra ( "placeNumber", position );

                startActivity ( intent );
            }

        } );


    }
}