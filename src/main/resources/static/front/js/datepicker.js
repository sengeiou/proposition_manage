$(function() {
    //contract-create.html 建立合約 開始時間、結束時間
    $("#contractStart").datepicker({ 
        // minDate: new Date(),
        "dateFormat": "yy-mm-dd"
    })
    $("#contractEnd").datepicker({
        minDate: new Date(),
        "dateFormat": "yy-mm-dd"
    })

    //lesson-list.html 、propoBasic-list.html 、propoGroup-list.html
    $("#createTime").datepicker({
        dateFormat: "yy-mm-dd",        
        onSelect: function( selectedDate ) {
            if(!$(this).data().datepicker.first){
                $(this).data().datepicker.inline = true
                $(this).data().datepicker.first = selectedDate;
            }else{
                if(selectedDate > $(this).data().datepicker.first){
                    $(this).val($(this).data().datepicker.first + ' ~ ' + selectedDate);
                }else{
                    $(this).val(selectedDate + ' ~ ' + $(this).data().datepicker.first);
                }
                $(this).data().datepicker.inline = false;
            }
        },
        onClose:function(){
            delete $(this).data().datepicker.first;
            $(this).data().datepicker.inline = false;
        }
    })
    //report.html 
    $("#contract_contractTime").datepicker({
        dateFormat: "yy-mm-dd",        
        onSelect: function( selectedDate ) {
            if(!$(this).data().datepicker.first){
                $(this).data().datepicker.inline = true
                $(this).data().datepicker.first = selectedDate;
            }else{
                if(selectedDate > $(this).data().datepicker.first){
                    $(this).val($(this).data().datepicker.first + ' ~ ' + selectedDate);
                }else{
                    $(this).val(selectedDate + ' ~ ' + $(this).data().datepicker.first);
                }
                $(this).data().datepicker.inline = false;
            }
        },
        onClose:function(){
            delete $(this).data().datepicker.first;
            $(this).data().datepicker.inline = false;
        }
    })
})