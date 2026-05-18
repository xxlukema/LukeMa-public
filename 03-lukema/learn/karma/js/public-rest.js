'use strict';


const loadTemplate = () => {
    console.log('Ready 1.');

    const src = $('div#public-rest-container').data('templateSrc');
    console.log('template file: ' + src);
    //$('div#public-rest-container').replaceWith(src);

    $.get(src, function (data) {
        $('div#public-rest-container').append(data.getElementsByTagName("template")[0].innerHTML);
        $('div#public-rest-container').removeAttr('data-template-src');
        // alert("Load was performed: " + data.getElementsByTagName("template")[0].innerHTML);
    });

    console.log('Ready 1 loaded.');
};


$(document).ready(
    loadTemplate
);


const bindData = () => {

    console.log('Ready 2.');

    // Standalone format function instead of modifying String.prototype
    function format(str) {
        const args = Array.prototype.slice.call(arguments, 1);
        return str.replace(/{(\d+)}/g, function (match, number) {
            return typeof args[number] != 'undefined'
                ? args[number]
                : match
                ;
        });
    }

    const doGet = () => {

        $('#result').text('Please wait...');

        const base_url = 'http://localhost:3000';
        const full_url = base_url + '/{0}/{1}';

        const method = $('#method').val();
        const id = $('#id').val();

        const url = format(full_url, method, id);

        console.log('################ url = ' + url);
        $.ajax({
            // crossDomain: true,
            type: "GET",
            url: url,
            dataType: "json",
            // jsonp: false,
            // jsonpCallback: "jsonpCallback",
            success: function (data) {
                console.log('Received server data: ' + JSON.stringify(data));
                $('#result').text(data.id + ' ' + data.title + ' ' + data.author);
            },
            error: function (xhr, statusText, err) {
                // alert("Error: statusText = " + statusText + ' message = ' + err.message);
                console.log("error: statusText = " + statusText + ' ' + JSON.stringify(xhr) + ' ' + err);
            }
        });
    }

    $('#getState').on('click', doGet);
};


$(bindData);


const localStorageReady = () => {
    console.log('localStorageReady');

    let name = localStorage.getItem('name');

    $('#localStorage').text(name);

    if (isEmpty(name)) {
        localStorage.setItem('name', 'Luke Ma');
        name = localStorage.getItem('name');

        console.log('Name (localStorage) is not found but now it is set to: name = ' + name);
    } else {
        console.log('Name (localStorage) exists: name = ' + name);
    }

};

$(localStorageReady);


const sessionStorageReady = () => {
    console.log('sessionStorageReady');

    let name = sessionStorage.getItem('name');

    $('#sessionStorage').text(name);

    if (isEmpty(name)) {
        sessionStorage.setItem('name', 'Luke Ma');
        name = sessionStorage.getItem('name');

        console.log('Name (sessionStorage) is not found but now it is set to: name = ' + name);
    } else {
        console.log('Name (sessionStorage) exists: name = ' + name);
    }

};

$(sessionStorageReady);



