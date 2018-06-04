package suthasidev.cleanfoodproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ServiceActivity extends AppCompatActivity {

    //Explicit
    private String userStr;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        userStr = getIntent().getStringExtra("userName");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarService);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFragment, new AddRecipeFragment())
                    .commit();
        }

    }   //Main Method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_service, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        Recipe
        if (item.getItemId() == R.id.itemAddRecipe) {
            Log.d("6octV1", "add recipe");
            Intent intent = new Intent(ServiceActivity.this, AddRecipeFragment.class);
            intent.putExtra("nameRecipe", userStr);
            startActivity(intent);
            getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, new AddRecipeFragment()).commit();
        }

//        Restaurant
        if (item.getItemId() == R.id.itemAddRestaurant) {
            Log.d("6octV1", "add restaurant");
            getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, new AddRestautantFragment()).commit();
        }

        return super.onOptionsItemSelected(item);
    }
}   //Main Class
