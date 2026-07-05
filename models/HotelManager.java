import java.time.LocalDate;

import enums.UserRole;

public class HotelManager extends Staff {

    private String departmentName;

    public HotelManager(
        String name, String username, String password,
        String employeeId, LocalDate hireDate,
        String departmentName
    ) {

        super(name, username, password, UserRole.HOTEL_MANAGER, employeeId, hireDate);
        this.departmentName = departmentName;
    }
}