
//显示弹出式提示信息
function showMsg(msg){
	top.layer.msg(msg, {skin: 'layui-layer-molv'});
}
//警告提示信息
function showMsgWarn(msg){
	top.layer.msg(msg, {icon : 0, skin: 'layui-layer-molv'});
}
//成功提示信息
function showMsgSuccess(msg){
	top.layer.msg(msg, {icon : 1, skin: 'layui-layer-molv'});
}
//错误提示信息
function showMsgError(msg){
	top.layer.msg(msg, {icon : 2, skin: 'layui-layer-molv'});
}
//默认提示框
function showAlert(msg){
	top.layer.alert(msg, {skin: 'layui-layer-molv'});
}
//警告提示框
function showAlertWarn(msg){
	top.layer.alert(msg, {icon : 0, skin: 'layui-layer-molv'});
}
//成功提示框
function showAlertSuccess(msg){
	top.layer.alert(msg, {icon : 1, skin: 'layui-layer-molv'});
}
//错误提示框
function showAlertError(msg){
	top.layer.alert(msg, {icon : 2, skin: 'layui-layer-molv'});
}
//疑问提示框
function showAlertQue(msg){
	top.layer.alert(msg, {icon : 3, skin: 'layui-layer-molv'});
}
//loading
function loading(){
	top.layer.load(1, {
	    shade: 0.3 //0.1透明度的白色背景
	});
}
//关闭loading
function closeLoading(){
	top.layer.closeAll('loading');
}

//显示树状单选弹出框
/**
 * title : 弹框标题
 * url : tree后台数据url地址
 * pid ： 选择tree中某个值之后，所需要赋予当前页面的value值的id
 * pidLabel : 选择tree中某个值之后，所需要赋予当前页面的text值的id
 * isParent : 是否在父窗口打开该dialog，true：在父窗口打开，false或者不填则默认在当前窗口打开
 */
function showSelectTree(title, url, pid, pidLabel, isParent){
	var _this;
	if(isParent != null && isParent != undefined && isParent == true){
		_this = parent;
	}else{
		_this = window;
	}
	_this.layer.open({
	    type: 2,
	    title : title,
	    skin: 'layui-layer-rim', //加上边框
	    area: ['420px', '600px'], //宽高
	    content: ctx + '/home/treeSelect?path=' + url,
	    btn : ['选择', '关闭'],
	    closeBtn : '1',
	    shadeClose : true,//点击遮罩层关闭
	    yes : function(index, layero){
	        var iframeWin = _this.window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	        var va = iframeWin.selectTreeValue();
	        if(va.value != null && va.value != undefined){
	        	$('#' + pid).val(va.value);
		        $('#' + pidLabel).val(va.label);
		        _this.layer.close(index);
	        }
	    }
	});
}
//显示上传并选择单张图片的弹出框
/**
 * title : 弹框标题
 * img ： 确定之后，所需要赋予当前img的src属性值的id
 * imgvalue : 确定之后，所需要赋予当前某个input的value值为url地址的id，可为空
 * isParent : 是否在父窗口打开该dialog，true：在父窗口打开，false或者不填则默认在当前窗口打开
 * param : 传递的参数，在回调方法中原样返回
 * width : 图片宽度限定
 * height : 图片高度限定
 */
function showSingleImg(title, img, imgvalue, isParent, param, width, height){
	var _this;
	if(isParent != null && isParent != undefined && isParent == true){
		_this = parent;
	}else{
		_this = window;
	}
	var _url = ctx + '/imgSelect';
	if(width != undefined && height != undefined && width != null && height != null){
		_url += '?width=' + width + '&height=' + height;
	}
	_this.layer.open({
	    type: 2,
	    title : title,
	    skin: 'layui-layer-rim', //加上边框
	    area: ['420px', '540px'], //宽高
	    content: _url,
	    btn : ['确定', '关闭'],
	    closeBtn : '1',
	    shadeClose : true,//点击遮罩层关闭
	    yes : function(index, layero){
	    	var iframeWin = _this.window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	        var va = iframeWin.getImgUrl();
	        if(va != null && va != undefined){
	        	if(img != null || img != undefined){
	        		$('#' + img).attr('src', va);
		        	if(imgvalue != null && imgvalue != undefined){
		        		$('#' + imgvalue).val(va);
		        	}
		        	_this.layer.close(index);
	        	}else{
	        		_this.layer.close(index);
	        		saveImgSelect(va, param);
	        	}
	        }
	    }
	});
}
//显示选择bootstrap小图标
/**
 * pid ： 选择tree中某个值之后，所需要赋予当前页面的value值的id
 * pidLabel : 选择tree中某个值之后，所需要赋予当前页面的text值的id
 * isParent : 是否在父窗口打开该dialog，true：在父窗口打开，false或者不填则默认在当前窗口打开
 */
function showSelectIco(pid, pidLabel, isParent){
	var _this;
	if(isParent != null && isParent != undefined && isParent == true){
		_this = parent;
	}else{
		_this = window;
	}
	_this.layer.open({
	    type: 2,
	    title : '选择图标',
	    skin: 'layui-layer-molv', //加上边框
	    area: ['820px', '600px'], //宽高
	    content: ctx + '/iconSelect',
	    btn : ['选择', '关闭'],
	    closeBtn : '1',
	    shadeClose : true,//点击遮罩层关闭
	    yes : function(index, layero){
	        var iframeWin = _this.window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	        var va = iframeWin.selectIcoValue();
	        if(va != null && va != undefined){
	        	$('#' + pid).val(va);
		        $('#' + pidLabel).html('<i class="' + va + '" style="font-size:30px;margin-left:20px;"></i>');
		        _this.layer.close(index);
	        }
	    }
	});
}