<template>
  <div class="register-container">

    <!-- 注册面板 -->
    <div class="register-panel animated fadeIn">
      <!-- 左侧图片区域 -->
      <div class="illustration">
        <transition name="fade" appear>
          <img src="/bag.png" alt="" class="illustration-img" />
        </transition>
      </div>

      <!-- 右侧注册表单 -->
      <div class="right-register">
        <transition-group name="slide-fade" tag="div">
          <div key="header" class="register-header">
            <h2>创建您的账户</h2>
            <p class="welcome-text">描述语</p>
          </div>

          <div key="account" class="input-group">
            <input v-model="account" class="register-input" placeholder="输入账号" @focus="animateInput('account')"
              @blur="resetInput('account')" />
            <span class="input-highlight"></span>
          </div>

          <div key="name" class="input-group">
            <input v-model="username" class="register-input" placeholder="用户名" @focus="animateInput('name')"
              @blur="resetInput('name')" />
            <span class="input-highlight"></span>
          </div>

          <div key="password" class="input-group">
            <input v-model="password" class="register-input" type="password" placeholder="输入密码"
              @focus="animateInput('password')" @blur="resetInput('password')" />
            <span class="input-highlight"></span>
          </div>

          <div key="confirm" class="input-group">
            <input v-model="againPassword" class="register-input" type="password" placeholder="确认密码"
              @focus="animateInput('confirm')" @blur="resetInput('confirm')" />
            <span class="input-highlight"></span>
          </div>

          <div key="button" class="button-group">
            <button class="register-btn" @click="registerFunc" @mouseenter="hoverButton(true)"
              @mouseleave="hoverButton(false)">
              <span class="btn-text">立即注册</span>
              <span class="btn-icon">→</span>
            </button>
          </div>

          <div key="footer" class="register-footer">
            <p>已有账户？<span class="login-link" @click="toDoLogin">返回登录</span></p>
          </div>
        </transition-group>
      </div>
    </div>

    <!-- 背景装饰元素 -->
    <div class="bg-elements">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
    </div>
  </div>
</template>

<script>
// 注册页 - B站「程序员辰星」原创出品
export default {
  name: "Register",
  data() {
    return {
      account: '', // 用户账号
      password: '', // 用户密码
      againPassword: '', // 用户确认密码
      username: '', // 用户名
      activeInput: null, // 输入框动画
      isButtonHovered: false // 按钮悬停效果
    }
  },
  methods: {
    // 返回登录页面
    toDoLogin() {
      this.$router.push('/login');
    },

    // 输入框动画
    animateInput(type) {
      this.activeInput = type;
    },

    resetInput() {
      this.activeInput = null;
    },

    // 按钮悬停效果
    hoverButton(isHovered) {
      this.isButtonHovered = isHovered;
    },

    async registerFunc() {
      if (!this.account || !this.password || !this.againPassword || !this.username) {
        this.$message.info('请填写相关信息哦!');
        return;
      }

      if (this.password !== this.againPassword) {
        this.$message.info('前后密码输入不一致!');
        return;
      }

      // 密码二次md5加密
      const bcryptPassword = this.$md5(this.$md5(this.password));

      // 构建注册参数UserRegisterDto类
      const userRegisterDto = {
        account: this.account,
        password: bcryptPassword,
        username: this.username
      }

      try {
        const { message } = await this.$axios.post(`user/register`, userRegisterDto);
        this.$message.success(message);
        this.$router.go(-1); // 返回登录页
      } catch (error) {
        this.$message.error(error.message);
      }
    }
  }
};
</script>

<style lang="scss" scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap');

* {
  user-select: none;
  box-sizing: border-box;
  font-family: 'Poppins', 'Segoe UI', sans-serif;
}

.register-container {
  width: 100%;
  min-height: 100vh;
  background-color: #f8fafc;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  position: relative;
  overflow: hidden;
  padding: 20px;

  .logo-container {
    display: flex;
    justify-content: left;
    margin: 20px 0;
    width: 100%;
    max-width: 1200px;
    z-index: 2;
  }

  .register-panel {
    display: flex;
    border-radius: 16px;
    height: auto;
    background: linear-gradient(135deg, #6a5acd 0%, #89cff0 100%);
    box-shadow: 0 20px 40px rgba(96, 84, 185, 0.2);
    overflow: hidden;
    z-index: 2;
    max-width: 900px;
    width: 100%;
    transition: all 0.3s ease;

    &:hover {
      box-shadow: 0 25px 50px rgba(96, 84, 185, 0.3);
    }

    .illustration {
      width: 40%;
      padding: 40px;
      display: flex;
      justify-content: center;
      align-items: center;
      background: rgba(255, 255, 255, 0.1);

      .illustration-img {
        width: 100%;
        max-width: 300px;
        filter: drop-shadow(0 10px 20px rgba(0, 0, 0, 0.1));
        animation: float 6s ease-in-out infinite;
      }
    }

    .right-register {
      width: 60%;
      background-color: white;
      padding: 50px;
      display: flex;
      flex-direction: column;
      justify-content: center;

      .register-header {
        margin-bottom: 30px;

        h2 {
          color: #2d3748;
          font-size: 28px;
          font-weight: 600;
          margin-bottom: 8px;
        }

        .welcome-text {
          color: #718096;
          font-size: 14px;
          font-weight: 400;
        }
      }

      .input-group {
        position: relative;
        margin-bottom: 25px;

        .register-input {
          width: 100%;
          height: 50px;
          font-size: 16px;
          padding: 0 15px;
          background-color: #f8fafc;
          border: 2px solid #e2e8f0;
          border-radius: 8px;
          transition: all 0.3s ease;
          font-weight: 500;
          color: #2d3748;

          &:focus {
            outline: none;
            border-color: #6a5acd;
            background-color: white;
            box-shadow: 0 0 0 3px rgba(106, 90, 205, 0.1);
          }

          &::placeholder {
            color: #a0aec0;
            font-weight: 400;
          }
        }

        .input-highlight {
          position: absolute;
          bottom: 0;
          left: 0;
          height: 2px;
          width: 0;
          background: linear-gradient(90deg, #6a5acd, #89cff0);
          transition: width 0.4s ease;
        }
      }

      .input-group.account .register-input:focus~.input-highlight,
      .input-group.name .register-input:focus~.input-highlight,
      .input-group.password .register-input:focus~.input-highlight,
      .input-group.confirm .register-input:focus~.input-highlight {
        width: 100%;
      }

      .button-group {
        margin-top: 30px;

        .register-btn {
          display: flex;
          align-items: center;
          justify-content: space-between;
          width: 100%;
          height: 50px;
          background: linear-gradient(90deg, #6a5acd, #89cff0);
          color: white;
          border: none;
          border-radius: 8px;
          font-size: 16px;
          font-weight: 500;
          cursor: pointer;
          overflow: hidden;
          position: relative;
          transition: all 0.3s ease;
          padding: 0 25px;

          .btn-text {
            transition: transform 0.3s ease;
          }

          .btn-icon {
            opacity: 0;
            transform: translateX(-20px);
            transition: all 0.3s ease;
          }

          &:hover {
            box-shadow: 0 10px 20px rgba(106, 90, 205, 0.3);

            .btn-text {
              transform: translateX(10px);
            }

            .btn-icon {
              opacity: 1;
              transform: translateX(0);
            }
          }

          &:active {
            transform: translateY(2px);
          }

          &::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
            transition: all 0.5s ease;
          }

          &:hover::before {
            left: 100%;
          }
        }
      }

      .register-footer {
        margin-top: 20px;
        text-align: center;

        p {
          color: #718096;
          font-size: 14px;

          .login-link {
            color: #6a5acd;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.2s ease;

            &:hover {
              color: #89cff0;
              text-decoration: underline;
            }
          }
        }
      }
    }
  }

  .bg-elements {
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    pointer-events: none;
    overflow: hidden;

    .circle {
      position: absolute;
      border-radius: 50%;
      background: linear-gradient(135deg, rgba(106, 90, 205, 0.1) 0%, rgba(137, 207, 240, 0.1) 100%);

      &.circle-1 {
        width: 300px;
        height: 300px;
        top: -100px;
        right: -100px;
        animation: pulse 8s infinite ease-in-out;
      }

      &.circle-2 {
        width: 200px;
        height: 200px;
        bottom: -50px;
        left: -50px;
        animation: pulse 6s infinite ease-in-out 2s;
      }

      &.circle-3 {
        width: 150px;
        height: 150px;
        top: 50%;
        left: 20%;
        animation: pulse 5s infinite ease-in-out 1s;
      }
    }
  }
}

/* 动画定义 */
@keyframes float {

  0%,
  100% {
    transform: translateY(0);
  }

  50% {
    transform: translateY(-15px);
  }
}

@keyframes pulse {

  0%,
  100% {
    transform: scale(1);
    opacity: 0.8;
  }

  50% {
    transform: scale(1.1);
    opacity: 0.6;
  }
}

/* Vue过渡动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.3s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateX(20px);
  opacity: 0;
}

.animated {
  animation-duration: 1s;
  animation-fill-mode: both;
}

.fadeIn {
  animation-name: fadeIn;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>