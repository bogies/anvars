var rbacLogin = new Vue({
    el: '#rbac_login',
    data: {
        loginParam: {username:'', password:''}
    },
    methods: {
        reqLogin: function() {
            $.ajax({
                url: URI_DOMAIN+'/s/login.jsd',
                type: 'POST',
                data: {username: this.loginParam.username, password: this.loginParam.password},
                dataType: 'JSON',
                beforeSend:function(req) {
                    req.setRequestHeader(HeaderConstants.METHOD, 'post');
                    req.setRequestHeader(HeaderConstants.URI, '/rbacs/members/login');
                },
                success: function(result) {
                    console.log(result);
                    if (200 == result.code) {
                        if (StringUtils.isEmpty(returnUrl)) {
                            window.location.href = URI_DOMAIN+'/v/index.html';
                        } else {
                            window.location.href = returnUrl;
                        }
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    console.log(textStatus);
                }
            });
        }
    }
});
