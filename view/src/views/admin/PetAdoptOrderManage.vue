<template>
  <div class="container">
    <div class="top-healder">
      <div class="nav-left">
        <Tab :buttons="[
          { label: '全部', value: 'null' },
          { label: '申请中', value: '1' },
          { label: '已审核', value: '2' },
          { label: '审核不通过', value: '3' },
          { label: '已完成', value: '4' }
        ]" initialActive="null" @change="handleChange" />
      </div>
      <div class="nav-right">
        <div>
          <AutoInput placeholder="通过宠物ID搜索" @listener="listener" />
        </div>
      </div>
    </div>
    <!-- 表格及分页信息 -->
    <div>
      <el-table :data="apiResult.data">
        <el-table-column width="100" prop="id" :sortable="true" label="序号"></el-table-column>
        <el-table-column prop="petId" width="120" :sortable="true" label="宠物ID"></el-table-column>
        <el-table-column prop="petName" label="宠物名"></el-table-column>
        <el-table-column prop="avatar" width="180" label="领养人基本信息">
          <template #default="scope">
            <div style="display: flex;justify-content: left;align-items: center;gap: 10px;">
              <img :src="scope.row.avatar" style="width: 20px;height: 20px;border-radius: 50%;" alt="">
              <span>{{ scope.row.username }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="status" width="120" label="订单状态">
          <template #default="scope">
            <el-tag size="mini" v-if="scope.row.status === 1">申请中</el-tag>
            <el-tag size="mini" type="success" v-else-if="scope.row.status === 2">已审核</el-tag>
            <el-tag size="mini" type="danger" v-else-if="scope.row.status === 3">审核不通过</el-tag>
            <el-tag size="mini" ype="success" v-else="scope.row.status === 4">已完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" width="168" label="申请时间"></el-table-column>
        <el-table-column label="" width="120" align="center">
          <template #default="scope">
            <div style="display: flex;justify-content: left;align-items: center;gap: 10px;">
              <div class="operate-buttons1" @click="view(scope.row)">
                <i class="el-icon-view"></i>
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
          :current-page="petAdoptOrderQueryDto.current" :page-sizes="[10, 20]" :page-size="petAdoptOrderQueryDto.size"
          layout="total, sizes, prev, pager, next, jumper" :total="apiResult.total"></el-pagination>
      </div>
    </div>

    <el-drawer :before-close="beforeClose" size="45%" title="订单详情" :visible.sync="drawer" :direction="direction">

      <div style="margin: 10px 20px;">
        <div
          style="display: flex;justify-content: space-between;font-size: 14px;color: rgb(229, 62, 48);background-color: rgb(248,248,248);width: 100%;padding: 10px 20px;box-sizing: border-box;">
          <span>提交状态：{{ petAdoptOrder.isAgainPost ? '再次提交' : '初次提交' }}</span>
          <span>次数：{{ petAdoptOrder.postNumber }}</span>
        </div>
        <h4>收货地址信息</h4>
        <div
          style="font-size: 14px;color: rgb(229, 62, 48);background-color: rgb(248,248,248);width: 100%;padding: 10px 20px;box-sizing: border-box;">
          {{ petAdoptOrder.addressee }} - {{ petAdoptOrder.concatPhone }} - {{ petAdoptOrder.detailAddress }}
        </div>
        <h4>审核状态</h4>
        <div
          style="font-size: 14px;color: rgb(229, 62, 48);background-color: rgb(248,248,248);width: 100%;padding: 10px 20px;box-sizing: border-box;">
          <el-tag size="mini" v-if="petAdoptOrder.status === 1">申请中</el-tag>
          <el-tag size="mini" type="success" v-else-if="petAdoptOrder.status === 2">已审核</el-tag>
          <el-tag size="mini" type="danger" v-else-if="petAdoptOrder.status === 3">审核不通过</el-tag>
          <el-tag size="mini" ype="success" v-else="petAdoptOrder.status === 4">已完成</el-tag>
        </div>
        <h4>证明材料</h4>
        <div style="padding: 10px;background-color: rgb(248,248,248);" v-html="petAdoptOrder.detail"></div>
        <div v-if="petAdoptOrder.status === 3"
          style="margin-top: 10px;font-size: 14px;color: rgb(229, 62, 48);background-color: rgb(248,248,248);width: 100%;padding: 10px 20px;box-sizing: border-box;">
          <h4 style="margin-top: 0;">审核不通过原因</h4>
          <div>
            {{ petAdoptOrder.auditErrorDetail }}
          </div>
        </div>
        <!-- 审核不通过原因框 -->
        <div style="margin-block: 10px;" v-if="operationStatus">
          <h4>原因补充</h4>
          <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="auditErrorDetail">
          </el-input>
        </div>
        <div style="margin-top: 20px;" v-if="petAdoptOrder.status !== 2 && petAdoptOrder.status !== 4">
          <el-button @click="auditSuccess" icon="el-icon-success" type="success">审核通过</el-button>
          <el-button v-if="!operationStatus" @click="auditError" icon="el-icon-error" type="warning">审核不通过</el-button>
          <el-button v-else @click="auditErrorConfirm" icon="el-icon-error" type="warning">驳回申请</el-button>
        </div>
      </div>

    </el-drawer>

    <!-- 删除确认弹窗 -->
    <el-dialog title="删除宠物领养订单" :show-close="false" :visible.sync="dialogDeletedVisible" width="20%">
      <span>确定删除宠物领养订单数据？</span>
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
import Tab from "@/components/Tab.vue"; // 导入封装好的输入框组件
export default {
  components: { AutoInput, Tab }, // 注册组件
  data() {
    return {
      id: null, // 页面即将删除的数据ID
      apiResult: { // 后端返回的查询数据的响应数据
        data: [], // 数据项
        total: 0, // 符合条件的数据总想 - 初始赋值为0
      },
      drawer: false,
      direction: 'rtl',
      petAdoptOrder: {},
      auditErrorDetail: '', // 审核不通过原因
      petAdoptOrderQueryDto: { // 搜索条件
        current: 1, // 当前页 - 初始是第一页
        size: 10, // 页面显示大小 - 初始是10条
        name: null, // 宠物领养订单名
      },
      dialogDeletedVisible: false, // 删除弹窗控制开关变量 - 初始是关（false）
      operationStatus: false, // 审核不通过的状态
      apiParam: {}, // 传送进后端的数据 - 用于新增与修改场景
    };
  },
  created() {
    this.fetchFreshData(); // 页面创建时，先去加载宠物领养订单数据
  },
  methods: {
    beforeClose() {
      this.drawer = false;
      this.operationStatus = false;
      this.auditErrorDetail = '';
    },
    handleChange(val) {
      this.petAdoptOrderQueryDto.status = Number(val.value);
      this.fetchFreshData();
    },
    async auditErrorConfirm() {
      try {
        if (this.auditErrorDetail === '') {
          this.$message.info('请补充驳回意见');
          return;
        }
        const petAdoptOrder = {
          id: this.petAdoptOrder.id,
          status: 3,
          auditErrorDetail: this.auditErrorDetail
        };
        const { message } = await this.$axios.put('/pet-adopt-order/audit', petAdoptOrder);
        this.$message.success(message);
        this.beforeClose();
        this.fetchFreshData();
      } catch (error) {
        this.$message.warning(error.message);
      }
    },
    async auditSuccess() {
      try {
        const petAdoptOrder = {
          id: this.petAdoptOrder.id,
          status: 2
        };
        const { message } = await this.$axios.put('/pet-adopt-order/audit', petAdoptOrder);
        this.$message.success(message);
        this.drawer = false;
        this.fetchFreshData();
      } catch (error) {
        this.$message.warning(error.message);
      }
    },
    auditError() {
      this.operationStatus = true;
    },
    view(data) {
      this.drawer = true;
      this.petAdoptOrder = { ...data };
    },
    // 修改宠物领养订单信息操作
    async updatePetTypeOperation() {
      try {
        const { code, message } = await this.$axios.post('/pet-adopt-order/update', this.apiParam);
        if (code === 200) {
          this.$message.success(message);
          this.cancelOperation(); // 值置位
          this.fetchFreshData(); // 重新加载宠物领养订单数据
        } else {
          this.$message.info(message);
        }
      } catch (error) {
        this.$message.warning(error.message);
      }
    },
    // 保存宠物领养订单信息操作
    async savePetTypeOperation() {
      try {
        const { message, code } = await this.$axios.post('/pet-adopt-order/save', this.apiParam);
        if (code === 200) {
          this.$message.success(message);
          this.cancelOperation(); // 值置位
          this.fetchFreshData(); // 重新加载宠物领养订单数据
        } else {
          this.$message.info(message);
        }
      } catch (error) {
        this.$message.warning(error.message);
      }
    },
    // 取消处理
    cancelOperation() {
      this.apiParam = {}; // 将参数对象清置

    },
    // 输入框组件输入回传
    listener(text) {
      this.petAdoptOrderQueryDto.petId = text; // 赋值查询条件的内容
      this.fetchFreshData(); // 重新加载数据
    },
    // 查询宠物领养订单数据
    async fetchFreshData() {
      try {
        const { data, total } = await this.$axios.post('/pet-adopt-order/query', this.petAdoptOrderQueryDto);
        this.apiResult.data = data;
        this.apiResult.total = total;
      } catch (error) {
        console.error('查询宠物领养订单信息异常:', error);
      }
    },
    // 分页 - 处理页面页数切换
    handleSizeChange(size) {
      this.petAdoptOrderQueryDto.size = size; // 当前页面大小重置
      this.petAdoptOrderQueryDto.currrent = 1; // 当前页设置为第一页
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 分页 - 处理页面当前页切换
    handleCurrentChange(current) {
      this.petAdoptOrderQueryDto.current = current; // 当前页选中
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 表格点击删除宠物领养订单
    handleDelete(row) {
      this.dialogDeletedVisible = true; // 开启删除弹窗确认
      this.id = row.id;
    },
    // 宠物领养订单删除
    async confirmDeleted() {
      try {
        const { code } = await this.$axios.delete(`/pet-adopt-order/${this.id}`);
        if (code === 200) {
          this.$notify.success({
            title: '宠物领养订单删除',
            message: '删除成功',
            position: 'buttom-right',
            suration: 1000,
          });
          this.dialogDeletedVisible = false; // 关闭删除确认弹窗
          this.id = null; // 将标识ID置位
          this.fetchFreshData(); // 删除宠物领养订单数据之后，重新加载宠物领养订单数据
        }
      } catch (error) {
        console.log("删除宠物领养订单数据异常：", error);
      }
    }
  },
};
</script>
<style scoped lang="scss">
.pager {
  margin-block: 20px;
}

.operate-buttons1 {
  padding: 4px 8px;
  cursor: pointer;
  border-radius: 6px;
  box-sizing: border-box;
  transition: all .5s ease;

  &:hover {
    background-color: rgb(236, 237, 238);
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