<template>
    <div class="password-container">
        <div class="password-card">
            <el-form ref="passwordForm" :model="form" :rules="rules" label-position="top"
                @submit.native.prevent="submitForm">
                <el-form-item label="当前密码" prop="oldPassword">
                    <el-input v-model="form.oldPassword" type="password" placeholder="请输入当前密码" show-password
                        clearable></el-input>
                </el-form-item>

                <el-form-item label="新密码" prop="newPassword">
                    <el-input v-model="form.newPassword" type="password" placeholder="请输入新密码" show-password
                        clearable></el-input>
                    <div class="password-hint">密码长度8-20位，需包含字母和数字</div>
                </el-form-item>

                <el-form-item label="确认新密码" prop="againPassword">
                    <el-input v-model="form.againPassword" type="password" placeholder="请再次输入新密码" show-password
                        clearable></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" native-type="submit" :loading="loading" class="submit-btn">
                        确认修改
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { clearToken, clearRole, clearUserInfoo } from "@/utils/storage"
import { clearUserInfo } from "../../utils/storage";
export default {
    name: "UpdatePassword",
    data() {
        const validatePassword = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else if (value.length < 8 || value.length > 20) {
                callback(new Error('密码长度需在8-20位之间'));
            } else if (!/[a-zA-Z]/.test(value) || !/\d/.test(value)) {
                callback(new Error('密码需包含字母和数字'));
            } else {
                if (this.form.againPassword !== '') {
                    this.$refs.passwordForm.validateField('againPassword');
                }
                callback();
            }
        };

        const validateAgainPassword = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.form.newPassword) {
                callback(new Error('两次输入密码不一致'));
            } else {
                callback();
            }
        };

        return {
            loading: false,
            form: {
                oldPassword: '',
                newPassword: '',
                againPassword: ''
            },
            rules: {
                oldPassword: [
                    { required: true, message: '请输入当前密码', trigger: 'blur' }
                ],
                newPassword: [
                    { required: true, validator: validatePassword, trigger: 'blur' }
                ],
                againPassword: [
                    { required: true, validator: validateAgainPassword, trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
        submitForm() {
            this.$refs.passwordForm.validate(valid => {
                if (valid) {
                    this.loading = true;
                    const updatePasswordDto = {
                        oldPassword: this.$md5(this.$md5(this.form.oldPassword)),
                        newPassword: this.$md5(this.$md5(this.form.newPassword)),
                        againPassword: this.$md5(this.$md5(this.form.againPassword)),
                    }
                    this.$axios.put('/user/updatePassword', updatePasswordDto)
                        .then(response => {
                            if (response.code === 200) {
                                this.$message.success('密码修改成功，请重新登录');
                                // 清空表单
                                this.$refs.passwordForm.resetFields();
                                clearToken();//清空Token
                                clearRole(); // 清空角色
                                clearUserInfo(); // 清空用户信息
                                this.$router.push('/login'); // 返回登录页
                            } else {
                                this.$message.error(response.message || '密码修改失败');
                            }
                        })
                        .catch(error => {
                            this.$message.error(error.message);
                        })
                        .finally(() => {
                            this.loading = false;
                        });
                } else {
                    return false;
                }
            });
        }
    }
};
</script>

<style scoped lang="scss">
.password-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 430px;
    padding-block: 20px;
}

.password-card {
    width: 100%;
    background-color: #fff;
    border-radius: 12px;
    //box-shadow: 0 8px 12px rgba(0, 0, 0, 0.05);
}

.password-title {
    margin-bottom: 30px;
    text-align: center;
    color: #303133;
    font-weight: 500;
    font-size: 24px;
}

::v-deep .el-form-item__label {
    font-weight: 500;
    color: #606266;
    padding-bottom: 8px;
}

::v-deep .el-input__inner {
    height: 44px;
    line-height: 44px;
    border-radius: 8px;
}

.password-hint {
    font-size: 12px;
    color: #909399;
    margin-top: 6px;
}

.submit-btn {
    width: 100%;
    height: 44px;
    border-radius: 8px;
    font-size: 16px;
    margin-top: 10px;
}

@media (max-width: 768px) {
    .password-card {
        padding: 30px 20px;
    }
}
</style>