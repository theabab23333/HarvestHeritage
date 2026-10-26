---
navigation:
  title: 开始
  position: 10
---

# 开始

本页介绍从打草到收获的基本流程。

## 打草

<Row alignItems="center" fullWidth={true} >
<GameScene zoom="2">
<ImportStructure src="assets/structure/grass.snbt"></ImportStructure>
</GameScene>
<ItemImage id="grass_shear" scale="3"></ItemImage>
</Row>

使用<ItemLink id="grass_shear" />打草，有概率获得<ItemLink id="unknown_seed" />。

---

## 识别

<Row>
<ItemImage id="unknown_seed" scale="3"></ItemImage>
<ItemImage id="magnifying_glass" scale="3"></ItemImage>
<ItemImage id="known_seed" scale="3"></ItemImage>
</Row>

将<ItemLink id="unknown_seed" />丢在地上，对地面使用<ItemLink id="magnifying_glass" />，即可将其识别为<ItemLink id="known_seed" />。

---

## 合成

<Row>
<ItemImage id="seed_packet" scale="3"></ItemImage>
<ItemImage id="crop_stand_block" scale="3"></ItemImage>
<Recipe id="harvestheritage:seedpacket/sugar_cane"></Recipe>
<Recipe id="harvestheritage:crop_stand_block"></Recipe>
</Row>

将<ItemLink id="known_seed" />与<ItemLink id="minecraft:paper" />合成，可获得对应的<ItemLink id="seed_packet" />；再用 4 根<ItemLink id="minecraft:stick" />合成<ItemLink id="crop_stand_block" />。

---

## 播种

<Row>
<BlockImage id="crop_stand_block" scale="3"></BlockImage>
</Row>

放置<ItemLink id="crop_stand_block" />，对空作物架使用<ItemLink id="seed_packet" />播种。作物成熟后，右键作物架即可收获。

---

## 延伸阅读

如需获得更多产物、杂交更多种子，或排查作物不生长的问题，请继续阅读：

- [生长](mechanism/grow.md)
- [杂交](mechanism/hybrid.md)
- [机制](mechanism/mechanism_index.md)