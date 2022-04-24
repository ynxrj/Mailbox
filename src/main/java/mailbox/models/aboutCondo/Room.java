package mailbox.models.aboutCondo;

public class Room implements Comparable<Room>{
    private String roomType, building, floor, room, roomNumber;
    private int remaining, capacity;

    public Room(String roomType, String building, String floor, String room, int remaining, int capacity) {
        this.roomType = roomType;
        this.building = building;
        this.floor = floor;
        this.room = room;
        this.remaining = remaining;
        this.capacity = capacity;
        this.roomNumber = building + floor + room;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getBuilding() {
        return building;
    }

    public String getFloor() {
        return floor;
    }

    public String getRoom() {
        return room;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getRemaining(){
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomtype='" + roomType +
                ", roomNumber='" + roomNumber + '\'' +
                '}';
    }

    @Override
    public int compareTo(Room room) {
        return this.getRoomNumber().compareToIgnoreCase(room.getRoomNumber());
    }
}
