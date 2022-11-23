package com.permissionx.app

import androidx.fragment.app.FragmentActivity

//定义成单例类是为了让PermissionX中的接口能够更加方便地被调用
object PermissionX {

    private const val TAG = "InvisibleFragment"

    //FragmentActivity是AppCompatActivity的父类
    fun request(activity: FragmentActivity, vararg permissions: String, callback: PermissionCallback) {
        val fragmentManager = activity.supportFragmentManager
        //判断传入的Activity参数中是否已经包含了指定TAG的Fragment
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        //如果已经包含则直接使用该Fragment
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            //否则就创建一个新的InvisibleFragment实例
            val invisibleFragment = InvisibleFragment()
            //并将它添加到Activity中，同时指定一个TAG
            //注意，在添加结束后一定要调用commitNow()方法，而不能调用commit()方法，因为它并不会立即执行添加操作，因而无法保证下一行代码执行时InvisibleFragment已经被添加到Activity中了
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        /*
        有了InvisibleFragment实例之后，接下来只需要调用它的requestNow()方法就能去申请运行时权限了，申请结果会自动回调到callback参数中
        permissions参数在这里实际上是一个数组，对于数组，我们可以遍历它，可以通过下标访问，但是不可以直接将它传递给另外一个接收可变长度参数的方法
        因此，在这里调用requestNow()方法时，在permissions参数的前面加上了一个*，表示将一个数组转换成可变参数传递过去
         */
        fragment.requestNow(callback, *permissions)
    }

}