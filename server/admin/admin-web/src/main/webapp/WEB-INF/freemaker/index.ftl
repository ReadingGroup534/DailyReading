<html>
<head>
<title>aiteu-控制台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" value="reading web admin"/>
<style type="text/css">
	body{
		background-color:#f7f8f8;
		font-size:18px; 
		line-height:24px;
	}
	.main-body{
		margin-left:240px;
		margin-right:240px;
		padding-left:10px;
		padding-right:10px;
		padding-top:15px;
		padding-bottom:15px;
	}
	.header-block{
		font-size:36;
		text-align:center;
	}
	.main-body-content{
		margin-top:30px;
	}
	.index-style{
		margin-left:5px;
		margin-right:5px;
	}
</style>
</head>
<body>
	<div class="main-body">
		<div class="header-block">控制台</div>
		<div class="main-body-content">
			<#list indexs as item>
				<a class="index-style" href="${base_context}/${item.url}">${item.name}</a>
			</#list>
		</div>
	</div>
</body>
</html>