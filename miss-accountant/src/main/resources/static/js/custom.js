$().ready(function(){
	// 全選
    $('.check_all').click(function () {
        $(this).parents('form').find('input:checkbox').prop('checked', $(this).is(':checked'));
    });

	// 送出
    $(".submit").click(function(){
	    $(this).parents('form').submit();
    });

	// ckobj
	$(".ckobj").each(function(){
		CKEDITOR.replace( $(this).attr("name"),
		{
		    filebrowserBrowseUrl : '/contents/backend/js/ckfinder/ckfinder.html',
		    filebrowserImageBrowseUrl : '/contents/backend/js/ckfinder/ckfinder.html?Type=Images',
		    filebrowserFlashBrowseUrl : '/contents/backend/js/ckfinder/ckfinder.html?Type=Flash',                     
		    filebrowserUploadUrl:'/contents/backend/js/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Files',       
		    filebrowserImageUploadUrl:'/contents/backend/js/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Images',
		    filebrowserFlashUploadUrl :'/contents/backend/js/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Flash'
	    });
	})

    // 刪除
    $(".delete").click(function(){
	    if($(this).html() == "刪除")
	    {
          	var id = $(this).parent().parent().attr("data");
          	swal({
	            title: "確認要刪除嗎?", 
	            type: "warning", 
	            showCancelButton: true, 
	            confirmButtonColor: "#DD6B55", 
	            confirmButtonText: "確認", 
                cancelButtonText: "取消",
          	},
          	function(){
              	swal({
              		title: "刪除成功!",
              		showConfirmButton: false
              	}); 
		        location.href = $("#site_root").val() + "/Delete/" + id;
          	});
	      	return false;
	    }
  	})  

	// 圖片上傳
	// Reg fileupload button
    reg_ajaxfileupload();
    reg_ajaxfileupload_file();
	
	function reg_ajaxfileupload()
	{
		$(".ajaxfileuplod").each(function() { 
			set_fileupload($(this));
		});
	}
	
	function set_fileupload(obj)
	{
		var init_files = [];
		var initialPreview = [];
		var initialPreviewConfig = [];
		
		if(obj.val())
			init_files = JSON.parse(obj.val());
		
		init_files.forEach(function(file,i) {
	    	initialPreview.push("<img style='height:160px' src='"+$("#img_src").val()+"/"+obj.attr("folder")+"/"+file + "'>");
	    	var preview = new Object;
	    	preview.caption = file;
	    	preview.width = "120px";
	    	preview.url = $("#site_root").val() + "/filedelete";
	    	preview.key = file;
	    	preview.extra = {folder:obj.attr("folder")};
	    	initialPreviewConfig.push(preview);
		});
		
		var setting = {
			language: "zh-TW",
		    uploadUrl: $("#site_root").val() + "/fileupload",
		    allowedFileExtensions: ["jpg", "png", "gif"],
			uploadAsync: true,
			overwriteInitial: false,
			validateInitialCount: true,
			showUpload: false, // hide upload button
			showRemove: false, // hide remove button
    		showClose: false,
    		//maxImageWidth: 200,
			initialPreview: initialPreview,
		    initialPreviewConfig: initialPreviewConfig,
		    uploadExtraData: {
		        folder: obj.attr("folder"),
		    }
		};
		
		f_html = $('<input type="file" accept="image/*" />');
		
		// 是否為多檔上傳
		if(obj.attr("multiple"))
		{
			f_html.prop("multiple" , true);
			
			if(obj.attr("maxFileCount"))
				setting["maxFileCount"] = parseInt(obj.attr("maxFileCount"));
		}
		else
			setting["maxFileCount"] = 1;

		// 限制圖片大小		
		if(obj.attr("minImageWidth"))
			setting["minImageWidth"] = parseInt(obj.attr("minImageWidth"));
		if(obj.attr("minImageHeight"))
			setting["minImageHeight"] = parseInt(obj.attr("minImageHeight"));
		if(obj.attr("maxImageWidth"))
			setting["maxImageWidth"] = parseInt(obj.attr("maxImageWidth"));
		if(obj.attr("maxImageHeight"))
			setting["maxImageHeight"] = parseInt(obj.attr("maxImageHeight"));
			
		obj.after(f_html);
		
		var f_obj = obj.next();
		
		f_obj.fileinput(setting)
		.on("filebatchselected", function(event, files) {
		    // trigger upload method immediately after files are selected
		    f_obj.fileinput("upload");
		})
		.on('filedeleted', function(event, key) {
			
			new_init_files = [];
			
	    	init_files.forEach(function(file,i) {
		    	if(file != key)
			    	new_init_files.push(file);
			});
			
			init_files = new_init_files;
	
			obj.val(JSON.stringify(init_files));
		})
		.on('filesorted', function(event, key) {

			new_init_files = [];
			
	    	key.stack.forEach(function(file,i) {
			    new_init_files.push(file.key);
			});
			
			init_files = new_init_files;

			obj.val(JSON.stringify(init_files));

		})
		.on('fileuploaded', function(event, data, previewId, index) {
		    var files = data.response.initialPreviewConfig;
		    files.forEach(function(file) {
			    init_files.push(file.key);
			});
			obj.val(JSON.stringify(init_files));
		})
		.on('fileuploaderror', function(event, data, msg) {
		    alert(msg);
		});
		;
	}

	function reg_ajaxfileupload_file()
	{
		$(".ajaxfileuplod_file").each(function() { 
			set_fileupload_file($(this));
		});
	}

	function set_fileupload_file(obj)
	{
		f_html = $('<div></div>').append(
					$('<p></p>').append(
						$('<input type="file" style="width:'+obj.attr("size")+'px" />')
					)
				).append($('<p></p>').append(
							$('<img style="width:'+obj.attr("size")+'px" /><a></a><input type="button" value="重 新 上 傳" />')
						));
				
		obj.after(f_html);
		
		h_html = obj;
		f_item = $("input:file",h_html.parent());
		img_item = $("img",h_html.parent());
		a_item = $("a",h_html.parent());
		b_item = $("input:button",h_html.parent());
		
		
		
		f_p = f_item.parent();
		r_p = f_p.next();
		
		if(h_html.val())
		{
			f_p.hide();
			
			if(h_html.attr("id").indexOf("sub_product") != -1 || h_html.val().indexOf("pdf") != -1)
			{
				a_item.html(h_html.val()).attr("href",$("#img_src").val() + "/" + h_html.attr("folder") + "/" + h_html.val()).attr("target","_blank");
				img_item.remove();
			}
			else
				img_item.attr("src",$("#img_src").val() + "/" + h_html.attr("folder") + "/" + h_html.val());
			
		}	
		else
			r_p.hide();
			
		b_item.click(function(){
			$(this).parent().hide();
			$(this).parent().prev().show();
			$(this).prev().removeAttr("src");            
		});
		
		new AjaxUpload(f_item,{
			action: $("#site_root").val() + '/do_upload/'+h_html.attr("id")+'/' + h_html.attr("folder"), 
			autoSubmit: true,
	        name: h_html.attr("id"),
	        responseType: 'json',
	        onSubmit: function (file, ext) {
	            this.disable();
	        },
	        onComplete: function (file, response) {
	        	if(response.error)
	        	{
	        	    alert(response.error);  	
	        	}
	        	else
	        	{
	        		ch_html = $("#" + response.upload_data.name);
	        		ch_html.val(response.upload_data.file_name);
	        		
	        		if(response.upload_data.name.indexOf("sub_product") != -1 || response.upload_data.file_name.indexOf("pdf") != -1|| response.upload_data.name.indexOf("csv") != -1){
	        			ca_item = $("a",ch_html.parent());
	        			ca_item.html(response.upload_data.file_name);
	        			ca_item.attr("href",$("#img_src").val() + "/" + ch_html.attr("folder") + "/" + response.upload_data.file_name);
	        			ca_item.attr("target","_blank");
						ca_item.parent().show();
						ca_item.parent().prev().hide();
	        		}
	        		else{
		        		cimg_item = $("img",ch_html.parent());
						cimg_item.attr("src",$("#img_src").val() + "/" + ch_html.attr("folder") + "/" + response.upload_data.file_name);
						cimg_item.parent().show();
						cimg_item.parent().prev().hide();
	        		}
	        	}               
	            this.enable();
	        }
		});
	}
})
	