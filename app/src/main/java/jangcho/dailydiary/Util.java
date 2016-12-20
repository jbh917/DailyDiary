package jangcho.dailydiary;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2016-12-20.
 */

public class Util {

    public static void setGlobalFont(Context context, View view, String font){
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                int len = vg.getChildCount();
                for (int i = 0; i < len; i++) {
                    View v = vg.getChildAt(i);
                    if (v instanceof TextView) {
                        if(font == "MILKYWAY") {
                            ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "MILKYWAY.TTF"));
                        } else if(font.equals("nanum_gothic")) {
                            ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "NanumGothic.ttc"));
                        } else if(font.equals("nanum_myeongjo")) {
                            ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "NanumMyeongjo.ttc"));
                        } else if(font.equals("seoul_hangang")) {
                            ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "SeoulHangangM.otf"));
                        } else if(font.equals("spoqa_han_sans")) {
                            ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "SpoqaHanSansRegular.ttf"));
                        } else {
                            ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "THEFACESHOP_INKLIPQUID.otf"));
                        }
                    }
                    setGlobalFont(context, v, font);
                }
            }
        } else {

        }

    }

}
