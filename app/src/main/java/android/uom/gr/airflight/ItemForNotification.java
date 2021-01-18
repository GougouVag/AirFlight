package android.uom.gr.airflight;

/**
 * Created by LamPrOs on 10/1/2017.
 */

public class ItemForNotification {

    private int ID;
    private String date;
    private String notificationText;

    public ItemForNotification(int ID, String date,String notificationText){

        this.ID = ID;
        this.date=date;
        this.notificationText=notificationText;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
}
