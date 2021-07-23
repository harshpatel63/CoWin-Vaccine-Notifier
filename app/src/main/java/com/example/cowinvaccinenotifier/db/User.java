package com.example.cowinvaccinenotifier.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.ArrayList;

@Entity
public class User {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "pincode")
    public int pincode;

   @ColumnInfo(name = "username")
   public String username;

   @ColumnInfo(name = "vaccines")
   public ArrayList<String> vaccines;

    @ColumnInfo(name = "fees")
    public ArrayList<String> fees;

    @ColumnInfo(name = "age")
    public ArrayList<String> age;

   public User(int pinocde, String username, ArrayList<String> vaccines, ArrayList<String> fees, ArrayList<String> age)
   {
       this.pincode = pinocde;
       this.username =  username;
       this.vaccines = vaccines;
       this.fees = fees;
       this.age = age;
   }

   public User(){}
}
