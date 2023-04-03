package es.codeurjc.webapp17.model.request;

public class UsersRequests {
    public static class UserInfoRequest{
        private String email;

        public String getEmail() {
            return email;
        }
    }
    public static class ModifyUserRequest{
        private String email, name, newBio, newPassword;

        public String getNewBio() {
            return newBio;
        }
        public String getEmail() {
            return email;
        }
        public String getName() {
            return name;
        }
        public String getNewPassword() {
            return newPassword;
        }
    }
    public static class CreateUserRequest{
        private String email, name, bio, password, role;

        public String getBio() {
            return bio;
        }
        public String getEmail() {
            return email;
        }
        public String getName() {
            return name;
        }
        public String getPassword() {
            return password;
        }
        public String getRole() {
            return role;
        }
    }
    public static class LoginRequest{
        private String email, password;
        public String getEmail() {
            return email;
        }
        public String getPassword() {
            return password;
        }
    }
}
