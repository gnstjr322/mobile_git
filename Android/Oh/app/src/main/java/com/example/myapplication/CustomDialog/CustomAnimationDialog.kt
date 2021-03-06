package com.example.myapplication.CustomDialog

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.myapplication.R

class CustomAnimationDialog(c: Context) : ProgressDialog(c) { // 통신을 할때 사용자에게 보여지는 로딩화면 구성하는 클래스
    private var imgLogo: ImageView? = null

    init {

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customdialog)
        imgLogo = findViewById<ImageView?>(R.id.img_android)
        val anim = AnimationUtils.loadAnimation(context, R.anim.loading)
        imgLogo?.animation = anim
    }
    class Builder(c: Context){
        var dialog = CustomAnimationDialog(c)

        fun show(): CustomAnimationDialog {
            dialog.show()
            return dialog
        }

        fun dismiss() : CustomAnimationDialog {
            if(dialog != null && dialog.isShowing){
                dialog.dismiss()

            }
            return dialog
        }
    }

}