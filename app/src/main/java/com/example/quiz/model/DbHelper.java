package com.example.quiz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quiz.model.QuestionContract.*;  // * denotes importhing every thing from question contract class
// by importing the Questioncontract class it will also allow us to minimize the effort to write question contract eachtime in queries

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String dbName = "QuizApplication.db";
    private static final int dbVersion = 1;

    private SQLiteDatabase db;

    public DbHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String create_table_question_query = "CREATE TABLE " + QuestionTable.TABLE_NAME
                + " ( " + QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_Ques + " TEXT, " +
                QuestionTable.COLUMN_OPT1 + " TEXT, " +
                QuestionTable.COLUMN_OPT2 + " TEXT, " +
                QuestionTable.COLUMN_OPT3 + " TEXT, " +
                QuestionTable.COLUMN_OPT4 + " TEXT, " +
                QuestionTable.COLUMN_ANS + " TEXT " + ")";

            db.execSQL(create_table_question_query);
            fillQuestions(); // to fill up the question table when the database is created for the first time
     }
    // _id is directly accessible from base column

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // to update the database table we need to write code here
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);
    }
    // update will be done by increasing database version by 1

    private void fillQuestions(){
        Question ques1 = new Question("Which months have 28 days?", "February", "March", "None", "All of the months", "All of the months");
        addQuestion(ques1);  // passing the question to add question method
        Question ques2 = new Question("How many 0.5cm slices of bread can you cut from a whole bread that's 25cm long? ", "1", "25", "39", "None of above", "1");
        addQuestion(ques2);
        Question ques3 = new Question("If a leaf falls to the ground in a forest and no one hears it, does it make a sound? ", "Yes", "No", "Depends upon how heavy the leaf was", "Depends on where it landed", "Yes");
        addQuestion(ques3);
        Question ques4 = new Question("The answer is really big. ", "Really Big", "An elephant", "THE ANSWER", "The data given is insufficient", "THE ANSWER");
        addQuestion(ques4);
        Question ques5 = new Question("There are 45 apples in your basket. You take three apples out of the basket. How many apples are left?", "3", "42", "45", "I don't like apples", "45");
        addQuestion(ques5);

    }

    // adding questions
    private void addQuestion(Question question){
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionTable.COLUMN_Ques, question.getQues());
        contentValues.put(QuestionTable.COLUMN_OPT1, question.getOpt1());
        contentValues.put(QuestionTable.COLUMN_OPT2, question.getOpt2());
        contentValues.put(QuestionTable.COLUMN_OPT3, question.getOpt3());
        contentValues.put(QuestionTable.COLUMN_OPT4, question.getOpt4());
        contentValues.put(QuestionTable.COLUMN_ANS, question.getCorrectAns());
        db.insert(QuestionTable.TABLE_NAME, null, contentValues); // for inserting the value in the database

    }

    // retrieving questions from the database
    public List<Question> retriveQuestions(){
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        // cursor is used here to query out the database and retrieve the question using select query
        Cursor cursor =  db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME, null);

        if(cursor.moveToFirst()){ // moves the cursor to the first entry
            do{
                Question question = new Question();
                question.setQues(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_Ques)));
                question.setOpt1(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPT1)));
                question.setOpt2(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPT2)));
                question.setOpt3(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPT3)));
                question.setOpt4(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPT4)));
                question.setCorrectAns(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_ANS)));
                questionList.add(question);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }



}
