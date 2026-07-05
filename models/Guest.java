import java.util.ArrayList;
import java.util.List;

import enums.MembershipLevel;
import enums.UserRole;

public class Guest extends User {

    private String nationalId;
    private String phone;
    private MembershipLevel membershipLevel;
    private int totalStays;
    private List<Reservation> reservationHistory;

    public Guest(String name, String username, String password, String nationalId, String phone) {
        super(name, username, password, UserRole.GUEST);
        this.nationalId = nationalId;
        this.phone = phone;
        this.membershipLevel = MembershipLevel.BRONZE;
        this.totalStays = 0;
        this.reservationHistory = new ArrayList<>();
    }

    public void addReservation(Reservation r) {
        reservationHistory.add(r);
    }

    public void increaseStay() {
        totalStays++;
    }

    public int getTotalStays() {
        return totalStays;
    }

    public MembershipLevel getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(MembershipLevel level) {
        this.membershipLevel = level;
    }
}