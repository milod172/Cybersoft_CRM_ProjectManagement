//Khi nào file HTML được Load thì thực hiện gì đó  --> $(document).ready()
$(document).ready(function(){
	
	//RestTemplate, HttpClient   (Tìm hiểu mấy thằng này --> Gọi đường dẫn bằng code, không phải browser
	//Đăng ký sự kiện click: $("tên_thẻ || tên_class || id").click
	//class ký hiệu dấu .
	//id ký hiệu dấu #
	
	//Đăng ký sự kiện click cho toàn bộ class có tên là btn-xoa
	$(".btn-xoa").click(function(){
		var id_user = $(this).attr('id-user')
		//Từ khóa this đại diện cho function đang thực thi
		var This = $(this)
		
		$.ajax({
		  method: "GET",
		  url: `http://localhost:8080/crm_project_02/api/user/delete?idUser=${id_user}`, //String literals
		 // data: { name: "John", location: "Boston" }	//Tham số data chỉ giành cho phương thức POST
		})
		  .done(function( result ) {
			//Console.log là System.out.print
			//Result đại diện cho kết quả từ link url trả về.
			//Lấy giá trị kiểu Object trong JS thì tenbien.key
			//.parent 		==> đi ra 1 thẻ cha
			//.remove 		==> là xóa thẻ
			//.closet		==> đi ra thằng cha được chỉ định
			if(result.data == true){
				//This.parent().parent().remove()			//Đang đại diện button xóa
				This.closest('tr').remove()
			}
			  
			console.log(result.data)			
		  });
		
		
	})
})