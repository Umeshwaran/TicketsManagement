package com.example.android.ticketsmanagement.Otherjava;

/**
 * Created by Android on 10/31/2017.
 */

public class Ticket {
    private String desc, title, status;
    private String ticketid;

    public Ticket() {

    }

    public Ticket(String jid, String jtitle, String jdescription, String jstatus) {
        this.ticketid = jid;
        this.title = jtitle;
        this.desc = jdescription;
        this.status = jstatus;

    }

    public String getTicketid() {
        return ticketid;
    }

    public void setTicketid(String jid) {
        this.ticketid = jid;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String jdesc) {
        this.desc = jdesc;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String jtitle) {
        this.title = jtitle;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String jstatus) {
        this.status = jstatus;
    }

}
