// pages/comments/comments.js
var util = require("../../utils/util")
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    marketId: '',
    isLoading: true,//是否显示加载数据提示
    freshCount: 6,
    currentIndex: 0,
    allCommentCount: 0,
    leftCount: 0,
    allCommentArray: [],
    comments: [],
    myLikeInfo: [],

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    // app.editTabBar();//添加tabBar数据
    wx.setNavigationBarTitle({
      title: '评价详情',
    })
    that.setData({
      marketId: options.marketId
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
    that.refreshComment();
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
    console.log("隐藏页面")
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    let that = this;
    console.log("卸载页面");
    wx.setStorage({
      key: 'myLikeInfo',
      data: {
        myLikeInfo: that.data.myLikeInfo
      },
      success: function (res) {
        console.log("存储点赞信息成功");
      },
      fail: function (res) {
        console.log("存储点赞信息失败");
      }
    })

    // wx.request({
    //   url: 'https://www.daddyok.com/EvaluatingServer/CommentsInfo',
    //   data: {
    //     commentIdArray: that.data.myLikeInfo,
    //     action: "giveThumbToComments"
    //   },
    //   method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
    //   header: {
    //     'content-type': ' application/json; charset=UTF-8'
    //   },
    //   success: function (res) {

    //   },
    //   fail: function (res) {
    //     // fail
    //     console.log("----------------faild-----------:" + res.data.error)
    //   },
    //   complete: function (res) {
    //     // complete
    //     console.log("-----------------------complete------------------")
    //   }
    // })
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    let that = this;
    let refresh = that.refreshComment();
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
  //上拉加载
  onReachBottom: function () {
    var conArr = [], that = this;
    console.log("onReachBottom")
    let allCommentCount = that.data.allCommentCount;
    let allCommentArray = that.data.allCommentArray;
    let currentIndex = that.data.currentIndex;
    let freshCount = that.data.freshCount;
    let objIndex = 0;
    let leftCount = allCommentCount - currentIndex;
    // 当前评论索引小于或等于每次刷新得最大值
    if (leftCount == 0) {
      that.setData({
        isLoading: false
      })
    } else {//当前评论索大于等于每次刷新得最大值
      if (leftCount >= freshCount) {
        console.log("leftCount[" + leftCount + "] >= freshCount")
        objIndex = currentIndex + freshCount;
        console.log("objIndex:" + objIndex);
      } else {
        console.log("leftCount[" + leftCount + "] < freshCount")
        objIndex = currentIndex + leftCount;
        console.log("objIndex:" + objIndex);
      }
      for (let i = currentIndex; i < objIndex; i++) {
        let dic = allCommentArray[i]
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
        };
        Info.commentId = dic.commentId;
        Info.avataUrl = dic.avataUrl;
        Info.nickName = dic.nickName;
        Info.createTime = dic.createTime;
        Info.content = dic.content;
        Info.starLevel = dic.starLevel;
        Info.like = false;
        Info.likeCount = dic.likeCount;
        Info.envLevel = util.getLevel(dic.envLevel);
        Info.devLevel = util.getLevel(dic.devLevel);
        Info.airLevel = util.getLevel(dic.airLevel);
        Info.photoUrls = [];
        conArr.push(Info);
      }
      //模拟网络加载
      setTimeout(function () {
        that.setData({
          comments: that.data.comments.concat(conArr),
          currentIndex: objIndex
        })
      }, 1000)
    }
  },
  GiveThumbUp: function (e) {
    let that = this;
    let id = e.currentTarget.dataset.commentid;
    let myLike = new Array();
    myLike = that.data.myLikeInfo;
    console.log("commentid:" + id)
    let list = that.data.comments
    console.log(list)
    let Info = {
      commentId: '',
      action: false
    }
    for (let i of list) {
      if (i.commentId == id) {
        console.log("id相同")
        i.like = !i.like
      }
      if (i.commentId == id && i.like) {
        console.log("点赞")
        i.likeCount = i.likeCount + 1
        myLike["cid" + i.commentId] = i.like;
      } else if (i.commentId == id && !i.like) {
        console.log("取消赞")
        i.likeCount = i.likeCount - 1
        myLike["cid" + i.commentId] = i.like;
      }
      console.log(myLike);
      that.setData({
        comments: list,
        myLikeInfo: myLike
      })
    }
  },
  previImage: function (e) {
    console.log(e);
    wx.previewImage({
      urls: e.currentTarget.dataset.path
    })
  },
  navToPublishPage: function (e) {
    let marketId = e.currentTarget.dataset.marketid
    let url = '../publishcomment/publish?marketId=' + marketId
    wx.navigateTo({
      url: url
    })
  },
  refreshComment: function () {
    let that = this;
    wx.request({
      url: 'https://www.daddyok.com/EvaluatingServer/CommentsInfo',
      data: {
        marketId: that.data.marketId,
        action: "getMarketComments"
      },
      method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {
        'content-type': ' application/json; charset=UTF-8'
      },
      success: function (res) {
        console.log(res);
        let allCommentArray = res.data.commentsInfo;
        let commentsArray = new Array();
        let freshCount = that.data.freshCount;
        let myLikeInfo = new Array();
        myLikeInfo = that.data.myLikeInfo;
        if (allCommentArray.length > freshCount) {
          that.setData({
            allCommentCount: allCommentArray.length,
            currentIndex: freshCount,
            leftCount: allCommentArray.length - freshCount,
            isLoading: true
          })
        }
        else {
          freshCount = allCommentArray.length;
          that.setData({
            allCommentCount: allCommentArray.length,
            currentIndex: freshCount,
            leftCount: allCommentArray.length - freshCount,
            isLoading: false
          })
        }

        for (var i = 0; i < freshCount; i++) {
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
          if (myLikeInfo["cid_"+dic.commentId]) {
            Info.like = true;
          } else {
            Info.like = false;
          }

          Info.likeCount = dic.likeCount;
          Info.photoUrls = [];
          commentsArray.push(Info)
        }
        that.setData({
          comments: commentsArray,
          allCommentArray: res.data.commentsInfo
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
  getObjectKeys: function (object) {
    var keys = [];
    for (var property in object)
      keys.push(property);
    return keys;
  },
  getObjectValues: function (object) {
    var values = [];
    for (var property in object)
      values.push(object[property]);
    return values;
  }
})