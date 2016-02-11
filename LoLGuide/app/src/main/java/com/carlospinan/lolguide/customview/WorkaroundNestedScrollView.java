package com.carlospinan.lolguide.customview;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Carlos Piñan
 * @source http://stackoverflow.com/questions/31829976/onclick-method-not-working-properly-after-nestedscrollview-scrolled
 */
public class WorkaroundNestedScrollView extends NestedScrollView {

    public WorkaroundNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // Explicitly call computeScroll() to make the Scroller compute itself
            computeScroll();
        }
        return super.onInterceptTouchEvent(ev);
    }
}
