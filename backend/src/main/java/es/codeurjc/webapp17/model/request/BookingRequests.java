package es.codeurjc.webapp17.model.request;

public class BookingRequests {
    public static class ChangeBookingRequest{
        private int action;
        public int getAction() {
            return action;
        }
    }
    public static class CreateBookingRequest{
        private long numPeople;
        private String date, hour, tlf;
        public long getNumPeople() {
            return numPeople;
        }
        public String getDate() {
            return date;
        }
        public String getHour() {
            return hour;
        }
        public String getTlf() {
            return tlf;
        }
    }
}
