import Vue from 'vue'
import Router from 'vue-router'
import Layout from '../components/Layout.vue'
import Login from '../views/login/Login.vue'

Vue.use(Router)
export const constantRouterMap = [
    {
        path: '/',
        component: Layout,
        name: '主页',
        hidden: true,
        redirect: '/admin/login'
    },
    {
        path: '/admin',
        component: Layout,
        name: '首页',
        icon: 'el-icon-menu',
        redirect: '/admin/dashboard',
        noDropdown: true,
        children: [{
            path: 'dashboard',
            component: resolve => require(['../views/dashboard'], resolve),
            name: '首页'
        }]
    }, {
        path: '/admin/login',
        name: '登录',
        hidden: true,
        component: Login
    },
    { path: '/404', component: resolve => require(['../views/error/404.vue'], resolve), hidden: true },
    { path: '/401', component: resolve => require(['../views/error/401.vue'], resolve), hidden: true }
]

 export const asyncRouterMap = [
     {
        path: '/admin/database',
        component: Layout,
        name: '基础信息',
        redirect: "noredirect",
        icon: 'el-icon-picture',
        children: [
            {
                path: 'user',
                name: '用户管理',
                component: resolve => require(['../views/database/UserList.vue'], resolve)
            },
            {
                path: 'banner',
                name: 'banner管理',
                component: resolve => require(['../views/database/BannerList.vue'], resolve)
            },
            {
                path: 'mainkind',
                name: 'LOGO分类管理',
                component: resolve => require(['../views/database/MainKind.vue'], resolve)
            },
            {
                path: 'childkind',
                name: 'LOGO子分类管理',
                component: resolve => require(['../views/database/ChildKind.vue'], resolve)
            },
            {
                path: 'logo',
                name: 'LOGO管理',
                component: resolve => require(['../views/database/Logo.vue'], resolve)
            }
        ]
    }, {
        path: '/admin/set',
        component: Layout,
        name: '设置',
        redirect: "noredirect",
        icon: 'el-icon-setting',
        children: [{
                path: 'font',
                name: '字体管理',
                component: resolve => require(['../views/set/Font.vue'], resolve)
            },
            {
                path: 'machine',
                name: '设备管理',
                component: resolve => require(['../views/set/Machine.vue'], resolve)
            },
            {
                path: 'help',
                name: '帮助中心',
                component: resolve => require(['../views/set/Help.vue'], resolve)
            },
            {
                path: 'feedback',
                name: '意见反馈',
                component: resolve => require(['../views/set/Feedback.vue'], resolve)
            },
            {
                path: 'about',
                name: '关于公司',
                component: resolve => require(['../views/set/About.vue'], resolve)
            }
        ]
    },
    {
            path: '/admin/auth',
            component: Layout,
            name: '权限管理',
            redirect: "noredirect",
            icon: 'el-icon-more',
            children: [{
                    path: 'account',
                    name: '账户列表',
                    component: resolve => require(['../views/auth/AccountList.vue'], resolve)
                },
                {
                    path: 'role',
                    name: '权限管理',
                    component: resolve => require(['../views/auth/RoleList.vue'], resolve)
                }
            ]
        },
        {
            path: '/admin/operate',
            component: Layout,
            name: '操作日志',
            redirect: "noredirect",
            icon: 'el-icon-menu',
            children: [{
                    path: 'operation',
                    name: '操作日志',
                    component: resolve => require(['../views/operate/Operation.vue'], resolve)
                }
            ]
        },
        {
            path: '*',
            name: '404',
            redirect: '/404',
            hidden: true
        }
]

export default new Router({
    scrollBehavior: () => ({
        y: 0
    }),
    mode: 'history',
    routes: constantRouterMap
})

