package jangcho.dailydiary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Administrator on 2016-12-10.
 */

public class WDailyActivity extends Activity {

    EditText editdaily = null;
    public TimeDB mTimeDB = null;
    Intent intent=null;

    int weathermode ; // 0: sunny 1: partly cloudy 2: cloud 3: rain 4: snow 5: snow/rain
    int weather_id;
    ImageView weather = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wdaily_activity_layout);

        mTimeDB = TimeDB.getInstance(this);

        TextView daily= (TextView)findViewById(R.id.w_daily);
        editdaily = (EditText)findViewById(R.id.w_daily_edit);
        String mmonth ="";
        String mweek ="";

        String FONT_TYPE = (String)MyAccount.getValue(this, "FONT");
        Util.setGlobalFont(this, getWindow().getDecorView(), FONT_TYPE);

        intent = getIntent();

        //////날씨 관련///////////

        weathermode = 1;
        weather_id = R.id.sunny;
        weather = (ImageView)findViewById(weather_id);


        ////////////////////////////



        ////////////DB 존재하면 내용을 append
        String[] columns = new String[]{"content","weather"};
        String[] temp = {""+intent.getIntExtra("year",0),""+intent.getIntExtra("month",0),""+intent.getIntExtra("day",0)};
        Cursor c = mTimeDB.query(columns ,"year = ? AND month =? AND day = ? ",temp,null,null,null);
        if(c != null && c.getCount()!=0){

            c.moveToFirst();
            editdaily.setText(c.getString(0));
            editdaily.setSelection(editdaily.length());
            weathermode = c.getInt(1);
            weather.setAlpha(0.3f);

            switch (weathermode){
                case 0: weather_id = R.id.sunny; break;
                case 1: weather_id = R.id.cloud; break;
                case 2: weather_id = R.id.smokycloud; break;
                case 3: weather_id = R.id.rain; break;
                case 4: weather_id = R.id.snow; break;
                case 5: weather_id = R.id.snow_rain; break;


            }
            weather =(ImageView)findViewById(weather_id);
            weather.setAlpha(1.0f);



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

    public void weatherClick(View v){

        weathermode =Integer.parseInt((String)v.getTag()) ;
        weather_id = v.getId();
        weather.setAlpha(0.3f);

        weather = (ImageView)findViewById(weather_id);
        weather.setAlpha(1.0f);



    }


    public void onClick(View v){
        switch(v.getId()){



            case R.id.w_write_clock : {           //시계버튼 클릭했을 시 현재 시간 append해주는 기능
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



            case R.id.w_done :{           //done버튼이 눌렸을시 디비에 값저장
                if(editdaily.getText().toString().length()==0){

                }else{

                    String[] columns = new String[]{"content"};
                    String[] temp = {""+intent.getIntExtra("year",0),""+intent.getIntExtra("month",0),""+intent.getIntExtra("day",0)};
                    Cursor c = mTimeDB.query(columns ,"year = ? AND month =? AND day = ? ",temp,null,null,null);
                    if(c != null && c.getCount()!=0){


                        ContentValues upRowValue = new ContentValues();
                        upRowValue.put("content",editdaily.getText().toString());
                        upRowValue.put("weather",weathermode);
                        mTimeDB.update(upRowValue,"year = ? AND month =? AND day = ? ",temp);

                    }else{


                        ContentValues addRowValue = new ContentValues();
                        addRowValue.put("year",intent.getIntExtra("year",0));
                        addRowValue.put("month",intent.getIntExtra("month",0));
                        addRowValue.put("day",intent.getIntExtra("day",0));
                        addRowValue.put("week",intent.getIntExtra("week",0));
                        addRowValue.put("content",editdaily.getText().toString());
                        addRowValue.put("weather",weathermode);

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


