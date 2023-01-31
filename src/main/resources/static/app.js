"use strict";
{
    $(initApp);

    function initApp(){
        console.log("init");

        getCurrentGw();

        setCurrentGw();

        setDefaultGw();
    }

    function getCurrentGw() {
        $.getJSON( "/api/gw/current", function( gw ) {

            $(".btn-primary")
                .removeClass("btn-primary")
                .addClass("btn-outline-primary");

            $(`#${gw.id}`)
                .removeClass("btn-outline-primary")
                .addClass("btn-primary");
        });
    }

    function setCurrentGw() {
        $(".gw").on("click", function() {

            $.ajax({
                url: "/api/gw/current",
                method: "POST",
                data: `{"id":"${this.id}"}`,
                contentType: "application/json; charset=utf-8"
            }).done(getCurrentGw);
        });
    }

    function setDefaultGw() {
            $("#default-gw").on("click", function() {

                $.ajax({
                    url: "/api/gw/current",
                    method: "DELETE"
                }).done(getCurrentGw);
            });
        }
}