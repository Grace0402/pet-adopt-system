<template>
    <div class="admin-layout">
        <!-- 左侧导航菜单 - 菜单组件 -->
        <aside class="sidebar">
            <Menu @route-listener="route" :routes="adminRoutes" :bag="menuBgColor" @select="handleRouteSelect" />
        </aside>
        <!-- 右侧内容区域 -->
        <main class="content-area">
            <div class="top-info">
                <div class="point-text">{{ activeRoute.name }}</div>
                <div class="user-info">
                    <el-dropdown trigger="click" placement="bottom-end">
                        <span class="el-dropdown-link">
                            <div class="dropdown-info">
                                <img :src="userInfo.avatar" />
                                <span>{{ userInfo.username }}</span>
                            </div>
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="dialogPasswordVisible = true" icon="el-icon-view">
                                修改密码
                            </el-dropdown-item>
                            <el-dropdown-item @click.native="handleLoginOut" icon="el-icon-s-fold">
                                退出登录
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </div>
            <section class="router-view">
                <router-view></router-view>
            </section>
        </main>

        <el-dialog title="修改密码" :visible.sync="dialogPasswordVisible" :closeOnClickModal="false" width="35%">
            <UpdatePassword />
        </el-dialog>
    </div>
</template>

<script>

// B站「程序员辰星」原创出品
import { get } from "@/utils/request"
import router from "@/router/index";
import { clearToken, clearRole, clearUserInfo, setUserInfo } from "@/utils/storage"
import Menu from '@/components/Menu.vue';
import UpdatePassword from '@/views/admin/UpdatePassword.vue';
import LevelHeader from '@/components/LevelHeader.vue';

export default {
    name: "admin-layout",
    components: { LevelHeader, Menu, UpdatePassword },
    data() {
        return {
            dialogPasswordVisible: false, // 退出登录弹窗开关
            adminRoutes: [], // 管理员路由信息
            userInfo: {}, // 用户信息
            activeRoute: {},
            logoColor: 'rgb(51,51,51)', // Logo字体颜色
            menuBgColor: 'rgb(250,250,250)' // 功能导航栏背景色
        };
    },
    created() {
        this.loadAdminRoutes();
        this.checkTokenAndLoadUser();
        this.handleRouteSelect('/adminLayout'); // 默认加载首页
    },
    methods: {

        handleLoginOut() {
            clearToken();
            clearRole();
            clearUserInfo();
            this.$message.success('退出登录成功');
            this.$router.push('/login');
        },

        // 路由切换
        route(activeRoute) {
            this.activeRoute = activeRoute;
        },

        // 路由跳转
        handleRouteSelect(routePath) {
            const route = this.adminRoutes.find(r => r.path === routePath);
            if (route) {
                if (this.$route.path !== routePath) {
                    this.activeRoute = { ...route };
                    this.$router.push(routePath);
                }
            }
        },

        // 加载管理员路由
        loadAdminRoutes() {
            const adminRoute = router.options.routes.find(r => r.path === '/admin');
            this.adminRoutes = (adminRoute && adminRoute.children) || [];
        },

        // 检查用户登录状态 - 校验Token令牌
        async checkTokenAndLoadUser() {
            try {
                const response = await get('user/auth');

                if (response.code === 400) {
                    this.$notify.error({
                        title: 'token校验异常',
                        message: response.message,
                        duration: 2000,
                    });
                    clearToken();
                    this.$router.push('/login');
                }

                this.userInfo = response.data;

                setUserInfo(this.userInfo); // 存储用户信息
            } catch (error) {
                console.error('token认证错误「Home.vue」:', error);
                this.$notify.error({
                    title: 'token校验异常,请重新登录',
                    message: error.message,
                    duration: 2000,
                });
                clearToken();
                this.$router.push('/login');
            }
        }
    }
};
</script>

<style scoped lang="scss">
.admin-layout {
    display: flex;
    height: 100vh;
    width: 100%;
    background-color: rgb(250, 250, 250);

    .sidebar {
        width: 206px;
        min-width: 60px;
        height: 100vh;
        box-sizing: border-box;
        padding-top: 10px;
        background-color: rgb(250, 250, 250);
        background-color: v-bind(menuBgColor);
        transition: width 0.3s ease;
        position: relative;

        .logo-container {
            display: flex;
            align-items: center;
            padding: 0 40px;
            margin: 10px 0;
        }
    }

    .dropdown-info {
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 6px;
        cursor: pointer;
        background-color: rgb(248, 248, 248);
        padding: 4px 10px;
        border-radius: 10px;

        &:hover {
            background-color: rgb(239, 240, 240);
        }

        img {
            width: 30px;
            height: 30px;
            border-radius: 50%;
        }

        span {
            font-size: 16px;
        }
    }

    .content-area {
        flex: 1;
        display: flex;
        flex-direction: column;
        overflow: hidden;
        background-color: rgb(255, 255, 255);

        .top-info {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 40px;

            .point-text {
                font-size: 22px;
            }

        }

        .router-view {
            flex: 1;
            padding: 0 6px;
            overflow-y: auto;
        }
    }
}
</style>