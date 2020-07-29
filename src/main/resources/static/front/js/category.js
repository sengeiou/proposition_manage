$(function(){
    //分類管理
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