;!function(){
	var $ = layui.jquery;
	$.ajaxSetup({
		complete : function(XMLHttpRequest, textStatus) {
			var text = XMLHttpRequest.responseText;
			if(isJSON(text)){
				var res = JSON.parse(text);
				if(typeof(res.success)!== "undefined" && !res.success && (res.code==1080 || res.code==1081)){
					layer.msg(res.msg,{icon:5,shift:6,time:2000},function(){window.location.href = 'admin/logout'});
				}else if(typeof(res.success)!== "undefined" && !res.success){
					if($.isEmptyObject(res.data) || !res.data){
						layer.msg(res.msg,{icon:5,shift:6});						
					}else{
						layer.msg(res.msg+':'+res.data,{icon:5,shift:6});
					}
				}
			}
		}
	});
	
}();


function permission(){
	$('.permission').each(function(){
		var permissionArray = parent.adminPermissionTags.split(',');
		if(permissionArray.includes($(this).attr('_permission'))){
			$(this).removeClass('layui-hide');
		}else{
			$(this).remove();
		}
	});
}


function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            if(typeof obj == 'object' && obj ){
                return true;
            }else{
                return false;
            }
        } catch(e) {
            //console.log('error：'+str+'!!!'+e);
            return false;
        }
    }
    //console.log('It is not a string!')
}


function confirmPwd(){
    var datetime = new Date();
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    //var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    //var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    //var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return String(year) +  String(month) + String(date);
}