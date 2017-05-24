package com.baituoyitong.kun.baituoyitong.mainpackage.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    //是对toast的封装,不会再重复创建toast


        private static Toast toast;

        public static void showToast(Context context,
                                     String content) {
            if (toast == null) {
                toast = Toast.makeText(context,
                        content,
                        Toast.LENGTH_SHORT);
            } else {
                toast.setText(content);
            }
            toast.show();
        }


}
