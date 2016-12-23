package jangcho.dailydiary;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-12-10.
 */

public class SearchActivity extends Activity {

    EditText editText = null;
    public TimeDB mTimeDB = null;
    ArrayList<Data> mData = null;
    DotBaseAdapter mAdapter = null;
    ListView mListView = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        //String FONT_TYPE = (String)MyAccount.getValue(this, "FONT");
        //Util.setGlobalFont(this, getWindow().getDecorView(), FONT_TYPE);

        mData = new ArrayList<Data>();
        mTimeDB = TimeDB.getInstance(this);
        editText = (EditText)findViewById(R.id.search_edit);
        mAdapter = new DotBaseAdapter(this, getAssets(), mData);
        mListView = (ListView) findViewById(R.id.search_list_view);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Intent intent = new Intent(getApplicationContext(), WDailyActivity.class);

                intent.putExtra("year", mData.get(position).tempYear);
                intent.putExtra("month", mData.get(position).tempMonth);
                intent.putExtra("day", mData.get(position).tempDay);
                intent.putExtra("week", mData.get(position).tempWeek);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                }, 250);

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }//입력하기전에

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mData.clear();
                if(s.toString()==""){

                }else {
                    String[] columns = new String[]{"_id", "year", "month", "day", "content", "week","weather"};
                    Cursor c = mTimeDB.query(columns, "content LIKE '%" + s.toString() + "%'", null, null, null, "year ASC, month ASC, day ASC");
                    if (c != null) {
                        while (c.moveToNext()) {
                            Data data = new Data();
                            data.tempYear = c.getInt(1);
                            data.tempMonth = c.getInt(2);
                            data.tempDay = c.getInt(3);
                            String tempDay_str = String.format("%02d", data.tempDay);

                            data.tempContent = "" + data.tempYear + "/" + data.tempMonth + "/" + tempDay_str + "\n" + c.getString(4);
                            data.tempWeek = c.getInt(5);
                            data.weather=c.getInt(6);
                            data.isDB = true;
                            mData.add(data);
                        }
                        c.close();
                    }
                }

                mAdapter.notifyDataSetChanged();


            }//입력되는 텍스트에 변화가 있을때


            @Override
            public void afterTextChanged(Editable s) {

            }//입력이 끝났을때
        });


    }



}
