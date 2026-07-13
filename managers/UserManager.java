package managers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.SuperAdmin;
import models.User;

public class UserManager {


    private final LogManager logManager;

    private final ArrayList<User> users;

    private User currentUser;

    public UserManager(LogManager logManager) {

        this.logManager = logManager;

        users = new ArrayList<>();
        currentUser = null;

        initializeSystem();
    }

    private void initializeSystem() {

        SuperAdmin admin = new SuperAdmin(
                "System Admin",
                "admin",
                "123admin",
                "ADMIN001",
                LocalDate.now()
        );
        users.add(admin);
    }

}