webpackJsonp([14],{"234V":function(t,e,a){var i=a("60iq");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);a("FIqI")("c1b58cc6",i,!0,{})},"4pOt":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=a("aA9S"),o=a.n(i),n=a("E4C3"),s=a.n(n),r=a("eOoE");var l={components:{},data:function(){return{isShow:!0,isCheck:!1,uploadUrl:this.GLOBAL.prefix+"/admin/upload/uploadImg",addEditDialogVisible:!1,dialogImageUrl:"",dialogImgVisible:!1,isEdit:!1,aboutImg:"",page:{pageNumber:1,pageSize:10,total:0},infoForm:{},rules:{fontKind:[{required:!0,message:"请输入字体标识",trigger:"blur"}],fontName:[{required:!0,message:"请输入字体名称",trigger:"blur"}]},tableData:[]}},created:function(){},mounted:function(){this.getList()},computed:{headers:function(){return{xinyeSysUserSessionId:this.$store.state.token}}},methods:{handleCoverSuccess:function(t,e){"200"!==t.code?this.$message.error(t.msg):(this.aboutImg=t.file.url,this.infoForm.aboutImg=t.file.url)},beforeAvatarUpload:function(t){var e="image/jpeg"===t.type||"image/png"===t.type,a=t.size/1024/1024<20;return e||this.$message.error("上传图片片只能是 JPG|PNG|IMAGE|JPEG 格式!"),a||this.$message.error("上传图片大小不能超过 20MB!"),e&&a},handleUpload:function(){this.infoForm.fontUrl=""},handleSizeChange:function(t){this.page.pageSize=t,this.getList()},handleCurrentChange:function(t){this.page.pageNumber=t,this.getList()},formatDateJoinAt:function(t,e){return this.$moment(t.joinAt).format("YYYY-MM-DD")},formatDateNextPayAt:function(t,e){return this.$moment(t.nextPayAt).format("YYYY-MM-DD")},handleCoverCardPreview:function(t){this.dialogImageUrl=t,this.dialogImgVisible=!0},handleSearch:function(){this.getList()},handleEdit:function(t,e){this.isCheck=!1,this.isShow=!0,this.isEdit=!0,this.infoForm=o()({},e),this.aboutImg=this.infoForm.aboutImg,this.addEditDialogVisible=!0},handleCheck:function(t,e){this.isCheck=!0,this.isEdit=!1,this.isShow=!0,this.fontFileList=[],this.infoForm=o()({},e),this.aboutImg=this.infoForm.aboutImg,this.addEditDialogVisible=!0},handleSubmitUpdate:function(){var t=this;this.$refs.infoForm.validate(function(e){e&&t.$confirm("确认提交吗？","提示",{confirmButtonText:"确定",cancelButtonText:"取消"}).then(function(){s.a.start();var e,a=o()({},t.infoForm);(e=a,Object(r.a)({url:"/admin/set/about/update",method:"post",data:e})).then(function(e){s.a.done(),t.$message.success(e.data.msg),t.$refs.infoForm.resetFields(),t.addEditDialogVisible=!1,t.getList()})})})},getList:function(){var t=this;(function(t){return Object(r.a)({url:"/admin/set/about/list",method:"post",data:t})})({pageNumber:this.page.pageNumber,pageSize:this.page.pageSize}).then(function(e){t.tableData=e.data.page.list,console.log(t.tableData),t.page.pageNumber=e.data.page.pageNumber,t.page.pageSize=e.data.page.pageSize,t.page.total=e.data.page.totalRow}).catch(function(e){t.$message.error(e),console.log(e)})}}},u={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("el-table",{staticClass:"table",attrs:{data:t.tableData,stripe:"",border:""}},[a("el-table-column",{attrs:{type:"index",label:"编号",width:"70"}}),t._v(" "),a("el-table-column",{attrs:{prop:"aboutImg",label:"图片",width:"100"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("img",{staticStyle:{width:"30px",height:"30px",cursor:"pointer"},attrs:{src:e.row.aboutImg},on:{click:function(a){return t.handleCoverCardPreview(e.row.aboutImg)}}})]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"createTime",label:"添加时间"}}),t._v(" "),a("el-table-column",{attrs:{prop:"sysUserName",label:"操作人"}}),t._v(" "),a("el-table-column",{attrs:{label:"操作",width:"230"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{size:"mini",type:"primary"},on:{click:function(a){return t.handleCheck(e.$index,e.row)}}},[t._v("查看")]),t._v(" "),a("el-button",{attrs:{size:"mini"},on:{click:function(a){return t.handleEdit(e.$index,e.row)}}},[t._v("编辑")])]}}])})],1),t._v(" "),a("el-pagination",{attrs:{"current-page":t.page.pageNumber,"page-sizes":[10,20,30,40],"page-size":t.page.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:t.page.total},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange}}),t._v(" "),a("el-dialog",{attrs:{title:"编辑",visible:t.addEditDialogVisible,width:"800px"},on:{"update:visible":function(e){t.addEditDialogVisible=e}}},[a("el-form",{ref:"infoForm",attrs:{model:t.infoForm,rules:t.rules,"label-width":"150px"}},[a("el-form-item",{attrs:{prop:"aboutImg",label:"logo图标：",disabled:t.isCheck}},[a("el-upload",{staticClass:"avatar-uploader",attrs:{action:t.uploadUrl,"show-file-list":!1,"on-success":t.handleCoverSuccess,"before-upload":t.beforeAvatarUpload}},[t.aboutImg?a("img",{staticClass:"avatar",attrs:{src:t.aboutImg}}):a("i",{staticClass:"el-icon-plus avatar-uploader-icon"})])],1)],1),t._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(e){t.addEditDialogVisible=!1}}},[t._v("关闭")]),t._v(" "),t.isEdit?a("el-button",{attrs:{type:"primary"},on:{click:t.handleSubmitUpdate}},[t._v("更 新")]):t._e()],1)],1),t._v(" "),a("el-dialog",{attrs:{visible:t.dialogImgVisible,size:"tiny"},on:{"update:visible":function(e){t.dialogImgVisible=e}}},[a("img",{attrs:{width:"100%",src:t.dialogImageUrl,alt:""}})])],1)},staticRenderFns:[]};var d=a("C7Lr")(l,u,!1,function(t){a("234V")},null,null);e.default=d.exports},"60iq":function(t,e,a){(t.exports=a("UTlt")(!0)).push([t.i,"\n.school-item {\r\n  margin-bottom: 1px;\n}\n.school-name {\r\n  font-weight: 700;\n}\r\n","",{version:3,sources:["D:/git/Xlabel2Web/WEB/admin_web/src/views/set/About.vue"],names:[],mappings:";AACA;EACE,mBAAmB;CACpB;AACD;EACE,iBAAiB;CAClB",file:"About.vue",sourcesContent:["\n.school-item {\r\n  margin-bottom: 1px;\n}\n.school-name {\r\n  font-weight: 700;\n}\r\n"],sourceRoot:""}])}});
//# sourceMappingURL=14.6b93dbf614de72bd005b.js.map