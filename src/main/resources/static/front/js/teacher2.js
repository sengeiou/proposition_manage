$(function(){
    //老師列表
    $(".btn-openArea").on("click",function(){  //審核區塊展開縮合button
        if ($(this).children('i').hasClass('fa-angle-up')) {
            $(this).children('i').removeClass('fa-angle-up').addClass('fa-angle-down')
            $(this).siblings('div.verify-list').removeClass('closeArea').addClass('openArea')
        }else {
            $(this).children('i').addClass('fa-angle-up').removeClass('fa-angle-down')
            $(this).siblings('div.verify-list').addClass('closeArea').removeClass('openArea')
        }
    })

    //建立老師
    $("#select-school").customselect()
    $("#residenceSelectZip").customselect()
    $("#selectZip").customselect()

    $("input[name='position']").on('click',function(){ //老師身份radio
        let position = $(this).attr('id')
        if (position != 'teacher') {
            $(`div[class*='Block']`).addClass('dis-n').find("input[type='checkbox']").prop('checked',false).siblings('div.checkbox-square').removeClass('active')
            $("div.principalBlock").removeClass('dis-n')
        }else {
            $(`div[class*='Block']`).addClass('dis-n').find("input[type='checkbox']").prop('checked',false).siblings('div.checkbox-square').removeClass('active')
            $("div.teacherBlock").removeClass('dis-n')
        }
        // $(`div[class*='Block']`).addClass('dis-n').find("input[type='checkbox']").prop('checked',false).siblings('div.checkbox-square').removeClass('active')
        // $(`div.${position}Block`).removeClass('dis-n')        
    })

    $(".selectCity").on("change",function(){
//        alert('選擇的縣市：：'+$(this).val())        
        // $(this).siblings('.selectArea').html(ajax 出來的鄉鎮放在這邊)
        sameAbove('selectCity')
    })

    $(".selectArea").on("change",function(){
//        alert('選擇的鄉鎮市區：：'+$(this).val())
        // $(this).siblings('.selectRoad').html(ajax 出來的路段放在這邊)  
        $(this).siblings(".customselect-block").html(' ')
        let html = `<option value="0">請選擇對應的郵遞區號</option>
                    <option value="100205">100205 中正區博愛路</option>
                    <option value="100206">100206 中正區博愛路</option>
                    <option value="100001">100001 中正區博愛路</option>
                    <option value="100002">100002 中正區博愛路</option>`

        $(this).siblings(".customselect-block").html(`<select id="selectZip" class="custom-select">${html}</select>`)

        $("#selectZip").customselect()
    })

    $(".selectResidenceArea").on("change",function(){
//        alert('選擇的戶籍地址鄉鎮市區：：'+$(this).val())
        // $(this).siblings('.selectRoad').html(ajax 出來的路段放在這邊)  
        $(this).siblings(".customselect-residence-block").html(' ')
        let html = `<option value="0">請選擇對應的郵遞區號</option>
                    <option value="100205">100205 中正區博愛路</option>
                    <option value="100206">100206 中正區博愛路</option>
                    <option value="100001">100001 中正區博愛路</option>
                    <option value="100002">100002 中正區博愛路</option>`

        $(this).siblings(".customselect-residence-block").html(`<select id="residenceSelectZip" class="custom-select">${html}</select>`)
        $("#residenceSelectZip").customselect()
    })

    $(".address-block").on("change","#residenceSelectZip",function(){ //戶籍地址郵遞區號select
        let zipInput = $(this).parent().parent().parent().find("input#residencezip")        
        $(this).val() == 0 ? zipInput.val('') : zipInput.val($(this).val().substring(0, 5))
        sameAbove('residenceSelectZip')        
    })

    $(".address-block").on("change","#selectZip",function(){ //通訊地址郵遞區號select
        let zipInput = $(this).parent().parent().parent().find("input#zip")      
        $(this).val() == 0 ? zipInput.val('') : zipInput.val($(this).val().substring(0, 5))
        sameAbove('selectZip')        
    })

    $("#sameAbove").on("click",function(){ //「同上」checkbox
        if ($(this).is(":checked")) {

            $("#zip").siblings(".selectCity").val($("#residencezip").siblings(".selectCity").val());
            $("#zip").siblings(".selectArea").val($("#residencezip").siblings(".selectResidenceArea").val());
            $("#city").trigger("change");
            $("#zip").siblings("#area").html($("#residencezip").siblings("#census_area").html());
            $("#zip").siblings("#area").val($("#residencezip").siblings("#census_area").val());            
//            $("#area").trigger("change");
            $("#address").val($("#residenceAddress").val());
                        
            //渲染
            $("#selectZip").empty();
            $("#area").siblings(".customselect-block").html(' ');
            $.getJSON('/front/json/allcity.json', function (obj) {
    			var road = obj.filter(function(item, index, array){
    			  return (item.City.indexOf($("#city").val()) >= 0 && item.Area.indexOf($("#area").val()) >= 0);       
    			});
    			var str = "<option value='0'>請選擇對應郵遞區號</option>";
    			if(road.length > 0){
    				for(var i =0;i < road.length;i++){
    					str += "<option value='"+road[i].Zip5+road[i].Area+road[i].Road+road[i].Scope+"'>"+road[i].Zip5+road[i].Area+road[i].Road+road[i].Scope+"</option>";
    				}
    			}								
    			$(".customselect-block").html("<select id='selectZip' class='custom-select'>"+str+"</select>")
    			$("#selectZip").val($("#residenceSelectZip").val());
    			$("#selectZip").customselect();
			});        	
            $("#zip").val($("#residencezip").val());
            
//            $("#zip").siblings(".customselect-block").find('span').thml($("#residencezip").siblings(".customselect-residence-block").find('span').thml());
//            $("#zip").siblings(".customselect-block").find('span').text($("#residencezip").siblings(".customselect-residence-block").find('span').text());
//            $("#selectZip").customselect();
//            $("#selectZip").siblings('a').find('span').text($("#residenceSelectZip").siblings('a').find('span').text())
            
        }else {
            $("#zip").val('')
            $("#zip").siblings(".selectCity").val('')
            $("#zip").siblings(".selectArea").val('')
//            $("#zip").siblings(".selectRoad").val(0)
            $("#address").val('')
            $("#selectZip").val('')
        }          
    })

    function sameAbove(addressSelect) {
        let same = true
        if (addressSelect == 'residenceSelectZip' || addressSelect == 'selectZip') {
            $("#residenceSelectZip").val() ==  $("#selectZip").val() ? same=true :same=false
        }else {
            let valArr = []
            $(`.${addressSelect}`).each(function(){
                valArr.includes($(this).val()) ? false : valArr.push($(this).val())
            })
            valArr.length == 1 ? same=true : same=false
        }
        
        if (!same) {
            $("#sameAbove").prop('checked',false)
            $("#sameAbove").siblings('.checkbox-square').removeClass('active')
        }
    }

    //編輯老師
    $("#editResidenceAddress").on("click",function(){   //修改戶籍地址checkbox
        if ($(this).is(':checked')) {            
            $(this).parent().parent().siblings('.wrap').removeClass('dis-n')
        }else {            
            $(this).parent().parent().siblings('.wrap').addClass('dis-n')
        }
    })

    $("#editAddress").on("click",function(){   //修改通訊地址checkbox
        if ($(this).is(':checked')) {            
            $(this).parent().parent().siblings('.wrap').removeClass('dis-n')
        }else {            
            $(this).parent().parent().siblings('.wrap').addClass('dis-n')
        }
    })
    $(".backToInit").on("click",function(){   //「返回初始值」button
        let initPosition = $("input[name='initPosition']").val()//老師身份     
        let identityState = $("input[name='initIdentityState']").val() //系統身份
        let schoolType = $("input[name='initSchoolType']").val() //監督範圍--學制
        let subject = $("input[name='initSubject']").val() //監督範圍--學科 
        // 先將全部input 取消勾選
        $("input[name='position']").prop('checked',false).siblings('.radio-circle').removeClass('active')
        $("input[name='content_provision']").prop('checked',false).siblings('.checkbox-square').removeClass('active')
        $("input[name='education']").prop('checked',false).siblings('.checkbox-square').removeClass('active')
        $("input[name='field']").prop('checked',false).siblings('.checkbox-square').removeClass('active')

        // 勾選初始值
        $(`input#${initPosition}`).prop('checked', true).siblings('.radio-circle').addClass('active')
        if (initPosition == 'teacher') {
            $("div.teacherBlock").removeClass('dis-n')
            $("div.principalBlock").addClass('dis-n')
        }else {
            $("div.teacherBlock").addClass('dis-n')
            $("div.principalBlock").removeClass('dis-n')
        }
        
        if (identityState) {
            identityState.split(',').forEach( function(element, index, array){            
                $(`input#${element}`).prop('checked', true).siblings('.checkbox-square').addClass('active')
            })
        }

        if (schoolType) {
            schoolType.split(',').forEach( function(element){
                $(`input#${element}`).prop('checked', true).siblings('.checkbox-square').addClass('active')
            })
        }
                       
        if (subject) {
            subject.split(',').forEach( function(element){
                $(`input#${element}`).prop('checked', true).siblings('.checkbox-square').addClass('active')
            })
        }       
    })

})