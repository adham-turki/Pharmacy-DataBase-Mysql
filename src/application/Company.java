package application;
public class Company {
    private int Compid;
    private String Compname;	  
    private String location;
    private String establsihDate;

    public Company(int compid, String compname, String location, String establsihDate) {
        Compid = compid;
        Compname = compname;
        this.location = location;
        this.establsihDate = establsihDate;
    }

    public int getCompid() {
        return Compid;
    }

    public void setCompid(int compid) {
        Compid = compid;
    }

    public String getCompname() {
        return Compname;
    }

    public void setCompname(String compname) {
        Compname = compname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEstablsihDate() {
        return establsihDate;
    }

    public void setEstablsihDate(String establsihDate) {
        this.establsihDate = establsihDate;
    }
}


