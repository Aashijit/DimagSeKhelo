package team.exp.dimagsekhelo.WebServiceRequestObjects;

public class PaymentRequest {

    private String _OrderId;
    private String _UserPhoneNumber;
    private String _Amount;
    private String _ContestId;
    private String _MatchId;

    public String get_OrderId() {
        return _OrderId;
    }

    public void set_OrderId(String _OrderId) {
        this._OrderId = _OrderId;
    }

    public String get_UserPhoneNumber() {
        return _UserPhoneNumber;
    }

    public void set_UserPhoneNumber(String _UserPhoneNumber) {
        this._UserPhoneNumber = _UserPhoneNumber;
    }

    public String get_Amount() {
        return _Amount;
    }

    public void set_Amount(String _Amount) {
        this._Amount = _Amount;
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

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "_OrderId='" + _OrderId + '\'' +
                ", _UserPhoneNumber='" + _UserPhoneNumber + '\'' +
                ", _Amount='" + _Amount + '\'' +
                ", _ContestId='" + _ContestId + '\'' +
                ", _MatchId='" + _MatchId + '\'' +
                '}';
    }
}
