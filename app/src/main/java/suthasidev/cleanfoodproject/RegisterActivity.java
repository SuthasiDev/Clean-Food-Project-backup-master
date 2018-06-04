package suthasidev.cleanfoodproject;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    //explicit
    private EditText userEditText, passwordEditText, nameEditText;
    private String userString, passwordString, nameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //bind widget
        bindWidget();

    }   //main method

    public void clickSignUpRegis(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();
        nameString = nameEditText.getText().toString().trim();

        //check space
        if (userString.equals("") || passwordString.equals("") || nameString.equals("")) {
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.myDialog(RegisterActivity.this, "มีช่องว่าง", "กรุณากรอกทุกช่อง");

        } else {
            updateNewValue();

        } //if


    }   //clickSignUpRegis

    private void updateNewValue() {
        //Connected Http
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        //update to mySQL
        try {
            ArrayList<NameValuePair> objNameValuePairs = new ArrayList<NameValuePair>();
            objNameValuePairs.add(new BasicNameValuePair("isAdd", "true"));
            objNameValuePairs.add(new BasicNameValuePair(MyManage.column_User, userString));
            objNameValuePairs.add(new BasicNameValuePair(MyManage.column_Password, passwordString));
            objNameValuePairs.add(new BasicNameValuePair(MyManage.column_Name, nameString));

            HttpClient objHttpClient = new DefaultHttpClient();
            MyConstant myConstant = new MyConstant();
            HttpPost objHttpPost = new HttpPost(myConstant.getUrlPostUser());
            objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
            objHttpClient.execute(objHttpPost);

            Toast.makeText(RegisterActivity.this, "Update Finish", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, "cannot update to mySQL", Toast.LENGTH_SHORT).show();
        }

    }   //updateNewValue

    private void bindWidget() {

        userEditText = (EditText) findViewById(R.id.edtUser);
        passwordEditText = (EditText) findViewById(R.id.edtPassword);
        nameEditText = (EditText) findViewById(R.id.edtName);
    }

}   //main class
