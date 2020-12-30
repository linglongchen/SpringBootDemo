$.validator.setDefaults({
	highlight : function(e) {
		$(e).closest(".form-group").removeClass("has-success").addClass(
				"has-error")
	},
	success : function(e) {
		e.closest(".form-group").removeClass("has-error").addClass(
				"has-success")
	},
	errorElement : "span",
	errorPlacement : function(e, r) {
		e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent()
				.parent() : r.parent())
	},
	errorClass : "help-block m-b-none",
	validClass : "help-block m-b-none"
}), $().ready(function() {
	$("#commentForm").validate();
	var e = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			userName : {
				required : !0,
				minlength : 2
			},
			userAge : {
				required : !0,
				minlength : 1
			},
			userSex : "required"
		},
		messages : {
			userName : {
				required : e + "请输入您的用户名",
				minlength : e + "用户名必须两个字符以上"
			},
			userAge : {
				required : e + "请输入您的密码",
				minlength : e + "密码必须1个字符以上"
			},
			userSex : {
				required : e + "必须同意协议后才能注册",
				element : "#agree-error"
			}
		}
	}), $("#username").focus(function() {
		var e = $("#firstname").val(), r = $("#lastname").val();
		e && r && !this.value && (this.value = e + "." + r)
	})
});
