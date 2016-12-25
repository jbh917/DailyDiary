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
import android.widget.ImageButton;
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
    public NewsDB mNewsDB =null;
    Intent intent=null;
    int year,month,day,week,weather1;
    ImageButton inews = null;

    ImageView weather = null;
    AnimationDrawable frameAnimation;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rdaily_activity_layout);


        //Util.setGlobalFont(this, getWindow().getDecorView(), FONT_TYPE);

        mTimeDB = TimeDB.getInstance(this);
        mNewsDB = NewsDB.getInstance(this);

        TextView daily= (TextView)findViewById(R.id.r_daily);
        textdaily = (TextView)findViewById(R.id.r_daily_edit);
        weather =(ImageView)findViewById(R.id.weather_ani1);
        inews = (ImageButton)findViewById(R.id.news);

        String mmonth ="";
        String mweek ="";

        intent = getIntent();
        year = intent.getIntExtra("year",0);
        month = intent.getIntExtra("month",0);
        day =intent.getIntExtra("day",0);
        week = intent.getIntExtra("week",0);

        String FONT_TYPE = (String)MyAccount.getValue(this, "FONT");
        Util.setFont(textdaily, FONT_TYPE, getAssets());

        String[] columns1 = new String[]{"content"};


        String[] temp1 = {""+year,""+month,""+day};
        Cursor c = mNewsDB.query(columns1 ,"year = ? AND month =? AND day = ? ",temp1,null,null,null);

        try{
            if(c != null && c.getCount()!=0){
                inews.setVisibility(View.VISIBLE);
            }else{
                inews.setVisibility(View.GONE);

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            c.close();
        }




        ////////////DB 존재하면 내용을 append
        String[] columns = new String[]{"content","weather"};


        String[] temp = {""+year,""+month,""+day};
        Cursor c1 = mTimeDB.query(columns ,"year = ? AND month =? AND day = ? ",temp,null,null,null);

        try{
            if(c1 != null && c1.getCount()!=0){
                c1.moveToFirst();
                textdaily.setText(c1.getString(0));
                weather1 = c1.getInt(1);

            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            c1.close();
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


            case R.id.news:{

                String[] columns = new String[]{"content"};


                String[] temp = {""+year,""+month,""+day};
                Cursor c = mNewsDB.query(columns ,"year = ? AND month =? AND day = ? ",temp,null,null,null);

                try{
                    if(c != null && c.getCount()!=0){

                        final Intent intent = new Intent(getApplicationContext(), NewsActivity.class);

                        intent.putExtra("year", year);
                        intent.putExtra("month", month);
                        intent.putExtra("day", day);
                        intent.putExtra("week", week);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        }, 250);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    c.close();
                }






                break;




            }


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


