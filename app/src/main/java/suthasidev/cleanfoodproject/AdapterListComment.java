package suthasidev.cleanfoodproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterListComment extends BaseAdapter {

    private Context context;
    private String[] nameStrings, commentStrings, dateStrings;

    public AdapterListComment(Context context, String[] nameStrings, String[] commentStrings, String[] dateStrings) {
        this.context = context;
        this.nameStrings = nameStrings;
        this.commentStrings = commentStrings;
        this.dateStrings = dateStrings;
    }

    @Override
    public int getCount() {
        return nameStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.listview_comment, viewGroup, false);

        TextView nameTextView = (TextView) view1.findViewById(R.id.txtName);
        TextView commentTextView = (TextView) view1.findViewById(R.id.txtComment);
        TextView dateTextView = (TextView) view1.findViewById(R.id.txtDate);

        nameTextView.setText(nameStrings[i]);
        commentTextView.setText(commentStrings[i]);
        dateTextView.setText(dateStrings[i]);

        return view1;
    }
}
