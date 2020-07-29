$(function(){
    //教案/命題列表
    $("#checkAll").on("click",function(){ //全選框checkbox
        if ($(this).is(":checked")) {
            $(".listCheckBox").prop('checked',true).siblings('div.checkbox-square').addClass('active')
        }else {
            $(".listCheckBox").prop('checked',false).siblings('div.checkbox-square').removeClass('active')
        }
        
    })

    $(".listCheckBox").on("click",function(){ //列表勾選框checkbox
        let checkAll = true
        $(".listCheckBox").each(function(){            
            if (!$(this).is(':checked')) {
                checkAll = false
            }
        })
        if (checkAll){            
            $("#checkAll").prop('checked',true).siblings('div.checkbox-square').addClass('active')
        }else {            
            $("#checkAll").prop('checked',false).siblings('div.checkbox-square').removeClass('active')
        }
    })

    $(".btnDownLessonFile").on("click",function(){ //下載檔案button
        let str = ''
        $(".listCheckBox").each(function(){            
            if ($(this).is(':checked')) {
                str += $(this).attr('id') + ','
            }
        })        
        if (str != '') {
            alert('下載教案或命題檔案在這，檔案的id：：'+str)
        }        
    })

    //教案/命題詳細資料
    $("input[name='verifyMethod']").on("change",function(){ //校長身份--初審區塊--選擇審議委員
        if ($(this).val() == 0 && $("#verifyTeacher").children().length == 1) {
            // alert('找對應的符合此學制/學科的委員')
            $.ajax({
                url: '/get/auditor',
                cache: false,
                async: false,
                dataType: 'json',
                type: 'POST',
                xhrFilds:{withCredentials:true},
                data: {
                    education_id: $("#educationId").val(),
                    subject_id: $("#subjectId").val()
                },
                error: function(xhr) {
                    alert("取得資料錯誤");                    
                },
                success: function(data) {                    
                    console.log(data)
                    let html;
                    if(data != null && data.length > 0) {
                    	for(i=0; i<data.length; i++) {
	                    	let child = data[i];
	                    	html += `<option value="${child.ACCOUNT}">${child.NAME}</option>`
                    	}
                    }
                    $("#verifyTeacher").html(html)
                    $("#verifyTeacher").addClass('custom-select')
                    $("#verifyTeacher").customselect()
                }
            });              
        }              
    })
    $(".lessonVerify").on("change","input:radio[name='verify']",function(){ //審核區塊的「審核結果」radio
        let faild = $(this).parent().parent().parent().find('.verifyFaild')
        let pass =  $(this).parent().parent().parent().find('.verifyPass')
        if ($(this).val() == 0) {
            faild.removeClass('dis-n')
            pass.addClass('dis-n')
            $(this).parent().parent().find('.memo').text('註：若選擇待修正，請擇一填寫回饋檔案或審核回饋')
            $("input[name='verifyMethod']").prop('checked',false)
            $("input[name='verifyMethod']").siblings('.radio-circle').removeClass('active')
            $("#verifyTeacher").val(0)
            $("div.custom-select a span").text('請選擇審議委員')
        }else if ($(this).val() == 1) {
            faild.addClass('dis-n')
            if (pass.length > 0) {
                pass.removeClass('dis-n')
                $(this).parent().parent().find('.memo').text('註：若選擇通過，請選擇審核方式')
            }           
            $(this).parent().parent().parent().find('textarea').val('')
            $("#lessonVerifyFile").val('')
            $(".lessonVerifyFileText").text('')
        }                           
    })

    //新增'、編輯教案/命題
    $(".attachmentBlock").on("change",".attach-select",function(){ //附件的select 
        let fileType = ''
        let fileInput = false
        let fileInputAccept = ''     
        
        if ($(this).val() == 0) {            
            $(this).siblings('.attach-label').addClass('dis-n')
            $(this).parent().parent().find('.attach-label').addClass('dis-n')
        }else {            
            if ($(this).val() == '1') {
                fileType = ".jpg、.jpeg、.png"      
                fileInputAccept = ".jpg,.jpeg,.png"            
            }else if ($(this).val() == '2') {
                fileType = ".mp3、.wav"
                fileInputAccept = ".mp3,.wav"            
            }else if ($(this).val() == '3') {
                fileType = ".avi、.mp4、.mpg"
                fileInputAccept = ".avi,.mp4,.mpg"            
            }else if ($(this).val() == '4') {
                fileInput = true
            }
            
            if (fileInput) { //若是超連結，顯示input text
                $(this).siblings('label.attach-label').addClass('dis-n')
                $(this).siblings('span.attach-label').addClass('dis-n')
                $(this).siblings('input.attach-linkInput').removeClass('dis-n')
            }else { // 其餘的顯示「選擇檔案」按鈕
                $(this).siblings('input.attach-linkInput').addClass('dis-n')
                $(this).siblings('span.attach-label').text('註：僅接受附檔名為' + fileType)
                $(this).siblings('label.attach-label').removeClass('dis-n')
                $(this).siblings('label.attach-label').find('input').attr('accept',fileInputAccept)
                $(this).siblings('span.attach-label').removeClass('dis-n')            
            }            
        }
        $(this).siblings('label.attach-label').find('input').val('') //清空input file內的值
        $(this).siblings('.attach-linkInput').val('') //清空超連結input的值
        $(this).parent().parent().find('p').text('') //清空input file的顯示文字
    })

    $(".attachmentBlock").on("click",".attach-plus",function(){ //附件「新增附件」icon
        let number = parseInt($(this).data('number'))
        let html = `<div class="v-center flex-wrap display-spaBetween m-top-15px">
                        <div class="v-center">
                            <span>附件${number+1}</span>
                            <select class="attach-select" name="material_type">
                                <option value="0">請選擇類型</option>
                                <option value="1">圖片</option>
                                <option value="2">音檔</option>
                                <option value="3">影片</option>
                                <option value="4">超連結</option>
                            </select>                            
                            <label for="attachment${number+1}" class="attach-label dis-n">
                                <input type="file" class="dis-n btnFile" id="attachment${number+1}" name="attachment" accept=".ppt,.doc,.docx,.xls,.xlsx" value="" data-text="attachmentText${number+1}">
                                <span class="btn-file">選擇檔案</span>
                            </label>                        
                            <span class="memo attach-label dis-n">註：僅接受附檔名為</span>
                            <input type="text" placeholder="請輸入超連結" name="link" class="attach-linkInput dis-n">
                        </div>                            
                        <i class="fas fa-plus-circle dis-n attach-label attach-plus" data-number='${number+1}'></i>
                        <p class="attachmentText${number+1} dis-n"></p>
                    </div>`
        $(this).parent().parent().append(html)
        $(this).remove()
    })

    $(".attachmentBlock").on("keyup",".attach-linkInput",function(){ //附件 「超連結」input        
        $(this).val() == '' ? false : $(this).parent().parent().find('i.attach-label').removeClass('dis-n')        
    }) 

    $(".contractMatBlock").on("click",".contractMat-plus",function(){ //素材授權申請「選擇檔案」button
        let number = parseInt($(this).data('number'))
        let html = `<div class="v-center flex-wrap display-spaBetween m-top-15px">
                        <div class="v-center">
                            <span class="contractMatNumber">檔案${number+1}</span>                                                          
                            <label for="contractMat${number+1}">
                                <input type="file" class="dis-n btnFile" id="contractMat${number+1}" name="contractMat" accept=".doc,.docx,.pdf" value="" data-text="contractMatText${number+1}">
                                <span class="btn-file">選擇檔案</span>
                            </label>                        
                            <span class="memo">註：僅接受附檔名為.doc、.docx、.pdf</span>                                
                        </div>                            
                        <i class="fas fa-plus-circle fa-lg dis-n contractMat-plus" data-number='${number+1}'></i>
                        <p class="contractMatText${number+1} dis-n"></p></div>`
        $(this).parent().parent().append(html)
        $(this).remove()
    })

    $(".keyWord").on("change",function(){  //關鍵字input
        if ($(this).val() == '') {
            return
        }else {
            let item = `<div class="lightboxItem"><span>${$(this).val()}</span><button class="deleteTag" type="button"><i class="fas fa-times"></i></button></div>`        
            $(this).parent().append(item)
            $(this).val('')
        }        
    })
    
    $(".keyWord").keydown(function(e){
        if (e.keyCode == 13){
            let item = `<div class="lightboxItem"><span>${$(this).val()}</span><button class="deleteTag" type="button"><i class="fas fa-times"></i></button></div>`        
            $(this).parent().append(item)
            $(this).val('')
        }
    })

    $(".create-content").on("click",".deleteTag",function(){  //tag刪除按鈕        
        let val = $(this).data('value')
        let lightBoxName = $(this).parent().parent().parent().find('button.btn-openLightBox').data('lb')        
        let lightBox = $(`section.${lightBoxName}-lightBox`)        
        lightBox.find(`input[value='${val}']`).prop('checked',false) //燈箱內input移除checked
        lightBox.find(`input[value='${val}']`).siblings('.checkbox-square').removeClass('active')
        $(this).parent().remove()  //移除tag
    })
})