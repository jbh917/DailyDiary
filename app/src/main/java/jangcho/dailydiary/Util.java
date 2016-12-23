package jangcho.dailydiary;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2016-12-20.
 */

public class Util {

    private static HashMap<String, String> font_style = new HashMap<String, String>();

    static {
        font_style.put("hankyoreh", "hankyoreh.TTF");
        font_style.put("godo", "godo.ttf");
        font_style.put("hanna", "hanna.ttf");
        font_style.put("inklipquid", "inklipquid.otf");
        font_style.put("nanum", "nanum.ttf");
        font_style.put("spoqahansans", "spoqahansans.ttf");
    }


    public static void setGlobalFont(Context context, View view, String font){
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                int len = vg.getChildCount();
                for (int i = 0; i < len; i++) {
                    View v = vg.getChildAt(i);
                    if (v instanceof TextView) {
                        if(font == "godo") {
                            ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "godo.ttf"));
                        } else if(font.equals("hanna")) {
                            ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "hanna.ttf"));
                        } else if(font.equals("inklipquid")) {
                            ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "inklipquid.otf"));
                        } else if(font.equals("nanum")) {
                            ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "nanum.ttf"));
                        } else if(font.equals("spoqahansans")) {
                            ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "spoqahansans.ttf"));
                        } else {
                            ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "hankyoreh.TTF"));
                        }
                    }
                    setGlobalFont(context, v, font);
                }
            }
        } else {

        }

    }

    public static void setFont(TextView tv, String font, AssetManager asset) {
        Typeface tf = Typeface.createFromAsset(asset, font_style.get(font));
        tv.setTypeface(tf);
    }

}
