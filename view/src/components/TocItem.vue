<template>
    <div class="toc-item" :style="indent">
        <a 
            href="javascript:;" 
            @click="handleClick" 
            :class="{ 'active': isActive }"
            :title="item.text"
        >
            <span class="toc-text">{{ truncatedText }}</span>
            <span class="toc-level" v-if="showLevelBadge">{{ item.level }}</span>
        </a>
        <div v-if="item.children.length > 0" class="toc-children">
            <toc-item 
                v-for="child in item.children" 
                :key="child.id" 
                :item="child"
                @item-click="handleChildClick"
            />
        </div>
    </div>
</template>

<script>
export default {
    name: 'TocItem',
    props: {
        item: {
            type: Object,
            required: true
        },
        showLevelBadge: {
            type: Boolean,
            default: false
        },
        maxTextLength: {
            type: Number,
            default: 30
        }
    },
    data() {
        return {
            isActive: false
        }
    },
    computed: {
        indent() {
            return {
                marginLeft: `${(this.item.level - 1) * 15}px`
            };
        },
        truncatedText() {
            return this.item.text.length > this.maxTextLength 
                ? this.item.text.substring(0, this.maxTextLength) + '...' 
                : this.item.text;
        }
    },
    methods: {
        handleClick() {
            this.$emit('item-click', this.item.id);
        },
        handleChildClick(id) {
            this.$emit('item-click', id);
        }
    },
    mounted() {
        // 监听active类变化
        const observer = new MutationObserver(() => {
            const link = this.$el.querySelector('a');
            if (link) {
                this.isActive = link.classList.contains('active');
            }
        });

        observer.observe(this.$el, {
            attributes: true,
            attributeFilter: ['class'],
            subtree: true
        });

        this.$once('hook:beforeDestroy', () => {
            observer.disconnect();
        });
    }
};
</script>

<style scoped lang="scss">
.toc-item {
    margin: 5px 0;
    line-height: 1.4;
    position: relative;
    
    a {
        display: flex;
        align-items: center;
        color: var(--text-color, #333);
        text-decoration: none;
        cursor: pointer;
        padding: 6px 10px;
        border-radius: 4px;
        transition: all 0.2s ease;
        background: transparent;
        
        &:hover {
            color: var(--primary-color, #1890ff);
            background: var(--hover-bg, rgba(24, 144, 255, 0.1));
        }

        &.active {
            color: var(--primary-color, #1890ff);
            font-weight: bold;
            background: var(--active-bg, rgba(24, 144, 255, 0.1));
        }

        .toc-text {
            flex: 1;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .toc-level {
            font-size: 0.8em;
            background: var(--level-bg, #eee);
            color: var(--level-color, #666);
            border-radius: 50%;
            width: 18px;
            height: 18px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            margin-left: 8px;
        }
    }
}

.toc-children {
    margin-top: 5px;
    margin-left: 15px;
    border-left: 1px dashed var(--border-color, #ddd);
    padding-left: 8px;
}

@media (max-width: 992px) {
    .toc-item {
        a {
            padding: 8px 12px;
        }
    }
}
</style>