package suthasidev.cleanfoodproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ShowRestaurant extends AppCompatActivity {

    ListView restaurantListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_restaurant);

        bindWidget();

        createListview();
    }

    private void createListview() {

        //Read All SQLite
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyManage.table_restaurant, null);
        cursor.moveToFirst();

        int intCount = cursor.getCount();
        final String[] resName = new String[intCount];
        final String[] resImg = new String[intCount];
        final String[] resAddress = new String[intCount];
        final String[] resPhone = new String[intCount];
        final String[] resWebsite = new String[intCount];
        final String[] resLat = new String[intCount];
        final String[] resLong = new String[intCount];

        for (int i = 0; i < intCount; i++) {
            resName[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Restaurant));
            resImg[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_ImageRestaurant));
            resAddress[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Address));
            resPhone[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Phone));
            resWebsite[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Website));
            resLat[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Lat));
            resLong[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Lng));

            cursor.moveToNext();
        }   //for
        cursor.close();

        RestaurantListAdapter myAdapter = new RestaurantListAdapter(ShowRestaurant.this, resName, resImg);
        restaurantListview.setAdapter(myAdapter);

        restaurantListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ShowRestaurant.this, ShowRestaurantDetail.class);
                intent.putExtra("resName", resName[position]);
                intent.putExtra("resImg", resImg[position]);
                intent.putExtra("resAddress", resAddress[position]);
                intent.putExtra("resPhone", resPhone[position]);
                intent.putExtra("resWebsite", resWebsite[position]);
                intent.putExtra("resLat", resLat[position]);
                intent.putExtra("resLong", resLong[position]);
                startActivity(intent);
            }   //OnItemClick
        });
    }

    private void bindWidget() {
        restaurantListview = (ListView) findViewById(R.id.listView4);
    }
}
