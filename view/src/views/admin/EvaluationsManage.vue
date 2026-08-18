<template>
  <div class="container">
    <div class="top-healder">
      <div class="nav-left">
      </div>
      <div class="nav-right">
        <div>
          <AutoInput placeholder="搜索评论" @listener="listener" />
        </div>
      </div>
    </div>
    <!-- 表格及分页信息 -->
    <div>
      <el-table :data="apiResult.data">
        <el-table-column width="200" prop="username" label="评论者">
          <template #default="scope">
            <div class="over-text">
              {{ scope.row.username }}
            </div>
          </template>
        </el-table-column>
        <el-table-column width="300" prop="content" label="评论内容">
          <template #default="scope">
            <div class="over-text">
              {{ scope.row.content }}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="contentType" :sortable="true" width="128" label="所属模块"></el-table-column>
        <el-table-column prop="upvoteCount" :sortable="true" width="128" label="点赞量"></el-table-column>
        <el-table-column prop="parentId" :sortable="true" width="108" label="层级">
          <template #default="scope">
            <div>{{ scope.row.parentId === null ? '父级' : '子级' }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" :sortable="true" width="168" label="评论时间"></el-table-column>
        <el-table-column label="" align="center">
          <template #default="scope">
            <div class="operate-buttons">
              <el-dropdown trigger="click" placement="bottom-end">
                <span class="el-dropdown-link">
                  <i class="el-icon-more"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item @click.native="handleDelete(scope.row)"
                    icon="el-icon-delete">删除评论</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件区域 -->
      <div class="pager">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
          :current-page="evaluationQueryDto.current" :page-sizes="[10, 20]" :page-size="evaluationQueryDto.size"
          layout="total, sizes, prev, pager, next, jumper" :total="apiResult.total"></el-pagination>
      </div>
    </div>

    <!-- 删除确认弹窗 -->
    <el-dialog title="删除评论" :show-close="false" :visible.sync="dialogDeletedVisible" width="20%">
      <span>确定删除评论数据？</span>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogDeletedVisible = false">取消</el-button>
        <el-button size="mini" type="primary" @click="confirmDeleted">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
// B站 「程序辰星」原创出品
import AutoInput from "@/components/AutoInput.vue"; // 自己封装好的输入框组件
export default {
  components: { AutoInput }, // 注册组件
  data() {
    return {
      id: null, // 页面即将删除的数据ID
      apiResult: { // 后端返回的查询数据的响应数据
        data: [], // 数据项
        total: 0, // 符合条件的数据总想 - 初始赋值为0
      },
      evaluationQueryDto: { // 搜索条件
        current: 1, // 当前页 - 初始是第一页
        size: 10, // 页面显示大小 - 初始是10条
      },
      dialogDeletedVisible: false, // 删除弹窗控制开关变量 - 初始是关（false）
    };
  },
  created() {
    this.fetchFreshData();
  },
  methods: {
    // 输入框组件输入回传
    listener(text) {
      this.evaluationQueryDto.content = text; // 赋值查询条件的内容
      this.fetchFreshData(); // 重新加载数据
    },
    // 查询评论数据
    async fetchFreshData() {
      try {
        const { data, total } = await this.$axios.post('/evaluations/query', this.evaluationQueryDto);
        this.apiResult.data = data;
        this.apiResult.total = total;
      } catch (error) {
        console.error('查询评论信息异常:', error);
      }
    },
    // 分页 - 处理页面页数切换
    handleSizeChange(size) {
      this.evaluationQueryDto.size = size; // 当前页面大小重置
      this.evaluationQueryDto.currrent = 1; // 当前页设置为第一页
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 分页 - 处理页面当前页切换
    handleCurrentChange(current) {
      this.evaluationQueryDto.current = current; // 当前页选中
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 表格点击删除评论
    handleDelete(row) {
      this.dialogDeletedVisible = true; // 开启删除弹窗确认
      this.id = row.id;
    },
    // 评论删除
    async confirmDeleted() {
      try {
        const { code } = await this.$axios.delete(`/evaluations/${this.id}`);
        if (code === 200) {
          this.$notify.success({
            title: '评论删除',
            message: '删除成功',
            position: 'buttom-right',
            suration: 1000,
          });
          this.dialogDeletedVisible = false; // 关闭删除确认弹窗
          this.id = null; // 将标识ID置位
          this.fetchFreshData(); // 删除评论数据之后，重新加载评论数据
        }
      } catch (error) {
        console.log("删除评论数据异常：", error);
      }
    }
  },
};
</script>
<style scoped lang="scss">
.pager {
  margin-block: 20px;
}

/* 默认隐藏操作按钮 */
.operate-buttons {
  opacity: 0;
  transition: opacity 0.3s;
  /* 添加过渡效果 */
  cursor: pointer;

  i {
    padding: 8px;
    border-radius: 6px;
    transition: all .5s ease;

    &:hover {
      background-color: rgb(236, 237, 238);
    }
  }

}

/* 行悬停时显示操作按钮 */
.el-table__body tr:hover .operate-buttons {
  opacity: 1;
}

.container {
  margin: 10px 20px;
}

.top-healder {
  margin-block: 10px;
  padding-inline: 10px;
  border-radius: 5px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .nav-left,
  .nav-right {
    display: flex;
    justify-content: left;
    align-items: center;
    gap: 10px;
  }

  .nav-left {
    display: flex;
  }

}
</style>