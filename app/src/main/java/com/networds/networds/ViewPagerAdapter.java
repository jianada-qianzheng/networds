//package com.example.networds;
//
//import android.content.Context;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//
//public class ViewPagerAdapter extends FragmentStatePagerAdapter {
//
//    private Context context;
//    private LayoutInflater layoutInflater;
//    private Word[] words ;
//    private  int position_original;
//
//
//
//    public ViewPagerAdapter(FragmentManager fm) {
//            super(fm);
//    }
//
//    public ViewPagerAdapter(FragmentManager fm, Context context,Word[] words,int position_original) {
//        super(fm);
//
//        this.context = context;
//        this.words=words;
//        this.position_original=position_original;
//
//        Word[] temp = new Word[40];
//
//        for(int i=0;i<40;i++){
//
//            if (position_original+i<40)
//                temp[i]=words[position_original+i];
//            else{
//                temp[i]=words[position_original+i-40];
//            }
//        }
//
//        this.words=temp;
//
//    }
//
//
////    public ViewPagerAdapter(Context context) {
////        this.context = context;
////    }
//
//    @Override
//        public DetailFragment getItem(int position) {
//            return DetailFragment.newInstance("detail1","detail2");
//        }
//
//    @Override
//    public int getCount() {
//        return 40;
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//
////    @Override
////    public Object instantiateItem(final ViewGroup container, final int position) {
////
////        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        View view = layoutInflater.inflate(R.layout.fragment_detail, null);
////        //TextView textView = (TextView) view.findViewById(R.id.text);
////
////        //change the first words postion
////
////
////
////        //textView.setText(words[position].getWord());
////
////        textView.setOnLongClickListener(new View.OnLongClickListener()
////        {
////
////            @Override
////            public boolean onLongClick(View v)
////            {
////                LayoutInflater inflater = (LayoutInflater)( context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
////
////                View layout = inflater.inflate(R.layout.popup_meaning,
////                        (ViewGroup) container.findViewById(R.id.layoutMeaning));
////
////
//////
////                PopupWindow pw = new PopupWindow(layout,1000,1000, true);
////
////                //pw.showAtLocation(container.findViewById(R.id.groupView), Gravity.CENTER, 0, 0);
////
////                pw.showAtLocation(container,Gravity.CENTER,0,0);
////
////                TextView textViewMeaning=layout.findViewById(R.id.textViewMeaning);
////
////                textViewMeaning.setText(words[position].getWord());
////
////
////                return true;
////            }
////        });
////
////
////
////        ViewPager vp = (ViewPager) container;
////        vp.addView(view, 0);
////        return view;
////
////    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//
//        ViewPager vp = (ViewPager) container;
//        View view = (View) object;
//        vp.removeView(view);
//
//    }
//
//
//
//
//}
