<template>
  <div class="pie-chart-container">
    <div ref="chart" :style="{ width: '100%', height: height }"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'BasicPieChart',
  props: {
    height: {
      type: String,
      default: '400px'
    },
    data: {
      type: Array,
      required: true,
      validator: value => value.every(item => 'name' in item && 'value' in item)
    }
  },
  data() {
    return {
      chart: null,
      colorPalette: [
        '#5470C6', '#91CC75', '#FAC858', '#EE6666',
        '#73C0DE', '#3BA272', '#FC8452', '#9A60B4'
      ]
    };
  },
  watch: {
    data: {
      deep: true,
      handler() {
        this.updateChart();
      }
    }
  },
  mounted() {
    this.initChart();
    window.addEventListener('resize', this.handleResize);
  },
  beforeDestroy() {
    this.cleanup();
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$refs.chart);
      this.updateChart();
    },
    updateChart() {
      if (!this.chart) return;

      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        series: [{
          name: '数据分布',
          type: 'pie',
          radius: '70%',
          data: this.data.map((item, index) => ({
            ...item,
            itemStyle: {
              color: this.colorPalette[index % this.colorPalette.length]
            }
          })),
          label: {
            show: true,
            formatter: '{b}: {c}'
          }
        }]
      };

      this.chart.setOption(option);
    },
    handleResize() {
      if (this.chart) {
        this.chart.resize();
      }
    },
    cleanup() {
      if (this.chart) {
        this.chart.dispose();
      }
      window.removeEventListener('resize', this.handleResize);
    }
  }
};
</script>

<style scoped>
.pie-chart-container {
  width: 100%;
  height: 100%;
}
</style>