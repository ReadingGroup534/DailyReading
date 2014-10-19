<html>
<head>
<title>控制台--Browse</title>
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
			<table>
				
					<#list browses as browse>
					<tr>
					<td>${browse.browse_id}</td>
					<td>${browse.browse_value}</td>						
					<td>${browse.description}</td>
					</tr>
					</#list>
			</table>
		</div>
	</div>
</body>
</html>