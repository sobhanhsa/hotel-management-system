package interfaces;

public interface Notifiable {
    void registerObserver(RoomObserver observer);
    void removeObserver(RoomObserver observer);
    void notifyObservers(String roomNumber);
}