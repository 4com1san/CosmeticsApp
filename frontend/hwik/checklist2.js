/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import 'react-native-gesture-handler';
import React, { Component } from 'react';
import { View,Text,StyleSheet, TouchableOpacity } from 'react-native';



class Check2 extends Component {
state = {
    list: '',

  
}


Checklist_1 = () => (
    this.state.checkBoolean1 ?
    <Text style = {styles.TrueTextView}>1. 세안 후 아무것도 바르지 않아도 그다지 당기는 느낌이 없다.</Text> 
    :
    <Text style = {styles.FlaseTextbiew}>1. 세안 후 아무것도 바르지 않아도 그다지 당기는 느낌이 없다. </Text>
)

    render(){
        return(
            <View style = {styles.mainView}>
                    <TouchableOpacity
                        onPress = {()=>this.changeBoolean1(this.state.checkBoolean1)} >
                      {this.Checklist_1()}     
                    </TouchableOpacity>

                    <TouchableOpacity
                    style = {styles.submit}
                    onPress = {()=>{
                        this.props.navigation.navigate('Main')
                    }}
                >
                    <Text>제출</Text>
                </TouchableOpacity>
            </View>
        )
    } 
}

const styles= StyleSheet.create({
    mainView: {
        paddingTop: 50,
        width:'100%',
        left : 10
    },
    TrueTextView : {
        paddingTop:10,
        fontSize : 13.5,
        //fontStyle : ,
       color: "#abadaa"
 },
 FlaseTextbiew:{
    paddingTop:10,
    fontSize : 13.5,
    //fontStyle : ,
   color: "#000000"
 },
 submit:{
    width: '50%',
    backgroundColor: '#f5eccb',
    borderColor:'black',
    marginTop: 30,
    left: 80,
    borderWidth: 0.5,
    padding: 8,
    alignItems: 'center',
    justifyContent: 'center'
}
})  

export default Check2;