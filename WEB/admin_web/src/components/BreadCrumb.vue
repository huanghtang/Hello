<template>

  <el-breadcrumb>
    <el-breadcrumb-item v-for="(item,index)  in levelList" :key="item.path">
      <span v-if='item.redirect==="noredirect" || index == levelList.length-1' class="no-redirect">{{item.name}} </span>
      <router-link v-else :to="item.redirect || item.path">{{item.name}} </router-link>
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script>
export default {
  created() {
    this.getBreadcrumb();
  },
  data() {
    return {
      levelList: null
    };
  },
  methods: {
    getBreadcrumb() {
      let matched = this.$route.matched.filter(item => item.name);
      const first = matched[0];
      if (first && first.name === "首页") {
        this.levelList = [{ name: "首页", path: "/admin" }];
      } else if (first && (first.name !== "首页" || first.path !== "")) {
        matched = [{ name: "首页", path: "/admin" }].concat(matched);
        this.levelList = matched;
      }
    }
  },
  watch: {
    $route() {
      this.getBreadcrumb();
    }
  }
};
</script>

<style>

</style>
