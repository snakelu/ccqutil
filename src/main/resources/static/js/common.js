/*
 * comment on 2017.3.25
 * for: 通用处理函数，包括ajax封装。
*/


$.ajaxSetup ({
    cache: false //关闭AJAX相应的缓存
});

/*
 * 封装的ajax请求函数
*/
var $ajax = function(type,url,data,successCallback,failCallback){
	/*初始化请求数据信息*/
	$.ajax({
		type:type,
		url: url, //地址
		dataType:"json",
        contentType: "application/json;charset=utf-8",
		data:JSON.stringify(data), //数据
		cache:false, //请求字段是否缓存
		success:function(data){//成功回调
			successCallback(data);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){//失败回调
			if(XMLHttpRequest.responseText.indexOf("平台登录")>0){
				//返回登录页，跳转页面
				window.location.href="/report/login.html";
			}
			failCallback(XMLHttpRequest);
		}
	});
}

/*
 * 封装的ajaxPOST请求函数
*/
var $ajaxPost = function(url,data,successCallback,failCallback){
	/*初始化请求数据信息*/
	$.ajax({
		type:'POST',
		url: url, //地址
		dataType:"json",
        contentType: "application/json;charset=utf-8",
		data:JSON.stringify(data), //数据
		cache:false, //请求字段是否缓存
		success:function(data){//成功回调
			successCallback(data);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){//失败回调
			if(XMLHttpRequest.responseText.indexOf("平台登录")>0){
				//返回登录页，跳转页面
				window.location.href="/report/login.html";
			}
			failCallback(XMLHttpRequest);
		}
	});
}

/*
 * 封装get方法ajax请求函数，
*/
var $ajaxGet = function(url,successCallback,failCallback){
	/*初始化请求数据信息*/
	$.ajax({
		type:"GET",
		url: url, //地址
        contentType: "application/json;charset=utf-8",
		cache:false, //请求字段是否缓存
		success:function(data){//成功回调
			successCallback(data);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){//失败回调
			if(XMLHttpRequest.responseText.indexOf("平台登录")>0){
				//返回登录页，跳转页面
				window.location.href="/report/login.html";
			}
			failCallback(XMLHttpRequest);
		}
	});
}

/*
 * 同步类型的ajax请求，其实可以和$ajax方法进行合并，多传递一个参数
*/
var $ajaxSync = function(type,url,data,successCallback,failCallback){
	/*初始化请求数据信息*/
	$.ajax({
		type:type,
		url: url, //地址
		dataType:"json",
		async: false,
        contentType: "application/json;charset=utf-8",
		data:JSON.stringify(data), //数据
		cache:false, //请求字段是否缓存
		success:function(data){//成功回调
			successCallback(data);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){//失败回调
			if(XMLHttpRequest.responseText.indexOf("平台登录")>0){
				//返回登录页，跳转页面
				window.location.href="/report/login.html";
			}
			failCallback(XMLHttpRequest);
		}
	});
}

/*
 * 同步类型的get ajax请求，其实可以和$ajaxGet方法进行合并，多传递一个参数
*/
var $ajaxSyncGet = function(url,successCallback,failCallback){
	/*初始化请求数据信息*/
	$.ajax({
		type:"GET",
		url: url, //地址
		dataType:"json",
		async: false,
        contentType: "application/json;charset=utf-8",
		cache:false, //请求字段是否缓存
		success:function(data){//成功回调
			successCallback(data);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){//失败回调
			if(XMLHttpRequest.responseText.indexOf("平台登录")>0){
				//返回登录页，跳转页面
				window.location.href="/report/login.html";
			}
			failCallback(XMLHttpRequest);
		}
	});
}

/**
 * 对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 * eg:
 * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 */
Date.prototype.pattern = function(fmt) {
 var o = {
    "M+" : this.getMonth()+1,                 //月份
    "d+" : this.getDate(),                    //日
    "h+" : this.getHours(),                   //小时
    "m+" : this.getMinutes(),                 //分
    "s+" : this.getSeconds(),                 //秒
    "q+" : Math.floor((this.getMonth()+3)/3), //季度
    "S"  : this.getMilliseconds()             //毫秒
  };
  if(/(y+)/.test(fmt))
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(var k in o)
    if(new RegExp("("+ k +")").test(fmt))
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
  return fmt;
}
/**
 * 根据毫秒值获取日期格式 YYYY-MM-DD HH:mm
 * 如果毫秒值为空 则返回""
 */
function formatDateYYYYMMDDhhmm(millisecond) {
	if (millisecond == null || millisecond == "") {
		return "";
	}
	return new Date(millisecond).pattern("yyyy-MM-dd hh:mm");
};

//将yyyy-mm-dd转成date对象
function parseDate(dateString){
	if(dateString == null || dateString==""){
		return new Date();
	}else {
		var date  = dateString.split(" ")[0];
		var time = dateString.split(" ")[1];
		var d = new Date();
		d.setFullYear(date.split("-")[0]);
		d.setMonth(parseInt(date.split("-")[1])-1);
		d.setDate(date.split("-")[2]);
		if(time != undefined){
			d.setHours(time.split("-")[0]);
			d.setMinutes(time.split("-")[1]);
			d.setSeconds(time.split("-")[2]);
		}
		return d;
	}
};

/*
 * 设置cookie函数
*/
function setCookie(name,value){
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString() + "; path=/";
}

/*
 * 设置登录后 userid username 的cookie
*/
function addInnerLoginCookie(logintoken,userid,username){
	setCookie("INNER_USER_TOKEN", logintoken);
	setCookie("inneruserid", userid);
	setCookie("username", username);
}

/*
 * 读取cookie函数
*/
function getCookie(name){
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

    if(arr=document.cookie.match(reg))

        return unescape(arr[2]);
    else
        return null;
}

/*
 * 清除cookie函数
*/
function cleanCookie(name)
{
   setCookie(name, "", -1);
}
