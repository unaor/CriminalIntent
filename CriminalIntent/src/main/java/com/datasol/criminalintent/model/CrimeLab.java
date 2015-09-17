package com.datasol.criminalintent.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.datasol.criminalintent.database.CrimeBaseHelper;
import com.datasol.criminalintent.database.CrimeDbSchema;

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
        return new ArrayList<>();
    }

    public Crime getCrime(UUID id){

        return null;
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
                CrimeDbSchema.CrimeTable.Cols.UUID + " =?",
                new String[]{uuidString});
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeDbSchema.CrimeTable.Cols.UUID , crime.getId().toString());
        values.put(CrimeDbSchema.CrimeTable.Cols.TITLE , crime.getTitle());
        values.put(CrimeDbSchema.CrimeTable.Cols.DATE , crime.getDate().getTime());
        values.put(CrimeDbSchema.CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        return values;
    }

    private Cursor queryCrimes(String whereClause, String [] whereArgs){
        Cursor cursor = mDataBase.query(
                CrimeDbSchema.CrimeTable.NAME,
                null, //null for selecting all columns
                whereClause,
                whereArgs,null,
                null,
                null
        );
        return cursor;
    }
}
