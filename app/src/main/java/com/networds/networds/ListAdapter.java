package com.networds.networds;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.networds.networds.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<String> implements View.OnClickListener{

    private ArrayList<String> dataSet;
    Context mContext;
    private ArrayListFragment.MyListener listener;
    private String mainWord;

    // View lookup cache
    private static class ViewHolder {
        TextView text;
        ImageView eye;
        TextView answer;
        //this incated if clicked the eye or see the meaning of word
    }

    public ListAdapter(ArrayList<String> data, Context context, ArrayListFragment.MyListener listener,String mainWord) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;
        this.listener=listener;
        this.mainWord=mainWord;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        //DataModel dataModel=(DataModel)object;

//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        //DataModel dataModel = getItem(position);
        final String text=getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.text = (TextView) convertView.findViewById(R.id.anto_or_syno_text);
            viewHolder.answer = (TextView) convertView.findViewById(R.id.answer);

            viewHolder.eye = (ImageView) convertView.findViewById(R.id.eye);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;


        Log.i("hello",""+text);

        if(!text.equalsIgnoreCase("")){
            viewHolder.text.setText(""+text.charAt(0));

        }

        viewHolder.answer.setText(text);

        Drawable eyeGray = mContext.getResources().getDrawable( R.drawable.eye_grey );
        viewHolder.eye.setImageDrawable(eyeGray);
        viewHolder.eye.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewHolder.text.setText(text);
                Drawable eyeGreen = mContext.getResources().getDrawable( R.drawable.eye_green );
                viewHolder.eye.setImageDrawable(eyeGreen);
                //todo gray eye go back to green eye
                //send the text to main activity
                Log.i("send information from:","list view");

                listener.sendContent(3+","+mainWord+","+viewHolder.text.getText().toString());

                //type 1:display the meaning of main word
                //type 2: display the meaning of second word
                //type 3:display nothing
            }
        });
        viewHolder.text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewHolder.text.setText(text);
                Drawable eyeGreen = mContext.getResources().getDrawable( R.drawable.eye_green );
                viewHolder.eye.setImageDrawable(eyeGreen);
                //todo gray eye go back to green eye
                //send the text to main activity
                Log.i("send information from:","list view");

                listener.sendContent(3+","+mainWord+","+""+viewHolder.text.getText().toString());

                //type 1:display the meaning of main word
                //type 2: display the meaning of second word
                //type 3:display nothing
            }
        });

        viewHolder.text.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){

                //todo
                String output=((TextView)v).getText().toString().toUpperCase();//if the uppercase, then the light will pop up

                if(((TextView)v).getText().length()>1) {
                    //viewHolder.notRemember.setText("notRemember");


                    listener.sendContent(2 + "," + mainWord + "," + viewHolder.text.getText().toString());
                }else {
//                    viewHolder.text.setText(text);
//                    Drawable eyeGreen = mContext.getResources().getDrawable( R.drawable.eye_green );
//                    viewHolder.eye.setImageDrawable(eyeGreen);
//                    //todo gray eye go back to green eye
//
//
//
//                    //send the text to main activity
//                    Log.i("send information from:","list view");
//                    listener.sendContent(3+","+mainWord+","+viewHolder.text.getText().toString());

                    //type 1:display the meaning of main word
                    //type 2: display the meaning of second word
                    //type 3:display nothing
                }


                return false;
            }

        });

        viewHolder.text.setOnTouchListener( new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:

                        listener.sendContent("0"+", , ");

                        Log.v("tag", "ACTION_UP  end record");
                        break;
//                    case MotionEvent.ACTION_DOWN:
//
//                        if(((TextView)v).getText().length()==1) {
//                            Log.v("tag", "ACTION_DOWN  start record");
//                            viewHolder.text.setText(text);
//                            Drawable eyeGreen = mContext.getResources().getDrawable(R.drawable.eye_green);
//                            viewHolder.eye.setImageDrawable(eyeGreen);
//                            //todo gray eye go back to green eye
//
//
//                            //send the text to main activity
//                            Log.i("send information from:", "list view");
//                            listener.sendContent(3 + "," + mainWord + "," + viewHolder.text.getText().toString());
//                        }
//                        break;
                    default:
                        break;
                }

                return false;
            }
        });
        viewHolder.text.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}