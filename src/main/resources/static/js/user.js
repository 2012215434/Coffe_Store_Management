//设置全局表单提交格式
// Vue.http.options.emulateJSON = true;

//Vue实例
new Vue({
    el: '#app',
    data() {
        return {
            info: {}
        }
    },
    methods: {
        getUser() {
            this.$http.post('/user').then(result => {
                if (result.body.success) {
                    this.info = result.body.message;
                } else {
                    // 弹出错误信息框
                    this.$emit(
                        'submit-form',
                        this.$message({
                            message: '获取用户信息失败',
                            type: 'warning',
                            duration: 6000
                        }),
                    );
                }
            });
        }
    },
    created() {
        this.getUser();
    }
});