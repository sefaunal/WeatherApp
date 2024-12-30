(function($) {

    "use strict";

    $(".toggle-password").click(function() {

        $(this).toggleClass("fa-eye fa-eye-slash");
        var input = $($(this).attr("toggle"));
        if (input.attr("type") == "password") {
            input.attr("type", "text");
        } else {
            input.attr("type", "password");
        }
    });

    $(".toggle-password-old").click(function() {

        $(this).toggleClass("fa-eye fa-eye-slash");
        var input = $("#old-password");
        if (input.attr("type") == "password") {
            input.attr("type", "text");
        } else {
            input.attr("type", "password");
        }
    });

    $(".toggle-password-new").click(function() {

        $(this).toggleClass("fa-eye fa-eye-slash");
        var input1 = $("#new-password");
        var input2 = $("#new-password-retype");
        if (input1.attr("type") == "password") {
            input1.attr("type", "text");
            input2.attr("type", "text");
        } else {
            input1.attr("type", "password");
            input2.attr("type", "password");
        }
    });

})(jQuery);