
# 项目布局


```
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

```

``` 
	//通过修改要跳转的Activity,实现不同的需求
	Intent intent = new Intent(StartNativeActivity.this,MyRNActivity.class);
```
# 使用



- 新建Android项目

- 在根目录添加配置文件(package.json)

```
{
  "name": "rnandnative",
  "version": "1.0.0",
  "description": "",
  "main": "index.android.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1",
    "start": "node node_modules/react-native/local-cli/cli.js start"
  },
  "author": "",
  "license": "ISC",
  "dependencies": {
    "react": "16.0.0-alpha.6",
    "react-native": "^0.44.0",
    "react-navigation": "git+https://github.com/react-community/react-navigation.git"
  }
}
```

- npm install

- 根目录加入index.android.js

```
import React, { Component } from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    NativeModules,
} from 'react-native';

export default class AwesomeProject extends Component {
    render() {
        return (
            <View style={styles.container}>
                <Text style={styles.welcome}>
                    Welcome to React Native!
                </Text>
                <Text style={styles.instructions}>
                    To get started, edit index.android.js
                </Text>
                <Text style={styles.instructions}>
                    Double tap R on your keyboard to reload,{'\n'}
                    Shake or press menu button for dev menu
                </Text>
                <Text style={styles.instructions} onPress={() => this.showToast()}>
                    点我调用原生
                </Text>
            </View>
        );
    }
    showToast () {
        //调用原生
        NativeModules.RNToastAndroid.show('from native',100);
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
});
```

- 将main文件夹覆盖Android生成的main

- 运行

```
	npm start
	./gradlew installDebug
```
