package team.exp.dimagsekhelo.WebServiceRequestObjects;

import team.exp.dimagsekhelo.DataObject.User;

public class UserAccountInfo {

    private String _MobileNumber;
    private String _HashedPassword;
    private String _Salt;
    private String _EmailId;

    public String get_MobileNumber() {
        return _MobileNumber;
    }

    public void set_MobileNumber(String _MobileNumber) {
        this._MobileNumber = _MobileNumber;
    }

    public String get_HashedPassword() {
        return _HashedPassword;
    }

    public void set_HashedPassword(String _HashedPassword) {
        this._HashedPassword = _HashedPassword;
    }

    public String get_Salt() {
        return _Salt;
    }

    public void set_Salt(String _Salt) {
        this._Salt = _Salt;
    }

    public String get_EmailId() {
        return _EmailId;
    }

    public void set_EmailId(String _EmailId) {
        this._EmailId = _EmailId;
    }


    public void populateFromDataObject(User user,String hashedPassword,byte[] salt){
        this.set_EmailId(user.getEmailId());
        this.set_HashedPassword(hashedPassword);
        this.set_Salt(new String(salt));
        this.set_MobileNumber(user.getMobileNumber());
    }


    @Override
    public String toString() {
        return "UserAccountInfo{" +
                "_MobileNumber='" + _MobileNumber + '\'' +
                ", _HashedPassword='" + _HashedPassword + '\'' +
                ", _Salt='" + _Salt + '\'' +
                ", _EmailId='" + _EmailId + '\'' +
                '}';
    }
}