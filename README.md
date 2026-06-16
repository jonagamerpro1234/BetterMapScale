# BetterMapScale

## Overview

BetterMapScale is a Fabric mod for Minecraft that aims to remove the resolution limitations of vanilla maps.

In vanilla Minecraft, every map uses an internal resolution of **128×128 pixels**, regardless of its zoom level. As a result, larger maps cover more territory but lose detail because the same amount of pixels must represent a much larger area.

The goal of BetterMapScale is to provide **true dynamic map resolutions**, where each map level scales both its coverage and its pixel density.

---

## Main Goal

Maintain a consistent relationship between map pixels and world blocks.

### Vanilla Minecraft

| Level | Coverage         | Resolution     |
| ----- | ---------------- | -------------- |
| 0     | 128×128 blocks   | 128×128 pixels |
| 1     | 256×256 blocks   | 128×128 pixels |
| 2     | 512×512 blocks   | 128×128 pixels |
| 3     | 1024×1024 blocks | 128×128 pixels |
| 4     | 2048×2048 blocks | 128×128 pixels |

### BetterMapScale

| Level | Coverage         | Resolution       |
| ----- | ---------------- | ---------------- |
| 0     | 128×128 blocks   | 128×128 pixels   |
| 1     | 256×256 blocks   | 256×256 pixels   |
| 2     | 512×512 blocks   | 512×512 pixels   |
| 3     | 1024×1024 blocks | 1024×1024 pixels |
| 4     | 2048×2048 blocks | 2048×2048 pixels |

This allows larger maps to display more terrain without sacrificing detail.

---

## Technologies

* Minecraft 1.20.1
* Fabric Loader
* Fabric API
* Fabric Loom
* Java 17

---

## Current Progress

### Completed

* Independent map state system.
* Dynamic map size system.
* Dynamic color array allocation.
* Terrain generation pipeline.
* Persistent map storage.
* Map ID management.
* NBT-based map item integration.
* Dynamic texture generation.
* Dynamic texture caching.
* Map loading from disk.
* Functional map screen renderer.
* Support for multiple dimensions.
* Functional 256×256 map implementation.

### In Progress

* In-hand map rendering.
* Terrain color improvements.
* Incremental map updates.
* Performance optimization.

### Planned

* Multiplayer synchronization.
* Region-based updates.
* Background map generation.
* Configurable map resolutions.
* Advanced terrain coloring.

---

## Technical Challenges

During development it became clear that Minecraft's map system is heavily built around a fixed size of **128×128 pixels**.

Several internal systems rely on hardcoded values such as:

* 128
* 127
* 16384 (128×128)

Examples include:

* MapState
* PlayerUpdateTracker
* MapUpdateS2CPacket
* Map NBT serialization
* Map rendering
* Partial map updates

Because of these limitations, simply increasing the internal map size causes visual corruption and synchronization issues.

Observed issues include:

* Horizontal rendering artifacts.
* Texture corruption.
* Inconsistent synchronization.
* Save/load problems.
* Compatibility limitations with vanilla map systems.

---

## Future Architecture

### Vanilla Maps

Vanilla maps will remain untouched and fully compatible with Minecraft's original implementation.

### Better Maps

A new custom map type is being developed with:

* Independent storage.
* Independent synchronization.
* Independent rendering.
* Dynamic resolutions.
* High-detail terrain representation.
* Future multiplayer support.

Core components:

* BetterFilledMapItem
* BetterEmptyMapItem
* MapState
* MapManager
* MapStorage
* MapRenderer
* Custom networking layer

---

## Current Architecture

### Map Creation

```text
BetterEmptyMapItem
        ↓
MapManager.createMap()
        ↓
MapGenerator
        ↓
MapStorage.saveMap()
        ↓
BetterFilledMapItem
```

### Map Loading

```text
BetterFilledMapItem
        ↓
Map ID (NBT)
        ↓
MapManager.getMap()
        ↓
Memory Cache
        ↓
MapStorage.loadMap()
        ↓
MapState
```

### Rendering Pipeline

```text
MapState
        ↓
MapRenderer
        ↓
MapTexture
        ↓
NativeImage
        ↓
Screen / Future Item Rendering
```

---

## Development Roadmap

### Phase 1 — Research & Prototyping

* Reverse engineer vanilla map systems.
* Dynamic texture rendering.
* Terrain generation experiments.
* Resolution scaling tests.

Status: **Completed**

---

### Phase 2 — Custom Map System

* Create BetterFilledMapItem.
* Create BetterEmptyMapItem.
* Create MapState.
* Create MapManager.
* Create MapStorage.
* Create MapRenderer.
* Remove dependency on vanilla map internals.

Status: **Completed**

---

### Phase 3 — Rendering & Synchronization

* In-hand map rendering.
* Incremental updates.
* Region-based synchronization.
* Multiplayer testing.

Status: **In Progress**

---

### Phase 4 — Optimization

* Background map generation.
* Texture update optimization.
* Cache improvements.
* Large-map performance testing.

Status: **Planned**

---

### Phase 5 — Release Preparation

* Configuration options.
* Documentation.
* Public testing.
* Initial release.

Status: **Planned**

---

## License

MIT License
