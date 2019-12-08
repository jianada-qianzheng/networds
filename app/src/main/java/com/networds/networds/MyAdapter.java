package com.networds.networds;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;

public class MyAdapter extends FragmentStatePagerAdapter
{
    private static final int NUM_ITEMS = 10;
    private ArrayList<Word> words;
    private int originalPosition;

    private long baseId = 0;


    MyAdapter(FragmentManager fm,ArrayList<Word> words,int originalPosition) {
        super(fm);
        this.words=words;
        this.originalPosition=originalPosition;


    }

    @Override
    public int getCount() {

        //return words.size();

        return Integer.MAX_VALUE;//todo unlimited pages

    }

    @Override
    public Fragment getItem(int position) {
        Word aWord = words.get(position%words.size());
        ArrayList<CharSequence> detail =new ArrayList<CharSequence>();

        detail.add(aWord.getWord());


        detail.add(aWord.getSyno1());

        detail.add(aWord.getSyno2());

        detail.add(aWord.getSyno3());


        detail.add(aWord.getAnto1());

        detail.add(aWord.getAnto2());

        detail.add(aWord.getAnto3());


        detail.add(aWord.getRemember()+"");

        //todo
        return ArrayListFragment.newInstance(detail);
    }

    public void deletePage(int position) {
        if (canDelete()) {
            words.remove(position);
            notifyDataSetChanged();
        }
    }

    boolean canDelete() {
        return words.size() > 0;
    }

    // This is called when notifyDataSetChanged() is called
    @Override
    public int getItemPosition(Object object) {
        // refresh all fragments when data set changed
        return PagerAdapter.POSITION_NONE;
    }
    public void notifyChangeInPosition(int n) {
        // shift the ID returned by getItemId outside the range of all previous fragments
        baseId += getCount() + n;
    }

}

