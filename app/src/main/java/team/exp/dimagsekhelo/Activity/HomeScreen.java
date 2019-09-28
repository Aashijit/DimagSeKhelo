package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.UpcomingMatchesListAdapter;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.WebServiceResponseObjects.UpcomingMatchesResponse;

public class HomeScreen extends AppCompatActivity {

    //Member Variables
    private ListView listView;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceUpcomingMatches = database.getReference("UpcomingMatches");

    private UpcomingMatchesListAdapter upcomingMatchesListAdapter;


    //Member Variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView =  (ListView) findViewById(R.id.listViewUpcomingMatches);
        progressBar = (ProgressBar) findViewById(R.id.upcomingMatchesProgressBar);
        setSupportActionBar(toolbar);

        //Initialize the variables
        firebaseAuth = FirebaseAuth.getInstance();

        //Initialize the variables


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, MyProfileScreen.class);
                startActivity(intent);
            }
        });

        //Fetch the upcoming matches here
        progressBar.setVisibility(View.VISIBLE);
        fetchUpcomingMatches();


    }




    private void fetchUpcomingMatches(){

        //Step 1 : Fetch the matches from the DB in a list
        final List<UpcomingMatchesResponse> upcomingMatchesResponseList = new ArrayList<>();
        databaseReferenceUpcomingMatches.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    UpcomingMatchesResponse upcomingMatchesResponse = dsp.getValue(UpcomingMatchesResponse.class);
                    upcomingMatchesResponseList.add(upcomingMatchesResponse);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Could not fetch the upcoming matches !!! ",Toast.LENGTH_LONG).show();
            }
        });


        //Step 2 : Update the List View
        upcomingMatchesListAdapter = new UpcomingMatchesListAdapter(this,upcomingMatchesResponseList);
        listView.setAdapter(upcomingMatchesListAdapter);
        progressBar.setVisibility(View.INVISIBLE);
    }



    public void logOut(View view) {
        firebaseAuth.signOut();
        Intent intent = new Intent(HomeScreen.this, SplashScreen.class);
        finish();
        startActivity(intent);
    }
}