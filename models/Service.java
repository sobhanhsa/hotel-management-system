package models;

public abstract class Service {

    private int nextServiceId = 1;

    private int serviceId;
    private String name;
    private double price;

    public Service(String name, double price) {
        this.serviceId = nextServiceId++;
        this.name = name;
        this.price = price;
    }

    public abstract double calculatePrice(Guest guest);

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}