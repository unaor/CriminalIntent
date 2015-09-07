package com.datasol.criminalintent.activities;

import android.support.v4.app.Fragment;
import com.datasol.criminalintent.fragment.CrimeListFragment;

/**
 * Created by unaor on 9/7/2015.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
