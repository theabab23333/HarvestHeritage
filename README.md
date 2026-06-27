# HarvestHeritage · 收获：遗产

[EN_US](README_EN.md)

> "我很敬佩第一个从草里打出种子的人——说不定那是小麦种子呢。"

将 IC2 作物杂交系统带到高版本 Minecraft，并加以简化和扩展。

打草收获的，不止是小麦种子。

---

## 🎮 快速上手

### 1. 杂交

当两个同排作物架（间隔一格）都成熟时，中间的空作物架会触发杂交，产生新品种。

杂交遗传规则：

- 速度/产量各继承自两个亲本
- 可通过多代杂交逐步优化属性
- 特定配方的产出可查阅 JEI

### 2. 大规模种植

手持 **种子袋** 右键点击 **脚手架**，将其转变为 **悬挂作物架** 并同时种植。

悬挂作物架不支持杂交，但支持立体堆叠，适合大规模自动化农场。

---

## ⚙️ 配置

服务端配置（`harvestheriest-server.toml`）：

- `seed_speed_max` — 种子最大生长速度（默认 31）
- `output_max` — 单次最大产量（默认 31）

---

## 🔌 数据包

将 JSON 文件放入 `data/<命名空间>/harvestheritage_seeds/` 即可注册自定义种子。

格式示例：

```json
{
  "seed": "minecraft:diamond",
  "category": "material",
  "results": [
    "minecraft:diamond"
  ],
  "stage": 5,
  "need_block": "minecraft:stone"
}
```

- seed : 对应种子袋上显示的物品，不能为空
- category : 可用类别：`crop`、`animal`、`mob`、`material`、`special`、`misc`，空时默认`misc`
- results : 收获时输出的物品，不能为空
- stage : 作物的生长阶段，不能为空
- need_block : 作物架下方为对应方块时，作物才能生长，可以为空

---

## ❤️ 致谢

- **theabab2333** — 作者
- **Abslb** & **GTriXy** — 贡献者
- IC2 经典杂交系统 — 灵感来源
