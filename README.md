# The Land of Ice and Fire

> A story-driven, turn-based RPG set in the world of Westeros. Choose your house, survive four levels of escalating enemies, and face the Night King in a final battle for the realm.

Built with **Java 17+** and **JavaFX**.

---

## Getting Started

**Requirements:** Java JDK 17 or higher

| Platform | Command |
|----------|---------|
| Windows | `run.bat` |
| Linux / macOS / Git Bash | `bash run.sh` |
| WSL (no display) | `bash run.sh --console` |

The script compiles all sources, syncs assets, and launches the game. On systems without a display it falls back to console mode automatically.

---

## Houses

| House | HP | Armor | Attack | Special |
|-------|----|-------|--------|---------|
| **Targaryen** | 100 | 13 | 18–25 | DRAGONFIRE — 2× damage, no miss |
| **Lannister** | 100 | 18 | 15–21 | IRON BANK — attack + 50% lifesteal |
| **Stark** | 125 | 13 | 16–22 | PACK HUNT — two independent strikes |

---

## Enemies

| Level | Enemy | HP | Attack | Location |
|-------|-------|----|--------|----------|
| 1 | Wildling | 80 | 6–10 | Haunted Forest |
| 2 | Giant | 130 | 7–13 | Fist of the First Men |
| 3 | White Walker | 165 | 9–16 | Frostfangs |
| 4 | **Night King** | 220 | 11–23 | Land of Always Winter |

---

## Combat

Each turn, choose one action:

| Action | Effect |
|--------|--------|
| **Attack** | Deal damage. 12.5% miss chance and 15% crit chance (2× dmg) on both sides. |
| **Block** | Fully negate the next enemy hit. Costs 2 armor per use. |
| **Special** | Trigger your house ability. 3-turn cooldown. |
| **Run** | Retreat one level and face the same enemy again. |

Armor passively reduces all incoming damage by 30% of its current value. The Block button is disabled when armor reaches 0.

---

## Story

The game is fully story-driven. Every screen has house-specific dialogue.

```
Start Menu
 └─ House Selection → Character Intro (house backstory + opening quote)
     └─ Location Screen → Battle → Victory Narrative (story beat between fights)
         └─ Location Screen → Battle → Victory Narrative
             └─ Location Screen → Battle → Victory Narrative
                 └─ Tavern (REST / REPAIR ARMOR / STRENGTH POTION)
                     └─ Night KingB Cutscene (the Night King speaks; you respond)
                         └─ Final Battle → End Screen
```

---

## Project Structure

```
src/
├── main/
│   ├── charactermanager/   Character model — houses, enemies, factories, SpecialResult
│   ├── game/               Console-mode game loop, BattleManager, utilities
│   └── gui/                JavaFX screens, AudioManager, Fonts
└── tests/                  JUnit unit tests

docs/                       Developer documentation
lib/                        JavaFX, JUnit, Hamcrest JARs
src/main/assets/
├── fonts/                  Game of Thrones .ttf
├── img/                    Backgrounds, house and enemy sprites
└── music/                  gotsoundtrack.weba, battle.m4a
```

---

## Testing

```bat
run.bat

java --module-path lib\javafx --add-modules javafx.controls,javafx.graphics,javafx.media ^
  -cp "bin;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" ^
  org.junit.runner.JUnitCore tests.HouseFactoryTest tests.EnemyFactoryTest tests.HouseTest
```

---

## Contributing

See [`docs/extending.md`](docs/extending.md) for guides on adding houses, enemies, screens, and audio.

- [`docs/architecture.md`](docs/architecture.md) — package breakdown and design patterns
- [`docs/game-flow.md`](docs/game-flow.md) — full screen sequence and all combat values
- [`docs/extending.md`](docs/extending.md) — how to add new content

---

## Roadmap

- Additional houses (Baratheon, Tyrell, Martell)
- Sound effects for attacks and specials
- Animated combat sprites
- Player level and progression system
- Location-specific background art

---

## License

MIT — see [LICENSE.md](LICENSE.md)

## Authors

KS Manejo
