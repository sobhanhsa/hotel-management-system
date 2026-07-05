import java.util.List;

import interfaces.Billable;
import interfaces.Filterable;
import interfaces.Searchable;

public class Reservation implements
        Billable,
        Searchable<Reservation>,
        Filterable<Reservation> {

    @Override
    public double calculateTotal() {
        return 0;
    }

    @Override
    public void applyDiscount(double percent) {
    }

    @Override
    public List<Reservation> search(String query) {
        return null;
    }

    @Override
    public List<Reservation> filter(java.util.function.Predicate<Reservation> predicate) {
        return null;
    }
}