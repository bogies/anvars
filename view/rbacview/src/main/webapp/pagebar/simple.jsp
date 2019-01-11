<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
    .pagin{position:relative;margin-top:10px;padding:0 12px;font-size:12px}
    .pagin .blue{color:#056dae;font-style:normal;}
    .pagin .paginList{position:absolute;right:12px;top:0;}
    .pagin .paginList li{list-style: none;}
    .pagin .paginList .paginItem{float:left;}
    .pagin .paginList .paginItem a{float:left;width:31px;height:28px;border:1px solid #DDD; text-align:center;line-height:30px;border-left:none;color:#3399d5;}
    .pagin .paginList .paginItem:first-child a{border-left:1px solid #DDD;}
    .pagin .paginList .paginItem:first-child a{border-bottom-left-radius:5px;border-top-left-radius:5px;}
    .pagin .paginList .paginItem:last-child a{border-bottom-right-radius:5px;border-top-right-radius:5px;}
    .pagin .paginList .paginItem.current,.pagin .paginList .paginItem.current a{background:#f5f5f5; cursor:default;color:#737373;}
    .pagin .paginList .paginItem:hover{background:#f5f5f5;}
    .pagin .paginList .paginItem.more,.pagin .paginList .paginItem.more a:hover{ cursor:default;}
    .pagin .paginList .paginItem.more:hover{background:#FFF;}
    .pagin .paginList .paginItem.more a{color:#737373;}
    .pagepre{ width:41px; height:28px;}
    .pagenxt{ width:41px; height:28px;}
</style>

<div class="pagin" style="margin-top:15px" ng-if="ngPageHelperData.total">
    <div class="message">
        共<i class="blue">{{ngPageHelperData.total}}</i>条记录，当前显示第&nbsp;<i class="blue">{{ngPageHelperData.pageNum}}&nbsp;</i>页
    </div>
    <ul class="paginList">
        <li class="paginItem" ng-if="!ngPageHelperData.isFirstPage">
            <a href="javascript:void(0)" ng-click="ngPageHelperGoto(1)"><span class="pagepre">首页</span></a>
        </li>
        <li class="paginItem" ng-if="ngPageHelperData.prePage">
            <a href="javascript:void(0)" ng-click="ngPageHelperGoto(ngPageHelperData.prePage)">
                <span class="pagepre"> < </span>
            </a>
        </li>
        <li class="paginItem" ng-repeat="x in ngPageHelperData.navigatepageNums">
            <a href="javascript:void(0)" ng-click="ngPageHelperGoto(x)">{{x}}</a>
        </li>
        <li class="paginItem">
            <a href="javascript:void(0)" ng-click="ngPageHelperGoto(ngPageHelperData.nextPage)">
                <span class="pagenxt">></span>
            </a>
        </li>
        <li class="paginItem">
            <a href="javascript:void(0)" ng-click="ngPageHelperGoto(ngPageHelperData.pages)">
                <span class="pagenxt">尾页</span>
            </a>
        </li>
    </ul>
</div>
<div class="pagin" style="margin-top:15px" ng-if="!ngPageHelperData.total">
    没有找到数据
</div>