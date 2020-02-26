package com.eju.cy.cardcollectsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eju.cy.uploadcardlibrary.camera.IDCardCamera
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tv_go.setOnClickListener {

            IDCardCamera.create(this).openCamera(IDCardCamera.TYPE_IDCARD_FRONT)

        }
        tv_goo.setOnClickListener {

            IDCardCamera.create(this).openCamera(IDCardCamera.TYPE_IDCARD_BACK)



        }
    }
}
