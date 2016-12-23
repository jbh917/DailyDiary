package jangcho.dailydiary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

public class MainActivity extends Activity {

    int curYear, curMonth, curDay, curHour, curMinute, curNoon, curSecond, curWeek;     //현재 년,월,일,시간,분,낮밤,초,요일
    int todayYear, todayMonth, todayDay ;    //오늘 년/달/일

    ArrayList<Data> mData = null;       //현재달의 정보 저장한 ArrayList
    ListView mListView = null;
    DotBaseAdapter mAdapter = null;
    public TimeDB mTimeDB = null;
    public NewsDB mNewsDB = null;
    HorizontalScrollView mhori = null;
    LinearLayout lin = null;
    LinearLayout ylin = null;
    int i_alphaid = R.id.dec;           //현재 깜빡이는 달의 id
    int b_alphaid = R.id.y2016;
    TextView pc;                        // 연필 갯수 TextView
    ImageButton i_alphabutt = null;     //현재 깜빡이는 달
    Button b_alphabutt =null;
    Animation ani = null;
    boolean islineclick =false;         //선모양 버튼이 눌렸는지

    final String UNITYADS_KEY = "1239782";
    final private UnityAdsListener unityAdsListener = new UnityAdsListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        UnityAds.initialize(this, UNITYADS_KEY, unityAdsListener, false);
/*
        MyAccount.setFont(this, "hankyoreh");
        MyAccount.setFontAvailable(this, false);
        MyAccount.setPencilCount(this, 100);
*/
        isAvailable();
        //String FONT_TYPE = (String)MyAccount.getValue(this, "FONT");
        //Util.setGlobalFont(this, getWindow().getDecorView(), FONT_TYPE);

        int pencil_count = (int)MyAccount.getValue(this, "CNT");
        pc = (TextView) findViewById(R.id.pencil_count);
        pc.setText(pencil_count+"");


        islineclick=false;
        mTimeDB = TimeDB.getInstance(this);
        mNewsDB = NewsDB.getInstance(this);

        mData = new ArrayList<Data>();
        mhori = (HorizontalScrollView)findViewById(R.id.horizontal);
        lin = (LinearLayout)findViewById(R.id.linear);
        ylin = (LinearLayout)findViewById(R.id.year_linear);


        i_alphabutt = (ImageButton)findViewById(i_alphaid);
        b_alphabutt = (Button)findViewById(b_alphaid);
        ani = AnimationUtils.loadAnimation(this, R.anim.alpha);
        i_alphabutt.startAnimation(ani);
        b_alphabutt.startAnimation(ani);

        //현재 요일 구하기/////////////////////
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

        ///////////////////////////////////////



        ///현재달에 맞게 점 그리기//////////
        oCalendar.set(Calendar.YEAR,curYear);
        oCalendar.set(Calendar.MONTH,curMonth-1);
        oCalendar.set(Calendar.DATE,curDay);



        for (int i = curDay; i > 0; i--) {
            Data data = new Data();
            data.tempYear = curYear;
            data.tempMonth = curMonth;
            data.tempDay = i;
            data.tempWeek = curWeek;
            curWeek--;
            if (curWeek == 0) {
                curWeek = 7;
            }
            String[] columns = new String[]{"content","weather"};
            String[] temp = {""+data.tempYear,""+data.tempMonth,""+i};
            Cursor c = mTimeDB.query(columns ,"year = ? AND month =? AND day = ? ",temp,null,null,null);
            if(c != null && c.getCount()!=0){
                data.isDB = true;
                c.moveToFirst();
                data.tempContent = c.getString(0);
                data.weather = c.getInt(1);
            }
            c.close();


            mData.add(0, data);
        }


        mAdapter = new DotBaseAdapter(this, getAssets(), mData);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(mAdapter);
        mListView.post(new Runnable() {
            @Override
            public void run() {
                mListView.setSelection(mListView.getCount()-1);
            }
        });
        ////////////////////////////////////


        //리스트뷰 리스너 달기//////

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String[] columns = new String[]{"content"};


                String[] temp = {""+mData.get(position).tempYear,""+mData.get(position).tempMonth,""+mData.get(position).tempDay};
                Cursor c = mTimeDB.query(columns ,"year = ? AND month =? AND day = ? ",temp,null,null,null);
                if(c != null && c.getCount()!=0){

                    final Intent intent = new Intent(getApplicationContext(), RDailyActivity.class);

                    intent.putExtra("year", mData.get(position).tempYear);
                    intent.putExtra("month", mData.get(position).tempMonth);
                    intent.putExtra("day", mData.get(position).tempDay);
                    intent.putExtra("week", mData.get(position).tempWeek);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(intent);
                        }
                    }, 250);



                }else{
                    if(todayYear!=mData.get(position).tempYear||todayMonth!=mData.get(position).tempMonth||todayDay!=mData.get(position).tempDay){

                        final Intent intent = new Intent(getApplicationContext(), AdsDialog.class);

                        intent.putExtra("year", mData.get(position).tempYear);
                        intent.putExtra("month", mData.get(position).tempMonth);
                        intent.putExtra("day", mData.get(position).tempDay);
                        intent.putExtra("week", mData.get(position).tempWeek);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        }, 250);


                    }else{
                        final Intent intent = new Intent(getApplicationContext(), WDailyActivity.class);

                        intent.putExtra("year", mData.get(position).tempYear);
                        intent.putExtra("month", mData.get(position).tempMonth);
                        intent.putExtra("day", mData.get(position).tempDay);
                        intent.putExtra("week", mData.get(position).tempWeek);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        }, 250);

                    }



                }



            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int temp_postion = position;

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
                alert_confirm.setMessage("" + mData.get(position).tempYear+"-"+mData.get(position).tempMonth + "-" + mData.get(position).tempDay + "의 일기내용을 정말로 삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int deleteRecordCnt = mTimeDB.delete("year='" +mData.get(temp_postion).tempYear+"'" +" AND month='" + mData.get(temp_postion).tempMonth+"'" +" AND day = '"+mData.get(temp_postion).tempDay+"'" ,null );

                                int deleteRecordCnt1 = mNewsDB.delete("year='" +mData.get(temp_postion).tempYear+"'" +" AND month='" + mData.get(temp_postion).tempMonth+"'" +" AND day = '"+mData.get(temp_postion).tempDay+"'" ,null );
                                banbok();
                            }
                        }).setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'No'
                                return;
                            }
                        });
                AlertDialog alert = alert_confirm.create();
                alert.show();

                return true;
            }
        });


    }




    /////////////////////////////////////////////


    public void onResume(){

        super.onResume();

        int pencil_count = (int)MyAccount.getValue(this, "CNT");
        //pc = (TextView) findViewById(R.id.pencil_count);
        pc.setText(pencil_count+"");
        isAvailable();

        banbok();
    }


    public void banbok(){       //리스트뷰 그려주는 함수

        if(islineclick) {
            mData.clear();
            String[] columns = new String[]{"_id", "year", "month", "day", "content", "week","weather"};
            Cursor c = mTimeDB.query(columns, "year = '" + curYear + "' AND month = '" + curMonth + "'", null, null, null, "day ASC");
            if (c != null) {
                while (c.moveToNext()) {
                    Data data = new Data();
                    data.tempYear = c.getInt(1);
                    data.tempMonth = c.getInt(2);
                    data.tempDay = c.getInt(3);
                    data.tempContent = c.getString(4);
                    data.tempWeek = c.getInt(5);
                    data.weather = c.getInt(6);
                    data.isDB = true;
                    mData.add(data);

                }
                c.close();
            }


            mListView.post(new Runnable() {
                @Override
                public void run() {
                    mListView.setSelection(mListView.getCount()-1);
                }
            });
            mAdapter.notifyDataSetChanged();


        }else {
            mData.clear();

            if (curYear > todayYear) {

            } else {
                Calendar oCalendar = Calendar.getInstance();
                if (curYear == todayYear && curMonth == todayMonth) {
                    curDay = todayDay;
                }
                oCalendar.set(Calendar.YEAR, curYear);
                oCalendar.set(Calendar.MONTH, curMonth - 1);
                oCalendar.set(Calendar.DATE, curDay);
                curWeek = oCalendar.get(Calendar.DAY_OF_WEEK);

                for (int i = curDay; i > 0; i--) {
                    Data data = new Data();
                    data.tempYear = curYear;
                    data.tempMonth = curMonth;
                    data.tempDay = i;
                    data.tempWeek = curWeek;
                    curWeek--;
                    if (curWeek == 0) {
                        curWeek = 7;
                    }
                    String[] columns = new String[]{"content","weather"};
                    String[] temp = {"" + data.tempYear, "" + data.tempMonth, "" + i};
                    Cursor c = mTimeDB.query(columns, "year = ? AND month =? AND day = ? ", temp, null, null, null);
                    if (c != null && c.getCount() != 0) {
                        data.isDB = true;
                        c.moveToFirst();
                        data.tempContent = c.getString(0);
                        data.weather =c.getInt(1);
                    }
                    c.close();


                    mData.add(0, data);


                }
                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        mListView.setSelection(mListView.getCount() - 1);
                    }
                });
                mAdapter.notifyDataSetChanged();


            }
        }

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.main_month:{                          //하단 월 버튼 눌렀을 시
                mhori.setVisibility(View.VISIBLE);
                lin.setVisibility(View.GONE);


                mhori.post(new Runnable() {
                    public void run() {


                        ImageButton temp=null;

                        switch (curMonth){
                            case 1: temp = (ImageButton)findViewById(R.id.jan); break;
                            case 2: temp = (ImageButton)findViewById(R.id.feb);break;
                            case 3: temp = (ImageButton)findViewById(R.id.mar);break;
                            case 4: temp = (ImageButton)findViewById(R.id.apr);break;
                            case 5: temp = (ImageButton)findViewById(R.id.may);break;
                            case 6: temp = (ImageButton)findViewById(R.id.jun);break;
                            case 7: temp = (ImageButton)findViewById(R.id.july);break;
                            case 8: temp = (ImageButton)findViewById(R.id.aug);break;
                            case 9: temp = (ImageButton)findViewById(R.id.sep);break;
                            case 10: temp = (ImageButton)findViewById(R.id.oct);break;
                            case 11: temp = (ImageButton)findViewById(R.id.nov);break;
                            case 12: temp = (ImageButton)findViewById(R.id.dec);break;


                        }
                        mhori.scrollTo(temp.getLeft(),0);
                    }
                });

                break;
            }
            case R.id.main_year:{                       //하단 년 버튼 눌렀을 시
                lin.setVisibility(View.GONE);
                ylin.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.cross:{
                final Intent intent = new Intent(getApplicationContext(), RDailyActivity.class);

                Calendar oCalendar = Calendar.getInstance();

                intent.putExtra("year", oCalendar.get(Calendar.YEAR));
                intent.putExtra("month", oCalendar.get(Calendar.MONTH) + 1);
                intent.putExtra("day", oCalendar.get(Calendar.DAY_OF_MONTH));
                intent.putExtra("week", oCalendar.get(Calendar.DAY_OF_WEEK));

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                }, 250);
                break;
            }
            case R.id.search:{
                final Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                }, 250);
                break;
            }
            case R.id.line:{

                if(!islineclick){
                    islineclick=true;
                }else{
                    islineclick=false;
                }
                banbok();
                break;
            }
            case R.id.font: {


                final Intent intent = new Intent(getApplicationContext(), SettingDialog.class);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                }, 250);
                break;
            }
                /////알람기능////////

        }



    }



    public void onClick2(View v){

        Button temp1 = (Button)findViewById(R.id.main_year);
        b_alphaid = v.getId();
        b_alphabutt.clearAnimation();
        b_alphabutt = (Button)findViewById(b_alphaid);
        b_alphabutt.startAnimation(ani);

        switch(v.getId()){

            case R.id.y2011:   temp1.setText("2011"); curYear=2011; break;
            case R.id.y2012:   temp1.setText("2012"); curYear=2012; break;
            case R.id.y2013:   temp1.setText("2013"); curYear=2013; break;
            case R.id.y2014:   temp1.setText("2014"); curYear=2014; break;
            case R.id.y2015:   temp1.setText("2015"); curYear=2015; break;
            case R.id.y2016:   temp1.setText("2016"); curYear=2016; break;
            case R.id.y2017:   temp1.setText("2017"); curYear=2017; break;
        }
        switch(curMonth){
            case 1: curDay = 31; break;
            case 2:{
                if(curYear%4==0){
                    curDay=29;
                }else{
                    curDay=28;
                }
                break;
            }
            case 3:curDay = 31; break;
            case 4:curDay = 30; break;
            case 5:curDay = 31; break;
            case 6:curDay = 30; break;
            case 7:curDay = 31; break;
            case 8:curDay = 31; break;
            case 9:curDay = 30; break;
            case 10:curDay = 31; break;
            case 11:curDay = 30; break;
            case 12:curDay = 31; break;

        }

        banbok();
        lin.setVisibility(View.VISIBLE);
        ylin.setVisibility(View.GONE);

    }

    public void onClick1(View v){

        ImageButton temp1 = (ImageButton)findViewById(R.id.main_month);



        i_alphaid = v.getId();
        i_alphabutt.clearAnimation();
        i_alphabutt = (ImageButton)findViewById(i_alphaid);
        i_alphabutt.startAnimation(ani);

        switch(v.getId()){
            case R.id.jan:  temp1.setImageResource(R.drawable.jan2); curDay = 31; curMonth=1; break;
            case R.id.feb:  {temp1.setImageResource(R.drawable.feb2);
                if(curYear%4==0){
                    curDay=29;
                }else{
                    curDay=28;
                }
                curMonth=2;
                break;}
            case R.id.mar:  temp1.setImageResource(R.drawable.mar2); curDay=31;curMonth=3; break;
            case R.id.apr:  temp1.setImageResource(R.drawable.apr2); curDay=30;curMonth=4;break;
            case R.id.may:  temp1.setImageResource(R.drawable.may2); curDay=31;curMonth=5;break;
            case R.id.jun:  temp1.setImageResource(R.drawable.jun2); curDay=30;curMonth=6;break;
            case R.id.july: temp1.setImageResource(R.drawable.jul2); curDay=31;curMonth=7;break;
            case R.id.aug:  temp1.setImageResource(R.drawable.aug2); curDay=31;curMonth=8;break;
            case R.id.sep:  temp1.setImageResource(R.drawable.sep2); curDay=30;curMonth=9;break;
            case R.id.oct:  temp1.setImageResource(R.drawable.oct2); curDay=31;curMonth=10;break;
            case R.id.nov:  temp1.setImageResource(R.drawable.nov2); curDay=30;curMonth=11;break;
            case R.id.dec:  temp1.setImageResource(R.drawable.dec2); curDay=31;curMonth=12;break;

        }

        banbok();

        mhori.setVisibility(View.GONE);
        lin.setVisibility(View.VISIBLE);
    }   //하단 월 horizontalscroll이 visible 됐을대


    private void isAvailable() {
        if((boolean)MyAccount.getValue(this, "AVAILABLE")) {

            String STARTAT = (String) MyAccount.getValue(this, "STARTAT");

            DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date startted_at = sdFormat.parse(STARTAT);
                Date today = new Date();

                compareAndSetAvailable(startted_at, today);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void compareAndSetAvailable(Date prev, Date current) {
        int ONEWEEK = 1000 * 60 * 60 * 24 * 7;
        int diff = (int)(current.getTime() - prev.getTime());

        if(diff > ONEWEEK) {
            MyAccount.setFontAvailable(this, false);
            MyAccount.setFont(this, "hankyoreh");
            MyAccount.setFontStartTime(this, "");
        }

/*
        int ONEMIN = 1000 * 60;
        if(diff > ONEMIN) {
            MyAccount.setFontAvailable(this, false);
            MyAccount.setFont(this, "hankyoreh");
            MyAccount.setFontStartTime(this, "");

        }
*/
    }





    private class UnityAdsListener implements IUnityAdsListener {

        @Override
        public void onUnityAdsReady(String s) {
            // 광고 로드 되었을 때
            Log.d("ad_status", "on Ready!");
        }

        @Override
        public void onUnityAdsStart(String s) {
            Log.d("ad_status", "Started!");
        }

        @Override
        public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
            if(finishState != UnityAds.FinishState.SKIPPED) {
                // reward
            } else {

            }
            Log.d("ad_status", "Finished!");

        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
            Log.d("ad_status", "Error");
        }


    }


}

