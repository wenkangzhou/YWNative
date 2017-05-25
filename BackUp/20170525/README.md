# 目录结构

```
index.android.js                 //Android程序入口
js                               //RN js代码，理论上IOS和Android共用一套
├── actions                      //理论上一个页面对应一个actions
│   ├── actionTypes.js           //存放所有action的名称
│   └── requestIndexData.js      //action是和页面直接打交道的，用于提供事件的要素给reducer，它本身不修改state。
├── api                          //api相关的管理
│   └── api.js                   //所有的请求地址
├── app.js                       //合并IOS和Android的入口。用Provider作为state的顶层分发，cover到所有组件
├── components                   //公共的组件写在这个文件夹
│   └── backPageComponent.js     //用于监听安卓回退的组件，理论上二级页面都应继承
├── page                         //这里编写我们的页面
│   ├── index.js                 //首页
│   └── rank.js                  //排行榜页
├── persistence                  //对请求做了一个本地缓存（待完善）
│   └── indexLocalData.js        //首页的本地缓存处理
├── reducers                     //匹配action类型，修改state值
│   ├── index.js                 //把所有的子reducers合并
│   └── indexDataState.js        //首页的reducers
├── res                          //存放资源文件
│   └── logo.png
├── store                        //负责存储状态，是action和reducers的桥梁
│   └── index.js                 
├── test.js                      //测试页面，忽略
└── utils                        //公共方法写在这里
    ├── fetchUtil.js             //对fetch封装（待完善）
    ├── formatUtil.js            //提供日期、字符转换
    └── toastUtil.js             //提供简易弹层

```