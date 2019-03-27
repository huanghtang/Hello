<template>
<div>
<!-- 搜索 -->
<el-form inline :model="query" label-position="right" label-width="70px" class="query-form">
     <el-select v-model="query.logoKindId" placeholder="全部分类">
        <el-option value="" label="全部"></el-option>
       <el-option v-for="item in kindList" :key="item.logoKindId" :label="item.logoKindName" :value="item.logoKindId"></el-option>
     </el-select>
   <el-input v-model="query.logoChildKindName" placeholder="请输入类名称" style="width:150px;"></el-input>
  <el-form-item>
    <el-button type="primary" @click="handleSearch" >搜索</el-button>
    <el-button type="primary" @click="handleAdd">新增分类</el-button>
  </el-form-item>
</el-form>
<!-- 數據表格 -->
<el-table :data="tableData"  class="table" stripe border>
  <el-table-column type="index" label="编号"  width="70"></el-table-column>
  <el-table-column prop="logoKindName" label="logo主类"></el-table-column>
  <el-table-column prop="logoChildKindName" label="logo子类"></el-table-column>
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
  <!-- logo主分类詳情 -->
 <el-form ref="infoForm" :model="infoForm" :rules="rules" label-width="150px">
   <el-form-item prop="logoKindId" label="logo主分类名称：">
     <el-select v-model="infoForm.logoKindId" placeholder="请选择主分类" :disabled="isCheck">
       <el-option v-for="item in kindList" :key="item.logoKindId" :label="item.logoKindName" :value="item.logoKindId"></el-option>
     </el-select>
   </el-form-item>
	<el-form-item prop="logoChildKindName"  label="logo子分类名称：">
	  <el-input v-model="infoForm.logoChildKindName" :disabled="isCheck"
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
// Progress 进度条
import NProgress from "nprogress";
import {
  list,
  remove,
  save,
  _getMainList,
  update
} from "@/api/baseData/child_kind";

export default {
  components: {},
  data() {
    return {
      isCheck: false,
      query: {
        logoChildKindName: "",
        logoKindId: ""
      },
    addEditDialogVisible: false,
      dialogImageUrl: "",
      isEdit: false,
      page: {
        pageNumber: 1,
        pageSize: 10,
        total: 0
      },
      kindList: [],
      infoForm: {},
      rules: {
        logoKindId: [{required: true, message: "请选择主类名称", trigger: "blur"}],
        logoChildKindName: [
          { required: true, message: "请输入子分类名称", trigger: "blur" },
          {
            pattern: /^[^`~!@#$%^&*()_\-+=<>?:"{}|,./;'\\[\]·~！@#￥%……&*（）——\-+={}|《》？：“”【】、；‘’，。、]/,
            message: "输入的名称不能包含特殊字符！"
          }
        ]
      },
      tableData: []
    };
  },
  created() {},
  mounted() {
    this.getList();
    this.getKindList();
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
    //获取logo主类
    getKindList: function() {
      _getMainList()
      .then(res => {
        this.kindList = res.data.list;
      })
      .catch(error => {
        this.$message.error(error);
        console.log(error);
      })
    },
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
      this.query.logoKindId = "";
      this.query.logoChildKindName = "";
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
      this.logoKindName = this.infoForm.logoKindName;
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
          let id = row.logoChildKindId;
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
        logoChildKindName: this.query.logoChildKindName,
        logoKindId: this.query.logoKindId,
        pageNumber: this.page.pageNumber,
        pageSize: this.page.pageSize
      };
      list(params)
        .then(res => {
          this.tableData = res.data.page.list;
          console.log(this.tableData);
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
