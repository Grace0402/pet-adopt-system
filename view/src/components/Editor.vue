<template>
    <div>
        <Toolbar 
            style="border-bottom: 1px solid #eae8e8;" 
            :editor="editor" 
            :defaultConfig="toolbarConfig"
            :mode="mode" 
        />
        <Editor 
            :style="{ height: height, overflowY: 'hidden' }" 
            v-model="content" 
            :defaultConfig="editorConfig"
            :mode="mode" 
            @onCreated="onCreated" 
        />
    </div>
</template>

<script>
import Vue from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import '@wangeditor/editor/dist/css/style.css'  // 引入编辑器样式

export default Vue.extend({
    components: { Editor, Toolbar },
    props: {
        receiveContent: {
            type: String,
            default: '',
            required: true
        },
        height: {
            type: String,
            default: '500px'
        },
        api: {
            type: String,
            required: true
        }
    },
    data() {
        return {
            editor: null,
            content: '<p>创作内容</p>',
            toolbarConfig: {
                excludeKeys: [  // 在这里定义要排除的菜单项
                    'group-video',
                    'code-block',
                    'todo',
                    'code',
                    'insertLink',
                    'codeBlock',
                    'insertTable'
                ]
            },
            editorConfig: {
                placeholder: '请输入内容...',
                MENU_CONF: {
                    uploadImage: {
                        server: this.api,  // 使用props传入的api
                        fieldName: 'file',
                        maxFileSize: 10 * 1024 * 1024,
                        maxNumberOfFiles: 10,
                        metaWithUrl: false,
                        withCredentials: true,
                        timeout: 10 * 1000,
                        customInsert(res, insertFn) {
                            insertFn(res.data, res.data, res.data);
                        },
                    },
                }
            },
            mode: 'default',
        }
    },
    methods: {
        onCreated(editor) {
            this.editor = Object.seal(editor);
            // 如果需要动态修改toolbar配置，可以在这里操作
            // this.toolbarConfig = {...}
        },
    },
    watch: {
        receiveContent: {
            handler(newVal) {
                console.log("接收内容：", newVal);
                this.content = newVal;
            },
            deep: true,
            immediate: true
        },
        content(newVal) {
            this.$emit('on-listener', newVal);
        },
    },
    beforeDestroy() {
        if (this.editor) {
            this.editor.destroy();
        }
    }
})
</script>

<style>
.w-e-toolbar {
    background-color: #f8f8f8 !important;
    border-bottom: 1px solid #e8e8e8 !important;
}

</style>