package com.permissionx.app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeCallBtn.setOnClickListener {
            //不用再去编写复杂的运行时权限相关的代码，只需要调用PermissionX的request()方法，传入当前的Activity和要申请的权限名
            //然后在Lambda表达式中处理权限的申请结果即可
            PermissionX.request(this,Manifest.permission.CALL_PHONE) { allGranted, deniedList ->
                if (allGranted) {
                    //如果allGranted等于true，那么就执行拨打电话操作
                    call()
                } else {
                    //否则使用Toast弹出一条失败提示
                    Toast.makeText(this, " You denied $deniedList", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

}