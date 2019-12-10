package com.example.quiz.model;

import android.provider.BaseColumns;

public final class QuestionContract {
    // container for different constraints that will be needed for sqlite operation
    public static class QuestionTable implements BaseColumns {
        public static final String TABLE_NAME = "question_Quiz";
        public static final String COLUMN_Ques = "question_Quiz";
        public static final String COLUMN_OPT1 = "opt1";
        public static final String COLUMN_OPT2 = "opt2";
        public static final String COLUMN_OPT3 = "opt3";
        public static final String COLUMN_OPT4 = "opt4";
        public static final String COLUMN_ANS  = "ans";

    }
    // Base columns is an interface c+b
    // this class simply consists of questions and  options that can be modified easily in the future


}
