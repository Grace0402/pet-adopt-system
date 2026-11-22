<template>
    <div class="notice-detail-container">
        <div class="detail">
            <!-- 公告基本信息 -->
            <div class="notice-basic-info">
                <h1 class="notice-title">{{ notice.title }}</h1>
                <div>发布于{{ notice.createTime }}</div>
            </div>

            <!-- 公告描述 -->
            <div class="notice-detail" v-html="notice.content"></div>
            <!-- 评论区 -->
            <div class="evaluations-info">
                <Evaluations :avatar="userInfo.avatar" :userId="userInfo.id" :contentId="Number(queryDto.id)"
                    contentType="NOTICE" />
            </div>
        </div>

    </div>
</template>

<script>
import Evaluations from '@/components/Evaluations.vue';
import { getUserInfo } from "@/utils/storage"
export default {
    components: { Evaluations },
    data() {
        return {
            notice: {},
            queryDto: {},
            userInfo: {}
        }
    },
    created() {
        this.queryDto = this.$router.currentRoute.query;
        this.fetchNotice(this.queryDto.id);
        this.userInfo = getUserInfo();
    },
    methods: {
        // 查询公告信息
        async fetchNotice(id) {
            try {
                const { data } = await this.$axios.get(`/notice/getById/${id}`);
                this.notice = data;
            } catch (error) {
                console.log("查询公告信息异常：", error);
            }
        },
    }
}
</script>

<style land="scss" scoped>
.notice-detail-container {
    background-color: rgb(245, 246, 247);

    .detail {
        width: 700px;
        background-color: rgb(255, 255, 255);
        margin: 0 auto;
        padding: 30px 40px;
        box-sizing: border-box;
        box-shadow: 0 4px 8px rgb(230,230,230);
    }
}
</style>