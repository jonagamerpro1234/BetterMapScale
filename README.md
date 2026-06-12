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

* Dynamic map size system.
* Dynamic color array allocation.
* Terrain color generation.
* Client-server synchronization.
* Dynamic texture rendering.
* Functional 256×256 and 512×512 map prototypes.
* Rendering experiments beyond vanilla limits.

### In Progress

* Persistent storage for large maps.
* Optimized synchronization.
* Incremental map updates.
* Stable rendering pipeline.

### Planned

* Independent map storage system.
* Custom map renderer.
* Multiplayer optimization.
* Configurable map resolutions.

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

## Lessons Learned

One of the most important discoveries made during development is that Minecraft's map system was never designed to scale beyond 128×128 pixels.

While it is technically possible to patch individual components, doing so introduces increasing complexity and maintenance challenges.

This led to a new development direction focused on building a dedicated mapping system rather than continuously modifying vanilla behavior.

---

## Future Architecture

### Vanilla Maps

Vanilla maps will remain untouched and fully compatible with Minecraft's original implementation.

### Better Maps

A new custom map type will be introduced:

* Independent storage.
* Independent synchronization.
* Independent rendering.
* Dynamic resolutions.
* High-detail terrain representation.
* Future multiplayer support.

Planned components:

* BetterFilledMapItem
* BetterMapState
* BetterMapStorage
* BetterMapRenderer
* Custom networking layer

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
* Create BetterMapState.
* Create BetterMapStorage.
* Remove dependency on vanilla map internals.

Status: **In Progress**

---

### Phase 3 — Optimization

* Incremental updates.
* Region-based synchronization.
* Performance improvements.
* Multiplayer testing.

Status: **Planned**

---

### Phase 4 — Release Preparation

* Configuration options.
* Documentation.
* Public testing.
* Initial release.

Status: **Planned**

---

## Educational Purpose

This project was originally created as a learning and research exercise focused on understanding Minecraft's map mechanics and rendering behavior.

The objective is to explore how maps are generated, updated, synchronized, and displayed within the game, while experimenting with alternative approaches to map rendering and resolution scaling.

Throughout development, the project has served as a practical way to learn about:

* Client-server synchronization
* Rendering systems
* Data storage and serialization
* Texture generation
* Performance considerations
* Mod development with Fabric

The knowledge gained from this research is being used to design an independent high-resolution map system that can coexist with Minecraft's vanilla maps.

---

## License

MIT License
