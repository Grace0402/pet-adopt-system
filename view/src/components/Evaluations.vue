<template>
    <div class="comment-container">
        <!-- 评论头部 -->
        <div class="comment-header">
            <h2>评论</h2>
        </div>

        <!-- 评论输入区域 -->
        <div class="comment-input-area">
            <div class="user-avatar">
                <img :src="avatar" alt="用户头像">
            </div>
            <div class="comment-form" :class="{ focused: isFocused }">
                <textarea style="resize: none;" v-model="content" placeholder="请友好交流" @focus="onFocus" @blur="onBlur"
                    maxlength="300"></textarea>
                <div class="comment-actions">
                    <span class="char-count">{{ content.length }} / 300</span>
                    <button class="submit-btn" @click="commentClick" :disabled="content.length === 0">评论</button>
                </div>
            </div>
        </div>

        <!-- 评论列表 -->
        <div class="comment-list">
            <div v-for="(comment, index) in commentList" :key="index" class="comment-item">
                <!-- 评论主体 -->
                <div class="comment-main">
                    <div class="comment-avatar">
                        <img :src="comment.avatar" alt="用户头像">
                    </div>
                    <div class="comment-content">
                        <div class="comment-meta">
                            <span class="username">{{ comment.username }}</span>
                            <span v-if="comment.userId == userId" class="user-tag">我自己</span>
                        </div>
                        <div class="comment-text">{{ comment.content }}</div>
                        <div class="opr">
                            <div>
                                {{ comment.time }}
                            </div>
                            <div class="btn">
                                <div v-if="comment.userId == userId" @click="showDeleteConfirm(comment)">
                                    <i class="el-icon-delete"></i>
                                    <span>删除</span>
                                </div>
                                <div @click="toggleReplyInput(comment)">
                                    <i class="el-icon-chat-round"></i>
                                    <span>{{ !comment.showReplyInput ? '回复' : '取消回复' }}</span>
                                </div>
                                <div class="upvote" @click="upvote(comment)">
                                    <img v-if="!comment.upvoteFlag" src="/update-active.png" alt="">
                                    <img v-else src="/update-default.png" alt="">
                                    <span class="count" :class="{ animate: upvoteAnimate === comment.id }">
                                        {{ comment.upvoteCount || 0 }}
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 回复输入框 -->
                <div v-if="comment.showReplyInput" class="reply-input-container">
                    <div class="comment-form">
                        <textarea style="resize: none;" v-model="replyContent" placeholder="我要回复"
                            maxlength="300"></textarea>
                        <div class="comment-actions">
                            <span class="char-count">{{ replyContent.length }} / 300</span>
                            <button class="submit-btn" @click="submitReply(comment)"
                                :disabled="replyContent.length === 0">评论</button>
                        </div>
                    </div>
                </div>

                <!-- 子评论 -->
                <div class="child-comments">
                    <div v-for="(child, childIndex) in comment.commentChildVOS" :key="childIndex" class="child-comment">
                        <div class="comment-main">
                            <div class="comment-avatar">
                                <img :src="child.avatar" alt="用户头像">
                            </div>
                            <div class="comment-content">
                                <div class="comment-meta">
                                    <span class="username">{{ child.username }}</span>
                                    <span v-if="child.userId == userId" class="user-tag">我自己</span>
                                    <span v-if="child.replierName != null" class="reply-to">
                                        回复 <img :src="child.replierAvatar" class="replier-avatar"> {{
                                            child.replierName }}
                                        <span v-if="child.replierId == userId" class="user-tag">我自己</span>
                                    </span>
                                </div>
                                <div class="comment-text">{{ child.content }}</div>
                                <div class="opr">
                                    <div>
                                        {{ child.time }}
                                    </div>
                                    <div class="btn">
                                        <div v-if="child.userId == userId" @click="showDeleteConfirm(child)">
                                            <i class="el-icon-delete"></i>
                                            <span>删除</span>
                                        </div>
                                        <div @click="toggleChildReplyInput(child)">
                                            <i class="el-icon-chat-round"></i>
                                            <span>{{ !child.replyInputStatus ? '回复' : '取消回复' }}</span>
                                        </div>
                                        <div class="upvote" @click="upvote(child)">
                                            <img v-if="!child.upvoteFlag" src="/update-active.png" alt="">
                                            <img v-else src="/update-default.png" alt="">
                                            <span class="count" :class="{ animate: upvoteAnimate === comment.id }">
                                                {{ child.upvoteCount || 0 }}
                                            </span>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <!-- 子评论的回复输入框 -->
                        <div v-if="child.replyInputStatus" class="reply-input-container">
                            <div class="comment-form">
                                <textarea style="resize: none;" v-model="replyChildContent" placeholder="我要回复"
                                    maxlength="300"></textarea>
                                <div class="comment-actions">
                                    <span class="char-count">{{ replyChildContent.length }} / 300</span>
                                    <button class="submit-btn" @click="submitReply1(child)"
                                        :disabled="replyChildContent.length === 0">评论</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 删除确认弹窗 -->
        <div v-if="showDeleteModal" class="modal-overlay">
            <div class="modal-content">
                <h3>删除该条评论？</h3>
                <div class="modal-actions">
                    <button class="cancel-btn" @click="showDeleteModal = false">不删了</button>
                    <button class="confirm-btn" @click="confirmDelete">好的</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { timeAgo } from '@/utils/date'
export default {
    props: {
        contentId: {
            type: Number,
            default: ''
        },
        contentType: {
            type: String,
            default: ''
        },
        userId: {
            type: Number,
            required: true
        },
        avatar: {
            type: String,
            required: true
        }
    },
    data() {
        return {
            userData: {},
            commentList: [],
            content: '',
            replyContent: '',
            replyChildContent: '',
            isFocused: false,
            evaluationsCount: 0,
            upvoteAnimate: null,
            showDeleteModal: false,
            commentToDelete: null
        }
    },
    watch: {
        contentId(newVal, oldVal) {
            if (newVal !== oldVal) {
                this.loadCommentList();
            }
        },
        content() {
            if (this.content === '') {
                this.isFocused = false;
            }
        },
    },
    created() {
        this.loadCommentList();
    },
    methods: {
        upvote(comment) {
            this.upvoteAnimate = comment.id;
            setTimeout(() => this.upvoteAnimate = null, 1000);

            this.$axios.post(`evaluations/upvoteOperation`, { id: comment.id }).then(res => {
                if (res.code == 200) {
                    let rep = res.data;
                    comment.upvoteCount = Number(rep.count);
                    comment.upvoteFlag = Boolean(rep.haveUpvote);
                }
            }).catch(err => {
                console.error(`点赞操作异常 -> `, err);
            })
        },
        showDeleteConfirm(comment) {
            this.commentToDelete = comment;
            this.showDeleteModal = true;
        },
        confirmDelete() {
            this.deleteComment(this.commentToDelete);
            this.showDeleteModal = false;
        },
        deleteComment(comment) {
            this.$axios.delete(`evaluations/${comment.id}`).then(res => {
                if (res.code == 200) {
                    this.showToast('删除成功', 'success');
                    this.loadCommentList();
                }
            }).catch(err => {
                console.error(`评论异常 -> `, err);
            })
        },
        onFocus() {
            this.isFocused = true;
        },
        onBlur() {
            if (this.content === '') {
                this.isFocused = false;
            }
        },
        commentClick() {
            if (this.content == '') {
                this.showToast('评论内容为空', 'info');
                return;
            }
            const evaluations = {
                contentType: this.contentType,
                content: this.content,
                contentId: this.contentId,
            }
            this.$axios.post(`evaluations/insert`, evaluations).then(res => {
                if (res.code == 200) {
                    this.content = '';
                    this.showToast(res.message, 'success');
                    setTimeout(() => {
                        this.loadCommentList()
                    }, 1100)
                } else {
                    this.showToast(res.message, 'error');
                }
            }).catch(err => {
                this.showToast(err.message, 'error');
                // console.error(`评论异常 -> `, err.message);
            })
        },
        toggleReplyInput(comment) {
            this.replyContent = '';
            this.commentList.map(entity => {
                if (entity.id !== comment.id && entity.showReplyInput) { // 同时间只能打开一个回复
                    entity.showReplyInput = false;
                    comment.replyText = undefined;
                }
            })
            if (comment.showReplyInput == null) {
                comment.showReplyInput = false;
            }
            comment.showReplyInput = !comment.showReplyInput;
        },
        toggleChildReplyInput(comment) {
            this.replyChildContent = '';
            // 确保replyInputStatus属性存在
            this.$set(comment, 'replyInputStatus', !comment.replyInputStatus);

            // 关闭其他所有打开的回复框
            this.closeOtherReplyInputs(comment.id);
        },

        // 关闭其他所有回复框
        closeOtherReplyInputs(currentCommentId) {
            // 处理主评论
            this.commentList.forEach(parentComment => {
                if (parentComment.id !== currentCommentId && parentComment.showReplyInput) {
                    this.$set(parentComment, 'showReplyInput', false);
                }

                // 处理子评论
                if (parentComment.commentChildVOS) {
                    parentComment.commentChildVOS.forEach(childComment => {
                        if (childComment.id !== currentCommentId && childComment.replyInputStatus) {
                            this.$set(childComment, 'replyInputStatus', false);
                        }
                    });
                }
            });
        },
        submitReply(comment) {
            if (this.replyContent == '') {
                this.showToast('评论内容不能为空', 'info');
                return;
            }
            const evaluationsDTO = {
                contentType: this.contentType,
                content: this.replyContent,
                contentId: this.contentId,
                parentId: comment.id
            }
            this.$axios.post(`evaluations/insert`, evaluationsDTO).then(res => {
                console.log(res);
                if (res.code == 200) {
                    this.replyContent = '';
                    comment.showReplyInput = false;
                    this.showToast('回复成功', 'success');
                    setTimeout(() => {
                        this.loadCommentList();
                    }, 1300)
                } else {
                    this.showToast(res.message, 'error');
                }
            }).catch(err => {
                this.showToast(err.message, 'error');
            })
        },
        submitReply1(comment) {
            if (this.replyChildContent == '') {
                this.showToast('评论内容不能为空', 'info');
                return;
            }
            const evaluationsDTO = {
                replierId: comment.userId,
                contentType: this.contentType,
                content: this.replyChildContent,
                contentId: this.contentId,
                parentId: comment.parentId
            }
            this.$axios.post(`evaluations/insert`, evaluationsDTO).then(res => {

                if (res.code == 200) {
                    this.replyChildContent = '';
                    comment.replyInputStatus = false;
                    this.showToast('回复成功', 'success');
                    setTimeout(() => {
                        this.loadCommentList();
                    }, 1300)
                } else {
                    this.showToast(res.message, 'error');
                }
            }).catch(err => {
                this.showToast(err.message, 'success');
            })
        },
        loadCommentList() {
            this.$axios.get(`evaluations/list/${this.contentId}/${this.contentType}`).then(res => {
                if (res.code == 200) {
                    this.commentList = res.data.data;
                    this.evaluationsCount = res.data.evaluationsCount;
                    this.commentList.forEach(entity => {
                        entity.time = timeAgo(entity.createTime);
                        entity.commentChildVOS.forEach(entity => entity.time = timeAgo(entity.createTime));
                    });
                }
            }).catch(err => {
                console.error(`评论查询异常异常 -> `, err);
            })
        },
        showToast(message, type) {
            this.$notify({
                type: type,
                title: '评论',
                message: message,
                duration: 1000,
                position: 'buttom-right'
            })
        }
    }
};
</script>

<style scoped>
* {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}

.comment-container {
    width: 100%;
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
}

/* 评论头部 */
.comment-header {
    margin-bottom: 20px;
}

.comment-header h2 {
    font-size: 1.5rem;
    text-align: left;
    color: #252933;
    font-weight: 600;
}

/* 评论输入区域 */
.comment-input-area {
    display: flex;
    gap: 15px;
    margin-bottom: 30px;
}

.user-avatar {
    width: 40px;
    height: 40px;
    flex-shrink: 0;
}

.user-avatar img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    object-fit: cover;
}

.comment-form {
    flex: 1;
    border-radius: 12px;
    transition: all 0.3s ease;
    background-color: rgb(245, 245, 246);
}

.comment-form textarea {
    width: 100%;
    min-height: 100px;
    padding: 12px;
    border: none;
    border-radius: 8px 8px 0 0;
    resize: vertical;
    font-size: 1rem;
    background-color: transparent;
    outline: none;
}

.comment-actions {
    display: flex;
    justify-content: left;
    align-items: center;
    font-size: 14px;
    padding: 8px 12px;
}

.char-count {
    font-size: 0.875rem;
    color: #6b7280;
}

.submit-btn {
    padding: 6px 16px;
    background-color: #3b82f6;
    color: white;
    border: none;
    border-radius: 20px;
    font-size: 0.875rem;
    cursor: pointer;
    transition: background-color 0.2s;
}

.submit-btn:disabled {
    background-color: #9ca3af;
    cursor: not-allowed;
}

.submit-btn:not(:disabled):hover {
    background-color: rgb(78, 110, 242);
}

/* 评论列表 */
.comment-list {
    display: flex;
    flex-direction: column;
    gap: 4px;
}


.comment-main {
    display: flex;
    gap: 12px;
}

.comment-avatar {
    width: 40px;
    height: 40px;
    flex-shrink: 0;
}

.comment-avatar img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    object-fit: cover;
}

.comment-content {
    flex: 1;
}

.opr {
    display: flex;
    justify-content: space-between;

    margin-top: 10px;
    font-size: 13px;
    color: #9195a3;

    .btn {
        display: flex;
        justify-content: left;
        align-items: center;
        gap: 12px;
        color: #222;
        cursor: pointer;

        .upvote {
            display: flex;
            justify-content: left;
            align-items: center;
            gap: 4px;

            img {
                width: 16px;
                height: 16px;
            }
        }



        i {
            margin-right: 4px;
        }
    }
}

.comment-meta {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    gap: 8px;
    margin-bottom: 8px;
}

.username {
    font-weight: 600;
    font-size: 13px;
    color: #222;
}

.user-tag {
    font-size: 0.75rem;
    padding: 2px 4px;
    background-color: #e8f0fb;
    color: #1e40af;
    border-radius: 4px;
}

.comment-time {
    font-size: 0.875rem;
    color: #6b7280;
}

.comment-text {
    font-weight: 400px !important;
    font-size: 15px;
    margin-top: 10px;
    text-align: left;
    color: #222;
}

.comment-actions {
    display: flex;
    gap: 16px;
}

.action-btn {
    display: flex;
    align-items: center;
    background: none;
    border: none;
    color: #6b7280;
    font-size: 14px;
    cursor: pointer;
    transition: color 0.2s;
}

.action-btn:hover {
    color: #3b82f6;
}

.action-btn .icon {
    font-size: 1rem;
}

.upvote-btn.active {
    color: #3b82f6;
}

.count {
    transition: all 0.3s ease;
}

.count.animate {
    animation: pulse 0.5s;
}

@keyframes pulse {
    0% {
        transform: scale(1);
    }

    50% {
        transform: scale(1.5);
    }

    100% {
        transform: scale(1);
    }
}

/* 子评论 */
.child-comments {
    margin-top: 16px;
    margin-left: 35px;
    padding-left: 16px;
}

.child-comment {
    gap: 12px;
    margin-bottom: 16px;
}

.reply-to {
    display: flex;
    align-items: center;
    gap: 4px;
    font-size: 0.875rem;
    color: #6b7280;
}

.replier-avatar {
    width: 16px;
    height: 16px;
    border-radius: 50%;
}

/* 回复输入框 */
.reply-input-container {
    margin-top: 12px;
    margin-left: 52px;
}

/* 删除确认弹窗 */
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal-content {
    background-color: white;
    padding: 24px;
    border-radius: 8px;
    max-width: 400px;
    width: 90%;
}

.modal-content h3 {
    margin-bottom: 20px;
    font-size: 1.25rem;
    color: #1f2937;
}

.modal-actions {
    display: flex;
    justify-content: flex-end;
    gap: 12px;
}

.cancel-btn,
.confirm-btn {
    padding: 8px 16px;
    border: none;
    border-radius: 4px;
    font-size: 0.875rem;
    cursor: pointer;
    transition: background-color 0.2s;
}

.cancel-btn {
    background-color: #c26a33;
    color: #fff;
}

.cancel-btn:hover {
    background-color: #c1652b;
}

.confirm-btn {
    background-color: #ef4444;
    color: white;
}

.confirm-btn:hover {
    background-color: #dc2626;
}

/* 移动端适配 */
@media (max-width: 768px) {
    .comment-container {
        padding: 15px;
    }

    .comment-input-area {
        gap: 10px;
    }

    .user-avatar,
    .comment-avatar {
        width: 48px;
        height: 48px;
    }

    .comment-form textarea {
        min-height: 80px;
    }

    .child-comments {
        margin-left: 36px;
    }

    .reply-input-container {
        margin-left: 36px;
    }

    .comment-actions {
        gap: 12px;
    }

    .action-btn {
        font-size: 0.75rem;
    }
}

@media (max-width: 480px) {
    .comment-header h2 {
        font-size: 1.25rem;
    }

    .comment-form textarea {
        font-size: 0.875rem;
    }

    .child-comments {
        margin-left: 20px;
        padding-left: 10px;
    }

    .reply-input-container {
        margin-left: 20px;
    }
}
</style>