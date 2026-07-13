package managers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.LogLevel;
import enums.MembershipLevel;
import models.Guest;
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

    public User login(
        String username,
        String password
    ) {

        for (User user : users) {

            if (user.getUsername().equals(username)
                    && user.checkPassword(password)) {


                if (!user.isEnabled()) {
                    return null;
                }


                currentUser = user;


                logManager.addLog(
                        LogLevel.INFO,
                        currentUser.getUsername(),
                        "LOGIN",
                        currentUser.getName()+"logged in"
                );


                return user;
            }
        }


        return null;
    }

    public Guest registerGuest(
        String username,
        String password,
        String fullName,
        String nationalId,
        String phone
    ) {

        validateUniqueUsername(username);

        validateUniqueNationalId(nationalId);


        Guest guest = new Guest(
                fullName,
                username,
                password,
                nationalId,
                phone
        );


        users.add(guest);


        logManager.addLog(
                LogLevel.INFO,
                username,
                "REGISTER_GUEST",
                "New guest account created"
        );


        return guest;
    }   

    public void logout() {

        if (currentUser == null) {
            return;
        }


        logManager.addLog(
                LogLevel.INFO,
                currentUser.getUsername(),
                "LOGOUT",
                currentUser.getUsername() + " logged out"
        );


        currentUser = null;
    }   

    // helpers
    
    private void validateUniqueUsername(String username) {

        for (User user : users) {

            if (user.getUsername().equals(username)) {

                throw new IllegalArgumentException(
                        "Username already exists"
                );
            }
        }
    }

    private void validateUniqueNationalId(String nationalId) {

        for (User user : users) {

            if (user instanceof Guest guest) {

                if (guest.getNationalId().equals(nationalId)) {

                    throw new IllegalArgumentException(
                            "National ID already exists"
                    );
                }
            }
        }
    }

}