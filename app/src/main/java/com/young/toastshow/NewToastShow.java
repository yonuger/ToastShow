package com.young.toastshow;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author: young
 * date:17/9/1  15:29
 */

public class NewToastShow {


    public static final double TOAST_LONG = 3;
    public static final double TOAST_SHORT = 1.5;

    private double time;
    private static Handler handler;
    private Timer showTimer;
    private Timer cancelTimer;

    private Toast toast;

    private int width;
    private View shadowBar;

    private void setView(Context context, String text, String message) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.toast_view, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView msg = (TextView) view.findViewById(R.id.message);
        shadowBar = view.findViewById(R.id.shadow_bar);

        title.setText(text);
        msg.setText(message);

        width=View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);

        int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(width,height);
        width=view.getMeasuredWidth();

        int margin = (int) context.getResources().getDimension(R.dimen.toast_radius);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(width-margin*2, 3);
        layoutParams1.setMargins(margin, 0, 0, 0);
        shadowBar.setLayoutParams(layoutParams1);


        width -= margin*2;

        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
    }

    private NewToastShow(Context context, String text, String message){
        showTimer = new Timer();
        cancelTimer = new Timer();
        setView(context, text, message);
    }

    public void setTime(double time) {
        this.time = time;
    }


    public static NewToastShow makeText(Context context, String text, String message, double time){
        NewToastShow newToastShow= new NewToastShow(context, text, message);
        newToastShow.setTime(time);
        handler = new Handler(context.getMainLooper());
        return newToastShow;
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        toast.setGravity(gravity, xOffset, yOffset);
    }


    public void show(){
        showTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new ShowRunnable());
            }
        }, 0, (long)(time * 1000));
        cancelTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new CancelRunnable());
            }
        }, (long)(time * 1000));
    }

    private class CancelRunnable implements Runnable{
        @Override
        public void run() {
            showTimer.cancel();
            toast.cancel();
        }
    }

    private class ShowRunnable implements Runnable{
        @Override
        public void run() {
            final ValueAnimator va = ValueAnimator.ofInt(0, width);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    //width
                    int w = (Integer) valueAnimator.getAnimatedValue();
                    //动态更新view的高度
                    shadowBar.getLayoutParams().width = w;
                    shadowBar.requestLayout();

                }
            });
            if( time == TOAST_LONG )
                va.setDuration(2000);
            else
                va.setDuration(500);
            //开始动画
            va.start();
            toast.show();
        }
    }

}
