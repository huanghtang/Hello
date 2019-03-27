<template>
<div>
<!-- 搜索 -->
<el-form inline label-position="right" label-width="60px" class="query-form">
  <el-form-item>
    <el-button type="primary" @click="handleAdd">添加权限组</el-button>
  </el-form-item>
</el-form>
<!-- 數據表格 -->
<el-table :data="tableData" empty-text="" class="table" stripe border v-loading="loading">
  <el-table-column type="index" label="序号"  width="70"></el-table-column>
  <el-table-column prop="name" label="权限组"></el-table-column>
  <el-table-column prop="createTime" label="添加時間"> </el-table-column>
  <el-table-column label="操作">
    <template slot-scope="scope">
      <el-button size="mini" type="info" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
      <el-button size="mini" type="danger" @click="handleRemove(scope.$index, scope.row)">刪除</el-button>
    </template>
  </el-table-column>
</el-table>


<el-dialog title="新增/编辑" :visible.sync="addEditDialogVisible" width="800px" >
  <!-- 权限详情 -->
 <el-form ref="infoForm" :model="infoForm" :rules="rules" label-width="120px">
	<el-form-item prop="name" label="权限组名称：">
	  <el-input v-model="infoForm.name"></el-input>
	</el-form-item>
	<el-form-item prop="menus" label="权限：">
   <el-tree :data="menus" show-checkbox expand-on-click-node node-key="id" ref="tree" 
      :default-expanded-keys="infoForm.menus" 
      :default-checked-keys="infoForm.menus" 
      :props="defaultProps">
   </el-tree>
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
import { list, remove, update, save, getMenuList } from "@/api/auth/role_list";
import NProgress from "nprogress"; // Progress 進度條

export default {
  data() {
    return {
      loading: false,
      addEditDialogVisible: false,
      isEdit: false,
      menus: [],
      infoForm: {
        id: "",
        name: "",
        menus: []
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
      tableData: [],
      rules: {
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
        menus: [{ required: true, message: "请选择", trigger: "blur" }]
      }
    };
  },
  created() {},
  mounted() {
    this.getList();
    this.getMenus();
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
    handleAdd: function() {
      this.getMenus();
      this.isEdit = false;
      this.addEditDialogVisible = true;
      this.infoForm = {};
    },
    handleEdit: function(index, row) {
      this.isEdit = true;
      this.addEditDialogVisible = true;
      this.infoForm = Object.assign({}, row);
     // console.log(this.infoForm);
      setTimeout(() => {
        this.$refs.tree.setCheckedKeys(row.menus);
      }, 100);
    },
    getList: function() {
      this.loading = true;
      list()
        .then(res => {
          this.tableData = res.data.list;
          console.log(this.tableData);
          this.loading = false;
        })
        .catch(error => {
          this.loading = false;
          this.$message.error(error);
          console.log(error);
        });
    },
    getMenus: function() {
      getMenuList()
        .then(res => {
          this.menus = res.data.list;
        })
        .catch(error => {
          this.$message.error(error);
          console.log(error);
        });
    },
    handleSubmitSave: function() {
      this.infoForm.menus = this.$refs.tree.getCheckedKeys();
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
      this.infoForm.menus = this.$refs.tree.getCheckedKeys();
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
          let id = row.sysUserRoleId;
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
