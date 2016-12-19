package jangcho.dailydiary;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends Activity {

    TextView textview = null;
    public TimeDB mTimeDB = null;
    int curYear, curMonth, curDay, curHour, curMinute, curNoon, curSecond, curWeek;     //현재 년,월,일,시간,분,낮밤,초,요일
    int todayYear, todayMonth, todayDay ;    //오늘 년/달/일



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTimeDB = TimeDB.getInstance(this);


        Calendar oCalendar = Calendar.getInstance();
        curYear = oCalendar.get(Calendar.YEAR);
        curMonth = oCalendar.get(Calendar.MONTH) + 1;   //0이면 1월
        curDay = oCalendar.get(Calendar.DAY_OF_MONTH);
        curHour = oCalendar.get(Calendar.HOUR);
        curMinute = oCalendar.get(Calendar.MINUTE);
        curSecond = oCalendar.get(Calendar.SECOND);
        curNoon = oCalendar.get(Calendar.AM_PM); // 0이면 AM 1이면 PM
        curWeek = oCalendar.get(Calendar.DAY_OF_WEEK);  //1이면 일요일

        todayDay = curDay;
        todayMonth = curMonth;
        todayYear = curYear;

        ///현재달에 맞게 점 그리기//////////
        oCalendar.set(Calendar.YEAR,curYear);
        oCalendar.set(Calendar.MONTH,curMonth-1);
        oCalendar.set(Calendar.DATE,curDay);
    }
}
