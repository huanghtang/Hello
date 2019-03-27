import fetch from '@/axios'

export function resetPassword(params) {
    return fetch({
        url: '/admin/my/resetPassword',
        method: 'post',
        data: params
    })
}
