$(function(){
    $(".menu").hover(function(){ //header子選單
        $(this).find("ul.sub-menu").toggle()
    })

    $(".account-name").hover(function(){ //header子選單        
        $(this).find('ul').toggle()
    })

    $(document).on("click","input[type='checkbox']",function(){
        if ($(this).siblings().is(".checkbox-square.active")) {
            $(this).parent().find(".checkbox-square").removeClass("active")
        } else {
            $(this).parent().find(".checkbox-square").addClass("active")
        }
    })
    $(document).on("click", "input[type='radio']", function () { // 頁面所有 radio 點擊樣式
        $(this).parent().find(".radio-circle").addClass("active")
        $(this).parent().siblings().find(".radio-circle").removeClass("active")
    })

    $("#select-school").customselect()
    
    $(window).scroll(function() { //header展開縮和
        var scrollingElement = (document.scrollingElement || document.body)      
        let scrollTop = scrollingElement.scrollTop    
        let logo = $(".header .logo")
        let accountName = $(".header .account-name")
        let nav = $(".header nav")
        let navAccount = $(".nav-mobile")
        if (scrollTop > 90 ) {
            logo.addClass('scrollState')
            accountName.addClass('scrollState')
            nav.addClass('scrollState')
            navAccount.addClass('scrollState')
        }else {
            logo.removeClass('scrollState')
            accountName.removeClass('scrollState')
            nav.removeClass('scrollState')
            navAccount.removeClass('scrollState')
        }
    })
    
    $(".btnFile").on("change",function(event){ //合約上傳檔案
        let text = $(event.target).data('text')        
        let fileData = event.target.files[0]        
        $(this).parent().parent().find(`.${text}`).text(fileData.name)
        $(this).parent().parent().find(`.${text}`).css('margin-top','10px')        
    })

    $(".btn-closeLightBox").on("click",function(){ //關閉燈箱
        let lightBox = $(this).data('lb')
        if (lightBox.indexOf('teacherImportData') > -1) {            
            $("#teacherImportFile").val('')       
            $("p.teacherImportFileText").text('')            
        }
        $("section.lightBoxBG").removeClass('dis-b').addClass('dis-n')
        $(`section.${lightBox}-lightBox`).removeClass('dis-b').addClass('dis-n')
    })
    $(".btn-closelightBoxAlert").on("click",function(){ //關閉燈箱        
        $("section.lightBoxBG").removeClass('dis-b').addClass('dis-n')
        $(`section.lightBoxAlert`).removeClass('dis-b').addClass('dis-n')
    })

    $(".btn-openLightBox").on("click",function(){ //打開燈箱
        let lightBox = $(this).data('lb')
        $("section.lightBoxBG").removeClass('dis-n').addClass('dis-b')
        $(`section.${lightBox}-lightBox`).removeClass('dis-n').addClass('dis-b')
    })

    $(".keyWord").on("change",function(){  //關鍵字輸入框生成tag
        if ($(this).val() == '') {
            return
        }else {
            let item = `<div class="lightboxItem"><span>${$(this).val()}</span><button class="deleteTag"><i class="fas fa-times"></i></button></div>`        
            $(this).parent().append(item)
        }        
    })

    $(".create-content").on("click",".deleteTag",function(){  //tag刪除按鈕        
        let val = $(this).data('value')
        let lightBoxName = $(this).parent().parent().find('button.btn-openLightBox').data('lb')        
        let lightBox = $(`section.${lightBoxName}-lightBox`)        
        lightBox.find(`input[value='${val}']`).prop('checked',false) //燈箱內input移除checked
        lightBox.find(`input[value='${val}']`).siblings('.checkbox-square').removeClass('active')
        $(this).parent().remove()  //移除tag
    })

    $(".createTagBtn").on("click",function(){   //燈箱確定按鈕，生成tag
        let tagName = $(this).data('tag') 
        let location = $(`button.btn-openLightBox[data-lb='${tagName}']`).parent()  //tag放置位置
        let checkedInput = $(this).parent().find('input:checked') //燈箱中被勾選的input
        location.find('.lightboxItem').remove() //先移除全部tag
        checkedInput.each(function(){            
            let item = `<div class="lightboxItem"><span>${$(this).siblings('span').text()}</span><button class="deleteTag" data-value="${$(this).val()}"><i class="fas fa-times"></i></button></div>`
            location.append(item)                     
        })        
        $("section.lightBoxBG").removeClass('dis-b').addClass('dis-n')
        $(`section.${tagName}-lightBox`).removeClass('dis-b').addClass('dis-n')
    })

    $(".editNewDataBtn").on("click",function(){ //燈箱確定按鈕，生成新資料
        let newData = $(this).data('newdata')
        let location = $(`button.btn-openLightBox[data-lb='${newData}']`).siblings('.editNewData') //新資料放置位置
        let inputIDName = newData.split('-')[1] //新資料的輸入框
        var state = true
        var text = ''
        if (newData.indexOf('edit-teacherID') > -1) {
            let status = checkID($(`input#${inputIDName}`).val()).status            
            if (!status) {                
                state = false
                text = checkID($(`input#${inputIDName}`).val()).text                             
            }
        }else if (newData.indexOf('edit-teacherBank') > -1) {
            let bankNum = $("input#bankNum").val()
            let subBankNum = $("input#subBankNum").val()
            let accountNum = $("input#accountNum").val()
            if (bankNum && subBankNum && accountNum) {
                $("p.editNewData.bankNum").text(`銀行代碼：${bankNum}`)
                $("p.editNewData.subBankNum").text(`分行代碼：${subBankNum}`)
                $("p.editNewData.accountNum").text(`匯款帳號：${accountNum}`)
            }else if (!bankNum && !subBankNum && !accountNum) {
                $("p.editNewData.bankNum").text('')
                $("p.editNewData.subBankNum").text('')
                $("p.editNewData.accountNum").text('')
            }else {
                state = false
                text = '請輸入完整的匯款資訊'
            }
        }

        if (state) {            
            location.text($(`input#${inputIDName}`).val())
            $("section.lightBoxBG").removeClass('dis-b').addClass('dis-n')
            $(`section.${newData}-lightBox`).removeClass('dis-b').addClass('dis-n')
        }else {
            openlightBoxAlert(text)
        }

    })

    //lesson-content.html 審核區塊
    // $("input[name='verify']").on("change",function(){
    //     $("#lessonVerifyFile").val('')
    //     $(".lessonVerifyFileText").text('')
    // })

    $("#schoolType").on("change",function(){        
        if ($(this).val() == 1) {
            console.log('國小！！年級的ajax在這~~~')
        }else if ($(this).val() == 2) {
            console.log('國中!!!!!年級的ajax在這~~~')
        }else if ($(this).val() == 3) {
            console.log('高中年級的ajax在這~~~')
        }
    })

    $(".btn-search").on("click",function(){
        console.log('搜尋在這邊～～～老師姓名：：' + $("input[name='teacherName']").val())
        console.log('搜尋在這邊～～～履行合約：：' + $("input[name='state']:checked").val())
        console.log('搜尋在這邊～～～合約時限：：' + $("input[name='available']:checked").val())        
    })

    $(".btn-submit").on("click",function(){
        let type = $(this).data('type')
        if (type.indexOf('teacherCreate') > -1) {
            checkTeacher(type)
        }else if (type.indexOf('contractCreate') > -1) {
            checkContract(type)
        }else if (type.indexOf('lessonCreate') > -1) {
            checkLessonOrProp(type)
        }else if (type.indexOf('lessonContentVerify') > -1) {
            contentVerify(type)
        }else if (type.indexOf('lessonEditTeacher') > -1) {
            contentEditTeacher(type)
        }else if (type.indexOf('lessonEditAdmin') > -1) {
            contentEditAdmin(type)
        }else if (type.indexOf('teacherEdit') > -1) {
            checkTeacher(type)
        }else if (type.indexOf('propoGroupCreate') > -1) {
            checkLessonOrProp(type)
        }else if (type.indexOf('propoBasicCreate') > -1) {
            checkLessonOrProp(type)
        }else if (type.indexOf('propoGroupContentVerify') > -1) {
            contentVerify(type)
        }else if (type.indexOf('propoBasicContentVerify') > -1) {
            contentVerify(type)
        }else if (type.indexOf('propoGroupEditTeacher') > -1) {
            contentEditTeacher(type)
        }else if (type.indexOf('propoBasicEditTeacher') > -1) {
            contentEditTeacher(type)
        }else if (type.indexOf('propoGroupEditAdmin') > -1) {
            contentEditAdmin(type)
        }else if (type.indexOf('propoBasicEditAdmin') > -1) {
            contentEditAdmin(type)
        }else if (type.indexOf('contractMatCreate') > -1) {
            checkContract(type)
        }
    })

    function openlightBoxAlert(text) {
        console.log(text)
        $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
        $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
        $("section.lightBoxAlert .container div").text(text)
    }
    
    function checkTeacher(type) {        
        let create = type=='teacherCreate'? true : false;        
        let status = true
        let text = ''
        let phoneCheck = /^[09]{2}[0-9]{8}$/;
        let emailCheck = /^([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22))*\x40([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d))*$/;

        if (create && !$("#teacherName").val()) {
            status = false
            text = '請填寫姓名'
        }else if (create && !checkID($("#teacherID").val()).status) {
            status = false
            text = checkID($("#teacherID").val()).text
        }else if (!$("#teacherEmail").val()) {
            status = false
            text = '請輸入的Email'
        }else if (!emailCheck.test($("#teacherEmail").val())){
            text = "請輸入正確的Email";
            status = false;
        }else if (!$("#teacherPhone").val()) {
            status = false
            text = '請輸入的手機號碼'
        }else if (!phoneCheck.test($("#teacherPhone").val())){
            text = "請輸入正確的手機號碼";
            status = false;
        }else if ($("#specialSkill").val() == 0) {
            status = false
            text = '請選擇領域'
        }else if ($("input.identityState:checked").length == 0) {
            status = false
            text = '請勾選系統身份'
        }else if ($("#select-school").val() == 0) {
            status = false
            text = '請選擇服務學校'
        }else if (create && !$("#bankNum").val()) {
            status = false
            text = '請填寫匯款銀行'
//        }else if (create &&!$("#subBankNum").val()) {
//            status = false
//            text = '請填寫分行'
        }else if (create &&!$("#accountNum").val()) {
            status = false
            text = '請填寫匯款帳號'
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }else {
        	if (type == 'teacherCreate') {
            	$("#mainForm").attr("action", "/teacher/addSubmit");
                $("#mainForm").submit();
                console.log('老師帳號新增驗證通過')
            }else if (type == 'teacherEdit') {
            	$("#mainForm").attr("action", "/teacher/editSubmit");
                $("#mainForm").submit();
                console.log('老師帳號修改驗證通過')
            }
        }
    }

    function checkID(idNum) {          
        let idHeader = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
        let idval = idNum.toUpperCase();
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
        if (idNum == '') {
            return {
                status : false,
                text : '請填寫身份證字號'
            }
        }else if (idval.length != 10) {
            return {
                status : false,
                text : '身份證字號長度錯誤'
            }
        }else if (isNaN(idval.substr(1,9)) || (!/^[A-Z]$/.test(idval.substr(0,1)))) {
            return {
                status : false,
                text : '請輸入正確格式的身份證字號'
            }
        }else if (((s % 10) == 0 && checkNum == 0 ) || (10 - (s % 10)) == checkNum){            
            return {
                status : true,
                text : ''
            }
        }else {            
            return {
                status : false,
                text : '請輸入正確的身份證字號'
            }
        }        
    }

    function checkContract(type) {
        let material = type == 'contractMatCreate' ? true :false        
        let status = true
        let text = ''
        let numCheck = /^(|[1-9][0-9]*)$/
        
        if (!$("#contractNumA").val()) {
            status = false
            text = '請輸入授權於臺灣知識庫合約編號'
        }else if ($(".contractFileTextA").text() == '') {
            status = false
            text = '請選擇授權於臺灣知識庫合約檔案'
        }else if (material && !$("#2ndPartyA").val()) {
            status = false
            text = '請輸入授權於臺灣知識庫乙方名稱'
        }else if (!$("#contractNumB").val()) {
            status = false
            text = '請輸入授權於授權於中華未來教育學會合約編號'
        }else if ($(".contractFileTextB").text() == '') {
            status = false
            text = '請選擇授權於中華未來教育學會合約檔案'
        }else if (material && !$("#2ndPartyB").val()) {
            status = false
            text = '請輸入授權於中華未來教育學會乙方名稱'
        }else if (material && $("input[name='contractType']:checked").length == 0) {
            status = false
            text = '請勾選類型'
        }else if (!$("#contractStart").val()) {
            status = false
            text = '請輸入合約開始時間'
        }else if (!$("#contractEnd").val()) {
            status = false
            text = '請輸入合約結束時間' 
        }else if  (Date.parse($("#contractStart").val()) >= Date.parse($("#contractEnd").val())) {
            status = false
            text = '合約結束時間請大於開始時間'
        }else if (!$("#lessonNum").val() && !$("#propoBasicNum").val() && !$("#propoGroupNum").val()) {
            status = false
            text = '合約授權內容必須至少填寫一欄'
        }else if ($("#lessonNum").val() && !numCheck.test($("#lessonNum").val())) {
            status = false
            text = '請輸入正確的教案數量'
        }else if ($("#propoBasicNum").val() && !numCheck.test($("#propoBasicNum").val())) {
            status = false
            text = '請輸入正確的命題基本題數量'
        }else if ($("#propoGroupNum").val() && !numCheck.test($("#propoGroupNum").val())) {
            status = false
            text = '請輸入正確的命題題組題數量'
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }else {
            if (type == 'contractCreate') {
                $("#mainForm").attr("action", "/contract/teacher/addSubmit");
                $("#mainForm").submit();
                console.log('新增合約驗證通過')
            }else {
            	$("#mainForm").attr("action", "/contract/material/addSubmit");
                $("#mainForm").submit();
                console.log('新增素材合約驗證通過')
            }
        }
    }

    function checkLessonOrProp(type) {        
        let status = true
        let text = ''

        if ($("#contractNum").val() == 0) {
            status = false
            text = '請選擇合約序號'
        }else if (!$("input#lessonName").val()) {
            status = false
            if (type == 'lessonCreate') {
                text = '請輸入教案名稱'
            }else {
                text = '請輸入命題名稱'
            }                
        }else if ($("#specialSkill").val() == 0) {
            status = false
            text = '請選擇學習領域'
        }else if ($("input[name='subject']:checked").length == 0) {
            status = false
            text = '請選擇學科'
        }else if ($("#schoolType").val() == 0) {
            status = false
            text = '請選擇學制'
        }else if ($("input[name='grade']:checked").length == 0) {
            status = false
            text = '請勾選年級'
        }else if ($(".lessonFileText").text() == '') {
            status = false
            text = '請選擇檔案'
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }else {
            let text = ''
            $(".keyWordInput").siblings('.lightboxItem').each(function(){   
                text += `,${$(this).text()}`
            })          
            $(".keyWordInput").val(text.substring(1))      
            console.log('關鍵字的input hidden：：' + $(".keyWordInput").val())

            $(".lightBoxInput").each(function(){
                var lightBoxText = ''
                $(this).siblings('.lightboxItem').each(function(){
                    lightBoxText += `,${$(this).find('span').text()}`                    
                })
                $(this).val(lightBoxText.substring(1))
                console.log('核心素養總綱/領綱、學習表現、學習內容input hidden::' + $(this).val())
            })

            if (type == 'lessonCreate') {
            	$("#mainForm").attr("action", "/lesson/addSubmit");
                $("#mainForm").submit();
                console.log('教案驗證通過')
            }else if (type == 'propoGroupCreate') {
            	$("#mainForm").attr("action", "/proposition/group/addSubmit");
                $("#mainForm").submit();
                console.log('命題-題組題驗證通過')
            }else if (type == 'propoBasicCreate') {
            	$("#mainForm").attr("action", "/proposition/basic/addSubmit");
                $("#mainForm").submit();
                console.log('命題-基本題驗證通過')
            }
        }
    }

    function contentVerify(type) {        
        let status = true
        let text = ''
        if ($("input[name='verify']:checked").length == 0) {
            status = false
            text = '請選擇審核結果'
        }else if ($("input[name='verify']:checked").val() == 0 && $(".lessonVerifyFileText").text() == '') {
            status = false
            text = '請選擇審核回饋檔案'
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }else {
        	if (type == 'lessonContentVerify') {
            	$("#mainForm").attr("action", "/lesson/audit");
                $("#mainForm").submit();
                console.log('教案詳細資料審核')
            }else if (type == 'propoGroupContentVerify') {
            	$("#mainForm").attr("action", "/proposition/group/audit");
                $("#mainForm").submit();
                console.log('命題-題組題：：詳細資料審核')
            }else if (type == 'propoBasicContentVerify') {
            	$("#mainForm").attr("action", "/proposition/basic/audit");
                $("#mainForm").submit();
                console.log('命題-基本題：：教案詳細資料審核')
            }
        }
    }

    function contentEditTeacher(type) {        
        let status = true
        let text = ''
        if ($(".lessonVerifyFileText").text() == '') {
            status = false
            text = '請選擇修訂檔案'
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }else {
        	if (type == 'lessonEditTeacher') {
            	$("#mainForm").attr("action", "/lesson/editSubmit");
                $("#mainForm").submit();
                console.log('教案檔案修訂通過')
            }else if (type == 'propoGroupEditTeacher') {
            	$("#mainForm").attr("action", "/proposition/group/editSubmit");
                $("#mainForm").submit();
                console.log('命題-題組題：：檔案修訂通過')
            }else if (type == 'propoBasicEditTeacher') {
            	$("#mainForm").attr("action", "/proposition/basic/editSubmit");
                $("#mainForm").submit();
                console.log('命題-基本題：：檔案修訂通過')
            }
        }
    }

    function contentEditAdmin(type) {        
        let status = true
        let text = ''
        if (!$("input#lessonName").val()) {
            status = false            
            if (type == 'lessonEditAdmin') {
                text = '請輸入教案名稱'
            }else {
                text = '請輸入命題名稱'
            }
        }else if ($("#specialSkill").val() == 0) {
            status = false
            text = '請選擇學習領域'
        }else if ($("input[name='subject']:checked").length == 0) {
            status = false
            text = '請選擇學科'
        }else if ($("#schoolType").val() == 0) {
            status = false
            text = '請選擇學制'
        }else if ($("input[name='grade']:checked").length == 0) {
            status = false
            text = '請勾選年級'        
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }else {
            let ketText = ''
            $(".keyWordInput").siblings('.lightboxItem').each(function(){   
                ketText += `,${$(this).text()}`
            })          
            $(".keyWordInput").val(ketText.substring(1))      
            console.log('關鍵字的input hidden：：' + $(".keyWordInput").val())
            
            $(".lightBoxInput").each(function(){
                var lightBoxText = ''
                $(this).siblings('.lightboxItem').each(function(){
                    lightBoxText += `,${$(this).find('span').text()}`                    
                })
                $(this).val(lightBoxText.substring(1))
                console.log('核心素養總綱/領綱、學習表現、學習內容input hidden::' + $(this).val())
            })

            if (type == 'lessonEditAdmin') {
            	$("#mainForm").attr("action", "/lesson/updateSubmit");
                $("#mainForm").submit();
                console.log('教案編輯驗證通過')
            }else if (type == 'propoGroupEditAdmin') {
            	$("#mainForm").attr("action", "/proposition/group/updateSubmit");
                $("#mainForm").submit();
                console.log('命題-題組題：：編輯驗證通過')
            }else if (type == 'propoBasicEditAdmin') {
            	$("#mainForm").attr("action", "/proposition/basic/updateSubmit");
                $("#mainForm").submit();
                console.log('命題-基本題：：編輯驗證通過')
            }
            
        }
    }

})