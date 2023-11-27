$(document).ready(function(){
	
	//Đăng ký sự kiện click cho toàn bộ class có tên là btn-xoa
	$(".btn-xoa").click(function(){
		var id_job = $(this).attr('id-job')
		var id_user = $(this).attr('id-user')
		var This = $(this)
		
		$.ajax({
		  method: "GET",
		  url: `http://localhost:8080/crm_project_02/api/task/delete?idJob=${id_job}&idUser=${id_user}`, //String literals
		
		})
		  .done(function( result ) {
			
			if(result.data == true){
				This.closest('tr').remove()
				console.log(id_user)

			}
			  console.log(result.data)
					
		  });
		
	})
})