package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {


    public ColorsFragment() {
        // Required empty public constructor
    }

    private MediaPlayer mediaPlayer;
    private  AudioManager audioManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_colors, container, false);

        /** TODO: Insert all the code from the ColorsActivity’s onCreate() method after the setContentView method call */
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<WordList> lists = new ArrayList<WordList>();
        lists.add(new WordList("red", "surx", R.drawable.color_red, R.raw.color_red));
        lists.add(new WordList(" dusty yellow", "jigarcha", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        lists.add(new WordList("white","safed", R.drawable.color_white, R.raw.color_white));
        lists.add(new WordList("black", "siyo", R.drawable.color_black, R.raw.color_black));
        lists.add(new WordList("green", "pistaqi", R.drawable.color_green, R.raw.color_green));
        lists.add(new WordList("brown", "jigari", R.drawable.color_brown, R.raw.color_brown));
        lists.add(new WordList("gray", "серый", R.drawable.color_gray, R.raw.color_gray));
        lists.add(new WordList("yellow", "zard", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));


        WordListAdapter adapt = new WordListAdapter(getActivity(), lists, R.color.category_colors);
        ListView listView = (ListView)rootView.findViewById(R.id.third_list);
        listView.setAdapter(adapt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                returnAuduiResource();
                int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback
                    WordList positionOfWord = lists.get(position);


                    mediaPlayer = MediaPlayer.create(getActivity(), positionOfWord.returnAudioId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mMediaPlayerListener);
                }
            }
        });

        return rootView;
    }

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                returnAuduiResource();
            }
        }
    };
    private MediaPlayer.OnCompletionListener mMediaPlayerListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            returnAuduiResource();
        }
    };
    private void returnAuduiResource(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        returnAuduiResource();
    }

}
