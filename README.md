# pet-adopt-system

一套完整的宠物领养管理系统，提供宠物信息展示、领养申请与后台审核、用户管理、救助信息发布、经验帖子、点赞收藏、协同过滤推荐等核心功能，旨在搭建爱心人士与流浪宠物之间的桥梁，规范线上领养流程。

## 技术栈

- 后端：Spring Boot 2.2 + MyBatis-Plus + MySQL + JWT
- 前端：Vue 2.6 + Vue Router + Element UI + Axios + ECharts
- 数据库：MySQL 5.7+

## 目录结构

```
├── api/   后端工程（Spring Boot，Maven）
├── view/  前端工程（Vue 2）
└── sql/   数据库脚本（pet_adopt_system.sql）
```

## 快速启动

### 1. 导入数据库

在 MySQL 中新建数据库并导入脚本：

```sql
CREATE DATABASE pet_adopt_system DEFAULT CHARACTER SET utf8mb4;
```

然后导入 `sql/pet_adopt_system.sql`。

### 2. 启动后端

修改 `api/src/main/resources/application.yml` 中的数据库密码：

```yaml
spring:
  datasource:
    username: root
    password: CHANGEME   # 改成你的 MySQL root 密码
```

在 `api/` 目录下执行：

```bash
mvn spring-boot:run
```

后端默认端口 21090，接口前缀 `/api/v1.0/pet-adopt-api`。

### 3. 启动前端

在 `view/` 目录下执行：

```bash
npm install
npm run serve
```

前端默认端口 21091，已配置代理到后端 21090，浏览器访问 http://localhost:21091 即可。

## 初始账号

初始管理员账号为 `admin`，初始密码及示例用户数据见 `sql/pet_adopt_system.sql` 中 `user` 表的记录（密码以 MD5 存储）。

## 安全说明

`application.yml` 中的数据库密码为占位符 `CHANGEME`，请勿将真实密码提交到仓库。
