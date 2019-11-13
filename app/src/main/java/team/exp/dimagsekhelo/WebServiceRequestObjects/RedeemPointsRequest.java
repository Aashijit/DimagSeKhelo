package team.exp.dimagsekhelo.WebServiceRequestObjects;

public class RedeemPointsRequest {

    private String _BankAccountNumber;
    private String _AccountHolderName;
    private String _IfscCode;
    private String _UserPhoneNumber;
    private String _ContestId;
    private String _MatchId;
    private String _Score;
    private String _Rank;

    public String get_BankAccountNumber() {
        return _BankAccountNumber;
    }

    public void set_BankAccountNumber(String _BankAccountNumber) {
        this._BankAccountNumber = _BankAccountNumber;
    }

    public String get_AccountHolderName() {
        return _AccountHolderName;
    }

    public void set_AccountHolderName(String _AccountHolderName) {
        this._AccountHolderName = _AccountHolderName;
    }

    public String get_IfscCode() {
        return _IfscCode;
    }

    public void set_IfscCode(String _IfscCode) {
        this._IfscCode = _IfscCode;
    }

    public String get_UserPhoneNumber() {
        return _UserPhoneNumber;
    }

    public void set_UserPhoneNumber(String _UserPhoneNumber) {
        this._UserPhoneNumber = _UserPhoneNumber;
    }

    public String get_ContestId() {
        return _ContestId;
    }

    public void set_ContestId(String _ContestId) {
        this._ContestId = _ContestId;
    }

    public String get_MatchId() {
        return _MatchId;
    }

    public void set_MatchId(String _MatchId) {
        this._MatchId = _MatchId;
    }

    public String get_Score() {
        return _Score;
    }

    public void set_Score(String _Score) {
        this._Score = _Score;
    }

    public String get_Rank() {
        return _Rank;
    }

    public void set_Rank(String _Rank) {
        this._Rank = _Rank;
    }

    @Override
    public String toString() {
        return "RedeemPointsRequest{" +
                "_BankAccountNumber='" + _BankAccountNumber + '\'' +
                ", _AccountHolderName='" + _AccountHolderName + '\'' +
                ", _IfscCode='" + _IfscCode + '\'' +
                ", _UserPhoneNumber='" + _UserPhoneNumber + '\'' +
                ", _ContestId='" + _ContestId + '\'' +
                ", _MatchId='" + _MatchId + '\'' +
                ", _Score='" + _Score + '\'' +
                ", _Rank='" + _Rank + '\'' +
                '}';
    }
}