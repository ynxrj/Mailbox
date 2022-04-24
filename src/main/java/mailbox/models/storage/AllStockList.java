package mailbox.models.storage;

import java.util.ArrayList;

public class AllStockList {
    ArrayList<Letter> allStockList;

    public AllStockList() {
        allStockList = new ArrayList<>();
    }

    public ArrayList<Letter> getAllStockList() {
        return allStockList;
    }

    public void addStock(Letter item){
        allStockList.add(item);
    }

    public void removeItemByTime(String time){
        allStockList.removeIf(item -> item.getTime().equals(time));
    }

    public Letter getItemByTime(String time){
        for(Letter item : allStockList){
            if(item.getTime().equals(time)){
                return item;
            }
        }
        return null;
    }

    public AllStockList getInStockList(){
        AllStockList inList = new AllStockList();
        for(Letter item : allStockList){
            if(item.getStatus().equals("InStock")){
                inList.addStock(item);
            }
        }
        return inList;
    }

    public AllStockList getInStockListByRoom(String roomNum){
        AllStockList inList = new AllStockList();
        for(Letter item : allStockList){
            String[] room = item.getReceiver().split(" - ");
            if(item.getStatus().equals("InStock") && room[0].equals(roomNum)){
                inList.addStock(item);
            }
        }
        return inList;
    }

    public AllStockList getOutStockList(){
        AllStockList outList = new AllStockList();
        for(Letter item : allStockList){
            if(item.getStatus().equals("Outgoing")){
                outList.addStock(item);
            }
        }
        return outList;
    }

    public AllStockList getOutStockListByRoom(String roomNum){
        AllStockList outList = new AllStockList();
        for(Letter item : allStockList){
            String[] room = item.getReceiver().split(" - ");
            if(item.getStatus().equals("Outgoing") && room[0].equals(roomNum)){
                outList.addStock(item);
            }
        }
        return outList;
    }
}
