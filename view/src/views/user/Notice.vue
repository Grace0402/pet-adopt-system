<template>
  <div class="container">
    <div class="top-healder">
      <div class="nav-left">
      </div>
      <div class="nav-right">
        <div>
          <AutoInput placeholder="搜索公告" @listener="listener" />
        </div>
      </div>
    </div>
    <!-- 表格及分页信息 -->
    <div>
      <el-table :data="apiResult.data">
        <el-table-column prop="title" label="标题"></el-table-column>
        <el-table-column prop="createTime" :sortable="true" width="168" label="发布于"></el-table-column>
        <el-table-column label="" width="110" align="center">
          <template #default="scope">
            <div class="operate-buttons1" @click="viewNotice(scope.row)">
              <i class="el-icon-view"></i>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件区域 -->
      <div class="pager">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
          :current-page="noticeQueryDto.current" :page-sizes="[10, 20]" :page-size="noticeQueryDto.size"
          layout="total, sizes, prev, pager, next, jumper" :total="apiResult.total"></el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
// B站 「程序员辰星」原创出品
import AutoInput from "@/components/AutoInput.vue"; // 导入封装好的输入框组件
export default {
  components: { AutoInput }, // 注册组件
  data() {
    return {
      apiResult: { // 后端返回的查询数据的响应数据
        data: [], // 数据项
        total: 0, // 符合条件的数据总想 - 初始赋值为0
      },
      noticeQueryDto: { // 搜索条件
        current: 1, // 当前页 - 初始是第一页
        size: 10, // 页面显示大小 - 初始是10条
        title: null, // 公告名
      },
    };
  },
  created() {
    this.fetchFreshData(); // 页面创建时，先去加载公告数据
  },
  methods: {
    viewNotice(data) {
      window.open(`notice-detail?id=${data.id}`, '_blank');
    },
    // 输入框组件输入回传
    listener(text) {
      this.noticeQueryDto.title = text; // 赋值查询条件的内容
      this.fetchFreshData(); // 重新加载数据
    },
    // 查询公告数据
    async fetchFreshData() {
      try {
        const { data, total } = await this.$axios.post('/notice/list', this.noticeQueryDto);
        this.apiResult.data = data;
        this.apiResult.total = total;
      } catch (error) {
        console.error('查询公告信息异常:', error);
      }
    },
    // 分页 - 处理页面页数切换
    handleSizeChange(size) {
      this.noticeQueryDto.size = size; // 当前页面大小重置
      this.noticeQueryDto.currrent = 1; // 当前页设置为第一页
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 分页 - 处理页面当前页切换
    handleCurrentChange(current) {
      this.noticeQueryDto.current = current; // 当前页选中
      this.fetchFreshData(); // 重新加载页面数据
    },
  },
};
</script>
<style scoped lang="scss">
.post-detail-container {
  padding: 20px;
  height: 100%;
  overflow-y: auto;
}

.post-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.user-info {
  display: flex;
  align-items: center;
}

.user-meta {
  margin-left: 10px;
  display: flex;
  flex-direction: column;
}

.username {
  font-weight: bold;
  font-size: 16px;
}

.pet-type {
  font-size: 12px;
  color: #909399;
}

.post-meta {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.create-time {
  font-size: 12px;
  color: #909399;
  margin-bottom: 5px;
}

.post-cover {
  margin-bottom: 20px;
}

.post-title {
  margin: 15px 0;
  color: #303133;
  font-size: 22px;
}

.post-content {
  line-height: 1.8;
  color: #606266;
}

.post-content>>>h3 {
  margin: 20px 0 10px;
  color: #303133;
}

.post-content>>>ul {
  padding-left: 20px;
}

.post-content>>>li {
  margin-bottom: 8px;
}

.post-stats {
  margin: 30px 0;
  display: flex;
  justify-content: space-around;
  color: #909399;
  font-size: 14px;
}

.post-stats i {
  margin-right: 5px;
}

.post-actions {
  margin-top: 30px;
  text-align: center;
}

.image-error {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 200px;
  background: #f5f7fa;
  color: #909399;
  font-size: 30px;
}

.pager {
  margin-block: 20px;
}

.operate-buttons1 {
  padding: 6px;
  box-sizing: border-box;
  cursor: pointer;

  &:hover {
    background-color: rgb(240, 240, 240);
  }
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