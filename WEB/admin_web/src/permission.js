import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 進度條
import 'nprogress/nprogress.css' // Progress 進度條樣式
import { loginWithCookie } from '@/api/login' // 驗權
// import { Message } from 'element-ui'


const whiteList = ['/admin/login'] // 不重定向白名單



router.beforeEach((to, from, next) => {
    NProgress.start() // 开启Progress
    if (store.state.menus.length === 0) {
        if (whiteList.indexOf(to.path) !== -1) {
            next()
        } else {
            loginWithCookie().then(res => {
                if (res.data.success) {
                    store.commit("login", res.data.sysUser);
                    const menus = res.data.sysUser.menus;
                    store.dispatch('generateRoutes', { menus }).then(() => {  // 生成可访问的路由表
                        router.addRoutes(store.state.addRouters) // 动态添加可访问路由表
                        next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,replace: true so the navigation will not leave a history record
                    })
                } else {
                    next("/admin/login")
                }
            })
            .catch(error => {
                console.log(error)
            });
        }
    } else {
        next()
        NProgress.done() // router在hash模式下 手动改变hash 重定向回来 不会触发afterEach 暂时hack方案 ps：history模式下无问题，可删除该行！
    }
})

router.afterEach(() => {
    NProgress.done() // 结束Progress
})


