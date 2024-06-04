package models;

public class UserResponse {
    private Boolean success;
    private String message;
    private String accessToken;
    private String refreshToken;
    private BodyUser user;

    public UserResponse(Boolean success, String message, String accessToken, String refreshToken, BodyUser user) {
        this.success = success;
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public static class BodyUser {
        private String email;
        private String password;
        private String name;

        public BodyUser(String email, String password, String name) {
            this.email = email;
            this.password = password;
            this.name = name;
        }

        public BodyUser() {

        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public BodyUser getUser() {
        return user;
    }

    public void setUser(BodyUser user) {
        this.user = user;
    }
}
