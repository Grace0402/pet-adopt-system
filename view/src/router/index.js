import Vue from "vue";
import VueRouter from "vue-router";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import { clearToken, getToken, getRole } from "@/utils/storage.js";
import echarts from 'echarts';
Vue.prototype.$echarts = echarts;
Vue.use(ElementUI);
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    redirect: '/view'
  },
  {
    path: "/view",
    name: 'view',
    component: () => import(`@/views/view/Index.vue`)
  },
  {
    path: "/pet-detail",
    name: 'petDetail',
    component: () => import(`@/views/user/PetDetail.vue`)
  },
  {
    path: "/pet-detail-admin",
    name: 'petDetail',
    component: () => import(`@/views/admin/ViewPetDetailAdmin.vue`)
  },
  {
    path: "/view-pet-detail",
    name: 'viewPetDetail',
    component: () => import(`@/views/user/ViewPetDetail.vue`)
  },
  {
    path: "/notice-detail",
    name: 'noticeDetail',
    component: () => import(`@/views/user/NoticeDetail.vue`)
  },
  {
    path: "/pet-post-detail",
    name: 'petPostDetail',
    component: () => import(`@/views/user/PetPostDetail.vue`)
  },
  {
    path: "/create-pet-post",
    name: 'createPetPost',
    component: () => import(`@/views/user/CreatePetPost.vue`)
  },
  {
    path: "/update-pet-post",
    name: 'updatePetPost',
    component: () => import(`@/views/user/UpdatePetPost.vue`)
  },
  {
    path: "/login",
    name: 'login',
    component: () => import(`@/views/login/Login.vue`)
  },
  {
    path: "/register",
    component: () => import(`@/views/register/Register.vue`)
  },
  {
    path: "/admin",
    component: () => import(`@/views/admin/Home.vue`),
    meta: {
      requireAuth: true,
    },
    children: [
      {
        path: "/admin-layout",
        name: '仪表盘',
        icon: 'el-icon-pie-chart',
        show: true,
        component: () => import(`@/views/admin/Main.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/user-manage",
        name: '用户管理',
        show: true,
        icon: 'el-icon-user',
        component: () => import(`@/views/admin/UserManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/pet-manage",
        name: '宠物管理',
        show: true,
        icon: 'el-icon-s-help',
        component: () => import(`@/views/admin/PetManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/pet-type-manage",
        name: '宠物类别管理',
        show: true,
        icon: 'el-icon-s-data',
        component: () => import(`@/views/admin/PetTypeManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/pet-post-manage",
        name: '宠物经验帖子管理',
        show: true,
        icon: 'el-icon-attract',
        component: () => import(`@/views/admin/PetPostManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/pet-adopt-order-manage",
        name: '宠物领养订单管理',
        show: true,
        icon: 'el-icon-document',
        component: () => import(`@/views/admin/PetAdoptOrderManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/notice-manage",
        name: '公告管理',
        show: true,
        icon: 'el-icon-document',
        component: () => import(`@/views/admin/NoticeManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/proposal-feedback-manage",
        name: '意见与反馈',
        show: true,
        icon: 'el-icon-c-scale-to-original',
        component: () => import(`@/views/admin/ProposalFeedbackManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/evaluations-manage",
        name: '评论管理',
        show: true,
        icon: 'el-icon-chat-dot-round',
        component: () => import(`@/views/admin/EvaluationsManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/update-password",
        name: '修改个人密码',
        show: false, // 不在导航栏里面显示
        component: () => import(`@/views/admin/UpdatePassword.vue`),
        meta: { requireAuth: true },
      },
    ]
  },
  {
    path: "/user",
    component: () => import(`@/views/user/Main.vue`),
    meta: {
      requireAuth: true,
    },
    children: [
      {
        path: "/pet-list",
        name: '宠物大厅',
        component: () => import(`@/views/user/PetList.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/pet-post",
        name: '养宠经验',
        component: () => import(`@/views/user/PetPost.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/pet-post-user",
        name: '创作中心',
        component: () => import(`@/views/user/PetPostUser.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/address",
        name: '收货地址',
        component: () => import(`@/views/user/Address.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/notice",
        name: '公告',
        component: () => import(`@/views/user/Notice.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/my-pet-adopt-order",
        name: '宠物领养订单',
        component: () => import(`@/views/user/MyPetAdoptOrder.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/proposal-feedback",
        name: '意见与反馈',
        component: () => import(`@/views/user/ProposalFeedback.vue`),
        meta: { requireAuth: true },
      },
    ]
  }
];
const router = new VueRouter({
  routes,
  mode: 'history'
});
router.beforeEach((to, from, next) => {
  // 放行登录页和注册页
  if (to.path === '/login' || to.path === '/register') {
    return next();
  }

  // 检查需要认证的路由
  if (to.matched.some(record => record.meta.requireAuth)) {
    const token = getToken();

    // 未登录情况处理
    if (!token) {
      return next({
        path: '/login',
        query: { redirect: to.fullPath } // 记录目标路由
      });
    }

    // 已登录时的权限检查
    try {
      const role = parseInt(getRole());

      // 管理员路径检查
      if (to.matched[0].path === '/admin' && role !== 1) {
        clearToken();
        return next("/login"); //返回登录页
      }

      return next();
    } catch (error) {
      console.error('权限检查失败:', error);
      return next('/login');
    }
  }

  // 普通页面直接放行
  next();
});
export default router;
