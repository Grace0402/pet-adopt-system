<template>
    <div>
        <div class="recommend">
            <!-- 推荐区域 -->
              <Carousel @obj-detail="objDetail" containerHeight="80vh" :carouselItems="carouselItems" />
        </div>
        <div class="pet">
            <div class="pet-type">
                <div :class="{ 'active': petTypeId === petType.id }" @click="petTypeClick(petType)"
                    class="pet-type-item" v-for="petType in petTypeList" :key="petType.id">
                    {{ petType.name }}
                </div>
            </div>

            <!-- 宠物列表区域 -->
            <div class="pet-list">
                <!-- 空状态 -->
                <div v-if="petList.length === 0" key="empty">
                    <el-empty description="该类别下，暂无宠物信息"></el-empty>
                </div>

                <!-- 宠物卡片列表 -->
                <template v-else>
                    <div v-for="pet in petList" :key="pet.id" class="pet-card" @click="viewPetDetail(pet.id)">
                        <div class="pet-cover">
                            <img :src="pet.cover" :alt="pet.name">
                            <div v-if="pet.isAdopt" class="adopted-tag">已领养</div>
                            <div v-if="pet.isRecommend" class="recommend-tag">推荐</div>
                        </div>
                        <div class="pet-info">
                            <h3>{{ pet.name }}</h3>
                            <div class="pet-meta">
                                <span>{{ pet.age }}岁</span>
                                <span>{{ pet.gender === 0 ? '公' : '母' }}</span>
                                <span>{{ pet.petTypeName }}</span>
                            </div>
                            <div class="pet-location">
                                <i class="el-icon-location"></i>
                                {{ pet.address }}
                            </div>
                            <div class="pet-vaccine" :class="{ 'vaccined': pet.isVaccine }">
                                {{ pet.isVaccine ? '已接种' : '未接种' }}

                            </div>
                        </div>
                    </div>
                </template>
            </div>
        </div>
    </div>
</template>

<script>
import Carousel from '@/components/Carousel.vue';
export default {
    components: { Carousel },
    name: 'Home',
    data() {
        return {
            petTypeList: [],
            petTypeId: null,
            petQueryDto: {},
            petList: [],
            recommendPetList: [],
            carouselItems: []
        }
    },
    created() {
        this.fetchPetType();
        this.fetchAutoRecommend();
    },
    methods: {
        objDetail(obj){
            this.viewPetDetail(obj.id);
        },
        async fetchAutoRecommend() {
            try {
                const { data } = await this.$axios.get(`/pet/autoRecommend/${4}`);
                this.carouselItems = data.map(entity=>{
                    return {
                        id: entity.id,
                        image: entity.cover,
                        title: entity.name,
                        subtitle: entity.address
                    }
                });
            } catch (error) {
                console.log("查询推荐宠物信息异常：", error);
            }
        },
        petTypeClick(petType) {
            this.petTypeId = petType.id;
            this.petQueryDto.petTypeId = petType.id;
            this.fetchPet();
        },
        async fetchPetType() {
            try {
                const { data } = await this.$axios.post('/pet-type/query', {});
                this.petTypeList = data;
                this.petTypeList.unshift({ id: null, name: '全部' });
                if (this.petTypeList.length > 0) {
                    this.petTypeClick(this.petTypeList[0]);
                }
            } catch (error) {
                console.log("查询宠物类别信息异常：", error);
            }
        },
        async fetchPet() {
            try {
                const { data } = await this.$axios.post('/pet/list', this.petQueryDto);
                this.petList = data;
            } catch (error) {
                console.log("查询宠物信息异常：", error);
            }
        },
        viewPetDetail(id) {
            window.open(`pet-detail?id=${id}`, '_blank');
        }
    }
}
</script>

<style scoped lang="scss">
.recommend {
    width: 100%;
    // background-color: rgba(26, 147, 62, 0.1);
    margin-bottom: 30px;
}

.pet {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 15px;

    .pet-type {
        margin-block: 20px;
        display: flex;
        gap: 10px;
        flex-wrap: wrap;

        .pet-type-item {
            cursor: pointer;
            padding: 8px 16px;
            border-radius: 10px;
            transition: all 0.3s ease;

            &:hover {
                background-color: rgb(240, 240, 240);
            }

            &.active {
                background-color: #1a933e;
                color: white;
            }
        }
    }

    .pet-list {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
        margin-top: 10px;

        .pet-card {
            width: calc((100% - 100px) / 4); // 6个卡片每行
            min-width: 180px;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            cursor: pointer;
            background: white;

            &:hover {
                transform: translateY(-5px);
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            }

            .pet-cover {
                position: relative;
                height: 180px;
                overflow: hidden;

                img {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                    transition: transform 0.5s ease;
                }

                &:hover img {
                    transform: scale(1.05);
                }

                .adopted-tag,
                .recommend-tag {
                    position: absolute;
                    top: 10px;
                    padding: 2px 8px;
                    border-radius: 4px;
                    color: white;
                    font-size: 12px;
                }

                .adopted-tag {
                    right: 10px;
                    background-color: #f56c6c;
                }

                .recommend-tag {
                    left: 10px;
                    background-color: #1a933e;
                }
            }

            .pet-info {
                padding: 15px;

                h3 {
                    margin: 0 0 8px;
                    font-size: 16px;
                    color: #333;
                    white-space: nowrap;
                    overflow: hidden;
                    text-overflow: ellipsis;
                }

                .pet-meta {
                    display: flex;
                    gap: 8px;
                    margin-bottom: 8px;
                    font-size: 12px;
                    color: #666;
                }

                .pet-location {
                    font-size: 12px;
                    color: #999;
                    margin-bottom: 8px;
                    display: flex;
                    align-items: center;

                    i {
                        margin-right: 4px;
                    }
                }

                .pet-vaccine {
                    font-size: 12px;
                    padding: 2px 8px;
                    border-radius: 4px;
                    display: inline-block;
                    background-color: #f0f0f0;
                    color: #666;

                    &.vaccined {
                        background-color: #e1f3d8;
                        color: #1a933e;
                    }
                }
            }
        }
    }
}

/* 列表动画效果 */
.list-enter-active,
.list-leave-active {
    transition: all 0.5s ease;
}

.list-enter,
.list-leave-to {
    opacity: 0;
    transform: translateY(30px);
}

.list-move {
    transition: transform 0.5s ease;
}

/* 响应式调整 */
@media (max-width: 1200px) {
    .pet-list .pet-card {
        width: calc((100% - 80px) / 5);
    }
}

@media (max-width: 992px) {
    .pet-list .pet-card {
        width: calc((100% - 60px) / 4);
    }
}

@media (max-width: 768px) {
    .pet-list .pet-card {
        width: calc((100% - 40px) / 3);
    }
}

@media (max-width: 576px) {
    .pet-list .pet-card {
        width: calc((100% - 20px) / 2);
    }
}
</style>