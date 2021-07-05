package com.example.cowinvaccinenotifier.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "pincode")
    public int pincode;

   @ColumnInfo(name = "username")
   public String username;

   public User(int pinocde, String username)
   {
       this.pincode = pinocde;
       this.username =  username;
   }

   public User(){}
}
