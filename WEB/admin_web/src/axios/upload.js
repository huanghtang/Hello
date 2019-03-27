import axios from 'axios'

const service = axios.create();

export function upload(file, url, token) {
    let param = new FormData();
    param.append("file", file);
    let config = {
        headers: {
            "Content-Type": "multipart/form-data",
            "xinye_admin_session_id": token
        }
    };
    return service.post(url, param, config);
}
