package jangcho.dailydiary;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

public class ReviewDialog extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        setContentView(R.layout.dialog_review);

    }

    public void onClick(View v){
        switch(v.getId()){

            case R.id.exit: {

                finish();
                break;
            }

            case R.id.yes:{
                MyAccount.setWriteReview(this,1);

                int cnt = (int) MyAccount.getValue(this, "CNT");
                MyAccount.setPencilCount(this,cnt+56);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + this.getPackageName()));
                startActivity(intent);

                new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
                    @Override
                    public void run() {
                        moveTaskToBack(true);

                        finish();

                        android.os.Process.killProcess(android.os.Process.myPid());

                    }
                }, 100);



                break;
            }
            case R.id.no:{

                moveTaskToBack(true);

                finish();

                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            }


        }



    }

}