package suthasidev.cleanfoodproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MyAdapter extends BaseAdapter {

    //Explicit
    private Context context;
    private String[] iconString, titleString;

    public MyAdapter(Context context, String[] iconString, String[] titleString) {
        this.context = context;
        this.iconString = iconString;
        this.titleString = titleString;
    }   //Constructor

    @Override
    public int getCount() {
        return iconString.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.recipe_listview, viewGroup, false);

        //For Image
        ImageView iconImageView = (ImageView) view1.findViewById(R.id.imageView);
        Picasso.with(context).load(iconString[i]).resize(100, 100).into(iconImageView);

        //For Textview
        TextView titleTextView = (TextView) view1.findViewById(R.id.textView2);
        titleTextView.setText(titleString[i]);

        return view1;
    }
}   //Main Class
