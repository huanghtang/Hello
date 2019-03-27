<template>
<div>
<!-- 數據表格 -->
<el-table :data="tableData"  class="table" stripe border>
    <el-table-column prop="sysUserName" label="操作人"></el-table-column>
    <el-table-column prop="ip" label="IP地址"></el-table-column>
 <el-table-column prop="content" label="操作内容"></el-table-column>
    <el-table-column prop="createTime" label="操作时间"></el-table-column>
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

</div>
</template>

<script type="text/ecmascript-6">
// Progress 進度條
import {
  list
} from "@/api/operate/operation";

export default {
  components: {},
  data() {
    return {
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
