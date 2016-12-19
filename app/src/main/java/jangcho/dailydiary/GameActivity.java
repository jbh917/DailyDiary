package jangcho.dailydiary;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class GameActivity extends Activity {
    boolean isStopped = false;
    countThread counter = new countThread();
    TextView number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        setContentView(R.layout.activity_game);

        number = (TextView) findViewById(R.id.counter);


        counter.start();

    }

    protected void onDestroy(){
        super.onDestroy();
        isStopped = true;
    }

    public void onClick(View v) {
        isStopped = true;

    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            try {
                number.setText(msg.what + "");
            } catch(Exception e) {
                Log.e("Exception", "TextView not ready");
            }

            Log.i("msg", msg.what+"");

            //prog.setVisibility(View.INVISIBLE);
        }
    };

    class countThread extends Thread {

        public void run() {
            int i = 1;

            while(true) {

                mHandler.sendEmptyMessage(i);

                try {
                    Thread.sleep(1);
                } catch (Exception e) {}

                if(isStopped) {
                    break;
                }

                if(i > 200) {
                    i = 1;
                } else {
                    i++;
                }
            }
        }
    }
}
