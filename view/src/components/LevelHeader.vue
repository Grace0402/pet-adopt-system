<template>
  <div class="header-container">
    <div class="left-section">
      <span class="toggle-btn" @click="operation">
        <i :class="showFlag ? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
      </span>
      <span class="breadcrumb">
        后台 / {{ tag || '' }}
      </span>
    </div>
    <div class="user-profile" @click="center">
      <img class="user-avatar" :src="userInfo.avatar" alt="用户头像">
      <span class="user-name">{{ userInfo.username }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "LevelHeader",
  data() {
    return {
      showFlag: sessionStorage.getItem('flag') === 'true',
    };
  },
  props: {
    tag: {
      type: String,
      default: ''
    },
    userInfo: {
      type: Object,
      default: () => ({})
    },
    bag: {
      type: String,
      default: ''
    },
  },
  methods: {
    center() {
      this.$emit('eventListener', 'center');
    },
    operation() {
      this.showFlag = !this.showFlag;
      sessionStorage.setItem('flag', this.showFlag);
      this.$emit('selectOperation', this.showFlag);
    },
  }
};
</script>

<style scoped lang="scss">
.header-container {
  padding-block: 6px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  position: relative;
  z-index: 10;

  .left-section {
    display: flex;
    align-items: center;
    gap: 10px;

    .toggle-btn {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 32px;
      height: 32px;
      margin-left: 15px;
      border-radius: 4px;
      cursor: pointer;
      transition: background-color 0.2s;

      &:hover {
        background-color: #eae9e9;
      }

      i {
        font-size: 20px;
        color: rgb(51, 51, 51);
      }
    }

    .breadcrumb {
      font-size: 18px;
      color: rgb(51, 51, 51);
      user-select: none;
      border-radius: 5px;
    }
  }

  .user-profile {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 6px 8px;
    border-radius: 8px;
    font-size: 14px;
    margin-right: 10px;
    cursor: pointer;

    &:hover {
      background-color: rgb(244, 244, 244);
    }

    .user-avatar {
      width: 20px;
      height: 20px;
      border-radius: 50%;
      object-fit: cover;
    }

    .user-name {
      font-size: 16px;
      color: rgb(51, 51, 51);
      font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
}
</style>