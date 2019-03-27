<template>
<div>
<!-- 數據表格 -->
<el-table :data="tableData"  class="table" stripe border>
  <el-table-column type="index" label="编号"  width="70"></el-table-column>
          <el-table-column prop="aboutImg" label="图片" width="100">
      <template  slot-scope="scope">
          <img style="width:30px;height:30px;cursor: pointer;"  :src="scope.row.aboutImg" @click="handleCoverCardPreview(scope.row.aboutImg)">
      </template>
    </el-table-column>
    <el-table-column prop="createTime" label="添加时间"></el-table-column>
      <el-table-column prop="sysUserName" label="操作人"></el-table-column>
  <el-table-column label="操作" width="230" >
    <template slot-scope="scope">
           <el-button size="mini" type="primary" @click="handleCheck(scope.$index, scope.row)">查看</el-button>
      <el-button size="mini"  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
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


<el-dialog title="编辑" :visible.sync="addEditDialogVisible" width="800px" >
  <!-- logo主分类詳情 -->
 <el-form ref="infoForm" :model="infoForm" :rules="rules" label-width="150px">
    <el-form-item prop="aboutImg" label="logo图标：" :disabled="isCheck" >
      <el-upload class="avatar-uploader" :action="uploadUrl"  :show-file-list="false" :on-success="handleCoverSuccess" :before-upload="beforeAvatarUpload">
        <img v-if="aboutImg" :src="aboutImg" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
  </el-form-item>

</el-form>
  <span slot="footer" class="dialog-footer">
      <el-button @click="addEditDialogVisible = false">关闭</el-button>
     <el-button type="primary" v-if="isEdit" @click="handleSubmitUpdate">更 新</el-button>
  </span>
</el-dialog>

<!--弹出图片-->
  <el-dialog :visible.sync="dialogImgVisible" size="tiny">
    <img width="100%" :src="dialogImageUrl" alt="">
  </el-dialog>
</div>
</template>

<script type="text/ecmascript-6">
// Progress 進度條
import NProgress from "nprogress";
import {
  list,
  update
} from "@/api/set/about";

export default {
  components: {},
  data() {
    return {
      isShow: true,
      isCheck: false,
      uploadUrl: this.GLOBAL.prefix + "/admin/upload/uploadImg",
    addEditDialogVisible: false,
      dialogImageUrl: "",
      dialogImgVisible: false,
      isEdit: false,
      aboutImg: "",
      page: {
        pageNumber: 1,
        pageSize: 10,
        total: 0
      },
      infoForm: {
      },
      rules: {
        fontKind: [{required: true, message: "请输入字体标识", trigger: "blur"}],
        fontName: [{ required: true, message: "请输入字体名称", trigger: "blur" }]
      },
      tableData: []
    };
  },
  created() {},
  mounted() {
    this.getList();
  },
  computed: {
    headers() {
      return {
        xinyeSysUserSessionId: this.$store.state.token
      };
    }
  },
  methods: {
     handleCoverSuccess: function(res, file) {
      if (res.code !== "200") {
        this.$message.error(res.msg);
      } else {
        this.aboutImg = res.file.url;
        this.infoForm.aboutImg = res.file.url;
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
      handleUpload: function() {
          this.infoForm.fontUrl = ""
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
    handleCoverCardPreview: function(val) {
      this.dialogImageUrl = val;
      this.dialogImgVisible = true;
    },
    handleSearch: function() {
      this.getList();
    },
    handleEdit: function(index, row) {
      this.isCheck = false;
      this.isShow = true;
      this.isEdit = true;
      this.infoForm = Object.assign({}, row);
      this.aboutImg = this.infoForm.aboutImg;
      this.addEditDialogVisible = true;
    },
    handleCheck: function(index, row) {
      this.isCheck = true;
      this.isEdit = false;
      this.isShow = true;
      this.fontFileList = [];
      this.infoForm = Object.assign({}, row);
      this.aboutImg = this.infoForm.aboutImg;
      this.addEditDialogVisible = true;
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
