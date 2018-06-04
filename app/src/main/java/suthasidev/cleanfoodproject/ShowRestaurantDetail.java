package suthasidev.cleanfoodproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ShowRestaurantDetail extends AppCompatActivity {

    private String resName, resImg, resAddress, resPhone, resWebsite, resLat, resLong;
    private TextView txtviewName, txtviewAddress;
    private ImageView showImg, btnMap, btnWeb, btnPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_restaurant_detail);

        bindWidget();

        getValue();

        setObject();


    }
    private void setObject() {

        txtviewName.setText(resName);

        Picasso.with(ShowRestaurantDetail.this).load(resImg)
                .resize(250, 200).into(showImg);

        txtviewAddress.setText(resAddress);
    }

    private void getValue() {
        //Receive Value From Intent
        resName = getIntent().getStringExtra("resName");
        resImg = getIntent().getStringExtra("resImg");
        resAddress = getIntent().getStringExtra("resAddress");
    }

    private void bindWidget() {
        txtviewName = (TextView) findViewById(R.id.textView);
        showImg = (ImageView) findViewById(R.id.imageView4);
        txtviewAddress = (TextView) findViewById(R.id.textView12);
        btnMap = (ImageView) findViewById(R.id.imageView);
        btnWeb = (ImageView) findViewById(R.id.imageView2);
        btnPhone = (ImageView) findViewById(R.id.imageView3);

    }
}
