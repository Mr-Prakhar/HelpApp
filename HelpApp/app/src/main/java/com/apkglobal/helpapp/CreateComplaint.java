package com.apkglobal.helpapp;

public class CreateComplaint {
    public String name;
    public String place,mobile,messege,date,imageurl;
    //default Constructer
    public CreateComplaint()
    {

    }

    public CreateComplaint( String name,String place,String mobile,String messege,String date,String imageurl)
    {
        this.name = name;
        this.place = place;
        this.mobile = mobile;
        this.messege= messege;
        this.date = date;
        this.imageurl=imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
