webpackJsonp([8],{"28rB":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=a("aA9S"),n=a.n(i),o=a("E4C3"),s=a.n(o),l=a("eOoE");var r={components:{},data:function(){return{isCheck:!1,query:{machineName:""},statusList:[{value:1,name:"已处理"},{value:2,name:"未处理"}],addEditDialogVisible:!1,dialogImageUrl:"",isEdit:!1,page:{pageNumber:1,pageSize:10,total:0},infoForm:{},rules:{content:[{required:!0,message:"请输入反馈内容",trigger:"blur"}]},tableData:[]}},created:function(){},mounted:function(){this.getList()},watch:{addEditDialogVisible:function(e,t){void 0!==this.$refs.infoForm&&this.$refs.infoForm.resetFields()}},computed:{headers:function(){return{xinyeSysUserSessionId:this.$store.status.token}}},methods:{handleSizeChange:function(e){this.page.pageSize=e,this.getList()},handleCurrentChange:function(e){this.page.pageNumber=e,this.getList()},formatDateJoinAt:function(e,t){return this.$moment(e.joinAt).format("YYYY-MM-DD")},formatDateNextPayAt:function(e,t){return this.$moment(e.nextPayAt).format("YYYY-MM-DD")},handleSearch:function(){this.getList()},handleEdit:function(e,t){this.isCheck=!1,this.isEdit=!0,this.infoForm=n()({},t),this.addEditDialogVisible=!0},handleCheck:function(e,t){this.isCheck=!0,this.isEdit=!1,this.infoForm=n()({},t),this.addEditDialogVisible=!0},handleSubmitUpdate:function(){var e=this;this.$refs.infoForm.validate(function(t){t&&e.$confirm("确认提交吗？","提示",{confirmButtonText:"确定",cancelButtonText:"取消"}).then(function(){s.a.start();var t,a=n()({},e.infoForm);(t=a,Object(l.a)({url:"/admin/set/feedback/update",method:"post",data:t})).then(function(t){s.a.done(),e.$message.success(t.data.msg),e.$refs.infoForm.resetFields(),e.addEditDialogVisible=!1,e.getList()})})})},getList:function(){var e=this;(function(e){return Object(l.a)({url:"/admin/set/feedback/list",method:"post",data:e})})({status:this.query.status,pageNumber:this.page.pageNumber,pageSize:this.page.pageSize}).then(function(t){e.tableData=t.data.page.list,console.log(e.tableData),e.page.pageNumber=t.data.page.pageNumber,e.page.pageSize=t.data.page.pageSize,e.page.total=t.data.page.totalRow}).catch(function(t){e.$message.error(t),console.log(t)})}}},u={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("el-form",{staticClass:"query-form",attrs:{inline:"",model:e.query,"label-position":"right","label-width":"70px"}},[a("el-select",{attrs:{placeholder:"请选择帮助类型"},model:{value:e.query.status,callback:function(t){e.$set(e.query,"status",t)},expression:"query.status"}},e._l(e.statusList,function(e){return a("el-option",{key:e.value,attrs:{label:e.name,value:e.value}})}),1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:e.handleSearch}},[e._v("搜索")])],1)],1),e._v(" "),a("el-table",{staticClass:"table",attrs:{data:e.tableData,stripe:"",border:""}},[a("el-table-column",{attrs:{type:"index",label:"编号",width:"70"}}),e._v(" "),a("el-table-column",{attrs:{prop:"content",label:"反馈内容","show-overflow-tooltip":!0}}),e._v(" "),a("el-table-column",{attrs:{prop:"createTime",label:"添加时间"}}),e._v(" "),a("el-table-column",{attrs:{prop:"sysUserName",label:"操作人"}}),e._v(" "),a("el-table-column",{attrs:{prop:"status",label:"处理状态"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.status?a("span",[e._v("已处理")]):e._e(),e._v(" "),2===t.row.status?a("span",[e._v("未处理")]):e._e()]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"操作",width:"230"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{size:"mini",type:"primary"},on:{click:function(a){return e.handleCheck(t.$index,t.row)}}},[e._v("查看")]),e._v(" "),a("el-button",{attrs:{size:"mini"},on:{click:function(a){return e.handleEdit(t.$index,t.row)}}},[e._v("编辑")])]}}])})],1),e._v(" "),a("el-pagination",{attrs:{"current-page":e.page.pageNumber,"page-sizes":[10,20,30,40],"page-size":e.page.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.page.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}}),e._v(" "),a("el-dialog",{attrs:{title:"新增/编辑/查看",visible:e.addEditDialogVisible,width:"800px"},on:{"update:visible":function(t){e.addEditDialogVisible=t}}},[a("el-form",{ref:"infoForm",attrs:{model:e.infoForm,rules:e.rules,"label-width":"150px"}},[a("el-form-item",{attrs:{prop:"content",label:"反馈内容："}},[a("el-input",{attrs:{disabled:e.isCheck,type:"textarea",autosize:"",placeholder:"请输入内容"},model:{value:e.infoForm.content,callback:function(t){e.$set(e.infoForm,"content",t)},expression:"infoForm.content"}})],1),e._v(" "),a("el-form-item",{attrs:{prop:"status",label:"帮助类型："}},[a("el-select",{attrs:{placeholder:"请选择帮助类型",disabled:e.isCheck},model:{value:e.infoForm.status,callback:function(t){e.$set(e.infoForm,"status",t)},expression:"infoForm.status"}},e._l(e.statusList,function(e){return a("el-option",{key:e.value,attrs:{label:e.name,value:e.value}})}),1)],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.addEditDialogVisible=!1}}},[e._v("关闭")]),e._v(" "),e.isEdit?a("el-button",{attrs:{type:"primary"},on:{click:e.handleSubmitUpdate}},[e._v("更 新")]):e._e()],1)],1)],1)},staticRenderFns:[]};var c=a("C7Lr")(r,u,!1,function(e){a("2no7")},null,null);t.default=c.exports},"2no7":function(e,t,a){var i=a("zREj");"string"==typeof i&&(i=[[e.i,i,""]]),i.locals&&(e.exports=i.locals);a("FIqI")("b89ecd7a",i,!0,{})},zREj:function(e,t,a){(e.exports=a("UTlt")(!0)).push([e.i,"\n.school-item {\r\n  margin-bottom: 1px;\n}\n.school-name {\r\n  font-weight: 700;\n}\r\n","",{version:3,sources:["D:/git/Xlabel2Web/WEB/admin_web/src/views/set/Feedback.vue"],names:[],mappings:";AACA;EACE,mBAAmB;CACpB;AACD;EACE,iBAAiB;CAClB",file:"Feedback.vue",sourcesContent:["\n.school-item {\r\n  margin-bottom: 1px;\n}\n.school-name {\r\n  font-weight: 700;\n}\r\n"],sourceRoot:""}])}});
//# sourceMappingURL=8.a4f0decf9e2814030fc5.js.map