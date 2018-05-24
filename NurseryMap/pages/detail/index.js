
const app = getApp()

Page({
  data: {
    flag: 0,
    name: "",
    address: "",
    link: "",
    lat: "",
    lng: "",
    phone: "",
    marketId:"",
    imgUrls: [
      'http://img02.tooopen.com/images/20150928/tooopen_sy_143912755726.jpg',
      'http://img06.tooopen.com/images/20160818/tooopen_sy_175866434296.jpg',
      'http://img06.tooopen.com/images/20160818/tooopen_sy_175833047715.jpg',
      'http://img02.tooopen.com/images/20150928/tooopen_sy_143912755726.jpg',
      'http://img06.tooopen.com/images/20160818/tooopen_sy_175866434296.jpg',
      'http://img06.tooopen.com/images/20160818/tooopen_sy_175833047715.jpg'
    ]

  },
  onLoad: function (query) {
    app.editTabBar();
    this.setData({
      name: query.name,
      address: query.address,
      link: query.link,
      lat: query.lat,
      lng: query.lng,
      phone: query.phoneNum,
      marketId:query.uId
    })
    this.GetLevel()
 
  },
  navToPos: function (e) {
    var lat = e.currentTarget.dataset.lat * 1
    var lng = e.currentTarget.dataset.lng * 1
    console.log('lat:' + lat + 'lng:' + lng)
    var name = e.currentTarget.dataset.name
    wx.openLocation({
      latitude: lat,
      longitude: lng,
      name: name,
      scale: 28
    })
  },
  phoneCall: function (e) {
    wx.makePhoneCall({
      phoneNumber: e.currentTarget.dataset.phone,
      success: function () {
        console.log("拨打电话成功！")
      },
      fail: function () {
        console.log("拨打电话失败！")
      }
    })
  },
  navToGradeDetail: function (e) {
    console.log(e);
    let marketId = e.currentTarget.dataset.marketid
    console.log("marketId:" + marketId);
    let url = '../comments/comments?marketId=' + marketId;
    wx.navigateTo({
      url: url
    })
  }
  ,
  navToTanTanDetail: function (e) {
    let link = "";
    wx.request({
      url: 'https://www.daddyok.com/EvaluatingServer/GetMaterial',
      data: '',
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {
        'content-type': ' application/json; charset=UTF-8'
      },
      success: function (res) {
        console.log(res)

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
    wx.redirectTo({
      url: "../web/index?url='https://www.daddyok.com'"
    })
  },
  GetLevel:function()
  {
    let flag =0;
    //getStarLevel
    let that = this;
    wx.request({
      url: 'https://www.daddyok.com/EvaluatingServer/CommentsInfo',
      data: {
        marketId: that.data.marketId,
        action: "getStarLevel"
      },
      method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {
        'content-type': ' application/json; charset=UTF-8'
      },
      success: function (res) {
        console.log(res);
        that.setData({
          flag: Math.round(res.data.starLevelInfo[0])
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
  }
})
