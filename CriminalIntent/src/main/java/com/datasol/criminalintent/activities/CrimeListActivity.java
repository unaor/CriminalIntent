package com.datasol.criminalintent.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.datasol.criminalintent.R;
import com.datasol.criminalintent.fragment.CrimeFragment;
import com.datasol.criminalintent.fragment.CrimeListFragment;
import com.datasol.criminalintent.model.Crime;

/**
 * Created by unaor on 9/7/2015.
 */
public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks ,
    CrimeFragment.Callbacks{

    private final String TAG= "CrimeListActivity";

    @Override
    protected Fragment createFragment() {
        boolean showSubtitle = (boolean)getIntent().getBooleanExtra(CrimeListFragment.SAVED_SUBTITLE_VISIBLE,false);
        Log.d(TAG,"Starting new CrimeListFragment with subtitle as "+ showSubtitle);
        return CrimeListFragment.newInstance(showSubtitle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) == null){
            //its a phone
            Intent intent  = CrimePagerActivity.newIntent(this,crime.getId());
        } else {
            //its a tablet
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());

            getSupportFragmentManager().beginTransaction().replace
                    (R.id.detail_fragment_container, newDetail).commit();
        }

    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
