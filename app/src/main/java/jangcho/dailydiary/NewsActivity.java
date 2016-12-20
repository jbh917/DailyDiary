package jangcho.dailydiary;

/**
 * Created by Administrator on 2016-12-20.
 */
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016-12-10.
 */

public class NewsActivity extends Activity {


    public NewsDB mNewsDB =null;
    Intent intent=null;
    String hyper="";
    String news ="";
    String[] anews;
    String[] ahyper;

    TextView textView1 = null;
    TextView textView2 = null;
    TextView textView3 = null;
    TextView textView4 = null;
    TextView textView5 = null;
    TextView textView6 = null;
    TextView textView7 = null;
    TextView textView8 = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);
        Util.setGlobalFont(this, getWindow().getDecorView());

        textView1 =(TextView)findViewById(R.id.news_1);
        textView2 =(TextView)findViewById(R.id.news_2);
        textView3 =(TextView)findViewById(R.id.news_3);
        textView4 =(TextView)findViewById(R.id.news_4);
        textView5 =(TextView)findViewById(R.id.news_5);
        textView6 =(TextView)findViewById(R.id.news_6);
        textView7 =(TextView)findViewById(R.id.news_7);
        textView8 =(TextView)findViewById(R.id.news_8);

        mNewsDB = NewsDB.getInstance(this);
        intent = getIntent();


        String[] columns = new String[]{"content","hyper"};
        String[] temp = {""+intent.getIntExtra("year",0),""+intent.getIntExtra("month",0),""+intent.getIntExtra("day",0)};

        Cursor c = mNewsDB.query(columns ,"year = ? AND month =? AND day = ? ",temp,null,null,null);

        if(c != null && c.getCount()!=0){

            c.moveToFirst();
            news=c.getString(0);
            hyper = c.getString(1);
        }


        c.close();

        anews = news.split("@");
        ahyper = hyper.split("@");

/*

        textView1.setText(anews[1]);
        textView2.setText(anews[3]);
        textView3.setText(anews[5]);
        textView4.setText(anews[7]);
        textView5.setText(anews[9]);
        textView6.setText(anews[11]);
        textView7.setText(anews[13]);
        textView8.setText(anews[15]);

*/

        textView1.setText(anews[1]);
        textView2.setText(anews[3]);
        textView3.setText(anews[5]);
        textView4.setText(anews[7]);
        textView5.setText(anews[9]);
        textView6.setText(anews[11]);
        textView7.setText(anews[13]);
        textView8.setText(anews[15]);

    }

    public void onClick(View v){

        String a="";

        switch (v.getId()){
            case R.id.butt1: a="http://news.naver.com"+ahyper[1]; break;
            case R.id.butt2:a="http://news.naver.com"+ahyper[3]; break;
            case R.id.butt3:a="http://news.naver.com"+ahyper[5]; break;
            case R.id.butt4:a="http://news.naver.com"+ahyper[7]; break;
            case R.id.butt5:a="http://news.naver.com"+ahyper[9]; break;
            case R.id.butt6:a="http://news.naver.com"+ahyper[11]; break;
            case R.id.butt7:a="http://news.naver.com"+ahyper[13]; break;
            case R.id.butt8:a="http://news.naver.com"+ahyper[15]; break;


        }

        Uri uri = Uri.parse(a);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        startActivity(intent);


    }

}
