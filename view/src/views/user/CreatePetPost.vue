<template>
    <div class="post-container">
        <div class="nav">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/user' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>宠物经验帖子发布</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="content">
            <div class="left">
                <!-- 宠物类型选择 -->
                <div>
                    <p>*宠物类型领域</p>
                    <el-select style="width: 100%;" v-model="petPost.petTypeId" placeholder="请选择">
                        <el-option v-for="item in petTypeList" :key="item.id" :label="item.name" :value="item.id">
                        </el-option>
                    </el-select>
                </div>
                <!-- 摘要信息部分 -->
                <div>
                    <p>*补充摘要</p>
                    <el-input type="textarea" :rows="4" placeholder="请输入内容-255字以内" v-model="petPost.summary">
                    </el-input>
                </div>
                <!-- 封面部分 -->
                <div style="margin-bottom: 20px;">
                    <p>*上传封面</p>
                    <div>
                        <img v-if="cover" style="width: 100%;height: 160px;border-radius: 5px;" :src="cover || ''"
                            alt="">
                        <el-upload class="avatar-uploader" action="http://localhost:21090/api/v1.0/pet-adopt-api/file/upload"
                            :show-file-list="false" :on-success="handleImageSuccess">
                            <i style="font-size: 30px;" class="el-icon-camera-solid"></i>
                        </el-upload>
                    </div>
                </div>
                <div @click="toPetPost" class="publish-pet-post">
                    立即发布
                </div>
            </div>
            <div class="right">
                <input v-model="petPost.title" placeholder="输入标题，30字以内" />
                <div>
                    <Editor style="width: 800px;" height="400px" @on-listener="onListener" :receiveContent="text"
                        api="http://localhost:21090/api/v1.0/pet-adopt-api/file/upload" />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Editor from "@/components/Editor"
export default {
    components: { Editor },
    data() {
        return {
            text: '', // 帖子内容文本
            cover: '', // 封面
            petTypeList: [], // 宠物类别信息数组
            petPost: {}, // 传至后端的宠物帖子数据
        }
    },
    created() {
        this.fetchPetType();
    },
    methods: {
        async toPetPost() {
            try {
                this.petPost.cover = this.cover;
                const { message } = await this.$axios.post(`/pet-post/save`, this.petPost);
                this.$notify({
                    title: '发布宠物经验帖子',
                    type: 'success',
                    message: message,
                    duration: 1000,
                });
                // 返回首页
                this.$router.push('/user');
            } catch (error) {
                this.$notify({
                    title: '参数校验',
                    type: 'info',
                    message: error.message,
                    duration: 1000,
                    position: 'bottom-right'
                })
            }
        },
        onListener(text) {
            this.petPost.content = text;
        },
        // 封面上传响应
        handleImageSuccess(res, file) {
            // 通知提示
            this.$notify({
                title: '封面上传',
                type: res.code === 200 ? 'success' : 'error',
                message: res.code === 200 ? '上传成功' : res.data,
                position: 'buttom-right',
                suration: 1000,
            })
            if (res.code === 200) {
                this.cover = res.data; // 响应里面的data，即后端返回的上传后的图片链接
            }
        },
        // 查询宠物类型信息
        async fetchPetType() {
            try {
                const { data } = await this.$axios.post(`/pet-type/query`, {});
                this.petTypeList = data;
            } catch (error) {
                console.log("查询宠物类别信息异常：", error);
            }
        },
    }
}
</script>

<style land="scss" scoped>
.publish-pet-post {
    background-color: rgb(26, 147, 62);
    color: rgb(255, 255, 255);
    padding: 10px 16px;
    text-align: center;
    border-radius: 20px;
    font-size: 14px;
    cursor: pointer;

    &:hover {
        background-color: rgb(26, 134, 58);
    }
}

.post-container {
    /* background-color: rgb(246, 247, 248); */
    min-height: 100vh;

    .nav {
        background-color: rgb(255, 255, 255);
        padding: 20px 30px;
        border-bottom: 1px solid #eee;
    }


    .content {
        display: flex;

        .left {
            width: 300px;
            background-color: rgb(255, 255, 255);
            height: calc(100vh - 55px);
            padding: 10px 30px;
            box-sizing: border-box;
        }

        .right {
            margin: 0 auto;
            width: 800px;
            /* background-color: rgb(255, 255, 255); */
            height: calc(100vh - 55px);
            padding: 10px 30px;
            box-sizing: border-box;

            input {
                border: none;
                outline: none;
                width: 800px;
                background-color: rgb(246, 247, 248);
                font-size: 32px;
                padding: 20px 30px;
                box-sizing: border-box;
            }
        }
    }
}
</style>