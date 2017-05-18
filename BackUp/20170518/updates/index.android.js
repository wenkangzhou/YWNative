/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    Image,
    NativeModules,
    DeviceEventEmitter,
} from 'react-native';

export default class AwesomeProject extends Component {
    constructor(props){
        super(props);
        this.state = {
           content:null,showModule:false
        };
        DeviceEventEmitter.addListener("test", (result) => {
            let mainComponent = require(result.name);
            this.setState({
                content:mainComponent,
                showModule:true
            })
        });
    }
    render() {
        let _content = null;
        if(this.state.content){
           _content = React.createElement(this.state.content,this.props);
           return _content;
        }else{
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
                    <Text style={styles.instructions} onPress={() => this.updateBundle()}>
                        点我更新bundle
                    </Text>
                    <Text style={styles.instructions} onPress={() => this.goNine()}>
                        点我加载页面9999
                    </Text>
                    <Text style={styles.instructions} onPress={() => this.goEight()}>
                        点我加载页面8888
                    </Text>
                    <Image 
                        source={require('./img/music_play.png')}
                        style={{width:92,height:92}}
                        />
                </View>
            );
        }
        
    }
    updateBundle () {
        NativeModules.updateBundle.check("5.0.0");
    }
    showToast () {
        //调用原生
        NativeModules.RNToastAndroid.show('from native',100);
    }
    goNine () {
        NativeModules.BundleLoad.goPage(9999);
    }
    goEight () {
        NativeModules.BundleLoad.goPage(8888);
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

AppRegistry.registerComponent('rnandnative', () => AwesomeProject);
