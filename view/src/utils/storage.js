// src/utils/storage.js

// 常量定义（全大写+下划线命名）
const TOKEN_KEY = "token";
const USER_INFO_KEY = "userInfo";
const ROLE_KEY = "role";
const LAST_VISITED_PATH_KEY = "last_visited_path";

// ==================== Token 操作 ====================
export const getToken = () => {
    try {
        return localStorage.getItem(TOKEN_KEY);
    } catch (e) {
        console.error('LocalStorage access error:', e);
        return null;
    }
};

export const setToken = (token) => {
    try {
        localStorage.setItem(TOKEN_KEY, token);
    } catch (e) {
        console.error('LocalStorage access error:', e);
    }
};

export const clearToken = () => {
    try {
        localStorage.removeItem(TOKEN_KEY);
    } catch (e) {
        console.error('LocalStorage access error:', e);
    }
};

// ==================== 用户信息操作 ====================
export const getUserInfo = () => {
    try {
        const json = localStorage.getItem(USER_INFO_KEY);
        return json ? JSON.parse(json) : null;
    } catch (e) {
        console.error('LocalStorage access error:', e);
        return null;
    }
};

export const setUserInfo = (userInfo) => {
    try {
        localStorage.setItem(USER_INFO_KEY, JSON.stringify(userInfo));
    } catch (e) {
        console.error('LocalStorage access error:', e);
    }
};

export const clearUserInfo = () => {
    try {
        localStorage.removeItem(USER_INFO_KEY);
    } catch (e) {
        console.error('LocalStorage access error:', e);
    }
};

// ==================== 角色操作 ====================
export const getRole = () => {
    try {
        const role = localStorage.getItem(ROLE_KEY);
        return role ? Number(role) : null;
    } catch (e) {
        console.error('LocalStorage access error:', e);
        return null;
    }
};

export const setRole = (role) => {
    try {
        localStorage.setItem(ROLE_KEY, Number(role));
    } catch (e) {
        console.error('LocalStorage access error:', e);
    }
};

export const clearRole = () => {
    try {
        localStorage.removeItem(ROLE_KEY);
    } catch (e) {
        console.error('LocalStorage access error:', e);
    }
};

// ==================== 路径记忆 ====================
export const getLastVisitedPath = () => {
    try {
        const path = localStorage.getItem(LAST_VISITED_PATH_KEY);
        return path || '/home'; // 默认返回首页
    } catch (e) {
        console.error('LocalStorage access error:', e);
        return '/home';
    }
};

export const setLastVisitedPath = (path) => {
    try {
        if (!['/login', '/register'].includes(path)) {
            localStorage.setItem(LAST_VISITED_PATH_KEY, path);
        }
    } catch (e) {
        console.error('LocalStorage access error:', e);
    }
};

export const clearLastVisitedPath = () => {
    try {
        localStorage.removeItem(LAST_VISITED_PATH_KEY);
    } catch (e) {
        console.error('LocalStorage access error:', e);
    }
};

// ==================== 通用方法 ====================
export const setStorage = (key, value) => {
    try {
        localStorage.setItem(key, JSON.stringify(value));
    } catch (e) {
        console.error('LocalStorage access error:', e);
    }
};

export const getStorage = (key) => {
    try {
        const value = localStorage.getItem(key);
        return value ? JSON.parse(value) : null;
    } catch (e) {
        console.error('LocalStorage access error:', e);
        return null;
    }
};

export const clearStorage = (key) => {
    try {
        localStorage.removeItem(key);
    } catch (e) {
        console.error('LocalStorage access error:', e);
    }
};

// ==================== 清空所有 ====================
export const clearAll = () => {
    try {
        localStorage.clear();
    } catch (e) {
        console.error('LocalStorage access error:', e);
    }
};