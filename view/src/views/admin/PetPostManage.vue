<template>
  <div class="container">
    <div class="top-healder">
      <div class="nav-left">
        <Tab :buttons="[
          { label: '全部', value: 'null' },
          { label: '未审核', value: '0' },
          { label: '已审核', value: '1' }
        ]" initialActive="null" @change="handleChange" />
      </div>
      <div class="nav-right">
        <div>
          <AutoInput placeholder="搜索宠物经验帖子" @listener="listener" />
        </div>
      </div>
    </div>
    <!-- 表格及分页信息 -->
    <div>
      <el-table :data="apiResult.data">
        <el-table-column width="100" prop="id" :sortable="true" label="序号"></el-table-column>
        <el-table-column prop="title" label="标题"></el-table-column>
        <el-table-column prop="username" width="110" label="发布者"></el-table-column>
        <el-table-column prop="upvoteNumber" :sortable="true" width="110" label="点赞量"></el-table-column>
        <el-table-column prop="viewNumber" :sortable="true" width="110" label="浏览量"></el-table-column>
        <el-table-column prop="saveNumber" :sortable="true" width="110" label="收藏量"></el-table-column>
        <el-table-column prop="isAudit" width="110" label="审核状态">
          <template #default="scope">
            <el-tag size="mini">{{ scope.row.isAudit ? '已审核' : '未审核' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="" width="150" align="center">
          <template #default="scope">
            <div style="display: flex;justify-content: center;align-items: center;gap: 8px;">
              <div class="operate-buttons" @click="viewPetPostDetail(scope.row.id)">
                <i class="el-icon-takeaway-box"></i>
              </div>
              <div class="operate-buttons">
                <el-dropdown trigger="click" placement="bottom-end">
                  <span class="el-dropdown-link">
                    <i class="el-icon-more"></i>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item @click.native="handleDelete(scope.row)"
                      icon="el-icon-delete">删除</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件区域 -->
      <div class="pager">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
          :current-page="petPostQueryDto.current" :page-sizes="[10, 20]" :page-size="petPostQueryDto.size"
          layout="total, sizes, prev, pager, next, jumper" :total="apiResult.total"></el-pagination>
      </div>
    </div>

    <el-drawer size="45%" title="帖子信息详情" :visible.sync="drawer" :direction="direction">

      <div class="post-detail-container">
        <!-- 头部信息 -->
        <div class="post-header">
          <div class="user-info">
            <el-avatar :size="50" :src="petPost.avatar" />
            <div class="user-meta">
              <span class="username">{{ petPost.username }}</span>
              <span class="pet-type">{{ petPost.petTypeName }}</span>
            </div>
          </div>
          <div class="post-meta">
            <span class="create-time" v-if="petPost.createTime">
              {{ petPost.createTime }}
            </span>
            <el-tag :type="petPost.isAudit ? 'success' : 'warning'" size="small">
              {{ petPost.isAudit ? '已审核' : '未审核' }}
            </el-tag>
          </div>
        </div>

        <!-- 封面图 -->
        <div class="post-cover" v-if="petPost.cover">
          <el-image :src="petPost.cover" fit="cover" style="width: 100%; max-height: 300px; border-radius: 4px;">
            <div slot="error" class="image-error">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </div>

        <!-- 标题 -->
        <h2 class="post-title">{{ petPost.title }}</h2>

        <!-- 内容 -->
        <div class="post-content" v-html="petPost.content"></div>

        <!-- 统计数据 -->
        <div class="post-stats">
          <span><i class="el-icon-view"></i> {{ petPost.viewNumber || 0 }} 浏览</span>
          <span><i class="el-icon-thumb"></i> {{ petPost.upvoteNumber || 0 }} 点赞</span>
          <span><i class="el-icon-star-off"></i> {{ petPost.saveNumber || 0 }} 收藏</span>
        </div>

        <!-- 操作按钮 -->
        <div class="post-actions">
          <el-button type="primary" size="small" v-if="!petPost.isAudit" @click="handleAudit">
            <i class="el-icon-check"></i> 通过审核
          </el-button>
        </div>
      </div>

    </el-drawer>

    <!-- 删除确认弹窗 -->
    <el-dialog title="删除宠物经验帖子" :show-close="false" :visible.sync="dialogDeletedVisible" width="20%">
      <span>确定删除宠物经验帖子数据？</span>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogDeletedVisible = false">取消</el-button>
        <el-button size="mini" type="primary" @click="confirmDeleted">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
// B站 「程序员辰星」原创出品
import AutoInput from "@/components/AutoInput.vue"; // 导入封装好的输入框组件
import Tab from "@/components/Tab" // 导入封装好的Tab组件
export default {
  components: { AutoInput, Tab }, // 注册组件
  data() {
    return {
      petPost: {}, // 宠物经验帖子信息
      drawer: false, // 详情抽屉控制开关变量 - 默认是false：关闭
      direction: 'rtl', // 详情抽屉出现方向
      id: null, // 页面即将删除的数据ID
      apiResult: { // 后端返回的查询数据的响应数据
        data: [], // 数据项
        total: 0, // 符合条件的数据总想 - 初始赋值为0
      },
      petPostQueryDto: { // 搜索条件
        current: 1, // 当前页 - 初始是第一页
        size: 10, // 页面显示大小 - 初始是10条
        name: null, // 宠物经验帖子名
      },
      dialogDeletedVisible: false, // 删除弹窗控制开关变量 - 初始是关（false）
      apiParam: {}, // 传送进后端的数据 - 用于新增与修改场景
    };
  },
  created() {
    this.fetchFreshData(); // 页面创建时，先去加载宠物经验帖子数据
  },
  methods: {
    // 审核宠物经验帖子信息
    handleAudit() {
      this.$confirm('确定要通过此帖子的审核吗?', '审核确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        try {
          this.$axios.put(`/pet-post/audit/${this.petPost.id}`).then(response => {
            if (response.code === 200) {
              this.$message.success(response.message);
              this.cancelOperation();
              this.fetchFreshData();
            }
          }).catch(error => {
            console.log("经验帖子审核异常：", error);
          });

        } catch (e) {
          console.log("查询宠物经验帖子信息异常：", e);
        }
      }).catch(() => { });
    },
    // 查询宠物详情并打开抽屉容器
    async viewPetPostDetail(id) {
      try {
        const { data } = await this.$axios.get(`/pet-post/getById/${id}`);
        this.petPost = data; // 查询回来的宠物经验帖子详情赋值
        this.drawer = true; // 打开内容详情抽屉
      } catch (e) {
        console.log("查询宠物经验帖子信息异常：", e);
      }
    },
    // 宠物经验帖子审核状态选中事件
    handleChange(obj) {
      this.petPostQueryDto.isAudit = Number(obj.value); // 转成数值类型，再赋值
      this.fetchFreshData(); // 重新加载宠物数据
    },
    // 取消处理
    cancelOperation() {
      this.petPost = {}; // 将参数对象清置 
      this.drawer = false; // 关闭宠物经验帖子新增弹窗
    },
    // 输入框组件输入回传
    listener(text) {
      this.petPostQueryDto.title = text; // 赋值查询条件的内容
      this.fetchFreshData(); // 重新加载数据
    },
    // 查询宠物经验帖子数据
    async fetchFreshData() {
      try {
        const { data, total } = await this.$axios.post('/pet-post/list', this.petPostQueryDto);
        this.apiResult.data = data;
        this.apiResult.total = total;
      } catch (error) {
        console.error('查询宠物经验帖子信息异常:', error);
      }
    },
    // 分页 - 处理页面页数切换
    handleSizeChange(size) {
      this.petPostQueryDto.size = size; // 当前页面大小重置
      this.petPostQueryDto.currrent = 1; // 当前页设置为第一页
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 分页 - 处理页面当前页切换
    handleCurrentChange(current) {
      this.petPostQueryDto.current = current; // 当前页选中
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 表格点击删除宠物经验帖子
    handleDelete(row) {
      this.dialogDeletedVisible = true; // 开启删除弹窗确认
      this.id = row.id;
    },
    // 宠物经验帖子删除
    async confirmDeleted() {
      try {
        const { code } = await this.$axios.delete(`/pet-post/${this.id}`);
        if (code === 200) {
          this.$notify.success({
            title: '宠物经验帖子删除',
            message: '删除成功',
            position: 'buttom-right',
            suration: 1000,
          });
          this.dialogDeletedVisible = false; // 关闭删除确认弹窗
          this.id = null; // 将标识ID置位
          this.fetchFreshData(); // 删除宠物经验帖子数据之后，重新加载宠物经验帖子数据
        }
      } catch (error) {
        console.log("删除宠物经验帖子数据异常：", error);
      }
    }
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