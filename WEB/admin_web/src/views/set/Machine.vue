<template>
<div>
<!-- 搜索 -->
<el-form inline :model="query" label-position="right" label-width="70px" class="query-form">
   <el-input v-model="query.machineName" placeholder="请输入设备名称" style="width:150px;"></el-input>
  <el-form-item>
    <el-button type="primary" @click="handleSearch" >搜索</el-button>
    <el-button type="primary" @click="handleAdd">新增设备</el-button>
  </el-form-item>
</el-form>
<!-- 數據表格 -->
<el-table :data="tableData"  class="table" stripe border>
  <el-table-column type="index" label="编号"  width="70"></el-table-column>
  <el-table-column prop="machineName" label="设备名称"></el-table-column>
    <el-table-column prop="createTime" label="添加时间"></el-table-column>
      <el-table-column prop="sysUserName" label="操作人"></el-table-column>
  <el-table-column label="操作" width="230" >
    <template slot-scope="scope">
           <el-button size="mini" type="primary" @click="handleCheck(scope.$index, scope.row)">查看</el-button>
      <el-button size="mini"  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
      <el-button size="mini" type="danger" @click="handleRemove(scope.$index, scope.row)">刪除</el-button>
    </template>
  </el-table-column>
</el-table>
<!-- 分頁組件 -->
<el-pagination
  @size-change="handleSizeChange"
  @current-change="handleCurrentChange"
  :current-page="page.pageNumber"
  :page-sizes="[10, 20, 30, 40]"
  :page-size="page.pageSize"
  layout="total, sizes, prev, pager, next, jumper"
  :total="page.total">
</el-pagination>


<el-dialog title="新增/编辑/查看" :visible.sync="addEditDialogVisible" width="800px" >
  <!-- 设备詳情 -->
 <el-form ref="infoForm" :model="infoForm" :rules="rules" label-width="150px">
	<el-form-item prop="machineName"  label="设备名称：">
	  <el-input v-model="infoForm.machineName" :disabled="isCheck"
    ></el-input>
	</el-form-item>
</el-form>
  <span slot="footer" class="dialog-footer">
      <el-button @click="addEditDialogVisible = false">关闭</el-button>
    <el-button type="primary" v-if="!isEdit && !isCheck"  @click="handleSubmitSave">确 定</el-button>
     <el-button type="primary" v-if="isEdit" @click="handleSubmitUpdate">更 新</el-button>
  </span>
</el-dialog>

</div>
</template>

<script type="text/ecmascript-6">
// Progress 進度條
import NProgress from "nprogress";
import {
  list,
  remove,
  save,
  update
} from "@/api/set/machine";

export default {
  components: {},
  data() {
    return {
      isCheck: false,
      query: {
        machineName: ""
      },
    addEditDialogVisible: false,
      dialogImageUrl: "",
      isEdit: false,
      page: {
        pageNumber: 1,
        pageSize: 10,
        total: 0
      },
      infoForm: {
      },
      rules: {
        machineName: [{ required: true, message: "请输入设备名称", trigger: "blur" }]
      },
      tableData: []
    };
  },
  created() {},
  mounted() {
    this.getList();
  },
        watch: {
    addEditDialogVisible: function(val, oldVla) {
      if (this.$refs["infoForm"] !== undefined) {
        this.$refs["infoForm"].resetFields();
      }
    }
  },
  computed: {
    headers() {
      return {
        xinyeSysUserSessionId: this.$store.state.token
      };
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
    formatDateJoinAt: function(row, column) {
      return this.$moment(row.joinAt).format("YYYY-MM-DD");
    },
    formatDateNextPayAt: function(row, column) {
      return this.$moment(row.nextPayAt).format("YYYY-MM-DD");
    },
    handleSearch: function() {
      this.getList();
    },
    handleAdd: function() {
      this.query.machineName = "";
      this.isEdit = false;
      this.isCheck = false;
      this.addEditDialogVisible = true;
      this.infoForm = Object.assign({}, null);
    },
    handleEdit: function(index, row) {
      this.isCheck = false;
      this.isEdit = true;
      this.infoForm = Object.assign({}, row);
      this.addEditDialogVisible = true;
    },
    handleCheck: function(index, row) {
      this.isCheck = true;
      this.isEdit = false;
      this.infoForm = Object.assign({}, row);
      this.addEditDialogVisible = true;
    },
    handleSubmitSave: function() {
      this.$refs.infoForm.validate(valid => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消"
          }).then(() => {
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
          this.$confirm("确认提交吗？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消"
          }).then(() => {
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
      this.$confirm("确定删除吗？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消"
          }).then(_ => {
          let id = row.machineId;
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
    },
    // handleUpShelf: function(index, row) {
    //   this.$confirm("確定上架嗎？", "提示", {
    //         confirmButtonText: "確定",
    //         cancelButtonText: "取消"
    //       }).then(_ => {
    //       let id = row.id;
    //       upShelf(id)
    //         .then(res => {
    //           this.infoForm.status = false;
    //           this.getList();
    //           this.$message.success(res.data.msg);
    //         })
    //         .catch(error => {
    //           this.$message.error(error);
    //         });
    //     })
    //     .catch(_ => {});
    // },
    // handleDownShelf: function(index, row) {
    //   this.$confirm("確定下架嗎？", "提示", {
    //         confirmButtonText: "確定",
    //         cancelButtonText: "取消"
    //       }).then(_ => {
    //       let id = row.id;
    //       downShelf(id)
    //         .then(res => {
    //           this.infoForm.status = false;
    //           this.getList();
    //           this.$message.success(res.data.msg);
    //         })
    //         .catch(error => {
    //           this.$message.error(error);
    //         });
    //     })
    //     .catch(_ => {});
    // },
    getList: function() {
      let params = {
        machineName: this.query.machineName,
        pageNumber: this.page.pageNumber,
        pageSize: this.page.pageSize
      };
      list(params)
        .then(res => {
          this.tableData = res.data.page.list;
          this.page.pageNumber = res.data.page.pageNumber;
          this.page.pageSize = res.data.page.pageSize;
          this.page.total = res.data.page.totalRow;
        })
        .catch(error => {
          this.$message.error(error);
          console.log(error);
        });
    }
  }
};
</script>

<style>
.school-item {
  margin-bottom: 1px;
}
.school-name {
  font-weight: 700;
}
</style>
