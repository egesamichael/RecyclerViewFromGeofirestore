package com.praisewebsolutions.recyclerviewfromgeofirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;

import org.imperiumlabs.geofirestore.GeoFirestore;
import org.imperiumlabs.geofirestore.GeoLocation;
import org.imperiumlabs.geofirestore.GeoQuery;
import org.imperiumlabs.geofirestore.listeners.GeoQueryEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private static FirebaseFirestore mDatabase;

    private Query query;
    GeoFirestore geoFire;
    private RecyclerView mRecyclerView;
    private ArrayList<User> mUser = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseFirestore.getInstance();
        final CollectionReference datasRef = mDatabase.collection("Users/");
      geoFire = new GeoFirestore(datasRef);
        geoFire.setLocation("mylocation", new GeoPoint(37.7853889, -122.4056973));

        mRecyclerView = findViewById(R.id.userslist);

        //Linear Layout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);

        //Set Layout Manager to RecyclerView
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //Create adapter
        MyAdapter myRecyclerViewAdapter = new MyAdapter(mUser, new MyAdapter.MyRecyclerViewItemClickListener()
        {
            //Handling clicks
            @Override
            public void onItemClicked(User user)
            {
                Toast.makeText(MainActivity.this, user.getUsername(), Toast.LENGTH_SHORT).show();
            }
        });

        //Set adapter to RecyclerView
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
        getUsers();
    }


    public void getUsers (){

        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoPoint(37.7832, -122.4056), 0.6);

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyExited(String s) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(Exception e) {

            }

            @Override
            public void onKeyMoved(String s, GeoPoint geoPoint) {

            }

            @Override
            public void onKeyEntered(String s, GeoPoint geoPoint) {

                mUser.add(new User(s));

            }


        });
    }
}