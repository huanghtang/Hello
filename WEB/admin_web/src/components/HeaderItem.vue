<template>
<el-header height="80px" :style="{ 'background-color':'#5589B1' }">
     <img
        src="../assets/element-logo.svg"
        alt="element-logo"
        class="header-logo">
		<ul class="header-operations">
       <li >欢迎您，{{this.$store.state.name}}</li>
       <li @click="dialogVisible = true">修改密码</li>
       <li @click="hanleLogOut">退出</li>
			<!-- <li @click="showThemeDialog">切換主題色</li> -->
		</ul>

    <el-dialog
  title="提示"
  :visible.sync="dialogVisible"
  width="30%"
  >

  <el-form :model="infoForm" status-icon :rules="rules2" ref="infoForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="原密码：" prop="oldPassword">
    <el-input type="password" v-model="infoForm.oldPassword" auto-complete="off"></el-input>
  </el-form-item>
  <el-form-item label="新密码：" prop="password">
    <el-input type="password" v-model="infoForm.password" auto-complete="off"></el-input>
  </el-form-item>
  <el-form-item label="确认新密码：" prop="checkPass">
    <el-input type="password" v-model="infoForm.checkPass" auto-complete="off"></el-input>
  </el-form-item>
 
  <el-form-item>
    <el-button type="primary" @click="submitForm('infoForm')">提交</el-button>
    <el-button @click="resetForm('infoForm')">重置</el-button>
  </el-form-item>
</el-form>

  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
</el-dialog>

</el-header>
</template>

<script type="text/ecmascript-6">
import { logout } from "@/api/login";
import { removeToken } from "@/utils/token";
import { resetPassword } from "@/api/my";
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.infoForm.checkPass !== "") {
          this.$refs.infoForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请在此输入密码"));
      } else if (value !== this.infoForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      primaryColor: "#5589B1",
      dialogVisible: false,
      infoForm: {
        password: "",
        oldPassword: "",
        checkPass: ""
      },
      rules2: {
        oldPassword: [{ validator: validatePass, trigger: "blur" }],
        password: [
          {
            min: 5,
            max: 18,
            message: "長度在 5 到 18 個字符",
            trigger: "blur"
          },
          { validator: validatePass, trigger: "blur" }
        ],
        checkPass: [
          {
            min: 5,
            max: 18,
            message: "長度在 5 到 18 個字符",
            trigger: "blur"
          },
          { validator: validatePass2, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    submitForm: function(formName) {
      var _this = this;
      this.$refs[formName].validate(valid => {
        if (valid) {
          let params = Object.assign({}, this.infoForm);
          resetPassword(params)
            .then(res => {
              this.$message.success(res.data.msg);
              logout();
              _this.$router.push({ path: "/admin/login" });
            })
            .catch(error => {
              this.$message.error(error);
              console.log(error);
            });
        } else {
          return false;
        }
      });
    },
    resetForm: function(formName) {
      this.$refs[formName].resetFields();
    },
    hanleLogOut: function() {
      var _this = this;
      this.$confirm("确认退出吗", "提示", {}).then(_res => {
        logout().then(res => {
          if (res.data.success) {
            this.$store.commit("logout");
            _this.$router.push({ path: "/admin/login" });
            removeToken();
          }
        });
      });
    }
  }
};
</script>

<style>
</style>
