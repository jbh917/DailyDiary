package jangcho.dailydiary;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by CHJ on 2016. 12. 20..
 */

public class SettingDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        setContentView(R.layout.dialog_settings);

        initDialog();
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.milkyway:
                MyAccount.setFont(this, "MILKYWAY");
                break;

            case R.id.nanum_gothic:
                MyAccount.setFont(this, "nanum_gothic");
                break;

            case R.id.nanum_myeongjo:
                MyAccount.setFont(this, "nanum_myeongjo");
                break;

            case R.id.seoul_hangang:
                MyAccount.setFont(this, "seoul_hangang");
                break;

            case R.id.spoqa_han_sans:
                MyAccount.setFont(this, "spoqa_han_sans");
                break;

            case R.id.tfs_inklipquid:
                MyAccount.setFont(this, "tfs_inkliqpuid");
                break;

            case R.id.buy:

                int cnt = (int)MyAccount.getValue(this, "CNT");
                Log.e("before", cnt+"");

                if(cnt > 56) {
                    MyAccount.setPencilCount(this, cnt - 56);
                    MyAccount.setFontAvailable(this, true);

                    Log.e("after_cnt", (int) MyAccount.getValue(this, "CNT") + "");
                    Log.e("after_boolean", (boolean) MyAccount.getValue(this, "AVAILABLE") + "");
                } else {
                    Toast.makeText(getApplicationContext(), "NOT ENOUGH PENCIL", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }


    private void initDialog() {

        String[] fonts = {"MILKYWAY.TTF",
                "NanumGothic.ttc",
                "NanumMyeongjo.ttc",
                "SeoulHangangM.otf",
                "SpoqaHanSansRegular.ttf",
                "THEFACESHOP_INKLIPQUID.otf"};

        TextView[] tv = {(TextView) findViewById(R.id.milkyway),
                (TextView) findViewById(R.id.nanum_gothic),
                (TextView) findViewById(R.id.nanum_myeongjo),
                (TextView) findViewById(R.id.seoul_hangang),
                (TextView) findViewById(R.id.spoqa_han_sans),
                (TextView) findViewById(R.id.tfs_inklipquid)
        };

        for(int i=0; i<fonts.length; i++) {
            Typeface tf = Typeface.createFromAsset(getAssets(), fonts[i]);
            tv[i].setTypeface(tf);
        }
    }
}