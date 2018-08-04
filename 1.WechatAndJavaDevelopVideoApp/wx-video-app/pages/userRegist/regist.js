const app = getApp()

Page({
    data: {

    },

    doRegister: function(e) {
      //从表单中获取到注册的用户名和密码
      var formObject = e.detail.value;
      var username = formObject.username;
      var password = formObject.password;
    
      //简单验证
      if(username.length == 0 || password.length == 0) {
        wx.showToast({
          title: '用户名或密码不能为空',
          icon: 'none',
          duration: 3000
        })
      }else {
        var serverUrl = app.serverUrl;
        wx.request({
          url: serverUrl + '/register',
          method: "POST",
          data: {
            username: username,
            password: password
          },
          header: {
            'content-type': 'application/json'
          },
          success: function(res) {
            var status = res.data.status;
            if(status == 200) {
              wx.showToast({
                title: "用户注册成功",
                icon: 'none',
                duration: 3000
              }),
              app.userInfo = res.data.data;
            }else if(status == 500) {
              wx.showToast({
                title: res.data.msg,
                icon: 'none',
                duration: 3000
              })
            }
          }
        })
      }
    } 

})