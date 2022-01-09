package com.vasu.moneytracker;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
public class CustomAdapter extends ArrayAdapter<newClass> {
    private static final String TAG = "PersonListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private static class ViewHolder {
        TextView title;
        TextView Value;
        TextView Date;
    }
    public CustomAdapter(Context context, int resource, ArrayList<newClass> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String title = getItem(position).gettitle();
        String Value = getItem(position).getValue();
        String Date = getItem(position).getDate();

        //Create the person object with the information
        newClass Newclass = new newClass(title,Value,Date);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.textView);
            holder.Value = (TextView) convertView.findViewById(R.id.textView2);
            holder.Date = (TextView) convertView.findViewById(R.id.textView3);
            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        lastPosition = position;

        holder.title.setText(Newclass.gettitle());
        holder.Value.setText(Newclass.getValue());
        holder.Date.setText(Newclass.getDate());

        return convertView;
    }
}
