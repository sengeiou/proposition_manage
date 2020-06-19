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

    // $(".btn-closeLightBox").on("click",function(){ //關閉燈箱
    //     let lightBox = $(this).data('lb')
    //     $("section.lightBoxBG").removeClass('dis-b').addClass('dis-n')
    //     $(`section.${lightBox}-lightBox`).removeClass('dis-b').addClass('dis-n')
    // })
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
        let item = `<div class="lightboxItem"><span>${$(this).val()}</span><button class="deleteTag"><i class="fas fa-times"></i></button></div>`        
        $(this).parent().append(item)
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
            let item = `<div class="lightboxItem"><span>${$(this).parent().text()}</span><button class="deleteTag" data-value="${$(this).val()}"><i class="fas fa-times"></i></button></div>`
            location.append(item)                     
        })        
        $("section.lightBoxBG").removeClass('dis-b').addClass('dis-n')
        $(`section.${tagName}-lightBox`).removeClass('dis-b').addClass('dis-n')
    })

    // $("#contractStart").datepicker({
    //     minDate: new Date()
    // })
    // $("#contractEnd").datepicker({
    //     minDate: new Date()
    // })

})