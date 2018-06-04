package suthasidev.cleanfoodproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class RecipeDetailActivity extends AppCompatActivity {

    //Explicit
    private TextView recipeTextView, descripTextView;
    private ListView ingredientsListView, howToListView;
    private ImageView imageView;
    private String recipeString, descripString, ingredientsString,
            howToString, imageString, nameUserString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        //Bind Widget
        bindWidget();

        //Receive Value From Intent
        receiveValue();

        //Toolbar Controller
        toolbarController();

        //Show View
        showView();

        //Create ListViewComment
        createListComment();


    }   //Main Method

    @Override
    protected void onResume() {
        super.onResume();

        try {
            createListComment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createListComment() {
        String tag = "tag";
        ListView listView = (ListView) findViewById(R.id.listComment);
        MyConstant myConstant = new MyConstant();
        String urlJSON = myConstant.getUrlGetCommentWhereRecipe();

        try {
            MyGetComment myGetComment = new MyGetComment(RecipeDetailActivity.this);
            myGetComment.execute(recipeString, urlJSON);

            String strJSON = myGetComment.get();
            Log.d(tag, "JSON " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            String[] nameStrings = new String[jsonArray.length()];
            String[] dateStrings = new String[jsonArray.length()];
            String[] commentStrings = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nameStrings[i] = jsonObject.getString("Name");
                dateStrings[i] = jsonObject.getString("Date");
                commentStrings[i] = jsonObject.getString("Comment");

            }   // for loop

            AdapterListComment adapterListComment = new AdapterListComment(
                    RecipeDetailActivity.this,
                    nameStrings,
                    commentStrings,
                    dateStrings
            );
            listView.setAdapter(adapterListComment);

        } catch (Exception e) {
            Log.d(tag, "e createList " + e.toString());
        }
    }

    private void toolbarController() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRecipeDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(recipeString);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.imvAddComment);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeDetailActivity.this, AddCommentActivity.class);
                intent.putExtra("Recipe", recipeString);
                intent.putExtra("nameUser", nameUserString);
                startActivity(intent);
            }
        });
    }

    public void clickReadComment(View view) {

        Intent intent = new Intent(RecipeDetailActivity.this, ReadComment.class);
        intent.putExtra("Recipe", recipeString);
        intent.putExtra("nameUser", nameUserString);
        startActivity(intent);

    }   //clickAddComment

    private void showView() {
        //Show TextView
        recipeTextView.setText(recipeString);
        descripTextView.setText(descripString);

        //Show Image
        Picasso.with(RecipeDetailActivity.this).load(imageString)
                .resize(250, 200).into(imageView);

        //Set Ingredients Listview
        String[] ingreSplit = ingredientsString.split("\n");
        ArrayList<String> ingreArrList = new ArrayList<String>();
        for (int i=0; i<ingreSplit.length; i++) {
            ingreArrList.add(ingreSplit[i]);
        }
        String[] ingreArr = {};
        ingreArr = ingreArrList.toArray(new String[ingreArrList.size()]);
        ArrayAdapter<String> ingreAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ingreArr);
        ingredientsListView.setAdapter(ingreAdapter);

        //Set HowTo Listview
        String[] howtoSplit = howToString.split("\n");
        ArrayList<String> howtoArrList = new ArrayList<String>();
        for (int i=0; i<howtoSplit.length; i++) {
            howtoArrList.add(howtoSplit[i]);
        }
        String[] howtoArr = {};
        howtoArr = howtoArrList.toArray(new String[howtoArrList.size()]);
        ArrayAdapter<String> howtoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, howtoArr);
        howToListView.setAdapter(howtoAdapter);
    }

    private void receiveValue() {
        recipeString = getIntent().getStringExtra("nameRecipe");
        imageString = getIntent().getStringExtra("urlRecipe");
        ingredientsString = getIntent().getStringExtra("Ingredients");
        howToString = getIntent().getStringExtra("HowTo");
        descripString = getIntent().getStringExtra("Descrip");
        nameUserString = getIntent().getStringExtra("nameUserString");
    }

    private void bindWidget() {
        recipeTextView = (TextView) findViewById(R.id.textView3);
        descripTextView = (TextView) findViewById(R.id.textView7);

        ingredientsListView = (ListView) findViewById(R.id.listView2);
        howToListView = (ListView) findViewById(R.id.listView3);

        imageView = (ImageView) findViewById(R.id.imageView2);
    }

}   //Main Class
