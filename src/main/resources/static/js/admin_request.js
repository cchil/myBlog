/**
 * 更新Main中的内容
 * @param {Object} html 传入的Html将会被插入到main标签的内部中去
 */
function updateFragment(html) {
    swal.close();
    var main = $('#main');
    main.hide("fade", 50, function () {
        main.empty();
        main.html(html);
        main.show("fade", {}, 350);
    });
}

/**
 * 检查字符串不是空的 工具方法
 */
function isNotBlank(str) {
    if (str == null || str.length == 0) {
        return false;
    }
    return true;
}

/**
 * 仪表板
 * @param page
 */
function item_1(page) {
    if (localUrl == null || localUrl.length == 0) {
        localUrl = '..';
    }
    if (page == null) {
        page = 1;
    }
    sweetAlert("系统提示","加载中","info");
    $.get(localUrl + '/main?page=' + page, function (d, s, t) {
        var result = t.responseText;
        updateFragment(result);
    });
}


/**
 * 资源管理
 * @param page
 */
function item_2(page) {
    if (localUrl == null || localUrl.length == 0) {
        localUrl = '..';
    }
    if (page == null) {
        page = 1;
    }
    sweetAlert("系统提示","加载中","info");
    $.get(localUrl + '/resources?page=' + page, function (d, s, t) {
        var result = t.responseText;
        updateFragment(result);
    });
}

/**
 * 我的文章
 */
function item_3(id) {
    if (localUrl == null || localUrl.length == 0) {
        localUrl = '..';
    }
    if (id == null) {
        id = -1;
    }
    sweetAlert("系统提示","加载中","info");
    $.get(localUrl + '/content-manage?labelId=' + id, function (d, s, t) {
        var result = t.responseText;
        updateFragment(result);
    });
}

function item_4() {
    if (localUrl == null || localUrl.length == 0) {
        localUrl = '..';
    }
    sweetAlert("系统提示","加载中","info");
    $.get(localUrl + '/account-manage', function (d, s, t) {
        var result = t.responseText;
        updateFragment(result);
    });
}

function item_5() {
    if (localUrl == null || localUrl.length == 0) {
        localUrl = '..';
    }
    var html = '<iframe src="' + localUrl + '/labels-tem" id="mainiframe" width="100%" height="600" frameborder="0" scrolling="no">\n' +
        '\t\t\t\t\t\t\n' +
        '\t\t\t\t\t</iframe>\n' +
        '\n' +
        '\t\t\t\t\t<script>\n' +
        '\t\t\t\t\t\tfunction changeFrameHeight() {\n' +
        '\t\t\t\t\t\t\tvar ifm = document.getElementById("mainiframe");\n' +
        '\t\t\t\t\t\t\tifm.height = document.documentElement.clientHeight - 56;\n' +
        '\t\t\t\t\t\t}\n' +
        '\t\t\t\t\t\twindow.onresize = function() {\n' +
        '\t\t\t\t\t\t\tchangeFrameHeight();\n' +
        '\t\t\t\t\t\t}\n' +
        '\t\t\t\t\t\t$(function() {\n' +
        '\t\t\t\t\t\t\tchangeFrameHeight();\n' +
        '\t\t\t\t\t\t});\n' +
        '\t\t\t\t\t</script>';
    updateFragment(html);
}

/**
 * 发布文章
 * @param type
 */
function item_6(type) {
    if (localUrl == null || localUrl.length == 0) {
        localUrl = '..';
    }
    if (type == null) {
        type = 1;
    }
    sweetAlert("系统提示","加载中","info");
    $.get(localUrl + '/handle-content?type=' + type, function (d, s, t) {
        var result = t.responseText;
        updateFragment(result);
    });
}

function item_7() {
    if (localUrl == null || localUrl.length == 0) {
        localUrl = '..';
    }
    sweetAlert("系统提示","加载中","info");
    $.get(localUrl + '/resources-tem', function (d, s, t) {
        var result = t.responseText;
        updateFragment(result);
    });
}

function item_8() {
    if (localUrl == null || localUrl.length == 0) {
        localUrl = '..';
    }
    sweetAlert("系统提示","加载中","info");
    $.get(localUrl + '/view-manage', function (d, s, t) {
        var result = t.responseText;
        updateFragment(result);
    });
}


function item_9(option) {
    if (localUrl == null || localUrl.length == 0) {
        localUrl = '..';
    }
    var data = '';
    if (option != null) {
        data = option;
    }
    sweetAlert("系统提示","加载中","info");
    $.get(localUrl + '/comment', data, function (d, s, t) {
        var result = t.responseText;
        updateFragment(result);
    });
}

function item_10() {
    if (localUrl == null || localUrl.length == 0) {
        localUrl = '..';
    }
    sweetAlert("系统提示","加载中","info");
    $.get(localUrl + '/explain-tem', function (d, s, t) {
        var result = t.responseText;
        updateFragment(result);
    });
}