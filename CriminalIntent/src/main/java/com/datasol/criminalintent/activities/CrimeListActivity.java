package com.datasol.criminalintent.activities;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.datasol.criminalintent.fragment.CrimeListFragment;

/**
 * Created by unaor on 9/7/2015.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    private final String TAG= "CrimeListActivity";

    @Override
    protected Fragment createFragment() {
        boolean showSubtitle = (boolean)getIntent().getBooleanExtra(CrimeListFragment.SAVED_SUBTITLE_VISIBLE,false);
        Log.d(TAG,"Starting new CrimeListFragment with subtitle as "+ showSubtitle);
        return CrimeListFragment.newInstance(showSubtitle);
    }
}
