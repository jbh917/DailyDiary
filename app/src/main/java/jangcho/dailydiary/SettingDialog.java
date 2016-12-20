package jangcho.dailydiary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    }

    public void onClick(View v) {
        switch(v.getId()) {
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
}