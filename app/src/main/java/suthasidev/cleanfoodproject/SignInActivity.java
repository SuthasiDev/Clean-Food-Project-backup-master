package suthasidev.cleanfoodproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Bind Widget
        bindWidget();

    }   // Main Method

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
    }

    public void clickSignInAuthen(View view) {
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //check space
        if (userString.equals("") || passwordString.equals("")) {
            //have space
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.myDialog(SignInActivity.this, "มีช่องว่าง", "กรุณากรอกทุกช่องค่ะ");

        } else {
            //no space
            checkUser();

        }

    }   // clickSignInAuthen

    private void checkUser() {
        try {
            MyManage myManage = new MyManage(this);
            String[] myResultStrings = myManage.searchUser(userString);

            //check password
            if (passwordString.equals(myResultStrings[2])) {
                //password true
                Intent intent = new Intent(SignInActivity.this, ServiceActivity.class);
                intent.putExtra("userName", myResultStrings[3]);
                startActivity(intent);
                finish();

            } else {
                //password fail
                MyAlertDialog myAlertDialog = new MyAlertDialog();
                myAlertDialog.myDialog(SignInActivity.this, "Password ผิด",
                        "Password ผิด ลองพิมพ์ใหม่อีกทีค่ะ");

            }

        } catch (Exception e) {
            //no this user
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.myDialog(SignInActivity.this, "ไม่มี User",
                    "ไม่มี " + userString + " ในฐานข้อมูลของเรา");

        }

    }   //checkUser

}   // Main Class
