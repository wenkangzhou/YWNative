package com.example.zhouwenkang.rnandnative;



public class ExtendBaseActivity extends BaseActivity {

    public static final ReactInfo reactInfo = new ReactInfo("rnandnative", null);

    @Override
    protected String getMainComponentName() {
        return reactInfo.getMainComponentName();
    }

    @Override
    public ReactInfo getReactInfo() {
        return reactInfo;
    }
}