<template>
  <div>
    <!-- 搜索 -->
    <el-form inline :model="query" label-position="right" label-width="70px" class="query-form">
      <el-select v-model="query.logoKindId" @change="selectMainChange" placeholder="全部分类">
        <el-option value label="全部"></el-option>
        <el-option
          v-for="item in kindList"
          :key="item.logoKindId"
          :label="item.logoKindName"
          :value="item.logoKindId"
        ></el-option>
      </el-select>
      <el-select v-model="query.logoChildKindId" placeholder="请选择子分类">
        <el-option value label="全部"></el-option>
        <el-option
          v-for="item in childList"
          :key="item.logoChildKindId"
          :label="item.logoChildKindName"
          :value="item.logoChildKindId"
        ></el-option>
      </el-select>
      <el-form-item>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
        <el-button type="primary" @click="handleAdd">新增LOGO</el-button>
      </el-form-item>
    </el-form>
    <!-- 數據表格 -->
    <el-table :data="tableData" class="table" stripe border>
      <el-table-column type="index" label="编号" width="70"></el-table-column>
      <el-table-column prop="logoKindName" label="logo主类"></el-table-column>
      <el-table-column prop="logoChildKindName" label="logo子类"></el-table-column>
      <el-table-column prop="logoImg" label="logo图标" width="100">
        <template slot-scope="scope">
          <img
            style="width:30px;height:30px;cursor: pointer;"
            :src="scope.row.logoImg"
            @click="handleCoverCardPreview(scope.row.logoImg)"
          >
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="添加时间"></el-table-column>
      <el-table-column prop="sysUserName" label="操作人"></el-table-column>
      <el-table-column label="操作" width="230">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleCheck(scope.$index, scope.row)">查看</el-button>
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
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
      :total="page.total"
    ></el-pagination>

    <el-dialog title="新增/编辑/查看" :visible.sync="addEditDialogVisible" width="800px">
      <el-form ref="infoForm" :model="infoForm" :rules="rules" label-width="150px">
        <el-form-item prop="logoKindId" label="logo主分类名称：">
          <el-select
            v-model="infoForm.logoKindId"
            @change="selectMainChange"
            placeholder="请选择主分类"
            :disabled="isCheck"
          >
            <el-option
              v-for="item in kindList"
              :key="item.logoKindId"
              :label="item.logoKindName"
              :value="item.logoKindId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="logoChildKindId" label="logo子分类名称：">
          <el-select v-model="infoForm.logoChildKindId" placeholder="请选择子分类" :disabled="isCheck">
            <el-option
              v-for="item in childList"
              :key="item.logoChildKindId"
              :label="item.logoChildKindName"
              :value="item.logoChildKindId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="logoImg" label="logo图标：">
          <el-upload
            class="avatar-uploader"
            :action="uploadUrl"
            :show-file-list="false"
            :on-success="handleCoverSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="logoImg" :src="logoImg" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addEditDialogVisible = false">关闭</el-button>
        <el-button type="primary" v-if="!isEdit && !isCheck" @click="handleSubmitSave">确 定</el-button>
        <el-button type="primary" v-if="isEdit" @click="handleSubmitUpdate">更 新</el-button>
      </span>
    </el-dialog>

    <!--弹出图片-->
    <el-dialog :visible.sync="dialogImgVisible" size="tiny">
      <img width="100%" :src="dialogImageUrl" alt>
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
  _getChildList,
  update
} from "@/api/baseData/logo";

export default {
  components: {},
  data() {
    var notBlank = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请上传logo图片"));
      } else if (value === undefined) {
        if (
          this.infoForm.logoImg === "" ||
          this.infoForm.logoImg === undefined
        ) {
          callback(new Error("请上传logo图片"));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    return {
      isCheck: false,
      query: {
        logoChildKindId: "",
        logoKindId: ""
      },
      uploadUrl: this.GLOBAL.prefix + "/admin/upload/uploadImg",
      addEditDialogVisible: false,
      dialogImageUrl: "",
      dialogImgVisible: false,
      isEdit: false,
      logoImg: "",
      page: {
        pageNumber: 1,
        pageSize: 10,
        total: 0
      },
      kindList: [], //主分类列表
      childList: [], //子分类列表
      infoForm: {},
      rules: {
        logoKindId: [
          { required: true, message: "请选择主类名称", trigger: "blur" }
        ],
        logoImg: [{ required: true, validator: notBlank, trigger: "change" }],
        logoChildKindId: [
          { required: true, message: "请输入子分类名称", trigger: "blur" }
        ],
        logoChildKindName: [
          { required: true, message: "请输入子分类名称", trigger: "blur" }
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
  computed: {
    headers() {
      return {
        xinyeSysUserSessionId: this.$store.state.token
      };
    }
  },
  watch: {
    addEditDialogVisible: function(val, oldVla) {
      if (this.$refs["infoForm"] !== undefined) {
        this.$refs["infoForm"].resetFields();
      }
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
        });
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
      this.query.logoChildKindId = "";
      this.logoImg = "";
      this.isEdit = false;
      this.isCheck = false;
      this.addEditDialogVisible = true;
      this.infoForm = Object.assign({}, null);
    },
    handleEdit: function(index, row) {
      this.isCheck = false;
      this.isEdit = true;
      this.infoForm = Object.assign({}, row);
      this.getChildList(this.infoForm.logoKindId);
      this.logoImg = this.infoForm.logoImg;
      this.addEditDialogVisible = true;
    },
    handleCheck: function(index, row) {
      this.isCheck = true;
      this.isEdit = false;
      this.infoForm = Object.assign({}, row);
      this.getChildList(this.infoForm.logoKindId);
      this.logoKindName = this.infoForm.logoKindName;
      this.logoImg = this.infoForm.logoImg;
      this.addEditDialogVisible = true;
    },
    //获得子分类列表
    getChildList: function(val) {
      _getChildList(val)
        .then(res => {
          this.childList = res.data.list;
        })
        .catch(error => {
          this.$message.error(error);
          console.log(error);
        });
    },
    selectMainChange: function(val) {
      this.getChildList(val);
      if (this.isEdit === true) {
        this.infoForm.logoChildKindId = "";
        this.getChildList(val);
      }
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
      })
        .then(_ => {
          let id = row.logoId;
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
    handleCoverCardPreview: function(val) {
      this.dialogImageUrl = val;
      this.dialogImgVisible = true;
    },
    handleCoverSuccess: function(res, file) {
      if (res.code !== "200") {
        this.$message.error(res.msg);
      } else {
        this.logoImg = res.file.url;
        this.infoForm.logoImg = res.file.url;
      }
    },
    //上传图片前
    beforeAvatarUpload: function(file) {
      const isJPG = file.type === "image/jpeg" || file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 20;
      if (!isJPG) {
        this.$message.error("上传图片片只能是 JPG|PNG|IMAGE|JPEG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传图片大小不能超过 20MB!");
      }
      if (isJPG && isLt2M) {
      }
      return isJPG && isLt2M;
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
        logoChildKindId: this.query.logoChildKindId,
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
