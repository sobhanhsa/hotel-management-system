import enums.MembershipLevel;

public class ParkingService extends Service {


    public ParkingService(){
        super("Parking",80000);
    }


    @Override
    public double calculatePrice(Guest guest){

        if(
            guest.getMembershipLevel()==MembershipLevel.GOLD ||
            guest.getMembershipLevel()==MembershipLevel.PLATINUM
        ){
            return 0;
        }

        return getPrice();
    }
}