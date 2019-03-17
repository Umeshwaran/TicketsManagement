package com.example.android.ticketsmanagement.Otherjava;

/**
 * Created by Android on 3/14/2018.
 */

public abstract class Constants{

    public static String IP ="http://192.168.43.73/";


     public static String Admin_URL = IP.concat("Ticket/getusers.php");
    public static String URL_TO_CREATE_USER=IP.concat("Ticket/create_user.php");
   public  static String URL_TO_CREATE_ENGG=IP.concat("Ticket/create_engineer.php");
   public static String URL_FOR_DESC=IP.concat("Ticket/getdata.php");
   public static String URL_FOR_SAVE=IP.concat("Ticket/savedata.php");
   public static String URL_TO_SAVE_USER=IP.concat("Ticket/getuserdetails.php");
   public static String URL_TO_DELETE=IP.concat("Ticket/delete.php");
   public static String URL_FOR_LOGIN=IP.concat("Ticket/testlogin.php");
    public static String URL = IP.concat("Ticket/sample.php");
   public static String URL_FOR_CREATE =IP.concat("Ticket/create_ticket.php");
    public static String URL_FOR_FCM_SAVE=IP.concat("Ticket/fcmsave.php");
    public static String URL_NOTIFY=IP.concat("Ticket/checknotify.php");



}
