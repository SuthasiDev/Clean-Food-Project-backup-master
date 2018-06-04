package suthasidev.cleanfoodproject;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddCommentActivity extends AppCompatActivity {

    //Explicit
    private TextView dateTextView, nameUserTextView, recipeTextView;
    private EditText commentEditText, userEditText;
    private String dateString, nameUserString, recipeString, commentString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        //Bind Widget
        bindWidget();

        showView();

    }   //Main Method

    private void showView() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        dateString = dateFormat.format(date);
        dateTextView.setText(dateString);

//        nameUserString = getIntent().getStringExtra("nameUser");
//        nameUserTextView.setText(nameUserString);

        recipeString = getIntent().getStringExtra("Recipe");
        recipeTextView.setText(recipeString);


    }

    public void clickSaveComment(View view) {

        commentString = commentEditText.getText().toString().trim();

        if (commentString.equals("")) {
            // ******* //
            Toast.makeText(AddCommentActivity.this, "Please Fill Comment", Toast.LENGTH_SHORT).show();
        } else {
            //No Space
            updateToMySQL();

        }

    }

    private void updateToMySQL() {

        nameUserString = userEditText.getText().toString().trim();

        // Connected Http
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("isAdd", "true"));
            nameValuePairs.add(new BasicNameValuePair("Recipe", recipeString));
            nameValuePairs.add(new BasicNameValuePair("Name", nameUserString));
            nameValuePairs.add(new BasicNameValuePair("Date", dateString));
            nameValuePairs.add(new BasicNameValuePair("Comment", commentString));

            HttpClient httpClient = new DefaultHttpClient();
            MyConstant myConstant = new MyConstant();
            HttpPost httpPost = new HttpPost(myConstant.getUrlPostComment());
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            httpClient.execute(httpPost);



            Toast.makeText(AddCommentActivity.this, "Update Successful",
                    Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(AddCommentActivity.this, "Cannot Update to Server",
                    Toast.LENGTH_SHORT).show();

        }
    }

    private void bindWidget() {

        dateTextView = (TextView) findViewById(R.id.textView8);
        userEditText = (EditText) findViewById(R.id.editName);
        recipeTextView = (TextView) findViewById(R.id.textView10);

        commentEditText = (EditText) findViewById(R.id.editText3);


    }
}   //Main Class
