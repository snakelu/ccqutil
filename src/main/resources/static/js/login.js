	/*回车事件*/
	$(function(){
		$("body").keyup(function (event) {
			if (event.which == 13){
				$("#login").trigger("click");
			}
		});

		 /*定义通用错误显示*/
		var errMsg = {
			legend: "我勒个去",
			login0: "朕的生日都忘了？跪搓衣板！",
			login1: "朕爱吃的水果都不晓得？跪搓衣板！",
			login2: "朕爱玩的游戏都不知道？跪搓衣板！",
			login3: "记错了？跪搓衣板！"
		};

		var title = ["朕的生日","朕最爱的水果","朕最爱的游戏"];
		
		var i = Math.round(Math.random()*2);
		$("#username").val(title[i]);
		if (i === 0) {
			$("#password").attr("placeholder","请输入四个数字");
		} else if (i === 1) {
			$("#password").attr("placeholder","请输入汉字拼音");
		} else {
			$("#password").attr("placeholder","请输入四个字母");
		}

		/*显示错误函数*/
		function showErrMsg(status){
			switch(status){
				case -1:
					console.log(errMsg.legend);
					break;
				case 0:
					$("#err_msg").show();
					$("#err_msg").text(errMsg.login0);
					break;
				case 1:
					$("#err_msg").show();
					$("#err_msg").text(errMsg.login1);
					break;
				case 2:
					$("#err_msg").show();
					$("#err_msg").text(errMsg.login2);
					break;
				case 3:
					$("#err_msg").show();
					$("#err_msg").text(errMsg.login3);
				default:
					break;
			}
		}

        $("#username,#password").focus(function(){
            $("#err_msg").hide();
        });

		/*登录按钮事件*/
		$("#login").click(function(){
			var status = -1;
			if(!$("#password").val()){
				status = i;
				showErrMsg(status);
				return;
			}
			/*ajax请求登陆*/
			var username = $("#username").val();
			var password = $("#password").val();
			var data = {};
			data.username = username;
			data.password = password;
			$.ajax({
				type:'post',
				url: "/login", //地址
				dataType:"json",
                contentType: "application/json",
				data:JSON.stringify(data), //数据
				cache:"", //请求字段是否缓存
				success:function(data){//成功回调
					if(data.errorCode != '0000' || !data.detailInfo) {
						status = 3;
						showErrMsg(status);
					}else {
						window.location.href = "view/upload.html";
					}
				},
				error:function(data){//失败回调
					alert("ajax failed!");
				}
			});
			//requestAjax();
		});

		/*适配IE下部分样式*/
		var aName = navigator.appName;
		var aVersion=navigator.appVersion;
		aVersion=aVersion.split(";")[1].replace(/[ ]/g,"");
		if(aName == "Microsoft Internet Explorer" && aVersion == "MSIE8.0"){//IE8
			$(".loginInput").css({"width":"220px","height":"35px","padding-top":"12px"});
			$(".login-box-button").css({"width":"284px"});
		}
	});