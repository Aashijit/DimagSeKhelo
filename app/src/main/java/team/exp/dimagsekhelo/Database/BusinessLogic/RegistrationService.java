package team.exp.dimagsekhelo.Database.BusinessLogic;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;

import team.exp.dimagsekhelo.DataObject.User;
import team.exp.dimagsekhelo.WebServiceRequestObjects.UserAccountInfo;

public class RegistrationService {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceRoot;


    public RegistrationService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceRoot = firebaseDatabase.getReference();
    }


    /**
     *
     * @param user
     * @return
     */
    public String saveUserProfileInformation(User user){

        //Hash the password
        //Generate the salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        //Fetch the hashed password
        String hashedPassword = AuthenticationService.getHashedPassword(user.getPassword(),salt);

        //Populate the database request object
        final UserAccountInfo userAccountInfo = new UserAccountInfo();
        userAccountInfo.populateFromDataObject(user,hashedPassword,salt);

        //Get the pushId
        String pushId = databaseReferenceRoot.push().getKey();

        if(pushId == null){
            Log.e("RegistrationService"," PushId is null");
            return null;
        }

        //Insert into the root node of the database
        databaseReferenceRoot.child(pushId).setValue(userAccountInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("RegistrationService","User Account saved"+userAccountInfo);
                }else{
                    Log.d("RegistrationService","User Account saved operation failed !!!");
                }
            }
        });
       return pushId;
    }
}