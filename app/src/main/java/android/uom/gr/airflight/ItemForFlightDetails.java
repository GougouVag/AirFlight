package android.uom.gr.airflight;

/**
 * Created by LamPrOs on 10/1/2017.
 */

public class ItemForFlightDetails {

    private String timeDepartGo ;
    private String  airportDepartGo;
    private String  timeArivalGo;
    private String  airportArivalGo;
    private String  flightTimeGo;
    private String  priceToGo;
    private String  curToGo;
    private String  airlineGo;
    private String  travel_classGo;
    private String  booking_codeGo;
    private String  seats_remainingGo;

//    "travel_class": "ECONOMY",
//            "booking_code": "R",
//            "seats_remaining": 3

    private String timeDepartEnd ;
    private String  airportDepartEnd;
    private String  timeArivalEnd;
    private String  airportArivalEnd;
    private String  flightTimeEnd;
    private String  priceToEnd;
    private String  curToEnd;
    private String  airlineEnd;
    private String  travel_classEnd;
    private String  booking_codeEnd;
    private String  seats_remainingEnd;

    public ItemForFlightDetails(String timeDepartGo , String  airportDepartGo, String  timeArivalGo, String  airportArivalGo, String  flightTimeGo, String  priceToGo, String  curToGo,String  airlineGo,String  travel_classGo,String  booking_codeGo,String  seats_remainingGo, String timeDepartEnd , String  airportDepartEnd, String  timeArivalEnd, String  airportArivalEnd, String  flightTimeEnd, String  priceToEnd, String  curToEnd,String  airlineEnd,String  travel_classEnd, String  booking_codeEnd,String  seats_remainingEnd){

        this.timeDepartGo = timeDepartGo ;
        this.airportDepartGo= airportDepartGo;
        this.timeArivalGo= timeArivalGo;
        this.airportArivalGo=airportArivalGo;
        this.flightTimeGo=flightTimeGo;
        this.priceToGo=priceToGo;
        this.curToGo=curToGo;
        this.airlineGo=airlineGo;
        this.travel_classGo = travel_classGo;
        this.booking_codeGo = booking_codeGo;
        this.seats_remainingGo = seats_remainingGo;

        this.timeDepartEnd =timeDepartEnd;
        this.airportDepartEnd=airportDepartEnd;
        this.timeArivalEnd=timeArivalEnd;
        this.airportArivalEnd=airportArivalEnd;
        this.flightTimeEnd=flightTimeEnd;
        this.priceToEnd=priceToEnd;
        this.curToEnd=curToEnd;
        this.airlineEnd=airlineEnd;
        this.travel_classEnd = travel_classGo;
        this.booking_codeEnd = booking_codeGo;
        this.seats_remainingEnd = seats_remainingGo;
    }

    public String getBooking_codeGo() {
        return booking_codeGo;
    }

    public String getSeats_remainingGo() {
        return seats_remainingGo;
    }

    public String getTravel_classGo() {
        return travel_classGo;
    }

    public String getBooking_codeEnd() {
        return booking_codeEnd;
    }

    public String getSeats_remainingEnd() {
        return seats_remainingEnd;
    }

    public String getTravel_classEnd() {
        return travel_classEnd;
    }

    public void setTravel_classGo(String travel_classGo) {
        this.travel_classGo = travel_classGo;
    }

    public void setBooking_codeEnd(String booking_codeEnd) {
        this.booking_codeEnd = booking_codeEnd;
    }

    public void setBooking_codeGo(String booking_codeGo) {
        this.booking_codeGo = booking_codeGo;
    }

    public void setSeats_remainingEnd(String seats_remainingEnd) {
        this.seats_remainingEnd = seats_remainingEnd;
    }

    public void setSeats_remainingGo(String seats_remainingGo) {
        this.seats_remainingGo = seats_remainingGo;
    }

    public void setTravel_classEnd(String travel_classEnd) {
        this.travel_classEnd = travel_classEnd;
    }

    public String getAirlineGo() {
        return airlineGo;
    }

    public String getAirlineEnd() {
        return airlineEnd;
    }

    public void setAirlineGo(String airlineGo) {
        this.airlineGo = airlineGo;
    }

    public void setAirlineEnd(String airlineEnd) {
        this.airlineEnd = airlineEnd;
    }

    public String getTimeDepartGo() {
        return timeDepartGo;
    }

    public String getAirportArivalEnd() {
        return airportArivalEnd;
    }

    public String getAirportArivalGo() {
        return airportArivalGo;
    }

    public String getAirportDepartEnd() {
        return airportDepartEnd;
    }

    public String getAirportDepartGo() {
        return airportDepartGo;
    }

    public String getCurToGo() {
        return curToGo;
    }

    public String getCurToEnd() {
        return curToEnd;
    }

    public String getFlightTimeEnd() {
        return flightTimeEnd;
    }

    public String getTimeArivalGo() {
        return timeArivalGo;
    }

    public String getFlightTimeGo() {
        return flightTimeGo;
    }

    public String getPriceToEnd() {
        return priceToEnd;
    }

    public String getPriceToGo() {
        return priceToGo;
    }

    public String getTimeArivalEnd() {
        return timeArivalEnd;
    }

    public String getTimeDepartEnd() {
        return timeDepartEnd;
    }

    public void setAirportArivalGo(String airportArivalGo) {
        this.airportArivalGo = airportArivalGo;
    }

    public void setAirportDepartGo(String airportDepartGo) {
        this.airportDepartGo = airportDepartGo;
    }

    public void setAirportDepartEnd(String airportDepartEnd) {
        this.airportDepartEnd = airportDepartEnd;
    }

    public void setCurToGo(String curToGo) {
        this.curToGo = curToGo;
    }

    public void setFlightTimeGo(String flightTimeGo) {
        this.flightTimeGo = flightTimeGo;
    }

    public void setTimeArivalGo(String timeArivalGo) {
        this.timeArivalGo = timeArivalGo;
    }

    public void setAirportArivalEnd(String airportArivalEnd) {
        this.airportArivalEnd = airportArivalEnd;
    }

    public void setCurToEnd(String curToEnd) {
        this.curToEnd = curToEnd;
    }

    public void setFlightTimeEnd(String flightTimeEnd) {
        this.flightTimeEnd = flightTimeEnd;
    }

    public void setPriceToEnd(String priceToEnd) {
        this.priceToEnd = priceToEnd;
    }

    public void setPriceToGo(String priceToGo) {
        this.priceToGo = priceToGo;
    }

    public void setTimeDepartEnd(String timeDepartEnd) {
        this.timeDepartEnd = timeDepartEnd;
    }

    public void setTimeArivalEnd(String timeArivalEnd) {
        this.timeArivalEnd = timeArivalEnd;
    }

    public void setTimeDepartGo(String timeDepartGo) {
        this.timeDepartGo = timeDepartGo;
    }


}
