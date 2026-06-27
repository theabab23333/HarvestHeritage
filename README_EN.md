# HarvestHeritage

[简体中文](README.md)

> "I admire the first person who beat seeds out of grass — maybe it was wheat seeds."

Bringing the IC2 crop breeding system to modern Minecraft, simplified and expanded.

When you break grass, you get more than just wheat seeds.

---

## 🎮 Quick Start

### 1. Hybrid Breeding

When two crop stands in a row (with one empty stand between them) are both mature, the empty stand will trigger hybridization, producing a
new variety.

Breeding inheritance rules:

- Speed and output are inherited from both parents
- Stats can be improved through multiple generations
- Check JEI for specific hybrid recipes

### 2. Large-Scale Farming

Right-click a **Scaffolding** block with a **Seed Packet** to convert it into a **Scaffolding Crop Stand** and plant simultaneously.

Scaffolding crop stands do not support hybridization but can be stacked vertically, ideal for automated farms.

---

## ⚙️ Configuration

Server config (`harvestheriest-server.toml`):

- `seed_speed_max` — Maximum seed speed stat (default: 31)
- `output_max` — Maximum output per harvest (default: 31)

---

## 🔌 Datapack

Place JSON files in `data/<namespace>/harvestheritage_seeds/` to register custom seeds.

Example:

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

- `seed` — Item displayed on the seed packet, required
- `category` — Available: `crop`, `animal`, `mob`, `material`, `special`, `misc`; defaults to `misc` when empty
- `results` — Items harvested when mature, required
- `stage` — Growth stages required to reach maturity, required
- `need_block` — Block required below the farmland for the crop to grow; can be omitted for no requirement

---

## ❤️ Credits

- **theabab2333** — Author
- **Abslb** & **GTriXy** — Contributors
- IC2 classic breeding system — Inspiration
