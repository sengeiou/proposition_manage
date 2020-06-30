$(function() {
    //contract-create.html 建立合約 開始時間、結束時間
    $("#contractStart").datepicker({ 
        minDate: new Date(),
        "dateFormat": "yy-mm-dd"
    })
    $("#contractEnd").datepicker({
        minDate: new Date(),
        "dateFormat": "yy-mm-dd"
    })
})