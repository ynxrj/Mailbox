package mailbox.models.aboutCondo;

import java.util.Collection;
import java.util.TreeSet;

public class RoomLists{
    private Collection<Room> roomLists;

    public RoomLists() {
        roomLists = new TreeSet<>();
    }

    public void addRoom(Room room){
        roomLists.add(room);
    }

    public Collection<Room> getLists() {
        return roomLists;
    }

    public boolean checkRoomNumber(Room rooms){
        if(rooms == null){
            return false;
        }
        for (Room room : roomLists) {
           if(room.getRoomNumber().equals(rooms.getRoomNumber())){
               return false;
           }
        }
        return true;
    }

    public Room getRoomInAddByRoomNumInComboBox(String roomNumber){
        for (Room room : roomLists) {
            String compareRoom = (room.getRoomType() + " - " + room.getRoomNumber() + " - Remaining : " + room.getRemaining());
            if(compareRoom.equals(roomNumber)){
                return room;
            }
        }
        return null;
    }

    public void setAddMaxByRoomNum(String roomNumber){
            for (Room room : roomLists) {
                if(room.getRoomNumber().equals(roomNumber)){
                    room.setRemaining(room.getRemaining() - 1);
                }
            }
    }

    public void setRemoveMaxByRoomNum(String roomNumber){
        for (Room room : roomLists) {
            if(room.getRoomNumber().equals(roomNumber)){
                room.setRemaining(room.getRemaining() + 1);
            }
        }
    }

    @Override
    public String toString() {
        return "RoomLists{" +
                "roomLists=" + roomLists +
                '}';
    }

}
