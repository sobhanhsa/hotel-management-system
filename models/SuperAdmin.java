package models;

import java.time.LocalDate;

import enums.UserRole;

public class SuperAdmin extends Staff {

    private boolean systemConfigAccess = true;

    public SuperAdmin(String name, String username, String password,
        String employeeId, LocalDate hireDate) {

        super(name, username, password, UserRole.SUPER_ADMIN, employeeId, hireDate);
    }

    public boolean hasFullAccess() {
        return systemConfigAccess;
    }
}