// pages/index/index.js

var util = require("../../utils/util")
var app = getApp();
var globalData = app.globalData;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    latitude: 30.657420,
    longitude: 104.065840,
    scale: 14,
    markers: [],
    LoginStatus:false
  }
  ,

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var _this = this;


    wx.getLocation({
      type: 'gcj02',
      success: function (res) {
        _this.setData({
          longitude: res.longitude,
          latitude: res.latitude
        })
      },
    })

    wx.getSystemInfo({
      success: function (res) {
        _this.setData({
          controls: [{
            id: 1, // 给控件定义唯一id
            iconPath: '/images/map/location.png', // 控件图标
            position: { // 控件位置
              left: 20, // 单位px
              top: res.windowHeight - 80, // 根据设备高度设置top值，可以做到在不同设备上效果一致
              width: 50, // 控件宽度/px
              height: 50 // 控件高度/px
            },
            clickable: true // 是否可点击，默认为true,可点击
          },
          {
            id: 2, // 给控件定义唯一id
            iconPath: '/images/map/person.png', // 控件图标
            position: { // 控件位置
              left: res.windowWidth - 70, // 单位px
              top: res.windowHeight - 80, // 根据设备高度设置top值，可以做到在不同设备上效果一致
              width: 50, // 控件宽度/px
              height: 50 // 控件高度/px
            },
            clickable: true // 是否可点击，默认为true,可点击
          }]
        })
      },
    })

    wx.request({
      url: 'https://www.daddyok.com/EvaluatingServer/NurseryInfo',
      data: {
        queryData: 'mapData'
      },
      method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {
        'content-type': ' application/json; charset=UTF-8'
      },
      success: function (res) {
        var dataArray = res.data.nurseriesInfos
        console.log(res)
        var markers = new Array()
        for (var i = 0; i < dataArray.length; i++) {
          var dic = dataArray[i]
          var Info =
            {
              iconPath: "",
              id: 0,
              latitude: 0,
              longitude: 0,
              width: 33,
              height: 33,
              title: "",
              address: "",
              phoneNum: "",
              link: "",
              uniqueId: "",
              callout: {}
            }
          Info.id = i;
          Info.latitude = dic.latitude
          Info.longitude = dic.langitude
          Info.title = dic.market_name
          Info.uniqueId = dic.uniqueId
          Info.callout = {
            content: dic.market_name,
            color: "#000000",
            fontSize: "16",
            borderRadius: "5",
            bgColor: "#FF8C00",
            padding: "5",
            display: "ALWAYS"
          }
          Info.address = dic.market_address
          Info.phoneNum = dic.market_phone_num
          Info.link = dic.advertorial_link
          if (dic.detect_status == "Y") {
            Info.iconPath = "/images/map/marker.png"
          }
          else {
            Info.iconPath = "/images/map/n_statusIcon.png"
          }
          markers.push(Info)
        }
        //console.log(markers)
        _this.setData({
          markers: markers
        })
      },
      fail: function (res) {
        // fail
        console.log("----------------faild-----------:" + res.data.error)
      },
      complete: function (res) {
        // complete
        console.log("-----------------------complete------------------")
      }
    })

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.mapCtx = wx.createMapContext("NurseriesMap");
    this.movetoPosition();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  movetoPosition: function () {
    this.mapCtx.moveToLocation();
  },

  controltap: function (e) {
    console.log(e.controlId)
    switch (e.controlId) {
      case 1: this.movetoPosition()
        break;
      case 2: this.navToMyInfo()
        break;
      default: break;
    }
  },
  navToMyInfo: function () {
    wx.navigateTo({
      url: '../myInfo/myInfo',
    })
  },
  markerstap: function (e) {
    let that = this;
    let url = '../myInfo/myInfo';
    console.log('-----------------------------markerName:' + e.markerId)
    wx.getStorage({
      key: 'userInfo',
      success: function(res) {
        if (res.data.status) {
      
          let mId = e.markerId;
          let marker = that.data.markers[mId];
          let name = marker.title;
          let address = marker.address;
          let phoneNum = marker.phoneNum;
          let link = marker.link;
          let lat = marker.latitude;
          let lng = marker.longitude;
          let uId = marker.uniqueId;
          console.log("marketid:" + uId);
          url = '../detail/index?name=' + name
            + '&phoneNum=' + phoneNum
            + '&address=' + address
            + '&link=' + link
            + '&lat=' + lat
            + '&lng=' + lng
            + '&uId=' + uId;
        }
        console.log("url:" + url);
        wx.navigateTo({
          url: url
        })
      },
      fail:function(res)
      {
        wx.navigateTo({
          url: url
        })
      }
    })
  },

})