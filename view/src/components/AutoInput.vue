<template>
    <div class="auto-input-container">
        <i class="el-icon-search" @click="search"></i>
        <input @keyup.enter="search" v-model="text" type="text" :placeholder="placeholder">
    </div>
</template>

<script>
export default {
    name: "AutoInput",
    data() {
        return {
            text: ''
        }
    },
    props: {
        placeholder: {
            type: String,
            default: '关键词'
        },
    },
    methods: {
        search() {
            this.$emit('listener', this.text);
        }
    }
};
</script>

<style scoped lang="scss">
.auto-input-container {
    padding: 6px 10px;
    border-radius: 5px;
    display: flex;
    min-width: 100px;
    border: 1px solid rgb(230, 230, 230);
    color: rgb(99, 98, 98);
    justify-content: left;
    box-sizing: border-box;
    align-items: center;

    input {
        outline: none;
        width: 90%;
        border: none;
        transition: all .5s ease;
    }

    i {
        cursor: pointer;
        width: 24px;

        &:hover {
            font-weight: 600;
        }
    }

}
</style>