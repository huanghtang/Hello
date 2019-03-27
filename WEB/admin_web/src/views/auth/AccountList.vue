<template>
<div>
<!-- 搜索 -->
<el-form inline :model="query" label-position="right" label-width="60px" class="query-form">
  <el-input v-model="query.sysUserName" placeholder="请输入账号" style="width:180px;" ></el-input>
  <el-form-item>
    <el-button type="primary" @click="handleSearch">搜索</el-button>
    <el-button type="primary" @click="handleAdd" >添加平台人员</el-button>
  </el-form-item>
</el-form><br>

<!-- 数据表格 -->
<el-table :data="tableData" class="table" stripe border v-loading="loading" >
  <el-table-column type="index" label="序号"  width="70"></el-table-column>
  <el-table-column prop="sysUserName" label="账号"></el-table-column>
    <el-table-column prop="linkManName" label="联系人名称"></el-table-column>
    <el-table-column prop="phoneNumber" label="电话号码"></el-table-column>
  <el-table-column prop="roleName" label="权限组"></el-table-column>
  <el-table-column prop="sysUserLoginTime" label="登录时间"></el-table-column>
   <el-table-column prop="sysUserCreateTime" label="创建时间"></el-table-column>
  <el-table-column label="操作" width="165px">
    <template slot-scope="scope">
      <el-button size="mini" type="info" v-if="scope.row.sysUserRoleId !== 0" @click="handleEdit(scope.$index, scope.row)" >编辑</el-button>
      <el-button size="mini" type="danger" v-if="scope.row.sysUserRoleId !== 0" @click="handleRemove(scope.$index, scope.row)" >删除</el-button>
    </template>
  </el-table-column>
</el-table>
<!-- 分页组件 -->
<el-pagination
  @size-change="handleSizeChange"
  @current-change="handleCurrentChange"
  :current-page="page.pageNumber"
  :page-sizes="[10, 20, 30, 40]"
  :page-size="page.pageSize"
  layout="total, sizes, prev, pager, next, jumper"
  :total="page.total">
</el-pagination>

<el-dialog title="新增/编辑" :visible.sync="addEditDialogVisible" width="800px" >
 <el-form ref="infoForm" :model="infoForm" :rules="rules" label-width="120px">
   	<el-form-item prop="linkManName" label="联系人名称：">
   <el-input v-model="infoForm.linkManName"></el-input>
	</el-form-item>
  	<el-form-item prop="phoneNumber" label="电话号码：">
   <el-input v-model="infoForm.phoneNumber"></el-input>
	</el-form-item>
	<el-form-item prop="sysUserName" label="登录账号：">
   <el-input v-model="infoForm.sysUserName"></el-input>
	</el-form-item>
  <el-form-item prop="sysUserPass" label="登录密码：" v-if="!isEdit">
   <el-input v-model="infoForm.sysUserPass" :disabled="isEdit"  type="password"></el-input>
	</el-form-item>
	<el-form-item prop="sysUserRoleId" label="权限组：">
   <el-select v-model="infoForm.sysUserRoleId" placeholder="请选择权限组">
    <el-option v-for="role in roleList" :key="role.sysUserRoleId" :label="role.name" :value="role.sysUserRoleId"></el-option>
  </el-select>
	</el-form-item>
</el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="addEditDialogVisible = false">关闭</el-button>
    <el-button type="primary" v-if="isEdit" @click="handleSubmitUpdate">提 交</el-button>
    <el-button type="primary" v-else @click="handleSubmitSave">确 定</el-button>
  </span>
</el-dialog>


</div>
</template>

<script type="text/ecmascript-6">
import { list, remove, update, save, getRoleList } from "@/api/auth/sys_user_list";
import NProgress from "nprogress"; // Progress 进度条

export default {
  data() {
    return {
      query: {
        sysUserName: ""
      },
      loading: false,
      addEditDialogVisible: false,
      isEdit: false,
      infoForm: {
        linkManName: "",
        phoneNumber: "",
        sysUserId: "",
        sysUserName: "",
        sysUserPass: "",
        sysUserRoleId: ""
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      page: {
        pageNumber: 1,
        pageSize: 10,
        total: 0
      },
      roleList: [],
      tableData: [],
      rules: {
        phoneNumber: [{ required: true, message: "请输入电话号码", trigger: "blur" }],
        linkManName: [{ required: true, message: "请输入联系人名称", trigger: "blur" }],
        sysUserPass: [{ required: true, message: "请输入密码", trigger: "blur" }],
        sysUserName: [{ required: true, message: "请输入登录账号", trigger: "blur" }],
        sysUserRoleId: [{ required: true, message: "请选择权限组" }]
      }
    };
  },
  created() {},
  mounted() {
    this.getList();
    this.getRoleList();
  },
  watch: {
    addEditDialogVisible: function(val, oldVla) {
      if (this.$refs["infoForm"] !== undefined) {
        this.$refs["infoForm"].resetFields();
      }
    }
  },
  methods: {
    handleSizeChange: function(val) {
      this.page.pageSize = val;
      this.getList();
    },
    handleCurrentChange: function(val) {
      this.page.pageNumber = val;
      this.getList();
    },
    handleSearch: function() {
      this.getList();
    },
    handleAdd: function() {
      this.infoForm.sysUserPass = "";
      this.isEdit = false;
      this.infoForm = Object.assign({}, null);
      this.addEditDialogVisible = true;
    },
    handleEdit: function(index, row) {
      this.isEdit = true;
      this.infoForm = Object.assign({}, row);
      this.addEditDialogVisible = true;
    },
    getList: function() {
      let params = {
        sysUserName: this.query.sysUserName,
        pageNumber: this.page.pageNumber,
        pageSize: this.page.pageSize
      };
      this.loading = true;
      list(params)
        .then(res => {
          this.tableData = res.data.page.list;
          this.page.pageNumber = res.data.page.pageNumber;
          this.page.pageSize = res.data.page.pageSize;
          this.page.total = res.data.page.totalRow;
          this.loading = false;
        })
        .catch(error => {
          this.loading = false;
          this.$message.error(error);
          console.log(error);
        });
    },
    getRoleList: function() {
      getRoleList()
        .then(res => {
          this.roleList = res.data.list;
        })
        .catch(error => {
          this.$message.error(error);
          console.log(error);
        });
    },
    handleSubmitSave: function() {
      this.$refs.infoForm.validate(valid => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            NProgress.start();
            let para = Object.assign({}, this.infoForm);
            save(para).then(res => {
              NProgress.done();
              this.$message.success(res.data.msg);
              this.$refs["infoForm"].resetFields();
              this.addEditDialogVisible = false;
              this.getList();
            });
          });
        }
      });
    },
    handleSubmitUpdate: function() {
      this.$refs.infoForm.validate(valid => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            NProgress.start();
            let para = Object.assign({}, this.infoForm);
            update(para).then(res => {
              NProgress.done();
              this.$message.success(res.data.msg);
              this.$refs["infoForm"].resetFields();
              this.addEditDialogVisible = false;
              this.getList();
            });
          });
        }
      });
    },
    handleRemove: function(index, row) {
      this.$confirm("确定删除吗？")
        .then(_ => {
          let id = row.sysUserId;
          remove(id)
            .then(res => {
              this.$message.success(res.data.msg);
              this.getList();
            })
            .catch(error => {
              this.$message.error(error);
            });
        })
        .catch(_ => {});
    }
  }
};
</script>

<style>

</style>
