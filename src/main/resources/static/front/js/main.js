$(function(){
    $(".menu").mouseenter(function(){ //header子選單        
        $(this).find("ul.sub-menu").removeClass('dis-n')
    })

    $(".menu").mouseleave(function(){ //header子選單        
        $(this).find("ul.sub-menu").addClass('dis-n')
    })

    $(".account-name").mouseenter(function(){ //header子選單        
        $(this).find('ul').removeClass('dis-n')
    })

    $(".account-name").mouseleave(function(){ //header子選單        
        $(this).find('ul').addClass('dis-n')
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
    
    $(document).on("change",".btnFile",function(event){ //上傳檔案
        let text = $(event.target).data('text')        
        let fileData = event.target.files[0]        
        if (text.indexOf('attachmentText') > -1) {  //新增教案/命題的附件
            $(this).parent().parent().parent().find(`.${text}`).text(fileData.name).removeClass('dis-n')
            $(this).parent().parent().parent().find(`.${text}`).css('margin-top','5px')                   
            $(this).parent().parent().parent().find('i.attach-plus').removeClass('dis-n')
        }else if (text.indexOf('contractMatText') > -1) { //新增教案/命題的素材授權申請
            $(this).parent().parent().parent().find(`.${text}`).text(fileData.name).removeClass('dis-n')
            $(this).parent().parent().parent().find(`.${text}`).css('margin-top','5px')                   
            $(this).parent().parent().parent().find('i.contractMat-plus').removeClass('dis-n')
        }else {
            $(this).parent().parent().find(`.${text}`).text(fileData.name)
            $(this).parent().parent().find(`.${text}`).css('margin-top','10px')
        }        
    })

    $(".btn-closeLightBox").on("click",function(){ //關閉燈箱
        let lightBox = $(this).data('lb')
        if (lightBox.indexOf('teacherImportData') > -1) {            
            $("#teacherImportFile").val('')       
            $("p.teacherImportFileText").text('')            
        }else if (lightBox == 'create-categorySubject') {   //分類管理--學科管理--關閉新增燈箱
            $(".create-categorySubject-lightBox input[type='text']").val('')
            $(".create-categorySubject-lightBox input[type='checkbox']").prop('checked',false)
            $(".create-categorySubject-lightBox div.checkbox-square").removeClass('active')
        }


        $("section.lightBoxBG").removeClass('dis-b').addClass('dis-n')
        $(`section.${lightBox}-lightBox`).removeClass('dis-b').addClass('dis-n')
    })
    
    $(".btn-closeMemolightBox").on("click",function(){ //關閉提醒燈箱        
        let lightBox = $(this).data('lb')
        if (lightBox == 'memo') {
            let btnType = $(this).data('type')
            if (btnType == 'editPassword') {
                location.href = "./member.html"
            }else if (btnType == 'passEditPassword') {
                alert("暫不修改密碼，需寫進log裡")
            }
        }        
        $("section.lightBoxBG").removeClass('dis-b').addClass('dis-n')
        $(`section.${lightBox}-lightBox`).removeClass('dis-b').addClass('dis-n')
        
    })

    $(".btn-closelightBoxAlert").on("click",function(){ //關閉警告燈箱        
        $("section.lightBoxBG").removeClass('dis-b').addClass('dis-n')
        $(`section.lightBoxAlert`).removeClass('dis-b').addClass('dis-n')
    })

    $(".openLightBox").on("click",function(){   //打開燈箱
        let lightBox = $(this).data('lb')
        let state = true
        let alertText = ''

        if (state) {
            $("section.lightBoxBG").removeClass('dis-n').addClass('dis-b')
            $(`section.${lightBox}-lightBox`).removeClass('dis-n').addClass('dis-b')
        }else {
            openlightBoxAlert(alertText)
        }
    })

    $(".btn-openLightBox").on("click",function(){ //打開燈箱
        let lightBox = $(this).data('lb')
        let state = true
        let alertText = ''
        
        if (lightBox == 'create-categorySubGrade' && $(".subItemGroup").hasClass('none')) {            
            state = false            
            alertText = '請先選擇學制'
        }else if (lightBox == 'verify') {            
            $("section.verify-lightBox .container .schoolType").text($(this).parent().siblings('.listSchoolType').text())
            $("section.verify-lightBox .container .subject").text($(this).parent().siblings('.listSubject').text())
            $("section.verify-lightBox .container .name").text($(this).parent().siblings('.listName').text())
            $("section.verify-lightBox .container .school").text($(this).parent().siblings('.listSchool').text())
            $("section.verify-lightBox .editNewDataBtn").data('id',$(this).data('id'))
        }else if (lightBox == 'edit-categoryMaterial' || lightBox == 'edit-categoryGrade') {    //分類管理--素材分類、學制年級（學制）--編輯button
            let item = $(this).parent().siblings('.item-group').find('.active')
            if (item.length == 0) {
                state = false   
                alertText = '請先選擇欲編輯項目'              
            }else {
                $(`section.${lightBox}-lightBox .container p`).text(item.text())                
                $(`section.${lightBox}-lightBox .container input`).data('id',item.attr('id'))                                
            }   
        }else if (lightBox == 'edit-categorySubGrade' && $(".subItemGroup").hasClass('none')) {     //分類管理--學制年級（年級）--編輯button
            state = false            
            alertText = '請先選擇學制'
        }else if (lightBox == 'edit-categorySubGrade' && !$(".subItemGroup").hasClass('none')) {    //分類管理--學制年級（年級）--編輯button
            let item = $(this).parent().siblings('.item-group').find('.active')
            if (item.length == 0) {
                state = false   
                alertText = '請先選擇欲編輯項目'              
            }else {
                $(`section.${lightBox}-lightBox .container p`).text(item.text())                
                $(`section.${lightBox}-lightBox .container input`).data('id',item.attr('id'))                                
            }
        }

        if (state) {
            $("section.lightBoxBG").removeClass('dis-n').addClass('dis-b')
            $(`section.${lightBox}-lightBox`).removeClass('dis-n').addClass('dis-b')
        }else {
            openlightBoxAlert(alertText)
        }
    })    

    $(".createTagBtn").on("click",function(){   //燈箱確定按鈕，生成tag
        let tagName = $(this).data('tag') 
        // let location = $(`button.btn-openLightBox[data-lb='${tagName}']`).parent()  //tag放置位置
        let location = $(`button.btn-openLightBox[data-lb='${tagName}']`).siblings('.lightboxItemWrap') //tag放置位置
        let checkedInput = $(this).parent().find('input:checked') //燈箱中被勾選的input
        location.find('.lightboxItem').remove() //先移除全部tag
        if (checkedInput.length != 0) {
            checkedInput.each(function(){            
                let item = `<div class="lightboxItem"><span>${$(this).siblings('span').text()}</span><button class="deleteTag" data-value="${$(this).val()}"><i class="fas fa-times"></i></button></div>`
                location.append(item)                     
            })
            location.css('margin-top','10px')
        }else {
            location.css('margin-top','0px')
        }
        $("section.lightBoxBG").removeClass('dis-b').addClass('dis-n')
        $(`section.${tagName}-lightBox`).removeClass('dis-b').addClass('dis-n')
    })

    $(".editNewDataBtn").on("click",function(){ //燈箱確定按鈕，生成新資料
        let newData = $(this).data('newdata')
        let location = $(`button.btn-openLightBox[data-lb='${newData}']`).siblings('.editNewData') //老師編輯-新資料放置位置
        let inputIDName = newData.split('-')[1] //新資料的輸入框
        var state = true
        var category = false
        var member = false
        var text = ''
        if (newData == 'edit-teacherID') {
            let status = checkID($(`input#${inputIDName}`).val()).status            
            if (!status) {                
                state = false
                text = checkID($(`input#${inputIDName}`).val()).text                             
            }
        }else if (newData == 'edit-teacherBank') {
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
        }else if (newData == 'edit-categoryMaterial' || newData == 'edit-categoryGrade') { //分類管理--素材分類、學制年級（學制）--編輯燈箱的「確定」button
            let val = $(`input#${inputIDName}`).val()
            if (val != '') {
                let itemId = $(`section.${newData}-lightBox .container input`).data('id')
                console.log('要編輯的 id 為：'+itemId+'，新資料為：'+val)
                $(`p#${itemId}`).text(val)
                $(`input#${inputIDName}`).val('')
            }
        }else if (newData == 'edit-categorySubject') {  //分類管理--學科管理--編輯燈箱的「確定」button
            let val = $(`input#${inputIDName}`).val()
            let subVal = $(`input#sub${inputIDName}`).val()
            let itemId = $(`section.${newData}-lightBox button.editNewDataBtn`).data('id')
            let itemNo = $(`section.${newData}-lightBox button.editNewDataBtn`).data('no')
            if (val != '' && subVal != ''&& $(".schoolType:checked").length >0) {                
                $(`div#${itemId} span`).eq(0).text(val)
                $(`div#${itemId} span`).eq(1).text(subVal)    
                //清空表格內的值                   
                $("input.schoolType").each(function(){                
                    $(`div#${itemId}`).siblings(`.${$(this).attr('id')}`).text('')
                })
                //渲染新的值在表格內
                let schooltTypeArr = []
                $("input.schoolType:checked").each(function(){                
                    $(`div#${itemId}`).siblings(`.${$(this).attr('id')}`).text(itemNo)
                    schooltTypeArr.push(`${$(this).attr('id')}`)
                })
                console.log('要編輯的 id 為：'+itemId+'，新資料為：'+val +'，新縮寫資料為：'+subVal+'，新的學制為：'+schooltTypeArr)       
                //清空燈箱內的值
                $(".edit-categorySubject-lightBox .container-grid input[type='text']").val('')   
                $(".edit-categorySubject-lightBox .container-grid input[type='checkbox']").prop('checked',false)
                $(".edit-categorySubject-lightBox .container-grid .checkbox-square").removeClass('active')     
            }else {
                state = false
                text = '請將欄位填寫完整'
            }                        
        }else if (newData == 'delete-categorySubject') {  //分類管理--學科管理--停用燈箱的「確定」button
            let itemId = $(`section.${newData}-lightBox button.editNewDataBtn`).data('id') 
            let deleteBtn = $(`#${itemId}`).siblings().find("[data-lb='delete-categorySubject']")
            //將「停用」按鈕。換成「啟用」，顯示「編輯」按鈕
            deleteBtn.parent().parent().addClass('failed-list')
            deleteBtn.parent().prev().children().remove()
            deleteBtn.addClass('dis-n').siblings().removeClass('dis-n')
            console.log('要停用的 id 為：' + itemId )
        }else if (newData == 'restart-categorySubject') {    //分類管理--學科管理--啟用燈箱的「確定」button            
            let itemId = $(`section.${newData}-lightBox button.editNewDataBtn`).data('id')            
            let deleteBtn = $(`#${itemId}`).siblings().find("[data-lb='delete-categorySubject']")
            //將「啟用」按鈕。換成「停用」，隱藏「編輯」按鈕
            deleteBtn.parent().parent().removeClass('failed-list')
            deleteBtn.parent().prev().html("<button class='btn-sm openLightBox' data-lb='edit-categorySubject'>編輯</button>")
            deleteBtn.removeClass('dis-n').siblings().addClass('dis-n')
            console.log('要啟用的 id 為：' + itemId )
        }else if (newData == 'create-categorySubject') {    //分類管理--學科管理--新增燈箱的「確定」button
            let itemName = $("input#createCategorySubject").val()
            let itemSubName = $("input#createSubcategorySubject").val()
            let itemNumber = $(".categorySubject-grid").length            
            if (itemName && itemSubName && $(".createSchoolType:checked").length >0) {
                let html =  `<div class="categorySubject-grid">
                            <div class="subjectName" id='${itemName}' data-no='${itemNumber}'>
                                <span>${itemName}</span>/<span>${itemSubName}</span>
                            </div>
                            <div class="elementary">${$("#createElementary").is(':checked') ? itemNumber : ''}</div>
                            <div class="junior">${$("#createJunior").is(':checked') ? itemNumber : ''}</div>
                            <div class="high">${$("#createHigh").is(':checked') ? itemNumber : ''}</div>                            
                            <div><button class="btn-sm openLightBox" data-lb="edit-categorySubject">編輯</button></div>
                            <div>
                                <button class="btn-smRed openLightBox" data-lb="delete-categorySubject">停用</button>
                                <button class="btn-sm openLightBox dis-n" data-lb="restart-categorySubject">啟用</button>
                            </div>
                        </div>`
                category = true 
                $(".create-categorySubject-lightBox input[type='checkbox']").prop('checked',false)
                $(".create-categorySubject-lightBox div.checkbox-square").removeClass('active')
                $(".grid-group").append(html) 
                console.log('新增的名稱：：'+itemName+'/新增的縮寫'+itemSubName)
            }else {
                state = false
                text = '請將欄位填寫完整'
            }
        }else if (newData == 'create-categoryMaterial' || newData == 'create-categoryGrade') {   //分類管理--素材分類、學制年級（學制）--新增燈箱的「確定」button
            let categoryItem = $("section.lightBoxContent .container .categoryItem").val()
            if (categoryItem) {
                let html = `<p class="item item-grid" id='${categoryItem}'>${categoryItem}</p>`  
                $(".category-block .item-group").append(html)
                category = true
                console.log('新增的名稱為：：'+categoryItem)
            }        
        }else if (newData == 'create-categorySubGrade' ) { // 分類管理--學制年級（年級）--新增燈箱的「確定」button
            let categoryItem = $("section.create-categorySubGrade-lightBox .container .categoryItem").val()
            if (categoryItem) {
                let html = `<p class="item item-grid" id='${categoryItem}'>${categoryItem}</p>`  
                $(".category-block .subItemGroup").append(html)
                let schoolType = $(".category-block .item-group .item.active").attr('id')
                console.log('學制為：：'+schoolType+',要新增的年級在這～～～'+categoryItem)
                category = true
            }
        }else if (newData == 'edit-categorySubGrade') {     // 分類管理--學制年級（年級）--編輯燈箱的「確定」button
            let val = $(`input#${inputIDName}`).val()
            if (val != '') {
                let schoolType = $(".category-block .item-group .item.active").attr('id')
                let itemId = $(`section.${newData}-lightBox .container input`).data('id')
                console.log('學制為：：'+schoolType+'要編輯的 id 為：'+itemId+'，新資料為：'+val)
                $(`p#${itemId}`).text(val)
                $(`input#${inputIDName}`).val('')                                
            }
        }else if (newData == 'edit-password') { //會員中心-修改密碼
            if ($("#password").val() != $("#check-password").val()) {
                state = false
                text = '密碼輸入不一致'
            }else if (!$("#password").val() && !$("#check-password").val()) {
                member = true
            }else {
                member = true
                $("#passwordText").text('*'.repeat($("#password").val().length))  
                openlightBoxAlert('密碼修改完成')
                console.log('密碼修改完成，新密碼：：'+$("#password").val())
            }            
        }else if (newData == 'verify') { //老師列表-審核燈箱
            if ($("input[name='verify']:checked").length != 0) {
                let teacherID = $(this).data('id')
                alert('老師身分證字號::'+teacherID+'是否通過::'+$("input[name='verify']:checked").val())
                $(`section.${newData}-lightBox input[name='verify']`).prop('checked',false)
                $(`section.${newData}-lightBox div.radio-circle`).removeClass('active')                
            }           
        }else if (newData == 'forgetPassword') {
            let account = $("input#account").val()
            if (account) {
//                alert('註冊帳號：：'+account)
//                openlightBoxAlert('已將通知信寄到您註冊信箱，請至信箱進行密碼重置流程')     
            }            
        }

        if (state && !category && !member) { //編輯老師
            location.text($(`input#${inputIDName}`).val())
            $("section.lightBoxBG").removeClass('dis-b').addClass('dis-n')
            $(`section.${newData}-lightBox`).removeClass('dis-b').addClass('dis-n')
        }else if (category) { //分類管理的「新增」功能，清空燈箱值
            $("section.lightBoxBG").removeClass('dis-b').addClass('dis-n')
            $(`section.${newData}-lightBox`).removeClass('dis-b').addClass('dis-n')
            $("section.lightBoxContent .categoryItem").val('')
            $("section.lightBoxContent .categorySubItem").val('')
        }else if (member) { //會員中心的「更改密碼」功能，清空燈箱值
            $("section.lightBoxBG").removeClass('dis-b').addClass('dis-n')
            $(`section.${newData}-lightBox`).removeClass('dis-b').addClass('dis-n')
            $(`section.${newData}-lightBox input`).val('')
        }else {
            openlightBoxAlert(text)
        }
    })
  
    $(".btnDownFile").on('click',function(){
        let btntype = $(this).data('btntype')
        if (btntype == 'exampleContractMat') {
            alert('下載素材授權申請範例檔')
        }else if (btntype == 'exampleTeacherList') {
            location.href="/teacher/download";
        }else if (btntype == 'reportLessonPropData') {  //報表匯出--教案/命題報表            
            let projectType = $("#lessonProp_projectType").val()
            let schoolType = $("#lessonProp_schoolType").val()
            let subject = $("#lessonProp_subject").val()                        
            if (projectType == 0 && schoolType == 0 && subject == 0) {
                openlightBoxAlert('請輸入任一篩選條件')
            }else {
                console.log('/創作類型：：'+ projectType + '/學制：：'+ schoolType + '/學科：：'+ subject)
                $(this).parent().siblings('.text').find('.numText').text('教案/命題搜尋成功後，更改數量～～～')
            }
       
        }else if (btntype == 'reportContractData') {    //報表匯出--老師授權合約報表
            let contractTime_start = $("#contract_contractTime").val().split(' ~ ')[0]
            let contractTime_end = $("#contract_contractTime").val().split(' ~ ')[1]
            let schoolType = $("#contract_schoolType").val()
            let subject = $("#contract_subject").val()                        
            let contractType = $(".contractTime:checked").val()                                    
            if (contractTime_start == '' && contractTime_end == undefined && schoolType == 0 && subject == 0 && contractType == undefined) {
                openlightBoxAlert('請輸入任一篩選條件')
            }else {
                console.log('合約開始時間：：'+contractTime_start +'合約結束時間：：'+contractTime_end + '/學制：：'+ schoolType + '/學科：：'+ subject + '/合約狀態：：'+ contractType)
                $(this).parent().siblings('.text').find('.numText').text('老師授權合約搜尋成功後，更改數量～～～')
            }

        }else if (btntype == 'reportTeacherData') {    //報表匯出--老師資料報表            
            let identityState = $("input:radio[name='identityState']:checked").val()
            let schoolType = $("#teacher_schoolType").val()
            let subject = $("#teacher_subject").val()
            if (identityState == undefined && schoolType == 0 && subject == 0) {
                openlightBoxAlert('請輸入任一篩選條件')
            }else {
                console.log('/系統身份：：' + identityState + '/學制：：'+ schoolType + '/學科：：'+subject)
                $(this).parent().siblings('.text').find('.numText').text('老師資料搜尋成功後，更改數量～～～')
            }            
        }
    })
    
    
    
    $(".btn-search").on("click",function(){
        let searchType = $(this).data('search')
        if (searchType == 'contractList' || searchType == 'contractMatList') {
            searchContract (searchType)
        }else if (searchType == 'teacherList') {
            searchTeacher ()
        }else if (searchType == 'lessonList' || searchType == 'propBasicList' || searchType == 'propGroupList') {
            searchLessonProp (searchType)
        }
    })

    $(".btn-submit").on("click",function(){
        let type = $(this).data('type')
        if (type.indexOf('teacherCreate') > -1) {
            checkTeacher(type)
        }else if (type.indexOf('contractCreate') > -1) {
            checkContract(type)
        }else if (type.indexOf('lessonCreate') > -1) {
            checkLessonOrProp(type)
        }else if (type == 'lessonContentVerify') {
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
        }else if (type == 'propoGroupContentVerify') {
            contentVerify(type)
        }else if (type == 'propoBasicContentVerify') {
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
        }else if (type.indexOf('register') > -1) {
            checkTeacher(type)
        }else if (type == 'lessonContentVerify_principal') {
            contentVerify_principal(type)
        }else if (type == 'propoGroupContentVerify_principal') {
            contentVerify_principal(type)
        }else if (type == 'propoBasicContentVerify_principal') {
            contentVerify_principal(type)
        }else if (type == 'lessonContentFinish' || type == 'propoGroupContentFinish' || type == 'propoBasicContentFinish') {
            contentFinish(type)
        }
    })

    $(".btnOpenMail").on("click",function(e){
        e.preventDefault()
        window.open ("mail.html", 
        "newwindow", 
        "height=640, width=600, toolbar=no, resizable=no, location=no, status=no")
    })

    $(".btnForgetPassword").on("click",function(){ // 登入頁「忘記密碼」
        $("section.forgetPassword-lightBox").removeClass('dis-n')
        $("section.lightBoxBG").removeClass('dis-n')   
    })


    function openlightBoxAlert(text) {        
        $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
        $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
        $("section.lightBoxAlert .container div").text(text)
    }
    
    function checkTeacher(type) {        
        let create = type=='teacherCreate' || type=='register'? true : false;        
        let status = true
        let text = ''
        let phoneCheck = /^[09]{2}[0-9]{8}$/;
        let telCheck = /^0\d{1,2}-?\d{6,8}$/;        
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
        }else if (!$("#teacherTel").val()) {
            status = false
            text = '請輸入的市話號碼'
        }else if (!telCheck.test($("#teacherTel").val())){
            text = "請輸入正確的市話號碼";
            status = false;
        }else if ($("#schoolType").val() == 0) {
            status = false
            text = '請選擇學制'
        }else if ($("#subject").val() == 0) {
            status = false
            text = '請選擇學科'
        }else if (type!='register' && !$("input[name='position']:checked").val()) {            
            status = false
            text = '請選擇老師身份'
        }else if ($("input[name='position']:checked").attr('id') == 'teacher' && $("input.identityState:checked").length == 0) { //老師身份為「一般老師」時的必填欄位
            status = false
            text = '請勾選系統身份'            
        }else if (type=='register' && $("input.identityState:checked").length == 0) { //註冊時，需勾選系統身份
            status = false
            text = '請勾選系統身份'            
        }else if (type!='register' && $("input[name='position']:checked").attr('id') != 'teacher' && $("input.schoolType:checked").length == 0) { //老師身份為「校長」、「組長」時的必填欄位
            status = false
            text = '請勾選監督範圍的學制'
        }else if (type!='register' && $("input[name='position']:checked").attr('id') != 'teacher' && $("input.subject:checked").length == 0) { //老師身份為「校長」、「組長」時的必填欄位
            status = false
            text = '請勾選監督範圍的學科'
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
        }else if (create && !$("#residenceAddress").val()) {         
            status = false
            text = '請填寫完整戶籍地址'
        }else if (create && $("#residenceSelectZip").val() == 0) {
            status = false
            text = '請選擇戶籍地址對應的郵遞區號'
        }else if (create && !$("#address").val()) {
            status = false
            text = '請填寫完整通訊地址'
        }else if (create && $("#selectZip").val() == 0) {
            status = false
            text = '請選擇通訊地址對應的郵遞區號'
        }else if (!create && $("#editResidenceAddress").is(":checked") && !$("#residenceAddress").val()) {  //編輯老師+修改checkbox            
            status = false
            text = '請填寫完整戶籍地址'            
        }else if (!create && $("#editResidenceAddress").is(":checked") && $("#residenceSelectZip").val() == 0) {
            status = false
            text = '請選擇戶籍地址對應的郵遞區號'            
        }else if (!create && $("#editAddress").is(":checked") && !$("#address").val()) {
            status = false
            text = '請填寫完整通訊地址'            
        }else if (!create && $("#editAddress").is(":checked") && $("#selectZip").val() == 0) {
            status = false
            text = '請選擇通訊地址對應的郵遞區號'            
        }
        
        var checkAccountText = "";
        $.ajax({
            url: '/teacher/check',
            cache: false,
            async: false,
            dataType: 'text',
            type: 'POST',
            xhrFilds:{withCredentials:true},
            data: {
                id_no: $("#teacherID").val(),
                id: $("#id").val()
            },
            error: function(xhr) {
            	checkAccountText = '檢查帳號錯誤';                   
            },
            success: function(data) {              
                if(data != "T"){
                	checkAccountText = '帳號重複註冊請重新輸入帳號'  
                }
            }
        });   
        
        if (checkAccountText != "") {
            status = false
            text = checkAccountText         
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }else {
        	if (type == 'teacherCreate') {
            	$("#c_area").val($("#census_area").val());
                $("#address_area").val($("#area").val());
                $("#census_zip").val($("#residenceSelectZip").val());
                $("#address_zip").val($("#selectZip").val());
        		
            	$("#mainForm").attr("action", "/teacher/addSubmit");
                $("#mainForm").submit();
                console.log('老師帳號新增驗證通過')
            }else if (type == 'teacherEdit') {
            	if($("#census_city").val() != "" && $("#census_area").val() != "0" && $("#residenceSelectZip").val() != "0" && $("residenceAddress").val() != ""){
            		$("#c_city").val($("#census_city").val());
            		$("#c_area").val($("#census_area").val());
            		$("#census_road").val($("#residenceAddress").val());
            		$("#census_zip").val($("#residenceSelectZip").val());
            	}
            	
            	if($("#city").val() != "" && $("#area").val() != "0" && $("#selectZip").val() != "0" && $("address").val() != ""){
            		$("#address_city").val($("#city").val());
            		$("#address_area").val($("#area").val());
            		$("#address_road").val($("#address").val());
            		$("#address_zip").val($("#selectZip").val());
            	}
            	
            	$("#mainForm").attr("action", "/teacher/editSubmit");
                $("#mainForm").submit();
                console.log('老師帳號修改驗證通過')
            }else if (type == 'register') {
                $("#c_area").val($("#census_area").val());
                $("#address_area").val($("#area").val());
                $("#census_zip").val($("#residenceSelectZip").val());
                $("#address_zip").val($("#selectZip").val());
            	
                $("#mainForm").attr("action", "/teacher/registerSubmit");
            	$("#mainForm").submit();
                console.log('註冊驗證通過')
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
        
        if (material && !$("#contractNumA").val()) {
            status = false
            text = '請輸入授權於臺灣知識庫合約編號'
        }else if (material && $(".contractFileTextA").text() == '') {
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
        }else if (!material && $("#schoolType").val() == 0) {
            status = false
            text = '請選擇學制'
        }else if (!material && $("#subject").val() == 0) {
            status = false
            text = '請選擇學科'
        }else if (!$("#contractStart").val()) {
            status = false
            text = '請輸入合約開始時間'
        }else if (!$("#contractEnd").val()) {
            status = false
            text = '請輸入合約結束時間' 
        }else if  (Date.parse($("#contractStart").val()) >= Date.parse($("#contractEnd").val())) {
            status = false
            text = '合約結束時間請大於開始時間'
        }else if (!material && !$("#lessonNum").val() && !$("#propoBasicNum").val() && !$("#propoGroupNum").val()) {
            status = false
            text = '合約授權內容必須至少填寫一欄'
        }else if (!material && $("#lessonNum").val() && !numCheck.test($("#lessonNum").val())) {
            status = false
            text = '請輸入正確的教案數量'
        }else if (!material && $("#propoBasicNum").val() && !numCheck.test($("#propoBasicNum").val())) {
            status = false
            text = '請輸入正確的命題選擇題數量'
        }else if (!material && $("#propoGroupNum").val() && !numCheck.test($("#propoGroupNum").val())) {
            status = false
            text = '請輸入正確的命題混合題數量'
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
            text = '請選擇合約流水號'
        }else if (!$("input#lessonName").val() && type == 'lessonCreate') {
            status = false
            // if (type == 'lessonCreate') {
            text = '請輸入教案名稱'
            // }else {
                // text = '請輸入命題名稱'
            // }                
        // }else if ($("#schoolType").val() == 0) {
        //     status = false
        //     text = '請選擇學制'
        }else if ($("input[name='grade']:checked").length == 0) {
            status = false
            text = '請勾選年級'
        // }else if ($("#subject").val() == 0) {
        //     status = false
        //     text = '請選擇學科'
        }else if ($("input[name='crossSubject']:checked").length == 0) {
            status = false
            text = '請選擇跨科'
        }else if ($(".lessonFileText").text() == '') {
            status = false
            text = '請選擇原始檔'
        }else if ($(".lessonFilePDFText").text() == '') {
            status = false
            text = '請選擇PDF檔'
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }else {
            let text = ''
            $(".keyWordInput").siblings('.lightboxItem').each(function(){ //關鍵字input hidden
                text += `,${$(this).text()}`
            })          
            $(".keyWordInput").val(text.substring(1))      
            console.log('關鍵字的input hidden：：' + $(".keyWordInput").val())

            $(".lightboxItemWrap").each(function(){ //「核心素養總綱」、「核心素養領綱」、「學習表現」、「學習內容」input hidden
                var lightBoxText = ''                
                // $(this).siblings('.lightboxItem').each(function(){
                //     lightBoxText += `,${$(this).find('span').text()}`
                // })
                $(this).children('.lightboxItem').each(function(){
                    lightBoxText += `,${$(this).find('span').text()}`
                })
                $(this).siblings('.lightBoxInput').val(lightBoxText.substring(1))
                console.log('核心素養總綱/領綱、學習表現、學習內容input hidden::' + $(this).siblings('.lightBoxInput').val())
            })

            if (type == 'lessonCreate') {
            	$("#mainForm").attr("action", "/lesson/addSubmit");
                $("#mainForm").submit();
                console.log('教案驗證通過')
            }else if (type == 'propoGroupCreate') {
            	$("#mainForm").attr("action", "/proposition/group/addSubmit");
                $("#mainForm").submit();
                console.log('命題-混合題驗證通過')
            }else if (type == 'propoBasicCreate') {
            	$("#mainForm").attr("action", "/proposition/basic/addSubmit");
                $("#mainForm").submit();
                console.log('命題-選擇題驗證通過')
            }
        }
    }

    function contentVerify_principal(type) {        
        let status = true
        let text = ''
        if ($("input[name='verify']:checked").length == 0) {
            status = false
            text = '請選擇審核結果'
        }else if ($("input[name='verify']:checked").val() == 0 && $(".lessonVerifyFileText").text() == '' && !$(".lessonVerify textarea").val()) {
            status = false
            text = '請選擇審核回饋檔案或填寫審核回饋'
        }else if ($("input[name='verify']:checked").val() == 1 && $("input[name='verifyMethod']:checked").length == 0) {
            status = false
            text = '請選擇審核方式'
        }else if ($("input[name='verify']:checked").val() == 1 && $("input[name='verifyMethod']:checked").val() == 0 && $("#verifyTeacher").val() ==0) {
            status = false
            text = '請選擇審議委員'
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }else {
        	if (type == 'lessonContentVerify_principal') {
            	$("#mainForm").attr("action", "/lesson/audit");
                $("#mainForm").submit();
                console.log('校長--教案詳細資料審核')
            }else if (type == 'propoGroupContentVerify_principal') {
            	$("#mainForm").attr("action", "/proposition/group/audit");
                $("#mainForm").submit();
                console.log('校長--命題-混合題：：詳細資料審核')
            }else if (type == 'propoBasicContentVerify_principal') {
            	$("#mainForm").attr("action", "/proposition/basic/audit");
                $("#mainForm").submit();
                console.log('校長--命題-選擇題：：教案詳細資料審核')
            }
        }
    }

    function contentVerify(type) {        
        let status = true
        let text = ''
        if ($("input[name='verify']:checked").length == 0) {
            status = false
            text = '請選擇審核結果'
        }else if ($("input[name='verify']:checked").val() == 0 && $(".lessonVerifyFileText").text() == '' && !$(".lessonVerify textarea").val()) {
            status = false
            text = '請選擇審核回饋檔案或填寫審核回饋'
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }else {
        	if (type == 'lessonContentVerify') {
            	$("#mainForm").attr("action", "/lesson/audit");
                $("#mainForm").submit();
                console.log('審議委員--教案詳細資料審核')
            }else if (type == 'propoGroupContentVerify') {
            	$("#mainForm").attr("action", "/proposition/group/audit");
                $("#mainForm").submit();
                console.log('審議委員--命題-混合題：：詳細資料審核')
            }else if (type == 'propoBasicContentVerify') {
            	$("#mainForm").attr("action", "/proposition/basic/audit");
                $("#mainForm").submit();
                console.log('審議委員--命題-選擇題：：教案詳細資料審核')
            }
        }
    }

    function contentEditTeacher(type) {        
        let status = true
        let text = ''
        if ($(".lessonVerifyFileText").text() == '') {
            status = false
            text = '請選擇修正原始檔'
        }else if ($(".lessonVerifyPDFFileText").text() == '') {
            status = false
            text = '請選擇修正PDF檔'
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }else {
        	if (type == 'lessonEditTeacher') {
            	$("#mainForm").attr("action", "/lesson/editSubmit");
                $("#mainForm").submit();
                console.log('教案檔案修正通過')
            }else if (type == 'propoGroupEditTeacher') {
            	$("#mainForm").attr("action", "/proposition/group/editSubmit");
                $("#mainForm").submit();
                console.log('命題-混合題：：檔案修正通過')
            }else if (type == 'propoBasicEditTeacher') {
            	$("#mainForm").attr("action", "/proposition/basic/editSubmit");
                $("#mainForm").submit();
                console.log('命題-選擇題：：檔案修正通過')
            }
        }
    }

    function contentEditAdmin(type) {        
        let status = true
        let text = ''
        if (!$("input#lessonName").val() && type == 'lessonEditAdmin') {
            status = false            
            // if (type == 'lessonEditAdmin') {
                text = '請輸入教案名稱'
            // }else {
            //     text = '請輸入命題名稱'
            // }
        // }else if ($("#schoolType").val() == 0) {
        //     status = false
        //     text = '請選擇學制'
        }else if ($("input[name='grade']:checked").length == 0) {
            status = false
            text = '請勾選年級'        
        // }else if ($("#subject").val() == 0) {
        //     status = false
        //     text = '請選擇學科'
        }else if ($("input[name='crossSubject']:checked").length == 0) {
            status = false
            text = '請選擇跨科'
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }else {
            let ketText = ''            
            $(".keyWordInput").siblings('.lightboxItem').each(function(){   //關鍵字input hidden
                ketText += `,${$(this).text()}`
            })          
            $(".keyWordInput").val(ketText.substring(1))      
            console.log('關鍵字的input hidden：：' + $(".keyWordInput").val())
            
            $(".lightboxItemWrap").each(function(){ //「核心素養總綱」、「核心素養領綱」、「學習表現」、「學習內容」input hidden
                var lightBoxText = ''
                $(this).children('.lightboxItem').each(function(){
                    lightBoxText += `,${$(this).find('span').text()}`
                })
                $(this).siblings('.lightBoxInput').val(lightBoxText.substring(1))
                console.log('核心素養總綱/領綱、學習表現、學習內容input hidden::' + $(this).siblings('.lightBoxInput').val())
            })

            if (type == 'lessonEditAdmin') {
            	$("#mainForm").attr("action", "/lesson/updateSubmit");
                $("#mainForm").submit();
                console.log('教案編輯驗證通過')
            }else if (type == 'propoGroupEditAdmin') {
            	$("#mainForm").attr("action", "/proposition/group/updateSubmit");
                $("#mainForm").submit();
                console.log('命題-混合題：：編輯驗證通過')
            }else if (type == 'propoBasicEditAdmin') {
            	$("#mainForm").attr("action", "/proposition/basic/updateSubmit");
                $("#mainForm").submit();
                console.log('命題-選擇題：：編輯驗證通過')
            }
            
        }
    }

    function contentFinish(type) {
        let status = true
        let text = ''
        if ($("input[name='verify']:checked").length == 0) {
            status = false
            text = '請選擇審核結果'
        }

        if (!status) {
            $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
            $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
            $("section.lightBoxAlert .container div").text(text)
        }else {
        	if (type == 'lessonContentFinish') {
        		$("#mainForm").attr("action", "/lesson/audit");
                $("#mainForm").submit();
                console.log('校長--教案詳細資料審核--完稿確認')
            }else if (type == 'propoGroupContentFinish') {
            	$("#mainForm").attr("action", "/proposition/group/audit");
                $("#mainForm").submit();
                console.log('校長--命題-混合題：：詳細資料審核--完稿確認')
            }else if (type == 'propoBasicContentFinish') {
            	$("#mainForm").attr("action", "/proposition/basic/audit");
                $("#mainForm").submit();
                console.log('校長--命題-選擇題：：教案詳細資料審核--完稿確認')
            }
        }

    }

    linkLocation()
    function linkLocation() {        
        let index = location.href.lastIndexOf('/')
        let link = location.href.substring(index+1 , location.href.length)
        // console.log(link)
        let IdentityState = ''
        let SchoolType = ''
        let subject = ''

        
         
        $("input[name='initPosition']").val($("input[name='position']:checked").attr('id'))
        //系統身份初始值
        $("input[name='content_provision']:checked").each(function(){            
            IdentityState += $(this).attr('id')+','
        })        
        $("input[name='initIdentityState']").val(IdentityState.substr( 0 , IdentityState.length -1 ))        

        //監督範圍--學制初始值
        $("input[name='education']:checked").each(function(){            
            SchoolType += $(this).attr('id')+','
        })        
        $("input[name='initSchoolType']").val(SchoolType.substr( 0 , SchoolType.length -1 ))

        //監督範圍--學科初始值
        $("input[name='field']:checked").each(function(){            
            subject += $(this).attr('id')+','
        })
        $("input[name='initSubject']").val(subject.substr( 0 , subject.length -1 ))
    }    


    //搜尋區塊
    function searchContract(type) {
        let teacherName = $("input#teacherName").val()
        let contractType = $("input.contractType:checked").val()
        let contractTime = $("input.contractTime:checked").val()
        console.log(teacherName)
        console.log(contractType)
        console.log(contractTime)

        if (type == 'contractMatList') {
            console.log('搜尋素材授權合約列表')
        }else if (type == 'contractList') {
            console.log('搜尋老師授權合約列表')
        }
        
    }

    function searchTeacher() {
        let position = $("input[name='position']:checked").attr('id')
        let identity = $("input.identityState:checked").attr('id')
        let teacherName = $("input#teacherName").val()
        let teacherEmail = $("input#teacherEmail").val()
        let subject = $("select.subject").val()
        let school = $("select.school").val()
        console.log('系統身份position::'+position)
        console.log('老師身份identity::'+identity)
        console.log('老師姓名teacherName::'+teacherName)
        console.log('電子信箱teacherEmail::'+teacherEmail)
        console.log('學科subject::'+subject)
        console.log('學校school::'+school)
    }

    function searchLessonProp (type) {
        let contractNumber = $("input#contractNumber").val()
        let name = $("input#name").val()    
        let createTime = $("input#createTime").val()
        let schoolType = $("select#schoolType").val()
        let subject = $("select#subject").val()
        let fileType = $("select#fileType").val()

        let start = createTime.split(' ~ ')[0]
        let end = createTime.split(' ~ ')[1]

        console.log('合約流水號::'+contractNumber)
        console.log('教案/命題名稱::'+name)
        console.log('教案/命題建立開始時間::'+start)
        console.log('教案/命題建立結束時間::'+end)
        console.log('學制::'+schoolType)
        console.log('學科::'+subject)
        console.log('檔案狀態::'+fileType)

        if (type =='lessonList') {
            console.log('!!搜尋教案!!')
        }else if (type == 'propBasicList') {
            console.log('!!搜尋命題-選擇題!!')
        }else if (type == 'propGroupList') {
            console.log('!!搜尋命題-混合題!!')
        }        
    }
})