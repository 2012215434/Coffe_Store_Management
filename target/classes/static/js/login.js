//设置全局表单提交格式
Vue.http.options.emulateJSON = true;

// Vue实例
new Vue({
    el: '#app',
    data() {
        return {
            checked: false,
            login: {
                firstName: '',
                email: '',
            },
            flag: true,
            loading: {}, //loading动画
        };
    },
    methods: {
        /**
         * loading加载动画
         */
        loadings(){
            this.loading = this.$loading({
                lock: true,
                text: '拼命加载中',
                spinner: 'el-icon-loading',
            });
            setTimeout(() => {
                this.loading.close();
            }, 2000);
        },

        submitForm(login) {
            this.$refs[login].validate((valid) => {
                if (valid) {
                    this.loadings(); //加载动画
                    //提交表单
                    this.$http.post('/login', {
                        firstName: this.login.firstName,
                        email: this.login.email,
                    }).then(result => {
                        if (result.body.success) {
                            window.location.href = "/user";
                            this.loading.close();
                        } else {
                            // 弹出错误信息框
                            this.$emit(
                                'submit-form',
                                this.$message({
                                    message: '用户名或密码错误！',
                                    type: 'warning',
                                    duration: 6000
                                }),
                            );
                            // 清空表单状态
                            this.$refs[login].resetFields();
                        }
                    });
                } else {
                    this.$emit(
                        'submit-form',
                        this.$message({
                            message: '输入信息有误！',
                            type: 'warning',
                            duration: 6000
                        }),
                    );
                    return false;
                }
            });
        },
        loginEnter(login){
            this.submitForm(login);
        },

    }
});