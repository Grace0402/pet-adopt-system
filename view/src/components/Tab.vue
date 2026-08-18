<template>
    <div class="sliding-button-group">
        <div class="buttons-container">
            <button 
                v-for="(button, index) in buttons" 
                :key="index" 
                :class="{ active: activeValue === button.value }"
                @click="selectButton(button)"
            >
                {{ button.label }}
            </button>
            <div class="slider" :style="sliderStyle"></div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'SlidingButtonGroup',
    props: {
        buttons: {
            type: Array,
            default: () => [],
            validator: buttons => buttons.every(b => 'value' in b && 'label' in b)
        },
        initialActive: {
            type: [Number, String], // 支持多种类型的value
            default: 'null'
        }
    },
    data() {
        return {
            activeValue: this.initialActive
        }
    },
    computed: {
        activeButtonIndex() {
            return this.buttons.findIndex(b => b.value === this.activeValue)
        },
        sliderStyle() {
            if (this.buttons.length === 0) return {}
            
            return {
                width: `${100 / this.buttons.length}%`,
                transform: `translateX(${this.activeButtonIndex * 100}%)`,
                transition: 'transform 0.3s ease'
            }
        }
    },
    methods: {
        selectButton(button) {
            if (this.activeValue !== button.value) {
                this.activeValue = button.value
                this.$emit('change', button)
            }
        }
    }
}
</script>

<style scoped>
.sliding-button-group {
    background-color: rgb(245, 245, 245);
    padding: 4px;
    border-radius: 5px;
    display: inline-block;
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}

.buttons-container {
    position: relative;
    display: flex;
    border-radius: 4px;
    background-color: rgb(245, 245, 245);
    overflow: hidden;
}

button {
    flex: 1;
    min-width: 100px; /* 最小宽度替代固定宽度 */
    padding: 5px 8px;
    border: none;
    background: transparent;
    cursor: pointer;
    font-size: 14px;
    color: #000;
    position: relative;
    z-index: 1;
    transition: color 0.3s;
    text-align: center;
}

button.active {
    color: #333;
}

.slider {
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    background-color: rgb(255, 255, 255);
    border-radius: 5px;
    z-index: 0;
}
</style>