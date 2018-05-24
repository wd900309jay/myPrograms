// pages/web/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    url:'http://mp.weixin.qq.com/s?__biz=MzI5Mzk4MDE5MQ==&mid=100001197&idx=1&sn=b0612140e233244d223e0c1a137dcaa9&chksm=6c6894dd5b1f1dcb408fc7fdfcbb0693f1db7a87c93f5fbf7d82e217b814d7ba50bcf4213a68#rd'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (query) {
    this.setData({
      url: query.url
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