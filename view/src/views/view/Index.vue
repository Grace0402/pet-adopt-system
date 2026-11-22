<template>
    <div>
        <div class="top">
            <div class="logo">
                <Logo sysName="宠物领养系统" textColor="rgb(255,255,255)" />
                <div @click="goLogin" class="nav-operation">
                    登录/注册
                </div>
            </div>
            <Carousel @obj-detail="objDetail" containerHeight="100vh" :carouselItems="carouselItems" />
        </div>
        <div class="pet-info">
            <div class="info">
                <h1>宠物信息</h1>
                <div class="pet">
                    <div @click="petInfo(pet)" class="pet-item" v-for="pet in petList" :key="pet.id">
                        <div class="cover">
                            <img :src="pet.cover" alt="" srcset="">
                        </div>
                        <div class="name">{{ pet.name }}</div>
                    </div>
                </div>

            </div>
        </div>
        <div class="bottom">
            <div>&copy; 2025年度《手把手带敲系列》之宠物领养系统 - B站「程序员辰星」原创出品</div>
        </div>
    </div>
</template>

<script>
import Carousel from '@/components/Carousel.vue';
import Logo from '@/components/Logo.vue';
import { getToken } from "@/utils/storage.js";
export default {
    components: { Carousel, Logo },
    data() {
        return {
            carouselItems: [], // 轮播图数据
            petTypeList: [], //宠物类别数据
            petTypeId: null,
            petList: []
        }
    },
    created() {
        this.fetchRecommendPetInfo();
        this.fetchPetInfo();
        this.auth();
    },
    methods: {
        async auth() {
            const token = getToken();
            if (token === null) { // 没有登录不做处理
                return;
            }
            try {
                await this.$axios.get('/user/auth');
                this.$router.push('/user');
            } catch (error) {
                // 认证不通过
            }
        },
        petInfo(pet) {
            window.open(`view-pet-detail?id=${pet.id}`, '_blank');
        },
        objDetail(obj) {
            window.open(`view-pet-detail?id=${obj.id}`, '_blank');
        },
        goLogin() {
            window.open('/login', '_blank');
        },
        // 查询宠物信息
        async fetchPetInfo() {
            try {
                const petQueryDto = { current: 1, size: 6 }
                const response = await this.$axios.post('/pet/list', petQueryDto);
                this.petList = response.data;
            } catch (error) {
                console.log("首页查询宠物信息：", error);
            }
        },
        // 查询推荐的宠物推荐信息
        async fetchRecommendPetInfo() {
            try {
                const response = await this.$axios.get('/pet/recommend');
                this.carouselItems = response.data.map(petItem => {
                    return {
                        id: petItem.id,
                        image: petItem.cover,
                        title: petItem.name,
                        subtitle: petItem.address
                    }
                });
            } catch (error) {
                console.log("首页查询推荐宠物信息：", error);

            }
        },
    }
}
</script>

<style scoped lang="scss">
.pet {
    margin-block: 20px;
    display: flex;
    flex-wrap: wrap;
    gap: 10px;

    .pet-item {
        flex: 0 0 calc(25% - 10px);
        cursor: pointer;
        padding: 10px;
        box-sizing: border-box;

        // &:hover {
        //     box-sizing: 2px 4px 8px rgb(79, 51, 51);
        // }

        .cover {
            img {
                width: 260px;
                height: 190px;
                border-radius: 5px;
            }
        }

        .name {
            font-size: 20px;
        }
    }
}

.top {
    min-height: 100vh;
    width: 100%;
    position: relative;

    .logo {
        position: absolute;
        top: 0;
        z-index: 100;
        width: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 10px 20px;
        box-sizing: border-box;
        background-color: rgba(0, 0, 0, 0.1);

        .nav-operation {
            background-color: rgb(255, 215, 0);
            border-radius: 30px;
            padding: 10px 20px;
            cursor: pointer;

            &:hover {
                background-color: rgb(237, 202, 3);
            }
        }
    }
}

.pet-info {
    width: 100%;
    display: flex;
    justify-content: center;

    .info {
        width: 900px;
        padding-block: 30px;
    }
}

.bottom {
    height: 50px;
    width: 100%;
    background-color: rgb(51, 51, 51);
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 14px;
    color: rgb(255, 255, 255);
}
</style>