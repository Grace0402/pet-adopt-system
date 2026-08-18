<template>
  <div class="line-chart-container" ref="container">
    <div class="chart-header">
      <span class="chart-title">{{ tag }}</span>
      <div class="chart-controls">
        <span class="time-label">时间范围</span>
        <el-select size="mini" v-model="selectedValue" placeholder="选择期限" @change="handleTimeChange">
          <el-option v-for="item in timeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </div>
    </div>
    <div ref="chart" :style="{ width: '100%', height: height }" class="chart-wrapper"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'EnhancedLineChart',
  props: {
    tag: {
      type: String,
      default: '数据趋势'
    },
    values: {
      type: Array,
      required: true
    },
    date: {
      type: Array,
      required: true
    },
    height: {
      type: String,
      default: '300px'
    },
    subtext: {
      type: String,
      default: '描述'
    }
  },
  data() {
    return {
      chart: null,
      selectedValue: '最近一年',
      timeOptions: [
        { value: 30, label: '最近30天' },
        { value: 90, label: '最近90天' },
        { value: 365, label: '最近一年' }
      ],
      resizeObserver: null,
      animationType: 'wave',
      resizeTimer: null
    };
  },
  watch: {
    values: {
      handler() {
        this.updateChart();
      },
      deep: true
    },
    date: {
      handler() {
        this.updateChart();
      },
      deep: true
    }
  },
  mounted() {
    this.initChart();
    this.setupResizeObserver();
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
        backgroundColor: 'transparent',
        grid: {
          left: '3%',
          right: '4%',
          top: '15%',
          bottom: '12%',
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: {
          type: 'category',
          data: this.date,
          axisLabel: {
            rotate: this.date.length > 15 ? 30 : 0,
            color: '#666',
            interval: 0, // 显示所有日期
            formatter: (value) => {
              return value; // 直接返回日期值
            }
          },
          axisLine: {
            lineStyle: {
              color: '#ddd',
              type: 'dashed'
            }
          },
          axisTick: {
            alignWithLabel: true,
            lineStyle: {
              color: '#ddd',
              type: 'dashed'
            }
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: ['#eee'],
              type: 'dashed'
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLine: {
            show: true,
            lineStyle: {
              color: '#ddd',
              type: 'dashed'
            }
          },
          axisTick: {
            show: true,
            lineStyle: {
              color: '#ddd',
              type: 'dashed'
            }
          },
          axisLabel: {
            color: '#666'
          },
          splitLine: {
            lineStyle: {
              color: ['#eee'],
              type: 'dashed'
            }
          }
        },
        series: [{
          smooth: 0.2,
          symbol: 'circle',
          name: `${this.subtext}`,
          type: 'line',
          smooth: true,
          data: this.values,
          lineStyle: {
            width: 1,
            color: 'rgb(91, 143, 249)'
          },
          areaStyle: {
            color: 'rgba(91, 143, 249, 0.15)'
          },
          animationType: this.animationType,
          animationDuration: 1500,
          emphasis: {
            symbol: 'circle',
            itemStyle: {
              color: 'rgb(91, 143, 249)',
              borderColor: '#fff',
              borderWidth: 4
            }
          },
          label: {
            show: true,
            position: 'top',
            distance: 10,
            color: '#666',
            fontSize: 12,
            formatter: (params) => {
              return params.value;
            },
            textStyle: {
              shadowColor: 'rgba(0, 0, 0, 0.2)',
              shadowBlur: 1
            }
          }
        }]
      };

      // 根据日期数量自动调整显示间隔
      if (this.date.length > 30) {
        option.xAxis.axisLabel.interval = Math.floor(this.date.length / 10);
      }

      this.chart.setOption(option);
    },
    handleTimeChange(value) {
      this.$emit('time-change', value);
    },
    handleResize() {
      if (this.resizeTimer) {
        clearTimeout(this.resizeTimer);
      }
      this.resizeTimer = setTimeout(() => {
        this.chart && this.chart.resize();
        this.resizeTimer = null;
      }, 200);
    },
    setupResizeObserver() {
      if (typeof ResizeObserver === 'undefined') {
        return;
      }

      this.resizeObserver = new ResizeObserver(entries => {
        for (let entry of entries) {
          if (entry.target === this.$refs.container) {
            this.handleResize();
          }
        }
      });

      this.resizeObserver.observe(this.$refs.container);
    },
    cleanup() {
      if (this.chart) {
        this.chart.dispose();
        this.chart = null;
      }

      if (this.resizeObserver) {
        this.resizeObserver.disconnect();
        this.resizeObserver = null;
      }

      if (this.resizeTimer) {
        clearTimeout(this.resizeTimer);
        this.resizeTimer = null;
      }

      window.removeEventListener('resize', this.handleResize);
    }
  }
};
</script>

<style scoped>
.line-chart-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  border-radius: 4px;
  overflow: hidden;
}

.chart-header::after {
  content: '';
  width: 100px;
  height: 12px;
  background-color: rgba(109, 115, 232, 0.4);
  position: absolute;
  top: 30px;
  left: 50px;
}

.chart-header {
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
}

.chart-title {
  font-size: 24px;
  font-weight: 600;
  z-index: 100;
}

.chart-controls {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgb(255, 255, 255);
  box-sizing: border-box;
  padding: 2px 4px;
  gap: 4px;
  border-radius: 4px;
}

::v-deep .el-select .el-input__inner {
  width: 100px !important;
  min-width: 100px;
  background-color: rgb(246, 246, 246);
  border: none;
}

.time-label {
  font-size: 12px;
  color: #666;
}

.chart-wrapper {
  flex: 1;
}
</style>