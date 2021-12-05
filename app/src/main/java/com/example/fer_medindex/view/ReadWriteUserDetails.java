package com.example.fer_medindex.view;

import java.util.UUID;

public class ReadWriteUserDetails {
    private String fullName, DoB, gender, mobile, Email, doctorId;
    private String imgAvatar;

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public ReadWriteUserDetails() {

    }

    public ReadWriteUserDetails(String textfullName, String textEmail, String textDoB,
                                String textGender, String textMobile,String imgHinh) {
        // this.doctorId = UUID.randomUUID().toString();
        this.fullName = textfullName;
        this.Email = textEmail;
        this.DoB = textDoB;
        this.gender = textGender;
        this.mobile = textMobile;
        this.imgAvatar = imgHinh;
    }

    public ReadWriteUserDetails(String textfullName, String textDoB, String textGender, String textMobile) {
        this.fullName = textfullName;
        this.DoB = textDoB;
        this.gender = textGender;
        this.mobile = textMobile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDoB() {
        return DoB;
    }

    public void setDoB(String doB) {
        DoB = doB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}