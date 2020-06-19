/**
 * 檢查內容是否為空
 */
function checkNull(obj) {
	if (obj.val() == null || $.trim(obj.val()) == "") {
		return true;
	} else {
		return false;
	}
}

/**
 * 檢查內容長度
 */
function checkLength(obj, len1, len2) {
	if(obj.val().length >= len1 && obj.val().length <= len2){
		return false;
	}else{
		return true;
	}
}

/**
 * 正整數檢查
 */
function checkNum(obj, len1, len2) {
	var content = obj.val();
	var result = true;
	var regex = /^[0-9]+$/;
	if(len1 == 0 && len2 == 0){
		if(regex.test(content)){
			result = false;
		}
	}else{
		if(regex.test(content) && content.length > len1 && content.length < len2){
			result = false;
		}
	}
	return result;
}

/**
 * 大小英文檢查
 */
function checkEng(obj, len1, len2) {
	var content = obj.val();
	var result = false;
	var regex = /^[a-zA-Z]+$/;
	if(len1 == 0 && len2 == 0){
		if(regex.test(content)){
			result = true;
		}
	}else{
		if(regex.test(content) && content.length > len1 && content.length < len2){
			result = true;
		}
	}
	return result;
}

/**
 * 正整數&大小英文檢查
 */
function checkNumAndEng(obj, len1, len2) {
	var content = obj.val();
	var result = false;
	var regex = /^[a-zA-Z0-9]+$/;
	if(len1 == 0 && len2 == 0){
		if(regex.test(content)){
			result = true;
		}
	}else{
		if(regex.test(content) && content.length > len1 && content.length < len2){
			result = true;
		}
	}
	return result;
}

/**
 * 浮點數
 */
function checkDecimal(obj, len1, len2) {
	var content = obj.val();
	var result = false;
	var regex = /^(-?\d+)(\.\d+)?$/;
	if(len1 == 0 && len2 == 0){
		if(regex.test(content)){
			result = true;
		}
	}else{
		if(regex.test(content) && content.length > len1 && content.length < len2){
			result = true;
		}
	}
	return result;
}

/**
 * 驗證手機格式
 */
function checkCellPhone(obj) {
	var regex = /^[0][9][0-9]{8}$/;
	if ($.trim(obj.val()) != "" && regex.test(obj.val())) {
		return true;
	} else {
		return false;
	}
}

/**
 * 驗證連結網址是否有效
 */
function checkURL(obj) {
	var regex = /http(s)?:////([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
	if ($.trim(obj.val()) != "" && regex.test(obj.val())) {
//		return "\n請輸入正確連結網址，EX：http://www.google.com";
		return true;
	} else {
		return false;
	}
}

/**
 * 檢查身份證字號(台灣)
 */
function checkId(obj) {
	var content = obj.val().toUpperCase();
	alert(content);
	var idfirst = new Array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
	var idfirstvalue = new Array('10','11','12','13','14','15','16','17','34','18','19','20','21','22','35','23','24','25','26','27','28','29','32','30','31','33');
	var i = 0;
	var saveindex;
	//取得身分證字號第一碼
	while(i<26){
		if(idfirst[i]==content.substr(0,1)){
		  saveindex=i;
		  break;
		}
		i++;
	}
	var idtotalvalue =(parseInt(idfirstvalue[saveindex].substr(0,1))*1)+(parseInt(idfirstvalue[saveindex].substr(1,1))*9)+(parseInt(content.substr(1,1))*8)+(parseInt(content.substr(2,1))*7)+(parseInt(content.substr(3,1))*6)+(parseInt(content.substr(4,1))*5)+(parseInt(content.substr(5,1))*4)+(parseInt(content.substr(6,1))*3)+(parseInt(content.substr(7,1))*2)+(parseInt(content.substr(8,1))*1)+(parseInt(content.substr(9,1))*1);
	if(idtotalvalue%10==0){
		return true;
	} else {
		return false;
	}
}

/**
 * 驗證中英文字數長度
 */
function checkByteLen(obj, len) {
	var string_length = 0;
	var content = obj.val();
	for(var i=0; i<content.length; i++) {
		if(string[i].match(/[^\x00-\xff]/ig) != null) {
			string_length += 3;
		} else {
			string_length ++;
		}
	}
	if(string_length > parseInt(len)) {
		return true;
	} else {
		return false;
	}
}

//图片大小验证    
function verificationPicFile(file) {    
    var fileSize = 0;    
    var fileMaxSize = 1024;//1M    
    var filePath = file.value;    
    if(filePath){    
        fileSize =file.files[0].size;    
        var size = fileSize / 1024;    
        if (size > fileMaxSize) {    
            alert("文件大小不能大于1M！");    
            file.value = "";    
            return false;    
        }else if (size <= 0) {    
            alert("文件大小不能为0M！");    
            file.value = "";    
            return false;    
        }    
    }else{    
        return false;    
    }    
}

/**
 * 圖片尺寸驗證
 */
function verificationPicFile(file, w, h) {
	var filePath = file.value;
	if(filePath){
		//讀取圖片數據
		var filePic = file.files[0];
		var reader = new FileReader();
		reader.onload = function (e) {
			var data = e.target.result;
			//加載圖片獲取圖片真實寬度和高度
			var image = new Image();
			image.onload=function() {
				var width = image.width;
				var height = image.height;
				if (width != w || height != h) {
					alert("圖片尺寸應為："+w+" * "+h);
					$(file).ace_file_input('reset_input');
					file.value = "";
					return false;
				}
			};
			image.src= data;
		};
		reader.readAsDataURL(filePic);
	} else {
		return false;
	}
}

/**
 * 日期格式檢查
 */
function dateValidationCheck(str) {
	var re = new RegExp("^([0-9]{4})[.-]{1}([0-9]{1,2})[.-]{1}([0-9]{1,2})$");
	var strDataValue;
	var infoValidation = true;
	if ((strDataValue = re.exec(str)) != null) {
		var i;
		i = parseFloat(strDataValue[1]);
		if (i <= 0 || i > 9999) { /*年*/
			infoValidation = false;
		}
		i = parseFloat(strDataValue[2]);
		if (i <= 0 || i > 12) { /*月*/
			infoValidation = false;
		}
		i = parseFloat(strDataValue[3]);
		if (i <= 0 || i > 31) { /*日*/
			infoValidation = false;
		}
	} else {
		infoValidation = false;
	}
	if (!infoValidation) {
//		alert("請輸入 YYYY-MM-DD 日期格式");
		return true;
	}
//	return infoValidation;
	return false;
}

/**
 * 檢查日期起迄
 */
function checkDateSinceAndAfter(beginTime, endTime) {
	//轉換為日期格式
	var begin = beginTime.replace(/-/g,"/");
	var end = endTime.replace(/-/g,"/");
	//如果起始日期大於結束日期
	if(Date.parse(begin)-Date.parse(end)>0){
		return true;
	}
	return false;
}