<template>

  <div class="dashboard">

        <el-card class="box-card">
          <div
            slot="header"
            class="clearfix"
          >
            <span>
              <h1>昨日核心指标</h1>
            </span>
          </div>
          <div id="wrap">
            <div id="div1">
              <span text-align:center>新注册人数</span>
              <br><br>
              <span style="font-size:130px">{{form.newUserNumber}}</span>
            </div>
            <div id="div2">
              <span>昨日登录人数</span>
              <br><br>
              <span style="font-size:130px">{{form.loginUserNumber}}</span>
            </div>
            <div id="div3">
              <span>累计注册人数</span>
              <br><br>
              <span style="font-size:130px">{{form.historyRegisterCount}}</span> </div>
          </div>
        </el-card>

    <br><br><br><br>

    <!--产品销售分析折线图-->
    <el-row>
      <el-card class="box-card">
        <div slot="header">
          <span>新用户注册数量分析</span>
        </div>
        <!-- 搜索 -->
        <el-form
          inline
          :model="userQuery"
          label-position="right"
          label-width="80px"
          class="query-form"
        >
          <el-form-item label="时间颗粒:">
            <el-select
              v-model="userQuery.timeType"
              placeholder="请时间颗粒"
              size="mini"
            >
              <el-option
                v-for="item in productTimeTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="时间段:">
            <el-date-picker
              v-model="userQuery.searchDate"
              type="daterange"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              :picker-options="pickerOptions2"
              size="mini"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="handleProductEchart"
              size="mini"
            >查看产品折线图</el-button>
          </el-form-item>
        </el-form>
        <el-row style="margin-top: 20px">
          <ve-line :data="userChartData"></ve-line>
        </el-row>
      </el-card>
    </el-row>
    <br><br><br><br>
    <el-card class="box-card">
      <el-table
        :data="tableData"
        class="table"
        stripe
        border
      >
        <el-table-column
          prop="time"
          label="时间"
        ></el-table-column>
        <el-table-column
          prop="newUserNumber"
          label="新注册数"
        ></el-table-column>
        <el-table-column
          prop="loginUserNumber"
          label="日访问"
        ></el-table-column>
      </el-table>
    </el-card>
    <!-- 分頁組件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page.pageNumber"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="page.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="page.total"
    >
    </el-pagination>
  </div>

</template>

<script type="text/ecmascript-6">
import VeLine from "v-charts/lib/line";
import { getYesterdayData, _getUserData, list } from "@/api/admin_dashboard";

export default {
  name: "list",
  components: {
    VeLine: VeLine
  },
  data() {
    return {
      page: {
        pageNumber: 1,
        pageSize: 10,
        total: 0
      },
      tableData: [],
      chartSettings: {},
      form: {},
      cpuIdle: "",
      cpuUsed: "",
      memFreePercent: "",
      memUsedPercent: "",
      activeName: "",
      //产品销售分析
      userQuery: {
        timeType: 1,
        searchDate: []
      },
      productData: [],
      userChartData: {
        columns: ["日期", "新注册数量"],
        rows: []
      },
      productTimeTypeList: [
        { value: 1, label: "日" },
        { value: 2, label: "周" },
        { value: 3, label: "月" }
      ],
      pickerOptions2: {
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime());
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            }
          }
        ]
      }
    };
  },
  created() {},
  mounted() {
    this.getProductData();
    this.getYesterdayData();
    this.getList();
    //放到這裡是因為渲染問題，如果直接在data默認，css有問題
    this.activeName = "first";
  },
  watch: {},
  methods: {
    handleSizeChange: function(val) {
      this.page.pageSize = val;
      this.getList();
    },
    handleCurrentChange: function(val) {
      this.page.pageNumber = val;
      this.getList();
    },
    getYesterdayData: function() {
      getYesterdayData()
        .then(res => {
          this.form = res.data.yesterdayData;
          console.log(this.form + "formmmm");
        })
        .catch(error => {
          this.$message.error(error);
          console.log(error);
        });
    },
    //产品折线图数据
    getProductData: function() {
      let params = {
        searchDate: this.userQuery.searchDate,
        timeType: this.userQuery.timeType
      };
      _getUserData(params)
        .then(res => {
          this.userChartData.rows = [];
          this.productData = res.data.list;
          console.log(this.productData);
          if (this.productData.length > 0) {
            for (var i = 0; i < this.productData.length; i++) {
              this.userChartData.rows.push({
                日期: this.productData[i].theDay,
                新注册数量: this.productData[i].userCounts
              });
            }
          } else {
            this.userChartData.rows.push({
              日期:
                new Date().getFullYear() +
                "/" +
                (new Date().getMonth() + 1) +
                "/" +
                new Date().getDate(),
              新注册数量: 0
            });
          }
        })
        .catch(error => {
          this.$message.error(error);
          console.log(error);
        });
    },
    //查看产品折线图
    handleProductEchart: function() {
      this.getProductData();
    },
    getList: function() {
      let params = {
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
.dashboard {
  margin-top: 20px;
}
.clearfix h1 {
  font-weight: 700;
}

.platform-li {
  list-style-type: none;
}

#wrap {
  display: flex;
  justify-content: space-around;
}
</style>
