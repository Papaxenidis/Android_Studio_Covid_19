package com.example.covid_examiner;

public class User {

    public String first_name,last_name,phone_number,email,ar_katataxis,pass;


    //ΒΟΗΘΗΤΙΚΗ ΚΛΑΣΗ USER

    public User()
    {

    }

    //ΜΕΤΑΒΙΒΑΣΗ ΔΕΔΟΜΕΝΩΝ ΤΟΥ ΧΡΗΣΗ.ΜΕΤΑ ΧΡΗΣΙΜΟΠΟΙΟΥΝΤΑΙ ΓΙΑ ΝΑ ΑΠΟΘΗΚΕΥΤΟΥΝ ΣΤΗΝ ΒΑΣΗ
    public User(String fname,String lname,String email,String ark,String fnum,String pass)
    {
        this.first_name = fname;
        this.last_name = lname;
        this.email = email;
        this.ar_katataxis = ark;
        this.phone_number= fnum;
        this.pass = pass;

    }



}
