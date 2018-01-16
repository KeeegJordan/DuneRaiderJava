public class User {

    private final String userId;
    private String accessToken;
    private String profilePic;
    private String bio;

    public User(String userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getBio() {
        return bio;
    }
}
