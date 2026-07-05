import java.time.LocalDate;

import enums.UserRole;

public class Receptionist extends Staff {

    private String shiftType;

    public Receptionist(String name, String username, String password,
            String employeeId, LocalDate hireDate,
            String shiftType) {

        super(name, username, password, UserRole.RECEPTIONIST, employeeId, hireDate);
        this.shiftType = shiftType;
    }
}