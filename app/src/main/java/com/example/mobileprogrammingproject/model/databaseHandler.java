package com.example.mobileprogrammingproject.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ThreadLocalRandom;

public class databaseHandler {

    private Connection connection;
    private PreparedStatement stmt;

    public databaseHandler() {
        try {
            boolean dbExists = true;
            if (!dbExists) {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:mobile.db");
                System.out.println("Opened database successfully");

                String sql = "CREATE TABLE Subject_Table (\n"
                        + "	Subject_Id integer PRIMARY KEY,\n"
                        + "	Subject_Name text NOT NULL,\n"
                        + " Subject_Code text NOT NULL,\n"
                        + " Subject_Status text NOT NULL\n"
                        + ");";
                stmt = connection.prepareStatement(sql);
                stmt.execute();

                sql = "CREATE TABLE Student_Table (\n"
                        + " Student_Number   INTEGER PRIMARY KEY,\n"
                        + " Student_Name   TEXT NOT NULL,\n"
                        + " Student_Grade  REAL NOT NULL,\n"
                        + " Student_Status TEXT NULL,\n"
                        + " Subject_Id INTEGER NOT NULL, \n"
                        + "    FOREIGN KEY (Subject_Id)\n"
                        + "    REFERENCES Subject_Table (Subject_Id) \n"
                        + ");";
                stmt = connection.prepareStatement(sql);
                stmt.execute();

                stmt.close();
                connection.close();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public databaseHandler(String lmao) {
    }

    //    private boolean checkDataBase() {
//    SQLiteDatabase checkDB = null;
//    try {
//        checkDB = SQLiteDatabase.openDatabase(DB_FULL_PATH, null,
//                SQLiteDatabase.OPEN_READONLY);
//        checkDB.close();
//    } catch (SQLiteException e) {
//        // database doesn't exist yet.
//    }
//    //where DB_FULL_PATH is the path to your database file.
//    return checkDB != null;
//}
    public ResultSet selectStudentsUnderSubject(int Subject_Id) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mobile.db");
            connection.setAutoCommit(false);

            ResultSet rs = null;
            String sqlStmt = "SELECT Student_Table.Student_Number, Student_Table.Student_Grade, Subject_Table.Subject_Name, \n"
                    + "Student_Table.Student_Status, Student_Table.Student_Name\n"
                    + "FROM Student_Table \n"
                    + "JOIN Subject_Table ON Student_Table.Subject_Id = Subject_Table.Subject_Id WHERE Student_Table.Subject_Id = ?;";
            stmt = connection.prepareStatement(sqlStmt);
            stmt.setInt(1, Subject_Id);
            rs = stmt.executeQuery();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet selectSubjects() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mobile.db");
            connection.setAutoCommit(false);


            ResultSet rs = null;
            String sqlStmt = "SELECT Subject_Id, Subject_Name FROM Subject_Table;";
            stmt = connection.prepareStatement(sqlStmt);
            rs = stmt.executeQuery();

            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteSubject(int subjectId) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mobile.db");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            String sqlStmt = "DELETE FROM Student_Table where Subject_Id=?";
            stmt = connection.prepareStatement(sqlStmt);
            stmt.setInt(1, subjectId);
            stmt.executeUpdate(sqlStmt);

            sqlStmt = "DELETE FROM Subject_Table where Subject_Id=?";
            stmt = connection.prepareStatement(sqlStmt);
            stmt.setInt(1, subjectId);
            stmt.executeUpdate(sqlStmt);

            stmt.close();
            connection.commit();
            connection.close();
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public boolean unEnrollStudent(int studentNumber) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mobile.db");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            String sqlStmt = "DELETE FROM Student_Table where Student_Number=?";
            stmt = connection.prepareStatement(sqlStmt);
            stmt.setInt(1, studentNumber);
            stmt.executeUpdate(sqlStmt);

            stmt.close();
            connection.commit();
            connection.close();
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public boolean enrollStudent(int subjectId, String studentName, float studentGrade, String studentStatus) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mobile.db");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            int randomId = ThreadLocalRandom.current().nextInt(0, 100000 + 1);

            String sqlStmt = "INSERT INTO Student_Table (Student_Number, Student_Name, Student_Grade, Student_Status, Subject_Id)\n"
                    + "VALUES (?, ?, ?, ?, ?);";
            stmt = connection.prepareStatement(sqlStmt);

            stmt.setInt(1, randomId);
            stmt.setString(2, studentName);
            stmt.setFloat(3, studentGrade);
            stmt.setString(4, studentStatus);
            stmt.setInt(5, randomId);

            stmt.executeUpdate(sqlStmt);
            stmt.close();
            connection.commit();
            connection.close();
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public boolean insertSubject(String SubjectName, String SubjectCode, String SubjectStatus) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mobile.db");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            int randomId = ThreadLocalRandom.current().nextInt(0, 100000 + 1);

            String sqlStmt = "INSERT INTO Subject_Table (Subject_Id,Subject_Name, Subject_Code, Subject_Status)\n"
                    + "VALUES (?, ?, ?, ?);";
            stmt = connection.prepareStatement(sqlStmt);

            stmt.setInt(1, randomId);
            stmt.setString(2, SubjectName);
            stmt.setString(3, SubjectCode);
            stmt.setString(4, SubjectStatus);

            stmt.executeUpdate(sqlStmt);
            stmt.close();
            connection.commit();
            connection.close();
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public boolean updateSubject(int subjectId, String SubjectName, String SubjectCode, String SubjectStatus) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mobile.db");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            String sqlStmt = "UPDATE Subject_Table SET Subject_Name = ?, Subject_Code = ?, Subject_Status = ?\n"
                    + "WHERE Subject_Id = ?;";
            stmt = connection.prepareStatement(sqlStmt);

            stmt.setString(1, SubjectName);
            stmt.setString(2, SubjectCode);
            stmt.setString(3, SubjectStatus);
            stmt.setInt(4, subjectId);

            stmt.executeUpdate(sqlStmt);
            stmt.close();
            connection.commit();
            connection.close();
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public boolean updateStudent(int subjectId, int studentNumber, String studentName, float studentGrade, String studentStatus) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mobile.db");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            String sqlStmt = "UPDATE Subject_Table Set Student_Name = ?, Student_Grade = ?, Subject_Status = ?\n"
                    + "WHERE Subject_Id = ? AND Student_Number = ?;";
            stmt = connection.prepareStatement(sqlStmt);

            stmt.setString(1, studentName);
            stmt.setFloat(2, studentGrade);
            stmt.setString(3, studentStatus);
            stmt.setInt(4, subjectId);
            stmt.setInt(5, studentNumber);

            stmt.executeUpdate(sqlStmt);
            stmt.close();
            connection.commit();
            connection.close();
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }
}

