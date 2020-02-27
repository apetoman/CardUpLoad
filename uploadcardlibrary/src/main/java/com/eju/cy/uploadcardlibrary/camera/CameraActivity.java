package com.eju.cy.uploadcardlibrary.camera;

import android.Manifest;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.eju.cy.uploadcardlibrary.R;
import com.eju.cy.uploadcardlibrary.callback.EjuHomeEventCar;
import com.eju.cy.uploadcardlibrary.cropper.CropListener;
import com.eju.cy.uploadcardlibrary.dto.UploadCardDto;
import com.eju.cy.uploadcardlibrary.entrance.IDCardCamera;
import com.eju.cy.uploadcardlibrary.global.Constant;
import com.eju.cy.uploadcardlibrary.net.AppNetInterface;
import com.eju.cy.uploadcardlibrary.net.RetrofitManager;
import com.eju.cy.uploadcardlibrary.utils.AppTags;
import com.eju.cy.uploadcardlibrary.utils.CommonUtils;
import com.eju.cy.uploadcardlibrary.utils.FileUtils;
import com.eju.cy.uploadcardlibrary.utils.ImageUtils;
import com.eju.cy.uploadcardlibrary.utils.JsonUtils;
import com.eju.cy.uploadcardlibrary.utils.ParameterUtils;
import com.eju.cy.uploadcardlibrary.utils.PermissionUtils;
import com.eju.cy.uploadcardlibrary.utils.ScreenUtils;
import com.eju.cy.uploadcardlibrary.view.CameraPreview;
import com.eju.cy.uploadcardlibrary.view.CropImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * @ Name: Caochen
 * @ Date: 2020-02-25
 * @ Time: 10:53
 * @ Description： 拍照界面
 */
public class CameraActivity extends Activity implements View.OnClickListener {

    private CropImageView mCropImageView;
    private Bitmap mCropBitmap;
    private CameraPreview mCameraPreview;
    private View mLlCameraCropContainer;
    private ImageView mIvCameraCrop;
    private ImageView mIvCameraFlash;
    private View mLlCameraOption;
    private View mLlCameraResult;
    private TextView mViewCameraCropBottom;
    private FrameLayout mFlCameraOption;
    private View mViewCameraCropLeft;
    private RelativeLayout rl_av_load;
    private TextView mTvNext;


    private int mType;//拍摄类型
    private boolean isToast = true;//是否弹吐司，为了保证for循环只弹一次

    private String userId = "";
    private String userToken = "";
    private String frontImg = "";
    private String backImg = "";
    UploadCardDto returnDto = new UploadCardDto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*动态请求需要的权限*/
        boolean checkPermissionFirst = PermissionUtils.checkPermissionFirst(this, IDCardCamera.PERMISSION_CODE_FIRST,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
        if (checkPermissionFirst) {
            init();
        }
    }

    /**
     * 处理请求权限的响应
     *
     * @param requestCode  请求码
     * @param permissions  权限数组
     * @param grantResults 请求权限结果数组
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isPermissions = true;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isPermissions = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) { //用户选择了"不再询问"
                    if (isToast) {
                        Toast.makeText(this, "请手动打开该应用需要的权限", Toast.LENGTH_SHORT).show();
                        isToast = false;
                    }
                }
            }
        }
        isToast = true;
        if (isPermissions) {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "允许所有权限");
            init();
        } else {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "有权限不允许");
            finish();
        }
    }

    private void init() {
        setContentView(R.layout.activity_camera);
        mType = getIntent().getIntExtra(IDCardCamera.TAKE_TYPE, 0);
        this.userId = getIntent().getStringExtra(AppTags.USER_ID);
        this.userToken = getIntent().getStringExtra(AppTags.USER_TOKEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        initView();
        initListener();
    }

    private void initView() {

        mCameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
        mLlCameraCropContainer = findViewById(R.id.ll_camera_crop_container);
        mIvCameraCrop = (ImageView) findViewById(R.id.iv_camera_crop);
        mIvCameraFlash = (ImageView) findViewById(R.id.iv_camera_flash);
        mLlCameraOption = findViewById(R.id.ll_camera_option);
        mLlCameraResult = findViewById(R.id.ll_camera_result);
        mCropImageView = findViewById(R.id.crop_image_view);
        mViewCameraCropBottom = (TextView) findViewById(R.id.view_camera_crop_bottom);
        mFlCameraOption = (FrameLayout) findViewById(R.id.fl_camera_option);
        mViewCameraCropLeft = findViewById(R.id.view_camera_crop_left);

        mTvNext = findViewById(R.id.tv_next);
        rl_av_load = findViewById(R.id.rl_av_load);

        float screenMinSize = Math.min(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this));
        float screenMaxSize = Math.max(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this));
        float height = (int) (screenMinSize * 0.75);
        float width = (int) (height * 75.0f / 47.0f);
        //获取底部"操作区域"的宽度
        float flCameraOptionWidth = (screenMaxSize - width) / 2;
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
        LinearLayout.LayoutParams cameraOptionParams = new LinearLayout.LayoutParams((int) flCameraOptionWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        mLlCameraCropContainer.setLayoutParams(containerParams);
        mIvCameraCrop.setLayoutParams(cropParams);
        //获取"相机裁剪区域"的宽度来动态设置底部"操作区域"的宽度，使"相机裁剪区域"居中
        mFlCameraOption.setLayoutParams(cameraOptionParams);

        switch (mType) {
            case IDCardCamera.TYPE_IDCARD_FRONT:
                mIvCameraCrop.setImageResource(R.mipmap.camera_idcard_front);
                break;
            case IDCardCamera.TYPE_IDCARD_BACK:
                mIvCameraCrop.setImageResource(R.mipmap.camera_idcard_back);
                break;
        }

        /*增加0.5秒过渡界面，解决个别手机首次申请权限导致预览界面启动慢的问题*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCameraPreview.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 500);
    }

    private void initListener() {
        mCameraPreview.setOnClickListener(this);
        mIvCameraFlash.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
        findViewById(R.id.iv_camera_close).setOnClickListener(this);
        findViewById(R.id.iv_camera_take).setOnClickListener(this);
        findViewById(R.id.iv_camera_result_ok).setOnClickListener(this);
        findViewById(R.id.iv_camera_result_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.camera_preview) {
            mCameraPreview.focus();
        } else if (id == R.id.iv_camera_close) {
            finish();
        } else if (id == R.id.iv_camera_take) {
            if (!CommonUtils.isFastClick()) {
                takePhoto();
            }
        } else if (id == R.id.iv_camera_flash) {
            if (CameraUtils.hasFlash(this)) {
                boolean isFlashOn = mCameraPreview.switchFlashLight();
                mIvCameraFlash.setImageResource(isFlashOn ? R.mipmap.camera_flash_on : R.mipmap.camera_flash_off);
            } else {
                Toast.makeText(this, R.string.no_flash, Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.iv_camera_result_ok) {
            confirm();


        } else if (id == R.id.iv_camera_result_cancel) {
            mCameraPreview.setEnabled(true);
            mCameraPreview.addCallback();
            mCameraPreview.startPreview();
            mIvCameraFlash.setImageResource(R.mipmap.camera_flash_off);
            setTakePhotoLayout();
        } else if (id == R.id.tv_next) {
//            mType = IDCardCamera.TYPE_IDCARD_BACK;
//
//            switch (mType) {
//                case IDCardCamera.TYPE_IDCARD_FRONT:
//                    mIvCameraCrop.setImageResource(R.mipmap.camera_idcard_front);
//                    break;
//                case IDCardCamera.TYPE_IDCARD_BACK:
//                    mIvCameraCrop.setImageResource(R.mipmap.camera_idcard_back);
//                    mTvNext.setText("完成");
//                    break;
//            }


        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        mCameraPreview.setEnabled(false);
        CameraUtils.getCamera().setOneShotPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(final byte[] bytes, Camera camera) {
                final Camera.Size size = camera.getParameters().getPreviewSize(); //获取预览大小
                camera.stopPreview();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final int w = size.width;
                        final int h = size.height;
                        Bitmap bitmap = ImageUtils.getBitmapFromByte(bytes, w, h);
                        cropImage(bitmap);
                    }
                }).start();
            }
        });
    }

    /**
     * 裁剪图片
     */
    private void cropImage(Bitmap bitmap) {
        /*计算扫描框的坐标点*/
        float left = mViewCameraCropLeft.getWidth();
        float top = mIvCameraCrop.getTop();
        float right = mIvCameraCrop.getRight() + left;
        float bottom = mIvCameraCrop.getBottom();

        /*计算扫描框坐标点占原图坐标点的比例*/
        float leftProportion = left / mCameraPreview.getWidth();
        float topProportion = top / mCameraPreview.getHeight();
        float rightProportion = right / mCameraPreview.getWidth();
        float bottomProportion = bottom / mCameraPreview.getBottom();

        /*自动裁剪*/
        mCropBitmap = Bitmap.createBitmap(bitmap,
                (int) (leftProportion * (float) bitmap.getWidth()),
                (int) (topProportion * (float) bitmap.getHeight()),
                (int) ((rightProportion - leftProportion) * (float) bitmap.getWidth()),
                (int) ((bottomProportion - topProportion) * (float) bitmap.getHeight()));

        /*设置成手动裁剪模式*/
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //将手动裁剪区域设置成与扫描框一样大
                mCropImageView.setLayoutParams(new LinearLayout.LayoutParams(mIvCameraCrop.getWidth(), mIvCameraCrop.getHeight()));
                setCropLayout();
                mCropImageView.setImageBitmap(mCropBitmap);
            }
        });
    }

    /**
     * 设置裁剪布局
     */
    private void setCropLayout() {
        mIvCameraCrop.setVisibility(View.GONE);
        mCameraPreview.setVisibility(View.GONE);
        mLlCameraOption.setVisibility(View.GONE);
        mCropImageView.setVisibility(View.VISIBLE);
        mLlCameraResult.setVisibility(View.VISIBLE);
        mViewCameraCropBottom.setText("");
    }

    /**
     * 设置拍照布局
     */
    private void setTakePhotoLayout() {
        mIvCameraCrop.setVisibility(View.VISIBLE);
        mCameraPreview.setVisibility(View.VISIBLE);
        mLlCameraOption.setVisibility(View.VISIBLE);
        mCropImageView.setVisibility(View.GONE);
        mLlCameraResult.setVisibility(View.GONE);
        mViewCameraCropBottom.setText(getString(R.string.touch_to_focus));

        mCameraPreview.focus();
    }


    private void showFrontOrBack(boolean type) {


        //拿到图片上传成功则反面，失败则正面
        mType = IDCardCamera.TYPE_IDCARD_BACK;
        mCameraPreview.setEnabled(true);
        mCameraPreview.addCallback();
        mCameraPreview.startPreview();
        mIvCameraFlash.setImageResource(R.mipmap.camera_flash_off);
        setTakePhotoLayout();

        if (type) {
            mIvCameraCrop.setImageResource(R.mipmap.camera_idcard_front);
            setTakePhotoLayout();
        } else {
            mIvCameraCrop.setImageResource(R.mipmap.camera_idcard_back);

        }


    }


    /**
     * 点击确认，返回图片路径
     */
    private void confirm() {
        /*手动裁剪图片*/
        mCropImageView.crop(new CropListener() {
            @Override
            public void onFinish(Bitmap bitmap) {
                if (bitmap == null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.crop_fail), Toast.LENGTH_SHORT).show();
                    finish();
                }

                /*保存图片到sdcard并返回图片路径*/
                if (FileUtils.createOrExistsDir(Constant.DIR_ROOT)) {
                    StringBuffer buffer = new StringBuffer();
                    String imagePath = "";
                    if (mType == IDCardCamera.TYPE_IDCARD_FRONT) {

                        imagePath = buffer.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append("idCardFrontCrop.jpg").toString();
                        if (ImageUtils.save(bitmap, imagePath, Bitmap.CompressFormat.JPEG)) {
                            Log.w("imagePath-------", imagePath);
                            String base64Img = imageToBase64(imagePath);
                            Log.w("base64Img-------", base64Img);
                            if (null != base64Img && base64Img.length() > 10) {
                                requestNet(base64Img);
                            } else {
                                Toast.makeText(CameraActivity.this, "请稍后再试", Toast.LENGTH_LONG).show();
                            }

                        }


                    } else if (mType == IDCardCamera.TYPE_IDCARD_BACK) {
                        Log.w("方面上传", "方面上传");
                        imagePath = buffer.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append("idCardBackCrop.jpg").toString();

                        if (ImageUtils.save(bitmap, imagePath, Bitmap.CompressFormat.JPEG)) {
                            Log.w("imagePath-------", imagePath);
                            String base64Img = imageToBase64(imagePath);
                            Log.w("base64Img-------", base64Img);
                            if (null != base64Img && base64Img.length() > 10) {
                                requestNet(base64Img);
                            } else {
                                Toast.makeText(CameraActivity.this, "请稍后再试", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Log.w("保存图片失败", "保存图片失败");
                        }

                    }


                }
            }
        }, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCameraPreview != null) {
            mCameraPreview.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCameraPreview != null) {
            mCameraPreview.onStop();
        }
    }


    public void setType(int type) {
        mType = type;
    }

    private void requestNet(final String base64Img) {
        rl_av_load.setVisibility(View.VISIBLE);
        AppNetInterface httpInterface = RetrofitManager.getDefault().provideClientApi(this, userId, userToken);

        String type = "front";
        if (mType == IDCardCamera.TYPE_IDCARD_FRONT) {
            type = "front";
        } else {
            type = "back";
        }

        Log.w("TYPE--------", type);
        final String finalType = type;
        httpInterface.uploadCardImg(
                ParameterUtils.prepareFormData(base64Img),
                ParameterUtils.prepareFormData(type),
                ParameterUtils.prepareFormData("true"),
                ParameterUtils.prepareFormData("false"),
                ParameterUtils.prepareFormData("false"),
                ParameterUtils.prepareFormData("true")
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UploadCardDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UploadCardDto uploadCardDto) {
                        rl_av_load.setVisibility(View.GONE);
                        Log.w("----", "onNext");

                        if (null != uploadCardDto && "10000".equals(uploadCardDto.getCode())) {
                            //如果正面身份证上传且识别成功
                            if (finalType.equals("front")) {

                                returnDto = uploadCardDto;
                                //拿到图片上传成功则反面，失败则正面
                                mType = IDCardCamera.TYPE_IDCARD_BACK;
                                mCameraPreview.setEnabled(true);
                                mCameraPreview.addCallback();

                                mCameraPreview.startPreview();
                                mIvCameraFlash.setImageResource(R.mipmap.camera_flash_off);
                                setTakePhotoLayout();

                                mIvCameraCrop.setImageResource(R.mipmap.camera_idcard_back);
                                setType(IDCardCamera.TYPE_IDCARD_BACK);

                                frontImg = base64Img;

                            } else {
                                backImg = base64Img;
                                returnDto.getData().setIssueOffice(uploadCardDto.getData().getIssueOffice());
                                returnDto.getData().setExpiryDate(uploadCardDto.getData().getExpiryDate());
                                returnDto.getData().setIssueDate(uploadCardDto.getData().getIssueDate());
                                //正方面照片
                                returnDto.getData().setFrontImgString(frontImg);
                                returnDto.getData().setBackImgString(backImg);


                                String json = JsonUtils.toJson(returnDto);

                                //观察者
                                EjuHomeEventCar.getDefault().post(json);
                                finish();
                            }


                        } else {
                            Toast.makeText(CameraActivity.this, uploadCardDto.getMsg(), Toast.LENGTH_LONG).show();
                            rl_av_load.setVisibility(View.GONE);
                            //正面失败
                            if ("front".equals(finalType)) {
                                Log.w("正面失败", "正面失败");
                                mCameraPreview.setEnabled(true);
                                mCameraPreview.addCallback();

                                mCameraPreview.startPreview();
                                mIvCameraFlash.setImageResource(R.mipmap.camera_flash_off);
                                setTakePhotoLayout();
                                mIvCameraCrop.setImageResource(R.mipmap.camera_idcard_front);
                            } else {
                                //反面失败
                                Log.w("反面失败", "反面失败");
                                mCameraPreview.setEnabled(true);
                                mCameraPreview.addCallback();

                                mCameraPreview.startPreview();
                                mIvCameraFlash.setImageResource(R.mipmap.camera_flash_off);
                                setTakePhotoLayout();
                                mIvCameraCrop.setImageResource(R.mipmap.camera_idcard_back);
                            }

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        rl_av_load.setVisibility(View.GONE);
                        Toast.makeText(CameraActivity.this, "请稍后再试", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.NO_CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

}