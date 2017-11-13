package com.a1ubkh4n.app.classroom.past_attendances;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.a1ubkh4n.app.classroom.R;
import com.a1ubkh4n.app.classroom.adapters.TakeAttendanceAdapter;
import com.a1ubkh4n.app.classroom.database.DatabaseManager;
import com.a1ubkh4n.app.classroom.date_time_pickers.CustomDatePickerDialog;
import com.a1ubkh4n.app.classroom.date_time_pickers.CustomTimePickerDialog;
import com.a1ubkh4n.app.classroom.date_time_pickers.DatePickerFragment;
import com.a1ubkh4n.app.classroom.date_time_pickers.TimePickerFragment;
import com.a1ubkh4n.app.classroom.interfaces.AdapterClickListener;
import com.a1ubkh4n.app.classroom.interfaces.DateBackListener;
import com.a1ubkh4n.app.classroom.model.Classroom;
import com.a1ubkh4n.app.classroom.model.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by a1ubkh4n on 4/16/2016.<br />
 * Alters a past attendance.
 */
public class PastAttendanceActivity extends AppCompatActivity implements DateBackListener {
    private Context context;
    private Toolbar toolbar;

    private RecyclerView list;
    private ArrayList<Student> arrayList = new ArrayList<>();
    private TakeAttendanceAdapter adapter;

    private TextView emptyText; //empty list view text

    private Classroom classroom;
    private String dateTime = "";

    //date and time pickers
    private DatePickerFragment datePickerFragment;
    private TimePickerFragment timePickerFragment;
    private CustomDatePickerDialog datePickerDialog;
    private CustomTimePickerDialog timePickerDialog;
    private Date changedDate;

    //save button
    private FloatingActionButton floatingActionButton;

    //select all - all students are present or absent
    private AppCompatCheckBox checkBoxSelectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectable_list);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            classroom = args.getParcelable("classroom");
            dateTime = args.getString("dateTime");
        }

        context = this;

        //toolbar
        setToolbar();

        list = (RecyclerView) findViewById(R.id.list);
        adapter = new TakeAttendanceAdapter(context, arrayList);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(context));
        list.setHasFixedSize(true);

        emptyText = (TextView) findViewById(R.id.emptyText);
        emptyText.setText(getString(R.string.emptyMessageSave));

        addAdapterClickListener();

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        startButtonAnimation();

        checkBoxSelectAll = (AppCompatCheckBox) findViewById(R.id.checkBoxSelectAll);
        checkBoxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!arrayList.isEmpty()) {
                    //make all students present or absent
                    for (Student stud : arrayList) {
                        stud.setPresent(isChecked);
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });

        new SelectAttendingStudents().execute();
    }

    /**
     * Create toolbar and set its attributes
     */
    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        if (toolbar != null && classroom != null && dateTime != null) {
            setTitle(classroom.getName());
            toolbar.setSubtitle(dateTime);
        }
    }

    /**
     * Set empty list text
     */
    private void setEmptyText() {
        if (emptyText != null) {
            if (arrayList.isEmpty()) {
                emptyText.setVisibility(View.VISIBLE);
            } else {
                emptyText.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Set floating action button with its animation
     */
    private void startButtonAnimation() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                floatingActionButton.setImageResource(R.drawable.ic_action_save);
                floatingActionButton.show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateAttendance().execute();
            }
        });
    }

    /**
     * List item click event
     */
    private void addAdapterClickListener() {
        adapter.setAdapterClickListener(new AdapterClickListener() {
            @Override
            public void OnItemClick(int position) {
                if (arrayList.size() > position) {
                    Student student = arrayList.get(position);
                    boolean isPresent = !student.isPresent();
                    arrayList.get(position).setPresent(isPresent);

                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Shows date picker
     */
    private void changeDate() {
        if (Build.VERSION.SDK_INT < 21) {
            datePickerDialog = new CustomDatePickerDialog(context);
            datePickerDialog.show();
        } else {
            datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), "DatePickerFragment");
        }
    }

    /**
     * Shows time picker
     */
    private void changeTime() {
        if (Build.VERSION.SDK_INT < 21) {
            timePickerDialog = new CustomTimePickerDialog(context);
            timePickerDialog.show();
        } else {
            timePickerFragment = new TimePickerFragment();
            timePickerFragment.show(getSupportFragmentManager(), "TimePickerFragment");
        }
    }

    /**
     * Makes the change both on variable that will be send to DB and on the toolbar subtitle
     */
    private void changeDateTime() {
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        dateTime = targetFormat.format(changedDate);
        toolbar.setSubtitle(dateTime);
    }

    @Override
    public void OnPress(int dayOfMonth, int month, int year) {
        changedDate.setYear(year - 1900);
        changedDate.setMonth(month);
        changedDate.setDate(dayOfMonth);

        changeTime();
    }

    @Override
    public void OnPress(int minute, int hour) {
        changedDate.setHours(hour);
        changedDate.setMinutes(minute);

        changeDateTime();
    }

    /**
     * Select attending and non-attending students from DB
     */
    private class SelectAttendingStudents extends AsyncTask<Void, Void, ArrayList<Student>> {

        @Override
        protected ArrayList<Student> doInBackground(Void... params) {
            ArrayList<Student> tmpList = null;
            if (classroom != null) {
                DatabaseManager databaseManager = new DatabaseManager(context);
                tmpList = databaseManager.selectAttendingStudents(dateTime, classroom.getId());
            }

            return tmpList;
        }

        @Override
        protected void onPostExecute(ArrayList<Student> tmpList) {
            arrayList.clear();

            if (tmpList != null) {
                arrayList.addAll(tmpList);
                adapter.notifyDataSetChanged();

                setEmptyText();
            }
        }
    }

    /**
     * Update attendance
     */
    private class UpdateAttendance extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            DatabaseManager databaseManager = new DatabaseManager(context);
            int rowsAffected = databaseManager.updateAttendance(arrayList, dateTime);

            return rowsAffected;
        }

        @Override
        protected void onPostExecute(Integer rowsAffected) {
            if (rowsAffected > 0) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
            }

            closeWindow();
        }
    }

    private void closeWindow() {
        finish();
        overridePendingTransition(R.anim.stand_still, R.anim.move_out_to_bottom);
    }

    @Override
    public void onBackPressed() {
        closeWindow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_past_attendance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar actions click
        switch (item.getItemId()) {
            case android.R.id.home:
                closeWindow();
                return true;
            case R.id.changeDateTime:
                changedDate = new Date();
                changeDate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}