package com.watchlist.authorization;

/**
 * Created by VEINHORN on 28/11/13.
 */
public class LogedInUser extends User {
    private String date; // contains the date when the user logged in
    private Integer tableId; // containes the primary table key
    public LogedInUser() {
        super();
    }

    public LogedInUser(User user, String date) {
        this.date = date;
        this.setPassword(user.getPassword());
        this.setUpdatedAt(user.getUpdatedAt());
        this.setEmail(user.getEmail());
        this.setName(user.getName());
        this.set_csrf(user.get_csrf());
        this.setConfirmation(user.getConfirmation());
        this.setCreatedAt(user.getCreatedAt());
        this.setId(user.getId());
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + "LogedInUser{" +
                "date='" + date + '\'' +
                ", tableId=" + tableId +
                '}';
    }
}
