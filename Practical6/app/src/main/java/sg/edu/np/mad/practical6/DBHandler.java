package sg.edu.np.mad.practical6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class DBHandler extends SQLiteOpenHelper {
    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "users.db";
    public static String USERS = "Users";
    public static String COLUMN_NAME = "name";
    public static String COLUMN_DESC = "description";
    public static String COLUMN_ID = "id";
    public static String COLUMN_FOLLOW = "followed";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_USERS_TABLE = "CREATE TABLE " + USERS + "(" + COLUMN_NAME + " TEXT,"
                + COLUMN_DESC + " TEXT,"+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +COLUMN_FOLLOW+" INTEGER"+")";
        db.execSQL(CREATE_USERS_TABLE);

        // Generate 20 users
        for (int i = 1; i <= 20; i++) {
            String nameRandom = String.valueOf((int) Math.round(Math.random() * 1000000));
            String descRandom = String.valueOf((int) Math.round(Math.random() * 100000000));

            String name = "Name" + nameRandom;
            String desc = "Description " + descRandom;
            String id = String.valueOf(i);
            String followed = String.valueOf(new Random().nextInt(2));


            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_DESC, desc);
            values.put(COLUMN_ID, id);
            values.put(COLUMN_FOLLOW, followed);
            db.insert(USERS, null, values);
        }
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        onCreate(db);
    }

    // READ
    public ArrayList<User> getUser() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM " + USERS;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString((int)cursor.getColumnIndex(COLUMN_NAME));
            String description = cursor.getString((int)cursor.getColumnIndex("description"));
            int id = cursor.getInt((int)cursor.getColumnIndex("id"));
            boolean follow = Boolean.parseBoolean(cursor.getString((int)cursor.getColumnIndex("followed")));

            User user = new User(name, description, id, follow);
            userList.add(user);
        }

        cursor.close();
        db.close();
        return userList;
    }


    //UPDATE
    public void updateUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.name);
        values.put(COLUMN_DESC, user.description);
        values.put(COLUMN_ID, user.id);
        values.put(COLUMN_FOLLOW, user.followed);
        String clause = COLUMN_ID + "=?";
        String[] args = {String.valueOf(user.id)};
        db.update(USERS, values, clause, args);
        db.close();
    }
}