<template>
	<div class="wrapper">
		<!-- 頂部頭部 -->
		<HeaderItem></HeaderItem>
    <!-- 中間區域 -->
		<el-container class="main">
      <!-- 左側菜單欄 -->
			<el-aside class="menu">
				<MenuItems></MenuItems>
			</el-aside>
      <!-- 展示頁-->
			<el-main class="content">
        <!-- 麵包屑導航 -->
				<BreadCrumb></BreadCrumb>

        <!-- 展示頁面 -->
				<router-view></router-view>

			</el-main> <!-- 展示頁 結束-->
		</el-container><!-- 中間區域 -->
	</div>
</template>

<script>
import HeaderItem from "./HeaderItem";
import MenuItems from "./MenuItems";
import BreadCrumb from "./BreadCrumb";
export default {
  components: {
    HeaderItem,
    MenuItems,
    BreadCrumb
  },
  created() {},
  mounted() {},
  methods: {}
};
</script>

<style>

</style>
