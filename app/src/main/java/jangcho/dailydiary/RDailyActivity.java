package jangcho.dailydiary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * Created by Administrator on 2016-12-10.
 */

public class RDailyActivity extends Activity {

    TextView textdaily = null;
    public TimeDB mTimeDB = null;
    Intent intent=null;
    int year,month,day,week,weather1;

    ImageView weather = null;
    AnimationDrawable frameAnimation;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rdaily_activity_layout);

        String FONT_TYPE = (String)MyAccount.getValue(this, "FONT");
        Util.setGlobalFont(this, getWindow().getDecorView(), FONT_TYPE);

        mTimeDB = TimeDB.getInstance(this);

        TextView daily= (TextView)findViewById(R.id.r_daily);
        textdaily = (TextView)findViewById(R.id.r_daily_edit);
        weather =(ImageView)findViewById(R.id.weather_ani1);


        String mmonth ="";
        String mweek ="";

        intent = getIntent();
        year = intent.getIntExtra("year",0);
        month = intent.getIntExtra("month",0);
        day =intent.getIntExtra("day",0);
        week = intent.getIntExtra("week",0);





        ////////////DB 존재하면 내용을 append
        String[] columns = new String[]{"content","weather"};


        String[] temp = {""+year,""+month,""+day};
        Cursor c = mTimeDB.query(columns ,"year = ? AND month =? AND day = ? ",temp,null,null,null);
        if(c != null && c.getCount()!=0){

            c.moveToFirst();
            textdaily.setText(c.getString(0));
            weather1 = c.getInt(1);
        }
        /////////////////////////////////////////

        switch(weather1){
            case 0: weather.setBackgroundResource(R.drawable.sunny_animation); break;
            case 1: weather.setBackgroundResource(R.drawable.cloud_animation); break;
            case 2: weather.setBackgroundResource(R.drawable.smokycloud_animation); break;
            case 3: weather.setBackgroundResource(R.drawable.rain_animation); break;
            case 4: weather.setBackgroundResource(R.drawable.snow_animation); break;
            case 5: weather.setBackgroundResource(R.drawable.snow_rain_animation); break;
        }
        frameAnimation = (AnimationDrawable)weather.getBackground();
        frameAnimation.start();


        switch(month){
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

        switch(week){
            case 1: mweek = "Sunday"; break;
            case 2: mweek = "Monday"; break;
            case 3: mweek = "Tuesday"; break;
            case 4: mweek = "Wednesday"; break;
            case 5: mweek = "Thursday"; break;
            case 6: mweek = "Friday"; break;
            case 7: mweek = "Saturday"; break;


        }


        daily.setText(mweek + " / " +mmonth+" "+intent.getIntExtra("day",0)+" / " + intent.getIntExtra("year",0));  //상단에 날짜 띄어줌

    }

    public void onClick(View v){
        switch(v.getId()){




            case R.id.r_done :{           //done버튼이 눌렸을시 디비에 값저장

               final Intent intent = new Intent(getApplicationContext(), WDailyActivity.class);

                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("day", day);
                intent.putExtra("week", week);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        finish();
                    }
                }, 250);

                break;
            }



        }

    }
    public void onStop(){
        super.onStop();
    }

}


