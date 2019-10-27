package team.exp.dimagsekhelo.WebServiceResponseObjects;

public class WicketsTaken {

    private String _Wicket;
    private String _Type;

    public WicketsTaken(String _Type) {
        this._Type = _Type;
    }

    public String get_Wicket() {
        return _Wicket;
    }

    public void set_Wicket(String _Wicket) {
        this._Wicket = _Wicket;
    }

    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }

    @Override
    public String toString() {
        return "WicketsTaken{" +
                "_Wicket='" + _Wicket + '\'' +
                ", _Type='" + _Type + '\'' +
                '}';
    }
}