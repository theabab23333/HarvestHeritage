---
navigation:
  parent: mechanism/mechanism_index.md
  title: 杂交
  position: 120
categories:
  - hybrid
---

# 杂交

两个成熟作物之间隔着一个空作物架时，可杂交产生新的种子袋；子代会继承或改变亲本的数值。

## 条件

两个成熟作物之间须隔一个空作物架，三个作物架须位于同一高度和水平直线上。

示例：

<GameScene zoom="3" interactive={true}>
  <ImportStructure src="../assets/structure/hybrid.snbt"></ImportStructure>
  <IsometricCamera yaw="180" pitch="10"></IsometricCamera>

  <BlockAnnotation x="0" y="0" z="0" color="#97d9e1"> 
  亲本所需方块
  </BlockAnnotation>

  <BlockAnnotation x="2" y="0" z="0" color="#97d9e1"> 
  亲本所需方块
  </BlockAnnotation>

  <BlockAnnotation x="0" y="1" z="0" color="#97d9e1"> 
  亲本作物（<ItemImage id="minecraft:coal"></ItemImage>）
  </BlockAnnotation>

  <BlockAnnotation x="2" y="1" z="0" color="#97d9e1"> 
  亲本作物（<ItemImage id="minecraft:emerald"></ItemImage>）
  </BlockAnnotation>

  <BlockAnnotation x="1" y="1" z="0" color="#fda085"> 
  可能产出的种子
    <ItemImage id="minecraft:diamond"></ItemImage>
    <ItemImage id="minecraft:amethyst_shard"></ItemImage>
    <ItemImage id="minecraft:quartz"></ItemImage>
    <ItemImage id="minecraft:emerald"></ItemImage>
    <ItemImage id="minecraft:coal"></ItemImage>
  </BlockAnnotation>
</GameScene>

规则：

- 两侧作物均须达到种子要求的生长阶段。
- 两侧作物必须来自不同的种子。
- 中间必须为空作物架。
- 仅检测东西南北四个水平方向；斜向、竖直方向或中间没有作物架时均不能杂交。
- 成熟作物受到随机刻时才会触发检测，且其下方所需方块条件仍须满足。
- 杂交成功后，新种子袋会放入中间的作物架；两侧作物不会被消耗，当前生长阶段也不会重置。
- 每个成熟作物每次受到随机刻都会尝试检测一次；中间已有种子袋时不会覆盖。

## 种子抽取

设匹配配方共提供 `H` 个输出条目（重复条目分别计数），候选池再加入两个亲本种子，共 `H + 2` 项。

抽取过程分为两步：

1. 有 `20%` 的概率直接选择亲本，两个亲本各占一半。
2. 否则（`80%`）从 `H` 个杂交输出与两个亲本中**等概率**选择一项。

因此：

```text
P(选择某个亲本)       = 10% + 80% / (H + 2)
P(选择某个杂交输出)   = 80% / (H + 2)
```

典型情况：

| 匹配到的输出条目 `H` | 每个亲本概率 | 每个杂交输出概率 |
|---------------------:|-------------:|-----------------:|
|                    0 |       50.00% |                - |
|                    1 |       36.67% |           26.67% |
|                    3 |       26.00% |           16.00% |
|                   13 |       15.33% |            5.33% |

> 若无匹配配方（`H = 0`），仍会以各 `50%` 的概率选择一个亲本，并正常合并数值。
>
> 如果数据包中完全没有任何杂交配方，杂交逻辑会直接结束，不会生成种子袋。
>
> 同一个输出在候选池中出现多次时，会按出现次数提高被选中的概率。

## 数值继承

生长速度与产出数量分别独立计算，互不影响。设两个亲本的同一数值分别为 `v1`、`v2`：

```text
m = min(v1, v2)                // 较低值
a = floor((v1 + v2) / 2)       // 平均值，向下取整
M = 配置文件中的最大值          // 默认 31
```

子代数值按以下概率抽取：

|  概率 | 结果                         | 说明                                  |
|------:|------------------------------|---------------------------------------|
|  `5%` | `max(1, m - 1 - rand(0..1))` | 降低 `1 ~ 2` 点，最低为 `1`           |
| `75%` | `min(M, a + 1 + rand(0..1))` | 在平均值上提高 `1 ~ 2` 点，最高为 `M` |
| `20%` | `a`                          | 保持平均值                            |

其中 `rand(0..1)` 表示等概率取得 `0` 或 `1`。

不考虑上下限时，单次数值的期望约为：

```text
E[v] = 0.05 * (m - 1.5) + 0.75 * (a + 1.5) + 0.20 * a
     = 0.95 * a + 0.05 * m + 1.05
```

因此，子代数值通常高于两个亲本的平均值；但低数值会受到最小值 `1` 的限制，
高数值会受到配置上限的限制。