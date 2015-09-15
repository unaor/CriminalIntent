package com.datasol.criminalintent.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import com.datasol.criminalintent.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by unaor on 9/15/2015.
 */
public class TimePickerFragment extends DialogFragment {

    private static final String ARG_TIME="time";
    public static final String EXTRA_TIME = "com.datasol.criminalintent.time";
    private final String TAG="TimePickerFragment";

    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date time){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, time);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date)getArguments().getSerializable(ARG_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker)v.findViewById(R.id.time_dialog_time_picker);
        mTimePicker.setIs24HourView(true);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);

        return new AlertDialog.Builder(getActivity()).
                setView(v).
                setTitle(R.string.time_picker_title).
                setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = mTimePicker.getCurrentHour();
                        int minute = mTimePicker.getCurrentMinute();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        Date date = calendar.getTime();
                        sendResult(Activity.RESULT_OK, date);

                    }
                }).create();

    }

    private void sendResult(int resultCode ,Date time){
        if(getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, time);
        Log.d(TAG, "Posting data to target activity resultCode is " + resultCode + " Target activity tag is " + getTargetFragment().getTag());
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
