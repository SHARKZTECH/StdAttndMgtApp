package com.example.studentattendancemanagementapplication;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ViewAbsentActivity extends AppCompatActivity {

    private LinearLayout absentListLayout;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_absent);

        absentListLayout = findViewById(R.id.absent_list);
        databaseHelper = new DatabaseHelper(this);

        List<Student> absentStudents = databaseHelper.getAbsentStudents();
        for (Student student : absentStudents) {
            TextView textView = new TextView(this);
            textView.setText(student.getFirstName() + " " + student.getLastName() + " - Absences: " + student.getAbsenceCount());
            absentListLayout.addView(textView);
        }
    }
}
