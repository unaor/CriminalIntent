package com.datasol.criminalintent.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.datasol.criminalintent.database.CrimeBaseHelper;
import com.datasol.criminalintent.database.CrimeCursorWrapper;
import com.datasol.criminalintent.database.CrimeDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by unaor on 9/7/2015.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mcontext;
    private SQLiteDatabase mDataBase;


    public static CrimeLab get(Context context) {

        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mcontext = context.getApplicationContext();
        mDataBase = new CrimeBaseHelper(mcontext).getWritableDatabase();

    }

    public List<Crime> getCrimes(){
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id){

        CrimeCursorWrapper cursor = queryCrimes(CrimeDbSchema.CrimeTable.Cols.UUID + "= ?" ,
                new String [] {id.toString()});
        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }

    public void addCrime(Crime crime){
        ContentValues values = getContentValues(crime);
        mDataBase.insert(CrimeDbSchema.CrimeTable.NAME,null,values);
    }

    public void deleteCrime(Crime crimeToDelete){
        String uuidString = crimeToDelete.getId().toString();
        mDataBase.delete(CrimeDbSchema.CrimeTable.NAME,
                CrimeDbSchema.CrimeTable.Cols.UUID + " =?",
                new String[]{uuidString});
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDataBase.update(CrimeDbSchema.CrimeTable.NAME, values,
                CrimeDbSchema.CrimeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeDbSchema.CrimeTable.Cols.UUID , crime.getId().toString());
        values.put(CrimeDbSchema.CrimeTable.Cols.TITLE , crime.getTitle());
        values.put(CrimeDbSchema.CrimeTable.Cols.DATE , crime.getDate().getTime());
        values.put(CrimeDbSchema.CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeDbSchema.CrimeTable.Cols.SUSPECT, crime.getSuspect());
        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String [] whereArgs){
        Cursor cursor = mDataBase.query(
                CrimeDbSchema.CrimeTable.NAME,
                null, //null for selecting all columns
                whereClause,
                whereArgs,null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }

    public File getPhotoFile(Crime crime){
        File externalFilesDir = mcontext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null){
            return null;
        }

        return new File(externalFilesDir,crime.getPhotoFileName());
    }

}
