package managers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.LogLevel;
import enums.MembershipLevel;
import enums.UserRole;
import exceptions.AccessDeniedException;
import models.Guest;
import models.HotelManager;
import models.Receptionist;
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

    public HotelManager createHotelManager(
        String name,
        String username,
        String password,
        String employeeId,
        LocalDate hireDate,
        String departmentName
    ) {

        requireRole(UserRole.SUPER_ADMIN);

        validateUniqueUsername(username);


        HotelManager manager = new HotelManager(
                name,
                username,
                password,
                employeeId,
                hireDate,
                departmentName
        );


        users.add(manager);


        logManager.addLog(
                LogLevel.INFO,
                currentUser.getUsername(),
                "CREATE_HOTEL_MANAGER",
                "Created: " + username
        );


        return manager;
    }

    public Receptionist createReceptionist(
        String name,
        String username,
        String password,
        String employeeId,
        LocalDate hireDate,
        String departmentName
    ) {

        requireRole(
                UserRole.HOTEL_MANAGER
        );


        validateUniqueUsername(username);


        Receptionist receptionist =
                new Receptionist(
                        name,
                        username,
                        password,
                        employeeId,
                        hireDate,
                        departmentName
                );


        users.add(receptionist);


        logManager.addLog(
                LogLevel.INFO,
                currentUser.getUsername(),
                "CREATE_RECEPTIONIST",
                "[Created User: " + username + "]"
        );


        return receptionist;
    }

    public void disableUser(
        String username
    ) {

        requireRole(
                UserRole.SUPER_ADMIN
        );


        User user = findByUsername(username);


        if (user == null) {
            return;
        }


        user.setEnabled(false);


        logManager.addLog(
                LogLevel.INFO,
                currentUser.getUsername(),
                "DISABLE_USER",
                "[Disabled User: " + username + "]"
        );
    }
    
    public void enableUser(
        String username
    ) {

        requireRole(
                UserRole.SUPER_ADMIN
        );


        User user = findByUsername(username);


        if (user == null) {
            return;
        }


        user.setEnabled(true);


        logManager.addLog(
                LogLevel.INFO,
                currentUser.getUsername(),
                "ENABLE_USER",
                "[Enabled User: " + username + "]"
        );
    }

    public ArrayList<User> getUsersByRole(
        UserRole role
    ) {

        ArrayList<User> result = new ArrayList<>();


        for (User user : users) {

            if (user.getRole() == role) {

                result.add(user);
            }
        }


        return result;
    }

    public void removeUser(
        String username
    ) throws AccessDeniedException{

        requireRole(
                UserRole.SUPER_ADMIN
        );


        User user = findByUsername(username);


        if (user == null) {
            return;
        }

        if (user == currentUser) {
            throw new AccessDeniedException("you can't remove your self!");
        }


        users.remove(user);


        logManager.addLog(
                LogLevel.INFO,
                currentUser.getUsername(),
                "DELETE_USER",
                "[Deleted User: " + username + "]"
        );
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

    private void requireRole(
        UserRole role
    ) throws AccessDeniedException{

        if (currentUser == null || currentUser.getRole() != role) {

            logManager.addLog(
                    LogLevel.ERROR,
                    currentUser.getUsername(),
                    "ACCESS_DENIED",
                    "[Required Role: " + role + "]"
            );


            throw new AccessDeniedException(
                    "Access denied"
            );
        }
    }

    // user getter || setters

    public User findByUsername(
        String username
    ) {

        for (User user : users) {

            if (user.getUsername().equals(username)) {

                return user;
            }
        }


        return null;
    }

    public Guest findGuestByNationalId(
        String nationalId
    ) {

        for (User user : users) {

            if (user instanceof Guest guest) {

                if (guest.getNationalId().equals(nationalId)) {

                    return guest;
                }
            }
        }


        return null;
    }

    public ArrayList<User> getAllUsers() {

        return users;
    }

    public User getCurrentUser() {

        return currentUser;
    }

    public boolean isLoggedIn() {

        return currentUser != null;
    }
}