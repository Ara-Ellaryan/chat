let currentSenderId;
let currentReceiverId;
let currentUserName;
let currentImageUrl;

$(document).ready(function () {
    currentSenderId = Number($('#current-user-id').attr('user-id'));
    currentReceiverId = Number($('#current-user-id').attr('user-id'));
    currentUserName = String($('#current-user-name').attr('user-name'));

    setInterval(function () {
        usersUpdate(currentSenderId);
    }, 10000);

    setInterval(function () {
        $('#user-search').val('');
    }, 20000)
});


function userSearch() {
    let searchEmail = $('#user-search').val();
    $.ajax({
       type: 'POST',
       url: '/userSearch',
       data: {email: searchEmail},
       success:function (data) {

           $('#user-search').val('');

           let newUser = userViewTemplate(data);

           $('.user-list li:last-child').after(newUser);

           /*avelacnel selected class@*/
           $('.chat-user-image').find('img').attr('src', data.imageUrl);
           $('.chat-user-image').find('img').attr('alt', data.name + ' ' + data.surname);
           $('.chat-user-name').text(data.name + ' ' + data.surname);
           $('.message-area').empty();

       } ,
        error: function (error) {
            $('#user-search').css('color', 'red').val(error.responseText);
        }
    });
}

function usersUpdate(currentUserId) {
    let usersViewContainer = $('.user-list');
    $.ajax({
        type: 'POST',
        url: '/usersList',
        success: function (data) {
            let template = '';

            data.forEach(function (userData) {
                if(userData['sender_id'] != currentUserId){
                    template += userViewTemplate(userData);
                }
            });

            usersViewContainer.empty();
            usersViewContainer.append(template);
        }
    });
}

function userViewTemplate(userData) {
    let template =  '<li class="contact" onclick="loadMessages()">'+
                        '<div class="user-image">' +
                            '<img class="profile-image" src="' + userData['image_url'] + '" alt="'+ userData['full_name'] + '"/>' +
                            '<span class = "user-activity contact-place ' + (userData['active'] ? 'active' : 'passive') + ' "></span>' +
                        '</div>' +
                        '<span class="user-name">' + userData['full_name'] + '</span>' +
                    '</li>';
    return template;
}
function loadMessages() {

}

function sendMessage() {

}

function openMenuList() {

}