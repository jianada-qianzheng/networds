package com.networds.networds;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.networds.networds.R;

import java.util.ArrayList;

public class ArrayListFragment extends Fragment implements View.OnClickListener,View.OnLongClickListener{
    ArrayList<CharSequence> wordDetail;
    ArrayList<CharSequence> wordDefault = new ArrayList<CharSequence>();
//int originalPosition;

    public TextView txt1;

    private MyListener myListener;//②作为属性定义

    public ImageView info;


//public TextView notRemember;


//    private static String[] cheeses = {"Cheddar", "Jack", "Gamonedo", "Lancashire",
//            "Limburger", "Pepperjack", "Skyr", "Feta", "Asiago"};

    /**
     * Create a new instance of CountingFragment, providing word, it help me to get the word information from main activity
     * as an argument.
     */
    static ArrayListFragment newInstance(ArrayList<CharSequence> word) {
        ArrayListFragment f = new ArrayListFragment();


        // Supply num input as an argument.
        Bundle args = new Bundle();

        //ArrayList<CharSequence> word = new ArrayList<CharSequence>();



        args.putCharSequenceArrayList("detail",word);

        //args.putInt("num", num);
        f.setArguments(args);

        return f;
    }


    public interface MyListener{
        public void sendContent(String info);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myListener = (MyListener) getActivity();
    }
    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wordDetail = getArguments() != null ? getArguments().getCharSequenceArrayList("detail") : wordDefault;

    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String text=wordDetail.get(0).toString();
        View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
        TextView txt= v.findViewById(R.id.word);

        ((TextView)txt).setText(text);

        CardView cardView=v.findViewById(R.id.cardView);




        if(wordDetail.get(4).toString().equalsIgnoreCase("1")) {
            ((TextView) txt).setTextColor(Color.GREEN);


        }else {
            ((TextView) txt).setTextColor(Color.BLACK);

        }


        //((TextView)txt).setText(String.format(getString(R.string.fragment_number), mNum));//todo
//        txt.setOnClickListener(this);
//        txt.setOnLongClickListener(this);

//        info=v.findViewById(R.id.info_button);
//        info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("information button","clicked");
//                String output=((TextView)txt1).getText().toString();//if the uppercase, then the light will pop up
//
//                myListener.sendContent(2+","+output+","+output);
//
//
//                // ((TextView)notRemember).setText("notRemember");
//            }
//        });
//
//
        txt1=v.findViewById(R.id.word);

        txt1.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){

                Log.i("main word cliked","haha");

                //todo
                String output=((TextView)v).getText().toString();//if the uppercase, then the light will pop up
                myListener.sendContent(2+","+output+","+output);


               // ((TextView)notRemember).setText("notRemember");
                return false;
            }

        });

        txt1.setOnTouchListener( new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:

                        myListener.sendContent(0+", , ");

                        Log.v("tag", "ACTION_UP  end record");
                        break;

                    default:
                        break;
                }

                return false;
            }
        });

        txt1.setOnClickListener(this);//when click txt1, it will light off


        //String text=getArguments().get("text")+"";
      //  txt1.setText("这是收到的"+ text);
        v.setTag(wordDetail.get(0).toString());//set the tag to the word string




//        if(wordDetail.get(1)!=null){
//
//
//
//            ArrayList<String> simi1 = new ArrayList<String>();
//
//            ArrayList<String> simi2 = new ArrayList<String>();
//
//            Log.i("syno:",wordDetail.get(1).toString());
//
//            String[] simArr = wordDetail.get(1).toString().split(";");
//
////            for (int i = 0; i < simArr.length; i++) {
////                if(i%2==0)
////                simi1.add(simArr[i]);
////                else
////                    simi2.add(simArr[i]);
////            }
//            int i=0;
//            int j=0;
//            while(i<simArr.length){
//                if(!simArr[i].equalsIgnoreCase("")) {
//                    if ( j% 2 == 0 ) {
//                        simi1.add(simArr[i]);
//                    }else{
//                        simi2.add(simArr[i]);
//                    }
//                    j++;
//                }
//
//                i++;
//
//            }
//
//
//            ListAdapter adapterSimi1 = new ListAdapter(simi1, getContext(),myListener,wordDetail.get(0).toString());
//            ListAdapter adapterSimi2 = new ListAdapter(simi2, getContext(),myListener,wordDetail.get(0).toString());
//
//            ListView listViewSimi1 = v.findViewById(R.id.sim_1);
//            ListView listViewSimi2 = v.findViewById(R.id.sim_2);
//
//            listViewSimi1.setAdapter(adapterSimi1);
//            listViewSimi2.setAdapter(adapterSimi2);
//
//
//
//
//        }



        if(wordDetail.get(0)!=null){



            ArrayList<String> syno = new ArrayList<String>();
            Log.i("syno:",wordDetail.get(2).toString());

            String[] synoArr ={ wordDetail.get(1).toString(),wordDetail.get(2).toString(),wordDetail.get(3).toString()};//wordDetail.get(2).toString().split(";");

            for (int i = 0; i < (synoArr.length>3?3:synoArr.length); i++) {
                if(!synoArr[i].equalsIgnoreCase(""))
                syno.add(synoArr[i]);
            }


            ListAdapter adapterSyno = new ListAdapter(syno, getContext(),myListener,wordDetail.get(0).toString());
            ListView listViewSyno = v.findViewById(R.id.syno_list);
            listViewSyno.setAdapter(adapterSyno);

        }

        if(wordDetail.get(4)!=null){



            ArrayList<String> anto = new ArrayList<String>();


            String[] antoArr ={wordDetail.get(4).toString(),wordDetail.get(5).toString(),wordDetail.get(6).toString()}; //wordDetail.get(3).toString().split(";");

            for (int i = 0; i < (antoArr.length>3?3:antoArr.length); i++) {
                if(!antoArr[i].equalsIgnoreCase(""))

                    anto.add(antoArr[i]);
            }



            ListAdapter adapter = new ListAdapter(anto, getContext(),myListener,wordDetail.get(0).toString());
            ListView listViewAnto = v.findViewById(R.id.anto_list);
            listViewAnto.setAdapter(adapter);



        }

        v.setOnClickListener(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width=displayMetrics.widthPixels;
        Log.i("viewpager width",""+width);
        //v.findViewById(R.id.syno_list).getLayoutParams().width =60;
        v.findViewById(R.id.syno_list).getLayoutParams().width =( width-202)/2;
        v.findViewById(R.id.anto_list).getLayoutParams().width =( width-202)/2;

       // v.findViewById(R.id.anto_list).getLayoutParams().width =60;


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setAdapter(new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, cheeses));
    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        Log.i("FragmentList", "Item clicked: " + id);
//    }

    @Override
    public void onClick(View v) {

        Log.i("v clicked",":success");
        //txt1.setText("clicked");





    }

    @Override
    public boolean onLongClick(View v) {

        Log.i("main word clicked",":success");


           // myListener.sendContent("1,"+wordDetail.get(0)+","+((TextView) v).getText().toString());

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.popup, null, false), 1000, 1000, true);

            //pw.showAtLocation(getActivity().findViewById(R.id.cloud_pager), Gravity.CENTER, 0, 0);

        return false;
    }




}

