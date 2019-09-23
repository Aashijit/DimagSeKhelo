package team.exp.dimagsekhelo.Database.BusinessLogic;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;

import team.exp.dimagsekhelo.DataObject.SaveProfileInformationResponse;
import team.exp.dimagsekhelo.DataObject.User;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.WebServiceRequestObjects.UserAccountInfo;

public class RegistrationService implements Codes {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceRoot;
    private DatabaseReference databaseReferenceUsers;


    SaveProfileInformationResponse saveProfileInformationResponse = null;


    public RegistrationService() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceRoot = firebaseDatabase.getReference();
        databaseReferenceUsers = databaseReferenceRoot.child("UserProfile");
    }





    /**
     * @param user
     * @return
     */
    public SaveProfileInformationResponse saveUserProfileInformation(User user) {

        //Check if already a user is present with the mobile number or the email Id
        //Add a data change listener


        final User userToBeInserted = user;
        Query query = databaseReferenceUsers;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    boolean isPresent = false;


                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                        UserAccountInfo userAccountInfo = dsp.getValue(UserAccountInfo.class);

                        if (userAccountInfo != null) {

                            //Check if the user Account already exists
                            if (userAccountInfo.get_EmailId().equalsIgnoreCase(userToBeInserted.getEmailId()) ||
                                    userAccountInfo.get_MobileNumber().equalsIgnoreCase(userToBeInserted.getMobileNumber())) {
                                saveProfileInformationResponse = new SaveProfileInformationResponse(RC_USER_PRESENT, RM_USER_PRESENT);
                                isPresent = true;
                                break;
                            }
                        }
                    }

                    if (!isPresent) {
                        //Hash the password
                        //Generate the salt
                        SecureRandom random = new SecureRandom();
                        byte[] salt = new byte[16];
                        random.nextBytes(salt);

                        //Fetch the hashed password
                        String hashedPassword = AuthenticationService.getHashedPassword(userToBeInserted.getPassword(), salt);

                        //Populate the database request object
                        final UserAccountInfo userAccountInfo = new UserAccountInfo();
                        userAccountInfo.populateFromDataObject(userToBeInserted, hashedPassword, salt);

                        //Get the pushId
                        final String pushId = databaseReferenceUsers.push().getKey();

                        if (pushId == null) {
                            Log.e("RegistrationService", " PushId is null");
                            saveProfileInformationResponse = new SaveProfileInformationResponse(RC_USER_NOT_INSERTED, RM_USER_NOT_INSERTED);
                            return;
                        }

                        //Insert into the root node of the database
                            databaseReferenceUsers.child(pushId).setValue(userAccountInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("RegistrationService", "User Account saved" + userAccountInfo);
                                        saveProfileInformationResponse = new SaveProfileInformationResponse(RC_SUCCESS, RM_SUCCESS);
                                        saveProfileInformationResponse.setUserId(pushId);
                                    } else {
                                        saveProfileInformationResponse = new SaveProfileInformationResponse(RC_USER_NOT_INSERTED, RM_USER_NOT_INSERTED);
                                        Log.d("RegistrationService", "User Account saved operation failed !!!");
                                    }
                                }
                            });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                saveProfileInformationResponse = new SaveProfileInformationResponse(RC_FAILURE, RM_FAILURE);
            }
        });



        return saveProfileInformationResponse;
    }

}