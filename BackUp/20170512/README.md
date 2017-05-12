
# 项目布局


```
.
├── AndroidManifest.xml                           //Android配置文件
├── assets                                        //bundle默认的访问地址
│   ├── index.android.bundle
│   └── index.android.bundle.meta
├── java
│   └── com
│       └── example
│           └── zhouwenkang
│               └── rnandnative
│                   ├── BaseActivity.java         //完全照抄ReactActivity，在此基础加上了预加载bundle的能力
│                   ├── ExtendBaseActivity.java   //继承BaseActivity的一个Demo
│                   ├── MainActivity.java         //RN初始化得到的MainActivity
│                   ├── MainApplication.java      //实例化RNJavaReactPackage中注册的模块
│                   ├── MyRNActivity.java         //原生集成RN，RN的访问入口代码
│                   ├── RNJavaReactPackage.java   //注册原生提供给RN用的模块
│                   ├── RNToastModule.java        //实现原生模块，提供JS访问的模块、方法名
│                   ├── ReactInfo.java            //配合BaseActivity提供注册名的接口
│                   ├── ReactPreLoader.java       //预加载实现的代码，配合BaseActivity
│                   └── StartNativeActivity.java  //项目启动文件
└── res                                           //资源文件夹
    ├── drawable
    ├── layout
    │   └── activity_main.xml
    ├── mipmap-hdpi
    │   ├── ic_launcher.png
    │   └── ic_launcher_round.png
    ├── mipmap-mdpi
    │   ├── ic_launcher.png
    │   └── ic_launcher_round.png
    ├── mipmap-xhdpi
    │   ├── ic_launcher.png
    │   └── ic_launcher_round.png
    ├── mipmap-xxhdpi
    │   ├── ic_launcher.png
    │   └── ic_launcher_round.png
    ├── mipmap-xxxhdpi
    │   ├── ic_launcher.png
    │   └── ic_launcher_round.png
    └── values
        ├── colors.xml
        ├── strings.xml
        └── styles.xml         
.

```

``` 
	//通过修改要跳转的Activity,实现不同的需求
	Intent intent = new Intent(StartNativeActivity.this,MyRNActivity.class);
```
