package com.example.fer_medindex;

import java.util.UUID;

public class ReadWritePatientDetails {
    public String fullname, patientId, ngaysinh, gioitinh, sodienthoai, CMND, diachi, trangthai, email;


    public ReadWritePatientDetails() {

    }

    public ReadWritePatientDetails(String textfullname,String textngaysinh, String textgioitinh, String textsodienthoai, String textCMND, String textemail, String textdiachi, String texttrangthai) {
        this.fullname = textfullname;
        this.patientId = UUID.randomUUID().toString();
        this.ngaysinh = textngaysinh;
        this.gioitinh = textgioitinh;
        this.sodienthoai = textsodienthoai;
        this.CMND = textCMND;
        this.diachi = textdiachi;
        this.trangthai = texttrangthai;
        this.email = textemail;
    }


}
