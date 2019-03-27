import fetch from '@/axios'

export function login(params) {
    return fetch({
        url: '/admin/login/doLogin',
        method: 'post',
        data: params
    })
}
export function logout() {
    return fetch({
        url: '/admin/login/logOut',
        method: 'get'
    })
}

export function loginWithCookie() {
    return fetch({
        url: '/admin/login/loginBySessionId',
        method: 'post'
    })
}
