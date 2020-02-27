package com.eju.cy.cardcollectsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.eju.cy.uploadcardlibrary.callback.EjuHomeEventCar
import com.eju.cy.uploadcardlibrary.callback.EjuHomeObserver
import com.eju.cy.uploadcardlibrary.camera.IDCardCamera
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), EjuHomeObserver {

    override fun update(obj: Any?) {
        Log.w("身份证识别结果", obj as String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EjuHomeEventCar.getDefault().register(this);

        tv_go.setOnClickListener {

            IDCardCamera.create(this, "1", "7d9f83cba7896e8c061565ffcb44a3d3d129e084")
                .openCamera(IDCardCamera.TYPE_IDCARD_FRONT)

        }
        tv_goo.setOnClickListener {

            IDCardCamera.create(this, "", "").openCamera(IDCardCamera.TYPE_IDCARD_BACK)


        }
    }

    override fun onDestroy() {
        EjuHomeEventCar.getDefault().unregister(this)
        super.onDestroy()
    }
}
