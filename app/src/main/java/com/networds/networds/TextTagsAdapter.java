package com.networds.networds;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by moxun on 16/1/19.
 */
public   class  TextTagsAdapter extends TagsAdapter {

    int NUM_ITEMS = 200;


   // public List<Word> dataSet = new ArrayList<>();

    public TextTagsAdapter(@NonNull Word... data) {
   //public TextTagsAdapter(String[] data) {
        dataSet.clear();

        Collections.addAll(dataSet, data);
    }

    public void lightOn(String s){



        for(int i=0;i<dataSet.size();i++){
            if (dataSet.get(i).getRemember()==0&&dataSet.get(i).getWord().equalsIgnoreCase(s)){
                dataSet.get(i).remember=1;
            }
        }

        //todo arrage list with c




    }


    public void lightOff(String s){



        for(int i=0;i<dataSet.size();i++){

            if (dataSet.get(i).getRemember()==1&&dataSet.get(i).getWord().equalsIgnoreCase(s)){
                dataSet.get(i).remember=0;
            }

        }

    }




    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setText(dataSet.get(position).getWord() );

        tv.setGravity(Gravity.CENTER);

//        tv.setOnClickListener(new View.OnClickListener() {//if not comment, the set listener in main acctivity does not work
//            @Override
//            public void onClick(View v) {
//                Log.e("Click", "Tag " + position + " clicked.");
//                Toast.makeText(context, "Tag " + position + " clicked", Toast.LENGTH_SHORT).show();
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.popup, null, false),1000,1000, true);
//
//                pw.showAtLocation(((Activity) context).findViewById(R.id.groupView), Gravity.CENTER, 0, 0);
//
//
//
//
//            }
//        });

//        Resources res = context.getResources();
//        Drawable drawable = res.getDrawable(R.drawable.bg);
//        tv.setBackground(drawable);



//        if(dataSet.get(position).getRemember()==0){
//            tv.setTextColor(Color.WHITE);//if the word is remembered by the users, it should be the ltgray,else it should be ltgray.
//
//        }else{
//            tv.setTextColor(Color.GREEN);
//
//        }

        return tv;
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position % NUM_ITEMS;
    }

//    @Override
//    public void onThemeColorChanged(View view, int themeColor) {
//        view.setBackgroundColor(themeColor);
//    }









}
