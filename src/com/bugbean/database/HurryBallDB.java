package com.bugbean.database;

/**
 * @author zylyye
 */

public class HurryBallDB {
    public static final String DB_NAME = "HurryBall";

    public static class tableUsers {
        public static final String NAME = "users";
        public static final String FIELD_ID = "user_id";
        public static final String FIELD_USERNAME = "username";
        public static final String FIELD_PWD = "password";
    }

    public static class tableScores {
        public static final String NAME = "scores";
        public static final String FIELD_USERNAME = "username";
        public static final String FIELD_SCORE = "score";
    }

}