package jangcho.dailydiary;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016-12-10.
 */

public class TimeDB {

    static final String DB_TIME = "DbTime.db";
    static final String TABLE_TIME = "Time";
    static final int DB_VERSION = 1;

    Context mContext = null;
    private static TimeDB mTimeDB = null;
    private SQLiteDatabase mDatabase = null;

    public static TimeDB getInstance(Context context){
        if(mTimeDB == null){
            mTimeDB = new TimeDB(context);
        }
        return mTimeDB;
    }

    private TimeDB(Context context){
        mContext = context;

        mDatabase = context.openOrCreateDatabase(DB_TIME,Context.MODE_PRIVATE,null);

        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TIME + "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "year        INTEGER,"+
                "month        INTEGER,"+
                "day        INTEGER,"+
                "week        INTEGER,"+
                "weather    INTEGER,"+
                "Content        TEXT);"
        );      //DB는 id,year,month,day,week,content 값을 저장한다.


    }

    public long insert(ContentValues addRowValue){

        return mDatabase.insert(TABLE_TIME,null,addRowValue);
    }

    public Cursor query(String[] columns,
                        String selection,
                        String[] selectionArgs,
                        String groupBy,
                        String having,
                        String orderBy){

        return mDatabase.query(TABLE_TIME,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);

    } //Select 문과 같다. columns : 검색결과로 얻게될 컬럼명 , selection : 검색조건항 설정(where 과 같다.),
    //selectionArgs : 만약 selection이 "year=?" "day=?"라면 selectionArgs에{"2016","5"}를 넣으면 year=2016, day=5가 대입된다.
    //groupBy : 같은 같이 들어간 컬럼들을 하나로 그룹핑 (알고있는 GroupBy랑 같다.)
    //having : groupBy의 인자 조건
    //orderBy : 정렬

    public int update(ContentValues updateRowValue,
                      String whereClause,
                      String[] whereArgs){
        return mDatabase.update(TABLE_TIME,updateRowValue,whereClause,whereArgs);

    }

    public int delete(String whereClause,String[] whereArgs){

        return mDatabase.delete(TABLE_TIME,whereClause,whereArgs);
    }

}
