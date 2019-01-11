angular.module('ngAntsModules', []).directive('ngZtree', ['$timeout', function($timeout) {
    /**
     * zTree 实现
     * 相关属性:
     * z-setting: ztree设置参数
     */
    var zTreeDir = {
        require: 'ngModel',
		restrict: 'A',
		link: function(scope, element, attrs, ngModel) {
            ngModel.$render = function() {
                $.fn.zTree.init(element, scope[attrs.zSetting], ngModel.$modelValue);
            };
		}
    };
    return zTreeDir;
}]).directive('ngPopZtree', [function() {
    /**
     * zTree弹出实现
     * 样关属性: 
     * bind-tree: 设置绑定的zTree弹出dom
     * tree_check: ztree是否为多选, 为多选时点击节点不收回
     */
	var popTree = {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var onTreeBodyDown = function(event) {
				var checkClickTree = false;
				if (attrs.treeCheck && 
					($(event.target).parents("#"+attrs.bindTree).length > 0)) {
						/// 多选时点击zTree节点不收起
					return;
				}
				if (event.target.id != element.context.id &&
					event.target.id != attrs.bindTree) {
			  		var e = $("#"+attrs.bindTree);
			  		e.fadeOut("fast");
			  		$("body").unbind("mousedown", onTreeBodyDown);
				}
			  };
			  
			element.click(function() {
				var offset = element.offset();
				var tree = $("#"+attrs.bindTree);
				var oh = element.outerHeight();
				tree.css({left:offset.left+'px', top:(parseInt(offset.top)+oh)+'px'}).slideDown('fast');
				$("body").bind("mousedown", onTreeBodyDown);
			});
		}
	};
	return popTree;
}]).directive('ngPagehelperBar', [function() {
    /**
     * mybatis PageHelper 的翻页工具条
     * 相关属性:
     * pagebar-html: pagerbar对应的模板地址, 与tommy显示格式相同
     * goto-page: 翻页对应的处理函数, 传参: 目的页码
     */
    var pageHelper = {
        restrict: 'AE',
        replace: true,
        // 将内部变量ngPageHelperData与外部变量pageInfo绑定
        scope: {ngPageHelperData:'=pageInfo', ngPageHelperGoto: '=gotoPage'}, 
        link: function(scope, element, attrs) {
            scope.getContentUrl = function() {
                return attrs.pagebarHtml;
            };   
        },
        template: '<div ng-include="getContentUrl()"></div>'
    };
    return pageHelper;
}]);
