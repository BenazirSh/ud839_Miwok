package com.example.android.miwok;

import android.app.Activity;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class WordListAdapter extends ArrayAdapter<WordList> {
    private static final String LOG_TAG = WordListAdapter.class.getSimpleName();
    private int ColorId;

    public WordListAdapter(Activity context, ArrayList<WordList> lists, int passedColorId){
        super(context, 0 , lists);
        ColorId = passedColorId;
    }
//    public WordAdapter(Activity context, ArrayList<Word> words){
//        super(context, 0, words);
//    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        }
        WordList currentList = getItem(position);
        TextView englishView = (TextView) listItemView.findViewById(R.id.Default_translation);
        englishView.setText(currentList.getEngTranslation());


        TextView tajikView = (TextView) listItemView.findViewById(R.id.Miwok_translation);
        tajikView.setText(currentList.getTajTranslation());



        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        imageView.setImageResource(currentList.returnImageId());
        View textView = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), ColorId);
        textView.setBackgroundColor(color);

        return  listItemView;
    }
}
