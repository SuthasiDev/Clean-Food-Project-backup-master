package suthasidev.cleanfoodproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private MyManage objMyManage;

    private GridView gridView;

    private String nameUserString;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    /*public int[] objImage = new int[]{R.drawable.android,
            R.drawable.marshmallow, R.drawable.lollipop, R.drawable.kitkat,
            R.drawable.jelly_bean, R.drawable.ice_cream_sandwich};

    public String[] objStrings = {"android-logo", "marshmallow-android",
            "lollipop-android", "kitkat-android", "jelly-bean-android",
            "ice-cream-sandwich-android"};*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create
        objMyManage = new MyManage(this);

        //Test Add Value
        //testAddValue();

        //Delete All SQLite
        deleteAllSQLite();

        //synchronize JSON to SQLite
        synJSONtoSQLite();

        //Create Toolbar
        createToolbar();

        //Set Gridview Adepter
        setGridview();


    }  //Main Method

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerMain);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                R.string.open,
                R.string.close
        );
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }   // createToolbar


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickSignIn(View view) {
        startActivity(new Intent(MainActivity.this, SignInActivity.class));
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        deleteAllSQLite();
        synJSONtoSQLite();

    }

    private void setGridview() {
        //Read All SQLite
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyManage.table_recipe, null);
        cursor.moveToFirst();

        int intCount = cursor.getCount();
        final String[] recipeString = new String[intCount];
        final String[] ingredientsStrings = new String[intCount];
        final String[] howToStrings = new String[intCount];
        final String[] descriptionStrings = new String[intCount];
        final String[] imageRecipeStrings = new String[intCount];

        for (int i = 0; i < intCount; i++) {
            recipeString[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Recipe));
            ingredientsStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Ingredients));
            howToStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_HowTo));
            descriptionStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Description));
            imageRecipeStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_ImageRecipe));

            cursor.moveToNext();
        }   //for
        cursor.close();


        GridviewAdapter adapter = new GridviewAdapter(MainActivity.this, recipeString, imageRecipeStrings);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, RecipeDetailActivity.class);
                intent.putExtra("nameRecipe", recipeString[position]);
                intent.putExtra("urlRecipe", imageRecipeStrings[position]);
                intent.putExtra("Ingredients", ingredientsStrings[position]);
                intent.putExtra("HowTo", howToStrings[position]);
                intent.putExtra("Descrip", descriptionStrings[position]);
                intent.putExtra("nameUser", nameUserString);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Your Clicked at" + recipeString[position], Toast.LENGTH_LONG).show();
            }
        });
    }


    private void synJSONtoSQLite() {

        //change policy
        StrictMode.ThreadPolicy MyPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(MyPolicy);

        int intTABLE = 0;
        while (intTABLE <= 3) {
            //InputStream
            InputStream objInputStream = null;

            MyConstant myConstant = new MyConstant();
            String[] urlStrings = myConstant.getUrlAddData();

            String tag = "CleanFood";

            try {
                HttpClient objHttpClient = new DefaultHttpClient();
                HttpPost objHttpPost = new HttpPost(urlStrings[intTABLE]);
                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();

            } catch (Exception e) {
                Log.d(tag, "InputStream ==> " + e.toString());

            }

            //JSON String
            String strJSON = null;
            try {

                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
                StringBuilder objStringBuilder = new StringBuilder();
                String strLine = null;
                while ((strLine = objBufferedReader.readLine()) != null) {
                    objStringBuilder.append(strLine);

                }   //while

                objInputStream.close();
                strJSON = objStringBuilder.toString();

            } catch (Exception e) {
                Log.d(tag, "strJSON ==> " + e.toString());
            }

            //Update SQLite
            try {
                JSONArray objJsonArray = new JSONArray(strJSON);
                for (int i = 0; i < objJsonArray.length(); i++) {
                    JSONObject jsonObject = objJsonArray.getJSONObject(i);

                    switch (intTABLE) {
                        case 0:
                            //userTABLE
                            String strUser = jsonObject.getString(MyManage.column_User);
                            String strPassword = jsonObject.getString(MyManage.column_Password);
                            String strName = jsonObject.getString(MyManage.column_Name);
                            objMyManage.addUser(strUser, strPassword, strName);
                            break;

                        case 1:
                            //recipeTABLE
                            String strRecipe = jsonObject.getString(MyManage.column_Recipe);
                            String strIngredients = jsonObject.getString(MyManage.column_Ingredients);
                            String strHowTo = jsonObject.getString(MyManage.column_HowTo);
                            String strDescription = jsonObject.getString(MyManage.column_Description);
                            String strImg = jsonObject.getString(MyManage.column_ImageRecipe);
                            objMyManage.addRecipe(strRecipe, strIngredients, strHowTo, strDescription, strImg);
                            break;

                        case 2:
                            //restaurantTABLE
                            String strRestaurant = jsonObject.getString(MyManage.column_Restaurant);
                            String strImgRes = jsonObject.getString(MyManage.column_ImageRestaurant);
                            String strPhone = jsonObject.getString(MyManage.column_Phone);
                            String strAddress = jsonObject.getString(MyManage.column_Address);
                            String strWeb = jsonObject.getString(MyManage.column_Website);
                            String strLat = jsonObject.getString(MyManage.column_Lat);
                            String strLng = jsonObject.getString(MyManage.column_Lng);
                            objMyManage.addRestaurant(strRestaurant, strImgRes, strPhone, strAddress, strWeb, strLat, strLng);
                            break;

                        case 3:
                            //commmentTABLE
                            String strCRecipe = jsonObject.getString(MyManage.column_Recipe);
                            String strCUser = jsonObject.getString(MyManage.column_Name);
                            String strCDate = jsonObject.getString(MyManage.column_Date);
                            String strCComment = jsonObject.getString(MyManage.column_Comment);

                            Log.d("5octV1", "CRecipe ==>" + strCRecipe);
                            Log.d("5octV1", "CUser ==>" + strCUser);
                            Log.d("5octV1", "CDate ==>" + strCDate);
                            Log.d("5octV1", "CComment ==>" + strCComment);

                            objMyManage.addComment(strCRecipe, strCUser, strCDate, strCComment);
                            break;
                    }

                }   //for

            } catch (Exception e) {
                Log.d("5octV1", "Error e => " + e.toString());
            }

            intTABLE += 1;
        }   //while

    }   //synJSONtoSQLite

    public void clickSignUpMain(View view) {

        startActivity(new Intent(MainActivity.this, RegisterActivity.class));

    } //clickSignUpMain

    private void deleteAllSQLite() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);

        objSqLiteDatabase.delete(MyManage.table_user, null, null);
        objSqLiteDatabase.delete(MyManage.table_recipe, null, null);
        objSqLiteDatabase.delete(MyManage.table_restaurant, null, null);


    } //deleteAllSQLite

    private void testAddValue() {
        objMyManage.addUser("user", "password", "name");
        objMyManage.addRecipe("recipe", "ingredients", "How-To",
                "description", "Image");
        objMyManage.addRestaurant("Res", "ImgRes", "phone", "Address",
                "Website", "Latitude", "Longtitude");


    } //testAddValuel
} //Main Class
