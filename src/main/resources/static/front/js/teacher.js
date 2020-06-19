$(function(){
    
    $(".btn-submit").on('click',function(){        
        let status = true
        let text = ''
        let phoneCheck = /^[09]{2}[0-9]{8}$/;
        let emailCheck = /^([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22))*\x40([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d))*$/;
        let correctId = true ;
        let idHeader = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
        let idval = $("input[name='id']").val().toUpperCase();
        let idNumber = (idHeader.indexOf(idval.substring(0,1))+10) +''+ idval.substr(1,9);
        let s = parseInt(idNumber.substr(0,1)) +
            parseInt(idNumber.substr(1,1)) * 9 +
            parseInt(idNumber.substr(2,1)) * 8 +
            parseInt(idNumber.substr(3,1)) * 7 +
            parseInt(idNumber.substr(4,1)) * 6 +
            parseInt(idNumber.substr(5,1)) * 5 +
            parseInt(idNumber.substr(6,1)) * 4 +
            parseInt(idNumber.substr(7,1)) * 3 +
            parseInt(idNumber.substr(8,1)) * 2 +
            parseInt(idNumber.substr(9,1));
        let checkNum = parseInt(idNumber.substr(10,1));
        if(((s % 10) == 0 && checkNum == 0 ) || (10 - (s % 10)) == checkNum){
            correctId = true ;
        }else{
            correctId = false ;
        }

        if (!$("input[name='name']").val()) {
            status = false
            text = '請填寫姓名'
        }else if (!$("input[name='id']").val()) {
            status = false
            text = '請填寫身份證字號'
        }else if (idval.length != 10) {
            status = false
            text = '身份證字號長度錯誤'
        }else if (isNaN(idval.substr(1,9)) || (!/^[A-Z]$/.test(idval.substr(0,1)))) {
            status = false
            text = '請輸入正確格式的身份證字號'
        }else if (!correctId) {
            status = false
            text = '請輸入正確的身份證字號'
        }else if ($("#specialSkill").val() == 0) {
            status = false
            text = '請選擇領域'
        }else if (!$("input[name='email']").val()) {
            status = false
            text = '請輸入的Email'
        }else if (!emailCheck.test($("input[name='email']").val())){
            text = "請輸入正確的Email";
            status = false;
        }else if (!$("input[name='phone']").val()) {
            status = false
            text = '請輸入的手機號碼'
        }else if (!phoneCheck.test($("input[name='phone']").val())){
            text = "請輸入正確的手機號碼";
            status = false;
        }else if ($("input[name='identityState']:checked").length == 0) {
            status = false
            text = '請勾選系統身份'
        }else if ($("#select-school").val() == 0) {
            status = false
            text = '請選擇服務學校'
        }else if (!$("input[name='bankNum']").val()) {
            status = false
            text = '請填寫身匯款銀行'
        }else if (!$("input[name='subBankNum']").val()) {
            status = false
            text = '請填寫分行'
        }else if (!$("input[name='accountNum']").val()) {
            status = false
            text = '請填寫匯款帳號'
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }
    })
})