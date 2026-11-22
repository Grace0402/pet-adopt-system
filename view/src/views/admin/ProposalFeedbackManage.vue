<template>
    <div class="container">
        <div class="top-healder">
            <div class="nav-left">
                <Tab :buttons="[
                    { label: '全部', value: 'null' },
                    { label: '未回复', value: '0' },
                    { label: '已回复', value: '1' }
                ]" initialActive="null" @change="handleChange" />
            </div>
            <div class="nav-right">
                <div>
                    <AutoInput placeholder="搜索反馈" @listener="listener" />
                </div>
            </div>
        </div>
        <!-- 表格及分页信息 -->
        <div>
            <el-table :data="apiResult.data">
                <el-table-column prop="detail" label="问题描述"></el-table-column>
                <el-table-column prop="createTime" :sortable="true" width="168" label="发布于"></el-table-column>
                <el-table-column prop="isReply" width="110" label="回复状态">
                    <template #default="scope">
                        <el-tag :type="scope.row.isReply ? 'success' : 'info'" size="mini">{{ scope.row.isReply ? '已回复'
                            : '未回复'
                        }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="" width="150" align="center">
                    <template #default="scope">
                        <div style="display: flex;justify-content: left;align-items: center;gap: 10px;">
                            <div style="display: flex;justify-content: center;align-items: center;gap: 8px;">
                                <div class="operate-buttons" @click="viewproposalFeedbackDetail(scope.row)">
                                    <i class="el-icon-takeaway-box"></i>
                                </div>
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
                    :current-page="proposalFeedbackQueryDto.current" :page-sizes="[10, 20]"
                    :page-size="proposalFeedbackQueryDto.size" layout="total, sizes, prev, pager, next, jumper"
                    :total="apiResult.total"></el-pagination>
            </div>
        </div>

        <!-- 回复内容的抽屉 -->
        <el-drawer size="25%" title="回复内容" :visible.sync="drawer" :direction="direction">
            <div style="padding: 10px;margin: 0 20px;background-color: rgb(245,246,247);">
                <div style="margin-bottom: 10px;display: flex;justify-content: left;align-items: center;gap: 10px;">
                    <img style="width: 20px;height: 20px;border-radius: 50%;" :src="proposalFeedback.avatar" alt=""
                        srcset="">
                    <div style="font-size: 14px;">{{ proposalFeedback.username }}</div>
                </div>
                <div>
                    {{ proposalFeedback.detail }}
                </div>
                <div style="font-size: 12px;margin-block: 10px;">
                    反馈时间：{{ proposalFeedback.createTime }}
                </div>
            </div>
            <div style="margin-inline: 20px;margin-block: 10px;">
                <el-input :disabled="proposalFeedback.isReply" type="textarea" :rows="3" placeholder="回复内容"
                    v-model="proposalFeedback.replyContent">
                </el-input>
                <div v-if="proposalFeedback.isReply" style="font-size: 14px;margin-block: 10px;">
                    回复时间：{{ proposalFeedback.replyTime }}
                </div>
                <div v-if="!proposalFeedback.isReply" class="primary-bt" @click="reply"
                    style="margin-inline: 0;margin-top: 10px;text-align: center;">
                    确定回复
                </div>
            </div>
        </el-drawer>

        <!-- 删除确认弹窗 -->
        <el-dialog title="删除意见与反馈" :show-close="false" :visible.sync="dialogDeletedVisible" width="20%">
            <span>确定删除？</span>
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
            proposalFeedback: {}, // 反馈信息
            drawer: false, // 详情抽屉控制开关变量 - 默认是false：关闭
            direction: 'rtl', // 详情抽屉出现方向
            id: null, // 页面即将删除的数据ID
            apiResult: { // 后端返回的查询数据的响应数据
                data: [], // 数据项
                total: 0, // 符合条件的数据总想 - 初始赋值为0
            },
            proposalFeedbackQueryDto: { // 搜索条件
                current: 1, // 当前页 - 初始是第一页
                size: 10, // 页面显示大小 - 初始是10条
                name: null, // 反馈名
            },
            id: null,
            dialogDeletedVisible: false, // 删除弹窗控制开关变量 - 初始是关（false）
        };
    },
    created() {
        this.fetchFreshData(); // 页面创建时，先去加载反馈数据
    },
    methods: {
        async confirmDeleted() {
            try {
                const { code } = await this.$axios.delete(`/proposal-feedback/${this.id}`);
                if (code === 200) {
                    this.$notify.success({
                        title: '意见与反馈删除',
                        message: '删除成功',
                        position: 'buttom-right',
                        duration: 1000,
                    });
                    this.dialogDeletedVisible = false; // 关闭删除确认弹窗
                    this.id = null; // 将标识ID置位
                    this.fetchFreshData(); // 删除数据之后，重新加载数据
                }
            } catch (error) {
                console.log("删除意见与反馈数据异常：", error);
            }
        },
        // 表格点击删除公告
        handleDelete(row) {
            this.dialogDeletedVisible = true; // 开启删除弹窗确认
            this.id = row.id;
        },
        // 意见与返回回复
        async reply() {
            try {
                const { message } = await this.$axios.post(`/proposal-feedback/update`, this.proposalFeedback);
                this.$notify({
                    title: '意见与反馈',
                    message: message,
                    type: 'success',
                    position: 'bottom-right',
                    duration: 1000
                })
                this.cancelOperation();
                this.fetchFreshData();
            } catch (e) {
                console.log("查询反馈信息异常：", e);
                this.$notify({
                    title: '意见与反馈',
                    message: e.message,
                    type: 'warning',
                    position: 'bottom-right',
                    duration: 1000
                })
            }
        },
        async confirmFeedback() {
            try {
                const { message } = await this.$axios.post(`/proposal-feedback/save`, this.proposalFeedback);
                this.$notify({
                    title: '意见与反馈',
                    message: message,
                    type: 'success',
                    position: 'bottom-right',
                    duration: 1000
                })
                this.noFeedback();
                this.fetchFreshData();
            } catch (e) {
                console.log("查询反馈信息异常：", e);
                this.$notify({
                    title: '意见与反馈',
                    message: e.message,
                    type: 'warning',
                    position: 'bottom-right',
                    duration: 1000
                })
            }
        },
        // 查询详情并打开抽屉容器
        async viewproposalFeedbackDetail(data) {
            this.proposalFeedback = data; // 查询回来的反馈详情赋值
            this.drawer = true; // 打开内容详情抽屉
        },
        // 反馈回复状态选中事件
        handleChange(obj) {
            this.proposalFeedbackQueryDto.isReply = Number(obj.value); // 转成数值类型，再赋值
            this.fetchFreshData(); // 重新加载数据
        },
        // 取消处理
        cancelOperation() {
            this.proposalFeedback = {}; // 将参数对象清置 
            this.drawer = false; // 关闭反馈新增弹窗
        },
        // 输入框组件输入回传
        listener(text) {
            this.proposalFeedbackQueryDto.detail = text; // 赋值查询条件的内容
            this.fetchFreshData(); // 重新加载数据
        },
        // 查询反馈数据
        async fetchFreshData() {
            try {
                const { data, total } = await this.$axios.post('/proposal-feedback/list', this.proposalFeedbackQueryDto);
                this.apiResult.data = data;
                this.apiResult.total = total;
            } catch (error) {
                console.error('查询反馈信息异常:', error);
            }
        },
        // 分页 - 处理页面页数切换
        handleSizeChange(size) {
            this.proposalFeedbackQueryDto.size = size; // 当前页面大小重置
            this.proposalFeedbackQueryDto.currrent = 1; // 当前页设置为第一页
            this.fetchFreshData(); // 重新加载页面数据
        },
        // 分页 - 处理页面当前页切换
        handleCurrentChange(current) {
            this.proposalFeedbackQueryDto.current = current; // 当前页选中
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