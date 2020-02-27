package com.eju.cy.cardcollectsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

import com.eju.cy.uploadcardlibrary.callback.EjuHomeEventCar
import com.eju.cy.uploadcardlibrary.callback.EjuHomeObserver
import com.eju.cy.uploadcardlibrary.entrance.IDCardCamera
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), EjuHomeObserver {

    override fun update(obj: Any?) {
        Log.w("身份证识别结果----", obj as String)
        Toast.makeText(this,"身份证识别结果",Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EjuHomeEventCar.getDefault().register(this);


        tv_go.setOnClickListener {
            //参数依次为  上下文，userId   userToken
            IDCardCamera.create(this, "1", "7d9f83cba7896e8c061565ffcb44a3d3d129e084")
                .openCamera(IDCardCamera.TYPE_IDCARD_FRONT)

        }

    }

    override fun onDestroy() {
        EjuHomeEventCar.getDefault().unregister(this)
        super.onDestroy()
    }
}
