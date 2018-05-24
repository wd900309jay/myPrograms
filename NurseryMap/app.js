
//app.js
App({
  onLaunch: function () {
    //调用API从本地缓存中获取数据
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

  }, 
  editTabBar: function () {
    var _curPageArr = getCurrentPages();
    var _curPage = _curPageArr[_curPageArr.length - 1];
    var _pagePath = _curPage.__route__;
    if (_pagePath.indexOf('/') != 0) {
      _pagePath = '/' + _pagePath;
    }
    var tabBar = this.globalData.tabBar;
    for (var i = 0; i < tabBar.list.length; i++) {
      tabBar.list[i].active = false;
      if (tabBar.list[i].pagePath == _pagePath) {
        console.log("pagePath:" + _pagePath);
        tabBar.list[i].active = true;//根据页面地址设置当前页面状态  
      }
    }
    _curPage.setData({
      tabBar: tabBar
    });
  }, 
 

  globalData: {
    userInfo: null,
    locationInfo: null,
    thirdSessionKey:"",
    loginStatus:false,
    //配置tabBar  
    tabBar: {
      "color": "#9E9E9E",
      "selectedColor": "#f00",
      "backgroundColor": "#fff",
      "borderStyle": "#ccc",
      "list": [
        {
          "pagePath": "/pages/index/index",
          "text": "主页",
          "iconPath": "/images/templateImg/homepage.png",
          "selectedIconPath": "/images/templateImg/homepage_fill.png",
          "selectedColor": "#4EDF80",
          active: false
        },
        {
          "pagePath": "/pages/detail/index",
          "text": "日志",
          "iconPath": "/images/templateImg/coordinates.png",
          "selectedIconPath": "/images/templateImg/coordinates_fill.png",
          "selectedColor": "#4EDF80",
          active: false
        },
        {
          "pagePath": "/pages/myInfo/myInfo",
          "text": "我的",
          "iconPath": "/images/templateImg/people.png",
          "selectedIconPath": "/images/templateImg/people_fill.png",
          "selectedColor": "#4EDF80",
          active: false
        }
      ],
      "position": "bottom"
    }  
  }
})