package jangcho.dailydiary;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-12-09.
 */

public class DotBaseAdapter extends BaseAdapter{
    Context mContext =null;
    ArrayList<Data> mData =null;
    LayoutInflater mLayoutInflater =null;

    public DotBaseAdapter(Context context, ArrayList<Data> data){
        mContext = context;
        mData =data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(mData.get(position).isDB){
            View itemLayout1 = mLayoutInflater.inflate(R.layout.content_view_layout,null);
            TextView content_content = (TextView) itemLayout1.findViewById(R.id.content_content);
            TextView content_week = (TextView) itemLayout1.findViewById(R.id.content_week);
            TextView content_day = (TextView) itemLayout1.findViewById(R.id.content_day);

            content_content.setText(mData.get(position).tempContent);
            content_day.setText(""+mData.get(position).tempDay);

            String mweek = "";
            switch(mData.get(position).tempWeek){
                case 1: mweek = "Sun"; break;
                case 2: mweek = "Mon"; break;
                case 3: mweek = "Tue"; break;
                case 4: mweek = "Wed"; break;
                case 5: mweek = "Thu"; break;
                case 6: mweek = "Fri"; break;
                case 7: mweek = "Sat"; break;


            }

            content_week.setText(mweek);

            return itemLayout1;
        }else {
            View itemLayout = mLayoutInflater.inflate(R.layout.dot_view_item_layout, null);
            ImageView Ib = (ImageView) itemLayout.findViewById(R.id.circle);

            if (mData.get(position).tempWeek == 1) {
                Ib.setImageResource(R.drawable.redcircle);

            }
            return itemLayout;
        }

    }
}
