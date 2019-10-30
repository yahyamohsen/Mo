package eg.com.misrins.mic.micproject;

/**
 * Created by MIC on 26/04/2017.
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.android.gms.maps.model.LatLng;

public class DataBaseHelperClass extends SQLiteOpenHelper{

    //The Android's default system path of your application database.
    private static String DB_PATH = "";

    private static String DB_NAME = "DBMIC1.11.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelperClass(Context context) {

        super(context, DB_NAME, null, 4);// version
        this.myContext = context;
      //  DB_PATH= myContext.getDatabasePath(DB_NAME).toString(); // original
        DB_PATH = this.myContext.getDatabasePath(DB_NAME).getAbsolutePath();


    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
            //   System.out.println("Database Exist ==========");
        }else{
            // System.out.println("Database doesn't Exist ==========");
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getWritableDatabase();

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
        //  this.getReadableDatabase();

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH ;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
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
        String outFileName = DB_PATH ;

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

    public void openDataBase() throws SQLException{

        //Open the database
        String myPath = DB_PATH ;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }/**/

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //db.execSQL("DROP TABLE IF EXISTS Data");
        }
    }



    public ArrayList<DTO> getDataListType( Integer type_id,Integer gov_id)
    {

        DataBaseHelperClass db =new DataBaseHelperClass(myContext);
        try {

            db.createDataBase();
            db.openDataBase();
            // System.out.println("CONNECTED SUCCESSFULLY...!");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        SQLiteDatabase sd = db.getReadableDatabase();
        String query ="";
        if(gov_id == -1){
            query = "select * From Data where  type_id =" + type_id;
        }else {
            query = "select * From Data where  type_id = " + type_id + " and gov_id =" + gov_id;
        }
        Cursor cursor = sd.rawQuery(query, null);

        ArrayList<DTO> list = new ArrayList<DTO>();

        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                do{

                    DTO b = new DTO();
                    if(cursor.getString(0)!=null)
                        b.setGov_id(Integer.parseInt(cursor.getString(0)));
                    if(cursor.getString(1)!=null)
                        b.setType_id(Integer.parseInt(cursor.getString(1)));
                    // b.setImage(R.drawable.img);
                    b.setName(cursor.getString(2));
                    b.setDegree(cursor.getString(3));
                    b.setAddress(cursor.getString(4));
                    b.setPhone(cursor.getString(5));
                    if(cursor.getString(6)!=null) {
                        b.setGoogle_cd(cursor.getString(6));
                        String [] location1 = b.getGoogle_cd().split(",");
                        try {
                            b.setLocation(new LatLng(Double.parseDouble(location1[0]), Double.parseDouble(location1[1])));
                        }catch (Exception e){

                        }
                    }
                    list.add(b);

                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        this.close();
        return list;
    }

    public  DTO  getDetails( String name,String address)
    {

        DataBaseHelperClass db =new DataBaseHelperClass(myContext);
        try {

            db.createDataBase();
            db.openDataBase();
            // System.out.println("CONNECTED SUCCESSFULLY...!");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        SQLiteDatabase sd = db.getReadableDatabase();
        String query = "select * From Data where  name = '"+name +"' and address ='"+address+"'";
        Cursor cursor = sd.rawQuery(query, null);
        //System.out.println("query : "+query);
        DTO b = new DTO();
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                do{


                    if(cursor.getString(0)!=null)
                        b.setGov_id(Integer.parseInt(cursor.getString(0)));
                    if(cursor.getString(1)!=null)
                        b.setType_id(Integer.parseInt(cursor.getString(1)));
                    // b.setImage(R.drawable.img);
                    b.setName(cursor.getString(2));
                    b.setDegree(cursor.getString(3));
                    b.setAddress(cursor.getString(4));
                    b.setPhone(cursor.getString(5));
                    //b.setGoogle_cd(cursor.getString(6));
                    if(cursor.getString(6)!=null) {
                        b.setGoogle_cd(cursor.getString(6));
                        String [] location1 = b.getGoogle_cd().split(",");
                        b.setLocation( new LatLng(Double.parseDouble(location1[0]),Double.parseDouble(location1[1])));
                    }


                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return b;
    }

    public Integer getTypeID( String typename)
    {

        DataBaseHelperClass db =new DataBaseHelperClass(myContext);
        try {

            db.createDataBase();
            db.openDataBase();
            // System.out.println("CONNECTED SUCCESSFULLY...!");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        SQLiteDatabase sd = db.getReadableDatabase();
        String query = "select * From Type where  type_name like '"+typename+"'";
        Cursor cursor = sd.rawQuery(query, null);

        //System.out.println("******//////: "+query);
        Integer i=0;
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                do{
                    i= cursor.getInt(0);
                }while (cursor.moveToNext());


            }
        }
        cursor.close();
        db.close();
        //System.out.println("******//////: "+i);
        return i;
    }

    public Integer getGovID( String govname)
    {

        DataBaseHelperClass db =new DataBaseHelperClass(myContext);
        try {

            db.createDataBase();
            db.openDataBase();
            // System.out.println("CONNECTED SUCCESSFULLY...!");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        SQLiteDatabase sd = db.getReadableDatabase();
        String query = "select * From Governorate where  gov_name like '"+govname+"'";
        Cursor cursor = sd.rawQuery(query, null);

        Integer id ;

        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                return cursor.getInt(0);
            }
        }
        cursor.close();
        db.close();
        return null;
    }

    public ArrayList<DTO> fetchData()
    {

        DataBaseHelperClass db =new DataBaseHelperClass(myContext);
        try {

            db.createDataBase();
            db.openDataBase();


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        SQLiteDatabase sd = db.getReadableDatabase();
        String query = "select * From Data";
        Cursor cursor = sd.rawQuery(query, null);
        String userName = null;
        ArrayList<DTO> list = new ArrayList<DTO>();

        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                do{

                    DTO b = new DTO();
                    if(cursor.getString(0)!=null)
                        b.setGov_id(Integer.parseInt(cursor.getString(0)));
                    if(cursor.getString(1)!=null)
                        b.setType_id(Integer.parseInt(cursor.getString(1)));
                    // b.setImage(R.drawable.img);
                    b.setName(cursor.getString(2));
                    b.setDegree(cursor.getString(3));
                    b.setAddress(cursor.getString(4));
                    b.setPhone(cursor.getString(5));
                    b.setGoogle_cd(cursor.getString(6));
                    list.add(b);

                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<String> getGovernorate( )
    {

        DataBaseHelperClass db =new DataBaseHelperClass(myContext);
        try {

            db.createDataBase();
            db.openDataBase();
            // System.out.println("CONNECTED SUCCESSFULLY...!");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        SQLiteDatabase sd = db.getReadableDatabase();
        String query = "select * From Governorate";
        Cursor cursor = sd.rawQuery(query, null);



        String userName = null;
        ArrayList<String> list = new ArrayList<String>();

        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                do{
                    list.add(cursor.getString(1));

                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return list;
    }
    public ArrayList<String> getType( )
    {

        DataBaseHelperClass db =new DataBaseHelperClass(myContext);
        try {

            db.createDataBase();
            db.openDataBase();
            //   System.out.println("CONNECTED SUCCESSFULLY...!");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        SQLiteDatabase sd = db.getReadableDatabase();
        String query = "select * From Governorate";
        Cursor cursor = sd.rawQuery(query, null);
        String userName = null;
        ArrayList<String> list = new ArrayList<String>();

        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                do{
                    list.add(cursor.getString(1));

                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<DTO> getSubListType( Integer type_id,Integer gov_id)
    {

        DataBaseHelperClass db =new DataBaseHelperClass(myContext);
        try {

            db.createDataBase();
            db.openDataBase();
            // System.out.println("CONNECTED SUCCESSFULLY...!");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        SQLiteDatabase sd = db.getReadableDatabase();
        String query="";


        if (type_id == 1)
            query = "select * From Data where  type_id in (11,12,13,14,15,16,17,18,19,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124) and gov_id =" + gov_id;
        else if (type_id == 8)
            query = "select * From Data where  type_id in(81,82,83,84) and gov_id =" + gov_id;



        Cursor cursor = sd.rawQuery(query, null);

        ArrayList<DTO> list = new ArrayList<DTO>();

        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                do{

                    DTO b = new DTO();
                    if(cursor.getString(0)!=null)
                        b.setGov_id(Integer.parseInt(cursor.getString(0)));
                    if(cursor.getString(1)!=null)
                        b.setType_id(Integer.parseInt(cursor.getString(1)));
                    // b.setImage(R.drawable.img);
                    b.setName(cursor.getString(2));
                    b.setDegree(cursor.getString(3));
                    b.setAddress(cursor.getString(4));
                    b.setPhone(cursor.getString(5));
                    b.setGoogle_cd(cursor.getString(6));
                    list.add(b);

                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
       /* System.out.println("govid:"+gov_id);
        System.out.println("typeID:"+type_id);
        System.out.println("List size:"+list.size());*/
        return list;
    }

    public ArrayList<DTO> getsublistCombo(Integer parent)
    {

        DataBaseHelperClass db =new DataBaseHelperClass(myContext);
        try {

            db.createDataBase();
            db.openDataBase();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        SQLiteDatabase sd = db.getReadableDatabase();
        String query = "select * From Type from parent ="+parent;
        Cursor cursor = sd.rawQuery(query, null);

        ArrayList<DTO> list = new ArrayList<DTO>();

        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                do{

                    DTO b = new DTO();

                    if(cursor.getString(0)!=null)
                        b.setType_id(Integer.parseInt(cursor.getString(0)));
                    // b.setImage(R.drawable.img);
                    b.setName(cursor.getString(2));

                    list.add(b);

                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();

        return list;
    }
}