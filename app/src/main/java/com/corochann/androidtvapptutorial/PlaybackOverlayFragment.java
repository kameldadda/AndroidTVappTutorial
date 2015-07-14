package com.corochann.androidtvapptutorial;

import android.content.Context;
import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.ControlButtonPresenterSelector;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.support.v17.leanback.widget.PlaybackControlsRowPresenter;
import android.util.Log;

/**
 * Created by corochann on 7/7/2015.
 */
public class PlaybackOverlayFragment extends android.support.v17.leanback.app.PlaybackOverlayFragment {

    private static final String TAG = PlaybackOverlayFragment.class.getSimpleName();

    private Movie mSelectedMovie;
    private PlaybackControlsRow mPlaybackControlsRow;
    private ArrayObjectAdapter mPrimaryActionAdapter;
    private ArrayObjectAdapter mSecondaryActionAdapter;
    private Context sContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        sContext = getActivity();

        mSelectedMovie = (Movie) getActivity().getIntent().getSerializableExtra(DetailsActivity.MOVIE);

        setBackgroundType(PlaybackOverlayFragment.BG_LIGHT);
        setFadingEnabled(true);

        setUpRows();
    }

    private ArrayObjectAdapter mRowsAdapter;

    private void setUpRows() {
        ClassPresenterSelector ps = new ClassPresenterSelector();

        PlaybackControlsRowPresenter playbackControlsRowPresenter;
        playbackControlsRowPresenter = new PlaybackControlsRowPresenter(new DetailsDescriptionPresenter());

        ps.addClassPresenter(PlaybackControlsRow.class, playbackControlsRowPresenter);
        ps.addClassPresenter(ListRow.class, new ListRowPresenter());
        mRowsAdapter = new ArrayObjectAdapter(ps);

        /*
         * Add PlaybackControlsRow to mRowsAdapter, which makes video control UI.
         * PlaybackControlsRow is supposed to be first Row of mRowsAdapter.
         */
        addPlaybackControlsRow();
        /* add ListRow to second row of mRowsAdapter */
        addOtherRows();

        setAdapter(mRowsAdapter);

    }

    private void addPlaybackControlsRow() {
        mPlaybackControlsRow = new PlaybackControlsRow(mSelectedMovie);
        mRowsAdapter.add(mPlaybackControlsRow);

        ControlButtonPresenterSelector presenterSelector = new ControlButtonPresenterSelector();
        mPrimaryActionAdapter = new ArrayObjectAdapter(presenterSelector);
        mSecondaryActionAdapter = new ArrayObjectAdapter(presenterSelector);
        mPlaybackControlsRow.setPrimaryActionsAdapter(mPrimaryActionAdapter);
        mPlaybackControlsRow.setSecondaryActionsAdapter(mSecondaryActionAdapter);


        /* PrimaryAction setting */
        mPrimaryActionAdapter.add(new PlaybackControlsRow.SkipPreviousAction(sContext));
        mPrimaryActionAdapter.add(new PlaybackControlsRow.RewindAction(sContext));
        mPrimaryActionAdapter.add(new PlaybackControlsRow.PlayPauseAction(sContext));
        mPrimaryActionAdapter.add(new PlaybackControlsRow.FastForwardAction(sContext));
        mPrimaryActionAdapter.add(new PlaybackControlsRow.SkipNextAction(sContext));

        /* SecondaryAction setting */
        mSecondaryActionAdapter.add(new PlaybackControlsRow.ThumbsUpAction(sContext));
        mSecondaryActionAdapter.add(new PlaybackControlsRow.ThumbsDownAction(sContext));
        mSecondaryActionAdapter.add(new PlaybackControlsRow.RepeatAction(sContext));
        mSecondaryActionAdapter.add(new PlaybackControlsRow.ShuffleAction(sContext));
        mSecondaryActionAdapter.add(new PlaybackControlsRow.HighQualityAction(sContext));
        mSecondaryActionAdapter.add(new PlaybackControlsRow.ClosedCaptioningAction(sContext));
    }

    private void addOtherRows() {
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new CardPresenter());
        Movie movie = new Movie();
        movie.setTitle("Title");
        movie.setStudio("studio");
        movie.setDescription("description");
        movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02580.jpg");
        listRowAdapter.add(movie);
        listRowAdapter.add(movie);

        HeaderItem header = new HeaderItem(0, "OtherRows");
        mRowsAdapter.add(new ListRow(header, listRowAdapter));
    }

}