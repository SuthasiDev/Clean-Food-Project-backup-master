package suthasidev.cleanfoodproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GridviewAdapter extends BaseAdapter {

    private Context mContext;
    private String[] title, img;

    public GridviewAdapter(Context c, String[] title, String[] img) {
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

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.gridview_item, null);

            //For Image
            ImageView ImgView = (ImageView) grid.findViewById(R.id.grid_image);
            Picasso.with(mContext).load(img[position]).resize(250, 200).into(ImgView);

            //For Textview
            TextView titleTextView = (TextView) grid.findViewById(R.id.grid_txt);
            titleTextView.setText(title[position]);

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
