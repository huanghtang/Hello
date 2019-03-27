import Vue from 'vue'
import Vuex from 'vuex'
import {
    constantRouterMap,
    asyncRouterMap
} from '@/router'

import {
    getToken,
    setToken,
    removeToken
} from '@/utils/token'


Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        username: '',
        name: '',
        status: '',
        menus: [],
        routers: [],
        addRouters: [],
        token: getToken()
    },
    mutations: {
        username(state, username) {
            state.username = username
        },
        name(state, name) {
            state.name = name
        },
        status(state, status) {
            state.status = status
        },
        token(state, token) {
            state.token = token
        },
        login(state, account) {
            state.username = account.username
            state.name = account.name
            state.status = account.status
            state.token = account.sessionId
            state.menus = account.menus
            state.routers = constantRouterMap
            let accessedRouters = filterAsyncRouter(asyncRouterMap, account.menus)
            state.addRouters = accessedRouters
            state.routers = constantRouterMap.concat(accessedRouters)
            setToken(account.sessionId)
        },
        logout(state) {
            state.username = ''
            state.name = ''
            state.status = ''
            state.token = ''
            removeToken()
        },
        setRouters(state, routers) {
            state.routers = constantRouterMap
            state.addRouters = routers
            state.routers = constantRouterMap.concat(routers)
        }
    },
    actions: {
        login({ commit }, data) {
            return new Promise(resolve => {
                const { account } = data
                commit('login', account)
                resolve()
            })
        },
        generateRoutes({ commit }, data) {
            return new Promise(resolve => {
                const { menus } = data
                let accessedRouters = filterAsyncRouter(asyncRouterMap, menus)
                commit('setRouters', accessedRouters)
                resolve()
            })
        }
    }
})

function hasPermission(menus, route) {
    var succ = false
    for (var menu of menus) {
        if (route.name === '404') {
            succ = true
            break
        } else if (menu.name && route.name === menu.name) {
            succ = true
            break
        } else if (route.children && route.children.length) {
            for (var child of route.children) {
                if (child.name === menu.name) {
                    succ = true
                    break
                }
            }
        }
    }
    return succ
}

/**
 * 遞歸過濾異步路由表，返回符合用戶角色權限的路由表
 * @param asyncRouterMap
 * @param menus
 */
function filterAsyncRouter(asyncRouterMap, menus) {
    const accessedRouters = asyncRouterMap.filter(route => {
        if (hasPermission(menus, route)) {
            if (route.children && route.children.length) {
                route.children = filterAsyncRouter(route.children, menus)
            }
            return true
        }
        return false
    })
    return accessedRouters
}

export default store
