<html>
<head>
	<script type="text/javascript" src="/js/jquery-2.0.3.min.js"></script>
	<script>
	jQuery(function () {
		
		var url = "/index/update.do";
		
		jQuery.ajax({
			url : url,
			type : "put",
			dataType: "json",
			data : {
				username : "zhanghao"
			},
			success : function(msg) {
				//alert(msg);
			}
		});
		
	});
	</script>
</head>
<body>
<h2>Hello World!</h2>
</body>
</html>
