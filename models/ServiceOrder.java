package models;

public class ServiceOrder {

    private Service service;
    private int quantity;


    public ServiceOrder(Service service, int quantity){
        this.service = service;
        this.quantity = quantity;
    }


    public double getTotalPrice(Guest guest){
        return service.calculatePrice(guest) * quantity;
    }


    public Service getService(){
        return service;
    }
}