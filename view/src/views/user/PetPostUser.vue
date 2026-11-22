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
        <div class="primary-bt" @click="createPetPost">
          <i class="el-icon-plus"></i>
          发布帖子
        </div>
      </div>
    </div>
    <!-- 表格及分页信息 -->
    <div>
      <el-table :data="apiResult.data">
        <el-table-column prop="title" label="标题"></el-table-column>
        <el-table-column prop="upvoteNumber" :sortable="true" width="110" label="点赞量"></el-table-column>
        <el-table-column prop="viewNumber" :sortable="true" width="110" label="浏览量"></el-table-column>
        <el-table-column prop="saveNumber" :sortable="true" width="110" label="收藏量"></el-table-column>
        <el-table-column prop="createTime" :sortable="true" width="168" label="发布于"></el-table-column>
        <el-table-column prop="isAudit" width="110" label="审核状态">
          <template #default="scope">
            <el-tag type="info" size="mini">{{ scope.row.isAudit ? '已审核' : '未审核' }}</el-tag>
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
                    <el-dropdown-item @click.native="handleEdit(scope.row)" icon="el-icon-delete">修改</el-dropdown-item>
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

    <!-- 用户收藏的宠物信息数据 -->
    <div>
      <div
        style="padding-inline: 20px;box-sizing: border-box;background-color: rgb(248,248,248);display: flex;justify-content: space-between;margin-block: 20px;align-items: center;">
        <h3>流量状况</h3>
      </div>
      <div style="display: flex;height: 400px;">
        <div style="width: 50%;">
          <div>
            <p>*流量类型</p>
            <Tab :buttons="[
              { label: '浏览', value: '1' },
              { label: '点赞', value: '2' },
              { label: '收藏', value: '3' }
            ]" initialActive="1" @change="handleNetTypeChange" />
          </div>
          <div>
            <p>*选择具体帖子</p>
            <el-select style="width: 73%;" @change="handlePetPostChange" v-model="petPostId" placeholder="请选择帖子">
              <el-option v-for="item in petPostSelected" :key="item.id" :label="item.title" :value="item.id">
              </el-option>
            </el-select>
          </div>
        </div>
        <div style="width: 50%;">
          <div v-if="values.length === 0">
            <el-empty description="暂无流量数据"></el-empty>
          </div>
          <div v-else>
            <!-- 调用折线图 -->
            <LineChart tag="帖子流量数据" @time-change="handleAgoDaysChange" :values="values" :date="date" />
          </div>

        </div>
      </div>

    </div>

    <!-- 用户收藏的宠物信息数据 -->
    <div>
      <div
        style="padding-inline: 20px;box-sizing: border-box;background-color: rgb(248,248,248);display: flex;justify-content: space-between;margin-block: 20px;align-items: center;">
        <h3>我收藏的宠物信息</h3>

        <span class="clear" @click="clearPetInfo">
          <el-tooltip class="item" effect="dark" content="清空我收藏的全部宠物" placement="bottom">
            <i class="el-icon-s-open"></i>
          </el-tooltip>
        </span>
      </div>
      <div>
        <div v-if="saveList.length === 0">
          <el-empty description="暂无收藏宠物信息"></el-empty>
        </div>
        <div class="item-container">
          <div @click="viewPetDetail(item.id)" class="item-save" v-for="(item, index) in saveList" :key="index">
            <div class="cover">
              <img :src="item.cover" alt="">
            </div>
            <div class="name">
              {{ item.name }}
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 用户收藏的宠物经验帖子信息数据 -->
    <div>
      <div
        style="padding-inline: 20px;box-sizing: border-box;background-color: rgb(248,248,248);display: flex;justify-content: space-between;margin-block: 20px;align-items: center;">
        <h3>我收藏的宠物经验帖子信息</h3>

        <span class="clear" @click="clearPetPostInfo">
          <el-tooltip class="item" effect="dark" content="清空我收藏的全部帖子" placement="bottom">
            <i class="el-icon-s-open"></i>
          </el-tooltip>
        </span>
      </div>
      <div>
        <div v-if="savePetPostList.length === 0">
          <el-empty description="暂无收藏宠物经验帖子信息"></el-empty>
        </div>
        <div class="item-container">
          <div @click="viewPetPostDetail(item.id)" class="item-save" v-for="(item, index) in savePetPostList"
            :key="index">
            <div class="cover">
              <img :src="item.cover" alt="">
            </div>
            <div class="name">
              {{ item.title }}
            </div>
          </div>
        </div>
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
import Tab from "@/components/Tab" // 导入封装好的Tab组件  components: { AutoInput, Tab, Editor,LineChart }, // 注册组件
import LineChart from "@/components/LineChart.vue";
export default {
  components: { AutoInput, Tab, LineChart }, // 注册组件
  data() {
    return {
      saveList: [],
      petPostId: null, // 选中的帖子ID
      petPostSelected: [], // 用户名下帖子数据下拉选择器的列表
      savePetPostList: [],
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
      values: [],
      date: [],
      activeNetQueryDto: {},
    };
  },
  created() {
    this.fetchFreshData(); // 页面创建时，先去加载宠物经验帖子数据
    this.querySaveList();
    this.querySavePetPostList();
    this.fetchPetPostSelected();
  },
  methods: {
    handlePetPostChange() {
      this.activeNetQueryDto.contentId = this.petPostId;
      this.fetchPetPostlistChart();
    },
    handleAgoDaysChange(val) {
      this.activeNetQueryDto.days = val;
      this.fetchPetPostlistChart();
    },
    handleNetTypeChange(val) {
      this.activeNetQueryDto.type = val.value;
      this.fetchPetPostlistChart();
    },
    async fetchPetPostlistChart() {
      try {
        const { data } = await this.$axios.post(`/active-net/listChart`, this.activeNetQueryDto);
        this.date = data.map(entity => entity.name);
        this.values = data.map(entity => entity.count);
      } catch (e) {
        console.log("查询流量状况异常：", e);
      }
    },
    async fetchPetPostSelected() {
      try {
        const { data } = await this.$axios.get(`/pet-post/listPetPostSelect`);
        this.petPostSelected = data;
        this.petPostId = this.petPostSelected[0].id;
        this.activeNetQueryDto.contentId = this.petPostSelected[0].id;
        this.activeNetQueryDto.days = 365;
        this.fetchPetPostlistChart();
      } catch (e) {
        console.log("查询用户名下帖子的下拉选择器异常：", e);
      }
    },
    async clearPetInfo() {
      try {
        await this.$axios.delete(`/active-net/deleteUserPetList`);
        this.querySaveList();
      } catch (e) {
        console.log("删除用户收藏的宠物信息列表信息异常：", e);
      }
    },
    async clearPetPostInfo() {
      try {
        await this.$axios.delete(`/active-net/deleteUserPetPostList`);
        this.querySavePetPostList();
      } catch (e) {
        console.log("删除用户收藏的宠物帖子列表信息异常：", e);
      }
    },
    viewPetDetail(id) {
      window.open(`pet-detail?id=${id}`, '_blank');
    },
    viewPetPostDetail(id) {
      window.open(`pet-post-detail?id=${id}`, '_blank');
    },
    // 跳转至经验帖子发布界面
    createPetPost() {
      window.open(`/create-pet-post`, '_blank');
    },
    // 跳转至经验帖子修改页面
    handleEdit(petPost) {
      window.open(`/update-pet-post?id=${petPost.id}`, '_blank');
    },
    // 查询用户收藏的宠物信息列表
    async querySaveList() {
      try {
        const { data } = await this.$axios.get(`/pet/saveList`);
        this.saveList = data;
      } catch (e) {
        console.log("查询用户收藏的宠物信息列表信息异常：", e);
      }
    },
    // 查询用户收藏的经验帖子数据
    async querySavePetPostList() {
      try {
        const { data } = await this.$axios.get(`/pet-post/saveList`);
        this.savePetPostList = data;
      } catch (e) {
        console.log("查询用户收藏的宠物经验帖子列表信息异常：", e);
      }
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
        const { data, total } = await this.$axios.post('/pet-post/listUser', this.petPostQueryDto);
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
.clear {
  padding: 6px;
  display: inline-block;
  border-radius: 4px;
  cursor: pointer;

  &:hover {
    background-color: rgb(230, 230, 230);
  }
}

.item-container {
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  /* 确保容器占满父元素宽度 */
  gap: 10px;
  /* 项目间距 */
  margin: 0;
  /* 移除负margin */
  padding: 0 5px;
  /* 左右内边距补偿 */
  box-sizing: border-box;

  .item-save {
    flex: 0 0 calc(20% - 8px);
    /* 精确计算宽度 */
    margin-bottom: 10px;
    /* 底部间距 */
    box-sizing: border-box;

    .cover {
      width: 100%;
      height: 140px;
      overflow: hidden;
      /* 防止图片溢出 */

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        border-radius: 4px;
        transition: transform 0.3s;
        /* 悬停效果 */
      }
    }

    .name {
      margin-top: 8px;
      font-size: 14px;
      text-align: center;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      padding: 0 2px;
      /* 文本内边距 */
    }

    &:hover {
      img {
        transform: scale(1.05);
        /* 悬停放大效果 */
      }
    }
  }
}

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