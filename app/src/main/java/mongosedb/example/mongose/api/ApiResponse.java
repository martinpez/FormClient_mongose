package mongosedb.example.mongose.api;

import java.util.List;

import mongosedb.example.mongose.models.User;

public class ApiResponse {

    private boolean success;
    private List<User> data;

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}

