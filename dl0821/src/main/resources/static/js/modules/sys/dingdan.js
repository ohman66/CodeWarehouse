$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sysDingdan/list',
        datatype: "json",
        colModel: [
			{ label: 'ID', name: 'id', index: "id", width: 45, key: true },

			{ label: '租赁信息', name: 'name', index: "title", width: 150 },
			{ label: '物品金额', name: 'ddje', index: "title", width: 150 },
			{ label: '租赁开始时间', name: 'kssj', index: "title", width: 150 },
			{ label: '租赁结束时间', name: 'jssj', index: "title", width: 150 },
			{ label: '预定人员', name: 'ydry', index: "title", width: 150 },
			{ label: '使用状态', name: 'wlzt', index: "title", width: 150 },
			// { label: '收货状态', name: 'sfqrsh', index: "title", width: 150 },
			{ label: '物品状态', name: 'sfqrsh', width: 80,
				formatter: function(value, options, row){
					if(value === "1"){
						return '<span class="label label-success">确认还租</span>';
					}else if(value === "3"){
						return '<span class="label label-success">申请退货</span>';
					}else{
						return '<span class="label label-success">待收货</span>';
					}
				}
			},
			{ label: '下单金额', name: 'zongjiage', index: "title", width: 150 },
			{ label: '订购数量', name: 'sl', index: "title", width: 150 },
			{ label: '保证金退还申请', name: 'bzjthsq', width: 80,
				formatter: function(value, options, row){
					if(value === "1"){
						return '<span class="label label-success">退还</span>';
					}else if(value === "0"){
						return '<span class="label label-success">申请中</span>';
					}else if(value === "2"){
						return '<span class="label label-success">拒绝退换保证金</span>';
					}else{
						return '<span class="label label-success">未还租无申请记录</span>';
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
			dingdanName: null
		},
		showList: true,
		title:null,
		equipmentList:{},
		allFiles:[],

		importModle: true,
		showListuploadsave: true,
		showDelect: true,
		dingdan:{
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
			sqthzt:'',

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
			console.log("dingdandingdandingdandingdan"+JSON.stringify(vm.dingdan))
			$.ajax({
				type: "POST",
				url: baseURL + "sysDingdan/update",
				contentType: "application/json",
				data: JSON.stringify(vm.dingdan),
				success: function(r){
					if(r.code === 0){
						alert('操作成功', function(){
							vm.reload();
							$("#myModalPreachData").modal('hide');
						});
					}else{
						alert(r.msg);
					}
				}
			});

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
					vm.dingdan.files = vm.allFiles;
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
			vm.dingdan = {deptName:null, deptId:null};
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
			vm.getDingdan(id)
			vm.getEquipmentList();
		},
		qxsh: function () {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}

			confirm('确定取消在申请的记录？', function(){
				$.ajax({
					type: "POST",
					url: baseURL + "sysDingdan/qxsh",
					contentType: "application/json",
					data: JSON.stringify(ids),
					success: function(r){
						if(r.code == 0){
							alert('审批成功', function(){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		shzt: function () {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}

			confirm('确定要审批选中的记录？', function(){
				$.ajax({
					type: "POST",
					url: baseURL + "sysDingdan/shzt",
					contentType: "application/json",
					data: JSON.stringify(ids),
					success: function(r){
						if(r.code == 0){
							alert('审批成功', function(){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		bzjthsp: function (bzjthsqinfo) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			var param = {bzjthsq:bzjthsqinfo,ids:ids}
			confirm('确定要审批选中的记录？', function(){
				$.ajax({
					type: "POST",
					url: baseURL + "sysDingdan/bzjthsp",
					contentType: "application/json",
					data: JSON.stringify(param),
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
		del: function () {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sysDingdan/delete",
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
		getDingdan: function(dingdanId){
            $.get(baseURL + "sysDingdan/info/"+dingdanId, function(r){
            	vm.dingdan = r.dingdan;
				vm.allFiles = r.dingdan.files;
    		});
		},
		savePlxx: function () {
			console.log("idididididididid"+vm.dingdan.id)
			// var url = vm.dingdan.id == null ? "sysDingdan/save" : "sysDingdan/update";
			$.ajax({
				type: "POST",
				url: baseURL + "sysDingdan/savePlxx",
				contentType: "application/json",
				data: JSON.stringify(vm.dingdan),
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
			var url = vm.dingdan.id == null ? "sysDingdan/save" : "sysDingdan/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dingdan),
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
					vm.getDingdan(id);
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
                var node = dept_ztree.getNodeByParam("deptId", vm.dingdan.deptid);
                if(node != null){
                    dept_ztree.selectNode(node);

                    vm.dingdan.deptName = node.name;
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
                    vm.dingdan.deptid = node[0].deptId;
                    vm.dingdan.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
	    reload: function () {
	    	vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'dingdanName': vm.q.dingdanName},
                page:page
            }).trigger("reloadGrid");
		},
		reloadallFiles: function () {
			var id = getSelectedRow();
			console.log("ididisssssssssssssdididid"+id)
			$.get(baseURL + "sysDingdan/infoprent/"+id, function(r){
				console.log("dfsdfdsfddddddddddddddddddddddddd"+JSON.stringify(r))
				vm.dingdan = r.dingdan;
				vm.allFiles = r.allFiles;
			});
		},
		dygn: function () {
			var id = getSelectedRow();
			console.log("idididididid"+id)
			if(id == null){
				return ;
			}
			vm.dingdan.id = id;

			$.get(baseURL + "sysDingdan/info/"+id, function(r){
				console.log("dfsdfdsfddddddddddddddddddddddddd"+JSON.stringify(r))
				vm.dingdan = r.dingdan;
			});
			// vm.getEquipmentList();

			$("#myModalPreachData").modal('show');
		}
	}
});

laydate.render({
	elem: '#kssj', //指定元素
	format:'yyyy-MM-dd HH:mm:ss',
	//日期时间选择器
	type: 'datetime',
	done: function(value, date, endDate){
		vm.dingdan.kssj= value;
	}
});

laydate.render({
	elem: '#jssj', //指定元素
	format:'yyyy-MM-dd HH:mm:ss',
	//日期时间选择器
	type: 'datetime',
	done: function(value, date, endDate){
		vm.jssj.enddate= value;
	}
});