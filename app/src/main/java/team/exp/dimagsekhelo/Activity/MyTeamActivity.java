package team.exp.dimagsekhelo.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.MyTeamAdapter;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.WebServiceRequestObjects.TeamContestRequest;

public class MyTeamActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceMyTeams = database.getReference("TeamContest");
    private List<TeamContestRequest> teamContestRequests;

    private MyTeamAdapter myTeamAdapter;

    private ListView myTeamList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);

        firebaseAuth = FirebaseAuth.getInstance();
        myTeamList = (ListView) findViewById(R.id.myTeamsList);

        fetchTeams();

    }

    private void fetchTeams(){
        databaseReferenceMyTeams.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teamContestRequests = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TeamContestRequest teamContestRequest = snapshot.getValue(TeamContestRequest.class);

                    if(teamContestRequest == null)
                        continue;

                    if(teamContestRequest.get_UserPhoneNumber().equalsIgnoreCase(firebaseAuth.getCurrentUser().getPhoneNumber()))
                        teamContestRequests.add(teamContestRequest);

                }
                myTeamAdapter = new MyTeamAdapter(MyTeamActivity.this,teamContestRequests);
                myTeamList.setAdapter(myTeamAdapter);


                myTeamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(this.getClass().getName(),"Could not fetch teams !!!");
                return;
            }
        });
    }



    public void myTeamGoBack(View view) {
        finish();
    }
}