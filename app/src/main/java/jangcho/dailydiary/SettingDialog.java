package jangcho.dailydiary;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CHJ on 2016. 12. 20..
 */

public class SettingDialog extends Activity {

    Typeface tf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        setContentView(R.layout.dialog_settings);

        initDialog();
    }

    public void onBuy(View v) {

        int cnt = (int)MyAccount.getValue(this, "CNT");
        Log.e("before", cnt+"");

        if(cnt > 56) {
            MyAccount.setPencilCount(this, cnt - 56);
            MyAccount.setFontAvailable(this, true);


            DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date today = new Date();
            String today_str = sdFormat.format(today);
            MyAccount.setFontStartTime(this, today_str);


        } else {
            Toast.makeText(getApplicationContext(), "NOT ENOUGH PENCIL", Toast.LENGTH_LONG).show();
        }

    }

    public void onClick(View v) {

        boolean isAvailable = (boolean) MyAccount.getValue(this, "AVAILABLE");

        if(isAvailable) {
            Button[] btn = {(Button) findViewById(R.id.milkyway),
                    (Button) findViewById(R.id.nanum_gothic),
                    (Button) findViewById(R.id.nanum_myeongjo),
                    (Button) findViewById(R.id.seoul_hangang),
                    (Button) findViewById(R.id.spoqa_han_sans),
                    (Button) findViewById(R.id.tfs_inklipquid)
            };

            switch (v.getId()) {
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
            }
            setBorder(btn);
        } else {
            Toast.makeText(getApplicationContext(), "NOT AVAILABLE", Toast.LENGTH_LONG).show();
        }
    }


    private void initDialog() {

        String[] fonts = {"MILKYWAY.TTF",
                "NanumGothic.ttc",
                "NanumMyeongjo.ttc",
                "SeoulHangangM.otf",
                "SpoqaHanSansRegular.ttf",
                "THEFACESHOP_INKLIPQUID.otf"};

        Button[] btn = {(Button) findViewById(R.id.milkyway),
                (Button) findViewById(R.id.nanum_gothic),
                (Button) findViewById(R.id.nanum_myeongjo),
                (Button) findViewById(R.id.seoul_hangang),
                (Button) findViewById(R.id.spoqa_han_sans),
                (Button) findViewById(R.id.tfs_inklipquid)
        };

        setBorder(btn);


        for(int i=0; i<fonts.length; i++) {
            Typeface tf = Typeface.createFromAsset(getAssets(), fonts[i]);
            btn[i].setTypeface(tf);
        }

    }

    private void setBorder(Button[] btn) {
        clearBorder(btn);

        String FONT_TYPE = (String)MyAccount.getValue(this, "FONT");

        switch(FONT_TYPE) {
            case "MILKYWAY": {
                btn[0].setBackgroundResource(R.drawable.border);
                break;
            } case "nanum_gothic": {
                btn[1].setBackgroundResource(R.drawable.border);
                break;
            } case "nanum_myeongjo": {
                btn[2].setBackgroundResource(R.drawable.border);
                break;
            } case "seoul_hangang": {
                btn[3].setBackgroundResource(R.drawable.border);
                break;
            } case "spoqa_han_sans": {
                btn[4].setBackgroundResource(R.drawable.border);
                break;
            } default: {
                btn[5].setBackgroundResource(R.drawable.border);
                break;
            }
        }
    }

    private void clearBorder(Button[] btn) {
        for(int i=0; i<btn.length; i++) {
            btn[i].setBackgroundResource(0);
        }
    }

}
