<template>
<div>
<!-- 搜索 -->
<el-form inline :model="query" label-position="right" label-width="70px" class="query-form">
       <el-select v-model="query.status" placeholder="登录状态">
       <el-option v-for="item in statusList" :key="item.value" :label="item.name" :value="item.value"></el-option>
     </el-select>
          <el-select v-model="query.registerType" placeholder="登录方式">
       <el-option v-for="item in loginStatusList" :key="item.value" :label="item.name" :value="item.value"></el-option>
     </el-select>
  <el-input v-model="query.userPhone" placeholder="请输入电话号码" style="width:150px;"></el-input>
  <el-form-item>
    <el-button type="primary" @click="handleSearch" >搜索</el-button>
  </el-form-item>
</el-form>
<!-- 数据表格 -->
<el-table :data="tableData" class="table" stripe border  >
    <el-table-column type="index" label="序号" width="70"></el-table-column>

    <el-table-column prop="userImg" label="头像" width="70">
      <template  slot-scope="scope">
          <img style="width:40px;height:40px;cursor: pointer;"  :src="scope.row.userImg" >
      </template>
    </el-table-column>
    <el-table-column prop="userPhone" label="用户电话" ></el-table-column>
    <el-table-column prop="registerType" label="登录方式" >
      <template  slot-scope="scope">
        <span v-if="scope.row.registerType === 0">qq登录</span>
        <span v-if="scope.row.registerType === 1">微信登录</span>
        <span v-if="scope.row.registerType === 2">手机注册</span>
      </template>
    </el-table-column>
    <el-table-column prop="status" label="状态">
      <template  slot-scope="scope">
        <span v-if="scope.row.status === 1">已注册</span>
        <span v-if="scope.row.status === 2">已登录</span>
      </template>
    </el-table-column>
    <el-table-column prop="lastLoginTime" label="登录时间" ></el-table-column>
    <el-table-column label="操作" width="190">
        <template slot-scope="scope">
          <el-button size="mini" type="info" @click="handleCheck(scope.$index, scope.row)">查看</el-button>
          <div style="margin-top: 8px">
            <el-button size="mini" type="danger" v-if="scope.row.carUserType===2" @click="handleCheck(scope.$index, scope.row)" v-has="'user_carUser_del'">删除</el-button>
          </div>
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


<!--新增頁面-->
<el-dialog title="查看" :visible.sync="addEditDialogVisible" width="50%" >
 <el-form ref="infoForm" :model="infoForm" :rules="rules" label-width="110px">
    <el-form-item prop="userImg" label="会员头像">
      <el-upload class="avatar-uploader"  :show-file-list="false" :disabled="isCheck">
        <img v-if="userImg" :src="userImg" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
    </el-form-item>
          <el-col :span="12"> 
         <el-form-item  prop="status" label="账号状态:">
        <el-select v-model="infoForm.status"  :disabled="isCheck" placeholder="账号状态:">
          <el-option  v-for="status in statusList" :key="status.value" :label="status.name" :value="status.value"></el-option>
        </el-select>
      </el-form-item>
          </el-col>
           <el-col :span="12"> 
            <el-form-item  prop="registerType" label="登录方式:">
        <el-select v-model="infoForm.registerType"  :disabled="isCheck" placeholder="登录方式:">
          <el-option  v-for="status in loginStatusList" :key="status.value" :label="status.name" :value="status.value"></el-option>
        </el-select>
      </el-form-item>
        </el-col>
          <el-row>
       <el-col :span="12">
	<el-form-item prop="userPhone" label="电话号码：">
	  <el-input v-model="infoForm.userPhone" :disabled="isCheck"></el-input>
	</el-form-item>
       </el-col>
        <el-col :span="12">
  	<el-form-item prop="userId" label="用户id：">
	  <el-input v-model="infoForm.userId" :disabled="isCheck"></el-input>
	</el-form-item>
        </el-col>
         <el-col :span="12">
  <el-form-item prop="lastLoginTime" label="登录时间：">
	  <el-input v-model="infoForm.lastLoginTime" :disabled="isCheck"></el-input>
	</el-form-item>
         </el-col>
      </el-row>
</el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="addEditDialogVisible = false">关闭</el-button>
  </span>
</el-dialog>

<el-dialog :visible.sync="dialogImgVisible" size="tiny">
  <img width="100%" :src="dialogImageUrl" alt="">
</el-dialog>

</div>
</template>

<script type="text/ecmascript-6">
// Progress 進度條
import {
  list
} from "@/api/baseData/user";

export default {
  components: {},
  data() {
    return {
      isCheck: false,
      query: {
        userPhone: "",
        status: "",
        registerType: ""
      },
      userImg: "",
      dialogImgVisible: false,
    addEditDialogVisible: false,
      dialogImageUrl: "",
      isEdit: false,
      page: {
        pageNumber: 1,
        pageSize: 10,
        total: 0
      },
      statusList:
      [{value: "",
       name: "全部"
        },
      { value: 1,
      name: '已注册'},
      { value: 2,
      name: '已登录'}
      ],
      loginStatusList:
      [{value: "",
       name: "全部"
        }, { value: 0,
      name: 'qq登录'},
      { value: 1,
      name: '微信登录'},
      { value: 2,
       name: '手机注册'}
      ],
      kindList: [],
      infoForm: {},
      rules: {
        logoKindId: [{required: true, message: "请选择主类名称", trigger: "blur"}],
        logoChildKindName: [{ required: true, message: "请输入子分类名称", trigger: "blur" }]
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
    handleSizeChange: function(val) {
      this.page.pageSize = val;
      this.getList();
    },
    handleCurrentChange: function(val) {
      this.page.pageNumber = val;
      this.getList();
    },
   handleCoverCardPreview: function(val) {
      this.dialogImageUrl = val;
      this.dialogImgVisible = true;
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
    handleCheck: function(index, row) {
      this.isCheck = true;
      this.isEdit = false;
      this.infoForm = Object.assign({}, row);
      this.userImg = this.infoForm.userImg;
      this.logoKindName = this.infoForm.logoKindName;
      this.addEditDialogVisible = true;
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
        userPhone: this.query.userPhone,
        status: this.query.status,
        registerType: this.query.registerType,
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
