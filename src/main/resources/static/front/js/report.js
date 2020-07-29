$(function() {
    //報表匯出
    $(".tab").on("click",function(e){    
        e.preventDefault()
        $(this).addClass('active').siblings('.tab').removeClass('active')
        let tab = $(this).data('tab')
        $(".report-content").find(`div.${tab}`).removeClass('dis-n').siblings('.tabContent').addClass('dis-n')
    })
})