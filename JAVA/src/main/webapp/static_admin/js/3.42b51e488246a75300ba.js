webpackJsonp([3],{DOtj:function(e,t,a){(e.exports=a("UTlt")(!0)).push([e.i,"\n.school-item {\r\n  margin-bottom: 1px;\n}\n.school-name {\r\n  font-weight: 700;\n}\r\n","",{version:3,sources:["D:/git/Xlabel2Web/WEB/admin_web/src/views/database/UserList.vue"],names:[],mappings:";AACA;EACE,mBAAmB;CACpB;AACD;EACE,iBAAiB;CAClB",file:"UserList.vue",sourcesContent:["\n.school-item {\r\n  margin-bottom: 1px;\n}\n.school-name {\r\n  font-weight: 700;\n}\r\n"],sourceRoot:""}])},IHPi:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=a("aA9S"),i=a.n(s),r=a("eOoE");var l={components:{},data:function(){return{isCheck:!1,query:{userPhone:"",status:"",registerType:""},userImg:"",dialogImgVisible:!1,addEditDialogVisible:!1,dialogImageUrl:"",isEdit:!1,page:{pageNumber:1,pageSize:10,total:0},statusList:[{value:"",name:"全部"},{value:1,name:"已注册"},{value:2,name:"已登录"}],loginStatusList:[{value:"",name:"全部"},{value:0,name:"qq登录"},{value:1,name:"微信登录"},{value:2,name:"手机注册"}],kindList:[],infoForm:{},rules:{logoKindId:[{required:!0,message:"请选择主类名称",trigger:"blur"}],logoChildKindName:[{required:!0,message:"请输入子分类名称",trigger:"blur"}]},tableData:[]}},created:function(){},mounted:function(){this.getList()},computed:{headers:function(){return{xinyeSysUserSessionId:this.$store.state.token}}},methods:{handleSizeChange:function(e){this.page.pageSize=e,this.getList()},handleCurrentChange:function(e){this.page.pageNumber=e,this.getList()},handleCoverCardPreview:function(e){this.dialogImageUrl=e,this.dialogImgVisible=!0},formatDateJoinAt:function(e,t){return this.$moment(e.joinAt).format("YYYY-MM-DD")},formatDateNextPayAt:function(e,t){return this.$moment(e.nextPayAt).format("YYYY-MM-DD")},handleSearch:function(){this.getList()},handleCheck:function(e,t){this.isCheck=!0,this.isEdit=!1,this.infoForm=i()({},t),this.userImg=this.infoForm.userImg,this.logoKindName=this.infoForm.logoKindName,this.addEditDialogVisible=!0},getList:function(){var e=this;(function(e){return Object(r.a)({url:"/admin/database/user/list",method:"post",data:e})})({userPhone:this.query.userPhone,status:this.query.status,registerType:this.query.registerType,pageNumber:this.page.pageNumber,pageSize:this.page.pageSize}).then(function(t){e.tableData=t.data.page.list,console.log(e.tableData),e.page.pageNumber=t.data.page.pageNumber,e.page.pageSize=t.data.page.pageSize,e.page.total=t.data.page.totalRow}).catch(function(t){e.$message.error(t),console.log(t)})}}},o={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("el-form",{staticClass:"query-form",attrs:{inline:"",model:e.query,"label-position":"right","label-width":"70px"}},[a("el-select",{attrs:{placeholder:"登录状态"},model:{value:e.query.status,callback:function(t){e.$set(e.query,"status",t)},expression:"query.status"}},e._l(e.statusList,function(e){return a("el-option",{key:e.value,attrs:{label:e.name,value:e.value}})}),1),e._v(" "),a("el-select",{attrs:{placeholder:"登录方式"},model:{value:e.query.registerType,callback:function(t){e.$set(e.query,"registerType",t)},expression:"query.registerType"}},e._l(e.loginStatusList,function(e){return a("el-option",{key:e.value,attrs:{label:e.name,value:e.value}})}),1),e._v(" "),a("el-input",{staticStyle:{width:"150px"},attrs:{placeholder:"请输入电话号码"},model:{value:e.query.userPhone,callback:function(t){e.$set(e.query,"userPhone",t)},expression:"query.userPhone"}}),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:e.handleSearch}},[e._v("搜索")])],1)],1),e._v(" "),a("el-table",{staticClass:"table",attrs:{data:e.tableData,stripe:"",border:""}},[a("el-table-column",{attrs:{type:"index",label:"序号",width:"70"}}),e._v(" "),a("el-table-column",{attrs:{prop:"userImg",label:"头像",width:"70"},scopedSlots:e._u([{key:"default",fn:function(e){return[a("img",{staticStyle:{width:"40px",height:"40px",cursor:"pointer"},attrs:{src:e.row.userImg}})]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"userPhone",label:"用户电话"}}),e._v(" "),a("el-table-column",{attrs:{prop:"registerType",label:"登录方式"},scopedSlots:e._u([{key:"default",fn:function(t){return[0===t.row.registerType?a("span",[e._v("qq登录")]):e._e(),e._v(" "),1===t.row.registerType?a("span",[e._v("微信登录")]):e._e(),e._v(" "),2===t.row.registerType?a("span",[e._v("手机注册")]):e._e()]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"status",label:"状态"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.status?a("span",[e._v("已注册")]):e._e(),e._v(" "),2===t.row.status?a("span",[e._v("已登录")]):e._e()]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"lastLoginTime",label:"登录时间"}}),e._v(" "),a("el-table-column",{attrs:{label:"操作",width:"190"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{size:"mini",type:"info"},on:{click:function(a){return e.handleCheck(t.$index,t.row)}}},[e._v("查看")]),e._v(" "),a("div",{staticStyle:{"margin-top":"8px"}},[2===t.row.carUserType?a("el-button",{directives:[{name:"has",rawName:"v-has",value:"user_carUser_del",expression:"'user_carUser_del'"}],attrs:{size:"mini",type:"danger"},on:{click:function(a){return e.handleCheck(t.$index,t.row)}}},[e._v("删除")]):e._e()],1)]}}])})],1),e._v(" "),a("el-pagination",{attrs:{"current-page":e.page.pageNumber,"page-sizes":[10,20,30,40],"page-size":e.page.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.page.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}}),e._v(" "),a("el-dialog",{attrs:{title:"查看",visible:e.addEditDialogVisible,width:"50%"},on:{"update:visible":function(t){e.addEditDialogVisible=t}}},[a("el-form",{ref:"infoForm",attrs:{model:e.infoForm,rules:e.rules,"label-width":"110px"}},[a("el-form-item",{attrs:{prop:"userImg",label:"会员头像"}},[a("el-upload",{staticClass:"avatar-uploader",attrs:{"show-file-list":!1,disabled:e.isCheck}},[e.userImg?a("img",{staticClass:"avatar",attrs:{src:e.userImg}}):a("i",{staticClass:"el-icon-plus avatar-uploader-icon"})])],1),e._v(" "),a("el-col",{attrs:{span:12}},[a("el-form-item",{attrs:{prop:"status",label:"账号状态:"}},[a("el-select",{attrs:{disabled:e.isCheck,placeholder:"账号状态:"},model:{value:e.infoForm.status,callback:function(t){e.$set(e.infoForm,"status",t)},expression:"infoForm.status"}},e._l(e.statusList,function(e){return a("el-option",{key:e.value,attrs:{label:e.name,value:e.value}})}),1)],1)],1),e._v(" "),a("el-col",{attrs:{span:12}},[a("el-form-item",{attrs:{prop:"registerType",label:"登录方式:"}},[a("el-select",{attrs:{disabled:e.isCheck,placeholder:"登录方式:"},model:{value:e.infoForm.registerType,callback:function(t){e.$set(e.infoForm,"registerType",t)},expression:"infoForm.registerType"}},e._l(e.loginStatusList,function(e){return a("el-option",{key:e.value,attrs:{label:e.name,value:e.value}})}),1)],1)],1),e._v(" "),a("el-row",[a("el-col",{attrs:{span:12}},[a("el-form-item",{attrs:{prop:"userPhone",label:"电话号码："}},[a("el-input",{attrs:{disabled:e.isCheck},model:{value:e.infoForm.userPhone,callback:function(t){e.$set(e.infoForm,"userPhone",t)},expression:"infoForm.userPhone"}})],1)],1),e._v(" "),a("el-col",{attrs:{span:12}},[a("el-form-item",{attrs:{prop:"userId",label:"用户id："}},[a("el-input",{attrs:{disabled:e.isCheck},model:{value:e.infoForm.userId,callback:function(t){e.$set(e.infoForm,"userId",t)},expression:"infoForm.userId"}})],1)],1),e._v(" "),a("el-col",{attrs:{span:12}},[a("el-form-item",{attrs:{prop:"lastLoginTime",label:"登录时间："}},[a("el-input",{attrs:{disabled:e.isCheck},model:{value:e.infoForm.lastLoginTime,callback:function(t){e.$set(e.infoForm,"lastLoginTime",t)},expression:"infoForm.lastLoginTime"}})],1)],1)],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.addEditDialogVisible=!1}}},[e._v("关闭")])],1)],1),e._v(" "),a("el-dialog",{attrs:{visible:e.dialogImgVisible,size:"tiny"},on:{"update:visible":function(t){e.dialogImgVisible=t}}},[a("img",{attrs:{width:"100%",src:e.dialogImageUrl,alt:""}})])],1)},staticRenderFns:[]};var n=a("C7Lr")(l,o,!1,function(e){a("pAkO")},null,null);t.default=n.exports},pAkO:function(e,t,a){var s=a("DOtj");"string"==typeof s&&(s=[[e.i,s,""]]),s.locals&&(e.exports=s.locals);a("FIqI")("74778d72",s,!0,{})}});
//# sourceMappingURL=3.42b51e488246a75300ba.js.map