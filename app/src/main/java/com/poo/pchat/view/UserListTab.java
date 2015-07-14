package com.poo.pchat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * This class represents the UserListTab.
 *
 * @author Felipe Porge Xavier - http://www.felipeporge.com
 */
public class UserListTab extends ListView {

    private boolean isShowing = false;

    /**
     * Constructor method.
     * @param context - The context.
     */
    public UserListTab(Context context) {
        super(context);
    }

    /**
     * Constructor method.
     * @param context - The context.
     * @param attrs - The attributes.
     */
    public UserListTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor method.
     * @param context - The context.
     * @param attrs - The attributes.
     * @param defStyleAttr - The style attributes.
     */
    public UserListTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
