<template>
  <div class="container">
    <div class="top-healder">
      <div class="nav-left">
      </div>
      <div class="nav-right">
        <div>
          <AutoInput placeholder="搜索公告" @listener="listener" />
        </div>
        <div class="primary-bt" @click="dialogVisible = true">
          <i class="el-icon-plus"></i>
          新增公告
        </div>
      </div>
    </div>
    <!-- 表格及分页信息 -->
    <div>
      <el-table :data="apiResult.data">
        <el-table-column width="100" prop="id" :sortable="true" label="序号"></el-table-column>
        <el-table-column prop="title" label="公告名"></el-table-column>
        <el-table-column prop="createTime" width="168" :sortable="true" label="发布时间"></el-table-column>
        <el-table-column label="" width="110" align="center">
          <template #default="scope">
            <div class="operate-buttons">
              <el-dropdown trigger="click" placement="bottom-end">
                <span class="el-dropdown-link">
                  <i class="el-icon-more"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item @click.native="handleEdit(scope.row)" icon="el-icon-edit">
                    修改信息
                  </el-dropdown-item>
                  <el-dropdown-item @click.native="handleDelete(scope.row)"
                    icon="el-icon-delete">删除公告</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件区域 -->
      <div class="pager">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
          :current-page="petTypeQueryDto.current" :page-sizes="[10, 20]" :page-size="petTypeQueryDto.size"
          layout="total, sizes, prev, pager, next, jumper" :total="apiResult.total"></el-pagination>
      </div>
    </div>

    <!-- 公告信息操作弹窗 -->
    <el-dialog :title="dialogControlOperation ? '新增公告信息' : '修改公告信息'" :show-close="false" :visible.sync="dialogVisible"
      :closeOnClickModal="false" width="50%">
      <div class="right">
        <input v-model="apiParam.title" placeholder="输入标题，30字以内" />
        <Editor :key="editorKey" style="width: 600px;" height="400px" @on-listener="onListener" :receiveContent="text"
          api="http://localhost:21090/api/v1.0/pet-adopt-api/file/upload" />
      </div>
      <span slot="footer" class="dialog-footer">
        <span class="primary-bt" @click="cancelOperation">取消</span>
        <span class="info-bt" @click="handleConfirm">
          {{ dialogControlOperation ? '确定新增' : '确定修改' }}
        </span>
      </span>
    </el-dialog>

    <!-- 删除确认弹窗 -->
    <el-dialog title="删除公告" :show-close="false" :visible.sync="dialogDeletedVisible" width="20%">
      <span>确定删除公告数据？</span>
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
import Editor from "@/components/Editor"
export default {
  components: { AutoInput, Editor }, // 注册组件
  data() {
    return {
      id: null, // 页面即将删除的数据ID
      apiResult: { // 后端返回的查询数据的响应数据
        data: [], // 数据项
        total: 0, // 符合条件的数据总想 - 初始赋值为0
      },
      petTypeQueryDto: { // 搜索条件
        current: 1, // 当前页 - 初始是第一页
        size: 10, // 页面显示大小 - 初始是10条
        name: null, // 公告名
      },
      text: '',
      editorKey: 0,
      dialogDeletedVisible: false, // 删除弹窗控制开关变量 - 初始是关（false）
      dialogVisible: false, // 操作弹窗控制开关变量 - 初始是关（false）
      dialogControlOperation: true, // 弹窗当前操作标识 - true：新增；false：修改
      apiParam: {}, // 传送进后端的数据 - 用于新增与修改场景
    };
  },
  created() {
    this.fetchFreshData(); // 页面创建时，先去加载公告数据
  },
  methods: {
    onListener(text) {
      this.apiParam.content = text;
    },
    handleConfirm() {
      // 判断行为类型 - 判断是新增操作还是修改操作
      this.dialogControlOperation ? this.saveNoticeOperation() : this.updateNoticeOperation();
    },
    // 修改公告信息操作
    async updateNoticeOperation() {
      try {
        const { message } = await this.$axios.post('/notice/update', this.apiParam);
        this.$message.success(message);
        this.cancelOperation(); // 值置位
        this.fetchFreshData(); // 重新加载公告数据
      } catch (error) {
        this.$message.warning(error.message);
      }
    },
    // 保存公告信息操作
    async saveNoticeOperation() {
      try {
        const { message } = await this.$axios.post('/notice/save', this.apiParam);
        this.$message.success(message);
        this.fetchFreshData(); // 重新加载公告数据
        this.cancelOperation(); // 值置位
      } catch (error) {
        this.$message.warning(error.message);
      }
    },
    // 取消处理
    cancelOperation() {
      this.apiParam = {}; // 将参数对象清置
      this.text = '';
      this.dialogVisible = false; // 关闭公告新增弹窗
      this.dialogControlOperation = true; // 操作标识变量设置为最初的状态，即true，为新增
      this.editorKey += 1; // 强制刷新编辑器组件
    },
    // 输入框组件输入回传
    listener(text) {
      this.petTypeQueryDto.title = text; // 赋值查询条件的内容
      this.fetchFreshData(); // 重新加载数据
    },
    // 查询公告数据
    async fetchFreshData() {
      try {
        const { data, total } = await this.$axios.post('/notice/list', this.petTypeQueryDto);
        this.apiResult.data = data;
        this.apiResult.total = total;
      } catch (error) {
        console.error('查询公告信息异常:', error);
      }
    },
    // 分页 - 处理页面页数切换
    handleSizeChange(size) {
      this.petTypeQueryDto.size = size; // 当前页面大小重置
      this.petTypeQueryDto.currrent = 1; // 当前页设置为第一页
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 分页 - 处理页面当前页切换
    handleCurrentChange(current) {
      this.petTypeQueryDto.current = current; // 当前页选中
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 表格点击修改公告
    async handleEdit(row) {
      try {
        const { data } = await this.$axios.get(`/notice/getById/${row.id}`);
        this.cancelOperation();
        this.apiParam = { ...data }; // 当前操作公告信息
        this.text = data.content;
        this.dialogControlOperation = false; // 标识为修改操作
        this.dialogVisible = true; // 打开操作面板
      } catch (error) {
        console.error('通过ID查询公告信息异常:', error);
      }
    },
    // 表格点击删除公告
    handleDelete(row) {
      this.dialogDeletedVisible = true; // 开启删除弹窗确认
      this.id = row.id;
    },
    // 公告删除
    async confirmDeleted() {
      try {
        const { code } = await this.$axios.delete(`/notice/${this.id}`);
        if (code === 200) {
          this.$notify.success({
            title: '公告删除',
            message: '删除成功',
            position: 'buttom-right',
            suration: 1000,
          });
          this.dialogDeletedVisible = false; // 关闭删除确认弹窗
          this.id = null; // 将标识ID置位
          this.fetchFreshData(); // 删除公告数据之后，重新加载公告数据
        }
      } catch (error) {
        console.log("删除公告数据异常：", error);
      }
    }
  },
};
</script>
<style scoped lang="scss">
.right {
  margin: 0 auto;
  /* background-color: rgb(255, 255, 255); */
  height: calc(100vh - 55px);
  padding: 10px 30px;
  box-sizing: border-box;

  input {
    border: none;
    outline: none;
    width: 600px;
    background-color: rgb(246, 247, 248);
    font-size: 32px;
    padding: 20px 30px;
    box-sizing: border-box;
  }
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