<template>
<div>
<!-- 搜索 -->
<el-form inline :model="query" label-position="right" label-width="70px" class="query-form">
      <el-select v-model="query.helpKind" placeholder="请选择帮助类型" >
        <el-option value="" label="全部"></el-option>
       <el-option v-for="item in helpKindList" :key="item.value" :label="item.name" :value="item.value"></el-option>
     </el-select>
          <el-select v-model="query.machineId" placeholder="请选择设备型号" >
            <el-option value="" label="全部"></el-option>
       <el-option v-for="item in machineList" :key="item.machineId" :label="item.machineName" :value="item.machineId"></el-option>
     </el-select>

  <el-input v-model="query.helpName" placeholder="请输入字体名称" style="width:150px;"></el-input>
  <el-form-item>
    <el-button type="primary" @click="handleSearch" >搜索</el-button>
    <el-button type="primary" @click="handleAdd">新增帮助</el-button>
  </el-form-item>
</el-form>
<!-- 數據表格 -->
<el-table :data="tableData"  class="table" stripe border>
  <el-table-column type="index" label="编号"  width="70"></el-table-column>
  <el-table-column prop="helpVideo" label="视频链接" :show-overflow-tooltip="true"></el-table-column>
  <el-table-column prop="machineName" label="设备类型"></el-table-column>
  <el-table-column prop="helpKind" label="帮助类型">
        <template  slot-scope="scope">
        <span v-if="scope.row.helpKind === 1">软件使用帮助</span>
        <span v-if="scope.row.helpKind === 2">硬件使用帮助</span>
      </template>
  </el-table-column>
  <el-table-column prop="helpName" label="帮助名称"></el-table-column>
        <el-table-column prop="helpLogo" label="帮助图标" width="100">
      <template  slot-scope="scope">
          <img style="width:30px;height:30px;cursor: pointer;"  :src="scope.row.helpLogo" @click="handleCoverCardPreview(scope.row.helpLogo)">
      </template>
    </el-table-column>
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
 <el-form ref="infoForm"  :model="infoForm" :rules="rules" label-width="150px">
       <el-form-item label="帮助视频上传" prop="helpVideo" >
        <el-upload
          class="upload-demo"
          :action="uploadVideoUrl"
          :on-success="handleVideoSuccess"
          :before-upload="beforeUploadVideo"
          ref='upload'
          :limit="1"
          :file-list="helpFileList">
          <el-button size="small" type="primary" v-if="isCheck == false" @click="handleUpload">点击上传</el-button>
          <div slot="tip" class="el-upload__tip" v-if="isCheck == false">只能上传视频文件，且不超过20M</div>
        </el-upload>
    <el-input v-model="infoForm.helpVideo" :disabled="true" v-if="isShow == true"></el-input>
   </el-form-item>
     <el-form-item prop="machineId" label="设备型号：" >
          <el-select v-model="infoForm.machineId" placeholder="请选择设备型号" :disabled="isCheck">
       <el-option v-for="item in machineList" :key="item.machineId" :label="item.machineName" :value="item.machineId"></el-option>
     </el-select>
     </el-form-item>
       <el-form-item prop="helpKind" label="帮助类型：">
            <el-select v-model="infoForm.helpKind" placeholder="请选择帮助类型" :disabled="isCheck">
       <el-option v-for="item in helpKindList" :key="item.value" :label="item.name" :value="item.value"></el-option>
     </el-select>
       </el-form-item>
      <el-form-item prop="helpName" label="帮助名称:">
       <el-input v-model="infoForm.helpName" :disabled="isCheck"></el-input>
   </el-form-item>
    <el-form-item prop="helpLogo" label="帮助图标：" :disabled="isCheck" >
      <el-upload class="avatar-uploader" :action="uploadUrl"  :show-file-list="false" :on-success="handleCoverSuccess" :before-upload="beforeAvatarUpload">
        <img v-if="helpLogo" :src="helpLogo" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
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
  _getMachineList,
  update
} from "@/api/set/help";

export default {
  components: {},
  data() {
            var helpBlank = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请上传帮助视频"));
      } else if (value === undefined) {
        if (
          this.infoForm.helpVideo === "" ||
          this.infoForm.helpVideo === undefined
        ) {
          callback(new Error("请上传帮助视频"));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
                var helpImg = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请上传帮助图标"));
      } else if (value === undefined) {
        if (
          this.infoForm.helpLogo === "" ||
          this.infoForm.helpLogo === undefined
        ) {
          callback(new Error("请上传帮助图标"));
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
      helpVideo: "",
      query: {
          helpName: ""
      },
      //设备列表
      machineList: [],
      //帮助类型列表
       helpKindList:
      [{ value: 1,
      name: '软件使用帮助'},
      { value: 2,
      name: '硬件使用帮助'}
      ],
      uploadUrl: this.GLOBAL.prefix + "/admin/upload/uploadImg",
     uploadVideoUrl: this.GLOBAL.prefix + "/admin/upload/uploadVideo",
    addEditDialogVisible: false,
      dialogImageUrl: "",
      dialogImgVisible: false,
      isEdit: false,
      helpLogo: "",
      page: {
        pageNumber: 1,
        pageSize: 10,
        total: 0
      },
      fontUrl: "",
      helpFileList: [],
      kindList: [], //主分类列表
      childList: [], //子分类列表
      infoForm: {
          fontUrl: ""
      },
      rules: {
        helpKind: [{required: true, message: "请选择帮助类型", trigger: "blur"}],
        machineId: [{ required: true, message: "请选择设备型号", trigger: "blur" }],
        helpName: [{ required: true, message: "请输入帮助名称", trigger: "blur" }],
        helpVideo: [{ required: true, validator: helpBlank, trigger: "change" }],
        helpLogo: [{ required: true, validator: helpImg, trigger: "change" }]
      },
      tableData: []
    };
  },
  created() {},
  mounted() {
    this.getList();
    this.getMachineist();
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
          //获取machine主类
    getMachineist: function() {
      _getMachineList()
      .then(res => {
        this.machineList = res.data.list;
      })
      .catch(error => {
        this.$message.error(error);
        console.log(error);
      })
    },
     handleCoverSuccess: function(res, file) {
      if (res.code !== "200") {
        this.$message.error(res.msg);
      } else {
        this.helpLogo = res.file.url;
        this.infoForm.helpLogo = res.file.url;
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
    //点击上传时候清除文件列表
    handleUpload: function() {
      this.$refs.upload.clearFiles();
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
    handleAdd: function() {
      this.query.helpName = "";
      this.query.helpKind = "";
      this.query.MachineId = "";
      this.helpFileList = [];
      this.helpLogo = "";
      this.isShow = false;
      this.isEdit = false;
      this.isCheck = false;
      this.infoForm = Object.assign({}, null);
      this.addEditDialogVisible = true;
    },
    handleEdit: function(index, row) {
      this.isCheck = false;
      this.isShow = true;
      this.isEdit = true;
      this.helpFileList = [];
      this.infoForm = Object.assign({}, row);
      this.helpLogo = this.infoForm.helpLogo;
      this.addEditDialogVisible = true;
    },
    handleCheck: function(index, row) {
      this.isCheck = true;
      this.isEdit = false;
      this.isShow = true;
      this.helpFileList = [];
      this.infoForm = Object.assign({}, row);
      this.helpLogo = this.infoForm.helpLogo;
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
          let id = row.helpId;
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
    handleVideoSuccess: function(res, file) {
      if (res.code !== "200") {
        this.$message.error(res.msg);
      } else {
        this.infoForm.helpVideo = res.file.url;
        this.helpVideo = this.infoForm.helpVideo;
      }
    },
        //上传视频前
    beforeUploadVideo(file) {
      const isLt20M = file.size / 1024 / 1024 < 20;
      if (
        [
          "video/mp4",
          "video/ogg",
          "video/flv",
          "video/avi",
          "video/wmv",
          "video/rmvb"
        ].indexOf(file.type) === -1
      ) {
        this.$message.error("请上传正确的视频格式");
        return false;
      }
      if (!isLt20M) {
        this.$message.error("上传视频大小不能超过20MB哦!");
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
        helpName: this.query.helpName,
        machineId: this.query.machineId,
        helpKind: this.query.helpKind,
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
