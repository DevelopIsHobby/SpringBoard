console.log("Reply Module...");

let replyService = (function() {

    function add(reply, callback, error) {
        console.log("add reply.....");


        $.ajax({
            type: "post",
            url: "/replies/new",
            data: JSON.stringify(reply),
            contentType: "application/json; charset=utf-8",
            success: function (result, status, xhr) {
                if (callback) {
                        callback(result);
                }
            }, error: function (result, status, er) {
                if (error) {
                    error(er);
                }
            }
        })
    }

    function getList(param, callback, error) {
        const bno = param.bno;
        const page = param.page || 1;

        $.getJSON("/replies/pages/" + bno + "/" + page + ".json",
            function(data) {
                if(callback) {
                    // callback(data);
                    callback(data.replyCnt, data.list);
                }
            }).fail(function (xhr, status, err) {
                if(error) {
                    error();
                }
        })
    }

    function remove(rno, replyer, callback, error) {

        console.log("--------------------------------------");
        console.log(JSON.stringify({rno:rno, replyer:replyer}));


        $.ajax({
            type: "delete",
            url: "/replies/"+rno,
            data : JSON.stringify({rno:rno, replyer:replyer}),
            contentType : "application/json; charset=utf-8",

            success: function (result, status, xhr) {
                if (callback) {
                    callback(result);
                }
            }, error: function (result, status, er) {
                if (error) {
                    error(er);
                }
            }
        })
    }

    function update(reply, callback, error) {
        console.log("RNO: " + reply.rno);
        $.ajax({
            type: "put",
            url: "/replies/"+reply.rno,
            data: JSON.stringify(reply),
            contentType: "application/json; charset=utf-8",
            success: function (result, status, xhr) {
                if (callback) {
                    callback(result);
                }
            }, error: function (result, status, er) {
                if (error) {
                    error(er);
                }
            }
        })
    }

    function get(rno, callback, error) {
        $.get("/replies/" + rno+ ".json", function (result) {
            if(callback) {
                callback(result);
            }
        }).fail(function(xhr, status, err) {
            if(error) {
                error();
            }
        })
    }

    return {
        add : add,
        getList : getList,
        remove : remove,
        update : update,
        get : get};
})();

