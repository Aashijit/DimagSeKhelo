package team.exp.dimagsekhelo.Database.BusinessLogic;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AuthenticationService {

    /**
     *
     * @param password
     * @param salt
     * @return
     */
    public static String getHashedPassword(String password,byte[] salt)
    {
        byte[] hashedPassword;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            hashedPassword = md.digest(password.getBytes());

        } catch (NoSuchAlgorithmException e) {
            Log.e("AuthenticationService",e.getMessage());
            return null;
        }
        return new String(hashedPassword);
    }

}
