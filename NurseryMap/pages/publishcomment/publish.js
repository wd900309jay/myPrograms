// pages/publishcomment/publish.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    thirdKey: '',
    marketId: "",
    word_count: 0,
    remind_text: "加油，还差15个字！",
    tempFilePaths: [],
    status: false,
    content: '',
    total_grade: {
      name: '总体',
      flag: '',
      grade_text: '',
      gradeStatus: true,
    },
    env_grade: {
      name: '环境',
      flag: '',
      grade_text: '',
    },
    dev_grade: {
      name: '设施',
      flag: '',
      grade_text: ''
    },
    air_grade: {
      name: '空气',
      flag: '',
      grade_text: ''
    }

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this;
    this.setData({
      marketId: options.marketId
    })
    wx.getStorage({
      key: 'thirdKey',
      success: function (res) {
        that.setData({
          thirdKey: res.data.thirdKey
        })
      },
    })
  },
  totalGrade: function (e) {
    let that = this;
    let flag = e.currentTarget.dataset.flag;
    let gradetype = e.currentTarget.dataset.type;
    let grade_text = '';
    switch (flag) {
      case '1':
        grade_text = '不满意';
        break;
      case '2':
        grade_text = '一般';
        break;
      case '3':
        grade_text = '不错';
        break;
      case '4':
        grade_text = '满意';
        break;
      case '5':
        grade_text = '超赞';
        break;
    };
    switch (gradetype) {
      case 'total':
        that.setData({
          total_grade: {
            name: '总体',
            flag: flag,
            grade_text: grade_text,
            gradeStatus: false
          }
        });
        break;
      case 'env':
        that.setData({
          env_grade: {
            name: '环境',
            flag: flag,
            grade_text: grade_text

          }
        });
        break;
      case 'dev':
        that.setData({
          dev_grade: {
            name: '设施',
            flag: flag,
            grade_text: grade_text

          }
        });
        break;
      case 'air':
        that.setData({
          air_grade: {
            name: '空气',
            flag: flag,
            grade_text: grade_text

          }
        });
        break;

    }
  },
  upload: function (page, path) {
    wx.showToast({
      icon: "loading",
      title: "正在上传"
    }),
      wx.uploadFile({
        url: constant.SERVER_URL + "/FileUploadServlet",
        filePath: path[0],
        name: 'file',
        header: { "Content-Type": "multipart/form-data" },
        formData: {
          //和服务器约定的token, 一般也可以放在header中
          'session_token': wx.getStorageSync('session_token')
        },
        success: function (res) {
          console.log(res);
          if (res.statusCode != 200) {
            wx.showModal({
              title: '提示',
              content: '上传失败',
              showCancel: false
            })
            return;
          }
          var data = res.data
          page.setData({  //上传成功修改显示头像
            src: path[0]
          })
        },
        fail: function (e) {
          console.log(e);
          wx.showModal({
            title: '提示',
            content: '上传失败',
            showCancel: false
          })
        },
        complete: function () {
          wx.hideToast();  //隐藏Toast
        }
      })
  },
  publishBtn: function () {
    let that = this;
    console.log("thirdSessionKey:" + that.data.thirdKey);
    console.log("content:" + that.data.content);
    console.log("totalStars:" + that.data.total_grade.flag);
    console.log("envStars:" + that.data.env_grade.flag);
    console.log("devStars:" + that.data.dev_grade.flag);
    console.log("airStars:" + that.data.air_grade.flag);
    console.log("marketId:" + that.data.marketId);


    wx.request({
      url: 'https://www.daddyok.com/EvaluatingServer/CommentsInfo',
      data: {
        marketId: that.data.marketId,
        action: "publishComment",
        thirdKey: that.data.thirdKey,
        content: that.data.content,
        totalStars: that.data.total_grade.flag,
        envStars: that.data.env_grade.flag,
        devStars: that.data.dev_grade.flag,
        airStars: that.data.air_grade.flag,
        //photoPathArray: that.data.tempFilePaths,
      },
      method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
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
  },
  bindFormSubmit: function (e) {
    let that = this;
    let text = e.detail.value.textarea;
    that.setData({
      content: text

    });

    that.publishBtn();
    wx.navigateBack({

    })
  },
  textAreaInput: function (e) {
    // console.log(e.detail);
    var count = e.detail.cursor;
    var sumCount = 0;
    var remind_text = "";
    if (count < 15) {
      sumCount = 15 - count;
      remind_text = "加油，还差" + sumCount + "个字！";
    } else if (count >= 15 & count < 100) {
      sumCount = 100 - count;
      remind_text = "再加" + sumCount + "字和3张图就有机会赢100积分！";
    } else {
      remind_text = "再加3张图就有机会赢100积分！";
    }

    this.setData({
      word_count: e.detail.cursor,
      remind_text: remind_text
    })
  },
  selectPhotos: function (e) {
    var that = this;
    wx.showActionSheet({
      itemList: ['从相册中选择', '拍照'],
      itemColor: "#00000",
      success: function (res) {
        if (!res.cancel) {
          if (res.tapIndex == 0) {
            that.chooseWxImage('album')
          } else if (res.tapIndex == 1) {
            that.chooseWxImage('camera')
          }
        }
      }
    })
  },
  chooseWxImage: function (type) {
    var that = this;

    wx.chooseImage({
      sizeType: ['original', 'compressed'],
      sourceType: [type],
      success: function (res) {
        var photoPathArray = res.tempFilePaths
        var tempFilePaths = new Array();
        // for (var i = 0; i < photoPathArray.length;i++)
        // {
        //   var PPath = photoPathArray[i];
        //   var Info=
        //   {
        //     photoPath:"",
        //     visible:false
        //   }
        //   Info.photoPath = PPath;
        //   if (Info.photoPath){
        //     Info.visible=true;
        //   }
        //   tempFilePaths.push(Info);
        // }

        that.setData({

          tempFilePaths: photoPathArray,
        })
      },
      fail: function (res) {

      }

    })
  },
  previImage: function (e) {
    wx.previewImage({
      urls: e.currentTarget.dataset.path,
    })
  }
})