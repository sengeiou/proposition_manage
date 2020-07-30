$(function(){
    function openlightBoxAlert(text) {        
        $("section.lightBoxAlert").removeClass('dis-n').add('dis-b')
        $("section.lightBoxBG").removeClass('dis-n').add('dis-b')
        $("section.lightBoxAlert .container div").text(text)
    }

    //分類管理
    $(".category-block").on("click",".openLightBox",function(){   //打開燈箱
        let lightBox = $(this).data('lb')
        let state = true
        let alertText = ''

        if (lightBox == 'edit-categorySubject') {   //分類管理--學科管理--編輯button
            let item = $(this).parent().siblings('.subjectName')   
            let elementary = $(this).parent().siblings('.elementary')   
            let junior = $(this).parent().siblings('.junior')   
            let high = $(this).parent().siblings('.high')   
            //判斷哪些學制有學科，渲染在燈箱內的checkbox
            !elementary.text() == true ? false: $(`input#elementary`).prop('checked', true).siblings('.checkbox-square').addClass('active')
            !junior.text() == true ? false: $(`input#junior`).prop('checked', true).siblings('.checkbox-square').addClass('active')
            !high.text() == true ? false: $(`input#high`).prop('checked', true).siblings('.checkbox-square').addClass('active')             
            //更改燈箱文字及綁學科id、編號於燈箱的「確定」按鈕上
            $(`section.${lightBox}-lightBox .container #categorySubject`).val(item.find('span').eq(0).text())
            $(`section.${lightBox}-lightBox .container #subcategorySubject`).val(item.find('span').eq(1).text())
            $(`section.${lightBox}-lightBox button.editNewDataBtn`).data('id',item.attr('id'))
            $(`section.${lightBox}-lightBox button.editNewDataBtn`).data('no',item.data('no'))

        }else if (lightBox == 'edit-categoryMaterial') {    //分類管理--素材分類--編輯button
            let item = $(this).parent().siblings('.item-group').find('.active')
            if (item.length == 0) {
                state = false   
                alertText = '請先選擇欲編輯項目'              
            }else {
                $(`section.${lightBox}-lightBox .container p`).text(item.text())                
                $(`section.${lightBox}-lightBox .container input`).data('id',item.attr('id'))                                
            }   
        }else if (lightBox == 'delete-categorySubject') {  //分類管理--學科管理--刪除button
            let item = $(this).parent().siblings('.subjectName')   
            let elementary = $(this).parent().siblings('.elementary')  
            let junior = $(this).parent().siblings('.junior')   
            let high = $(this).parent().siblings('.high')
            //判斷各學制下有沒有該學科
            let elementaryText = !elementary.text() == true ? '' :'國小' 
            let juniorText = !junior.text() == true ? '' :',國中' 
            let highText = !high.text() == true ? '' :',高中'
            //更改燈箱文字及綁學科id、編號於燈箱的「確定」按鈕上
            $(`section.${lightBox}-lightBox .container-grid p`).eq(1).text(item.find('span').eq(0).text())
            $(`section.${lightBox}-lightBox .container-grid p`).eq(3).text(item.find('span').eq(1).text())
            $(`section.${lightBox}-lightBox .container-grid p`).eq(5).text(elementaryText+juniorText+highText)
            $(`section.${lightBox}-lightBox button.editNewDataBtn`).data('id',item.attr('id'))     
        }


        if (state) {
            $("section.lightBoxBG").removeClass('dis-n').addClass('dis-b')
            $(`section.${lightBox}-lightBox`).removeClass('dis-n').addClass('dis-b')
        }else {
            openlightBoxAlert(alertText)
        }
    })


    $(".block").on('click','.item',function(){ //點擊選項         
        $(this).addClass('active')
        $(this).siblings('.item').removeClass('active')
        if ($(this).data('type') == 'grade') {
            $(".subItemGroup").html('')
            if ($(this).attr('id') == 'elementary') {
                console.log('學制--國小')
                // 放進相對應的年級
                let html = '<p class="item" id="one">一年級</p><p class="item" id="two">二年級</p><p class="item" id="three">三年級</p><p class="item" id="four">四年級</p><p class="item" id="five">五年級</p><p class="item" id="six">六年級</p>'
                $(".subItemGroup").removeClass('none').addClass('item-group').html(html)
            }else if ($(this).attr('id') == 'junior') {
                console.log('學制--國中')
                // 放進相對應的年級
                let html = '<p class="item" id="one">一年級</p><p class="item" id="two">二年級</p><p class="item" id="three">三年級</p>'
                $(".subItemGroup").removeClass('none').addClass('item-group').html(html)
            }else if ($(this).attr('id') == 'hight') {
                console.log('學制--高中')
                // 放進相對應的年級
                let html = '<p class="item" id="one">一年級</p><p class="item" id="two">二年級</p><p class="item" id="three">三年級</p>'
                $(".subItemGroup").removeClass('none').addClass('item-group').html(html)
            }
        }        
    })
    $(".deleteBtn").on('click',function(){ //點擊「刪除」按鈕
        let item = $(this).parent().siblings('.item-group').find('.active').attr('id')   
        item == undefined ? openlightBoxAlert('請先選擇欲刪除項目') :false
        console.log('刪除按鈕在這～～要刪除的id : '+item)
    })
    $(".upBtn").on('click',function(){ //點擊「上移」按鈕
        $(this).parent().siblings('.item-group').find('.active').each(function(){                     
            if ($(this).index() > 0) {
                let itemId = $(this).attr('id')        
                console.log('上移按鈕在這～～要上移的id : '+itemId)                
                $(this).prev().before($(this))
            }else {
                return
            }
        })    
    })
    $(".downBtn").on('click',function(){  //點擊「下移」按鈕
        $(this).parent().siblings('.item-group').find('.active').each(function(){                               
            let itemLength = $(this).siblings().length
            if ($(this).index() < itemLength ) {
                let itemId = $(this).attr('id')        
                console.log('下移按鈕在這～～要下移的id : '+itemId)                
                $(this).next().after($(this))            
            }else {
                return
            }
        })
    })
    $(".firstBtn").on('click',function(){ //點擊「置頂」按鈕
        $(this).parent().siblings('.item-group').find('.active').each(function(){  
            if ($(this).index() > 0) {
                let itemId = $(this).attr('id')        
                console.log('置頂按鈕在這～～要置頂的id : '+itemId)
                let group = $(this).parent()[0]
                group.insertBefore($(this)[0] , group.childNodes[0])
            }else {
                return
            }
        })
    })
    $(".lastBtn").on('click',function(){  //點擊「置底」按鈕        
        $(this).parent().siblings('.item-group').find('.active').each(function(){              
            let itemLength = $(this).siblings().length
            if ($(this).index() < itemLength ) {
                let itemId = $(this).attr('id')        
                console.log('置底按鈕在這～～要置底的id : '+itemId)
                $(this).parent().append($(this))
            }else {
                return
            }
        })
    })
})