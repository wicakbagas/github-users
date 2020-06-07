package com.bagas.githubusers.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserResult implements Serializable {

    private String message;

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_result")
    private boolean incompleteResult;

    private List<User> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResult() {
        return incompleteResult;
    }

    public void setIncompleteResult(boolean incompleteResult) {
        this.incompleteResult = incompleteResult;
    }

    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
