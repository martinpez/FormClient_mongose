package mongosedb.example.mongose.api;

import mongosedb.example.mongose.models.User;

public class ApiResponseget {

    private boolean success;
    private User data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
