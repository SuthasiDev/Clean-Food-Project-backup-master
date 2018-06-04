package suthasidev.cleanfoodproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyManage {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static String table_user = "userTABLE";
    public static String table_recipe = "recipeTABLE";
    public static String table_restaurant = "restaurantTABLE";
    public static String table_comment = "commmentTABLE";

    public static final String column__id = "_id";
    public static final String column_User = "User";
    public static final String column_Password = "Password";
    public static final String column_Name = "Name";

    public static final String column_Recipe = "Recipe";
    public static final String column_Ingredients = "Ingredients";
    public static final String column_HowTo = "HowTo";
    public static final String column_Description = "Description";
    public static final String column_ImageRecipe = "ImageRecipe";

    public static final String column_Restaurant = "Restaurant";
    public static final String column_ImageRestaurant = "ImageRestaurant";
    public static final String column_Phone = "Phone";
    public static final String column_Address = "Address";
    public static final String column_Website = "Website";
    public static final String column_Lat = "Lat";
    public static final String column_Lng = "Lng";


    public static final String column_Comment = "Comment";
    public static final String column_Date = "Date";

    public MyManage(Context context) {

        //Connected DB
        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    } //Constructors

    public String[] searchUser(String strUser) {

        try {
            String[] resultStrings = null;
            Cursor cursor = readSqLiteDatabase.query(table_user,
                    new String[] {column__id, column_User, column_Password, column_Name},
                    column_User + "=?",
                    new String[] {String.valueOf(strUser)},
                    null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    resultStrings = new String[cursor.getColumnCount()];
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        resultStrings[i] = cursor.getString(i);
                    }
                }
            }
            cursor.close();
            return resultStrings;

        } catch (Exception e) {
            return null;
        }
    }

    public long addRestaurant(String strRestaurant,
                              String strImgRes,
                              String strPhone,
                              String strAddress,
                              String strWeb,
                              String strLat,
                              String strLng) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(column_Restaurant, strRestaurant);
        objContentValues.put(column_ImageRestaurant, strImgRes);
        objContentValues.put(column_Phone, strPhone);
        objContentValues.put(column_Address, strAddress);
        objContentValues.put(column_Website, strWeb);
        objContentValues.put(column_Lat, strLat);
        objContentValues.put(column_Lng, strLng);

        return writeSqLiteDatabase.insert(table_restaurant, null, objContentValues);
    }

    public long addRecipe(String strRecipe,
                          String strIngredients,
                          String strHowTo,
                          String strDescription,
                          String strImg) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(column_Recipe, strRecipe);
        objContentValues.put(column_Ingredients, strIngredients);
        objContentValues.put(column_HowTo, strHowTo);
        objContentValues.put(column_Description, strDescription);
        objContentValues.put(column_ImageRecipe, strImg);

        return writeSqLiteDatabase.insert(table_recipe, null, objContentValues);
    }

    public long addComment(String strRecipe,
                          String strName,
                          String strDate,
                          String strComment) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(column_Recipe, strRecipe);
        objContentValues.put(column_Name, strName);
        objContentValues.put(column_Date, strDate);
        objContentValues.put(column_Comment, strComment);

        return writeSqLiteDatabase.insert(table_comment, null, objContentValues);
    }

    public long addUser(String strUser,
                        String strPassword,
                        String strName) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(column_User, strUser);
        objContentValues.put(column_Password, strPassword);
        objContentValues.put(column_Name, strName);

        return writeSqLiteDatabase.insert(table_user, null, objContentValues);
    }

} //Main Class
