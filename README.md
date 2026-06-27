# The Land of Ice and Fire

## Introduction
Welcome to **The Land of Ice and Fire**, a text-based adventure set in the world of Westeros. Winter has come, and the fate of the Seven Kingdoms rests in your hands. Choose your house, fight your way through four levels of increasingly powerful enemies, and face the Night King in a final battle for survival.

## How to Run

### Requirements
- Java JDK 17 or higher

### Running the Game

**Linux / WSL / macOS**
```bash
chmod +x run.sh
./run.sh
```

**Windows (Git Bash)**
```bash
bash run.sh
```

The script compiles all sources, syncs resources, and launches the game automatically. On systems without a display (e.g. WSL without WSLg), it falls back to console mode.

## Gameplay

### Choose Your House
Select one of three houses at the start, each with a distinct advantage:

| House | Bonus | Attack Style |
|-------|-------|-------------|
| **Targaryen** | — | Highest attack damage (fire-based) |
| **Lannister** | +5 Armor | Balanced attack |
| **Stark** | +15 HP | Cold-based attack |

### Battle System
- Combat is **turn-based** — you attack, then the enemy attacks.
- Each turn you choose to **Attack** or **Run** (running drops you back one level).
- There is a **12.5% miss chance** on both sides.
- Your **armor degrades** as you take damage below 85 HP, reducing its protective value over time.
- Armor reduces incoming damage by 30% of its current value.

### Enemies

| Level | Enemy | Location | Attack Damage |
|-------|-------|----------|---------------|
| 1 | **Wildling** | Haunted Forest | 6 – 10 |
| 2 | **Giant** | Fist of the First Men | 7 – 13 |
| 3 | **White Walker** | Frostfangs | 9 – 16 |
| 4 | **Night King** *(Boss)* | Land of Always Winter | 11 – 23 |

### Tavern (Before the Final Battle)
After defeating the White Walker, you visit a tavern to prepare for the Night King:

- **Rest** — Restores 40 HP
- **Repair Armor** — Restores 7 armor
- **Drink Strength Potion** — Adds 5–10 damage to all attacks permanently

## Project Structure

```
src/
├── main/
│   ├── charactermanager/   # Character classes, houses, enemies, factories
│   ├── game/               # Core game logic (BattleManager, GameBuilder, Tavern, etc.)
│   └── gui/                # Swing GUI (StartMenu, HouseSelection)
└── tests/                  # JUnit test suite
lib/                        # junit and hamcrest JARs
bin/                        # Compiled output
run.sh                      # Build & run script
```

## Testing
Unit tests cover the factory classes and core character mechanics. To run them manually:

```bash
javac -cp "lib/*" -d bin $(find src -name "*.java")
java -cp "bin;lib/*" org.junit.runner.JUnitCore tests.HouseFactoryTest tests.EnemyFactoryTest tests.HouseTest
```
*(Use `:` instead of `;` on Linux/macOS.)*

