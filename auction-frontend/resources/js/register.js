const errorLineStart = "<p class='error'>";
const errorLineEnd = "</p>";
const successLineStart = "<p class='success'>";
const successLineEnd = "</p>";


function register() {

    $("form#register_form .error").remove();

    const firstName = $("form#register_form input#firstName").val();

    const lastName = $("form#register_form input#lastName").val();

    const email = $("form#register_form input#email").val();
    const password = $("form#register_form input#password").val();
    const confirmPassword = $("form#register_form input#confirmPassword").val();

    const userDto = JSON.stringify({ firstName, lastName, email, password, confirmPassword });

    $.ajax({
        url: 'http://localhost:8080/api/register',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data: userDto,
        //     processData: false,
        success: function (data, textStatus, jQxhr) {

            console.log('success: ' + JSON.stringify(data));
            const successLine = successLineStart + "Registration has been completed!"
                + successLineEnd;
            var successElement = $("form#register_form .registerSuccessful");
            $(successElement).append(successLine);
        },
        error: function (jqXhr, textStatus, errorThrown) {
            const errorsArray = jqXhr.responseJSON.errors;
            if (errorsArray != undefined) {
                for (var index = 0; index < errorsArray.length; index++) {

                    const element = $("#" + errorsArray[index].field);

                    if (!$(element).next().hasClass("error")) {
                        const errorLine = errorLineStart + errorsArray[index].defaultMessage + errorLineEnd;
                        $(errorLine).insertAfter(element);

                    }

                    // console.log("error " + index + "-> " + errorsArray[index].field + ":"
                    //     + errorsArray[index].defaultMessage);
                }
            } else {
                const errorMessage = jqXhr.responseJSON.message;
                console.log("singura eroare: " + errorMessage);
            }

        }
    });

}


function validateUsername(element){
    var firstName = $(element).val();
    if(firstName.length<6){
        if (! $(element).next().hasClass("error")) {
            const errorLine = errorLineStart + "First name not valid" + errorLineEnd;
            $(errorLine).insertAfter(element);
        }
    }
}
$(document).ready(function () {
    $("form#register_form button").click(function () {
        register();
    });

    $("#firstName").focusout(function(){
        validateUsername(this);
    });

});
