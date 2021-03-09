package project.topmovies.logic;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;

    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {

        DatePickerFragment fragment = new DatePickerFragment();

        fragment.setListener(listener);

        return fragment;

    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {

        this.listener = listener;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Selected date (initial value)
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), listener, year, month, day);

        // Min and max date
        c.set(Calendar.YEAR, year);

        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

        c.set(Calendar.MONTH, month + 6);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

        return datePickerDialog;

    }

}
