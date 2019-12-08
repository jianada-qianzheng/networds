package com.networds.networds;

public class Word {


    private String word;
    private String anto1;
    private String anto2;
    private String anto3;

    private String syno1;
    private String syno2;
    private String syno3;

    private int weight;

    int remember;

    public Word(String word, String syno1,String syno2,String syno3, String anto1,String anto2,String anto3,int remember,int weight){

        this.word=word;
        this.remember=remember;
        this.anto1=anto1;
        this.syno1=syno1;
        this.anto2=anto2;
        this.syno2=syno2;
        this.anto3=anto3;
        this.syno3=syno3;
        this.weight=weight;
    }

    public String getWord(){
        return word;

    }

    public String getAnto1(){
        return anto1;

    }
    public String getSyno1(){
        return syno1;

    }
    public String getAnto2(){
        return anto2;

    }
    public String getSyno2(){
        return syno2;

    }
    public String getAnto3(){
        return anto3;

    }
    public String getSyno3(){
        return syno3;

    }

    public int getWeight(){
        return weight;

    }





    public int getRemember(){
        return remember;
    }



    public int setWord(String word){
        this.word=word;
        return 0;
    }


}

