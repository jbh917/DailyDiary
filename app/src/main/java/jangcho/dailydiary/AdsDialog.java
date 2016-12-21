package jangcho.dailydiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by 장보현1 on 2016-12-21.
 */

public class AdsDialog extends Activity {

    Intent intent=null;
    int year,month,day,week;

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

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.adsview:{


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

                finish();
                break;

            }
            case R.id.exit: finish();break;


        }



    }

}