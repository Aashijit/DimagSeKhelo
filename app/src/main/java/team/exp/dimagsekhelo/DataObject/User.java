package team.exp.dimagsekhelo.DataObject;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class User {

    private String mobileNumber;
    private String password;
    private String emailId;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void populateDataObject(String emailId, String password, String mobileNumber){
        this.setEmailId(emailId);
        this.setMobileNumber(mobileNumber);
        this.setPassword(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "mobileNumber='" + mobileNumber + '\'' +
                ", password='" + password + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
