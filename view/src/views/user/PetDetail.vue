<template>
    <div class="pet-detail-container">

        <!-- 宠物基本信息 -->
        <div class="pet-basic-info">
            <h1 class="pet-name">{{ petInfo.name }}</h1>
            <div class="meta-info">
                <span class="pet-type">{{ petInfo.petTypeName }}</span>
                <span class="pet-gender">{{ petInfo.gender | genderFilter }}</span>
                <span class="pet-age">{{ petInfo.age }}个月</span>
            </div>

            <!-- 疫苗状态 -->
            <div class="vaccine-status" :class="{ 'vaccined': petInfo.isVaccine }">
                {{ petInfo.isVaccine ? '已接种疫苗' : '未接种疫苗' }}
            </div>
        </div>

        <!-- 宠物封面图片 -->
        <div class="pet-cover">
            <img :src="petInfo.cover" :alt="petInfo.name" class="cover-image">
        </div>

        <!-- 宠物详情描述 -->
        <div class="pet-detail" v-html="petInfo.detail"></div>

        <!-- 点赞及收藏区域 -->
        <div class="operation-area">
            <div class="box">
                <!-- 收藏区域 -->
                <div class="save" @click="saveOperation">
                    <div style="display: flex;justify-content: center;align-items: center;">
                        <i :style="{ color: saveStatus ? 'rgb(216, 30, 6)' : 'rgb(0, 0, 0)' }"
                            class="el-icon-star-on"></i>
                    </div>
                    <div class="text">
                        {{ saveStatus ? '取消收藏' : '收藏' }}
                    </div>
                </div>
                <!-- 点赞区域 -->
                <div class="like" @click="upvoteOperation">
                    <div style="display: flex;justify-content: center;align-items: center;">
                        <img :src="upvoteStatus ? '/update-default.png' : '/update-active.png'" alt="" srcset="">
                    </div>
                    <div class="text">
                        {{ upvoteStatus ? '取消点赞' : '点赞' }}
                    </div>
                </div>
            </div>
        </div>

        <!-- 领养信息 -->
        <div class="adopt-info">
            <div class="address">
                <i class="el-icon-location-outline"></i>
                <span>{{ petInfo.address }}</span>
            </div>

            <!-- 领养按钮 -->
            <el-button v-if="!petInfo.isAdopt" type="primary" class="adopt-btn" @click="handleAdopt">
                申请领养
            </el-button>
            <div v-else class="adopted-tag">
                <el-tag type="info">已被领养</el-tag>
            </div>
        </div>

        <!-- 评论区 -->
        <div class="evaluations-info">
            <Evaluations :avatar="userInfo.avatar" :userId="userInfo.id" :contentId="Number(queryDto.id)"
                contentType="PET" />
        </div>

        <el-drawer size="40%" title="申请领养宠物" :visible.sync="drawer" :direction="direction" :before-close="handleClose">
            <div style="margin: 10px 20px;">
                <div style="margin-bottom: 20px;">
                    <div v-if="active === 0">
                        <div
                            style="font-size: 14px;color: rgb(229, 62, 48);background-color: rgb(248,248,248);width: 100%;padding: 10px 20px;box-sizing: border-box;">
                            步骤一：先确保期待领养的宠物信息是否正确
                        </div>
                    </div>
                    <div v-else-if="active === 1">
                        <div
                            style="font-size: 14px;color: rgb(229, 62, 48);background-color: rgb(248,248,248);width: 100%;padding: 10px 20px;box-sizing: border-box;">
                            步骤二：请如实填写领养证明材料，包括但不限于自己的基本家庭情况、教育经历、与宠物的故事等
                        </div>
                    </div>
                    <div v-else="active === 2">
                        <div
                            style="font-size: 14px;color: rgb(229, 62, 48);background-color: rgb(248,248,248);width: 100%;padding: 10px 20px;box-sizing: border-box;">
                            步骤三：请选择一个收货地址，收货地址可在「首页 - 收货地址」处进行维护
                        </div>
                    </div>
                </div>
                <el-steps :active="active" finish-status="success">
                    <el-step title="确认宠物基本信息"></el-step>
                    <el-step title="填写证明材料"></el-step>
                    <el-step title="确定收货地址"></el-step>
                </el-steps>
                <div style="margin-block: 20px;">
                    <div v-if="active === 0">
                        <el-descriptions class="margin-top" title="" :column="2" :size="size" border>
                            <el-descriptions-item>
                                <template slot="label">
                                    宠物图片
                                </template>
                                <img :src="petInfo.cover" style="height: 70px;width: 100px;" alt="宠物图片">
                            </el-descriptions-item>
                            <el-descriptions-item>
                                <template slot="label">
                                    宠物名
                                </template>
                                {{ petInfo.name }}
                            </el-descriptions-item>
                            <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-location-outline"></i>
                                    当前所在地址
                                </template>
                                {{ petInfo.address }}
                            </el-descriptions-item>
                            <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-tickets"></i>
                                    性别
                                </template>
                                <el-tag size="small">{{ petInfo.gender === 1 ? '公' : '母' }}</el-tag>
                            </el-descriptions-item>
                            <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-tickets"></i>
                                    宠物类目
                                </template>
                                <el-tag type="success" size="small">{{ petInfo.petTypeName }}</el-tag>
                            </el-descriptions-item>
                            <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-tickets"></i>
                                    疫苗状态
                                </template>
                                <el-tag type="success" size="small">{{ petInfo.isVaccine ? '已接种疫苗' : '未接种疫苗' }}</el-tag>
                            </el-descriptions-item>
                            <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-tickets"></i>
                                    年龄
                                </template>
                                {{ petInfo.age }}个月
                            </el-descriptions-item>
                        </el-descriptions>
                    </div>
                    <div v-else-if="active === 1">
                        <Editor api="http://localhost:21090/api/v1.0/pet-adopt-api/file/upload" height="200px" :receiveContent="receiveContent"
                            @on-listener="onListener" />
                    </div>
                    <div v-else="active === 2">
                        <div v-if="addressList.length === 0"
                            style="font-size: 14px;color: rgb(229, 62, 48);background-color: rgb(248,248,248);width: 100%;padding: 10px 20px;box-sizing: border-box;">
                            用户您好！检测到您的账户名下，暂无任何收货地址信息，请先去维护必要的收货地址信息。
                        </div>
                        <div :style="{ color: selectedAddress.id === address.id ? 'rgb(0, 0, 255)' : '', border: selectedAddress.id === address.id ? '1px solid rgb(0, 0, 255)' : '' }"
                            @click="addressClick(address)" class="address-item" v-for="(address, index) in addressList"
                            :key="index">
                            {{ address.addressee }} - {{ address.concatPhone }} - {{ address.detail }}
                        </div>
                    </div>
                </div>
                <div>
                    <el-button icon="el-icon-back" v-if="active !== 0" type="primary" style="margin-top: 12px;"
                        @click="last">上一步</el-button>
                    <!-- B站「程序员辰星」原创出品，除此之外全是盗版，盗版毫无保障 -->
                    <el-button icon="el-icon-right" v-if="active !== 2" type="primary" style="margin-top: 12px;"
                        @click="next">下一步</el-button>
                    <el-button icon="el-icon-success" v-if="active === 2" type="primary" style="margin-top: 12px;"
                        @click="postPetAdoptOrder">确认提交</el-button>
                </div>

            </div>

        </el-drawer>

    </div>
</template>

<script>
import Evaluations from '@/components/Evaluations.vue';
import Editor from '@/components/Editor.vue';
import { getUserInfo } from "@/utils/storage"
export default {
    components: { Evaluations, Editor },
    data() {
        return {
            petInfo: {},
            active: 0,
            drawer: false,
            size: '',
            direction: 'rtl',
            queryDto: {},
            userInfo: {},
            saveActiveNetId: null,
            upvoteActiveNetId: null,
            addressList: [],
            selectedAddress: {},
            receiveContent: '', // 证明材料
            saveStatus: false, // 收藏状态，初始值false，表示未收藏
            upvoteStatus: false, // 点赞状态，初始值false，表示未点赞
        }
    },
    filters: {
        genderFilter(val) {
            return val === 1 ? '公' : '母'
        }
    },
    created() {
        this.queryDto = this.$router.currentRoute.query;
        this.fetchPetInfo(this.queryDto.id);
        this.userInfo = getUserInfo();

        this.fetchSaveStatus();
        this.fetchUpvoteStatus();
    },
    methods: {
        async postPetAdoptOrder() {
            try {
                if (this.receiveContent === '<p><br></p>') {
                    this.$notify({
                        title: '宠物领养',
                        type: 'info',
                        message: '请补充证明材料',
                        position: 'buttom-right',
                        duration: 1000,
                    });
                    return;
                }
                const petAdoptOrder = {
                    detail: this.receiveContent,
                    addressId: this.selectedAddress.id,
                    petId: this.petInfo.id,
                }
                const { message } = await this.$axios.post('/pet-adopt-order/save', petAdoptOrder);
                this.$notify({
                    title: '宠物领养',
                    type: 'success',
                    message: message,
                    position: 'buttom-right',
                    duration: 1000,
                });
                this.drawer = false;
                this.receiveContent = '';
                this.selectedAddress = {};
            } catch (error) {
                console.log("领养宠物信息异常", error);
                this.$notify({
                    title: '宠物领养',
                    type: 'info',
                    message: error.message,
                    position: 'buttom-right',
                    duration: 1000,
                })
            }
        },
        addressClick(address) {
            this.selectedAddress = address;
        },
        onListener(text) {
            this.receiveContent = text;
        },
        last() {
            if (this.active-- <= 0) this.active = 2;
        },
        next() {
            if (this.active++ > 2) this.active = 0;
        },
        handleClose() {
            this.drawer = false;
        },
        // 查询用户自己的收货地址
        async fetchAddress() {
            try {
                const { data } = await this.$axios.post('/address/queryUser', {});
                this.addressList = data;
                const defaultAddressList = this.addressList.filter(entity => entity.isDefault);
                if (defaultAddressList.length !== 0) {
                    this.selectedAddress = defaultAddressList[0];
                }
            } catch (error) {
                console.log("查询用户自己的收货地址信息异常：", error);
            }
        },
        // 查询用户与宠物信息的点赞关系
        async fetchUpvoteStatus() {
            try {
                const activeNetQueryDto = {
                    contentId: this.queryDto.id,
                    contentType: 'PET',
                    type: 2, // 行为类型，2是点赞
                }
                const { data } = await this.$axios.post('/active-net/queryUser', activeNetQueryDto);
                this.upvoteStatus = data.length != 0;
                if (data.length != 0) {
                    this.upvoteActiveNetId = data[0].id;
                }
            } catch (error) {
                console.log("查询宠物点赞信息异常：", error);
            }
        },
        // 查询用户与宠物信息的收藏关系
        async fetchSaveStatus() {
            try {
                const activeNetQueryDto = {
                    contentId: this.queryDto.id,
                    contentType: 'PET',
                    type: 3, // 行为类型，3是收藏
                }
                const { data } = await this.$axios.post('/active-net/queryUser', activeNetQueryDto);
                this.saveStatus = data.length != 0;
                if (data.length != 0) {
                    this.saveActiveNetId = data[0].id;
                }
            } catch (error) {
                console.log("查询宠物收藏信息异常：", error);
            }
        },
        // 点赞处理方法
        upvoteOperation() {
            if (!this.upvoteStatus) {
                // 新增用户与宠物信息之间的点赞关系建立 - 新增
                const activeNet = {
                    contentId: this.queryDto.id,
                    contentType: 'PET',
                    type: 2
                }
                this.$axios.post('/active-net/save', activeNet).then(response => {
                    if (response.code === 200) {
                        this.fetchUpvoteStatus();
                        this.$notify({
                            duration: 1000,
                            title: '点赞操作',
                            message: '点赞成功',
                            type: 'success'
                        });
                    }
                }).catch(error => {
                    console.log("用户与宠物信息之间的点赞关系建立异常：", error);
                })
            } else {
                if (this.upvoteActiveNetId === null) {
                    console.log("点赞行为互动类型Id为null");
                    return;
                }
                // 删除用户与宠物信息之间建立的收藏关系 - 删除
                this.$axios.delete(`/active-net/${this.upvoteActiveNetId}`).then(response => {
                    if (response.code === 200) {
                        this.fetchUpvoteStatus();
                        this.$notify({
                            duration: 1000,
                            title: '点赞操作',
                            message: '取消点赞成功',
                            type: 'success'
                        });
                    }
                }).catch(error => {
                    console.log("用户与宠物信息之间的点赞关系删除异常：", error);
                })
            }
        },
        // 收藏处理方法
        saveOperation() {
            if (!this.saveStatus) {
                // 新增用户与宠物信息之间的收藏关系建立 - 新增
                const activeNet = {
                    contentId: this.queryDto.id,
                    contentType: 'PET',
                    type: 3
                }
                this.$axios.post('/active-net/save', activeNet).then(response => {
                    if (response.code === 200) {
                        this.fetchSaveStatus();
                        this.$notify({
                            duration: 1000,
                            title: '收藏操作',
                            message: '收藏成功',
                            type: 'success'
                        });
                    }
                }).catch(error => {
                    console.log("用户与宠物信息之间的收藏关系建立异常：", error);
                })
            } else {
                if (this.saveActiveNetId === null) {
                    console.log("收藏行为互动类型Id为null");
                    return;
                }
                // 删除用户与宠物信息之间建立的收藏关系 - 删除
                this.$axios.delete(`/active-net/${this.saveActiveNetId}`).then(response => {
                    if (response.code === 200) {
                        this.fetchSaveStatus();
                        this.$notify({
                            duration: 1000,
                            title: '收藏操作',
                            message: '取消收藏成功',
                            type: 'success'
                        });
                    }
                }).catch(error => {
                    console.log("用户与宠物信息之间的收藏关系删除异常：", error);
                })
            }
        },
        // 查询宠物信息
        async fetchPetInfo(id) {
            try {
                const { data } = await this.$axios.get(`/pet/${id}`);
                this.petInfo = data;
            } catch (error) {
                console.log("查询宠物详情信息异常：", error);
            }
        },
        handleAdopt() {
            this.drawer = true;
            this.fetchAddress();
        }
    }
}
</script>

<style land="scss" scoped>
.address-item {
    background-color: rgb(248, 248, 248);
    padding: 10px 20px;
    box-sizing: border-box;
    margin-bottom: 1px;
    font-size: 14px;
    cursor: pointer;
    border: 1px solid rgb(248, 248, 248);

    &:hover {
        background-color: rgb(240, 240, 240);
    }
}

.operation-area {
    display: flex;
    justify-content: center;
    align-items: center;

    .box {
        display: flex;
        gap: 20px;

        .text {
            font-size: 14px;
            font-weight: 600;
            color: rgb(81, 91, 101);
        }

        .like {
            padding: 20px;
            border-radius: 5px;
            cursor: pointer;

            img {
                width: 25px;
                height: 25px;
            }

            &:hover {
                background-color: rgb(230, 248, 248);
            }
        }

        .save {
            padding: 20px;
            border-radius: 5px;
            cursor: pointer;

            i {
                font-size: 25px;
            }

            &:hover {
                background-color: rgb(230, 248, 248);
            }
        }
    }
}

.active-net {
    display: flex;
    justify-content: center;
    align-items: center;

    .box {
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 20px;

        .icon {
            display: flex;
            justify-content: center;
        }

        img {
            width: 22px;
            height: 22px;
        }

        i {
            font-size: 22px;
        }
    }

}

.pet-detail-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background: #fff;
    border-radius: 8px;
    /* box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); */
}

.cover-image {
    width: 100%;
    max-height: 400px;
    object-fit: cover;
    border-radius: 8px;
}

.pet-basic-info {
    margin: 20px 0;
    padding-bottom: 15px;
    border-bottom: 1px solid #eee;
}

.pet-name {
    font-size: 42px;
    color: #333;
    margin-bottom: 10px;
}

.meta-info {
    display: flex;
    gap: 15px;
    color: #666;
    font-size: 14px;
    margin-bottom: 10px;
}

.vaccine-status {
    display: inline-block;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 12px;
    background: #fef0f0;
    color: #f56c6c;
}

.vaccine-status.vaccined {
    background: #f0f9eb;
    color: #67c23a;
}

.pet-detail {
    margin: 20px 0;
    line-height: 1.6;
    color: #666;
}

.pet-detail>>>ul {
    padding-left: 20px;
}

.pet-detail>>>li {
    margin-bottom: 8px;
}

.adopt-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 30px;
    padding-top: 15px;
    padding-bottom: 15px;
    background-color: rgb(230, 248, 248);
    padding-inline: 20px;
}

.address {
    color: #666;
    font-size: 14px;
}

.address i {
    margin-right: 5px;
}

.adopt-btn {
    width: 120px;
}

.adopted-tag {
    padding: 8px 0;
}
</style>