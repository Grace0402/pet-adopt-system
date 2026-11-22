<template>
    <div class="main-contianer">
        <div class="left">
            <div class="static-area">
                <div class="item-static" v-for="(item, index) in staticData" :key="index">
                    <div class="count">{{ item.count }}</div>
                    <div class="text">{{ item.name }}</div>
                </div>
            </div>
            <div class="chart-top">
                <div class="bar">
                    <BarChart height="400px" tag="领养订单创建" :values="value" :date="date" />
                </div>
                <div class="pie">
                    <PieCharts height="400px" :data="petTypeCountList" />
                </div>
            </div>
        </div>
        <div class="right">
            <div style="margin-bottom: 20px;font-size: 26px;">宠物信息</div>
            <div v-for="(item, index) in petList" :key="index">
                <div>
                    <img style="width: 100%;height: 110px;" :src="item.cover" alt="">
                </div>
                <div @click="viewPetDetail(item.id)" style="margin-block: 10px;font-size: 20px;cursor: pointer;">
                    {{ item.name }}
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import PieCharts from '@/components/PieCharts.vue';
import BarChart from '@/components/BarChart.vue';

export default {
    components: { PieCharts, BarChart },
    data() {
        return {
            staticData: [],
            petTypeCountList: [],
            value: [],
            date: [],
            petList: []
        }
    },
    created() {
        this.fetchStatic();
        this.fetchPetTypeCount();
        this.fetchPetAdoptOrderCount(365);
        this.fetchPetData();
    },
    methods: {
        viewPetDetail(id) {
            window.open(`pet-detail-admin?id=${id}`, '_blank');
        },
        async fetchPetData() {
            try {
                const petQueryDto = {
                    size: 3,
                    current: 1
                }
                const { data } = await this.$axios.post(`/pet/list`, petQueryDto);
                this.petList = data;
            } catch (e) {
                console.log("首页宠物信息异常：", e);
            }
        },
        async fetchPetAdoptOrderCount(days) {
            try {
                const { data } = await this.$axios.get(`/main/petAdoptOrderCount/${days}`);
                this.value = data.map(entity => entity.count);
                this.date = data.map(entity => entity.name);
            } catch (e) {
                console.log("首页宠物领养订单异常：", e);
            }
        },
        async fetchPetTypeCount() {
            try {
                const { data } = await this.$axios.get('/main/petTypeCount');
                this.petTypeCountList = data.map(entity => {
                    return {
                        name: entity.name,
                        value: entity.count
                    }
                });
            } catch (e) {
                console.log("首页查询宠物类别下的宠物数异常：", e);
            }
        },
        async fetchStatic() {
            try {
                const { data } = await this.$axios.get('/main/staticData');
                this.staticData = data;
            } catch (e) {
                console.log("首页查询静态数据异常：", e);
            }
        },
    }
};
</script>
<style scoped lang="scss">
.main-contianer {
    margin: 0 30px;
    box-sizing: border-box;
    display: flex;
    overflow-x: hidden;
    .left {
        width: 84%;

        .static-area {
            background-color: rgb(248, 248, 248);
            display: flex;
            justify-content: space-evenly;

            .item-static {
                .count {
                    text-align: center;
                    padding-block: 10px;
                    font-size: 24px;
                    font-weight: 800;
                }

                .text {
                    font-size: 16px;
                    margin-bottom: 12px;
                }
            }
        }

        .chart-top {
            display: flex;
            justify-content: space-evenly;

            .bar {
                width: 50%;
            }

            .pie {
                width: 50%;
            }
        }
    }

    .right {
        width: 16%;
        padding: 10px;
    }
}
</style>