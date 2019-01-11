/// Tommy Micro Service Promise
/**
 * 请求数据
 * @param {string} url 请求服务的地址
 * @param {string} method 请求服务的方法
 * @param {object} data 参数数据, 格式:{key:value,...}
 * @param {object} loadingParams 请求数据时的loading参数, 格式{dom: jqueryObject, params:loadingParamsObject}
 */
function Tmsp(url, method, data, loadingParams) {
    if (!data) {
        data = {none:'none'};
    }
    var uri = encodeURI(url);

    var p = new Promise(function(resolve, reject) {
        $.ajax({
            url: URI_DOMAIN+'/s/do.jsd',
            type: 'POST',
            data: data,
            dataType: 'JSON',
            beforeSend:function(req) {
                if (loadingParams) {
                    loadingParams.dom.loading(loadingParams.params);
                }
                req.setRequestHeader('V-Req-Method', method);
                req.setRequestHeader('V-Req-Uri', uri);
            },
            success: function(result) {
                if (loadingParams) {
                    loadingParams.dom.loading('toggle');
                }
                if (200 == result.code) {
                    resolve(result);
                } else {
                    reject(result);
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                if (loadingParams) {
                    loadingParams.dom.loading('toggle');
                }
                reject(new Error(textStatus));
            }
        });
    });

    return p;
}
