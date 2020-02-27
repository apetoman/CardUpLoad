 
# 身份证信息采集SDK

## Step1

-**引入**:

```
主工程需要以下jar支持，请添加，如已添加请忽略
 implementation rootProject.ext.dependencies["retrofit"]
 implementation rootProject.ext.dependencies["okhttp3"]
 implementation rootProject.ext.dependencies["retrofit-converter-gson"]
 
 //引入SDK
 implementation 'com.eju.cy.uploadcardlibrary:uploadCard:1.0.7' 
```

## Step2 
-**配置混淆**
```
-keep public class com.eju.cy.uploadcardlibrary.**{*;}
-dontwarn com.eju.cy.uploadcardlibrary.**
```
## Step3

-**调用方式如下**

```
class MainActivity : AppCompatActivity(), EjuHomeObserver {

    override fun update(obj: Any?) {
        Log.w("身份证识别结果----", obj as String)
       
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

```
