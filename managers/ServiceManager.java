package managers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidDateRangeException;
import exceptions.OutsideWorkingHoursException;
import models.ExtraCleaningService;
import models.Invoice;
import models.MaintenanceRequest;
import models.MiniBarService;
import models.ParkingService;
import models.Service;
import models.ServiceOrder;

public class ServiceManager {

    private List<ServiceOrder> serviceOrders;
    private List<Service> availableServices;

    public ServiceManager() {
        availableServices = new ArrayList<>();
        serviceOrders = new ArrayList<>();
        initializeDefaultServices();
    }

    public void addServiceToInvoice(
            Invoice invoice,
            Service service
    ) throws InvalidDateRangeException{
        if (service instanceof ExtraCleaningService) {
            LocalTime start = LocalTime.of(9, 0);
            LocalTime end = LocalTime.of(17, 0);
            
            LocalTime now = LocalTime.now();
            
            boolean inside =
                    !now.isBefore(start) &&
                    !now.isAfter(end);

            if (!inside) 
                throw new OutsideWorkingHoursException("out of operating hours");
        }

        invoice.addService(new ServiceOrder(service, 1));
        serviceOrders.add(new ServiceOrder(service, 1));
    }

    private void initializeDefaultServices() {

        availableServices.add(
                new MiniBarService(
                        45000
                )
        );

        availableServices.add(
                new ExtraCleaningService(
                )
        );

        availableServices.add(
                new ParkingService()
        );

        availableServices.add(
                new MaintenanceRequest()
        );
    }

    public List<Service> getAvailableServices() {
        return availableServices;
    }

}