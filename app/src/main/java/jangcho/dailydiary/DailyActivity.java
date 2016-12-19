package jangcho.dailydiary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Calendar;

/**
 * Created by Administrator on 2016-12-10.
 */

public class DailyActivity extends Activity {

    EditText editdaily = null;
    public TimeDB mTimeDB = null;
    Intent intent=null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_activity_layout);

        mTimeDB = TimeDB.getInstance(this);

        TextView daily= (TextView)findViewById(R.id.daily);
        editdaily = (EditText)findViewById(R.id.daily_edit);
        String mmonth ="";
        String mweek ="";

        intent = getIntent();



        ////////////DB 존재하면 내용을 append
        String[] columns = new String[]{"content"};
        String[] temp = {""+intent.getIntExtra("year",0),""+intent.getIntExtra("month",0),""+intent.getIntExtra("day",0)};
        Cursor c = mTimeDB.query(columns ,"year = ? AND month =? AND day = ? ",temp,null,null,null);
        if(c != null && c.getCount()!=0){

            c.moveToFirst();
            editdaily.setText(c.getString(0));
            editdaily.setSelection(editdaily.length());
        }
        /////////////////////////////////////////


        switch(intent.getIntExtra("month",0)){
            case 1: mmonth = "January"; break;
            case 2: mmonth = "Febuary"; break;
            case 3: mmonth = "March"; break;
            case 4: mmonth = "April"; break;
            case 5: mmonth = "May"; break;
            case 6: mmonth = "June"; break;
            case 7: mmonth = "July"; break;
            case 8: mmonth = "August"; break;
            case 9: mmonth = "September"; break;
            case 10: mmonth = "October"; break;
            case 11: mmonth = "November"; break;
            case 12: mmonth = "December"; break;

        }

        switch(intent.getIntExtra("week",0)){
            case 1: mweek = "Sunday"; break;
            case 2: mweek = "Monday"; break;
            case 3: mweek = "Tuesday"; break;
            case 4: mweek = "Wednesday"; break;
            case 5: mweek = "Thursday"; break;
            case 6: mweek = "Friday"; break;
            case 7: mweek = "Saturday"; break;


        }
        editdaily.requestFocus();

        daily.setTypeface(Typeface.createFromAsset(getAssets(),"MILKYWAY.TTF"));    //날짜 폰트 은하수
        daily.setText(mweek + " / " +mmonth+" "+intent.getIntExtra("day",0)+" / " + intent.getIntExtra("year",0));  //상단에 날짜 띄어줌

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.write_clock : {           //시계버튼 클릭했을 시 현재 시간 append해주는 기능
                Calendar oCalendar =Calendar.getInstance();
                int curHour = oCalendar.get(Calendar.HOUR);
                int curMinute = oCalendar.get(Calendar.MINUTE);
                int curNoon= oCalendar.get(Calendar.AM_PM); // 0이면 AM 1이면 PM
                String tempNoon;
                if(curNoon==0){
                    tempNoon = "AM";
                }else{
                    tempNoon="PM";
                }

                editdaily.append(tempNoon + " "+ curHour + " : " +curMinute);
                break;

            }



            case R.id.done :{           //done버튼이 눌렸을시 디비에 값저장
                if(editdaily.getText().toString().length()==0){

                }else{

                    String[] columns = new String[]{"content"};
                    String[] temp = {""+intent.getIntExtra("year",0),""+intent.getIntExtra("month",0),""+intent.getIntExtra("day",0)};
                    Cursor c = mTimeDB.query(columns ,"year = ? AND month =? AND day = ? ",temp,null,null,null);
                    if(c != null && c.getCount()!=0){
                        ContentValues upRowValue = new ContentValues();
                        upRowValue.put("content",editdaily.getText().toString());
                        mTimeDB.update(upRowValue,"year = ? AND month =? AND day = ? ",temp);

                    }else{

                        ContentValues addRowValue = new ContentValues();
                        addRowValue.put("year",intent.getIntExtra("year",0));
                        addRowValue.put("month",intent.getIntExtra("month",0));
                        addRowValue.put("day",intent.getIntExtra("day",0));
                        addRowValue.put("week",intent.getIntExtra("week",0));
                        addRowValue.put("content",editdaily.getText().toString());

                        long insertRecordId = mTimeDB.insert(addRowValue);


                    }
                    c.close();

                }   //DB에 저장 되어있으면 갱신 없으면 생성




                finish();
                break;
            }



        }

    }
    public void onStop(){
        super.onStop();
    }

}


