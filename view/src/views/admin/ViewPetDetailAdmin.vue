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

        <!-- 领养信息 -->
        <div class="adopt-info">
            <div class="address">
                <i class="el-icon-location-outline"></i>
                <span>{{ petInfo.address }}</span>
            </div>
        </div>

    </div>
</template>

<script>
import Evaluations from '@/components/Evaluations.vue';
export default {
    components: { Evaluations },
    data() {
        return {
            petInfo: {},
            queryDto: {},
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
    },
    methods: {
        // 查询宠物信息
        async fetchPetInfo(id) {
            try {
                const { data } = await this.$axios.get(`/pet/viewById/${id}`);
                this.petInfo = data;
            } catch (error) {
                console.log("查询宠物详情信息异常：", error);
            }
        },
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