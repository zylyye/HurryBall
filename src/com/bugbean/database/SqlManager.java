package com.bugbean.database;

/**
 * @author zylyye
 */

public class SqlManager {
    private static ConnectionDB sConnectionDB;
    private SqlManager(){}

    public static ConnectionDB getConnectionDB() {
        if (sConnectionDB == null) {
            sConnectionDB = new ConnectionDB(HurryBallDB.DB_NAME);
        }
        return sConnectionDB;
    }
}
