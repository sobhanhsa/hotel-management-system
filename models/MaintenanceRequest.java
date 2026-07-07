package models;

public class MaintenanceRequest extends Service {


    public MaintenanceRequest(){
        super("Maintenance",0);
    }


    @Override
    public double calculatePrice(Guest guest){
        notifyManager();
        return 0;
    }


    private void notifyManager(){
        System.out.println(
            "Maintenance request sent to Hotel Manager"
        );
    }
}