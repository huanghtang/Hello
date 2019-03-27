<template>
  <div class="login_body">
    <el-form
      :model="loginForm"
      :rules="rules2"
      ref="loginForm"
      label-position="left"
      label-width="0px"
      class="demo-ruleForm login-container"
    >
      <h3 class="title">系统登录</h3>
      <el-form-item prop="username">
        <el-input
          type="text"
          v-model="loginForm.username"
          auto-complete="off"
          placeholder="账号"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          v-model="loginForm.password"
          auto-complete="off"
          placeholder="密码"
        ></el-input>
      </el-form-item>
      <!-- <el-checkbox v-model="checked" checked class="remember">記住密碼</el-checkbox> -->
      <el-form-item style="width:100%;">
        <el-button
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
          :loading="logining"
        >登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { login } from "@/api/login";
export default {
  data() {
    return {
      logining: false,
      dialogVisible: false,
      loginForm: {
        username: "",
        password: ""
      },
      rules2: {
        username: [{ required: true, message: "请输入账号", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      },
      checked: true
    };
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.logining = true;
          let loginParams = {
            username: this.loginForm.username,
            password: this.loginForm.password
          };
          login(loginParams)
            .then(res => {
              const account = res.data.sysUser;
              // this.$store.dispatch("login", { account }).then(() => {
              //   // 生成可访问的路由表
              //   this.$router.push({ path: "/admin" }); //登录成功之后重定向到首页
              // });
              // console.log(account.menus);
              this.$store.dispatch('login', { account }).then(() => { // 生成可訪問的路由表
                  const menus = account.menus;
                  this.$store.dispatch('generateRoutes', { menus }).then(() => { // 生成可訪問的路由表
                      this.$router.addRoutes(this.$store.state.addRouters) // 動態添加可訪問路由表
                      this.$router.push({ path: "/admin" }); //登錄成功之後重定向到首頁
                  })
              })
            })
            .catch(error => {
              console.log(error);
              this.$message.error(error);
              this.logining = false;
            });
        }
      });
    }
  }
};
</script>

<style>
.login-container {
  -webkit-border-radius: 5px;
  border-radius: 5px;
  -moz-border-radius: 5px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

.remember {
  margin: 0px 0px 35px 0px;
}
.code {
  width: 400px;
}
</style>
