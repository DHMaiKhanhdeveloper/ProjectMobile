package com.example.fer_medindex.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ReadWritePatientDetails {
    public String fullname, patientId, ngaysinh, gioitinh, sodienthoai, cmnd, diachi, trangthai, email,tinhtrangbenh;
    public Date createTime;
    public String imgHinh;

    public ReadWritePatientDetails() {

    }

    public String getImgHinh() {
        return imgHinh;
    }

    public void setImgHinh(String imgHinh) {
        this.imgHinh = imgHinh;
    }



    public ReadWritePatientDetails(String textfullname, String textngaysinh, String textgioitinh, String textsodienthoai, String textcmnd, String textemail, String textdiachi, String texttrangthai, String imgHinh) {
        this.patientId = UUID.randomUUID().toString();
        this.fullname = textfullname;
        this.imgHinh = imgHinh;
        this.ngaysinh = textngaysinh;
        this.gioitinh = textgioitinh;
        this.sodienthoai = textsodienthoai;
        this.cmnd = textcmnd;
        this.diachi = textdiachi;
        this.trangthai = texttrangthai;
        this.email = textemail;
        this.createTime = new Date();
    }
    public ReadWritePatientDetails(String textfullname, String textngaysinh, String textgioitinh, String textsodienthoai, String textcmnd, String textemail, String textdiachi, String texttrangthai, String imgHinh,String tinhtrangbenh) {
        this.patientId = UUID.randomUUID().toString();
        this.fullname = textfullname;
        this.imgHinh = imgHinh;
        this.ngaysinh = textngaysinh;
        this.gioitinh = textgioitinh;
        this.sodienthoai = textsodienthoai;
        this.cmnd = textcmnd;
        this.diachi = textdiachi;
        this.trangthai = texttrangthai;
        this.email = textemail;
        this.createTime = new Date();
        this.tinhtrangbenh = tinhtrangbenh;
    }
    public ReadWritePatientDetails(String textfullname, String textemail) {
        this.fullname = textfullname;
        this.email = textemail;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getCMND() {
        return cmnd;
    }

    public void setCMND(String CMND) {
        this.cmnd = CMND;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getTinhtrangbenh() {
        return tinhtrangbenh;
    }

    public void setTinhtrangbenh(String tinhtrangbenh) {
        this.tinhtrangbenh = tinhtrangbenh;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCreateTimeString() {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        return dateFormat.format(createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
