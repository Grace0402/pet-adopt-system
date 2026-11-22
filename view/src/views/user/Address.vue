<template>
  <div class="container">
    <div class="top-healder">
      <div class="nav-left">
        <Tab :buttons="[
          { label: '全部', value: 'null' },
          { label: '非默认地址', value: '0' },
          { label: '默认地址', value: '1' }
        ]" initialActive="null" @change="handleChange" />
      </div>
      <div class="nav-right">
        <div>
          <AutoInput placeholder="搜索收货地址" @listener="listener" />
        </div>
        <div class="primary-bt" @click="saveAddress">
          <i class="el-icon-plus"></i>
          新增收货地址
        </div>
      </div>
    </div>
    <!-- 表格及分页信息 -->
    <div>
      <el-table :data="apiResult.data">
        <el-table-column prop="addressee" width="110" label="收件人"></el-table-column>
        <el-table-column prop="concatPhone" width="110" label="联系电话"></el-table-column>
        <el-table-column prop="detail" label="详细地址"></el-table-column>
        <el-table-column prop="createTime" :sortable="true" width="168" label="创建于"></el-table-column>
        <el-table-column prop="isDefault" width="130" label="是否是默认地址">
          <template #default="scope">
            <el-switch @change="changeSwitch(scope.row)" v-model="scope.row.isDefault" active-color="#13ce66" inactive-color="#f1f1f1">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="" width="150" align="center">
          <template #default="scope">
            <div style="display: flex;justify-content: center;align-items: center;gap: 8px;">
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
          :current-page="addressQueryDto.current" :page-sizes="[10, 20]" :page-size="addressQueryDto.size"
          layout="total, sizes, prev, pager, next, jumper" :total="apiResult.total"></el-pagination>
      </div>
    </div>

    <el-drawer size="30%" :before-close="beforeClose" :title="drawerOperation ? '收货地址修改' : '收货地址新增'"
      :visible.sync="drawer" :direction="direction">

      <div style="padding: 10px 20px;">
        <h5 style="margin-top: 0;">*填写收件人</h5>
        <el-input v-model="apiParam.addressee" placeholder="收件人"></el-input>
        <h5>*填写联系电话</h5>
        <el-input v-model="apiParam.concatPhone" placeholder="联系电话"></el-input>
        <h5>*填写详细地址</h5>
        <el-input type="textarea" :rows="3" placeholder="详细地址描述" v-model="apiParam.detail">
        </el-input>
        <h5>是否是默认地址</h5>
        <el-switch v-model="apiParam.isDefault" active-color="#13ce66" inactive-color="#ff4949">
        </el-switch>
        <div v-if="!drawerOperation" class="primary-bt" style="margin-inline: 0;text-align: center;margin-top: 20px;"
          @click="saveMyAddress">
          新增收货地址
        </div>
        <div v-else class="primary-bt" style="margin-inline: 0;text-align: center;margin-top: 20px;"
          @click="updateMyAddress">
          修改收货地址
        </div>
      </div>


    </el-drawer>

    <!-- 删除确认弹窗 -->
    <el-dialog title="删除收货地址" :show-close="false" :visible.sync="dialogDeletedVisible" width="20%">
      <span>确定删除收货地址数据？</span>
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
      drawer: false, // 详情抽屉控制开关变量 - 默认是false：关闭
      drawerOperation: false, // false：新增；true：修改
      direction: 'rtl', // 详情抽屉出现方向
      id: null, // 页面即将删除的数据ID
      apiResult: { // 后端返回的查询数据的响应数据
        data: [], // 数据项
        total: 0, // 符合条件的数据总想 - 初始赋值为0
      },
      addressQueryDto: { // 搜索条件
        current: 1, // 当前页 - 初始是第一页
        size: 10, // 页面显示大小 - 初始是10条
        name: null, // 收货地址名
      },
      dialogDeletedVisible: false, // 删除弹窗控制开关变量 - 初始是关（false）
      apiParam: { isDefault: false }, // 传送进后端的数据 - 用于新增与修改场景
    };
  },
  created() {
    this.fetchFreshData(); // 页面创建时，先去加载收货地址数据
  },
  methods: {
    changeSwitch(data){
      this.apiParam = {...data};
      this.updateMyAddress();
    },
    beforeClose() {
      this.cancelOperation();
    },
    saveAddress() {
      // 弹出信息填写框
      this.drawer = true;
    },
    async saveMyAddress() {
      try {
        const { message } = await this.$axios.post('/address/save', this.apiParam);
        this.$notify({
          title: '收货地址新增',
          type: 'info',
          message: message,
          duration: 1000,
          position: 'bottom-right'
        });
        this.cancelOperation();
        this.fetchFreshData();
      } catch (error) {
        console.error('新增收货地址信息异常:', error);
        this.$notify({
          title: '收货地址新增',
          type: 'info',
          message: error.message,
          duration: 1000,
          position: 'bottom-right'
        })
      }
    },
    async updateMyAddress() {
      try {
        const { message } = await this.$axios.put('/address/update', this.apiParam);
        this.$notify({
          title: '收货地址修改',
          type: 'info',
          message: message,
          duration: 1000,
          position: 'bottom-right'
        });
        this.cancelOperation();
        this.fetchFreshData();
      } catch (error) {
        console.error('修改收货地址信息异常:', error);
        this.$notify({
          title: '收货地址修改',
          type: 'info',
          message: error.message,
          duration: 1000,
          position: 'bottom-right'
        })
      }
    },
    handleEdit(address) {
      this.apiParam = { ...address };
      this.drawer = true;
      this.drawerOperation = true; // 标识是修改状态
    },
    // 收货地址审核状态选中事件
    handleChange(obj) {
      this.addressQueryDto.isDefault = Number(obj.value); // 转成数值类型，再赋值
      this.fetchFreshData(); // 重新加载收货地址数据
    },
    // 取消处理
    cancelOperation() {
      this.apiParam = {}; // 将参数对象清置 
      this.apiParam.isDefault = false;
      this.drawer = false; // 关闭收货地址新增弹窗
      this.drawerOperation = false; // 恢复初始的操作状态 - 新增
    },
    // 输入框组件输入回传
    listener(text) {
      this.addressQueryDto.addressee = text; // 赋值查询条件的内容
      this.fetchFreshData(); // 重新加载数据
    },
    // 查询收货地址数据
    async fetchFreshData() {
      try {
        const { data, total } = await this.$axios.post('/address/queryUser', this.addressQueryDto);
        this.apiResult.data = data;
        this.apiResult.total = total;
      } catch (error) {
        console.error('查询收货地址信息异常:', error);
      }
    },
    // 分页 - 处理页面页数切换
    handleSizeChange(size) {
      this.addressQueryDto.size = size; // 当前页面大小重置
      this.addressQueryDto.currrent = 1; // 当前页设置为第一页
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 分页 - 处理页面当前页切换
    handleCurrentChange(current) {
      this.addressQueryDto.current = current; // 当前页选中
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 表格点击删除收货地址
    handleDelete(row) {
      this.dialogDeletedVisible = true; // 开启删除弹窗确认
      this.id = row.id;
    },
    // 收货地址删除
    async confirmDeleted() {
      try {
        const { code } = await this.$axios.delete(`/address/${this.id}`);
        if (code === 200) {
          this.$notify.success({
            title: '收货地址删除',
            message: '删除成功',
            position: 'buttom-right',
            suration: 1000,
          });
          this.dialogDeletedVisible = false; // 关闭删除确认弹窗
          this.id = null; // 将标识ID置位
          this.fetchFreshData(); // 删除收货地址数据之后，重新加载收货地址数据
        }
      } catch (error) {
        console.log("删除收货地址数据异常：", error);
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