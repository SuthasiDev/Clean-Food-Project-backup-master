package suthasidev.cleanfoodproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


public class AddRestaurant extends AppCompatActivity {

    private GoogleApiClient client;
    private LocationManager locationManager;
    private Criteria criteria;
    private boolean GPSABoolean, networkABoolean;

    private EditText editTxtName, editTxtImg, editTxtAddress, editTxtPhone, editTxtWeb;
    private TextView txtViewLat, txtViewLong;
    private String resName, resImg, resAddress, resPhone, resWeb, resLat, resLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        bindWidget();

        //Setup Location
        setupLocation();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }   //onCreate

    private Location requestLocation(String strProvider, String strError) {

        Location location = null;
        if (locationManager.isProviderEnabled(strProvider)) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return location;
            }
            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(strProvider);
        } else {
            Log.d("GPS", strError);
        }

        return location;
    }

    private int checkSelfPermission(String accessFineLocation) {
        return 0;
    }

    public final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            txtViewLat.setText(String.format("%.7f", location.getLatitude()));
            txtViewLong.setText(String.format("%.7f", location.getLongitude()));
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void setupLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);

    }

    public void clickAddBtn(View view) {
        resName = editTxtName.getText().toString().trim();
        resImg = editTxtImg.getText().toString().trim();
        resAddress = editTxtAddress.getText().toString().trim();
        resPhone = editTxtPhone.getText().toString().trim();
        resWeb = editTxtWeb.getText().toString().trim();
        resLat = txtViewLat.getText().toString().trim();
        resLong = txtViewLong.getText().toString().trim();


        //check space
        if (resName.equals("") || resImg.equals("") || resAddress.equals("") || resPhone.equals("") || resWeb.equals("")) {
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.myDialog(AddRestaurant.this, "มีช่องว่าง", "กรุณากรอกทุกช่อง");

        } else {
            updateNewValue();
        }

    }

    private void updateNewValue() {
        //Connected Http
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        String img = "http://swiftcodingthai.com/tan/Restaurant/" + resImg + ".png";

        //update to mySQL
        try {
            ArrayList<NameValuePair> objNameValuePairs = new ArrayList<NameValuePair>();
            objNameValuePairs.add(new BasicNameValuePair("isAdd", "true"));
            objNameValuePairs.add(new BasicNameValuePair(MyManage.column_Restaurant, resName));
            objNameValuePairs.add(new BasicNameValuePair(MyManage.column_ImageRestaurant, img));
            objNameValuePairs.add(new BasicNameValuePair(MyManage.column_Address, resAddress));
            objNameValuePairs.add(new BasicNameValuePair(MyManage.column_Phone, resPhone));
            objNameValuePairs.add(new BasicNameValuePair(MyManage.column_Website, resWeb));
            objNameValuePairs.add(new BasicNameValuePair(MyManage.column_Lat, resLat));
            objNameValuePairs.add(new BasicNameValuePair(MyManage.column_Lng, resLong));

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/tan/php_add_restaurant.php");
            objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
            objHttpClient.execute(objHttpPost);

            Toast.makeText(AddRestaurant.this, "Update Finish", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(AddRestaurant.this, "cannot update to mySQL", Toast.LENGTH_SHORT).show();

        }
    }

    private void bindWidget() {
        editTxtName = (EditText) findViewById(R.id.editTxt1);
        editTxtImg = (EditText) findViewById(R.id.editTxt2);
        editTxtAddress = (EditText) findViewById(R.id.editTxt3);
        editTxtPhone = (EditText) findViewById(R.id.editTxt4);
        editTxtWeb = (EditText) findViewById(R.id.editTxt5);

        txtViewLat = (TextView) findViewById(R.id.txtView7);
        txtViewLong = (TextView) findViewById(R.id.txtView9);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(locationListener);

        String strLat = "Unknow";
        String strLng = "Unknow";

        Location networkLocation = requestLocation(LocationManager.NETWORK_PROVIDER, "No Internet");
        if (networkLocation != null) {
            strLat = String.format("%.7f", networkLocation.getLatitude());
            strLng = String.format("%.7f", networkLocation.getLongitude());
        }

        Location gpsLocation = requestLocation(LocationManager.GPS_PROVIDER, "No GPS");
        if (gpsLocation != null) {
            strLat = String.format("%.7f", gpsLocation.getLatitude());
            strLng = String.format("%.7f", gpsLocation.getLongitude());
        }

        txtViewLat.setText(strLat);
        txtViewLong.setText(strLng);

    }   // onResume

    @Override
    public void onStart() {
        super.onStart();

        GPSABoolean = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!GPSABoolean) {
            networkABoolean = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!networkABoolean) {
                Log.d("GPS", "Cannot Find Location");
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(locationListener);
    }

    public void clickCancleBtn (View view) {

        editTxtName.setText("");
        editTxtImg.setText("");
        editTxtAddress.setText("");
        editTxtPhone.setText("");
        editTxtWeb.setText("");
    }
}
