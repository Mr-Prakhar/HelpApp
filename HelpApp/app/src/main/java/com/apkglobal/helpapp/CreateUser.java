package com.apkglobal.helpapp;

public class CreateUser
{

    public String uname;
    public String uemail,uroll,urepass,utype,ugender,complaint;
    //default Constructer
    public CreateUser()
    {

    }

    public CreateUser( String uname,String uroll,String uemail,String repass,String radioval, String utype,String complaint)
    {
        this.uname = uname;
        this.uroll = uroll;
        this.uemail = uemail;
        this.urepass = repass;
        this.utype = utype;
        this.ugender = radioval;
        this.complaint = complaint;
    }
}