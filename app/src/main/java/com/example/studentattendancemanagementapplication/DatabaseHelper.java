package com.example.studentattendancemanagementapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "attendance.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STUDENT = "Student";

    private static final String COLUMN_ID = "student_id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_ABSENCE_COUNT = "absence_count";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_STUDENT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_ABSENCE_COUNT + " INTEGER DEFAULT 0)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }

    public boolean addStudent(String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_ABSENCE_COUNT, 0);

        long result = db.insert(TABLE_STUDENT, null, values);
        return result != -1;
    }
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENT, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int studentId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
                @SuppressLint("Range") int absenceCount = cursor.getInt(cursor.getColumnIndex(COLUMN_ABSENCE_COUNT));

                Student student = new Student(studentId, firstName, lastName, absenceCount);
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return studentList;
    }

    public void incrementAbsenceCount(int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_STUDENT + " SET " + COLUMN_ABSENCE_COUNT + " = " + COLUMN_ABSENCE_COUNT + " + 1 WHERE " + COLUMN_ID + " = ?", new Object[]{studentId});
    }

    public List<Student> getAbsentStudents() {
        List<Student> absentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_ABSENCE_COUNT + " > 0", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int studentId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
                @SuppressLint("Range") int absenceCount = cursor.getInt(cursor.getColumnIndex(COLUMN_ABSENCE_COUNT));

                Student student = new Student(studentId, firstName, lastName, absenceCount);
                absentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return absentList;
    }
}
