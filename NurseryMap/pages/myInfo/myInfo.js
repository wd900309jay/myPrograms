// pages/myInfo/myInfo.js
var app = getApp();
var globalData = app.globalData;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {
      avatarUrl: "",
      nickName: "未登录"
    },
    status: false,
    status_text: "登录",
    encryptedData: "",
    iv: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.setNavigationBarTitle({
      title: '个人中心',
    })
    wx.getStorage({
      key: 'userInfo',
      // 能获取到则显示用户信息，并保持登录状态，
      success: (res) => {
        wx.hideLoading();
        this.setData({
          userInfo: {
            avatarUrl: res.data.userInfo.avatarUrl,
            nickName: res.data.userInfo.nickName
          },
          bType: res.data.bType,
          status_text: res.data.status_text,
          status: res.data.status
        })
        globalData.loginStatus = res.data.status
      }
    })
  },
  loginAction: function () {
    var _this = this;
    if (!_this.data.status) {
      wx.showLoading({
        title: '正在登录',
      })
      wx.login({
        success: function (res) {
          var code = res.code;
          console.log(code);
          //获取code
          if (code) {
            wx.getUserInfo({
              withCredentials: true,
              lang: 'zh_CN',
              success: function (res) {
                console.log("userRes:" + res);
                _this.setData({
                  userInfo: {
                    avatarUrl: res.userInfo.avatarUrl,
                    nickName: res.userInfo.nickName
                  },
                  status: true,
                  status_text: "退出登录",
                  encryptedData: res.encryptedData,
                  iv: res.iv
                })
                globalData.loginStatus =true;
              
                wx.setStorage({
                  key: 'userInfo',
                  data: {
                    userInfo: {
                      avatarUrl: res.userInfo.avatarUrl,
                      nickName: res.userInfo.nickName
                    },
                    status: true,
                    status_text: "退出登录"
                  },
                  success: function (res) {
                    console.log("-------存储个人信息成功！------")
                  },
                  fail: function (res) { },
                  complete: function (res) { },
                })
              },
              fail: function (res) {
                console.log(res)
              },
              complete: function (res) { },
            })
            //获取3rdsessionkey
            wx.request({
              url: 'https://www.daddyok.com/EvaluatingServer/LoginServlet',
              data: {
                code: code
              },
              method: 'POST',
              header: {
                "content-type": "application/x-www-form-urlencoded"
              },
              success: function (res) {
                console.log(res.data)
                var thirdSessionKey = res.data.thirdkey;
                if (thirdSessionKey) {
                  wx.setStorage({
                    key: 'thirdKey',
                    data: {
                      thirdKey: thirdSessionKey
                    },
                    success: function (res) {
                      console.log("-------存储3rd_key成功！------")
                    }
                  })
                  console.log("thirdSessionKey:" + thirdSessionKey);
                  wx.hideLoading();
                  //请求解密敏感数据
                  wx.request({
                    url: 'https://www.daddyok.com/EvaluatingServer/DecryptUserInfo',
                    data: {
                      thirdKey: thirdSessionKey,
                      encryptedData: _this.data.encryptedData,
                      iv: _this.data.iv
                    },
                    header: { "content-type": "application/x-www-form-urlencoded" },
                    method: 'Post',
                    success: function (res) {
                      console.log('请求解密成功-------')
                    },
                    fail: function (res) {
                      console.log('请求解密失败-------' + res)
                    },
                    complete: function (res) { },
                  })
                }
              }
            })
          }
          else {
            console.log('登录失败-------' + res.errMsg)

          }
        }
      })
    }
    else {
      wx.showModal({
        title: '确认退出？',
        content: '退出后无法使用全部功能',
        success: function (res) {
          if (res.confirm) {
            console.log("确定")
            wx.removeStorageSync('userInfo')
            _this.setData({
              userInfo: {
                avatarUrl: "",
                nickName: "未登录",
              },
              status: false,
              status_text: "登录"
            })
          } else {
            console.log("取消")
            _this.setData({
              status: true
            })
          }
        }
      })
    }

  },
  GetUserInfo: function (result) {

  },
  menuBtn: function (e) {
    var _this = this;
    if (_this.data.status) {
      var id = e.currentTarget.dataset.menu_id;
      switch (id) {
        case '0x01':
          console.log('0x01');
          wx.navigateTo({
            url: '../myhomepage/homepage',
            success: function(res) {},
            fail: function(res) {},
            complete: function(res) {},
          })
          break;
        case '0x02':
          console.log('0x02');
          break;
        case '0x03':
          console.log('0x03');
          break;
        case '0x04':
          console.log('0x04');
          break;
        default:
          break;
      }
    }
    else {
      wx.showModal({
        title: '请登录',
        content: '登录后可操作'
      })
    }
  }

})