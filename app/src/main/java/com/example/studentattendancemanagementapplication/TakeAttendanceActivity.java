package com.example.studentattendancemanagementapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class TakeAttendanceActivity extends AppCompatActivity {

    private LinearLayout studentListLayout;
    private DatabaseHelper databaseHelper;
    private List<Student> studentList;
    private List<CheckBox> checkBoxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        studentListLayout = findViewById(R.id.student_list);
        databaseHelper = new DatabaseHelper(this);
        studentList = databaseHelper.getAllStudents();
        checkBoxList = new ArrayList<>();

        populateStudentList();

        findViewById(R.id.submit_attendance_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordAttendance();
            }
        });
    }

    private void populateStudentList() {
        for (Student student : studentList) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(student.getFirstName() + " " + student.getLastName());
            checkBoxList.add(checkBox);
            studentListLayout.addView(checkBox);
        }
    }

    private void recordAttendance() {
        for (int i = 0; i < studentList.size(); i++) {
            if (checkBoxList.get(i).isChecked()) {
                databaseHelper.incrementAbsenceCount(studentList.get(i).getStudentId());
            }
        }
        Toast.makeText(this, "Attendance recorded successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
