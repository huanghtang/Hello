import fetch from '@/axios'

export function list(params) {
    return fetch({
        url: '/admin/operate/page',
        method: 'post',
        data: params
    })
}
