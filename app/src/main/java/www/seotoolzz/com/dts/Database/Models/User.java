package www.seotoolzz.com.dts.Database.Models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName ="users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;


    private String user_account_id;

    private String user_account_md5_id;

    private String user_account_image;

    private String user_account_name;

    private String user_account_department;

    private String user_account_station;

    private String user_account_contact;

    private String user_account_designation;

    private String user_account_status;

    private String user_account_datetime;

    private String user_account_username;

    private String user_account_password;

    private String user_account_security;

    private String user_account_active;

    private String user_account_text_code;

    private String user_account_code1;

    private String user_account_code2;

    private String user_account_code3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_account_id() {
        return user_account_id;
    }

    public void setUser_account_id(String user_account_id) {
        this.user_account_id = user_account_id;
    }

    public String getUser_account_md5_id() {
        return user_account_md5_id;
    }

    public void setUser_account_md5_id(String user_account_md5_id) {
        this.user_account_md5_id = user_account_md5_id;
    }

    public String getUser_account_image() {
        return user_account_image;
    }

    public void setUser_account_image(String user_account_image) {
        this.user_account_image = user_account_image;
    }

    public String getUser_account_name() {
        return user_account_name;
    }

    public void setUser_account_name(String user_account_name) {
        this.user_account_name = user_account_name;
    }

    public String getUser_account_department() {
        return user_account_department;
    }

    public void setUser_account_department(String user_account_department) {
        this.user_account_department = user_account_department;
    }

    public String getUser_account_station() {
        return user_account_station;
    }

    public void setUser_account_station(String user_account_station) {
        this.user_account_station = user_account_station;
    }

    public String getUser_account_contact() {
        return user_account_contact;
    }

    public void setUser_account_contact(String user_account_contact) {
        this.user_account_contact = user_account_contact;
    }

    public String getUser_account_designation() {
        return user_account_designation;
    }

    public void setUser_account_designation(String user_account_designation) {
        this.user_account_designation = user_account_designation;
    }

    public String getUser_account_status() {
        return user_account_status;
    }

    public void setUser_account_status(String user_account_status) {
        this.user_account_status = user_account_status;
    }

    public String getUser_account_datetime() {
        return user_account_datetime;
    }

    public void setUser_account_datetime(String user_account_datetime) {
        this.user_account_datetime = user_account_datetime;
    }

    public String getUser_account_username() {
        return user_account_username;
    }

    public void setUser_account_username(String user_account_username) {
        this.user_account_username = user_account_username;
    }

    public String getUser_account_password() {
        return user_account_password;
    }

    public void setUser_account_password(String user_account_password) {
        this.user_account_password = user_account_password;
    }

    public String getUser_account_security() {
        return user_account_security;
    }

    public void setUser_account_security(String user_account_security) {
        this.user_account_security = user_account_security;
    }

    public String getUser_account_active() {
        return user_account_active;
    }

    public void setUser_account_active(String user_account_active) {
        this.user_account_active = user_account_active;
    }

    public String getUser_account_text_code() {
        return user_account_text_code;
    }

    public void setUser_account_text_code(String user_account_text_code) {
        this.user_account_text_code = user_account_text_code;
    }

    public String getUser_account_code1() {
        return user_account_code1;
    }

    public void setUser_account_code1(String user_account_code1) {
        this.user_account_code1 = user_account_code1;
    }

    public String getUser_account_code2() {
        return user_account_code2;
    }

    public void setUser_account_code2(String user_account_code2) {
        this.user_account_code2 = user_account_code2;
    }

    public String getUser_account_code3() {
        return user_account_code3;
    }

    public void setUser_account_code3(String user_account_code3) {
        this.user_account_code3 = user_account_code3;
    }


}
