package suthasidev.cleanfoodproject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ListviewAdapter extends BaseAdapter {
    private Context mContext;
    private String[] user, comment, date;

    public ListviewAdapter(Context c, String[] user, String[] comment, String[] date) {
        mContext = c;
        this.user = user;
        this.comment = comment;
        this.date = date;
    }

    @Override
    public int getCount() {
        return user.length;
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

            //For Textview
            TextView userTextView = (TextView) list.findViewById(R.id.list_txt);
            userTextView.setText(user[position]);

            TextView commentTextView = (TextView) list.findViewById(R.id.textView15);
            commentTextView.setText(comment[position]);

            TextView dateTextView = (TextView) list.findViewById(R.id.textView17);
            dateTextView.setText(date[position]);

        } else {
            list = (View) convertView;
        }

        return list;
    }
}
