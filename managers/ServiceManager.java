package managers;

import java.util.List;

import models.Invoice;
import models.Service;
import models.ServiceOrder;

public class ServiceManager {

    private List<ServiceOrder> services;

    public void addServiceToInvoice(
            Invoice invoice,
            Service service
    ) {
        invoice.addService(new ServiceOrder(service, 1));
    }

    public List<ServiceOrder> getAvailableServices() {
        return services;
    }

}