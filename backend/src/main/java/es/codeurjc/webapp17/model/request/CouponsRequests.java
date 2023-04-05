package es.codeurjc.webapp17.model.request;

public class CouponsRequests {
    public static class ModifyCouponsRequest{
        private String code;
        private String discount;
        private String newUserEmail;
        private int uses;

        public String getCode() {
            return code;
        }
        public String getDiscount() {
            return discount;
        }
        public String getNewUserEmail() {
            return newUserEmail;
        }
        public int getUses() {
            return uses;
        }       
    }

    public static class CreateCouponsRequest{
        private String code;
        private String discount;
        private int uses;
        private String user;
        public String getCode() {
            return code;
        }
        public String getDiscount() {
            return discount;
        }
        public int getUses() {
            return uses;
        }
        public String getUser() {
            return user;
        }

        
    }
}
