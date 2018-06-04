package suthasidev.cleanfoodproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyOpenHelper extends SQLiteOpenHelper{

    // Explicit
    public static final String database_name = "food.db";
    private static final int database_version = 1;

    private static final String create_userTable = "create table userTABLE (" +
            "_id integer primary key, " +
            "User text, " +
            "Password text, " +
            "Name text);";

    private static final String create_recipeTABLE = "create table recipeTABLE (" +
            "_id integer primary key," +
            "Recipe text," +
            "Ingredients text," +
            "HowTo text," +
            "Description text," +
            "ImageRecipe text);";

    private static final String create_restaurantTABLE = "create table restaurantTABLE(" +
            "_id integer primary key," +
            "Restaurant text," +
            "ImageRestaurant text," +
            "Phone text," +
            "Address text," +
            "Website text," +
            "Lat text," +
            "Lng text);";

    private static final String create_commmentTABLE = "create table commmentTABLE(" +
            "_id integer primary key," +
            "Recipe text," +
            "Name text," +
            "Date text," +
            "Comment text);";

    public MyOpenHelper(Context context) {
        super(context, database_name, null, database_version);
    } // Constructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_recipeTABLE);
        db.execSQL(create_restaurantTABLE);
        db.execSQL(create_userTable);
        db.execSQL(create_commmentTABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
} //Main Class
