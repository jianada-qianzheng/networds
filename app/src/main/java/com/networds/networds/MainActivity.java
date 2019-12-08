package com.networds.networds;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

//import com.yinglan.scrolllayout.ScrollLayout;



import android.support.v4.app.FragmentManager;

public class MainActivity extends FragmentActivity implements ArrayListFragment.MyListener,TextToSpeech.OnInitListener {

    private TagCloudView tagCloudView;
    private TextTagsAdapter textTagsAdapter;
    ViewPager viewPager;
    FragmentManager fm;
    int NUM_ITEMS = 50;

    int TOTAL_NUMBER=500;
    ScrollLayout scrollLayout;

    MyAdapter viewPagerAdapter;
    private ArrayList<Word> words;

    TextToSpeech textToSpeech ;
    PopupWindow pw;

    PopupWindow pw1;

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    private ImageButton start;

    private int height;

    private RadioGroup buttons;

    Comparator c;

    private View view1;
    private View view2;

    private TextView buttomText;
    private TextView middleText;

    private ChatEditText editText;

    private ConstraintLayout constraintLayout;


    private ImageView editeFrame;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width=displayMetrics.widthPixels;


        start=findViewById(R.id.start);
        view1=findViewById(R.id.view_1);
        view2=findViewById(R.id.view_2);

        buttomText=findViewById(R.id.middleText);
        //middleText=findViewById(R.id.middleText);


        editText=findViewById(R.id.editText);

        editText.setClickable(false);

        editeFrame=findViewById(R.id.edit_frame);


        constraintLayout=findViewById(R.id.view_2);

        editText.setKeyImeChangeListener(new ChatEditText.KeyImeChange(){
            @Override
            public void onKeyIme(int keyCode, KeyEvent event)
            {
                if (KeyEvent.KEYCODE_BACK == event.getKeyCode())
                {
                    // do something

                    Log.i("keyboard","down");

                    InputMethodManager imm = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm!=null) {
                        //editText.requestFocus();
                        imm.showSoftInput(editText, InputMethodManager.HIDE_IMPLICIT_ONLY);

                    }

                    ConstraintSet constraintSet = new ConstraintSet();
                    //constraintSet.clear(R.id.editText);

                    constraintSet.clone(constraintLayout);
                    //constraintSet.connect(R.id.editText,ConstraintSet.BOTTOM,R.id.view_2,ConstraintSet.BOTTOM,0);
                    constraintSet.constrainPercentWidth(R.id.edit_frame,0.91f);
                    constraintSet.constrainPercentHeight(R.id.edit_frame,0.09f);
                    constraintSet.constrainPercentHeight(R.id.middleText,0.07f);
                    constraintSet.constrainPercentHeight(R.id.scroll_down_layout,0.81f);
                    constraintSet.constrainPercentWidth(R.id.scroll_down_layout,1);
                    constraintSet.constrainPercentHeight(R.id.search,0.03f);
                    constraintSet.constrainPercentHeight(R.id.mic,0.03f);
                    constraintSet.constrainPercentWidth(R.id.editText,0.63f);


                    constraintSet.constrainPercentWidth(R.id.bottomText,0.8f);
                    constraintSet.constrainPercentHeight(R.id.bottomText,0.03f);
                    constraintSet.applyTo(constraintLayout);

                    editeFrame.setBackground(    (ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_rectangle)));

                }

            }});

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // showMyDialog();

                    InputMethodManager imm = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm!=null) {
                        //editText.requestFocus();
                        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

                    }


                    ConstraintSet constraintSet = new ConstraintSet();
                    //constraintSet.clear(R.id.editText);

                    constraintSet.clone(constraintLayout);
                    //constraintSet.connect(R.id.editText,ConstraintSet.BOTTOM,R.id.view_2,ConstraintSet.BOTTOM,0);
                    constraintSet.constrainPercentWidth(R.id.edit_frame,1);
                    constraintSet.constrainPercentHeight(R.id.edit_frame,0.15f);
                    constraintSet.constrainPercentHeight(R.id.middleText,0f);
                    constraintSet.constrainPercentHeight(R.id.scroll_down_layout,0.85f);
                    constraintSet.constrainPercentHeight(R.id.search,0.06f);
                    constraintSet.constrainPercentHeight(R.id.mic,0.06f);
                    constraintSet.constrainPercentWidth(R.id.editText,0.7f);


                    constraintSet.constrainPercentWidth(R.id.bottomText,1);
                    constraintSet.constrainPercentHeight(R.id.bottomText,0);

                    constraintSet.applyTo(constraintLayout);

                    editeFrame.setBackground(    (ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_rectangle_1)));

                    editText.setText("");
                }
            }
        });



        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                InputMethodManager imm = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm!=null) {
                    //editText.requestFocus();
                    imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

                }


                ConstraintSet constraintSet = new ConstraintSet();
                //constraintSet.clear(R.id.editText);

                constraintSet.clone(constraintLayout);
                //constraintSet.connect(R.id.editText,ConstraintSet.BOTTOM,R.id.view_2,ConstraintSet.BOTTOM,0);
                constraintSet.constrainPercentWidth(R.id.edit_frame,1);
                constraintSet.constrainPercentHeight(R.id.edit_frame,0.15f);
                constraintSet.constrainPercentHeight(R.id.middleText,0f);
                constraintSet.constrainPercentHeight(R.id.scroll_down_layout,0.85f);
                constraintSet.constrainPercentHeight(R.id.search,0.06f);
                constraintSet.constrainPercentHeight(R.id.mic,0.06f);
                constraintSet.constrainPercentWidth(R.id.editText,0.7f);




                constraintSet.constrainPercentWidth(R.id.bottomText,1);
                constraintSet.constrainPercentHeight(R.id.bottomText,0);

                constraintSet.applyTo(constraintLayout);

                editeFrame.setBackground(    (ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_rectangle_1)));

                editText.setText("");









            }


        });










        //RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //lp.setMargins(0, height-buttonHeight, 0, 0);
        //buttons.setLayoutParams(lp);

        //RelativeLayout.LayoutParams layoutParamsButtonWrite = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //layoutParamsButtonWrite.setMargins(width/2-buttonHeight*3/4, height-buttonHeight*3/2, width/2-buttonHeight*3/4, 0);
        //buttonWrite.setLayoutParams(layoutParamsButtonWrite);




        mDBHelper = new DatabaseHelper(this);



        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }




        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (scrollLayout.getCurrentStatus() == ScrollLayout.Status.OPENED) {
                    //todo add words
                    Log.i("tag clicked:", "" + scrollLayout.getLayoutMode());

                    scrollLayout.setToClosed();//close actually is open


                    Log.e("Click", "Tag " + 0 + " clicked.");
                    viewPager = findViewById(R.id.pager);
                    //viewPager.setMinimumHeight(400);//todo

                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.VISIBLE);

                    editText.setClickable(true);

                ConstraintSet constraintSet = new ConstraintSet();
                //constraintSet.clear(R.id.editText);

                constraintSet.clone(constraintLayout);
                //constraintSet.connect(R.id.editText,ConstraintSet.BOTTOM,R.id.view_2,ConstraintSet.BOTTOM,0);

                constraintSet.constrainPercentWidth(R.id.editText,0.63f);

                constraintSet.applyTo(constraintLayout);




                    fm = getSupportFragmentManager();


                    Collections.sort(words, c);

                    //todo                                //todo unlimited pages
//                        Word last=new Word(words.get(0).getWord(),words.get(0).getSimilar(),words.get(0).getSyno(),words.get(0).getAnto(),0,words.get(0).getWeight());
//                        words.add(last);//add a same page at the end

//                        for(int j=0;j<NUM_ITEMS;j++){
//                            temp[j]=words.get(j);
//                        }

                    //textToSpeech.speak(words.get(0), TextToSpeech.QUEUE_FLUSH, null);

//                    for (int i = 0; i < words.size(); i++) {
//                        if (words.get(i).getWord().equalsIgnoreCase(((TextView) view).getText().toString())) {
//                            0 = i;
//                        }
//                    }


                    viewPagerAdapter = new MyAdapter(fm, words, 0);
                    //viewPagerAdapter.instantiateItem(viewPager,position);//?
                    viewPager.setAdapter(viewPagerAdapter);
                    viewPager.setCurrentItem(0);//produce word detail card



                    //viewPager.setListener

                    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                        @Override
                        public void onPageScrolled(int position, float offset, int offsetPixels) {


                            Log.i("pos,off,osetp", "" + position + "," + offset + "," + offsetPixels);
                            //todo read

                            textToSpeech.speak(words.get(position % words.size()).getWord().toLowerCase(), TextToSpeech.QUEUE_FLUSH, null);


                            //end of pages or start of pages
//                                if (offset >0.5 ) {
//                                    Log.i("offfset",offset+"");
////                                    if (position == 0) {
////                                        viewPager.setCurrentItem(words.size() - 2,true);
////                                    } else
//                                        if (position == (words.size() - 2)) {
//                                            viewPager.setCurrentItem(0,true);
//                                        //viewPager.setCurrentItem(0,true);
//                                    }
//                                }
                        }

                        @Override
                        public void onPageSelected(int i) {
                            Log.i("page change", "" + i);
                            if(i>0) {
                                if (viewPager.findViewWithTag(words.get((viewPager.getCurrentItem() - 1) % words.size()).getWord()) != null) {
                                    TextView notRemember = (TextView) (viewPager.findViewWithTag(words.get((viewPager.getCurrentItem() - 1) % words.size()).getWord()).findViewById(R.id.not_remember));

                                    if (notRemember.getText().equals("notRemember")) {
                                        Word temp = words.remove((i - 1) % words.size());
//                                        Log.i("page change ?", "" + ((TextView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem() % words.size()).getWord()).findViewById(R.id.not_remember))));


                                        //words.add(temp);

                                        words.add((i + 19) % words.size(), temp);

                                        //viewPagerAdapter.deletePage(viewPager.getCurrentItem()%words.size());


                                        viewPagerAdapter.notifyDataSetChanged();

                                        //viewPagerAdapter.notifyDataSetChanged();
                                    }
                                }
                            }

                            //Log.i("page change ?",""+notRemember.getText());
                        }

                        @Override
                        public void onPageScrollStateChanged(int i) {

                            //((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.word))).setTextColor(Color.GREEN);

                            Log.i("state changed", "yes");


                        }
                    });




                //}else{
                    //scrollLayout.setToOpen();

                //}
            }
        });

        scrollLayout=findViewById(R.id.scroll_down_layout);
        //SQLiteDatabase wordsDB = openOrCreateDatabase("NetWordsDB",MODE_PRIVATE,null);

        textToSpeech= new TextToSpeech(this, this);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

         pw1 = new PopupWindow(inflater.inflate(R.layout.popup_meaning, null, false),width,600, true);





        //wordsDB.execSQL("CREATE TABLE IF NOT EXISTS words(word VARCHAR,weight INTEGER,pronunciation VARCHAR, verb VARCHAR,verbt VARCHAR,verbi VARCHAR,noun VARCHAR,adj VARCHAR,adv VARCHAR,prep VARCHAR,conj VARCHAR,sentence VARCHAR,looklike VARCHAR,syno VARCHAR, anto VARCHAR, remember INTEGER );");//create the database

       // Cursor resultSet = mDb.rawQuery("Select * from words",null);//read the data from the database.

//        if(!resultSet.moveToNext()) {//if no table in the database, import the data from csv file
//
//
//            try {//import the csv file
//
//                InputStreamReader isr = new InputStreamReader(is);
//                BufferedReader br = new BufferedReader(isr);
//                String line;
//                String csvSplitBy = ",";
//
//                br.readLine();
//
//                while ((line = br.readLine()) != null) {
//                    String[] row = line.split(csvSplitBy);
//                    Log.i("lines:", line);
//                    Log.i("words:", row[0]);
//
//                    //Log.i("insert:","INSERT INTO words VALUES("+row[0]+   ","   + row[1]+   ","      +  (row[3].equalsIgnoreCase("")?null:row[3])+","   + ( row[4].equalsIgnoreCase("")?null:row[4])+","   +  (row[5].equalsIgnoreCase("")?null:row[5])+","   +  (row[6].equalsIgnoreCase("")?null:row[6])+","   +  (row[7].equalsIgnoreCase("")?null:row[7])+","   +  (row[8].equalsIgnoreCase("")?null:row[8])+","   +  (row[9].equalsIgnoreCase("")?null:row[9])+","   +  (row[10].equalsIgnoreCase("")?null:row[10])+","   +  (row[11].equalsIgnoreCase("")?null:row[11])+","+ (row[12].equalsIgnoreCase("")?null:row[12])+","+ (row[13].equalsIgnoreCase("")?null:row[13])+"," + (row[14].equalsIgnoreCase("")?null:row[14])+","  +   "0);");//todo insert more
//
//                    //wordsDB.execSQL("INSERT INTO words VALUES( null,'/hell/' , null,null,null ,null ,null ,null ,null ,null ,null,null ,3,4,5);");
//
//                        wordsDB.execSQL("INSERT INTO words VALUES("+row[0]+   ","   + row[1]+   ","      +  (row[3].equalsIgnoreCase("")?null:row[3])+","   + ( row[4].equalsIgnoreCase("")?null:row[4])+","   +  (row[5].equalsIgnoreCase("")?null:row[5])+","   +  (row[6].equalsIgnoreCase("")?null:row[6])+","   +  (row[7].equalsIgnoreCase("")?null:row[7])+","   +  (row[8].equalsIgnoreCase("")?null:row[8])+","   +  (row[9].equalsIgnoreCase("")?null:row[9])+","   +  (row[10].equalsIgnoreCase("")?null:row[10])+","   +  (row[11].equalsIgnoreCase("")?null:row[11])+","+ (row[12].equalsIgnoreCase("")?null:row[12])+","+ (row[13].equalsIgnoreCase("")?null:row[13])+"," + (row[14].equalsIgnoreCase("")?null:row[14])+","  +   "0);");//todo insert more
//
//                        //wordDetail = getArguments() != null ? getArguments().getCharSequenceArrayList("detail") : wordDefault;
//
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }










        tagCloudView = (TagCloudView) findViewById(R.id.tag_cloud);
        //final ViewGroup viewGroup=findViewById(R.id.cloud_pager);
        //viewGroup.setBackgroundColor(Color.BLACK);


        //TODO get the word from SQLite


        Cursor  resultSet = mDb.rawQuery("Select synos.word,synos.syno1,synos.syno2,synos.syno3,synos.anto1,synos.anto2,synos.anto3,words.remember,words.rank from synos join words on synos.word=words.word where words.rank>13000",null);

        words = new ArrayList<Word>();
        int i=0;

        //resultSet.moveToFirst();

        while(resultSet.moveToNext()&&i<NUM_ITEMS) {

            Log.i("words.syno",""+resultSet.getString(8));

            //String newWord = resultSet.getString(0)+resultSet.getInt(1);
                //public Word(String word, String syno1,String syno2,String syno3, String anto1,String anto2,String anto3,int remember,int weight){


            Word newWord=new Word(resultSet.getString(0),resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4) ,resultSet.getString(5),resultSet.getString(6),resultSet.getInt(7),resultSet.getInt(8));// create a new word object

            words.add(newWord);

            i++;

            //todo if there are not enough 40 words at last
        }//read the words from database


        //NUM_ITEMS=(words.size()>NUM_ITEMS)?NUM_ITEMS:words.size();
        Word [] temp =new Word[NUM_ITEMS];

        c = new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                // TODO Auto-generated method stub
                double n1;
                double n2;

                if (o1.getRemember()==1){
                    n1=10000;
                }else{
                    n1=0.1;
                }

                if (o2.getRemember()==1){
                    n2=10000;
                }else{
                    n2=0.1;
                }

                if((o1.getWeight()*n1<o2.getWeight()*n2) ){
                    return 1;
                    //注意！！返回值必须是一对相反数，否则无效。jdk1.7以后就是这样。
                    //		else return 0; //无效
                }
                else return -1;
            }
        };


        Collections.sort(words,c);

        for(int j=0;j<NUM_ITEMS;j++){
            temp[j]=words.get(j);
        }



        //todo
        textTagsAdapter = new TextTagsAdapter(temp);////produce the tag cloud
        tagCloudView.setAdapter(textTagsAdapter);//produce the detail card

        scrollLayout.setOnScrollChangedListener(new ScrollLayout.OnScrollChangedListener() {
            @Override
            public void onScrollProgressChanged(float currentProgress) {
                Log.i("scroll","1:"+scrollLayout.getLayoutMode());
            }

            @Override
            public void onScrollFinished(ScrollLayout.Status currentStatus) {
                Log.i("scroll","2:"+scrollLayout.getLayoutMode());
                if(scrollLayout.getCurrentStatus()==ScrollLayout.Status.OPENED) {
                    scrollLayout.setToExit();
                }
                view2.setVisibility(View.GONE);

                view1.setVisibility(View.VISIBLE);







                editText.setClickable(false);




                InputMethodManager imm = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm!=null) {
                    //editText.requestFocus();
                    imm.showSoftInput(editText, InputMethodManager.HIDE_IMPLICIT_ONLY);

                }

                ConstraintSet constraintSet = new ConstraintSet();
                //constraintSet.clear(R.id.editText);

                constraintSet.clone(constraintLayout);
                //constraintSet.connect(R.id.editText,ConstraintSet.BOTTOM,R.id.view_2,ConstraintSet.BOTTOM,0);

                constraintSet.constrainPercentWidth(R.id.editText,0);



                constraintSet.applyTo(constraintLayout);

            }

            @Override
            public void onChildScroll(int top) {
                Log.i("scroll","3:"+scrollLayout.getLayoutMode());


            }
        });

///added on juin 2019
        editText.setOnKeyListener(new View.OnKeyListener() {
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        Log.i("input string:",""+keyCode);

                        // If the event is a key-down event on the "enter" button
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            // Perform action on key press
                            Log.i("input string:",""+keyCode);

                            viewPager=findViewById(R.id.pager);

                            if(scrollLayout.getCurrentStatus()==ScrollLayout.Status.CLOSED){//viewPager.getVisibility()== View.VISIBLE) {

                                //pw.dismiss();

                                String text=(v!=null)?((EditText)v).getText().toString():null;



                                Log.i("input string:",""+text);


                                String notRemember=((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).getText().toString();

                                Log.i("not remember:",notRemember);


                                //ListView listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.sim_1)));

//                                for(int i=0;i<listViewSim1.getChildCount();i++){
//                                    Log.i("answer string:",""+((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText());
//
//                                    if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().toLowerCase().equalsIgnoreCase(text.toLowerCase())){
//                                        ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString());
//                                        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH, null);
//
//
//                                        if(!notRemember.equalsIgnoreCase("notRemember")) {//if set to not remember, do not set to remember
//                                            ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.word))).setTextColor(Color.GREEN);
//                                            ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).setText("remembered");
//
//
//
//                                        }
//                                    }
//                                }

                                //listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.sim_2)));

//                                for(int i=0;i<listViewSim1.getChildCount();i++){
//                                    Log.i("answer string:",""+((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText());
//
//                                    if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().equalsIgnoreCase(text)){
//                                        ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(text);
//                                        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH, null);
//
//
//                                    }
//                                }

                                ListView listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.syno_list)));

                                for(int i=0;i<listViewSim1.getChildCount();i++){
                                    Log.i("answer string:",""+((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText());

                                    if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().toLowerCase().equalsIgnoreCase(text.toLowerCase())){
                                        ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString());
                                        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH, null);

                                        if(!notRemember.equalsIgnoreCase("notRemember")) {//if set to not remember, do not set to remember
                                            ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.word))).setTextColor(Color.GREEN);

                                            ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).setText("remembered");

                                            mDb.execSQL("UPDATE words SET REMEMBER = 1 WHERE word = '" + words.get(viewPager.getCurrentItem()%words.size()).getWord() + "';");//todo important get the right word


                                            textTagsAdapter.lightOn(words.get(viewPager.getCurrentItem()%words.size()).getWord());
                                            tagCloudView.onChange();
                                        }
                                    }
                                }

                                listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.anto_list)));

                                for(int i=0;i<listViewSim1.getChildCount();i++){
                                    Log.i("answer string:",""+((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText());

                                    if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().toLowerCase().equalsIgnoreCase(text.toLowerCase())){
                                        ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString());
                                        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH, null);

                                        if(!notRemember.equalsIgnoreCase("notRemember")) {//if set to not remember, do not set to remember

                                            ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.word))).setTextColor(Color.GREEN);
//todo change the text to remember
                                            ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).setText("remembered");

                                            mDb.execSQL("UPDATE words SET REMEMBER = 1 WHERE word = '" + words.get(viewPager.getCurrentItem()%words.size()).getWord() + "';");


                                            textTagsAdapter.lightOn(words.get(viewPager.getCurrentItem()%words.size()).getWord());
                                            tagCloudView.onChange();
                                        }

                                    }
                                }




                            }else{

                                String text=(v!=null)?((EditText)v).getText().toString():null;

                                Log.i("input from button",""+text);

                                pw.dismiss();


                                int position=-1;
                                Collections.sort(words,c);

                                for(int i=0;i<words.size();i++){
                                    if(words.get(i).getWord().equalsIgnoreCase(text)){
                                        position=i;
                                    }
                                }
                                if(position!=-1) {

                                    scrollLayout.setToClosed();

                                    viewPager = findViewById(R.id.pager);
                                    viewPager.setVisibility(View.VISIBLE);
                                    fm = getSupportFragmentManager();




                                    viewPagerAdapter = new MyAdapter(fm, words, position);
                                    //viewPagerAdapter.instantiateItem(viewPager,position);//?
                                    viewPager.setAdapter(viewPagerAdapter);
                                    viewPager.setCurrentItem(position);//produce word detail card

                                }
                            }






                            return true;
                        }
                        return false;
                    }
                });


///added on juin 2019
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        Log.i("input string:",""+actionId);

                        if (actionId == EditorInfo.IME_ACTION_DONE) {

                            if(viewPager.getVisibility()== View.VISIBLE) {

                               //String text=(event!=null)?event.getCharacters():null;

                                String text= v.getText().toString().trim();

                               Log.i("input string:",""+text);

                               //viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.sim_1).setBackgroundColor(Color.RED);

//                               ListView listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.sim_1)));
//
//                               for(int i=0;i<listViewSim1.getChildCount();i++){
//                                   if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().equalsIgnoreCase(text)){
//                                       ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(text);
//                                   }
//                               }
//
//                               listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.sim_2)));
//
//                               for(int i=0;i<listViewSim1.getChildCount();i++){
//                                   if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().equalsIgnoreCase(text)){
//                                       ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(text);
//                                   }
//                               }

                                ListView listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.syno_list)));

                               for(int i=0;i<listViewSim1.getChildCount();i++){
                                   if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().equalsIgnoreCase(text)){
                                       ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(text);
                                       //todo insert database
                                   }
                               }

                               listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.anto_list)));

                               for(int i=0;i<listViewSim1.getChildCount();i++){
                                   if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().equalsIgnoreCase(text)){
                                       ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(text);
                                       //todo insert database

                                   }
                               }


                               //viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.syno).setBackgroundColor(Color.GREEN);
                           }

                        }
                        return false;
                    }
                });




        tagCloudView.setOnTagClickListener(//set click listener for every tag
                new TagCloudView.OnTagClickListener() {
                    @Override
                    public void onItemClick(ViewGroup parent, final View view, int position) {

                        Log.i("tag clicked:",""+scrollLayout.getLayoutMode());

                        scrollLayout.setToClosed();//close actually is open

                        //viewPager.setVisibility(View.VISIBLE);

                        Log.e("Click", "Tag " + position + " clicked.");
                        //Toast.makeText(getApplicationContext(), "Tag " + position + " clicked", Toast.LENGTH_SHORT).show();
                        viewPager =  findViewById(R.id.pager);
                        viewPager.setVisibility(View.VISIBLE);

                        fm=getSupportFragmentManager();



                        Collections.sort(words,c);

                        //todo                                //todo unlimited pages
//                        Word last=new Word(words.get(0).getWord(),words.get(0).getSimilar(),words.get(0).getSyno(),words.get(0).getAnto(),0,words.get(0).getWeight());
//                        words.add(last);//add a same page at the end

//                        for(int j=0;j<NUM_ITEMS;j++){
//                            temp[j]=words.get(j);
//                        }

                       // textToSpeech.speak(((TextView)view).getText().toString(),TextToSpeech.QUEUE_FLUSH, null);

                        for(int i=0;i<words.size();i++){
                           if( words.get(i).getWord().equalsIgnoreCase(((TextView)view).getText().toString())){
                               position=i;
                            }
                        }


                        viewPagerAdapter = new MyAdapter(fm,words,position);
                        //viewPagerAdapter.instantiateItem(viewPager,position);//?
                        viewPager.setAdapter(viewPagerAdapter);
                        viewPager.setCurrentItem(position);//produce word detail card

                        //viewPager.setListener



                        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                            @Override
                            public void onPageScrolled(int position, float offset, int offsetPixels) {


                                Log.i("pos,off,osetp",""+position+","+offset+","+offsetPixels);
                                //todo read

                                textToSpeech.speak(words.get(position%words.size()).getWord().toLowerCase(),TextToSpeech.QUEUE_FLUSH, null);


                                //end of pages or start of pages
//                                if (offset >0.5 ) {
//                                    Log.i("offfset",offset+"");
////                                    if (position == 0) {
////                                        viewPager.setCurrentItem(words.size() - 2,true);
////                                    } else
//                                        if (position == (words.size() - 2)) {
//                                            viewPager.setCurrentItem(0,true);
//                                        //viewPager.setCurrentItem(0,true);
//                                    }
//                                }
                            }

                            @Override
                            public void onPageSelected(int i) {
                                Log.i("page change",""+i);
                                if(i>0) {
                                    if (viewPager.findViewWithTag(words.get((viewPager.getCurrentItem() - 1) % words.size()).getWord()) != null) {
                                        TextView notRemember = (TextView) (viewPager.findViewWithTag(words.get((viewPager.getCurrentItem() - 1) % words.size()).getWord()).findViewById(R.id.not_remember));

                                        if (notRemember.getText().equals("notRemember")) {
                                            Word temp = words.remove((i - 1) % words.size());
//                                            Log.i("page change ?", "" + ((TextView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem() % words.size()).getWord()).findViewById(R.id.not_remember))));


                                            //words.add(temp);

                                            words.add((i + 19) % words.size(), temp);

                                            //viewPagerAdapter.deletePage(viewPager.getCurrentItem()%words.size());


                                            viewPagerAdapter.notifyDataSetChanged();

                                            //viewPagerAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }

                                //Log.i("page change ?",""+notRemember.getText());
                            }

                            @Override
                            public void onPageScrollStateChanged(int i) {

                                //((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.word))).setTextColor(Color.GREEN);

                                Log.i("state changed","yes");



                            }
                        });
                        //viewPager.setVisibility(View.INVISIBLE);

                        //add liestener to the viewpager,but not work
//                        ViewPager.OnAdapterChangeListener adapterChangeListener = new ViewPager.OnAdapterChangeListener() {
//                            @Override
//                            public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
//                                //listenToAdapterChanges(newAdapter);
////                                findViewById(R.id.syno).setBackgroundColor(Color.YELLOW);
////                                viewPager.findViewById(R.id.syno).setBackgroundColor(Color.YELLOW);
//
//                            }
//                        };
//                        viewPager.addOnAdapterChangeListener(adapterChangeListener);





                    }
                }
        );
        //tagCloudView.setAdapter(textTagsAdapter);//produce the detail card

        tagCloudView.setDarkColor(Color.argb(00,0,0,0));
        tagCloudView.setLightColor(Color.argb(00,0,0,0));
        //tagCloudView.getChildAt(0).setBackgroundColor(Color.RED);

        //.findViewWithTag("hello").setBackgroundColor(Color.RED);

       //Log.i("Text:", ( (TextView) textTagsAdapter.getView(this,0,tagCloudView)).getText().toString());

        //Drawable d = getResources().getDrawable(R.drawable.tagbg);

        //((TextView) textTagsAdapter.getView(this,0,tagCloudView)).setBackgroundColor(Color.RED);






         //buttonWrite = findViewById(R.id.write_button);

//        buttonWrite.setOnLongClickListener(new View.OnLongClickListener(){
//            @Override
//            public boolean onLongClick(View v){
//                Intent intent = new Intent(
//                        	                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//
//                	                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
//
//                	                try {
//                    	                    startActivityForResult(intent,200);
//                    	                    Log.i("voice input:","begin");
//
//
//
//                    	                } catch (ActivityNotFoundException a) {
//
//                    	                }
//
//
//                return false;
//            }
//        });


//        buttonWrite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {//add listener to the button, need to add a long press listener
//
//                Log.i("write button","clicked");
//
//                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                pw = new PopupWindow(inflater.inflate(R.layout.popup, null, false),width,height/5, true);
//
//                //pw.showAtLocation(findViewById(R.id.cloud_pager), Gravity.CENTER, 0, 0);
//
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//
//
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//
//
//                EditText editText=pw.getContentView().findViewById(R.id.input);
//
//
//
//
//
//
//                editText.setOnKeyListener(new View.OnKeyListener() {
//                    public boolean onKey(View v, int keyCode, KeyEvent event) {
//                        // If the event is a key-down event on the "enter" button
//                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                            // Perform action on key press
//
//                            viewPager=findViewById(R.id.pager);
//
//                            if(scrollLayout.getCurrentStatus()==ScrollLayout.Status.CLOSED){//viewPager.getVisibility()== View.VISIBLE) {
//
//                                pw.dismiss();
//
//                                String text=(v!=null)?((EditText)v).getText().toString():null;
//
//
//
//                                Log.i("input string:",""+text);
//
//
//                                String notRemember=((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).getText().toString();
//
//                                Log.i("not remember:",notRemember);
//
//
//                                ListView listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.sim_1)));
//
//                                for(int i=0;i<listViewSim1.getChildCount();i++){
//                                    Log.i("answer string:",""+((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText());
//
//                                    if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().toLowerCase().equalsIgnoreCase(text.toLowerCase())){
//                                        ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString());
//                                        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH, null);
//
//
//                                        if(!notRemember.equalsIgnoreCase("notRemember")) {//if set to not remember, do not set to remember
//                                            ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.word))).setTextColor(Color.GREEN);
//                                            ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).setText("remembered");
//
//
//
//                                        }
//                                    }
//                                }
//
//                                listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.sim_2)));
//
//                                for(int i=0;i<listViewSim1.getChildCount();i++){
//                                    Log.i("answer string:",""+((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText());
//
//                                    if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().equalsIgnoreCase(text)){
//                                        ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(text);
//                                        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH, null);
//
//
//                                    }
//                                }
//
//                                listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.syno_list)));
//
//                                for(int i=0;i<listViewSim1.getChildCount();i++){
//                                    Log.i("answer string:",""+((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText());
//
//                                    if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().toLowerCase().equalsIgnoreCase(text.toLowerCase())){
//                                        ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString());
//                                        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH, null);
//
//                                        if(!notRemember.equalsIgnoreCase("notRemember")) {//if set to not remember, do not set to remember
//                                            ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.word))).setTextColor(Color.GREEN);
//
//                                            ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).setText("remembered");
//
//                                            mDb.execSQL("UPDATE words SET REMEMBER = 1 WHERE word = '" + words.get(viewPager.getCurrentItem()%words.size()).getWord() + "';");//todo important get the right word
//
//
//                                            textTagsAdapter.lightOn(words.get(viewPager.getCurrentItem()%words.size()).getWord());
//                                            tagCloudView.onChange();
//                                        }
//                                    }
//                                }
//
//                                listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.anto_list)));
//
//                                for(int i=0;i<listViewSim1.getChildCount();i++){
//                                    Log.i("answer string:",""+((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText());
//
//                                    if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().toLowerCase().equalsIgnoreCase(text.toLowerCase())){
//                                        ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString());
//                                        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH, null);
//
//                                        if(!notRemember.equalsIgnoreCase("notRemember")) {//if set to not remember, do not set to remember
//
//                                            ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.word))).setTextColor(Color.GREEN);
////todo change the text to remember
//                                            ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).setText("remembered");
//
//                                            mDb.execSQL("UPDATE words SET REMEMBER = 1 WHERE word = '" + words.get(viewPager.getCurrentItem()%words.size()).getWord() + "';");
//
//
//                                            textTagsAdapter.lightOn(words.get(viewPager.getCurrentItem()%words.size()).getWord());
//                                            tagCloudView.onChange();
//                                        }
//
//                                    }
//                                }
//
//
//
//
//                            }else{
//
//                                String text=(v!=null)?((EditText)v).getText().toString():null;
//
//                                Log.i("input from button",""+text);
//
//                                pw.dismiss();
//
//
//                                int position=-1;
//                                Collections.sort(words,c);
//
//                                for(int i=0;i<words.size();i++){
//                                    if(words.get(i).getWord().equalsIgnoreCase(text)){
//                                        position=i;
//                                    }
//                                }
//                                if(position!=-1) {
//
//                                    scrollLayout.setToClosed();
//
//                                    viewPager = findViewById(R.id.pager);
//                                    viewPager.setVisibility(View.VISIBLE);
//                                    fm = getSupportFragmentManager();
//
//
//
//
//                                    viewPagerAdapter = new MyAdapter(fm, words, position);
//                                    //viewPagerAdapter.instantiateItem(viewPager,position);//?
//                                    viewPager.setAdapter(viewPagerAdapter);
//                                    viewPager.setCurrentItem(position);//produce word detail card
//
//                                }
//                            }
//
//
//
//
//
//
//                            return true;
//                        }
//                        return false;
//                    }
//                });



//                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                    @Override
//                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                       if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                           if(viewPager.getVisibility()== View.VISIBLE) {
//
//                               String text=(event!=null)?event.getCharacters():null;
//
//                               Log.i("input string:",""+text);
//
//                               viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.sim_1).setBackgroundColor(Color.RED);
//
//                               ListView listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.sim_1)));
//
//                               for(int i=0;i<listViewSim1.getChildCount();i++){
//                                   if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().equalsIgnoreCase(text)){
//                                       ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(text);
//                                   }
//                               }
//
//                               listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.sim_2)));
//
//                               for(int i=0;i<listViewSim1.getChildCount();i++){
//                                   if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().equalsIgnoreCase(text)){
//                                       ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(text);
//                                   }
//                               }
//
//                               listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.syno_list)));
//
//                               for(int i=0;i<listViewSim1.getChildCount();i++){
//                                   if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().equalsIgnoreCase(text)){
//                                       ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(text);
//                                   }
//                               }
//
//                               listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.anto_list)));
//
//                               for(int i=0;i<listViewSim1.getChildCount();i++){
//                                   if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().equalsIgnoreCase(text)){
//                                       ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(text);
//                                   }
//                               }
//
//
//                               //viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.syno).setBackgroundColor(Color.GREEN);
//                           }
//
//                        }
//                        return false;
//                    }
//                });








//                ArrayListFragment myFragment = new ArrayListFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("DATA","search");//这里的values就是我们要传的值
//                myFragment.setArguments(bundle);

//try to pass some information to fragment, but failed
//                Bundle bundle=new Bundle();
//                bundle.putString("text", "search");
//                ArrayListFragment fragment=new ArrayListFragment();
//                fragment.setArguments(bundle);
//
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
//                //beginTransaction.add(R.id., fragment, "fragment5");
//                beginTransaction.commit();




//this part is to search the words and edit the pages
                //todo add if view can be seen
//                findViewById(R.id.syno).setBackgroundColor(Color.YELLOW);
//
//                ((TextView) findViewById(R.id.syno)).setText(" no:");
//
//                viewPager.findViewById(R.id.syno).setBackgroundColor(Color.RED);
//
//
//                viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.syno).setBackgroundColor(Color.GREEN);//find the current view by tag
//                //viewPagerAdapter.getItem(0).getView().setBackgroundColor(Color.GREEN);



                //.findViewById(R.id.chang).setBackgroundColor(Color.RED);

                //fragmentManager.findFragmentById(R.id.chang);

                //viewPager.setCurrentItem(0);//todo this sentence can go to any page
//            }
//        });

//        viewPager.findViewById(R.id.eye_similar_1).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick (View v){
//                Log.i("hello","hello");
//                v.setBackgroundColor(Color.RED);
//            }
//
//        });
        //resultSet.close();
//mDb.close();
    }

    @Override
    public void onInit(int status) {
        Log.d("Speech", "OnInit - Status ["+status+"]");

        if (status == TextToSpeech.SUCCESS) {
            Log.d("Speech", "Success!");
            textToSpeech.setLanguage(Locale.US);
        }
    }


//    public static class MyAdapter extends FragmentStatePagerAdapter {
//
//
//
//
//        public MyAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public int getCount() {
//            return NUM_ITEMS;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return ArrayListFragment.newInstance(position);
//        }
//    }
//
//    public static class ArrayListFragment extends ListFragment {
//        int mNum;
//
//        String [] word={"1","2"};
//
//        /**
//         * Create a new instance of CountingFragment, providing "num"
//         * as an argument.
//         */
//        static ArrayListFragment newInstance(int num) {
//            ArrayListFragment f = new ArrayListFragment();
//
//            // Supply num input as an argument.
//            Bundle args = new Bundle();
//            args.putInt("num", num);
//            f.setArguments(args);
//
//            return f;
//        }
//
//        /**
//         * When creating, retrieve this instance's number from its arguments.
//         */
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
//        }
//
//        /**
//         * The Fragment's UI is just a simple text view showing its
//         * instance number.
//         */
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View v = inflater.inflate(R.layout.detail_layout, container, false);
//            View tv = v.findViewById(R.id.text);
//            ((TextView)tv).setText("Fragment #" + mNum);
//            return v;
//        }
//
//        @Override
//        public void onActivityCreated(Bundle savedInstanceState) {
//            super.onActivityCreated(savedInstanceState);
//
//
//
//
//           setListAdapter(new ArrayAdapter<String>(getActivity(),
//                   android.R.layout.simple_list_item_1, word));
//        }
//
//        @Override
//        public void onListItemClick(ListView l, View v, int position, long id) {
//            Log.i("FragmentList", "Item clicked: " + id);
//        }
//    }
//
//

    @Override
    public void sendContent(String info) {//get the word pressed in the detail card

        Log.i("get information",info);
        if (info!=null && !"".equalsIgnoreCase(info)) {

            try {
                mDBHelper.updateDataBase();
            } catch (IOException mIOException) {
                throw new Error("UnableToUpdateDatabase");
            }

            try {
                mDb = mDBHelper.getWritableDatabase();
            } catch (SQLException mSQLException) {
                throw mSQLException;
            }


            String[] arryInfo=info.split(",");

//            ListView similarList1=viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.sim_1);
//            ListView similarList2=viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.sim_2);

            boolean isSimilarWord=false;

//            for(int i=0;i<similarList1.getChildCount();i++){
//                if(arryInfo.length>2) {
//                    Log.i("similar word:",""+((TextView)similarList1.getChildAt(i).findViewById(R.id.answer)).getText().toString());
//                    Log.i("arrayINfo[2]:",""+arryInfo[2].toLowerCase());
//
//                    if (((TextView) similarList1.getChildAt(i).findViewById(R.id.answer)).getText().toString().toLowerCase().equalsIgnoreCase(arryInfo[2].toLowerCase())) {
//                        isSimilarWord = true;
//                    }
//                }
//            }
//
//            for(int i=0;i<similarList2.getChildCount();i++){
//                if(arryInfo.length>2) {
//
//                    Log.i("similar word:",""+((TextView)similarList2.getChildAt(i).findViewById(R.id.answer)).getText().toString());
//                    Log.i("arrayINfo[2]:",""+arryInfo[2].toLowerCase());
//
//                    if (((TextView) similarList2.getChildAt(i).findViewById(R.id.answer)).getText().toString().toLowerCase().equalsIgnoreCase(arryInfo[2].toLowerCase())) {
//                        isSimilarWord = true;
//                    }
//                }
//            }




            //type 0: cancel the pop up
            //type 1: display the meaning of main word
            //type 2: display the meaning of second word
            //type 3: display nothing
            if(arryInfo[0].equalsIgnoreCase("0")){
                Log.i("touch ","up"+pw1.isShowing());

                //if(pw.isShowing())
                    pw1.dismiss();
                    scrollLayout.setDraggable(true);

            }

            if(arryInfo[0].equalsIgnoreCase("1")) {

                popupMeaning(arryInfo[1],pw1);
                scrollLayout.setDraggable(false);

                textToSpeech.speak(arryInfo[1],TextToSpeech.QUEUE_FLUSH, null);

                ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).setText("notRemember");
                ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.word))).setTextColor(Color.BLACK);

                try {
                    mDBHelper.updateDataBase();
                } catch (IOException mIOException) {
                    throw new Error("UnableToUpdateDatabase");
                }

                try {
                    mDb = mDBHelper.getWritableDatabase();
                } catch (SQLException mSQLException) {
                    throw mSQLException;
                }
                mDb.execSQL("UPDATE words SET REMEMBER = 0 WHERE word = '"+arryInfo[1]+"';");


                textTagsAdapter.lightOff(arryInfo[1]);

                tagCloudView.onChange();
            }else if(arryInfo[0].equalsIgnoreCase("2")){





                popupMeaning(arryInfo[2],pw1);
                textToSpeech.speak(arryInfo[2],TextToSpeech.QUEUE_FLUSH, null);
                Log.i("2:Is similar word:",""+isSimilarWord);

                if(!isSimilarWord) {
                    ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).setText("notRemember");
                    ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.word))).setTextColor(Color.BLACK);

                    try {
                        mDBHelper.updateDataBase();
                    } catch (IOException mIOException) {
                        throw new Error("UnableToUpdateDatabase");
                    }

                    try {
                        mDb = mDBHelper.getWritableDatabase();
                    } catch (SQLException mSQLException) {
                        throw mSQLException;
                    }

                    mDb.execSQL("UPDATE words SET REMEMBER = 0 WHERE word = '" + arryInfo[1] + "';");


                    textTagsAdapter.lightOff(arryInfo[1]);

                    tagCloudView.onChange();
                }


            }else if(arryInfo[0].equalsIgnoreCase("3")){
                textToSpeech.speak(arryInfo[2],TextToSpeech.QUEUE_FLUSH, null);

                String remembered=                           ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).getText().toString();
                Log.i("remembered????:",remembered+"");

                Log.i("3:Is similar word:",""+isSimilarWord);


                if((!isSimilarWord)&&(!remembered.equals("remembered"))) {



                    ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).setText("notRemember");
                    ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.word))).setTextColor(Color.BLACK);


                    try {
                        mDBHelper.updateDataBase();
                    } catch (IOException mIOException) {
                        throw new Error("UnableToUpdateDatabase");
                    }

                    try {
                        mDb = mDBHelper.getWritableDatabase();
                    } catch (SQLException mSQLException) {
                        throw mSQLException;
                    }

                    mDb.execSQL("UPDATE words SET REMEMBER = 0 WHERE word = '" + arryInfo[1] + "';");


                    textTagsAdapter.lightOff(arryInfo[1]);

                    tagCloudView.onChange();
                }
            }

//            int positionOfOff=-1;
//
//            for(int i=0;i<words.size();i++){
//                if (words.get(i).getWord().equalsIgnoreCase(info)){
//                    positionOfOff=i;
//                    break;
//                }
//
//
//
//            }



            //tagCloudView.onChange();




            //mDb.close();



            //viewPager.setVisibility(View.INVISIBLE);

            //wordsDB.close();
            //todo light off


        }else {
            //Toast.makeText(MainActivity.this, "please input", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Log.i("vice input:",""+result.get(0));

                String text=result.get(0).toLowerCase();


                if(scrollLayout.getCurrentStatus()==ScrollLayout.Status.CLOSED) {//the status is not right be carefull!!! closed means open

                    //pw.dismiss();




                    Log.i("input string:",""+text);




                    //viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.sim_1).setBackgroundColor(Color.RED);

                    //ListView listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.sim_1)));
                    String notRemember=((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).getText().toString();
//                    Log.i("not remember:",notRemember);
//                    for(int i=0;i<listViewSim1.getChildCount();i++){
//                        Log.i("answer string:",""+((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText());
//
//                        if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().toLowerCase().equalsIgnoreCase(text.toLowerCase())){
//                            ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(text.toLowerCase());
//
//                            if(!notRemember.equalsIgnoreCase("notRemember")) {//if the card condition is not not_remmebered, then change the word status to remembered and light on
//                                //((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.word))).setTextColor(Color.GREEN);
//
//
//                                //mDb.execSQL("UPDATE words SET REMEMBER = 1 WHERE word = '" + ((TextView) findViewById(R.id.word)).getText().toString() + "';");
//
//
//                                //textTagsAdapter.lightOn(((TextView) findViewById(R.id.word)).getText().toString());
//                                //tagCloudView.onChange();
//                            }
//                        }
//                    }
//
//                    listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.sim_2)));
//
//                    for(int i=0;i<listViewSim1.getChildCount();i++){
//                        Log.i("answer string:",""+((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText());
//
//                        if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().toLowerCase().equalsIgnoreCase(text.toLowerCase())){
//                            ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(text.toLowerCase());
//
//                            if(!notRemember.equalsIgnoreCase("notRemember")) {
//
////                                ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.word))).setTextColor(Color.GREEN);
////
////
////                                mDb.execSQL("UPDATE words SET REMEMBER = 1 WHERE word = '" + ((TextView) findViewById(R.id.word)).getText().toString() + "';");
////
////
////                                textTagsAdapter.lightOn(((TextView) findViewById(R.id.word)).getText().toString());
////                                tagCloudView.onChange();
//                            }
//                        }
//                    }

                    ListView listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.syno_list)));

                    for(int i=0;i<listViewSim1.getChildCount();i++){
                        Log.i("answer string:",""+((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText());

                        if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().toLowerCase().equalsIgnoreCase(text.toLowerCase())){
                            ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(text.toLowerCase());


                            if(!notRemember.equalsIgnoreCase("notRemember")) {

                                ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.word))).setTextColor(Color.GREEN);
                                ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).setText("remembered");
                                Log.i("set remember",""+((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.not_remember))).getText().toString());

                                mDb.execSQL("UPDATE words SET REMEMBER = 1 WHERE word = '" + ((TextView) findViewById(R.id.word)).getText().toString() + "';");


                                textTagsAdapter.lightOn(((TextView) findViewById(R.id.word)).getText().toString());
                                //tagCloudView.onChange();
                            }
                        }
                    }

                    listViewSim1= ((ListView) (viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.anto_list)));

                    for(int i=0;i<listViewSim1.getChildCount();i++){
                        Log.i("answer string:",""+((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText());

                        if (((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString().toLowerCase().equalsIgnoreCase(text.toLowerCase())){
                            ((TextView)listViewSim1.getChildAt(i).findViewById(R.id.anto_or_syno_text)).setText(((TextView)listViewSim1.getChildAt(i).findViewById(R.id.answer)).getText().toString());

                            if(!notRemember.equalsIgnoreCase("notRemember")) {
                                ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.word))).setTextColor(Color.GREEN);
                                ((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).setText("remembered");
                                Log.i("set remember",""+((TextView)(viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()%words.size()).getWord()).findViewById(R.id.not_remember))).getText().toString());



                                mDb.execSQL("UPDATE words SET REMEMBER = 1 WHERE word = '" + ((TextView) findViewById(R.id.word)).getText().toString() + "';");


                                textTagsAdapter.lightOn(((TextView) findViewById(R.id.word)).getText().toString());
                                //tagCloudView.onChange();
                            }
                        }
                    }


                    //viewPager.findViewWithTag(words.get(viewPager.getCurrentItem()).getWord()).findViewById(R.id.syno).setBackgroundColor(Color.GREEN);
                }else{
                    //String text=result.get(0);
                    //popupMeaning(text);//todo list fragment
                    Log.i("tag clicked:",""+scrollLayout.getLayoutMode());


                    //viewPager.setVisibility(View.VISIBLE);

                    //Log.e("Click", "Tag " + position + " clicked.");
                    //Toast.makeText(getApplicationContext(), "Tag " + position + " clicked", Toast.LENGTH_SHORT).show();
                    int position=-1;
                    Collections.sort(words,c);

                    for(int i=0;i<words.size();i++){
                        if(words.get(i).getWord().equalsIgnoreCase((text))){
                            position=i;
                        }
                    }
                    if(position!=-1) {

                        scrollLayout.setToClosed();

                        viewPager = findViewById(R.id.pager);
                        viewPager.setVisibility(View.VISIBLE);
                        fm = getSupportFragmentManager();


//                        for(int j=0;j<NUM_ITEMS;j++){
//                            temp[j]=words.get(j);
//                        }
                        viewPagerAdapter = new MyAdapter(fm, words, position);
                        //viewPagerAdapter.instantiateItem(viewPager,position);//?
                        viewPager.setAdapter(viewPagerAdapter);
                        viewPager.setCurrentItem(position);//produce word detail card

                    }

                }



                //textView.setText(result.get(0));
            }
        }
    }

public boolean popupMeaning(String info,PopupWindow pw1){

    Log.i("pop up","begin");
     DatabaseMeaningHelper meaningHelper;
    SQLiteDatabase meaningDB;

    meaningHelper=new DatabaseMeaningHelper(this);


    try {
        meaningHelper.updateDataBase();
    } catch (IOException mIOException) {
        throw new Error("UnableToUpdateDatabase");
    }

    try {
        meaningDB = meaningHelper.getWritableDatabase();
    } catch (SQLException mSQLException) {
        throw mSQLException;
    }



    //wordsDB.execSQL("UPDATE words SET REMEMBER = 0 WHERE word = '"+info+"';");


    //receive infromation from list
    //Toast.makeText(MainActivity.this, "content get from list view: "+info, Toast.LENGTH_SHORT).show();


    //if( !Character.isLowerCase(info.charAt(0)) ){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    //pw = new PopupWindow(inflater.inflate(R.layout.popup_meaning, null, false),1000,500, true);

        //pw.showAtLocation(findViewById(R.id.cloud_pager), Gravity.TOP, 0, 0);



        //pw1.setElevation(1.1f);



    Log.i("pw1",""+pw1.isShowing());

        ((TextView)pw1.getContentView().findViewById(R.id.word)).setText(info);

        ArrayList<String> meaningList=new ArrayList<String>();

    try {
        mDBHelper.updateDataBase();
    } catch (IOException mIOException) {
        throw new Error("UnableToUpdateDatabase");
    }

    try {
        mDb = mDBHelper.getWritableDatabase();
    } catch (SQLException mSQLException) {
        throw mSQLException;
    }


        Cursor resultSet = meaningDB.rawQuery("Select Definition from 'words' where Word="+"'"+info+"';",null);

        Log.i("cursor get count",resultSet.getCount()+"");

        if(resultSet.getCount()!=0) {


            resultSet.moveToNext();




            //TextView pronuciation = pw1.getContentView().findViewById(R.id.pronouciation);


            //pronuciation.setText(resultSet.getString(0));


            //if (resultSet.getString(3) != null) {
               // Log.i("v.:", "" + resultSet.getString(3));
                ((TextView) pw1.getContentView().findViewById(R.id.textView3)).setText("v. " + resultSet.getString(0));
                pw1.getContentView().findViewById(R.id.textView3).setVisibility(View.VISIBLE);

            pw1.setFocusable(true);

// 设置允许在外点击消失
            pw1.setOutsideTouchable(true);

//刷新状态
            pw1.update();

//点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
// 设置背景色 ...
            pw1.setBackgroundDrawable(new BitmapDrawable());

// new BitmapDrawable() 已经过时，可以使用 new BitmapDrawable(this.getResources())
// 背景色的设置还可以
// popup.setBackgroundDrawable(new ColorDrawable(0x55000000));


                pw1.showAtLocation(findViewById(R.id.container), Gravity.CENTER,0,0);
            //}

//            if (resultSet.getString(4) != null) {
//                Log.i("vt.:", "" + resultSet.getString(4));
//                ((TextView) pw1.getContentView().findViewById(R.id.textView4)).setText("vt. " + resultSet.getString(4));
//                pw1.getContentView().findViewById(R.id.textView4).setVisibility(View.VISIBLE);
//            }
//
//            if (resultSet.getString(5) != null) {
//                Log.i("vi.:", "" + resultSet.getString(5));
//                ((TextView) pw1.getContentView().findViewById(R.id.textView5)).setText("vi. " + resultSet.getString(5));
//                pw1.getContentView().findViewById(R.id.textView5).setVisibility(View.VISIBLE);
//            }
//
//            if (resultSet.getString(6) != null) {
//                Log.i("n.:", "" + resultSet.getString(6));
//                ((TextView) pw1.getContentView().findViewById(R.id.textView6)).setText("n. " + resultSet.getString(6));
//                pw1.getContentView().findViewById(R.id.textView6).setVisibility(View.VISIBLE);
//            }
//
//            if (resultSet.getString(7) != null) {
//                Log.i("adj.:", "" + resultSet.getString(7));
//                ((TextView) pw1.getContentView().findViewById(R.id.textView7)).setText("adj. " + resultSet.getString(7));
//                pw1.getContentView().findViewById(R.id.textView7).setVisibility(View.VISIBLE);
//            }
//            if (resultSet.getString(8) != null) {
//                Log.i("adv.:", "" + resultSet.getString(8));
//                ((TextView) pw1.getContentView().findViewById(R.id.textView8)).setText("adv. " + resultSet.getString(8));
//                pw1.getContentView().findViewById(R.id.textView8).setVisibility(View.VISIBLE);
//            }
//
//            if (resultSet.getString(9) != null) {
//                Log.i("prep.:", "" + resultSet.getString(9));
//                ((TextView) pw1.getContentView().findViewById(R.id.textView9)).setText("prep. " + resultSet.getString(9));
//                pw1.getContentView().findViewById(R.id.textView9).setVisibility(View.VISIBLE);
//            }
//
//            if (resultSet.getString(10) != null) {
//                Log.i("conj.:", "" + resultSet.getString(10));
//                ((TextView) pw1.getContentView().findViewById(R.id.textView10)).setText("conj. " + resultSet.getString(10));
//                pw1.getContentView().findViewById(R.id.textView10).setVisibility(View.VISIBLE);
//            }
//
//            if (resultSet.getString(11) != null) {
//                Log.i("sentence.:", "" + resultSet.getString(11));
//
//                ((TextView) pw1.getContentView().findViewById(R.id.textView11)).setText("sentence. " + resultSet.getString(11));
//                pw1.getContentView().findViewById(R.id.textView11).setVisibility(View.VISIBLE);
//            }

        }

//        pw.getContentView().findViewById(R.id.speak).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                textToSpeech.speak(((TextView) pw.getContentView().findViewById(R.id.word)).getText().toString(),TextToSpeech.QUEUE_FLUSH, null);
//            }
//        });



    //}


    Log.i("content from list view:",info);

    int positionOfOff=-1;

    for(int i=0;i<words.size();i++){
        if (words.get(i).getWord().equalsIgnoreCase(info)){
            positionOfOff=i;
            break;
        }



    }


//    textTagsAdapter.lightOff(info);
//
//    tagCloudView.onChange();

    //viewPager.setVisibility(View.INVISIBLE);
//resultSet.close();
    //mDb.close();
    //todo light off




        return true;
}


//    @Override//set action down but not work
//    public boolean onTouchEvent(MotionEvent event) {
//        if(event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
//            Log.i("action up","action down");
//
//        }
//        if(event.getAction() == MotionEvent.ACTION_UP) {
//
//        }
//        Log.i("action up","action down");
//
//
//        return super.onTouchEvent(event);
//    }

}
