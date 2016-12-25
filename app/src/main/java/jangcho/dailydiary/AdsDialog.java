package jangcho.dailydiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.unity3d.ads.UnityAds;

/**
 * Created by 장보현1 on 2016-12-21.
 */

public class AdsDialog extends Activity {

    Intent intent=null;
    int year,month,day,week;
    int count;
    TextView top;
    TextView bottom;

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

        top =(TextView)findViewById(R.id.adstext);
        bottom =(TextView)findViewById(R.id.adstext2);


        if(count!=0){
            top.setText("광고를 보시면 \n"+count+ "개의 연필을 추가로 드립니다.");
            bottom.setVisibility(View.GONE);
        }

    }

    public void onClick(View v){
        int cnt = (int) MyAccount.getValue(this, "CNT");

        switch (v.getId()){
            case R.id.adsview:{
                if(!UnityAds.isReady()){
                    Toast.makeText(getApplicationContext(), "광고준비가 되지 않았습니다. 잠시후 다시 눌러주세요", Toast.LENGTH_SHORT).show();
                }else{
                    UnityAds.show(this, "rewardedVideo");

                    if(count!=0){
                        cnt = cnt + count*2;
                        MyAccount.setPencilCount(this, cnt);
                    } else {
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

                }


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