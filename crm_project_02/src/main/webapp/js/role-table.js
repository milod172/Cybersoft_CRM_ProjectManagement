$(document).ready(function(){
	
	//Đăng ký sự kiện click cho toàn bộ class có tên là btn-xoa
	$(".btn-xoa").click(function(){
		var id = $(this).attr('id-role')
		var This = $(this)
		
		$.ajax({
		  method: "GET",
		  url: `http://localhost:8080/crm_project_02/api/role/delete?id=${id}`, //String literals
		
		})
		  .done(function( result ) {
			//Console.log là System.out.print
			//Result đại diện cho kết quả từ link url trả về.
			//Lấy giá trị kiểu Object trong JS thì tenbien.key
			//.parent 		==> đi ra 1 thẻ cha
			//.remove 		==> là xóa thẻ
			//.closet		==> đi ra thằng cha được chỉ định
			if(result.data == true){
				This.closest('tr').remove()
			}
			  
			console.log(result.data)			
		  });
		
	})
})