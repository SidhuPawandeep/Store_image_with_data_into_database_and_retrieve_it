package com.example.lenovo.logindemo2.pojoclasses;

/**
 * Created by lENOVO on 8/29/2017.
 */

public class LoginPojo {
    int id,user_id;
    String name,email,password,phoneNo;
    byte[] image;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LoginPojo(int id, int user_id, byte[] image) {
        this.id=id;
        this.user_id=user_id;
        this.image=image;
    }
    public LoginPojo(int user_id, byte[] image) {

        this.user_id=user_id;
        this.image=image;
    }
    public LoginPojo(int i, String name, String password,String phoneNo,byte[] image,String email) {
        this.id=i;
        this.name=name;
        this.email=email;
        this.phoneNo=phoneNo;
        this.password=password;
        this.image=image;
    }
    public LoginPojo( String name, String email,String phoneNo,String password,byte[] image) {
                this.name=name;
        this.email=email;
        this.phoneNo=phoneNo;
        this.password=password;
        this.image=image;
    }
    public  LoginPojo(){}
    public LoginPojo(String name, String  email,String password){
        this.name = name;
        this.email=email;
        this.password=password;
    }
    // constructor
    public LoginPojo(String email, String password){
        this.email = email;
        this.password = password;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
