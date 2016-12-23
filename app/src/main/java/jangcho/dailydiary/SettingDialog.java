package jangcho.dailydiary;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
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
        boolean isAvailable = (boolean) MyAccount.getValue(this, "AVAILABLE");

        if(!isAvailable) {
            if (cnt > 56) {
                MyAccount.setPencilCount(this, cnt - 56);
                MyAccount.setFontAvailable(this, true);


                DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date today = new Date();
                String today_str = sdFormat.format(today);
                MyAccount.setFontStartTime(this, today_str);

                LinearLayout buy_btn = (LinearLayout) findViewById(R.id.buy);
                buy_btn.setVisibility(View.GONE);
            } else {
                Toast.makeText(getApplicationContext(), "NOT ENOUGH PENCIL", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void onClick(View v) {

        boolean isAvailable = (boolean) MyAccount.getValue(this, "AVAILABLE");

        if(isAvailable) {
            Button[] btn = {(Button) findViewById(R.id.hankyoreh),
                    (Button) findViewById(R.id.godo),
                    (Button) findViewById(R.id.hanna),
                    (Button) findViewById(R.id.inklipquid),
                    (Button) findViewById(R.id.nanum),
                    (Button) findViewById(R.id.spoqahansans)
            };

            switch (v.getId()) {
                case R.id.hankyoreh:
                    MyAccount.setFont(this, "hankyoreh");
                    break;

                case R.id.godo:
                    MyAccount.setFont(this, "godo");
                    break;

                case R.id.hanna:
                    MyAccount.setFont(this, "hanna");
                    break;

                case R.id.inklipquid:
                    MyAccount.setFont(this, "inklipquid");
                    break;

                case R.id.nanum:
                    MyAccount.setFont(this, "nanum");
                    break;

                case R.id.spoqahansans:
                    MyAccount.setFont(this, "spoqahansans");
                    break;
            }
            setBorder(btn);
        } else {
            Toast.makeText(getApplicationContext(), "NOT AVAILABLE", Toast.LENGTH_LONG).show();
        }
    }


    private void initDialog() {

        String[] fonts = {"hankyoreh.TTF",
                "godo.ttf",
                "hanna.ttf",
                "inklipquid.otf",
                "nanum.ttf",
                "spoqahansans.ttf"};

        Button[] btn = {(Button) findViewById(R.id.hankyoreh),
                (Button) findViewById(R.id.godo),
                (Button) findViewById(R.id.hanna),
                (Button) findViewById(R.id.inklipquid),
                (Button) findViewById(R.id.nanum),
                (Button) findViewById(R.id.spoqahansans)
        };

        setBorder(btn);


        for(int i=0; i<fonts.length; i++) {
            Typeface tf = Typeface.createFromAsset(getAssets(), fonts[i]);
            btn[i].setTypeface(tf);
        }

        boolean isAvailable = (boolean)MyAccount.getValue(this, "AVAILABLE");

        if(isAvailable) {
            LinearLayout buy_btn = (LinearLayout) findViewById(R.id.buy);
            buy_btn.setVisibility(View.GONE);
        }

    }

    private void setBorder(Button[] btn) {
        clearBorder(btn);

        String FONT_TYPE = (String)MyAccount.getValue(this, "FONT");

        switch(FONT_TYPE) {
            case "godo": {
                btn[1].setBackgroundResource(R.drawable.border);
                break;
            } case "hanna": {
                btn[2].setBackgroundResource(R.drawable.border);
                break;
            } case "inklipquid": {
                btn[3].setBackgroundResource(R.drawable.border);
                break;
            } case "nanum": {
                btn[4].setBackgroundResource(R.drawable.border);
                break;
            } case "spoqahansans": {
                btn[5].setBackgroundResource(R.drawable.border);
                break;
            } default: {
                btn[0].setBackgroundResource(R.drawable.border);
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
