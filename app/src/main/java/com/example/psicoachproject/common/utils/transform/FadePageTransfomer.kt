package com.example.psicoachproject.common.utils.transform

import android.view.View

/**
 * Created by Angelo on 5/12/2021
 */
class FadePageTransfomer : androidx.viewpager.widget.ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        if(position <= -1.0F || position >= 1.0F) {
            view.alpha = 0.0F
        } else if( position == 0.0F ) {
            view.alpha = 1.0F
        } else {
            view.alpha = 1.0F - (Math.abs(position) * 2);
        }
    }
}