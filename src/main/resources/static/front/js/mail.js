$(function(){
    $(".btnSendMail").on("click",function(){
        let emailCheck = /^([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22))*\x40([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d))*$/;
        let recipient = $("#recipient").val()   //收件者
        let copy = $("#copy").val()             //副本
        let blindCopy = $("#blindCopy").val()   //密件副本
        let title = $("#title").val()           //主旨
        let message = $("#message").val()       //訊息
        
        if(!recipient) {
            alert('請輸入收件者')
        }else if (!emailCheck.test(recipient)) {
            alert('收件者格式錯誤')
        }else if (copy && !emailCheck.test(copy)) {
            alert('副本格式錯誤')
        }else if (blindCopy && !emailCheck.test(blindCopy)) {
            alert('密件副本格式錯誤')
        }else if (!title) {
            alert('請輸入主旨')
        }else if (!message) {
            alert('請輸入訊息')
        }else {
            alert('驗證成功～～～可以寄信～～～')
            $("#mainForm").submit();
        }
    })
})