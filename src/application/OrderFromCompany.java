package application;
public class OrderFromCompany {
    private int orderId;
    private double price;
    private int compId;

    public OrderFromCompany(int orderId, double price, int compId) {
        this.orderId = orderId;
        this.price = price;
        this.compId = compId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCompId() {
        return compId;
    }

    public void setCompId(int compId) {
        this.compId = compId;
    }


}
