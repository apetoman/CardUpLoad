package com.eju.cy.uploadcardlibrary.entrance;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;


import com.eju.cy.uploadcardlibrary.camera.CameraActivity;
import com.eju.cy.uploadcardlibrary.utils.AppTags;

import java.lang.ref.WeakReference;


/**
 * @ Name: Caochen
 * @ Date: 2020-02-25
 * @ Time: 10:53
 * @ Description： 身份证相机
 */
public class IDCardCamera {

    public final static int TYPE_IDCARD_FRONT = 1;//身份证正面
    public final static int TYPE_IDCARD_BACK = 2;//身份证反面
    public final static int RESULT_CODE = 0X11;//结果码
    public final static int PERMISSION_CODE_FIRST = 0x12;//权限请求码
    public final static String TAKE_TYPE = "take_type";//拍摄类型标记
    public final static String IMAGE_PATH = "image_path";//图片路径标记


    private final WeakReference<Activity> mActivity;
    private final WeakReference<Fragment> mFragment;


    //接口
    public String userId = "";
    public String userToken = "";


    public void initSDK(String userId, String userToken) {
        this.userId = userId;
        this.userToken = userToken;

    }

    public static IDCardCamera create(Activity activity, String userId, String userToken) {

        return new IDCardCamera(activity, userId, userToken);
    }


    public static IDCardCamera create(Fragment fragment, String userId, String userToken) {
        return new IDCardCamera(fragment, userId, userToken);
    }

    private IDCardCamera(Activity activity, String userId, String userToken) {
        this(activity, (Fragment) null, userId, userToken);
        initSDK(userId, userToken);
    }

    private IDCardCamera(Fragment fragment, String userId, String userToken) {
        this(fragment.getActivity(), fragment, userId, userToken);
        initSDK(userId, userToken);
    }

    private IDCardCamera(Activity activity, Fragment fragment, String userId, String userToken) {
        this.mActivity = new WeakReference(activity);
        this.mFragment = new WeakReference(fragment);
        initSDK(userId, userToken);
    }

    /**
     * 打开相机
     *
     * @param IDCardDirection 身份证方向（TYPE_IDCARD_FRONT / TYPE_IDCARD_BACK）
     */
    public void openCamera(int IDCardDirection) {
        Activity activity = this.mActivity.get();
        Fragment fragment = this.mFragment.get();
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(TAKE_TYPE, IDCardDirection);
        intent.putExtra(TAKE_TYPE, IDCardDirection);

        intent.putExtra(AppTags.USER_ID, userId);
        intent.putExtra(AppTags.USER_TOKEN, userToken);
        if (fragment != null) {
            fragment.startActivityForResult(intent, IDCardDirection);
        } else {
            activity.startActivityForResult(intent, IDCardDirection);
        }
    }

    /**
     * 获取图片路径
     *
     * @param data Intent
     * @return 图片路径
     */
    public static String getImagePath(Intent data) {
        if (data != null) {
            return data.getStringExtra(IMAGE_PATH);
        }
        return "";
    }
}

