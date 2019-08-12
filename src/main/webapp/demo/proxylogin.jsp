<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
　　<script src="https://code.jquery.com/jquery-3.0.0.min.js"></script>
</head>
<body>
<script type="text/javascript">
/*  var askData= $("#askData").val();
 var askurl= $("#askurl").val();
 var okurl= $("#okurl").val();
		 $.ajax({
		     type:'get',
		     url:askurl,//跨域请求：http://sso.test.com:8080/login/replylogin
		     data:{
		    	 askData:askData
		     },
		     dataType:'jsonp',
		     contentType:'application/x-www-form-urlencoded;charset=UTF-8',
		     async:false,
		     cache:false,
		     success:function(data){
		    	 console.log(data);
		    	  if(data.msg == "-1"){
		                window.location.href = "http://sso.test.com:8080/login.html?ReturnURL=http%3A%2F%2Fmy.web.com%3A8090%2Fproxylogin.html";
		    	  }else{
		    		  $.post(okurl, {replyTxt:data.msg} , function(e) {//okurl:http://my.web.com:9081/login/oklogin
		    			    console.log(e);
	                        window.location.href = e;
	                    });
		    	  }
		
		     },
		     error:function(){
		    	 window.location.href = "http://sso.test.com:8080/login.html?ReturnURL=http%3A%2F%2Fmy.web.com%3A8090%2Fproxylogin.ht"
		     }
		}); */
		function proxyLogin(askurl, askData, okurl) {
		    var killAjax = true;
		    setTimeout(function() {
		        checkajaxkill();
		    }, 30000);
		    var ajaxCall = jQuery.getJSON(askurl + "?callback=?", {askData:askData}, function(d){
		    	killAjax = false;
		    	if(d.msg == "-1"){
			    	window.location.href = "http://sso.test.com:8080/login?ReturnURL=http://my.web.com:8090/login/proxylogin";
		    	}else{
		    		$.post(okurl, {replyTxt:d.msg} , function(e) {
		    			console.log(e.returl);
			    		window.location.href = e.returl;
				    },"json");
		    	}
		    });
		    function checkajaxkill(){
		        if(killAjax){
		        	ajaxCall.abort();
		        	window.location.href = "http://my.web.com:8090/demo/outtime.html";
		        }
		    }
		}
		proxyLogin("${(askurl)!}", "${(askData)!}", "${(okurl)!}");
</script>

  <span>hello</span>
</body>
</html>