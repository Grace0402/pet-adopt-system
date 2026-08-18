<template>
  <div class="bar-chart-container">
    <div class="chart-header">
      <span class="tag">{{ tag }}</span>
    </div>
    <div ref="chart" :style="{ width: '100%', height: height }" class="chart-wrapper"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'AnimatedBarChart',
  props: {
    height: {
      type: String,
      default: '300px'
    },
    tag: {
      type: String,
      default: '数据统计'
    },
    values: {
      type: Array,
      required: true
    },
    date: {
      type: Array,
      required: true
    },
    showControls: {
      type: Boolean,
      default: false
    },
    animationType: {
      type: String,
      default: 'ripple',
      validator: value => ['ripple', 'scale', 'bounce'].includes(value)
    }
  },
  data() {
    return {
      chart: null,
      currentAnimation: this.animationType,
      colorPalette: [
        '#5470C6', '#91CC75', '#FAC858', '#EE6666',
        '#73C0DE', '#3BA272', '#FC8452', '#9A60B4'
      ],
      resizeObserver: null,
      resizeTimer: null
    };
  },
  watch: {
    values: {
      deep: true,
      handler() {
        this.updateChart();
      }
    },
    date: {
      deep: true,
      handler() {
        this.updateChart();
      }
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
          bottom: '10%',
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          formatter: params => {
            return `
              <div style="font-weight:bold;margin-bottom:5px">${params[0].name}</div>
              <div style="display:flex;align-items:center">
                <span style="display:inline-block;width:10px;height:10px;border-radius:50%;background:${params[0].color};margin-right:5px"></span>
                ${params[0].seriesName}: <strong style="margin-left:5px">${params[0].value}</strong>
              </div>
            `;
          }
        },
        xAxis: {
          type: 'category',
          data: this.date,
          axisLine: {
            lineStyle: {
              color: '#6E7079',
              type: 'dashed' // 横坐标轴线改为虚线
            }
          },
          axisTick: {
            alignWithLabel: true,
            lineStyle: {
              type: 'dashed', // 横坐标刻度线改为虚线
              color: '#6E7079'
            }
          },
          axisLabel: {
            color: '#6E7079',
            interval: 0,
            rotate: this.date.length > 10 ? 45 : 0,
            fontSize: 12
          },
          splitLine: {
            show: true, // 显示横坐标网格线
            lineStyle: {
              type: 'dashed', // 横坐标网格线为虚线
              color: '#E0E6F1'
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLine: {
            show: true,
            lineStyle: {
              color: '#6E7079',
              type: 'dashed' // 纵坐标轴线改为虚线
            }
          },
          axisTick: {
            show: true,
            lineStyle: {
              type: 'dashed', // 纵坐标刻度线改为虚线
              color: '#6E7079'
            }
          },
          axisLabel: {
            color: '#6E7079',
            fontSize: 12
          },
          splitLine: {
            lineStyle: {
              type: 'dashed', // 纵坐标网格线保持虚线
              color: '#E0E6F1'
            }
          }
        },
        series: [{
          name: this.tag,
          type: 'bar',
          barWidth: '60%',
          data: this.values,
          itemStyle: {
            color: params => {
              return new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: this.colorPalette[params.dataIndex % this.colorPalette.length] },
                { offset: 1, color: this.colorPalette[(params.dataIndex + 4) % this.colorPalette.length] }
              ]);
            },
            borderRadius: [4, 4, 0, 0],
            shadowColor: 'rgba(0, 0, 0, 0.1)',
            shadowBlur: 5,
            shadowOffsetY: 3
          },
          label: {
            show: true,
            position: 'top',
            color: '#333',
            fontSize: 12,
            formatter: '{c}'
          },
          emphasis: {
            itemStyle: {
              shadowColor: 'rgba(0, 0, 0, 0.4)',
              shadowBlur: 10,
              shadowOffsetY: 5
            }
          },
          animationType: this.currentAnimation,
          animationDuration: 1500,
          animationEasing: this.getAnimationEasing()
        }],
        dataZoom: this.date.length > 10 ? [{
          type: 'slider',
          show: true,
          xAxisIndex: [0],
          start: 0,
          end: 40,
          height: 15,
          bottom: 5,
          handleSize: 0,
          backgroundColor: '#F5F7FA',
          dataBackground: {
            lineStyle: {
              color: '#D1D9E6',
              width: 1
            },
            areaStyle: {
              color: '#E4E7ED'
            }
          },
          fillerColor: 'rgba(84, 112, 198, 0.2)',
          borderColor: '#E4E7ED'
        }] : []
      };

      this.chart.setOption(option, true);
    },
    getAnimationEasing() {
      switch (this.currentAnimation) {
        case 'ripple': return 'elasticOut';
        case 'scale': return 'cubicOut';
        case 'bounce': return 'bounceOut';
        default: return 'cubicOut';
      }
    },
    handleResize() {
      clearTimeout(this.resizeTimer);
      this.resizeTimer = setTimeout(() => {
        this.chart && this.chart.resize();
      }, 200);
    },
    setupResizeObserver() {
      if (typeof ResizeObserver !== 'undefined') {
        this.resizeObserver = new ResizeObserver(this.handleResize);
        this.resizeObserver.observe(this.$refs.chart);
      }
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

<style scoped lang="scss">
.bar-chart-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  transition: all 0.3s ease;

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
    padding: 12.5px 20px;
    //border-bottom: 1px solid #f0f0f0;

    .tag {
      font-size: 24px;
      font-weight: 600;
      z-index: 100;
      color: #333;
    }

    .chart-actions {
      display: flex;
      gap: 10px;
    }
  }

  .chart-wrapper {
    flex: 1;
    transition: all 0.3s ease;
  }
}

@media (max-width: 768px) {
  .bar-chart-container {
    .chart-header {
      flex-direction: column;
      align-items: flex-start;
      gap: 10px;
      padding: 10px 15px;

      .chart-actions {
        width: 100%;
        justify-content: flex-end;
      }
    }
  }
}
</style>