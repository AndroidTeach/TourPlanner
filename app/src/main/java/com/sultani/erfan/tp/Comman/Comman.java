package com.sultani.erfan.tp.Comman;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sultani.erfan.tp.Model.User;

/**
 * Created by Sultani on 10/17/2017.
 */

public class Comman {

    public static User currentUser;

    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Progress";

        else if(status.equals("1"))
            return "Paid";
        else
            return "Not Paid";

    }

    public static final String DELETE = "Delete";
    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";


    public  static Boolean isConnectedToInternet(Context context){

        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager!= null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info!=null){

                for (int i=0;i<info.length;i++){
                    if (info[i].getState()== NetworkInfo.State.CONNECTED)
                        return  true;


                }
            }


        }
             return false;
    }


}
