package models;

public class MiniBarService extends Service {


    public MiniBarService(double price){
        super("Mini Bar",price);
    }


    @Override
    public double calculatePrice(Guest guest){
        return getPrice();
    }
}