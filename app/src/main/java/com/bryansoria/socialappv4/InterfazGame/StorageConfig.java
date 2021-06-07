package com.bryansoria.socialappv4.InterfazGame;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bryansoria.socialappv4.Model.Game;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StorageConfig {

    private static final String LIST_KEY = "list_key";

    public static void guardarListaJuego(Context context, ArrayList<Game> list){
        Gson gson=new Gson();
        String jsonString= gson.toJson(list);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    public static ArrayList<Game> leerListaJuego(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY, "");
        Gson gson=new Gson();
        Type type = new TypeToken<ArrayList<Game>>(){}.getType();
        ArrayList<Game> list= gson.fromJson(jsonString, type);
        return list;
    }

    public static ArrayList<Game> eliminarJuego(Context context, ArrayList<Game> list, Game p){
        ArrayList<Game> listP= leerListaJuego(context);
        for(int i=0;i<listP.size();i++){
            if(listP.get(i).getImg() == p.getImg()){
                listP.remove(i);
            }
            guardarListaJuego(context,listP);
        }

        return listP;
    }

}