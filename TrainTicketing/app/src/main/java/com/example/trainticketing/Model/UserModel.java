package com.example.trainticketing.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("nic")
    @Expose
    private String nic;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("type ")
    @Expose
    private int type ;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "_id='" + _id + '\'' +
                ", fullname='" + fullname + '\'' +
                ", nic='" + nic + '\'' +
                ", phone='" + phone + '\'' +
                ", email=" + email +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", type=" + type +
                '}';
    }
}
