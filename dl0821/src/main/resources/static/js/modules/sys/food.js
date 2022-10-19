$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sysFood/list',
        datatype: "json",
        colModel: [
			{ label: 'ID', name: 'id', index: "id", width: 45, key: true },
			{ label: '租赁物品名称', name: 'foodname', index: "title", width: 150 },
			{ label: '分类', name: 'localcolor', width: 80,
				formatter: function(value, options, row){
					if(value === "1"){
						return '<span class="label label-success">日常用品</span>';
					}else if(value === "2"){
						return '<span class="label label-success">厨卫用品</span>';
					}else if(value === "3"){
						return '<span class="label label-success">家电用品</span>';
					}else if(value === "4"){
						return '<span class="label label-success">家具以及家居装饰用品</span>';
					}else{
						return '<span class="label label-success">其他类</span>';
					}
				}
			},
			{ label: '物品单价/天', name: 'jiage', index: "title", width: 150 },
			{ label: '租赁保证金', name: 'bzj', index: "title", width: 150 },
			{ label: '物品描述', name: 'describe', index: "title", width: 150 },
			{ label: '上架状态', name: 'issj', width: 80,
				formatter: function(value, options, row){
					if(value === "1"){
						return '<span class="label label-success">上架</span>';
					}else if(value === "2"){
						return '<span class="label label-success">下架</span>';
					}else{
						return '<span class="label label-success">未知</span>';
					}
				}
			},
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.records",
            page: "page.current",
            total: "page.pages",
            records: "page.total"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

//菜单树
var menu_ztree;
var menu_setting = {
	data: {
		simpleData: {
			enable: true,
			idKey: "menuId",
			pIdKey: "parentId",
			rootPId: -1
		},
		key: {
			url:"nourl"
		}
	},
	check:{
		enable:true,
		nocheckInherit:true
	}
};

//部门结构树
var dept_ztree;
var dept_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};

//数据树
var data_ztree;
var data_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    },
    check:{
        enable:true,
        nocheckInherit:true,
        chkboxType:{ "Y" : "", "N" : "" }
    }
};

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			foodName: null
		},
		showList: true,
		title:null,
		equipmentList:{},
		allFiles:[],

		importModle: true,
		showListuploadsave: true,
		showDelect: true,
		food:{
			id:'',
			qcmc:'',
			bh:'',
			username:'',
			createdate:'',
			enddate:'',
			equipmentIdList:[],
			equipmentId:'',
			title:'',
			content:'',
			author:'',
			deptName:'',
			deptid:'',
			files: [],

		}
	},
	methods: {
		deleteFile: function(id) {
			if(id == null) {
				alert("请选择要删除的文件!");
				return;
			}
			vm.deleteFles = {"id":id};
			confirm('确定要删除该记录？', function(){
				$.ajax({
					type: "POST",
					url: baseURL + "tMaterialFile/deleteByFileId",
					contentType: "application/json",
					data: JSON.stringify(vm.deleteFles),
					success: function(r){
						if(r.code == 0){
							alert('文件删除成功', function(){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		download: function (id) {
			console.log("id)id)id)id)" + id)
			$.get(baseURL + "tMaterialFile/ishSingleFile/" + id, function (r) {
				if (r.code == 0) {
					if (r.fileName != '无下载文件' && r.fileName != '文件不存在') {
						var url = baseURL + "tMaterialFile/downFile?id=" + id + "&token=" + token;
						window.location.href = url;
					} else {
						alert(r.fileName)
					}
				}
			});
		},
		saveFile: function () {
			var value = document.querySelectorAll('*[name="abc"]')
			$("#box").val(value);
			$("#myModalPreachData").modal('hide');
		},
		importFile: function () {
			if ($("#fileList").val() == null || $("#fileList").val() == "") {
				alert("请选择具体附件再上传!");
				return;
			}
			// if (vm.selected == "请选择") {
			// 	alert("请选择密级");
			// 	return;
			// }
			var form = document.getElementById('upload');
			$.ajax({
				url: baseURL + "tMaterialFile/importPsot",
				type: 'post',
				data: new FormData(form),
				processData: false,
				contentType: false,
				dataType: "json",
				success: function (r) {
					console.log(JSON.stringify(r))
					if (r.msg == 'false') {
						alert('您不具备上传该密级条件');
						return;
					}
					if (r.msg == 'false1') {
						alert('密标程序未启动');
						return;
					}
					var obj = new Object();
					$("#fileList").val("");
					obj['id'] = r.id;
					obj['filePath'] = r.path;
					obj['fileName'] = r.fileName;
					obj['mbfklj'] = r.mbfklj;
					vm.allFiles.push(obj);
					vm.food.files = vm.allFiles;
					alert("导入成功！");
				},
				error: function () {
					alert("导入失败！");
				}
			})
		},
		openPreachData: function () {
			$("#fileList").val("");
			$("#myModalPreachData").modal('show');
			vm.selected = "请选择";
		},
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.equipmentList = {};
			vm.title = "新增";
			vm.food = {deptName:null, deptId:null};
			vm.allFiles = [];
			vm.food.files = [];
			vm.getEquipmentList();
			vm.getDept();

		},
		getEquipmentList: function(){
			$.get(baseURL + "sysEquipment/select", function(r){
				vm.equipmentList = r.equipmentList;
			});
		},

		update: function () {
			var id = getSelectedRow();
			console.log("idididididid"+id)
			if(id == null){
				return ;
			}
			
			vm.showList = false;
            vm.title = "修改";
			vm.getDept();
			vm.getFood(id)
			vm.getEquipmentList();
		},
		sxj: function (issj) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			var param = {ids:ids,issj:issj}
			confirm('确定要上下架选中的记录？', function(){
				$.ajax({
					type: "POST",
					url: baseURL + "sysFood/sxj",
					contentType: "application/json",
					data: JSON.stringify(param),
					success: function(r){
						if(r.code == 0){
							alert('上下架成功', function(){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		del: function () {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sysFood/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getFood: function(foodId){
            $.get(baseURL + "sysFood/info/"+foodId, function(r){
            	vm.food = r.food;
				vm.allFiles = r.food.files;
    		});
		},
		savePlxx: function () {
			console.log("idididididididid"+vm.food.id)
			// var url = vm.food.id == null ? "sysFood/save" : "sysFood/update";
			$.ajax({
				type: "POST",
				url: baseURL + "sysFood/savePlxx",
				contentType: "application/json",
				data: JSON.stringify(vm.food),
				success: function(r){
					if(r.code === 0){
						alert('操作成功', function(){
							vm.reloadallFiles();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		saveOrUpdate: function () {
			var url = vm.food.id == null ? "sysFood/save" : "sysFood/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.food),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		getMenuTree: function(id) {
			//加载菜单树
			$.get(baseURL + "sys/menu/list", function(r){
				menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, r);
				//展开所有节点
				menu_ztree.expandAll(true);
				
				if(id != null){
					vm.getFood(id);
				}
			});
	    },
        getDataTree: function(id) {
            //加载菜单树
            $.get(baseURL + "sys/dept/list", function(r){
                data_ztree = $.fn.zTree.init($("#dataTree"), data_setting, r);
                //展开所有节点
                data_ztree.expandAll(true);
            });
        },
        getDept: function(){
            //加载部门树
            $.get(baseURL + "sys/dept/list", function(r){
                dept_ztree = $.fn.zTree.init($("#deptTree"), dept_setting, r);
                var node = dept_ztree.getNodeByParam("deptId", vm.food.deptid);
                if(node != null){
                    dept_ztree.selectNode(node);

                    vm.food.deptName = node.name;
                }
            })
        },
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = dept_ztree.getSelectedNodes();
                    //选择上级部门
                    vm.food.deptid = node[0].deptId;
                    vm.food.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
	    reload: function () {
	    	vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'foodName': vm.q.foodName},
                page:page
            }).trigger("reloadGrid");
		},
		reloadallFiles: function () {
			var id = getSelectedRow();
			console.log("ididisssssssssssssdididid"+id)
			$.get(baseURL + "sysFood/infoprent/"+id, function(r){
				console.log("dfsdfdsfddddddddddddddddddddddddd"+JSON.stringify(r))
				vm.food = r.food;
				vm.allFiles = r.allFiles;
			});
		},
		dygn: function () {
			// $("#jqGrid").printArea();
			// bdhtml=window.document.body.innerHTML;
			// sprnstr="<!--startprint-->";
			// eprnstr="<!--endprint-->";
			// prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
			// prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
			// window.document.body.innerHTML=prnhtml;
			// window.print();

			var id = getSelectedRow();
			console.log("idididididid"+id)
			if(id == null){
				return ;
			}
			vm.food.id = id;

			// vm.getFood(id)
			//
			$.get(baseURL + "sysFood/infoprent/"+id, function(r){
				console.log("dfsdfdsfddddddddddddddddddddddddd"+JSON.stringify(r))
				vm.food = r.food;
				vm.food.content = "";
				vm.allFiles = r.allFiles;
			});
			// vm.getEquipmentList();

			$("#myModalPreachData").modal('show');
		}
	}
});

laydate.render({
	elem: '#faultTime', //指定元素
	format:'yyyy-MM-dd HH:mm:ss',
	//日期时间选择器
	type: 'datetime',
	done: function(value, date, endDate){
		vm.food.createdate= value;
	}
});

laydate.render({
	elem: '#enddateTime', //指定元素
	format:'yyyy-MM-dd HH:mm:ss',
	//日期时间选择器
	type: 'datetime',
	done: function(value, date, endDate){
		vm.food.enddate= value;
	}
});