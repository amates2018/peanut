/*
 * Copyright (c) 2018. Property of Dennis Kwabena Bilson. No unauthorized duplication of this material should be made without prior permission from the developer
 */

package io.peanutsdk.indicators.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.LinearInterpolator;

import android.animation.ValueAnimator;

import java.util.ArrayList;

import io.peanutsdk.indicators.Indicator;

public class BallScaleMultipleIndicator extends Indicator {

    float[] scaleFloats= {1,1,1};
    int[] alphaInts= {255,255,255};

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float circleSpacing=4;
        for (int i = 0; i < 3; i++) {
            paint.setAlpha(alphaInts[i]);
            canvas.scale(scaleFloats[i],scaleFloats[i],getWidth()/2,getHeight()/2);
            canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2-circleSpacing,paint);
        }
    }

    @Override
    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators=new ArrayList<>();
        long[] delays= {0, 200, 400};
        for (int i = 0; i < 3; i++) {
            int index=i;
            ValueAnimator scaleAnim=ValueAnimator.ofFloat(0,1);
            scaleAnim.setInterpolator(new LinearInterpolator());
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            scaleAnim.setStartDelay(delays[i]);

            ValueAnimator alphaAnim=ValueAnimator.ofInt(255,0);
            alphaAnim.setInterpolator(new LinearInterpolator());
            alphaAnim.setDuration(1000);
            alphaAnim.setRepeatCount(-1);
            addUpdateListener(alphaAnim,new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alphaInts[index] = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            scaleAnim.setStartDelay(delays[i]);

            animators.add(scaleAnim);
            animators.add(alphaAnim);
        }
        return animators;
    }

}
