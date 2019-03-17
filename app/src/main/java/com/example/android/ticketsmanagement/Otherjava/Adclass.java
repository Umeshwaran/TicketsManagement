package com.example.android.ticketsmanagement.Otherjava;

/**
 * Created by Android on 11/10/2017.
 */

public class Adclass {
    private String Name, ucode;
    private int uid;
    private String flag;

    public Adclass() {

    }

    public Adclass(int jid, String jname, String jucode, String jflag) {
        this.uid = jid;
        this.flag = jflag;
        this.Name = jname;
        this.ucode = jucode;

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int jid) {
        this.uid = jid;
    }


    public String getName() {
        return Name;
    }

    public void setName(String jname) {
        this.Name = jname;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String jucode) {
        this.ucode = jucode;
    }


    public String getflag() {
        return flag;
    }

    public void setflag(String jflag) {
        this.flag = jflag;
    }

}
