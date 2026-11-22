<template>
  <div class="logo-container">
    <img 
      src="/logo.png" 
      alt="Logo" 
      class="logo-image"
      :style="{ width: logoSize + 'px', height: logoSize + 'px' }"
    >
    <transition name="fade">
      <span 
        v-if="!collapsed" 
        class="logo-text"
        :style="{ 
          color: textColor,
          fontSize: fontSize + 'px',
          opacity: textOpacity
        }"
      >
        {{ sysName }}
      </span>
    </transition>
  </div>
</template>

<script>
export default {
  name: "AppLogo",
  props: {
    sysName: {
      type: String,
      default: ''
    },
    collapsed: {
      type: Boolean,
      default: false
    },
    textColor: {
      type: String,
      default: 'rgb(51,51,51)'
    },
    logoSize: {
      type: Number,
      default: 30
    },
    fontSize: {
      type: Number,
      default: 22
    }
  },
  data() {
    return {
      textOpacity: 1 // 用于平滑过渡
    };
  },
  watch: {
    collapsed(newVal) {
      // 在状态变化前先设置透明度，避免闪影
      if (newVal) {
        this.textOpacity = 0;
      } else {
        setTimeout(() => {
          this.textOpacity = 1;
        }, 10);
      }
    }
  }
};
</script>

<style scoped lang="scss">
.logo-container {
  display: flex;
  align-items: center;
  margin: 0 10px;
  user-select: none;
  height: 100%;
  overflow: hidden;
}

.logo-image {
  flex-shrink: 0;
  transition: all 0.3s ease;
}

.logo-text {
  margin-left: 8px;
  font-weight: 600;
  font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
  white-space: nowrap;
  transition: all 0.3s ease;
}

/* 过渡动画 */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>