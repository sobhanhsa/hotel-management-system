public class ExtraCleaningService extends Service {


    public ExtraCleaningService(){
        super("Extra Cleaning",50000);
    }


    @Override
    public double calculatePrice(Guest guest){
        return getPrice();
    }
}