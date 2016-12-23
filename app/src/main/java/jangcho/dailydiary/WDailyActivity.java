package jangcho.dailydiary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.unity3d.ads.UnityAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



import java.util.Calendar;


/**
 * Created by Administrator on 2016-12-10.
 */



public class WDailyActivity extends Activity {

    EditText editdaily = null;
    public TimeDB mTimeDB = null;
    public NewsDB mNewsDB = null;
    Intent intent = null;

    int weathermode; // 0: sunny 1: partly cloudy 2: cloud 3: rain 4: snow 5: snow/rain
    int weather_id;
    ImageView weather = null;
    String s_news = "";
    String hyper = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wdaily_activity_layout);


        mTimeDB = TimeDB.getInstance(this);
        mNewsDB = NewsDB.getInstance(this);

        TextView daily = (TextView) findViewById(R.id.w_daily);
        editdaily = (EditText) findViewById(R.id.w_daily_edit);
        String mmonth = "";
        String mweek = "";

        String FONT_TYPE = (String) MyAccount.getValue(this, "FONT");
        Util.setGlobalFont(this, getWindow().getDecorView(), FONT_TYPE);

        intent = getIntent();

        //////날씨 관련///////////

        weathermode = 1;
        weather_id = R.id.sunny;
        weather = (ImageView) findViewById(weather_id);


        ////////////////////////////


        ////////////DB 존재하면 내용을 append
        String[] columns = new String[]{"content", "weather"};
        String[] temp = {"" + intent.getIntExtra("year", 0), "" + intent.getIntExtra("month", 0), "" + intent.getIntExtra("day", 0)};
        Cursor c = mTimeDB.query(columns, "year = ? AND month =? AND day = ? ", temp, null, null, null);
        if (c != null && c.getCount() != 0) {

            c.moveToFirst();
            editdaily.setText(c.getString(0));
            editdaily.setSelection(editdaily.length());
            weathermode = c.getInt(1);
            weather.setAlpha(0.3f);

            switch (weathermode) {
                case 0:
                    weather_id = R.id.sunny;
                    break;
                case 1:
                    weather_id = R.id.cloud;
                    break;
                case 2:
                    weather_id = R.id.smokycloud;
                    break;
                case 3:
                    weather_id = R.id.rain;
                    break;
                case 4:
                    weather_id = R.id.snow;
                    break;
                case 5:
                    weather_id = R.id.snow_rain;
                    break;


            }
            weather = (ImageView) findViewById(weather_id);
            weather.setAlpha(1.0f);


        }


        /////////////////////////////////////////


        switch (intent.getIntExtra("month", 0)) {
            case 1:
                mmonth = "January";
                break;
            case 2:
                mmonth = "Febuary";
                break;
            case 3:
                mmonth = "March";
                break;
            case 4:
                mmonth = "April";
                break;
            case 5:
                mmonth = "May";
                break;
            case 6:
                mmonth = "June";
                break;
            case 7:
                mmonth = "July";
                break;
            case 8:
                mmonth = "August";
                break;
            case 9:
                mmonth = "September";
                break;
            case 10:
                mmonth = "October";
                break;
            case 11:
                mmonth = "November";
                break;
            case 12:
                mmonth = "December";
                break;

        }

        switch (intent.getIntExtra("week", 0)) {
            case 1:
                mweek = "Sunday";
                break;
            case 2:
                mweek = "Monday";
                break;
            case 3:
                mweek = "Tuesday";
                break;
            case 4:
                mweek = "Wednesday";
                break;
            case 5:
                mweek = "Thursday";
                break;
            case 6:
                mweek = "Friday";
                break;
            case 7:
                mweek = "Saturday";
                break;


        }
        editdaily.requestFocus();

        String font_type = (String)MyAccount.getValue(this, "FONT");
        daily.setTypeface(Typeface.createFromAsset(getAssets(), font_type));    //날짜 폰트 은하수
        daily.setText(mweek + " / " + mmonth + " " + intent.getIntExtra("day", 0) + " / " + intent.getIntExtra("year", 0));  //상단에 날짜 띄어줌


    }

    public void weatherClick(View v) {

        weathermode = Integer.parseInt((String) v.getTag());
        weather_id = v.getId();
        weather.setAlpha(0.3f);

        weather = (ImageView) findViewById(weather_id);
        weather.setAlpha(1.0f);


    }


    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.w_write_clock: {           //시계버튼 클릭했을 시 현재 시간 append해주는 기능
                Calendar oCalendar = Calendar.getInstance();
                int curHour = oCalendar.get(Calendar.HOUR);
                int curMinute = oCalendar.get(Calendar.MINUTE);
                int curNoon = oCalendar.get(Calendar.AM_PM); // 0이면 AM 1이면 PM
                String tempNoon;
                if (curNoon == 0) {
                    tempNoon = "AM";
                } else {
                    tempNoon = "PM";
                }

                editdaily.append(tempNoon + " " + curHour + " : " + curMinute);
                break;


            }


            case R.id.w_done: {           //done버튼이 눌렸을시 디비에 값저장


                if (editdaily.getText().toString().length() == 0) {

                } else {


                    String[] columns = new String[]{"content"};
                    String[] temp = {"" + intent.getIntExtra("year", 0), "" + intent.getIntExtra("month", 0), "" + intent.getIntExtra("day", 0)};
                    Cursor c = mTimeDB.query(columns, "year = ? AND month =? AND day = ? ", temp, null, null, null);


                    if (c != null && c.getCount() != 0) {


                        ContentValues upRowValue = new ContentValues();
                        upRowValue.put("content", editdaily.getText().toString());
                        upRowValue.put("weather", weathermode);
                        mTimeDB.update(upRowValue, "year = ? AND month =? AND day = ? ", temp);

                    } else {

                        int year = intent.getIntExtra("year", 0);
                        int month = intent.getIntExtra("month", 0);
                        int day = intent.getIntExtra("day", 0);

                        Calendar oCalendar = Calendar.getInstance();
                        int curYear = oCalendar.get(Calendar.YEAR);
                        int curMonth = oCalendar.get(Calendar.MONTH) + 1;   //0이면 1월
                        int curDay = oCalendar.get(Calendar.DAY_OF_MONTH);


                        oCalendar.add(Calendar.DATE,-1);

///////////////////////////////////////////////오늘 날짜 DB에 Insert
                        if (curYear == year && curMonth == month && curDay == day) {



                            news();
                            ContentValues addRowValue1 = new ContentValues();
                            addRowValue1.put("year", year);
                            addRowValue1.put("month", month);
                            addRowValue1.put("day", day);
                            addRowValue1.put("hyper", hyper);
                            addRowValue1.put("content", s_news);

                            long insertRecordId1 = mNewsDB.insert(addRowValue1);

                            int count =0;
                            String[] columns1 = new String[]{"pencil_count"};
                            String[] temp1 = {"" + oCalendar.get(Calendar.YEAR), "" + (oCalendar.get(Calendar.MONTH)+1), "" + oCalendar.get(Calendar.DATE)};
                            Cursor c1 = mTimeDB.query(columns1, "year = ? AND month =? AND day = ? ", temp1, null, null, null);

                            if (c1 != null && c1.getCount() != 0) {     //DB에 전날값이 있는경우
                                c1.moveToFirst();
                                count = c1.getInt(0);
                                ContentValues upRowValue = new ContentValues();
                                upRowValue.put("pencil_count", 0);
                                mTimeDB.update(upRowValue, "year = ? AND month =? AND day = ? ", temp1);


                                    ContentValues addRowValue = new ContentValues();
                                    addRowValue.put("year", year);
                                    addRowValue.put("month", month);
                                    addRowValue.put("day", day);
                                    addRowValue.put("week", intent.getIntExtra("week", 0));
                                    addRowValue.put("content", editdaily.getText().toString());
                                    addRowValue.put("pencil_count",(count+1));
                                    addRowValue.put("weather", weathermode);

                                    long insertRecordId = mTimeDB.insert(addRowValue);

                                if(count!=0){
                                    final Intent intent = new Intent(getApplicationContext(), AdsDialog.class);

                                    intent.putExtra("count",count+1);

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(intent);
                                        }
                                    }, 250);
                                }



                            }   //전날 값이 DB에 있는 경우

                            else{

                                ContentValues addRowValue = new ContentValues();
                                addRowValue.put("year", year);
                                addRowValue.put("month", month);
                                addRowValue.put("day", day);
                                addRowValue.put("week", intent.getIntExtra("week", 0));
                                addRowValue.put("content", editdaily.getText().toString());
                                addRowValue.put("pencil_count",(count+1));
                                addRowValue.put("weather", weathermode);

                                long insertRecordId = mTimeDB.insert(addRowValue);

                                final Intent intent = new Intent(getApplicationContext(), AdsDialog.class);

                                intent.putExtra("count",count+1);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(intent);
                                    }
                                }, 250);

                            }


                            c1.close();



                        }
      /////////////////////////////////////////////////////////////////////////////////////////////////////

     ////////////////////////////////////오늘날짜가 아닌 날짜 DB에 insert
                        else {


                            ContentValues addRowValue = new ContentValues();
                            addRowValue.put("year", year);
                            addRowValue.put("month", month);
                            addRowValue.put("day", day);
                            addRowValue.put("week", intent.getIntExtra("week", 0));
                            addRowValue.put("content", editdaily.getText().toString());
                            addRowValue.put("pencil_count",0);
                            addRowValue.put("weather", weathermode);

                            long insertRecordId = mTimeDB.insert(addRowValue);




                        }


                    }
                    c.close();

                }   //DB에 저장 되어있으면 갱신 없으면 생성


                finish();
                break;
            }


        }

    }

    public void news() {

        try {
            Document document = Jsoup.connect("http://news.naver.com/main/ranking/popularDay.nhn").get();

            if (null != document) {

                Elements elements = document.select("div#ranking_000 > ul > li > a");
                s_news = "";
                hyper = "";
                for (int i = 0; i < elements.size(); i++) {

                    s_news += (elements.get(i).attr("title") + "@");


                }

                for (int i = 0; i < elements.size(); i++) {

                    hyper += (elements.get(i).attr("href") + "@");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void onStop() {
        super.onStop();
    }



}
