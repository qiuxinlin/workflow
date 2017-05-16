/**
 * Created by RanYu Zhao on 2017/4/11.
 */
(function(jQuery) {
    jQuery.fn.extend({
        datagrid: function(option, callbacks) {
            var vthis = jQuery(this);
            var defaults = {
                maxHeight : 'auto',
                coldefaultwidth : "80",
                checkboxcolwidth : "60",
                // colMaxWidth : "150",
                // griddata : {},
                mutiselect : false,
                controlbarwidth : "120",
                editable : false,
                pagetotal : 0,
                pagesize : 10,
                currentpage : 0
            };
            var options = jQuery.extend(defaults, option);
            var pagesize = options.pagesize;
            var currentpage = options.currentpage

            var gridTitles = new Array();
            var gridContents = new Array();

            // console.log('parent container margin-----' + $(vthis).css('margin').replace('px', ''));
            var parentContainerWidth = $(vthis).width()/* - $(vthis).css('margin').replace('px', '') * 2*/;
            // console.log('parentContainerWidth----------' + parentContainerWidth);
            // var parentContainerHeight = $(vthis).height();
            var parentContainerHeight = $(vthis).css('max-height').replace('px', '');
            // console.log('parentContainerHeight--------' + parentContainerHeight);
            // 当前列宽
            var currentColWidth = 0;
            var griddata = options.griddata;
            var theadData,tbodyData;

            // 初始化数据
            var initTitleData = function (data) {
                options.mutiselect = data.mutiselect;
                options.editable = data.editable;
                theadData = data.thead;
                var titleData, contentData;
                // console.log(options.mutiselect);
                if(options.mutiselect == true) {
                    // 多选列
                    var allSelect = '<input type="checkbox" />&nbsp;全选';
                    titleData = jQuery.extend(true, {}, {'theadname':'mutiselect', 'text':allSelect, 'display':true, 'dummycol' : true});
                    titleData.isfrost = true;
                    titleData.colwidth = options.checkboxcolwidth;
                    gridTitles.push(titleData);
                }
                // console.log('theadData---------' + theadData);
                $.each(theadData, function (i, obj) {
                    titleData = jQuery.extend(true, {}, obj);
                    titleData.isfrost = false;
                    titleData.colwidth = options.coldefaultwidth;
                    titleData.dummycol = false;
                    gridTitles.push(titleData);
                });
            }

            var initFlag = true;
            // 初始化表格框架
            var buildDataGridWrap = function () {
                var vthisWidth = $(vthis).width();
                var searchWrap = jQuery('');
                var dataGridWrap = jQuery('<div class="datagrid-wrap" editable="' + options.editable + '"></div>');
                dataGridWrap.appendTo(vthis);
                var paginationWrap = jQuery('<div id="Pagination" class="pagination"></div>');
                paginationWrap.appendTo(vthis);
                var tip = jQuery('<div id="tipDiv" class="datagrid-tip"></div>');
                tip.appendTo(vthis);

                $('.datagrid-container').on('click', '.hidden-col', function () {
                    // console.log('enter 76------------');
                    if($('.datagrid-wrap').find('.editing').length > 0) {
                        showDataGridTip('<span>请先保存/取消编辑的行数据</span>');
                        return false;
                    }
                });
                // 显示/隐藏列
                $('.datagrid-container').on('click', '#showHideCol ul li', function () {
                    // console.log('enter 77------------');
                    if($('.datagrid-wrap').find('.editing').length > 0) {
                        showDataGridTip('<span>请先保存/取消编辑的行数据</span>');
                        return false;
                    }
                    // console.log('enter 57--------------');
                    var liObj = $(this);
                    if( $(this).find('i.col-selected').length > 0) {
                        // $('div[name="' + $(this).attr('targetcol') + '"]').css({'display' : 'none'});
                        $.each(gridTitles, function (i, obj) {
                            if(obj.theadname == $(liObj).attr('targetcol')) {
                                obj.display = false;
                            }
                        });
                        $(this).find('i.col-selected').remove();
                    } else {
                        // $('div[name="' + $(this).attr('targetcol') + '"]').css({'display' : 'inline-block'});
                        $.each(gridTitles, function (i, obj) {
                            if(obj.theadname == $(liObj).attr('targetcol')) {
                                obj.display = true;
                            }
                        });
                        $(this).find('a').append('<i class="fa fa-check col-selected" aria-hidden="true"></i>');
                    }
                    rewriteDataGrid();
                    styleGrid();
                });

                // 取消冻结窗口
                $('.datagrid-container').on('click', '.cancel-frost-li', function (event) {
                    if($('.datagrid-wrap').find('.editing').length > 0) {
                        showDataGridTip('<span>请先保存/取消编辑的行数据</span>');
                        return false;
                    }
                    var targetCol = $(this).attr('targetcol');
                    // console.log('target col------' + targetCol);
                    var frostedAll = $('#frostTheadWrap').find('div.td-wrap');
                    // console.log('frostedAll length----------' + frostedAll.length);
                    $.each(gridTitles, function (i, objI) {
                        $.each(frostedAll, function (j, objJ) {
                            if(objI.theadname == $(objJ).attr('name')) {
                                objI.isfrost = false;
                            }
                        });
                    });
                    $.each(gridContents, function (i, objI) {
                        $.each(frostedAll, function (j, objJ) {
                            if(objI.theadname == $(objJ).attr('name')) {
                                objI.isfrost = false;
                            }
                        });
                    });
                    rewriteDataGrid();
                    event.stopImmediatePropagation();
                }) ;

                // 冻结窗口
                $('.datagrid-container').on('click', '.frost-li', function () {
                    if($('.datagrid-wrap').find('.editing').length > 0) {
                        showDataGridTip('<span>请先保存/取消编辑的行数据</span>');
                        return false;
                    }
                    // console.log('enter frost window--------');
                    var targetCol = $(this).attr('targetcol');
                    var prevAll = $('#activeTheadWrap').find('div[name="' + targetCol + '"]').prevAll();
                    // prevAll.push($('#activeTheadWrap').find('div[name="' + targetCol + '"]').attr('name'));
                    // console.log('prevAllTitles length----------' + prevAll.length);
                    $.each(gridTitles, function (i, objI) {
                        $.each(prevAll, function (j, objJ) {
                            // console.log('objI.theadname-----' + objI.theadname);
                            // console.log('objJ-----' + $(objJ).attr("name"));
                            if(objI.theadname == $(objJ).attr("name")) {
                                objI.isfrost = true;
                            }
                        });
                    });
                    $.each(gridContents, function (i, objI) {
                        $.each(prevAll, function (j, objJ) {
                            if(objI.theadname == $(objJ).attr("name")) {
                                objI.isfrost = true;
                            }
                        });
                    });
                    rewriteDataGrid();
                }) ;

                // 保存编辑行
                $('.datagrid-container').on('click', '.save-edit-row', function () {
                    // console.log('enter save edit row');
                    var rowIndex = $(this).parents('tr').index();
                    // console.log('rowIndex----------' + rowIndex);
                    var editDataJson = {};
                    // console.log('table length-------' + $('.body-wrap .table').length);
                    $.each($('.body-wrap .table'), function (i, table) {
                        // console.log('count----' + i);
                        var detailData = {};
                        var td = $(table).find('tr').eq(rowIndex).find('td');
                        if($(td).find('input').not(':checkbox').length > 0) {
                            // console.log('enter input get value------');
                            var input = $(td).find('input');
                            detailData['value'] = $(input).val();
                            detailData['text'] = $(input).val();
                            editDataJson[$(input).attr('name')] = detailData;
                        }
                        if($(td).find('select').length > 0) {
                            // console.log('enter get select value---------');
                            var select = $(td).find('select');
                            detailData['value'] = $(td).find('select option:selected').val();
                            detailData['text'] = $(td).find('select option:selected').text();
                            editDataJson[$(select).attr('name')] = detailData;
                        }
                    });
                    //console.log('------------' + $('#controlTbodyContainer .table').find('tr').eq(rowIndex).find('td').find('input').length);
                    var input = $('#controlTbodyContainer .table').find('tr').eq(rowIndex).find('td').find('input');
                    editDataJson[$(input).attr('name')] = $(input).val();
                    // console.log('editDataJson----' + JSON.stringify(editDataJson));
                    $('.datagrid-wrap').removeClass('editing');
                    var editsave = callbacks.editsave;
                    editsave(rowIndex, editDataJson, callbackEditSave);
                });

                // 取消行编辑
                $('.datagrid-container').on('click', '.cancel-edit-row', function () {
                    $('.datagrid-wrap').removeClass('editing');
                    rewriteDataGrid();
                });

                // 拉宽列
                $('.datagrid-container').on('mousedown', '.pull-col-icon', function (e) {
                    if($('.datagrid-wrap').find('.editing').length > 0) {
                        showDataGridTip('<span>请先保存/取消编辑的行数据</span>');
                        return false;
                    }
                    // console.log('mousedown---------------');
                    var pullObj = $(this);
                    $(pullObj).addClass('pulling');
                    var colWidth =  $(pullObj).parents('.td-wrap').width();
                    // console.log('colWidth---------' + colWidth);
                    var activeAreaWidth = $('#activeArea').width();
                    var containerWidth = $('#activeTheadContainer').width();
                    // console.log('containerWidth-----' + containerWidth);
                    var mousedownX = e.clientX;
                    // console.log('mousedownX---------' + mousedownX);
                    $(pullObj).unbind('mousemove').bind('mousemove', function (emove) {
                        if($(pullObj).hasClass('pulling')) {
                            var mousemoveX = emove.clientX;
                            // console.log('mousemoveX--------' + mousemoveX);
                            var moveWidth = mousemoveX - mousedownX;
                            var movedWidth = colWidth + moveWidth;
                            $(pullObj).parents('.td-wrap').css({
                                'width' : movedWidth + 'px',
                                'min-width' : movedWidth + 'px'
                            });
                            var colName = $(pullObj).parents('.td-wrap').attr('name');
                            $('.body-wrap').find('div[name="' + colName + '"]').css({
                                'width' : movedWidth + 'px',
                                'min-width' : movedWidth + 'px'
                            });
                            if($(pullObj).parents('.head-wrap').hasClass('active-head')) {
                                $('#activeTheadContainer').css({
                                    'width' : containerWidth + moveWidth + 'px'
                                });
                                $('#activeTbodyContainer').css({
                                    'width' : containerWidth + moveWidth + 'px'
                                });
                                $('.mCSB_container').css({'width' : containerWidth + moveWidth + 'px'});
                            } else {
                                $('#activeArea').css({
                                    'width' : activeAreaWidth - moveWidth + 'px'
                                });
                                $('#activeTheadWrap').css({
                                    'width' : activeAreaWidth - moveWidth + 'px'
                                });
                                $('#activeTbodyWrap').css({
                                    'width' : activeAreaWidth - moveWidth + 'px'
                                });
                            }
                        }
                    });
                    return false;
                });
                $('.datagrid-container').on('mouseup', '.pull-col-icon', function (e) {
                    if($('.datagrid-wrap').find('.editing').length > 0) {
                        showDataGridTip('<span>请先保存/取消编辑的行数据</span>');
                        return false;
                    }
                    // console.log('mouseup-----------------');
                    $(this).removeClass('pulling');
                    $.each(gridTitles, function (i, obj) {
                        var colName = obj.theadname;
                        // console.log('width-------' + $('.head-wrap').find('div[name="' + colName +'"]').css('width').replace('px', ''));
                        obj.colwidth = $('.head-wrap').find('div[name="' + colName +'"]').css('width').replace('px', '');
                    });
                    // console.log('gridTitles-----------' + JSON.stringify(gridTitles));
                    return false;
                });

            }
            var buildDataGrid = function() {
                var dataGridWrap = $('.datagrid-wrap');
                // 冻结区域结构
                var frostArea = jQuery('<div id="frostArea"></div>');
                frostArea.appendTo(dataGridWrap);
                var frostTheadWrap = jQuery('<div id="frostTheadWrap" class="head-wrap frost-head clearfix"></div>');
                frostTheadWrap.appendTo(frostArea);
                var frostTbodyWrap = jQuery('<div id="frostTbodyWrap" class="body-wrap clearfix"></div>');
                frostTbodyWrap.appendTo(frostArea);
                var frostTbodyContainer = jQuery('<div id="frostTbodyContainer" class="clearfix"></div>');
                frostTbodyContainer.appendTo(frostTbodyWrap);
                // 活动区域结构
                var activeArea = jQuery('<div id="activeArea"></div>');
                activeArea.appendTo(dataGridWrap);
                var activeTheadWrap = jQuery('<div id="activeTheadWrap" class="head-wrap active-head clearfix"></div>');
                activeTheadWrap.appendTo(activeArea);
                var activeTheadContainer = jQuery('<div id="activeTheadContainer" class="clearfix"></div>');
                activeTheadContainer.appendTo(activeTheadWrap);
                var activeTbodyWrap = jQuery('<div id="activeTbodyWrap" class="body-wrap clearfix"></div>');
                activeTbodyWrap.appendTo(activeArea);
                var activeTbodyContainer = jQuery('<div id="activeTbodyContainer" class="clearfix"></div>');
                activeTbodyContainer.appendTo(activeTbodyWrap);
                // 操作区域结构
                var rightBarWrap = jQuery('<div id="rightBarWrap"></div>');
                rightBarWrap.appendTo(dataGridWrap);
                var controlBtnIcon = jQuery('<div class="dropdown hidden-col"><a data-toggle="dropdown"><i class="fa fa-bars control-bar"></i></a><ul class="dropdown-menu dropdown-menu-right multi-level"></ul></div>');
                controlBtnIcon.appendTo(rightBarWrap);
                var controlItem = jQuery('<li id="showHideCol" class="dropdown-submenu"><a>显示/隐藏列</a><ul class="dropdown-menu dropdown-menu-right pull-left" style="overflow: auto;"></ul></li><li id="frostCol"><a></a></li>');
                controlItem.appendTo($(controlBtnIcon).find('ul'));

                var controlBarWrap = jQuery('<div id="controlBarWrap"></div>');
                controlBarWrap.appendTo(dataGridWrap);
                var controlBarTheadWrap = jQuery('<div id="controlTheadWrap" class="control-head clearfix"></div>');
                controlBarTheadWrap.appendTo(controlBarWrap);
                var controlBarTbody = jQuery('<div id="controlTbodyWrap" class="control-body clearfix"></div>');
                controlBarTbody.appendTo(controlBarWrap);
                var controlTbodyContainer = jQuery('<div id="controlTbodyContainer" class="clearfix"></div>');
                controlTbodyContainer.appendTo(controlBarTbody);

                var bottomBarWrap = jQuery('<div id="bottomBarWrap"></div>');
                bottomBarWrap.appendTo(dataGridWrap);

                // if(initFlag) {
                    // console.log('pagination-------------');
                    $("#Pagination").pagination(options.pagetotal,{
                        items_per_page : pagesize,
                        current_page : options.currentpage,
                        num_edge_entries : 1,
                        num_display_entries : 8,
                        callback : paginationCallback,//回调函数
                        callbackpagesize : paginationChangePageSizeCallback
                    });
                //     initFlag = false;
                // }

            }

            var gridContainer = $('.datagrid-wrap');
            var paginationCallback = function (index, gridContainer) {
                // console.log('index-------' + index);
                // console.log('enter 397---------');
                var getGridData = callbacks.getgriddata;
                options.currentpage = index;
                getGridData(index, pagesize, paginationRewritDataGrid);
            }

            var paginationChangePageSizeCallback = function (newPageSize) {
                // console.log('newPageSize-------' + newPageSize);
                pagesize = newPageSize;
                paginationCallback(options.currentpage, gridContainer);
            }

            var paginationRewritDataGrid = function (paginationData) {
                // console.log('paginationRewritDataGrid---------' + JSON.stringify(paginationData));
                gridContents = new Array();
                var tbodyData = paginationData.tbody;
                // console.log('tbodyData length-----------' + tbodyData.length);
                $.each(tbodyData, function (i, obj) {
                    var contentData = jQuery.extend(true, {}, obj);
                    if(options.mutiselect == true) {
                        contentData.mutiselect = {'value' : '', 'text' : '<input type="checkbox" />'};
                    }
                    // contentData.isfrost = false;
                    // contentData.colwidth = options.coldefaultwidth;
                    gridContents.push(contentData);
                });
                options.pagetotal = paginationData.pagetotal;
                options.pagesize = paginationData.pagesize;
                rewriteDataGrid();
            }

            // 行编辑保存成功回调函数
            var callbackEditSave = function (rowIndex, rowData) {
                // console.log('callbackEditSave-----------' + JSON.stringify(rowData));
                var oldRowData = gridContents[rowIndex];
                // console.log('oldRowData-----------' + JSON.stringify(oldRowData));
                for(var key in rowData) {
                    oldRowData[key].value = rowData[key].value;
                    oldRowData[key].text = rowData[key].text;
                }
                // console.log('newRowData-----------' + JSON.stringify(oldRowData));
                rewriteDataGrid();
            }

            // 排序回调函数
            var callbackSortGrid = function (sortCol, sortType, data) {
                gridContents = new Array();
                $.each(data, function (i, obj) {
                    contentData = jQuery.extend(true, {}, obj);
                    if(options.mutiselect == true) {
                        contentData.mutiselect = {'value' : '', 'text' : '<input type="checkbox" />'};
                    }
                    // contentData.isfrost = false;
                    // contentData.colwidth = options.coldefaultwidth;
                    gridContents.push(contentData);
                });
                // console.log('gridTitles-----------' + JSON.stringify(gridTitles));
                rewriteDataGrid();
                $('.head-wrap').find('div[name="' + sortCol + '"]').find('.sort-' + sortType + '-icon').css({'color' : '#000000'});
            }

            // 对冻结区域thead赋值
            var valuationFrostAreaThead = function (theadData) {
                var displayDiv = theadData.display == true ? 'display:inline-block;' : 'display:none;';
                var editabel = options.editable && theadData.editable ? true : false;
                var theadDiv = jQuery('<div class="td-wrap" name="' + theadData.theadname + '" editable="' + editabel + '" dummycol="' + theadData.dummycol + '" style="' + displayDiv + 'width: ' + theadData.colwidth + 'px;"></div>');
                theadDiv.appendTo($('#frostTheadWrap'));
                if(theadData.editable == true) {
                    $(theadDiv).attr('edittype', theadData.edittype);
                }
                if(typeof theadData.dataurl != "undefined") {
                    $(theadDiv).attr('dataurl', theadData.dataurl);
                }
                var tableThead = jQuery('<div class="table-thead-wrap"></div>');
                tableThead.appendTo(theadDiv);
                var tableInThead = jQuery('<table class="table" style="table-layout: fixed;"></table>');
                tableInThead.appendTo(tableThead);
                var thead = jQuery('<thead></thead>');
                thead.appendTo(tableInThead);
                var th = jQuery('<th>' + theadData.text  + '</th>');
                th.appendTo(thead);
                if(theadData.sortable == true) {
                    var sortupIcon = jQuery('<i class="fa fa-caret-up sort-icon sort-asc-icon" aria-hidden="true"></i>');
                    sortupIcon.appendTo(th);
                    var sortdownIcon = jQuery('<i class="fa fa-caret-down sort-icon sort-desc-icon" aria-hidden="true"></i>');
                    sortdownIcon.appendTo(th);
                }
                var pullColIcon = jQuery('<div class="pull-col-icon"></div>');
                pullColIcon.appendTo(th);
                var tbodyDiv = jQuery('<div class="td-wrap" name="' + theadData.theadname + '"  style="' + displayDiv + 'width: ' + theadData.colwidth + 'px;"></div>');
                tbodyDiv.appendTo($('#frostTbodyContainer'));
                var tableTbody = jQuery('<div class="table-tbody-wrap"></div>');
                tableTbody.appendTo(tbodyDiv);
                var tableInTbody = jQuery('<table class="table" style="table-layout: fixed;"></table>');
                tableInTbody.appendTo(tableTbody);
                var tbody = jQuery('<tbody></tbody>');
                tbody.appendTo(tableInTbody);

                // 隐藏列操作中添加列元素
                valuationShowHideCol(theadData);

            }

            // 对活动区域thead赋值
            var valuationActiveAreaThead = function (theadData) {
                var displayDiv = theadData.display == true ? 'display:inline-block;' : 'display:none;';
                var editabel = options.editable && theadData.editable ? true : false;
                var theadDiv = jQuery('<div class="td-wrap" name="' + theadData.theadname + '" editable="' + editabel + '" dummycol="' + theadData.dummycol + '" style="' + displayDiv + 'width: ' + theadData.colwidth + 'px;"></div>');
                theadDiv.appendTo($('#activeTheadContainer'));
                if(theadData.editable == true) {
                    $(theadDiv).attr('edittype', theadData.edittype);
                }
                if(typeof theadData.dataurl != "undefined") {
                    $(theadDiv).attr('dataurl', theadData.dataurl);
                }
                var tableThead = jQuery('<div class="table-thead-wrap"></div>');
                tableThead.appendTo(theadDiv);
                var tableInThead = jQuery('<table class="table" style="table-layout: fixed;"></table>');
                tableInThead.appendTo(tableThead);
                var thead = jQuery('<thead></thead>');
                thead.appendTo(tableInThead);
                var th = jQuery('<th>' + theadData.text + '</th>');
                th.appendTo(thead);
                if(theadData.sortable == true) {
                    var sortupIcon = jQuery('<i class="fa fa-caret-up sort-icon sort-asc-icon" aria-hidden="true"></i>');
                    sortupIcon.appendTo(th);
                    var sortdownIcon = jQuery('<i class="fa fa-caret-down sort-icon sort-desc-icon" aria-hidden="true"></i>');
                    sortdownIcon.appendTo(th);
                }
                var pullColIcon = jQuery('<div class="pull-col-icon"></div>');
                pullColIcon.appendTo(th);
                var tbodyDiv = jQuery('<div class="td-wrap" name="' + theadData.theadname + '"  style="' + displayDiv + 'width: ' + theadData.colwidth + 'px;"></div>');
                tbodyDiv.appendTo($('#activeTbodyContainer'));
                var tableTbody = jQuery('<div class="table-tbody-wrap"></div>');
                tableTbody.appendTo(tbodyDiv);
                var tableInTbody = jQuery('<table class="table" style="table-layout: fixed;"></table>');
                tableInTbody.appendTo(tableTbody);
                var tbody = jQuery('<tbody></tbody>');
                tbody.appendTo(tableInTbody);
                // countBodyContainerWidth($(theadDiv));

                // 隐藏列操作中添加列元素
                valuationShowHideCol(theadData);
            }
            
            // 对操作区域thead赋值
            var valuationControlBarThead = function () {
                var theadDiv = jQuery('<div class="td-wrap" style="width: ' + options.controlbarwidth + 'px;"></div>');
                theadDiv.appendTo($('#controlTheadWrap'));
                var tableThead = jQuery('<div class="table-thead-wrap"></div>');
                tableThead.appendTo(theadDiv);
                var tableInThead = jQuery('<table class="table" style="table-layout: fixed;"></table>');
                tableInThead.appendTo(tableThead);
                var thead = jQuery('<thead></thead>');
                thead.appendTo(tableInThead);
                var th = jQuery('<th>操作</th>');
                th.appendTo(thead);
                var tbodyDiv = jQuery('<div class="td-wrap"></div>');
                tbodyDiv.appendTo($('#controlTbodyContainer'));
                var tableTbody = jQuery('<div class="table-tbody-wrap"></div>');
                tableTbody.appendTo(tbodyDiv);
                var tableInTbody = jQuery('<table class="table" style="table-layout: fixed;"></table>');
                tableInTbody.appendTo(tableTbody);
                var tbody = jQuery('<tbody></tbody>');
                tbody.appendTo(tableInTbody);
            }

            // 显示/隐藏列
            var valuationShowHideCol = function (theadData) {
                // console.log('enter 241----------');
                var colSelected = '';
                if(theadData.display == true) {
                    colSelected = '<i class="fa fa-check col-selected" aria-hidden="true"></i>';
                }
                var li = jQuery('<li targetcol="' + theadData.theadname + '"><a href="#">' + theadData.text +  colSelected +'</a></li>');
                // console.log($('#showHideCol').find('ul').length);
                $('#showHideCol').find('ul').append(li);
            }


            // 对冻结/活动区域tbody赋值
            var valuationAreaTbody = function (index, tbodyData) {
                // console.log('valuationAreaTbody-------------');
                // 给操作列赋值
                var tagetTable = $('#controlTbodyContainer').find('.table-tbody-wrap').find('.table');
                var tbody = $(tagetTable).find('tbody');
                var controlRowContent = jQuery('<tr><td><span><a href="##">查看</a> | <a href="##">编辑</a> | <a href="##">删除</a></span><span style="display: none;"><a href="##" class="save-edit-row">保存</a> | <a href="##" class="cancel-edit-row">取消</a></span></td></tr>');
                controlRowContent.appendTo(tbody);

                for(var key in tbodyData) {
                    if($('.body-wrap').find('div[name="' + key + '"]').length > 0) {
                        var tagetTable = $('.body-wrap').find('div[name="' + key + '"]').find('.table-tbody-wrap').find('.table');
                        var tbody = $(tagetTable).find('tbody');
                        var rowContent = jQuery('<tr><td value="' + tbodyData[key].value + '"><span>' + tbodyData[key].text + '</span></td></tr>');
                        rowContent.appendTo(tbody);
                        if($('.head-wrap').find('div[name="' + key + '"]').attr('dummycol') == 'false') {
                            var editable = $('.head-wrap').find('div[name="' + key + '"]').attr('editable');
                            if(editable == 'true') {
                                var element;
                                var edittype = $('.head-wrap').find('div[name="' + key + '"]').attr('edittype');
                                if(edittype == 'select') {
                                    element = jQuery('<select name="' + key + '" id="' + key + index + '" class="selectpicker show-tick form-control" style="display: none;" value="' + tbodyData[key].value + '"></select>');
                                    element.appendTo($(rowContent).find('td'));
                                } else if(edittype == 'mutiselect') {
                                    element = jQuery('<select name="' + key + '" id="' + key + index + '" multiple class="selectpicker show-tick form-control" style="display: none;" value="' + tbodyData[key].value + '"></select>');
                                    element.appendTo($(rowContent).find('td'));
                                } else {
                                    element = jQuery('<input type="' + edittype + '" class="form-control" name="' + key + '" value="' + tbodyData[key].value +'" style="display: none;" />');
                                    element.appendTo($(rowContent).find('td'));
                                }
                            } else {
                                var hiddeninput = jQuery('<input type="hidden" id="' + key + '" value="' + tbodyData[key].value +'" name="' + key + '" />');
                                hiddeninput.appendTo($(rowContent).find('td'));
                            }
                        }
                    } else {
                        // console.log('enter 435-----------');
                        var hiddeninput = jQuery('<input type="hidden" id="' + key + '" value="' + tbodyData[key].value +'" name="' + key + '" />');
                        // console.log('controlRowContent td------' + $(controlRowContent).find('td').length);
                        hiddeninput.appendTo($(controlRowContent).find('td'));
                    }
                }

            }


            // 对表格初始化样式
            var styleGrid = function (parentContainerWidth) {
                $('.datagrid-wrap').css({
                    'min-width' : parentContainerWidth + 'px',
                    'max-width' : parentContainerWidth + 'px'
                });
                var bodyTdHeightTotal = 0;
                var headWrapHeight = 0;
                $.each($('.head-wrap .td-wrap'), function () {
                    if($(this).css('display') != 'none') {
                        // console.log('td width---------' + $(this).css('min-width').replace('px',''));
                        currentColWidth = Number($(this).css('width').replace('px',''));
                        tdWrapTotalWidth += currentColWidth;
                        tdDisplayLength++;
                    }
                    var thHeight = $('.table-thead-wrap table thead th').height();
                    // console.log('thHeight--------' + thHeight);
                    var padding = Number($('.table-thead-wrap table thead th').css('paddingTop').replace('px','')) + Number($('.table-thead-wrap table thead th').css('paddingBottom').replace('px',''));
                    // console.log('padding----------' + padding);
                    headWrapHeight = Number(thHeight) + Number(padding);
                    // console.log('headWrapHeight---------' + headWrapHeight);
                    $(this).height(headWrapHeight + 'px');
                    $(this).parent('.head-wrap').height(headWrapHeight + 'px');
                });
                // console.log('body-wrap-----------' + $('.body-wrap').length);
                $.each($('.body-wrap').eq($('.body-wrap').length - 1).find('.td-wrap').eq(0), function (i, obj) {
                    // console.log('height---------' + $(obj).height());
                    bodyTdHeightTotal += $(obj).height();
                });
                // console.log('bodyTdHeightTotal-----------' + bodyTdHeightTotal + headWrapHeight);
                // console.log(parentContainerHeight - $('#Pagination').height());
                if(parentContainerHeight - $('#Pagination').height() > bodyTdHeightTotal + headWrapHeight) {
                    $('.datagrid-wrap').height(bodyTdHeightTotal + headWrapHeight + 'px');
                } else {
                    $('.datagrid-wrap').height(parentContainerHeight - $('#Pagination').height() + 'px');
                }
                // $('.datagrid-wrap').height(parentContainerHeight - $('#Pagination').height() + 'px');
                if($('#frostArea').find('.td-wrap').length == 0) {
                    $('#frostArea').width('0px');
                }
                // console.log('editable--------' + options.editable);
                if(!options.editable) {
                    // console.log('enter 409--');
                    $('#controlBarWrap').css({
                        'display' : 'none',
                        'width' : 0
                    });
                }
                var tdWrapTotalWidth = 0;
                var tdDisplayLength = 0;
                // console.log($('.head-wrap .td-wrap').length);
                $('#controlTheadWrap').height(headWrapHeight + 'px');
                $('#controlTheadWrap').find('.td-wrap').height(headWrapHeight + 'px');
                // console.log('.head-wrap length---' + $('.head-wrap').length);
                // console.log('activeTheadWrap height--------' + $('#activeArea').find('.head-wrap').css('height'));
                $('#activeTbodyWrap').height($('.datagrid-wrap').height() - headWrapHeight + 'px');
                $('#rightBarWrap').find('#showHideCol').find('.dropdown-menu').height($('.datagrid-wrap').height() - $('.head-wrap').height() - 30 + 'px');
                // console.log('parentContainerWidth-----' + parentContainerWidth);
                // console.log('tdWrapTotalWidth-----' + tdWrapTotalWidth);
                var remainSpaceWidth = 0;
                var scrollRightBarWidth = $('#rightBarWrap').width();
                var controlBarWidth = $('#controlBarWrap').width();
                var frostAreaWidth, activeAreaWidth;
                if((parentContainerWidth - controlBarWidth - scrollRightBarWidth) > tdWrapTotalWidth) {
                    // console.log('enter 597----------');
                    // 重新分配列宽
                    remainSpaceWidth = parentContainerWidth - controlBarWidth - scrollRightBarWidth - tdWrapTotalWidth;
                    // console.log('remainSpaceWidth----' + remainSpaceWidth);
                    var eachIncreaseWidth = Math.floor(remainSpaceWidth / tdDisplayLength);
                    // console.log('eachIncreaseWidth----' + remainSpaceWidth / tdDisplayLength);
                    // console.log('eachIncreaseWidth floor----' + eachIncreaseWidth);
                    // console.log('enter 443---------');
                    $.each($('#frostArea .td-wrap, #activeArea .td-wrap'), function () {
                        if($(this).css('display') != 'none') {
                            // console.log('max width-----' + $(this).width() + eachWidth + 'px');
                            // $(this).width(Number($(this).css('width').replace('px','')) + eachIncreaseWidth + 'px');
                            $(this).css({
                                'width' : Number($(this).css('width').replace('px','')) + eachIncreaseWidth + 'px'
                            });
                        }
                    });
                } else if((parentContainerWidth - controlBarWidth - scrollRightBarWidth) == tdWrapTotalWidth) {
                    // console.log('enter 615---------');
                    var eachWidth = parentContainerWidth / tdDisplayLength;
                    // console.log('eachWidth--------' + eachWidth);
                    // console.log('enter 462---------');
                    $.each($('#frostArea .td-wrap, #activeArea .td-wrap'), function () {
                        if($(this).css('display') != 'none') {
                            // console.log('max width-----' + $(this).width() + eachWidth + 'px');
                            $(this).width(eachWidth - 1 + 'px');
                        }
                    });
                }
                frostAreaWidth = $('#frostTheadWrap').width();
                // console.log('frostAreaWidth-------' + frostAreaWidth);
                activeAreaWidth = parentContainerWidth - frostAreaWidth;
                // console.log('activeAreaWidth-------' + activeAreaWidth);
                $('#activeArea').css({'width' : activeAreaWidth - controlBarWidth - scrollRightBarWidth - 3 + 'px'});
                $('#activeTheadWrap').css({'width' : activeAreaWidth - controlBarWidth - scrollRightBarWidth - 3 + 'px'});
                $('#activeTbodyWrap').css({'width' : activeAreaWidth - controlBarWidth - scrollRightBarWidth - 3 + 'px'});
                var activeTbodyColLength =  0;
                var acriveTbodyColTotalWidth = 0;
                $.each($('#activeTbodyContainer').find('.td-wrap'), function () {
                    if($(this).css('display') != 'none') {
                        activeTbodyColLength++;
                        acriveTbodyColTotalWidth += $(this).width();
                    }
                });
                // console.log('head-wrap height---------' + $('.head-wrap').height());
                // console.log('table-tbody-wrap-------' + $('.table-tbody-wrap').eq(0).height());
                // console.log('bottomBarWrap height-------' + $('#bottomBarWrap').height());
                // console.log('activeTbodyColLength------' + activeTbodyColLength);
                // console.log('activeTbodyContainer width---------' + activeTbodyColLength * options.coldefaultwidth);
                $('#activeTheadContainer').css({'width' : acriveTbodyColTotalWidth + 'px'});
                // console.log('activeTheadContainer width------' + $('#activeTheadContainer').width());
                $('#activeTbodyContainer').css({'width' : acriveTbodyColTotalWidth + 'px'});
                // 绑定滚动条插件mCustomScrollbar
                $("#activeTbodyWrap").mCustomScrollbar({
                    theme: 'inset-2-dark',
                    scrollbarPosition: 'outside',
                    axis: 'yx',
                    mouseWheel: true,
                    scrollButtons:{
                        scrollType:'continuous',
                        enable:true,
                        scrollSpeed:10,
                        scrollAmount:20
                    },
                    callbacks: {
                        whileScrolling: function() {
                            scroll();
                        }
                    }
                });
                // 右侧滚动条设置样式
                if($('.mCSB_scrollTools_vertical').length > 0) {
                    // console.log($('#controlBarWrap').outerWidth());
                    // console.log($('#rightBarWrap').outerWidth());
                    $('.mCSB_outside+.mCSB_scrollTools').css({
                        'right' : '-' + ($('#controlBarWrap').outerWidth() + $('#rightBarWrap').outerWidth() + 'px')
                    });
                }
            };
            
            // 计算body container width
            var countBodyContainerWidth = function (obj, tdWidth) {
                if($(obj).css('display') != 'none') {
                    if ($(obj).parents('.head-wrap').hasClass('active-head')) {
                        var width = $('#activeTheadContainer').width();
                        console.log('width--------' + width);
                        $('#activeTheadContainer').css({'width' : width + tdWidth + 'px'});
                        $('#activeTbodyContainer').css({'width' : width + tdWidth + 'px'});
                    } else {

                    }
                }
            } 

            // mCustomScrollbar滚动回调事件
            var scroll = function() {
                // console.log('enter scroll------');
                // var scrollLeftWidth = $('.grid-content-wrap-active').scrollLeft();
                var scrollLeftWidth = $('.mCSB_container').css('left');
                // console.log('scrollLeftWidth------' + $('.mCSB_container').css('left'));
                $('#activeTheadContainer').css('marginLeft', scrollLeftWidth);
                var scrollTopHeight = $('.mCSB_container').css('top');
                // console.log('scroll top-----------' + scrollTopHeight);
                $('#frostTbodyContainer').css('marginTop', scrollTopHeight);
                $('#controlTbodyContainer').css('marginTop', scrollTopHeight);
            }
            
            // 操作后重绘页面
            var rewriteDataGrid = function () {
                // console.log('enter rewriteDataGrid-------------');
                $('.datagrid-wrap').empty();
                buildDataGrid();
                $.each(gridTitles, function (i, obj) {
                    if(obj.isfrost == true) {
                        valuationFrostAreaThead(obj);
                    } else {
                        // console.log('td width-----' + obj.colwidth);
                        valuationActiveAreaThead(obj);
                    }
                });
                // 加载操作列
                valuationControlBarThead();
                // console.log('gridContents length----------' + gridContents.length);
                $.each(gridContents, function (i, obj) {
                    // console.log('i-----------' + i);
                    valuationAreaTbody(i, obj);
                });
                $('.body-wrap .table tr:odd, .control-body .table tr:odd').css({'background-color' : '#f7f7f7'});
                styleGrid(parentContainerWidth);

                // 选择列
                $('.head-wrap').find('.td-wrap').on('click', function (event) {
                    if($('.datagrid-wrap').find('.editing').length > 0) {
                        showDataGridTip('<span>请先保存/取消编辑的行数据</span>');
                        return false;
                    }
                    // console.log('enter you are click head title--------');
                    // console.log('click---------------');
                    $('.body-wrap').find('.table-tbody-wrap').css({
                        'background-color' : 'inherit'
                    });
                    $('.body-wrap').find('div[name="' + $(this).attr('name') + '"]').find('.table-tbody-wrap').css({
                        'background-color' : '#f7f7f7'
                    });
                    $('#frostCol').attr('targetcol', $(this).attr('name'));
                    $('#frostCol').removeClass('cancel-frost-li frost-li');
                    if($(this).parents('.head-wrap').hasClass('frost-head')) {
                        // console.log('text--------' + $('#frostCol').find('a').text());
                        $('#frostCol').find('a').empty().text('取消冻结窗口');
                        $('#frostCol').addClass('cancel-frost-li');
                    } else {
                        // console.log('text--------' + $('#frostCol').find('a').text());
                        $('#frostCol').find('a').empty().text('冻结窗口');
                        $('#frostCol').addClass('frost-li');
                    }
                    $('#frostCol').css({'display' : 'block'});
                    event.stopImmediatePropagation();
                    // event.stopPropagation();
                });

                // 全选
                $('.head-wrap').find('div[name="mutiselect"]').find('input[type="checkbox"]').on('click', function (event) {
                    if($('.datagrid-wrap').find('.editing').length > 0) {
                        showDataGridTip('<span>请先保存/取消编辑的行数据</span>');
                        return false;
                    }
                    // console.log('checked---------' + $(this).is(':checked'));
                    // console.log('all select----------');
                    if($(this).is(':checked')) {
                        $('.body-wrap div[name="mutiselect"] input[type="checkbox"]').prop("checked", true);
                    } else {
                        // console.log('checked false');
                        $('.body-wrap div[name="mutiselect"] input[type="checkbox"]').removeAttr("checked");
                    }
                    event.stopImmediatePropagation();
                    // event.stopPropagation();
                });

                // 绑定单选事件
                $('.body-wrap').find('div[name="mutiselect"]').find('input[type="checkbox"]').on('click', function (event) {
                    if($('.datagrid-wrap').find('.editing').length > 0) {
                        showDataGridTip('<span>请先保存/取消编辑的行数据</span>');
                        return false;
                    }
                    // console.log('enter 单选--------');
                    // console.log('checked---------' + $(this).is(':checked'));
                    if($(this).is(':checked')) {
                        $(this).prop("checked", true);
                        if($('.body-wrap').find('div[name="mutiselect"]').find('input[type="checkbox"]:checked').length == $('.body-wrap').find('div[name="mutiselect"]').find('input[type="checkbox"]').length) {
                            $('.head-wrap').find('div[name="mutiselect"]').find('input[type="checkbox"]').prop("checked", true);
                        }
                    } else {
                        $(this).removeAttr("checked");
                        $('.head-wrap').find('div[name="mutiselect"]').find('input[type="checkbox"]').removeAttr('checked');
                    }

                    event.stopImmediatePropagation();
                    event.stopPropagation();
                });

                // 绑定行编辑事件
                $('.body-wrap').find('.td-wrap').find('tr').on('click', function (event) {
                    $('.hidden-col').removeClass('open');
                    var index =  $(this).index() ;
                    // console.log('index------' + index);
                    if(!options.editable || $('.datagrid-wrap').find('.editing').length > 0) {
                        // console.log('editing------------' + $(this).hasClass('editing'));
                        if(!$(this).hasClass('editing')) {
                            $('.bootstrap-select').removeClass('open');
                        }
                        return false;
                    }
                    // console.log('editable----------' + options.editable);
                    // console.log('table length--------' + $('.body-wrap').find('.table').length);
                    $.each($('.body-wrap').find('.table'), function () {
                        $(this).find('tr').eq(index).addClass('editing');
                        var td = $(this).find('tr').eq(index).find('td').eq(0);
                        var tdWidth = $(td).outerWidth();
                        // console.log('td width--------' + tdWidth);
                        var tdHeight = $(td).outerHeight();
                        // console.log('td height--------' + tdHeight);
                        if($(td).find('input').not(':checkbox').length > 0) {
                            $(td).find('input').css({
                                'width' : tdWidth + 'px',
                                'height' : tdHeight + 'px',
                                'display' : 'inline-block'
                            });
                        }
                        if($(td).find('select').length > 0) {
                            $(td).find('select').css({
                                'width' : tdWidth + 'px',
                                'height' : tdHeight + 'px',
                                'display' : 'inline-block'
                            });
                            var colName = $(td).parents('.td-wrap').attr('name');
                            var dataurl = $('.head-wrap').find('div[name="' + colName + '"]').attr('dataurl');
                            // console.log('dataurl--------' + dataurl);
                            $(td).find('select').empty();
                            $.ajax({
                                method: "GET",
                                url: dataurl,
                                dataType: 'json',
                                success: function (data) {
                                    var options = data.options;
                                    for(var i = 0;i < options.length;i++) {
                                        if(options[i].optionvalue ==  $(td).find('select').attr('value')) {
                                            $(td).find('select').append('<option value="' + options[i].optionvalue + '" selected>' + options[i].optiontext + '</option>');
                                        } else {
                                            $(td).find('select').append('<option value="' + options[i].optionvalue + '">' + options[i].optiontext + '</option>');
                                        }
                                    }
                                    var edittype = $('.head-wrap').find('div[name="' + $(td).parents('.td-wrap').attr('name') + '"]').attr('edittype');
                                   // if(edittype == 'mutiselect') {
                                    $('#' + $(td).parents('.td-wrap').attr('name') + index).selectpicker();
                                       // $('#' + $(td).parents('.td-wrap').attr('name') + index).selectpicker('render');
                                    // console.log($('.bootstrap-select').find('.dropdown-menu').length);
                                    // var datagridWrapHeight = $('.datagrid-wrap').height();
                                    // console.log('datagridWrapHeight----------' + datagridWrapHeight);
                                    // var headWrapHeight = $('.head-wrap').height();
                                    // console.log('headWrapHeight----------' + headWrapHeight);
                                    // var dropdownMenuHeight = Number(datagridWrapHeight) - Number(headWrapHeight);
                                    // console.log('dropdownMenuHeight--------' + dropdownMenuHeight);
                                    // $.each($('.bootstrap-select').find('.dropdown-menu'), function () {
                                    //     $(this).css({
                                    //         'max-height' : Number(datagridWrapHeight) - Number(headWrapHeight) - 30 + 'px'
                                    //     });
                                    // });
                                }
                            });
                        }

                       if($('.head-wrap').find('div[name="' + $(td).parents('.td-wrap').attr('name') + '"]').attr('editable') == 'true') {
                           $(td).css({'padding' : 0});
                           $(td).find('span').css({'display' : 'none'});
                       }

                    });
                    $('#controlTbodyContainer').find('.table tr').eq(index).find('span').eq(0).css({'display' : 'none'});
                    $('#controlTbodyContainer').find('.table tr').eq(index).find('span').eq(1).css({'display' : 'block'});
                    event.stopImmediatePropagation();
                    event.stopPropagation();
                });

                // 高亮行
                /*$('.body-wrap').find('.td-wrap').find('tr').on('mouseover', function (event) {
                    var index =  $(this).index() ;
                    $.each($('.body-wrap').find('.table'), function () {
                        $(this).find('tr').eq(index).css({'background-color' : '#eef1f6'});
                    });
                    $.each($('.control-body').find('.table'), function () {
                        $(this).find('tr').eq(index).css({'background-color' : '#eef1f6'});
                    });
                });
                $('.body-wrap').find('.td-wrap').find('tr').on('mouseout', function() {
                    $('.body-wrap').find('.table').find('tr').css('background-color', '');
                    $('.control-body').find('.table').find('tr').css('background-color', '');
                });*/

                // 升序/降序排列
                $('.sort-icon').on('click', function (event) {
                    if($('.datagrid-wrap').find('.editing').length > 0) {
                        showDataGridTip('<span>请先保存/取消编辑的行数据</span>');
                        return false;
                    }
                    // console.log('enter sort--------');
                    var sortgrid = callbacks.sortgrid;
                    var sortType = 'asc';
                    if($(this).hasClass('sort-desc-icon')) {
                        sortType = 'desc';
                    }
                    var griddata = options.griddata;
                    sortgrid($(this).parents('.td-wrap').attr('name'), sortType, currentpage, pagesize, callbackSortGrid);
                    // $(this).css({'color' : '#000000'});
                    event.stopImmediatePropagation();
                });

                /*$('input').iCheck({
                    checkboxClass: 'icheckbox_minimal-grey',
                    radioClass: 'iradio_minimal-grey',
                    increaseArea: '5%' // optional
                });*/

            }

            var showDataGridTip = function (text) {
                $('.datagrid-tip').append(text);
                $('.datagrid-tip').css({
                    'display' : 'block',
                    'line-height' : $('.datagrid-container').height() + 'px'
                });
                setTimeout(function () {
                    $('.datagrid-tip').css('display', 'none');
                }, 5000);
            }

            var initDataGrid = function (data) {
                buildDataGridWrap();
                // 初始化数据
                // theadData = data.thead;
                initTitleData(data);
                paginationCallback(options.currentpage);
            }

            // initDataGrid();
            var init = callbacks.getgridtitle;
            init(initDataGrid);
        }
    });
})(jQuery);
