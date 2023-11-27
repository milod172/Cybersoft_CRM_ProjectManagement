$(document).ready(function(){
	
	//Đăng ký sự kiện click cho toàn bộ class có tên là btn-xoa
	$(".btn-xoa").click(function(){
		var id = $(this).attr('id-project')
		var This = $(this)
		
		$.ajax({
		  method: "GET",
		  url: `http://localhost:8080/crm_project_02/api/group/delete?id=${id}`, //String literals
		
		})
		  .done(function( result ) {
			
			if(result.data == true){
				This.closest('tr').remove()

			}
			  console.log(result.data)
					
		  });
		
	})
})