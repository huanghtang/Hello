<template>
  <div>
    <!-- 搜索 -->
    <el-form inline :model="query" label-position="right" label-width="70px" class="query-form">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">添加Banner</el-button>
      </el-form-item>
    </el-form>
    <!-- 数据表格 -->
    <el-table :data="tableData" class="table" stripe border width="600px">
      <el-table-column type="index" label="序号" width="70"></el-table-column>
      <el-table-column prop="cover" label="图片" width="70">
        <template slot-scope="scope">
          <img
            style="width:30px;height:30px;cursor: pointer;"
            :src="scope.row.cover"
            @click="handleCoverCardPreview(scope.row.cover)"
          >
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="上传时间" width="170"></el-table-column>
      <el-table-column label="操作" width="230">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleRemove(scope.$index, scope.row)">删除</el-button>
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
      :total="page.total"
    ></el-pagination>

    <el-dialog title="新增/编辑Banner" :visible.sync="addEditDialogVisible" width="600px">
      <el-form ref="infoForm" :model="infoForm" :rules="rules" label-width="80px">
        <el-form-item prop="cover" label="图片：">
          <el-upload
            class="avatar-uploader"
            :action="uploadUrl"
            :show-file-list="false"
            :on-success="handleCoverSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="cover" :src="cover" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addEditDialogVisible = false">关闭</el-button>
        <el-button type="primary" v-if="isEdit" @click="handleSubmitUpdate">提 交</el-button>
        <el-button type="primary" v-else @click="handleSubmitSave">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog :visible.sync="dialogImgVisible" size="tiny">
      <img width="100%" :src="dialogImageUrl" alt>
    </el-dialog>
  </div>
</template>

<script type="text/ecmascript-6">
// Progress 进度条
import { list, remove, save, update } from "@/api/baseData/banner";

export default {
  data() {
    var notBlank = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请上传banner图片"));
      } else if (value === undefined) {
        if (this.infoForm.cover === "" || this.infoForm.cover === undefined) {
          callback(new Error("请上传banner图片"));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    return {
      uploadUrl: this.GLOBAL.prefix + "/admin/upload/uploadImg",
      query: {
        title: ""
      },
      addEditDialogVisible: false,
      dialogImgVisible: false,
      dialogImageUrl: "",
      isEdit: false,
      page: {
        pageNumber: 1,
        pageSize: 10,
        total: 0
      },
      cover: "",
      infoForm: {},
      rules: {
        cover: [{ required: true, validator: notBlank, trigger: "change" }]
      },
      tableData: []
    };
  },
  created() {},
  mounted() {
    this.getList();
  },
  methods: {
    handleSizeChange: function(val) {
      this.page.pageSize = val;
      this.getList();
    },
    handleCoverCardPreview: function(val) {
      this.dialogImageUrl = val;
      this.dialogImgVisible = true;
    },
    handleCurrentChange: function(val) {
      this.pageNumber = val;
      this.getList();
    },
    handleSelectChange: function(val) {
      //console.log(this.infoForm);
    },
    listenCoverUploadSuccess: function(imgUrl) {
      this.infoForm.cover = imgUrl;
    },
    handleSearch: function() {
      this.getList();
    },
    handleAdd: function() {
      this.isEdit = false;
      this.cover = "";
      this.addEditDialogVisible = true;
      this.infoForm = Object.assign({}, null);
    },
    handleEdit: function(index, row) {
      this.isEdit = true;
      this.infoForm = Object.assign({}, row);
      this.cover = this.infoForm.cover;
      this.addEditDialogVisible = true;
    },
    handleSubmitSave: function() {
      this.$refs.infoForm.validate(valid => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            let para = Object.assign({}, this.infoForm);
            save(para).then(res => {
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
            let para = Object.assign({}, this.infoForm);
            update(para).then(res => {
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
          let id = row.id;
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
    handleCoverSuccess: function(res, file) {
      if (res.code !== "200") {
        this.$message.error(res.msg);
      } else {
        this.cover = res.file.url;
        this.infoForm.cover = res.file.url;
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
    getList: function() {
      let params = {
        //title: this.query.title,
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
</style>
