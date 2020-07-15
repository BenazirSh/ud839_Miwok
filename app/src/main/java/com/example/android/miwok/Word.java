package com.example.android.miwok;

public class Word {
    private String miwokTranslation;
    private String defaultTranslation;
    private int imageResourceId;
    private int audioResourceId;

    public Word(String english, String miwok, int AudioId){
        miwokTranslation = miwok;
        defaultTranslation = english;
        audioResourceId = AudioId;
    }

    public Word(String english, String miwok, int id, int AudioId){
        miwokTranslation = miwok;
        defaultTranslation = english;
        imageResourceId=id;
        audioResourceId = AudioId;
    }

    public String getDefaultTranslation()
    {
        return  defaultTranslation;
    }
    public String getMiwokTranslation(){
        return  miwokTranslation;
    }
    public int getImageResourceId(){return imageResourceId; }
    public int getAudioResourceId(){return audioResourceId;}

    @Override
    public String toString() {
        return "Word{" +
                "miwokTranslation='" + miwokTranslation + '\'' +
                ", defaultTranslation='" + defaultTranslation + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", audioResourceId=" + audioResourceId +
                '}';
    }
}
