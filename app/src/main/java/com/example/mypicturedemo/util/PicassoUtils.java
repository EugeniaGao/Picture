package com.example.mypicturedemo.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mypicturedemo.R;

/**
 * @Module :
 * @Comments : PicassoUtils
 * @Author : KnightOneAdmin
 * @CreateDate : 16/9/11
 * @ModifiedBy : KnightOneAdmin
 * @ModifiedDate: 下午3:36
 * @Modified : PicassoUtils
 */
public class PicassoUtils {
    /**
     * 设置图片
     * @param context
     * @param url
     * @param img
     */
    public static void setAvatarImg(Context context, String url, ImageView img) {

        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .fitCenter()
                .into(img);

    }
}
