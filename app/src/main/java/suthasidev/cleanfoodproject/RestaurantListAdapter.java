package suthasidev.cleanfoodproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RestaurantListAdapter extends BaseAdapter {
    private Context mContext;
    private String[] title, img;

    public RestaurantListAdapter (Context c, String[] title, String[] img) {
        mContext = c;
        this.title = title;
        this.img = img;
    }

    @Override
    public int getCount() {
        return title.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View list;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            list = new View(mContext);
            list = inflater.inflate(R.layout.listview_item, null);

            //For Image
            ImageView ImgView = (ImageView) list.findViewById(R.id.list_img);
            Picasso.with(mContext).load(img[position]).resize(200, 200).into(ImgView);

            //For Textview
            TextView titleTextView = (TextView) list.findViewById(R.id.list_txt);
            titleTextView.setText(title[position]);

        } else {
            list = (View) convertView;
        }

        return list;
    }
}

