let senderId;
let senderImageUrl;
let receiverId;
let receiverName;
let receiverImageUrl;

$(document).ready(function () {
    senderId = Number($('#current-user-id').attr('user-id'));
    receiverId = Number($('#current-user-id').attr('user-id'));
    receiverName = $('#current-user-name').attr('user-name');
    receiverImageUrl = $('.profile-image').attr('src');

    setInterval(function () {
        usersUpdate(senderId);
    }, 10000);

    setInterval(function () {
        loadMessages(receiverId, receiverName, receiverImageUrl)
    }, 8000);

    setInterval(function () {
        $('#user-search').val('');
    }, 20000)
});

/*-------------------------------------------------------for contacts-------------------------------------------------*/
function usersUpdate(currentUserId) {
    let usersViewContainer = $('.user-list');
    $.ajax({
        type: 'POST',
        url: '/usersList',
        success: function (data) {
            let template = '';

            data.forEach(function (userData) {
                if (userData['sender_id'] != currentUserId) {
                    template += userViewTemplate(userData);
                }
            });

            usersViewContainer.empty();
            usersViewContainer.append(template);
        }
    });
}

function userSearch() {
    let searchEmail = $('#user-search').val();

    $.ajax({
        type: 'POST',
        url: '/userSearch',
        data: {email: searchEmail},
        success: function (data) {

            $('#user-search').val('');
            let newUser = userViewTemplate(data);
            $('.user-list li:last-child').after(newUser);
            InviteContact(data, 'Hello!!!');
        },
        error: function (error) {
            $('#user-search').css('color', 'red').val(error.responseText);
        }
    });
}

function userViewTemplate(userData) {

    let template = '<li class="contact' + (userData['full_name'] == receiverName ? ' selected' : '') +
                    ' " onclick="loadMessages(' + userData['sender_id'] + ', \'' + userData['full_name'] + '\', \'' +
                    userData['image_url'] + '\')">' +
        '<div class="user-image">' +
        '<img class="profile-image" src="' + userData['image_url'] + '" alt="' + userData['full_name'] + '"/>' +
        '<span class = "user-activity contact-place ' + (userData['active'] ? 'active' : 'passive') + ' "></span>' +
        '</div>' +
        '<span class="user-name">' + userData['full_name'] + '</span>' +
        '</li>';
    return template;
}

/*-------------------------------------------------------for massages-------------------------------------------------*/
function sendMessage() {

    let message = $('#message-box').val().trim();

    $.ajax({
        type: 'POST',
        url: '/createMessage',
        data: {senderId: senderId, receiverId: receiverId, message: message},
        success: function () {
            $('#message-box').val('');
            loadMessages(receiverId, receiverName, receiverImageUrl);
        }
    });
}

function loadMessages(contact_id, contact_name, contact_img) {
    if (event) {
        event.stopPropagation();
    }

    senderId = Number($('#current-user-id').attr('user-id'));
    senderImageUrl = $('.profile-image').attr('src');
    receiverId = contact_id;
    receiverName = contact_name;
    receiverImageUrl = contact_img;

    $.ajax({
        type: 'POST',
        url: '/messages',
        data: {senderId: senderId, receiverId: receiverId},
        success: function (data) {
            fillChatArea(data, receiverName, receiverImageUrl);
        },
        error: function (error) {
            console.log('error');
        }
    });
}

function fillChatArea(messageContent, receiverName, receiverImg) {
    $('.chat-user-image').find('img').attr('src', receiverImg);
    $('.chat-user-name').text(receiverName);
    $('.contact').removeClass('selected');
    $('.user-name:contains(' + receiverName + ')').parent().addClass('selected');

    let messageArea = $('.message-area');
    messageArea.empty();
    messageContent.forEach(function (message) {

        if (message['sender_id'] == senderId) {
            messageArea.append(messageTemplate(message['message'], senderImageUrl, message['created_at'], 'sender'));
        } else {
            messageArea.append(messageTemplate(message['message'], receiverImg, message['created_at'], 'receiver'));
        }

        messageArea.scrollTop(messageArea.prop('scrollHeight'));
    });

}

function messageTemplate(massage, receiverImg, time, classValue) {

    let template = '<div class="message-wrapper ' + classValue + '">' +
        '<img src="' + receiverImg + '"/>' +
        '<div class="message-text-wrapper">' +
        '<span class="message-text">' + massage + '</span> <br>' +
        '<span class="time">' + DateAndTime(time) + '</span>' +
        '</div>' +
        '</div>';

    return template;
}

function InviteContact(newContact, inviteMessage) {
    receiverId = newContact['sender_id']
    receiverName = newContact['full_name'];
    receiverImageUrl = newContact['image_url'];
    let message = inviteMessage;

    $.ajax({
        type: 'POST',
        url: '/createMessage',
        data: {senderId: senderId, receiverId: receiverId, message: message},
        success: function () {
            loadMessages(receiverId, receiverName, receiverImageUrl);
        }
    });
}

function DateAndTime(timestamp) {
    let d = new Date(timestamp);
    let month = ((d.getMonth() + 1) < 9 ? '0' + (d.getMonth() + 1) : (d.getMonth() + 1));
    let day = (d.getDate() < 9 ? '0' + d.getDate() : d.getDate());
    let hour = (d.getHours() < 9 ? '0' + d.getHours() : d.getHours());
    let min = (d.getMinutes() < 9 ? '0' + d.getMinutes() : d.getMinutes());
    let time;

    time = hour + ':' + min + ' ' + day + '-' + month;
    return time;
}

function openMenuList() {

}