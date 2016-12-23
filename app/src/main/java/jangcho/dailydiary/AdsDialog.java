package jangcho.dailydiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.unity3d.ads.UnityAds;

/**
 * Created by 장보현1 on 2016-12-21.
 */

public class AdsDialog extends Activity {

    Intent intent=null;
    int year,month,day,week;
    int count;
    TextView a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        setContentView(R.layout.ads_dailog);


        intent = getIntent();
        year = intent.getIntExtra("year",0);
        month = intent.getIntExtra("month",0);
        day =intent.getIntExtra("day",0);
        week = intent.getIntExtra("week",0);
        count = intent.getIntExtra("count",0);

        a =(TextView)findViewById(R.id.adstext);

        if(count!=0){
            a.setText("광고를 보시면"+(count*2)+"개를 드립니다.");
        }

    }

    public void onClick(View v){
        int cnt = (int) MyAccount.getValue(this, "CNT");

        switch (v.getId()){
            case R.id.adsview:{
                UnityAds.show(this, "rewardedVideo");

                if(count!=0){
                    cnt = cnt + count*2;
                    MyAccount.setPencilCount(this, cnt);
                }else{
                    MyAccount.setPencilCount(this, cnt + 1);

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


                }

                finish();
                break;

            }
            case R.id.exit: {
                if(count!=0) {
                    cnt = cnt + count;
                    MyAccount.setPencilCount(this, cnt);
                }
                finish();
                break;
            }

        }



    }

}