import fetch from '@/axios'

export function parentPageSave(params) {
    return fetch({
        url: '/admin/info/startpage/parentPageSave',
        method: 'post',
        data: params
    })
}
export function teacherPageSave(params) {
    return fetch({
        url: '/admin/info/startpage/teacherPageSave',
        method: 'post',
        data: params
    })
}


export function getCover() {
    return fetch({
        url: '/admin/info/startpage/getCover',
        method: 'post'
    })
}
