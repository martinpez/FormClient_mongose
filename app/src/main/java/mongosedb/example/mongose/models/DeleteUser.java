package mongosedb.example.mongose.models;


public class DeleteUser {
    private String email;

    public DeleteUser(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


