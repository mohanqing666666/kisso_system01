//$(function(){
//        $.ajax({
//            url: askurl,
//            data:  {askData:askData},
//            success: function(d){
//                if(d.msg == "-1"){
//                        window.location.href = "http://sso.test.com:8080/login.html?ReturnURL=http%3A%2F%2Fmy.web.com%3A8090%2Fproxylogin.html";
//                    }else{
//                    $.post(okurl, {replyTxt:d.msg} , function(e) {
//                        window.location.href = e.returl;
//                    }, "json");
//                }
//            },error:function(){
//                window.location.href = "http://sso.test.com:8080/login.html?ReturnURL=http%3A%2F%2Fmy.web.com%3A8090%2Fproxylogin.ht"
//            },
//            dataType: json
//         });
//    });

console.log(111);