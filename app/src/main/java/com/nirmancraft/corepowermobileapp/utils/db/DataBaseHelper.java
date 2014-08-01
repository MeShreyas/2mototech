package com.nirmancraft.corepowermobileapp.utils.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.nirmancraft.corepowermobileapp.objects.DBRecord;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shreyas on 7/6/2014.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH;

    private static String DB_NAME = "CPDB.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
        this.DB_PATH = "/data/data/com.nirmancraft.corepowermobileapp/databases/";
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist

        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + File.separator+ DB_NAME;
        try {
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

    public List<DBRecord> getAllRecords(){
        List<DBRecord> recList = new ArrayList<DBRecord>();
        SQLiteDatabase  db = this.getReadableDatabase();
        String query = "Select _id,title,body,image from DataTable order by title";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                DBRecord dbRec = new DBRecord();
                dbRec.set_id(Integer.parseInt(cursor.getString(0)));
                dbRec.setTitle(cursor.getString(1));
                dbRec.setBody(cursor.getString(2));
                dbRec.setImage(cursor.getString(3));
                recList.add(dbRec);

            } while (cursor.moveToNext());
        }
        return recList;
    }

    public DBRecord getRecordById(int id) {
        SQLiteDatabase  db = this.getReadableDatabase();
        String query = "Select _id,title,body,image from DataTable where _id="+id;
        Cursor cursor = db.rawQuery(query,null);
        DBRecord dbRec = new DBRecord();
        if (cursor.moveToFirst()) {
            do {
                dbRec.set_id(Integer.parseInt(cursor.getString(0)));
                dbRec.setTitle(cursor.getString(1));
                dbRec.setBody(cursor.getString(2));
                dbRec.setImage(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        return dbRec;
    }

    public List<DBRecord> searchRecords(String queryParam) {
        List<DBRecord> recList = new ArrayList<DBRecord>();
        SQLiteDatabase  db = this.getReadableDatabase();
        String query = "Select _id,title,body,image from DataTable where title like (%"+queryParam+"%) or body like (%"+queryParam+"%)";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                DBRecord dbRec = new DBRecord();
                dbRec.set_id(Integer.parseInt(cursor.getString(0)));
                dbRec.setTitle(cursor.getString(1));
                dbRec.setBody(cursor.getString(2));
                dbRec.setImage(cursor.getString(3));
                recList.add(dbRec);

            } while (cursor.moveToNext());
        }
        return recList;
    }

    public boolean checkIfLicensed(String token) {
        SQLiteDatabase  db = this.getReadableDatabase();
        String query = "Select count(1) from License where token=\""+token+"\"";
        Cursor cursor = db.rawQuery(query,null);
        int count = -1;
        if (cursor.moveToFirst()) {
            do {
                count = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return count>=1?true:false;
    }

    public void saveLicense(String license) {
        SQLiteDatabase  db = this.getReadableDatabase();
        db.execSQL("INSERT into License (token) values(\""+license+"\")");
    }
}
