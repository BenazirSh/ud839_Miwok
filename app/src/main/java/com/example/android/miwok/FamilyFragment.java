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
public class FamilyFragment extends Fragment {


    public FamilyFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_family, container, false);

        /** TODO: Insert all the code from the FamilyActivity’s onCreate() method after the setContentView method call */
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<WordList> lists = new ArrayList<WordList>();
        lists.add(new WordList("mother", "nanajon", R.drawable.family_mother, R.raw.family_older_sister));
        lists.add(new WordList("father", "dada", R.drawable.family_father, R.raw.phrase_yes_im_coming));
        lists.add(new WordList("grandfather", "bobo", R.drawable.family_grandfather, R.raw.family_younger_brother));
        lists.add(new WordList("sister", "apa/uka", R.drawable.family_younger_sister, R.raw.color_dusty_yellow));
        lists.add(new WordList("brother", "aka/uka", R.drawable.family_older_brother, R.raw.family_younger_sister));
        lists.add(new WordList("daughter", "duxtar(bacha)", R.drawable.family_daughter, R.raw.family_daughter));
        lists.add(new WordList("son", "pisar(bacha)", R.drawable.family_son, R.raw.family_son));

        WordListAdapter adapter = new WordListAdapter(getActivity(), lists, R.color.category_family);
        ListView listView = (ListView) rootView.findViewById(R.id.secondList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseAudioResource();
                int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback
                    WordList positionOfWord = lists.get(position);
                    MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), positionOfWord.returnAudioId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(nameOfListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseAudioResource();
    }

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
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
                releaseAudioResource();
            }
        }
    };

    private MediaPlayer.OnCompletionListener nameOfListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseAudioResource();

        }
    };
    private void releaseAudioResource(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }

    }

}
