package com.datasol.criminalintent.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import com.datasol.criminalintent.R;

/**
 * Created by brute on 9/14/2015.
 */
public class DatePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      return new AlertDialog.Builder(getActivity()).
              setTitle(R.string.date_picker_title).
              setPositiveButton(android.R.string.ok,null).
              create();
    }
}
