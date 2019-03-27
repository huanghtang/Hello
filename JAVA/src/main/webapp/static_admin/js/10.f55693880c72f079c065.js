webpackJsonp([10],{"+fc0":function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=i("aA9S"),o=i.n(n),a=i("E4C3"),s=i.n(a),r=i("eOoE");var l={components:{},data:function(){var t=this;return{isShow:!0,isCheck:!1,query:{fontName:""},uploadUrl:this.GLOBAL.prefix+"/admin/upload/uploadFont",addEditDialogVisible:!1,dialogImageUrl:"",dialogImgVisible:!1,isEdit:!1,logoImg:"",page:{pageNumber:1,pageSize:10,total:0},fontUrl:"",fontFileList:[],kindList:[],childList:[],infoForm:{fontUrl:""},rules:{fontKind:[{required:!0,message:"请输入字体标识",trigger:"blur"}],fontName:[{required:!0,message:"请输入字体名称",trigger:"blur"}],fontUrl:[{required:!0,validator:function(e,i,n){""===i?n(new Error("请上传字体文件")):void 0!==i||""!==t.infoForm.fontUrl&&void 0!==t.infoForm.fontUrl?n():n(new Error("请上传字体文件"))},trigger:"change"}]},tableData:[]}},created:function(){},mounted:function(){this.getList()},watch:{addEditDialogVisible:function(t,e){void 0!==this.$refs.infoForm&&this.$refs.infoForm.resetFields()}},computed:{headers:function(){return{xinyeSysUserSessionId:this.$store.state.token}}},methods:{handleSizeChange:function(t){this.page.pageSize=t,this.getList()},handleCurrentChange:function(t){this.page.pageNumber=t,this.getList()},formatDateJoinAt:function(t,e){return this.$moment(t.joinAt).format("YYYY-MM-DD")},formatDateNextPayAt:function(t,e){return this.$moment(t.nextPayAt).format("YYYY-MM-DD")},handleSearch:function(){this.getList()},handleAdd:function(){this.query.fontName="",this.fontFileList=[],this.isShow=!1,this.isEdit=!1,this.isCheck=!1,this.addEditDialogVisible=!0,this.infoForm=o()({},null)},handleEdit:function(t,e){this.isCheck=!1,this.isShow=!0,this.isEdit=!0,this.fontFileList=[],this.infoForm=o()({},e),this.addEditDialogVisible=!0},handleCheck:function(t,e){this.isCheck=!0,this.isEdit=!1,this.isShow=!0,this.fontFileList=[],this.infoForm=o()({},e),this.addEditDialogVisible=!0},selectMainChange:function(t){this.getChildList(t),!0===this.isEdit&&(this.infoForm.logoChildKindId="",this.getChildList(t))},handleSubmitSave:function(){var t=this;this.$refs.infoForm.validate(function(e){e&&t.$confirm("确认提交吗？","提示",{confirmButtonText:"确定",cancelButtonText:"取消"}).then(function(){s.a.start();var e,i=o()({},t.infoForm);(e=i,Object(r.a)({url:"/admin/set/font/add",method:"post",data:e})).then(function(e){s.a.done(),t.$message.success(e.data.msg),t.$refs.infoForm.resetFields(),t.addEditDialogVisible=!1,t.getList()})})})},handleSubmitUpdate:function(){var t=this;this.$refs.infoForm.validate(function(e){e&&t.$confirm("确认提交吗？","提示",{confirmButtonText:"确定",cancelButtonText:"取消"}).then(function(){s.a.start();var e,i=o()({},t.infoForm);(e=i,Object(r.a)({url:"/admin/set/font/update",method:"post",data:e})).then(function(e){s.a.done(),t.$message.success(e.data.msg),t.$refs.infoForm.resetFields(),t.addEditDialogVisible=!1,t.getList()})})})},handleRemove:function(t,e){var i=this;this.$confirm("确定删除吗？","提示",{confirmButtonText:"确定",cancelButtonText:"取消"}).then(function(t){(function(t){return Object(r.a)({url:"/admin/set/font/del/"+t,method:"get"})})(e.fontId).then(function(t){i.$message.success(t.data.msg),i.getList()}).catch(function(t){i.$message.error(t)})}).catch(function(t){})},handleFontSuccess:function(t,e){"200"!==t.code?this.$message.error(t.msg):(this.infoForm.fontUrl=t.file.url,this.fontUrl=this.infoForm.fontUrl)},beforeUploadFont:function(t){var e=t.name.substring(t.name.lastIndexOf(".")+1),i=t.size/1024/1024<30;return"ttf"!==e&&"TTF"!==e?(this.$message.error("请上传正确的字体格式"),!1):i?void 0:(this.$message.error("上传文件大小不能超过30MB哦!"),!1)},getList:function(){var t=this;(function(t){return Object(r.a)({url:"/admin/set/font/list",method:"post",data:t})})({fontName:this.query.fontName,pageNumber:this.page.pageNumber,pageSize:this.page.pageSize}).then(function(e){t.tableData=e.data.page.list,console.log(t.tableData),t.page.pageNumber=e.data.page.pageNumber,t.page.pageSize=e.data.page.pageSize,t.page.total=e.data.page.totalRow}).catch(function(e){t.$message.error(e),console.log(e)})}}},d={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",[i("el-form",{staticClass:"query-form",attrs:{inline:"",model:t.query,"label-position":"right","label-width":"70px"}},[i("el-input",{staticStyle:{width:"150px"},attrs:{placeholder:"请输入字体名称"},model:{value:t.query.fontName,callback:function(e){t.$set(t.query,"fontName",e)},expression:"query.fontName"}}),t._v(" "),i("el-form-item",[i("el-button",{attrs:{type:"primary"},on:{click:t.handleSearch}},[t._v("搜索")]),t._v(" "),i("el-button",{attrs:{type:"primary"},on:{click:t.handleAdd}},[t._v("新增分类")])],1)],1),t._v(" "),i("el-table",{staticClass:"table",attrs:{data:t.tableData,stripe:"",border:""}},[i("el-table-column",{attrs:{type:"index",label:"编号",width:"70"}}),t._v(" "),i("el-table-column",{attrs:{prop:"fontName",label:"字体名称"}}),t._v(" "),i("el-table-column",{attrs:{prop:"fontKind",label:"字体标识"}}),t._v(" "),i("el-table-column",{attrs:{prop:"createTime",label:"添加时间"}}),t._v(" "),i("el-table-column",{attrs:{prop:"sysUserName",label:"操作人"}}),t._v(" "),i("el-table-column",{attrs:{label:"操作",width:"230"},scopedSlots:t._u([{key:"default",fn:function(e){return[i("el-button",{attrs:{size:"mini",type:"primary"},on:{click:function(i){return t.handleCheck(e.$index,e.row)}}},[t._v("查看")]),t._v(" "),i("el-button",{attrs:{size:"mini"},on:{click:function(i){return t.handleEdit(e.$index,e.row)}}},[t._v("编辑")]),t._v(" "),i("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(i){return t.handleRemove(e.$index,e.row)}}},[t._v("刪除")])]}}])})],1),t._v(" "),i("el-pagination",{attrs:{"current-page":t.page.pageNumber,"page-sizes":[10,20,30,40],"page-size":t.page.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:t.page.total},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange}}),t._v(" "),i("el-dialog",{attrs:{title:"新增/编辑/查看",visible:t.addEditDialogVisible,width:"800px"},on:{"update:visible":function(e){t.addEditDialogVisible=e}}},[i("el-form",{ref:"infoForm",attrs:{model:t.infoForm,rules:t.rules,"label-width":"150px"}},[i("el-form-item",{attrs:{prop:"fontName",label:"字体名称："}},[i("el-input",{attrs:{disabled:t.isCheck},model:{value:t.infoForm.fontName,callback:function(e){t.$set(t.infoForm,"fontName",e)},expression:"infoForm.fontName"}})],1),t._v(" "),i("el-form-item",{attrs:{prop:"fontKind",label:"字体标识"}},[i("el-input",{attrs:{disabled:t.isCheck},model:{value:t.infoForm.fontKind,callback:function(e){t.$set(t.infoForm,"fontKind",e)},expression:"infoForm.fontKind"}})],1),t._v(" "),i("el-form-item",{attrs:{prop:"fontUrl",label:"字体上传"}},[i("el-upload",{ref:"upload",staticClass:"upload-demo",attrs:{action:t.uploadUrl,"on-success":t.handleFontSuccess,"before-upload":t.beforeUploadFont,limit:1,"file-list":t.fontFileList}},[0==t.isCheck?i("el-button",{attrs:{size:"small",type:"primary"}},[t._v("点击上传")]):t._e(),t._v(" "),0==t.isCheck?i("div",{staticClass:"el-upload__tip",attrs:{slot:"tip"},slot:"tip"},[t._v("只能上传字体文件，且不超过20M")]):t._e()],1),t._v(" "),1==t.isShow?i("el-input",{attrs:{disabled:!0},model:{value:t.infoForm.fontUrl,callback:function(e){t.$set(t.infoForm,"fontUrl",e)},expression:"infoForm.fontUrl"}},[t._v(t._s(t.fontUrl))]):t._e()],1)],1),t._v(" "),i("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{on:{click:function(e){t.addEditDialogVisible=!1}}},[t._v("关闭")]),t._v(" "),t.isEdit||t.isCheck?t._e():i("el-button",{attrs:{type:"primary"},on:{click:t.handleSubmitSave}},[t._v("确 定")]),t._v(" "),t.isEdit?i("el-button",{attrs:{type:"primary"},on:{click:t.handleSubmitUpdate}},[t._v("更 新")]):t._e()],1)],1),t._v(" "),i("el-dialog",{attrs:{visible:t.dialogImgVisible,size:"tiny"},on:{"update:visible":function(e){t.dialogImgVisible=e}}},[i("img",{attrs:{width:"100%",src:t.dialogImageUrl,alt:""}})])],1)},staticRenderFns:[]};var c=i("C7Lr")(l,d,!1,function(t){i("BbYP")},null,null);e.default=c.exports},BbYP:function(t,e,i){var n=i("CLhK");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);i("FIqI")("ebe7a4fe",n,!0,{})},CLhK:function(t,e,i){(t.exports=i("UTlt")(!0)).push([t.i,"\n.school-item {\r\n  margin-bottom: 1px;\n}\n.school-name {\r\n  font-weight: 700;\n}\r\n","",{version:3,sources:["D:/git/Xlabel2Web/WEB/admin_web/src/views/set/Font.vue"],names:[],mappings:";AACA;EACE,mBAAmB;CACpB;AACD;EACE,iBAAiB;CAClB",file:"Font.vue",sourcesContent:["\n.school-item {\r\n  margin-bottom: 1px;\n}\n.school-name {\r\n  font-weight: 700;\n}\r\n"],sourceRoot:""}])}});
//# sourceMappingURL=10.f55693880c72f079c065.js.map