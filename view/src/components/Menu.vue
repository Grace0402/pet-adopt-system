<template>
	<div>
		<!-- Logo -->
		<div class="logo">
			<img src="/logo.png" alt="" srcset="">
			<span>宠物领养系统</span>
		</div>
		<!-- 导航区 -->
		<ul class="menu" :style="{ backgroundColor: bag, padding: '12px 6px' }">
			<li v-for="(item, index) in routes" :key="index" class="menu-item" v-if="item.show"
				:class="{ 'active': activeIndex === item.path }" @click="handleSelect(item.path)">
				<i :class="item.icon" class="menu-icon"
					:style="{ fontWeight: activeIndex === item.path ? '600' : '' }"></i>
				<span class="menu-title">{{ item.name }}</span>
			</li>
		</ul>
	</div>
</template>

<script>
import Logo from '@/components/Logo.vue';
export default {
	name: 'Menu',
	components: { Logo },
	data() {
		return {
			activeIndex: "1",
			isCollapse: true,
			selectedMenuItem: '',

		}
	},
	props: {
		routes: {
			type: Array,
			required: true
		},
		bag: {
			type: String,
			default: '#FFFFFF'
		}
	},
	created() {
		const saveLastPath = sessionStorage.getItem('activeMenuItem');
		if (saveLastPath === null) {
			this.handleSelect(this.routes[0].path);
		} else {
			this.handleSelect(saveLastPath);
		}
	},
	methods: {
		handleSelect(index) {
			this.activeIndex = index;
			this.$emit('select', this.activeIndex);
			const routeIn = this.routes.filter(e => e.path === index);
			this.$emit('route-listener', routeIn[0]);
			sessionStorage.setItem('activeMenuItem', this.activeIndex);
		},
	},
};
</script>

<style scoped>
.logo {
	font-weight: bold;
	font-size: 18px;
	display: flex;
	align-items: center;
	justify-content: left;
	margin-inline: 14px;
	flex-wrap: wrap;
	height: 40px;
	user-select: none;
	font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;

	img {
		width: 30px;
		height: 30px;
	}

	span {
		margin-left: 8px;
		color: #634d4d;
	}
}

.menu {
	list-style: none;
	margin: 0;
	padding: 0;
	width: 100%;
	box-sizing: border-box;
}

.menu-item {
	height: 36px;
	line-height: 36px;
	margin-block: 6px;
	padding: 12px 6px;
	cursor: pointer;
	display: flex;
	box-sizing: border-box;
	align-items: center;
	color: #333;
	user-select: none;
	border-radius: 5px;
	justify-content: left;
	white-space: nowrap;
	overflow: hidden;
	box-sizing: border-box;
	i{
		margin-right: 6px;
	}
}

.menu-item:hover {
	background-color: rgb(239, 240, 240);
}

.menu-icon {
	font-size: 16px;
	display: flex;
	align-items: center;
	justify-content: center;
}

.menu-title {
	font-size: 14px;
	transition: opacity 0.3s ease;
}

.active {
	background-color: rgb(239, 240, 240);
	/* font-weight: bold; */
	border-radius: 6px;
	color: rgb(31, 31, 31);

	i {
		font-weight: 900;
	}
}

/* When collapsed */
.menu.collapsed .menu-item {
	justify-content: center;
	padding: 0;
}

.menu.collapsed .menu-icon {
	margin-right: 0;
}
</style>