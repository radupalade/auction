const errorLineStart = "<p class='error'>";
const errorLineEnd = "</p>";
const successLineStart = "<p class='success'>";
const successLineEnd = "</p>";
let base64Image = "";

function loadImageAsBase64() {
    const file = $("#photo")[0].files[0];
    const reader = new FileReader();
    reader.addEventListener("load", function () {
        base64Image = reader.result;
    }, false);
    reader.readAsDataURL(file);
}

function handleError(jqXhr) {
    console.log(jqXhr);
    // const errorsArray = jqXhr.responseJSON.errors;
    //
    // if (errorsArray != undefined) {
    //   for (var index = 0; index < errorsArray.length; index++) {
    //     const element = $("#" + errorsArray[index].field);
    //     if (!$(element).next().hasClass("error")) {
    //       const errorLine = errorLineStart + errorsArray[index].defaultMessage + errorLineEnd;
    //       $(errorLine).insertAfter(element);
    //     }
    //   }
    // } else {
    //   const errorMessage = jqXhr.responseJSON.message;
    //   console.log("singura eroare: " + errorMessage);
    // }
}

function handleSuccess(data) {
    console.log('success: ' + JSON.stringify(data));
    const successLine = successLineStart + "Item has been added!"
        + successLineEnd;
    var successElement = $("form#addItem_form .addSuccessful");
    $(successElement).append(successLine);
    $("input").val("");
}

function createAuthorizationHeader() {
    const jwt = localStorage.jwt;
    if (jwt) {
        return { "Authorization": "Bearer " + jwt };
    } else {
        return {};
    }
}

function addItem() {
    const name = $("form#addItem_form input#name").val();

    const description = $("form#addItem_form input#description").val();

    const category = $("form#addItem_form input#category").val();
    const startingPrice = $("form#addItem_form input#startingPrice").val();
    const startDate = $("form#addItem_form input#startDate").val();
    const endDate = $("form#addItem_form input#endDate").val();

    const photo = base64Image;
    const itemDto = JSON.stringify({
        name, description, category, startingPrice, startDate, endDate, photo
    });
    $.ajax({
        url: 'http://localhost:8080/api/admin/item',
        dataType: 'json',
        headers: createAuthorizationHeader(),
        type: 'post',
        contentType: 'application/json',
        data: itemDto,
        success: function (data, textStatus, jQxhr) {
            handleSuccess(data);
        },
        error: function (jqXhr, textStatus, errorThrown) {
            handleError(jqXhr);
        }
    });
}

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

function addItemOnPage(itemDto) {
    console.log(itemDto + "!!!");
    const newItem = $(".itemPattern").clone();
    $(newItem).find(".itemName").html(itemDto.name);
    $(newItem).find(".itemName").attr("href", "itemPage.html?id=" + itemDto.id);
    $(newItem).find(".itemCategory").html(itemDto.category);
    $(newItem).find(".itemPrice").html("$" + itemDto.currentPrice);
    $(newItem).find(".itemImage").attr("src", itemDto.photo);
    $(newItem).removeClass("itemPattern");
    $(".itemList").append(newItem);
}

function getAllItems(isAdmin) {
    let url = "";
    if (isAdmin) {
        url = 'http://localhost:8080/api/admin/item';
    } else {
        url = 'http://localhost:8080/api/user/item'
    }
    $.ajax({
        url: url,
        dataType: 'json',
        headers: createAuthorizationHeader(),
        type: 'get',
        contentType: 'application/json',
        success: function (itemList, textStatus, jQxhr) {
            console.log(itemList);
            itemList.forEach(addItemOnPage);

        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(jqXhr);
        }
    });
}

function setHeaderForUser() {
    $.ajax({
        url: 'http://localhost:8080/api/authenticated/details',
        dataType: 'json',
        headers: createAuthorizationHeader(),
        type: 'get',
        contentType: 'application/json',
        success: function (headerDto, textStatus, jQxhr) {
            console.log(headerDto);
            $(".helloMessage").text("Hello, " + headerDto.firstName + "!");
            if (headerDto.admin) {
                $("li.nav-item.adminOnly").show();

            } else {
                $("li.nav-item.userOnly").show();

            }
            getAllItems(headerDto.admin);
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(jqXhr);
        }
    });
}

function addLogout() {
    $(".button.logout").click(function () {
        localStorage.jwt = "";
        window.location.href = "login.html";
    });
}

$(document).ready(function () {
    setHeaderForUser();
    addLogout();
});
