<template>
<div>
<!-- 搜索 -->
<el-form inline :model="query" label-position="right" label-width="70px" class="query-form">
  <el-input v-model="query.fontName" placeholder="请输入字体名称" style="width:150px;"></el-input>
  <el-form-item>
    <el-button type="primary" @click="handleSearch" >搜索</el-button>
    <el-button type="primary" @click="handleAdd">新增分类</el-button>
  </el-form-item>
</el-form>
<!-- 數據表格 -->
<el-table :data="tableData"  class="table" stripe border>
  <el-table-column type="index" label="编号"  width="70"></el-table-column>
  <el-table-column prop="fontName" label="字体名称"></el-table-column>
  <el-table-column prop="fontKind" label="字体标识"></el-table-column>
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
  <!-- 字体詳情 -->
 <el-form ref="infoForm" :model="infoForm" :rules="rules" label-width="150px">
   <el-form-item prop="fontName" label="字体名称：">
    <el-input v-model="infoForm.fontName" :disabled="isCheck"></el-input>
   </el-form-item>
      <el-form-item prop="fontKind" label="字体标识">
       <el-input v-model="infoForm.fontKind" :disabled="isCheck"></el-input>
   </el-form-item>
  <el-form-item prop="fontUrl" label="字体上传" >
        <el-upload
          class="upload-demo"
          :action="uploadUrl"
          :on-success="handleFontSuccess"
          :before-upload="beforeUploadFont"
          :limit="1"
          ref='upload'
          :file-list="fontFileList">
          <el-button size="small" type="primary" v-if="isCheck == false" @click="handleUpload">点击上传</el-button>
          <div slot="tip" class="el-upload__tip" v-if="isCheck == false">只能上传字体文件，且不超过20M</div>
        </el-upload>
           <el-input v-model="infoForm.fontUrl" :disabled="true" v-if="isShow == true">{{fontUrl}}</el-input>
    </el-form-item>
</el-form>
  <span slot="footer" class="dialog-footer">
      <el-button @click="addEditDialogVisible = false">关闭</el-button>
    <el-button type="primary" v-if="!isEdit && !isCheck"  @click="handleSubmitSave">确 定</el-button>
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
  remove,
  save,
  update
} from "@/api/set/font";

export default {
  components: {},
  data() {
        var fontBlank = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请上传字体文件"));
      } else if (value === undefined) {
        if (
          this.infoForm.fontUrl === "" ||
          this.infoForm.fontUrl === undefined
        ) {
          callback(new Error("请上传字体文件"));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    return {
      isShow: true,
      isCheck: false,
      query: {
          fontName: ""
      },
      uploadUrl: this.GLOBAL.prefix + "/admin/upload/uploadFont",
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
      fontUrl: "",
      fontFileList: [],
      kindList: [], //主分类列表
      childList: [], //子分类列表
      infoForm: {
          fontUrl: ""
      },
      rules: {
        fontKind: [{required: true, message: "请输入字体标识", trigger: "blur"}],
        fontName: [{ required: true, message: "请输入字体名称", trigger: "blur" }],
                fontUrl: [
          { required: true, validator: fontBlank, trigger: "change" }
        ]
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
      this.query.fontName = "";
      this.fontFileList = [];
      this.isShow = false;
      this.isEdit = false;
      this.isCheck = false;
      this.addEditDialogVisible = true;
      this.infoForm = Object.assign({}, null);
    },
    handleEdit: function(index, row) {
      this.isCheck = false;
      this.isShow = true;
      this.isEdit = true;
      this.fontFileList = [];
      this.infoForm = Object.assign({}, row);
      this.addEditDialogVisible = true;
    },
    handleCheck: function(index, row) {
      this.isCheck = true;
      this.isEdit = false;
      this.isShow = true;
      this.fontFileList = [];
      this.infoForm = Object.assign({}, row);
      this.addEditDialogVisible = true;
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
    handleUpload: function() {
      this.$refs.upload.clearFiles();
    },
    handleRemove: function(index, row) {
      this.$confirm("确定删除吗？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消"
          }).then(_ => {
          let id = row.fontId;
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
    //字体上传
    handleFontSuccess: function(res, file) {
      if (res.code !== "200") {
        this.$message.error(res.msg);
      } else {
        this.infoForm.fontUrl = res.file.url;
        this.fontUrl = this.infoForm.fontUrl;
      }
    },
        //上传字体前
    beforeUploadFont(file) {
        let extension = file.name.substring(file.name.lastIndexOf('.') + 1);
        console.log(file.name.lastIndexOf('.'));
      const isLt20M = file.size / 1024 / 1024 < 30;
      if (extension !== 'ttf' && extension !== 'TTF') {
        this.$message.error("请上传正确的字体格式");
        return false;
      }
      if (!isLt20M) {
        this.$message.error("上传文件大小不能超过30MB哦!");
        return false;
      }
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
        fontName: this.query.fontName,
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
