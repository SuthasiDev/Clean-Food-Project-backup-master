package suthasidev.cleanfoodproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.provider.MediaStore;
import android.database.Cursor;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class AddRecipeFragment extends Fragment {

    private Uri uri;
    private ImageView imageView;
    private EditText recipeEditTxt, ingredientsEditTxt, howtoEditTxt, DescriptEditTxt;
    private Button btnSave;
    private String recipeString, descripString, ingredientsString,
            howToString, imageString, nameUserString;
    public String[] FILE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        return view;
    }   //onCreateView

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Image Controller
        imageController();

        //Bind Widget
        bindWidget();

        // Get Data
        getData();

    }   //onActivityCreated

    private void getData() {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateToMySQL();

//                if (recipeString.equals("")) {
//                    // ******* //
//                    Toast.makeText(getActivity(), "Please Fill Recipe", Toast.LENGTH_SHORT).show();
//                } else {
//                    //No Space
//                    updateToMySQL();
//
//                }
            }
        });

    }

    private void imageController() {

        imageView = (ImageView) getView().findViewById(R.id.imgProfile);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Please Choose App"), 1);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {

            Log.d("tag", "Choose Success");

            try {

                uri = data.getData();
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

//    public void clickSaveRecipe(View v) {
//
//        recipeString = recipeEditTxt.getText().toString().trim();
//        ingredientsString = ingredientsEditTxt.getText().toString().trim();
//        howToString = howtoEditTxt.getText().toString().trim();
//        descripString = DescriptEditTxt.getText().toString().trim();
//
//        if (recipeString.equals("")) {
//            // ******* //
//            Toast.makeText(getActivity(), "Please Fill Comment", Toast.LENGTH_SHORT).show();
//        } else {
//            //No Space
//            updateToMySQL();
//
//        }
//
//    }


    private void updateToMySQL() {

        nameUserString = getActivity().getIntent().getStringExtra("userName");
        recipeString = recipeEditTxt.getText().toString().trim();
        ingredientsString = ingredientsEditTxt.getText().toString().trim();
        howToString = howtoEditTxt.getText().toString().trim();
        descripString = DescriptEditTxt.getText().toString().trim();

        Log.d("tag", nameUserString);
        Log.d("tag", recipeString);
        Log.d("tag", ingredientsString);
        Log.d("tag", howToString);
        Log.d("tag", descripString);
        //Log.d("tag", imageString);

        //Connected Http
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("isAdd", "true"));
            nameValuePairs.add(new BasicNameValuePair(MyManage.column_Recipe, recipeString));
            nameValuePairs.add(new BasicNameValuePair(MyManage.column_Ingredients, ingredientsString));
            nameValuePairs.add(new BasicNameValuePair(MyManage.column_HowTo, howToString));
            nameValuePairs.add(new BasicNameValuePair(MyManage.column_Description, descripString));
            //nameValuePairs.add(new BasicNameValuePair(MyManage.column_Recipe, recipeString));

            HttpClient objHttpClient = new DefaultHttpClient();
            MyConstant myConstant = new MyConstant();
            HttpPost objHttpPost = new HttpPost(myConstant.getUrlPostRecipe());
            objHttpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            objHttpClient.execute(objHttpPost);

            Toast.makeText(getActivity(), "Update Successful",
                    Toast.LENGTH_SHORT).show();
            getActivity().finish();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Cannot Update to Server",
                    Toast.LENGTH_SHORT).show();

        }
    }

    private void bindWidget() {

        recipeEditTxt  = (EditText) getView().findViewById(R.id.edtRecipe);
        ingredientsEditTxt = (EditText) getView().findViewById(R.id.edtIngredients);
        howtoEditTxt = (EditText) getView().findViewById(R.id.edtHowTo);
        DescriptEditTxt = (EditText) getView().findViewById(R.id.edtDescription);

        btnSave = (Button) getView().findViewById(R.id.btnSave);

    }

}   //Main Class