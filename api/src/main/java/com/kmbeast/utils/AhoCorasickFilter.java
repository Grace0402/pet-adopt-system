package com.kmbeast.utils;

import java.util.*;

/**
 * Trie算法树替换敏感词实现类 - 带失败指针的AC自动机（性能更好）
 * Trie树（前缀树）是一种高效的数据结构，其核心优势在于能够共享敏感词的公共前缀，减少不必要的重复匹配。
 * 数学表达 ：
 * 设：S = {w₁, w₂, ..., wₙ} 为敏感词集合，T = t₁t₂...tₘ 为待检测文本，δ: Trie节点转移函数，F ⊆ Q 为接受状态（敏感词结束节点）
 * 匹配过程可表示为：
 * 对于文本T中的每个位置i，检查是否存在j使得：δ*(root, tᵢ...tⱼ) ∈ F，其中δ*表示状态转移的闭包。
 */
public class AhoCorasickFilter {

    // 这是敏感词典，需要拓展哪些敏感词，在这里拓展
    // 如果要优化，可以新建一张数据库表，去拓展这些词典，也可以引入redis存储
    public final static List<String> sensitiveWords = Arrays.asList(
            "暴力", "色情", "赌博", "毒品"
    );

    private final AcNode root = new AcNode();

    private static class AcNode {
        Map<Character, AcNode> children = new HashMap<>();
        AcNode fail;
        boolean isEnd;
        int length; // 敏感词长度
    }

    public void addWord(String word) {
        AcNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new AcNode());
            node = node.children.get(c);
        }
        node.isEnd = true;
        node.length = word.length();
    }

    public void buildFailurePointer() {
        Queue<AcNode> queue = new LinkedList<>();
        root.fail = null;
        queue.offer(root);

        while (!queue.isEmpty()) {
            AcNode p = queue.poll();
            for (Map.Entry<Character, AcNode> entry : p.children.entrySet()) {
                char c = entry.getKey();
                AcNode pc = entry.getValue();

                if (p == root) {
                    pc.fail = root;
                } else {
                    AcNode q = p.fail;
                    while (q != null) {
                        if (q.children.containsKey(c)) {
                            pc.fail = q.children.get(c);
                            break;
                        }
                        q = q.fail;
                    }
                    if (q == null) {
                        pc.fail = root;
                    }
                }
                queue.offer(pc);
            }
        }
    }

    public String filter(String text) {
        char[] chars = text.toCharArray();
        AcNode p = root;

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            while (p != root && !p.children.containsKey(c)) {
                p = p.fail;
            }
            if (p.children.containsKey(c)) {
                p = p.children.get(c);
            }
            if (p.isEnd) {
                // 替换敏感词
                int start = i - p.length + 1;
                Arrays.fill(chars, start, i + 1, '*');
                p = root; // 重置到根节点
            }
        }
        return new String(chars);
    }

}
