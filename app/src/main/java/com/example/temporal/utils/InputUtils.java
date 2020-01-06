package com.example.temporal.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.example.temporal.testing.AssertionException;

public class InputUtils {


    public static void showKeyboardForView(@NonNull final Context context, final View view){

        if(view.requestFocus()){

            final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (inputMethodManager == null){
                throw new AssertionException("inputMethodManager was null");
            }

            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }

    }


}
