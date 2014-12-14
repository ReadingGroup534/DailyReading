/************** sql **************/
tables:	
	browse: 分类信息表
	article: 文章基本信息表

/************* server **********/
host: http://localhost:8080/
urls:
	文章详情：/reading/detail?aid=&appkey=
	解释：
		aid: 文章的id
		appkey: server访问私钥，将与server端核对，表示有效的访问
	数据更新：/reading/update?aid=&op=&appkey=
	解释：
		aid: 同上
		appkey: 同上
		op: 需要更新的操作类型,值包括
			praise->点赞数+1
			share->分享数+1
	文章列表：/reading/list?bid=&limit=&start=&appkey=
	解释：
		appkey：同上
		bid:文章的分类id,可选，不加此参数表示在所有文章列表中取
		limit:每次取文章的个数，可选，默认5
		start:从文章列表的起始位置取，可选，默认0
	类目列表：/reading/category?appkey=
	解释：
		appkey:同上
