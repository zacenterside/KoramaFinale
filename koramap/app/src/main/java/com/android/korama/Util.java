package com.android.korama;

import com.android.korama.model.Post;

import java.util.LinkedList;

/**
 * Created by Dell Latitude E7470 on 07/03/2017.
 */

public class Util {

    public  static LinkedList<Post> posts3 = new LinkedList<>();
    public  static LinkedList<Post> posts4 = new LinkedList<>();
    public  static LinkedList<Post> posts5 = new LinkedList<>();
    public  static LinkedList<Post> posts6 = new LinkedList<>();
    public  static LinkedList<Post> posts7 = new LinkedList<>();
    public  static LinkedList<Post> post33 = new LinkedList<>();
    public  static LinkedList<Post> posts8 = new LinkedList<>();
    public  static LinkedList<Post> posts9 = new LinkedList<>();
    public  static LinkedList<Post> posts1 = new LinkedList<>();
    public  static LinkedList<Post> posts10 = new LinkedList<>();

    public static LinkedList<Post> getListCategorie(int i) {

        switch (i) {


            case 3:
                return posts3;


            case 4:
                return posts4;


            case 5:
                return posts5;


            case 6:
                return posts6;


            case 7:
                return posts7;


            case 33:
                return post33;


            case 8:
                return posts8;

            case 9:
                return posts9;


            case 1:
                return posts1;

            case 10:
                return posts10;



        }
    return new LinkedList<>();

    }
}