window.ListUtils = {
    /**
     * 从 srcList 移动选项到 dstList
     * @param itemName 要判断的项名称
     * @param value 要判断的值
     */
    move: function (srcList, dstList, itemName, value) {
        for (var i in srcList) {
            if (value == srcList[i][itemName]) {
                dstList.push(srcList[i]);
                srcList.splice(i, 1);
                break;
            }
        }
    },
     /**
     * 将 srcList 中的所有移动到 dstList
     */
    moveAll: function (srcList, dstList) {
        for (var i in srcList) {
            dstList.push(srcList[i]);
        }
        srcList.splice(0, srcList.length);
    },
    /**
     * 去重, 若值在两个列表中都存在, 则从 delList 中删除
     * @param itemName 要判断的项名称
     */
    removeDuplicates: function (srcList, delList, itemName) {
        for (var i in srcList) {
            this.remove(delList, itemName, srcList[i][itemName]);
        }
    },
    /**
     * 从列表中删除指定项
     * @param list 要操作的列表
     * @param itemName 要判断的项名称
     * @param value 要判断的值
     */
    remove: function (list, itemName, value) {
        var len = list.length;
        for (var i=0; i!=len; ) {
            if (!list[i]) {
                break;
            }

            if (list[i][itemName] == value) {
                list.splice(i, 1);
            } else {
                ++i;
            }
        }
    },
    /**
     * 获取指定项第一个匹配的索引
     * @param list 要查找的列表
     * @param itemName 要判断的项名称
     * @param value 要判断的值
     * @returns 对应值的索引; -1=未找到
     */
    getIndex: function (list, itemName, value) {
        var i = 0;
        var len = list.length;
        for (; i!=len; ++i) {
            if (list[i][itemName] == value) {
                return i;
            }
        }

        return -1;
    }
};