import java.time.LocalDate;

import enums.UserRole;

public abstract class Staff extends User {

    protected String employeeId;
    protected LocalDate hireDate;

    public Staff(
            String name, String username, String password, UserRole role,
            String employeeId, LocalDate hireDate
    ) {
        super(name, username, password, role);
        this.employeeId = employeeId;
        this.hireDate = hireDate;
    }
}