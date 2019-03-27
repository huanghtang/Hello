<template>
  <el-menu
    :default-active="$route.path"
    unique-opened
  >
    <template v-for="item in routers">
      <router-link
        v-if="!item.hidden && item.noDropdown && item.children.length > 0"
        :to="item.path+'/'+item.children[0].path"
      >
        <el-menu-item :index="item.path+'/'+item.children[0].path">
          <i :class="item.icon"></i><span>{{item.children[0].name}}</span>
        </el-menu-item>
      </router-link>
      <el-submenu
        :index="item.name"
        v-if="!item.noDropdown && !item.hidden"
      >
        <template slot="title">
          <i :class="item.icon"></i><span>{{item.name}}</span>
        </template>
        <template
          v-for="child in item.children"
          v-if='!child.hidden'
        >
          <sidebar-item
            v-if='child.children && child.children.length>0'
            :routes='[child]'
          > </sidebar-item>
          <router-link
            v-else
            :to="item.path+'/'+child.path"
          >
            <el-menu-item :index="item.path+'/'+child.path">
              <i :class="child.icon"></i><span>{{child.name}}</span>
            </el-menu-item>
          </router-link>
        </template>
      </el-submenu>
    </template>
  </el-menu>
</template>

<script type="text/ecmascript-6">
// import { constantRouterMap } from "@/router";

import { mapState } from "vuex";
export default {
  name: "MenuItems",
  computed: {
    ...mapState(["routers"])
  }
};

// export default {
//   data() {
//     return {
//       constantRouterMap: constantRouterMap
//     };
//   }
// };
</script>

<style>
</style>
