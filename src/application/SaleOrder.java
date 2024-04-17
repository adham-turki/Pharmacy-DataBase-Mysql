package application;
import java.sql.Date;

public class SaleOrder {
    private int Oid;
    private Date Odate;
    private double price;
    private int Eid;



    public SaleOrder(int oid, Date odate,double price, int eid) {
        Oid = oid;
        Odate = odate;
        this.price =price;
        Eid = eid;
    }

    public int getOid() {
        return Oid;
    }

    public void setOid(int oid) {
        Oid = oid;
    }

    public Date getOdate() {
        return Odate;
    }

    public void setOdate(Date odate) {
        this.Odate = odate;
    }

    public int getEid() {
        return Eid;
    }

    public void setEid(int eid) {
        Eid = eid;
    }
    

    public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
    public String toString() {
        return "SaleOrder{" +
                "Oid=" + Oid +
                ", Odate=" + Odate +
                ", Oprice=" + price +
                ", Eid=" + Eid +
                '}';
    }
}
