package android.uom.gr.airflight;

/**
 * Created by LamPrOs on 10/1/2017.
 */

public class ItemForHistory {

    private int id;
    private String from;
    private String to;
    private String adults_number;
    private String children_number;
    private String infants;
    private String way_is;
    private String depart_day;
    private String arrival_day;

    public ItemForHistory(int id,String from,String to,String adults_number,String children_number,String infants,String way_is,String depart_day,String arrival_day ){

        this.id = id;
        this.from = from;
        this.to = to;
        this.adults_number = adults_number;
        this.children_number = children_number;
        this.infants = infants;
        this.way_is = way_is;
        this.depart_day = depart_day;
        this.arrival_day = arrival_day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getAdults_number() {
        return adults_number;
    }

    public String getArrival_day() {
        return arrival_day;
    }

    public String getChildren_number() {
        return children_number;
    }

    public String getDepart_day() {
        return depart_day;
    }

    public String getInfants() {
        return infants;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getWay_is() {
        return way_is;
    }

    public void setAdults_number(String adults_number) {
        this.adults_number = adults_number;
    }

    public void setChildren_number(String children_number) {
        this.children_number = children_number;
    }

    public void setDepart_day(String depart_day) {
        this.depart_day = depart_day;
    }

    public void setInfants(String infants) {
        this.infants = infants;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setArrival_day(String arrival_day) {
        this.arrival_day = arrival_day;
    }

    public void setWay_is(String way_is) {
        this.way_is = way_is;
    }


}
