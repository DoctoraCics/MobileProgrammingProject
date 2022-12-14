package com.example.mobileprogrammingproject.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import java.util.ArrayList;
import java.util.Random;

public class androidDBHandlerSqlLite extends SQLiteOpenHelper {

    private static final String DB_NAME = "teacherDB";
    private static final int DB_VERSION = 1;
    private static String dbPath;

    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 100000000;
    private static final int MAX_TRIES = 5;

    //Under Subject table
    private static final String SUBJECT_TABLE = "Subject_Table";
    private static final String SUBJECT_ID = "Subject_Id";
    private static final String SUBJECT_NAME = "Subject_Name";
    private static final String SUBJECT_CODE = "Subject_Code";
    private static final String SUBJECT_STATUS = "Subject_Status";

    //Under Student Table
    private static final String STUDENT_TABLE = "Student_Table";
    private static final String STUDENT_NUMBER = "Student_Number";
    private static final String STUDENT_NAME = "Student_Name";
    private static final String STUDENT_GRADE = "Student_Grade";
    private static final String STUDENT_STATUS = "Student_Status";
    private static final String STUDENT_SUBJECT_ID = "Student_Subject_Id";

    public androidDBHandlerSqlLite(Context context) { super(context, DB_NAME, null, DB_VERSION); }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.dbPath = sqLiteDatabase.getPath();
            String CREATE_TABLE = "CREATE TABLE " + SUBJECT_TABLE +
                    "(" +
                    SUBJECT_ID + " INTEGER PRIMARY KEY," +
                    SUBJECT_NAME + " TEXT NOT NULL," +
                    SUBJECT_CODE + " TEXT NOT NULL," +
                    SUBJECT_STATUS + " TEXT" +
                    ")";

            sqLiteDatabase.execSQL(CREATE_TABLE);
        CREATE_TABLE = "CREATE TABLE "  + STUDENT_TABLE + " ("
                    + STUDENT_NUMBER + " INTEGER PRIMARY KEY,"
                    + STUDENT_NAME + " TEXT NOT NULL,"
                    + STUDENT_GRADE + " REAL,"
                    + STUDENT_STATUS + " TEXT,"
                    + STUDENT_SUBJECT_ID +" INTEGER REFERENCES " + SUBJECT_TABLE + ")";
        System.out.println(CREATE_TABLE);
            sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onConfigure(SQLiteDatabase sqLiteDatabase) {
        super.onConfigure(sqLiteDatabase);
        sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
    }

    public ArrayList<Student> selectStudentsUnderSubject(int Subject_Id) {

        try {
            ArrayList<Student> studentWithSubject = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();

            String unformattedsqlStmt = "SELECT Student_Table.Student_Number, Student_Table.Student_Grade, Subject_Table.Subject_Name, Student_Table.Student_Status, Student_Table.Student_Name FROM Student_Table "
                    + "JOIN Subject_Table ON Student_Table.Student_Subject_Id = Subject_Table.Subject_Id WHERE Student_Table.Student_Subject_Id = %d;";

            String sqlStmt = String.format(unformattedsqlStmt, Subject_Id);

            System.out.println(sqlStmt);

            Cursor cursor = db.rawQuery(sqlStmt, null);
            while(cursor.moveToNext()){
                int StudentNum = cursor.getInt(0);
                double StudentGrade = cursor.getDouble(1);
                String SubjectName = cursor.getString(2);
                String StudentStatus = cursor.getString(3);
                String StudentName = cursor.getString(4);
                Student newStudent = new Student(StudentNum,StudentGrade,SubjectName,StudentStatus,StudentName);
                studentWithSubject.add(newStudent);
            }
            cursor.close();
            return studentWithSubject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Subject> selectSubjects() {

        try {
            ArrayList<Subject>subjectList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String sqlStmt = "SELECT * FROM Subject_Table;";
            Cursor cursor = db.rawQuery(sqlStmt, null);
            while (cursor.moveToNext()){
                subjectList.add(new Subject(cursor.getInt(0), cursor.getString(1)
                ,cursor.getString(2),cursor.getString(3)));
            }
            cursor.close();
            return subjectList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteSubject(int subjectId) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String sqlStmt = "DELETE FROM Student_Table where Student_Subject_Id=?";
            SQLiteStatement stmt = db.compileStatement(sqlStmt);
            stmt.bindLong(1, subjectId);
            stmt.executeUpdateDelete();

            sqlStmt = "DELETE FROM Subject_Table where Subject_Id=?";
            stmt = db.compileStatement(sqlStmt);
            stmt.bindLong(1, subjectId);
            stmt.executeUpdateDelete();
            stmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unEnrollStudent(int studentNumber) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String sqlStmt = "DELETE FROM Student_Table where Student_Number=?";
            SQLiteStatement stmt = db.compileStatement(sqlStmt);
            stmt.bindLong(1, studentNumber);
            stmt.executeUpdateDelete();
            stmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean enrollStudent(int subjectId, String studentName, float studentGrade, String studentStatus) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            int randomId = generateRandomId(MIN_NUMBER, MAX_NUMBER);

            String sqlStmt = "INSERT INTO Student_Table (Student_Number, Student_Name, Student_Grade, Student_Status, Student_Subject_Id)\n"
                    + " VALUES (?, ?, ?, ?, ?);";
            SQLiteStatement stmt = db.compileStatement(sqlStmt);

            System.out.println(sqlStmt);

            stmt.bindLong(1, randomId);
            stmt.bindString(2, studentName);
            stmt.bindDouble(3, studentGrade);
            stmt.bindString(4, studentStatus);
            stmt.bindLong(5, subjectId);
            stmt.executeInsert();
            stmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertSubject(String SubjectName, String SubjectCode, String SubjectStatus) {
        boolean inserted = false;
        int tries = 0;

        while(!inserted && !(tries > MAX_TRIES)){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            int randomId = generateRandomId(0, 100000);

            String sqlStmt = "INSERT INTO Subject_Table (" + SUBJECT_ID + ","+ SUBJECT_NAME +"," + SUBJECT_CODE +"," + SUBJECT_STATUS + ")"
                    + "VALUES (?, ?, ?, ?);";

            SQLiteStatement stmt = db.compileStatement(sqlStmt);
            stmt.bindLong(1, randomId);
            stmt.bindString(2, SubjectName);
            stmt.bindString(3, SubjectCode);
            stmt.bindString(4, SubjectStatus);

            stmt.executeInsert();
            stmt.close();
            inserted = true;
        } catch (Exception e) {
            e.printStackTrace();
            tries++;
        }
        }
        return inserted;
    }

    public boolean updateSubject(int subjectId, String SubjectName, String SubjectCode, String SubjectStatus) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String sqlStmt = "UPDATE Subject_Table SET Subject_Name = ?, Subject_Code = ?, Subject_Status = ?\n"
                    + "WHERE Subject_Id = ?;";
            SQLiteStatement stmt = db.compileStatement(sqlStmt);

            stmt.bindString(1, SubjectName);
            stmt.bindString(2, SubjectCode);
            stmt.bindString(3, SubjectStatus);
            stmt.bindLong(4, subjectId);

            stmt.executeUpdateDelete();
            stmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStudent(int subjectId, int studentNumber, String studentName, float studentGrade, String studentStatus) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String sqlStmt = "UPDATE Student_Table Set Student_Name = ?, Student_Grade = ?, Student_Status = ? "
                    + "WHERE Student_Subject_Id = ? AND Student_Number = ?;";

            SQLiteStatement stmt = db.compileStatement(sqlStmt);

            stmt.bindString(1, studentName);
            stmt.bindDouble(2, studentGrade);
            stmt.bindString(3, studentStatus);
            stmt.bindLong(4, subjectId);
            stmt.bindLong(5, studentNumber);

            stmt.executeUpdateDelete();
            stmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int generateRandomId(int min, int max){
        int random = new Random().nextInt((max - min) + 1) + min;
        return random;
    }
}

