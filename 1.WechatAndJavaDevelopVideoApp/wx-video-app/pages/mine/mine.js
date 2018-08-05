const app = getApp()

Page({
  data: {
    faceUrl: "../resource/images/noneface.png",
    isMe: true,
    isFollow: false,


    videoSelClass: "video-info",
    isSelectedWork: "video-info-selected",
    isSelectedLike: "",
    isSelectedFollow: "",

    myVideoList: [],
    myVideoPage: 1,
    myVideoTotal: 1,

    likeVideoList: [],
    likeVideoPage: 1,
    likeVideoTotal: 1,

    followVideoList: [],
    followVideoPage: 1,
    followVideoTotal: 1,

    myWorkFalg: false,
    myLikesFalg: true,
    myFollowFalg: true

  },

  onLoad: function (params) {
    var me = this;
    var user = app.getGlobalUserInfo();
    var userId = user.id;
    console.log("onLoad:" + userId);

    me.setData({
      userId: userId
    });


    var serverUrl = app.serverUrl;
    wx.request({
      url: serverUrl + '/user/info?userId='+userId,
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success: function(res) {
        console.log(res.data);
        wx.hideLoading();
        
        if(res.data.status == 200) {
          var userInfo = res.data.data;
          var faceUrl = "../resource/images/noneface.png";
          if (userInfo.faceImage != null && userInfo.faceImage != '' && userInfo.faceImage != undefined) {
            faceUrl = userInfo.faceImage;
          }
          me.setData({
            faceUrl: faceUrl,
            fansCounts: userInfo.fansCounts,
            followCounts: userInfo.followCounts,
            receiveLikeCounts: userInfo.receiveLikeCounts,
            nickname: userInfo.nickname,
            isFollow: userInfo.follow
          });
        }
      }
    })
  },


  logout: function () {
    var user = app.getGlobalUserInfo();
    
    var serverUrl = app.serverUrl;
    wx.showLoading({
      title: '请等待',
    });

    //调用注销接口
    wx.request({
      url: serverUrl + '/logout?userId=' + user.id,
      method: "POST",
      header: {
        'content-type': 'application/json'
      },
      success: function(res) {
        console.log(res.data);
        wx.hideLoading();
        wx.showToast({
          title: '注销成功',
          icon: 'success',
          duration: 2000
        });

        wx.removeStorageSync("userInfo");
        wx.redirectTo({
          url: '../userLogin/login',
        })
      }
    })
  },

  changeFace: function() {
    var me = this;
    wx.chooseImage({
      count: 1,
      sizeType: ['compressed'],
      sourceType: ['album'],
      success: function(res) {
        var tempFilePaths = res.tempFilePaths;
        console.log(tempFilePaths);

        wx.showLoading({
          title: '上传中...',
        })
        var serverUrl = app.serverUrl;
        var userInfo = app.getGlobalUserInfo();
        wx.uploadFile({
          url: serverUrl + '/user/uploadFace?userId=' + userInfo.id,
          filePath: tempFilePaths[0],
          name: 'file',
          header: {
            'content-type': 'application/json'
          },
          success: function (res) {
            var data = JSON.parse(res.data);
            console.log(data);
            wx.hideLoading();
            if(data.status == 200) {
              wx.showToast({
                title: "上传成功",
                icon: "success"
              });

              var faceImageUrl = data.data;
              me.setData({
                faceUrl: faceImageUrl
              });

            }else if(data.status == 500){
              wx.showToast({
                title: data.msg
              })
            }
          }
        })
      }
    })
  }
})
