package team.exp.dimagsekhelo.Utils;

public class DataValidation {

    public static boolean isValidMobileNumber(String mobileNumber){
        return mobileNumber != null && mobileNumber.matches("[0-9]{10}");
    }

    public static boolean isPasswordAndConfirmPasswordSame(String password, String confirmPassword){
        return ((password != null && confirmPassword != null) && password.equals(confirmPassword));
    }


    public static boolean isValidEmailId(String emailId){
        return emailId != null && emailId.matches("[a-zA-Z0-9]{1,}@[a-zA-Z]{1,}\\.[a-zA-Z]{1,}");
    }
}