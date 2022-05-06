package com.example.jdawgsquareservices;

public class User {
    private String phone;
    private String note;

    public User(String phone, String note) {
        this.phone = phone;
        this.note = note;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
