// pages/myhomepage/homepage.js
var util=require("../../utils/util.js");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    navTypes: ['点评', '收藏', '会员'],
    currentTab: 0,
    navItems: [[], [], []],
    collection: [],
    memberInfo: [],
    comments: [],
    userInfo:[],
    thirdKey:'',
    myLikeInfo:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that =this;
    wx.getStorage({
      key: 'userInfo',
      // 能获取到则显示用户信息，并保持登录状态，
      success: (res) => {
        wx.hideLoading();
        this.setData({
          userInfo: {
            avatarUrl: res.data.userInfo.avatarUrl,
            nickName: res.data.userInfo.nickName
          }
        })
      }
    })
    wx.getStorage({
      key: 'myLikeInfo',
      success: function (res) {
        that.setData({
          myLikeInfo: res.data.myLikeInfo
        })
      },
      fail: function (res) { },
      complete: function (res) { },
    })
    wx.getStorage({
      key: 'thirdKey',
      success: function (res) {
        that.setData({
          thirdKey: res.data.thirdKey
        })
        that.refreshComment();
      },
    })
 
  },
  navbarTap: function (e) {
    var idx = e.currentTarget.dataset.idx;
    this.setData({
      currentTab: idx
    })
  },
  refreshComment: function () {
    let that = this;
    wx.request({
      url: 'https://www.daddyok.com/EvaluatingServer/CommentsInfo',
      data: {
        thirdKey: that.data.thirdKey,
        action: "getUserComments"
      },
      method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {
        'content-type': ' application/json; charset=UTF-8'
      },
      success: function (res) {
        console.log(res);
        let allCommentArray = res.data.commentsInfo;
        let commentsArray = new Array();
        let myLikeInfo = new Array();
        myLikeInfo = that.data.myLikeInfo;

        for (var i = 0; i < allCommentArray.length; i++) {
          var dic = allCommentArray[i]
          let Info = {
            commentId: '',
            avataUrl: '',
            nickName: '',
            createTime: '',
            content: '',
            starLevel: 0,
            envLevel: '',
            devLevel: '',
            airLevel: '',
            like: false,
            likeCount: 0
            // photoUrls: []
          }
          Info.commentId = dic.commentId;
          Info.avataUrl = dic.avataUrl;
          Info.nickName = dic.nickName;
          Info.createTime = dic.createTime;
          Info.content = dic.content;
          Info.starLevel = dic.starLevel;
          Info.envLevel = util.getLevel(dic.envLevel);
          Info.devLevel = util.getLevel(dic.devLevel);
          Info.airLevel = util.getLevel(dic.airLevel);
          console.log(myLikeInfo);
          console.log(myLikeInfo.indexOf(dic.commentId));
          if (myLikeInfo.indexOf(dic.commentId) < 0) {
            Info.like = false;
          } else {
            Info.like = true;
          }

          Info.likeCount = dic.likeCount;
          Info.photoUrls = [];
          commentsArray.push(Info)
        }
        that.setData({
          comments: commentsArray,
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
  
  }
})