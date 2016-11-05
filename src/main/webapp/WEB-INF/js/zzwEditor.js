dId = getQueryString('dId');
user = getQueryString('user');
key = getQueryString('key');

if (!user) {
	$.ajax({
		url : 'http://ipinfo.io/json',
		type : 'get',
		async : false,// 使用同步的方式,true为异步方式
		success : function(data) {
			var ipinfo = eval(data)
			user = ipinfo.ip

		},
	});
}

function insertDiary() {
	$('#status').text('更新中...')
	count = 0;
	isHaving = true;
	var html = editor.$txt.html();
	var encryptKey = key;
	var data = {
		message : html,
		encryptKey : encryptKey,
		uAccount : user,
		dId : dId
	};

	$.post("/diary/insert", data).success(function() {
		$('#status').text('更新成功,最后更新时间为：' + new Date().toLocaleString())
		isHaving = false;
		if (count == 1) {
			insertDiary()
		}

	}).error(function() {
		isHaving = false;
		$('#status').text('更新失败')
	})
}

function getQueryString(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return '';
}

function createMobileEditor() {
	// ___E 三个下划线
	editor = new ___E('editor-trigger');
	editor.config.uploadImgUrl = '/fileUpload?path=' + user;
	editor.config.uploadTimeout = 600 * 1000;
	editor.init();
}

function onchange() {
	var count = 0;
	var isHaving = false;
	$(document).ready(function() {
		var callback = function(records) {
			if (!isHaving) {
				insertDiary()
			} else {
				count = 1;
			}
			console.log(editor.$txt.html());
		};

		var mo = new MutationObserver(callback);

		var option = {
			'childList' : true,
			'subtree' : true,
			// 'attributes': true,
			'characterData' : true
		};

		mo.observe($('.wangEditor-mobile-txt')[0], option);

	})

	return editor;
}

function createEditor() {
	editor = new wangEditor('editor-trigger');
	// 上传图片
	editor.config.uploadImgUrl = '/fileUpload';
	editor.config.uploadParams = {
		path : user,
	};
	editor.config.uploadTimeout = 600 * 1000;
	editor.config.uploadImgFileName = 'wangEditorMobileFile';

	// 表情显示项
	editor.config.emotionsShow = 'value';
	editor.config.emotions = {
		/*
		 * 'default': { title: '默认', data: './emotions.data' },
		 */
		'weibo' : {
			title : '微博表情',
			data : [
					{
						icon : 'http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/7a/shenshou_thumb.gif',
						value : '[草泥马]'
					},
					{
						icon : 'http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/60/horse2_thumb.gif',
						value : '[神马]'
					},
					{
						icon : 'http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/bc/fuyun_thumb.gif',
						value : '[浮云]'
					},
					{
						icon : 'http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/c9/geili_thumb.gif',
						value : '[给力]'
					},
					{
						icon : 'http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/f2/wg_thumb.gif',
						value : '[围观]'
					},
					{
						icon : 'http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/70/vw_thumb.gif',
						value : '[威武]'
					} ]
		}
	};

	// onchange 事件
	var count = 0;
	var isHaving = false
	editor.onchange = function() {
		if (!isHaving) {
			insertDiary()
		} else {
			count = 1;
		}

		console.log(editor.$txt.html());
	};
	editor.create();

	return editor;
}

function initEditor(mobile) {

	if (!dId && !user) {
		createEditor();
	}
	if (dId) {
		$.post('/diary/queryById', {
			dId : dId,
			encryptKey : key
		}).success(function(date) {
			if (date) {
				var diary = eval(date);
				$('#editor-trigger').html(diary.message);
			}
			if (mobile) {
				createMobileEditor();
				onchange();
			} else {
				createEditor()
			}

		})
	}
	if (user) {
		$.post('/diary/queryByUserInToday', {
			user : user,
			encryptKey : key
		}).success(function(date) {
			if (date) {
				var diary = eval(date);
				$('#editor-trigger').html(diary.message);
			}
			if (mobile) {
				createMobileEditor();
				onchange();
			} else {
				createEditor()
			}
		})
	}
}