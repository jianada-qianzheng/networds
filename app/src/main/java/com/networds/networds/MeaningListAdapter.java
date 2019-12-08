//package com.example.networds;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class MeaningListAdapter extends ArrayAdapter<String> {
//
//    private ArrayList<String> dataSet;
//    Context mContext;
//
//    // View lookup cache
//    private static class ViewHolder {
//        TextView meaning;
//
//    }
//
//    public MeaningListAdapter(ArrayList<String> data, Context context) {
//        super(context, R.layout.row_item, data);
//        this.dataSet = data;
//        this.mContext=context;
//
//    }
//
//
//
//    private int lastPosition = -1;
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        //DataModel dataModel = getItem(position);
//        final String text=getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        final ViewHolder viewHolder; // view lookup cache stored in tag
//
//        final View result;
//
//        if (convertView == null) {
//
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.meaning_row, parent, false);
//            viewHolder.meaning = (TextView) convertView.findViewById(R.id.meaning_row);
//
//
//
//            result=convertView;
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//            result=convertView;
//        }
//
//        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        //result.startAnimation(animation);
//        lastPosition = position;
//
//
//
//
//
//
//
//
//        //viewHolder.meaning.setTag(position);
//        // Return the completed view to render on screen
//        return convertView;
//    }
//}