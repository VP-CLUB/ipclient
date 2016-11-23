/**
 * Created by vpclub on 16-11-23.
 */
$(document).ready(function() {
    $.ajax({
        url: "http://ipservicedemo/greeting"
    }).then(function(data, status, jqxhr) {
        $('.greeting-id').append(data.id);
        $('.greeting-content').append(data.content);
        console.log(jqxhr);
    });
});