import axios from 'axios'
import { getToken, clearToken } from '@/utils/storage'

// 环境配置
const ENV = process.env.NODE_ENV
const BASE_URL_MAP = {
  development: 'http://localhost:21090/api/v1.0/pet-adopt-api', // 开发环境
  test: 'http://test-api.example.com/api', // 测试环境
  production: 'http://localhost:21090/api/v1.0/pet-adopt-api' // 生产环境
}

// 创建实例
const request = axios.create({
  baseURL: BASE_URL_MAP[ENV] || BASE_URL_MAP.development,
  timeout: 15000, // 超时时间
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  }
})

// 请求队列（用于取消请求）
const pendingRequests = new Map()

/**
 * 生成请求唯一标识
 */
const generateReqKey = (config) => {
  const { method, url, params, data } = config
  return [method, url, JSON.stringify(params), JSON.stringify(data)].join('&')
}

/**
 * 添加请求到队列
 */
const addPendingRequest = (config) => {
  const requestKey = generateReqKey(config)
  config.cancelToken = config.cancelToken || new axios.CancelToken(cancel => {
    if (!pendingRequests.has(requestKey)) {
      pendingRequests.set(requestKey, cancel)
    }
  })
}

/**
 * 移除队列中的请求
 */
const removePendingRequest = (config) => {
  const requestKey = generateReqKey(config)
  if (pendingRequests.has(requestKey)) {
    const cancel = pendingRequests.get(requestKey)
    cancel(requestKey)
    pendingRequests.delete(requestKey)
  }
}

/**
 * 清空所有pending状态的请求
 */
const clearPendingRequests = () => {
  for (const [requestKey, cancel] of pendingRequests) {
    cancel(requestKey)
  }
  pendingRequests.clear()
}

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 取消重复请求
    removePendingRequest(config)
    addPendingRequest(config)

    // 添加认证token
    const token = getToken();

    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }

    // 处理GET请求数组参数
    if (config.method === 'get' && config.params) {
      config.paramsSerializer = params => {
        return Object.keys(params)
          .map(key => {
            if (Array.isArray(params[key])) {
              return params[key].map(item => `${key}=${item}`).join('&')
            }
            return `${key}=${params[key]}`
          })
          .join('&')
      }
    }

    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    // 请求完成后移除队列
    removePendingRequest(response.config)

    const { data, status } = response
    const { code, message } = data

    // 业务状态码处理
    if (code !== undefined && code !== 200) {
      // 特殊状态码处理
      if (code === 401) {
        clearToken()
        return Promise.reject(new Error('登录状态已过期，请重新登录'))
      }
      // 错误返回
      return Promise.reject(data)
    }

    // 正常返回数据
    return data
  },
  error => {
    
    // 从队列中移除失败的请求
    if (error.config) {
      removePendingRequest(error.config)
    }

    // 请求超时处理
    if (error.code === 'ECONNABORTED' && error.message.includes('timeout')) {
      Message.error('请求超时，请重试')
      return Promise.reject(error)
    }

    // 取消请求不报错
    if (axios.isCancel(error)) {
      console.log(error);
      return Promise.reject()
    }
    
    const { response } = error
    if (response) {
      switch (response.status) {
        case 400:
          error.message = '请求参数错误'
          break
        case 401:
          clearToken()
          break
        case 403:
          error.message = '拒绝访问'
          break
        case 404:
          error.message = `请求地址不存在: ${error.config.url}`
          break
        case 500:
          error.message = '服务器内部错误'
          break
        case 503:
          error.message = '服务不可用'
          break
        default:
          error.message = `连接错误 ${response.status}`
      }
    } else {
      error.message = '网络连接异常'
    }
    return Promise.reject(error)
  }
)

// 封装常用方法
export const get = (url, params = {}, config = {}) => {
  return request({
    method: 'get',
    url,
    params,
    ...config
  })
}

export const post = (url, data = {}, config = {}) => {
  return request({
    method: 'post',
    url,
    data,
    ...config
  })
}

export const put = (url, data = {}, config = {}) => {
  return request({
    method: 'put',
    url,
    data,
    ...config
  })
}

export const del = (url, params = {}, config = {}) => {
  return request({
    method: 'delete',
    url,
    params,
    ...config
  })
}

// 文件上传封装
export const upload = (url, file, config = {}) => {
  const formData = new FormData()
  formData.append('file', file)

  return request({
    method: 'post',
    url,
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    ...config
  })
}

// 导出实例和取消方法
export default request
export { clearPendingRequests }