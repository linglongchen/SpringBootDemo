( function() {

	function stripHtml( value ) {

		// Remove html tags and space chars
		return value.replace( /<.[^<>]*?>/g, " " ).replace( /&nbsp;|&#160;/gi, " " )

		// Remove punctuation
		.replace( /[.(),;:!?%#$'\"_+=\/\-“”’]*/g, "" );
	}

	$.validator.addMethod( "maxWords", function( value, element, params ) {
		return this.optional( element ) || stripHtml( value ).match( /\b\w+\b/g ).length <= params;
	}, $.validator.format( "Please enter {0} words or less." ) );

	$.validator.addMethod( "minWords", function( value, element, params ) {
		return this.optional( element ) || stripHtml( value ).match( /\b\w+\b/g ).length >= params;
	}, $.validator.format( "Please enter at least {0} words." ) );

	$.validator.addMethod( "rangeWords", function( value, element, params ) {
		var valueStripped = stripHtml( value ),
			regex = /\b\w+\b/g;
		return this.optional( element ) || valueStripped.match( regex ).length >= params[ 0 ] && valueStripped.match( regex ).length <= params[ 1 ];
	}, $.validator.format( "Please enter between {0} and {1} words." ) );
	
	$.validator.addMethod( "phoneNum", function( value, element, params ) {
		return this.optional( element ) || /^1[3|4|5|7|8][0-9]\d{8}$/.test(stripHtml( value ));
	}, $.validator.format( "请输入正确的手机号码" ) );

    $.validator.addMethod( "money", function( value, element ) {
        return this.optional( element ) || /^([1-9]\d{0,9}|0)([.]?|(\.\d{1,2})?)$/.test(value);
    }, $.validator.format( "请输入正确的金额" ) );
}() );
