package suthasidev.cleanfoodproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadComment extends AppCompatActivity {
    TextView recipeTextView;
    ListView commentList;

    String recipeString, nameUserString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comment);

        bindWidget();

        receiveValue();

        setObject();
    }

    private void setObject() {
        //Set TextView
        recipeTextView.setText(recipeString);

        //Set ListView
        //Read All SQLite
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT `Recipe` FROM `commmentTABLE` WHERE 1", null);
        cursor.moveToFirst();

        int intCount = cursor.getCount();
        final String[] recipeArray = new String[intCount];
        final String[] nameArray = new String[intCount];
        final String[] dateArray = new String[intCount];
        final String[] commentArray = new String[intCount];

        for (int i = 0; i < intCount; i++) {
            recipeArray[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Recipe));
            nameArray[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Name));
            dateArray[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Date));
            commentArray[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Comment));

            cursor.moveToNext();
        }   //for
        cursor.close();

        ListviewAdapter adapter = new ListviewAdapter(ReadComment.this, nameArray, commentArray, dateArray);
        commentList.setAdapter(adapter);
    }

    private void receiveValue() {
        recipeString = getIntent().getStringExtra("Recipe");
        nameUserString = getIntent().getStringExtra("nameUser");
    }

    private void bindWidget() {
        recipeTextView = (TextView) findViewById(R.id.textView14);
        commentList = (ListView) findViewById(R.id.commentList);
    }
}
