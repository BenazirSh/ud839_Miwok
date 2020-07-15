package com.example.android.miwok;

public class WordList {
    private String tajikTranslation;
    private String englishTranslation;
    private int imageId;
    private int audioId;

    public WordList(String English, String Tajik, int audioIden){
        tajikTranslation = Tajik;
        englishTranslation = English;
        audioId = audioIden;
    }
    public WordList(String English, String Tajik, int id, int audioIdent){
        tajikTranslation = Tajik;
        englishTranslation = English;
        imageId = id;
        audioId = audioIdent;
    }
    public String getTajTranslation(){
        return  tajikTranslation;
    }
    public String getEngTranslation(){
        return englishTranslation;
    }
    public int returnImageId(){return imageId;}
    public int returnAudioId(){return  audioId;}


}
