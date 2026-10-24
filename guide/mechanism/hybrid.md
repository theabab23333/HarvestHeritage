---
navigation:
  parent: mechanism/mechanism_index.md
  title: 杂交
  position: 120
categories:
  - hybrid
---

# 杂交

杂交可以让两个成熟作物产生新的种子袋，并让子代继承或改变父代的数值。

## 触发条件

杂交需要两个成熟作物之间隔着一个空的作物架，并且三个方块位于同一高度、
同一水平直线上：

(这是个例子)

<GameScene zoom="3" interactive={true}>
  <ImportStructure src="../assets/structure/hybrid.snbt"></ImportStructure>
  <IsometricCamera yaw="180" pitch="10"></IsometricCamera>

  <BlockAnnotation x="0" y="0" z="0" color="#97d9e1"> 
  父本作物需要的方块
  </BlockAnnotation>

  <BlockAnnotation x="2" y="0" z="0" color="#97d9e1"> 
  父本作物需要的方块
  </BlockAnnotation>

  <BlockAnnotation x="0" y="1" z="0" color="#97d9e1"> 
  父本作物(煤炭)
  </BlockAnnotation>

  <BlockAnnotation x="2" y="1" z="0" color="#97d9e1"> 
  父本作物(绿宝石)
  </BlockAnnotation>

  <BlockAnnotation x="1" y="1" z="0" color="#fda085"> 
  可能杂交出的种子
    <ItemImage id="minecraft:diamond"></ItemImage>
    <ItemImage id="minecraft:amethyst_shard"></ItemImage>
    <ItemImage id="minecraft:quartz"></ItemImage>
    <ItemImage id="minecraft:emerald"></ItemImage>
    <ItemImage id="minecraft:coal"></ItemImage>
  </BlockAnnotation>
</GameScene>

具体规则：

- 两侧作物都必须达到各自的成熟生长阶段。
- 中间必须是作物架，且没有放入种子袋。
- 只扫描东西南北四个水平方向。斜向、竖直方向或中间没有作物架都不能杂交。
- 触发扫描的成熟作物需要受到随机刻，且它下方所需的方块条件仍然满足。
- 杂交成功后，种子袋会放入中间的空作物架，两侧作物不会被消耗，生长阶段也不会重置。
- 每个成熟作物每次受到随机刻都会尝试寻找一次；中间已经有种子袋后，不会再次覆盖。

## 种子种类抽取

设所有匹配配方一共提供了 `H` 个输出条目（重复条目也分别计数），
候选池中还会加入两个父本种子，因此总候选数为 `H + 2`。

抽取过程分为两步：

1. 有 `20%` 的概率直接选择父本，两个父本各占一半。
2. 否则（`80%`）从 `H` 个杂交输出和两个父本中 **等概率**选择一个。

因此：

```text
P(选择某个父本)       = 10% + 80% / (H + 2)
P(选择某个杂交输出)   = 80% / (H + 2)
```

几个典型情况：

| 匹配到的输出条目 `H` | 每个父本概率 | 每个杂交输出概率 |
|---------------------:|-------------:|-----------------:|
|                    0 |       50.00% |                - |
|                    1 |       36.67% |           26.67% |
|                    3 |       26.00% |           16.00% |
|                   13 |       15.33% |            5.33% |

> 如果没有匹配到具体配方（`H = 0`），仍会以各 `50%` 的概率选择一个父本，并正常合并数值。
>
> 如果数据包中完全没有任何杂交配方，杂交逻辑会直接结束，不会生成种子袋。
>
> 同一个输出在候选池中出现多次时，会按出现次数提高被选中的概率。

## 数值继承

生长速度和产出数量分别独立计算，互不影响。设父代的某个数值为 `v1`、`v2`：

```text
m = min(v1, v2)                // 较低值
a = floor((v1 + v2) / 2)       // 平均值，向下取整
M = 配置文件中的最大值          // 默认 31
```

子代数值按下面的概率抽取：

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

因此，子代大多数情况下会比父代的平均值更高；但低数值父本会受到最低值 `1` 的限制，
高数值则会受到配置上限的限制。