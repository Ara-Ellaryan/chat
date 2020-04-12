function doRegister() {
    event.preventDefault();
    let isOk = true;

    let name = $('#input-name').val();
    $('#input-name-span').text('');
    if (isEmpty(name)) {
        $('#input-name-span').text('Name is required!');
        isOk = false;
    }

    let surname = $('#input-surname').val();
    $('#input-surname-span').text('');
    if (isEmpty(surname)) {
        $('#input-surname-span').text('Surname is required!');
        isOk = false;
    }

    let email = $('#input-email').val();
    $('#input-email-span').text('');
    if (isEmpty(email)) {
        $('#input-email-span').text('Email is required!');
        isOk = false;
    } else if (!emailValidate(email.trim())) {
        $('#input-email-span').text('Wrong Email Format!');
        isOk = false;
    }

    let password = $('#input-password').val();
    $('#input-password-span').text('');
    $('#input-confirmPassword-span').text('');
    if (isEmpty(password)) {
        $('#input-password-span').text('Password is required!');
        isOk = false;
    } else {
        let confirmPassword = $('#input-confirmPassword').val();
        if (password.trim() !== confirmPassword.trim()) {
            $('#input-confirmPassword-span').text('Password do not match!');
            isOk = false;
        }
    }

    if (isOk) {
        $('#register-form').submit();
    }
}

function doLogin() {
    event.preventDefault();
    let isOk = true;

    let email = $('#input-email').val();
    $('#input-email-span').text('');
    if (isEmpty(email)) {
        $('#input-email-span').text('Email is required!');
        isOk = false;
    } else if (!emailValidate(email.trim())) {
        $('#input-email-span').text('Wrong Email Format!');
        isOk = false;
    }

    let password = $('#input-password').val();
    $('#input-password-span').text('');
    if (isEmpty(password)) {
        $('#input-password-span').text('Password is required!');
        isOk = false;
    }

    if(isOk){
        $('#login-form').submit();
    }
}


function isEmpty(value) {
    return !value || value.trim().length === 0;
}

function emailValidate(email) {
    let regexp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regexp.test(String(email).toLowerCase());
}