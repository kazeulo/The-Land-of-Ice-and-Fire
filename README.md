# The Land of Ice and Fire

## Introduction
Welcome to **The Land of Ice and Fire**, a thrilling text-based adventure set in the world of Westeros. The game takes place in a time when winter can last for decades, and the fate of the Seven Kingdoms hangs in the balance. As a player, you will embark on a perilous journey to battle formidable enemies and ultimately face the Night King.

## Gameplay
### Objective
Your mission is to progress through four challenging levels, each featuring increasingly powerful enemies. Your ultimate goal is to defeat the Night King and save Westeros from the coming darkness.

### Game Flow
1. **Choose Your House**: Select one of the three great houses, each with unique abilities:
   - **House Targaryen**: Gains additional attack damage.
   - **House Lannister**: Possesses superior armor.
   - **House Stark**: Starts with higher HP.

2. **Battle System**:
   - Combat follows a turn-based format.
   - You can choose to **attack** or **run**.
   - Coin toss mechanics add a chance of attack failure.
   - Enemies have varying strengths, requiring strategy to defeat them.

3. **Tavern (Before Final Battle)**:
   - Before facing the Night King, you can visit a tavern to prepare:
     - **Rest**: Restore HP.
     - **Repair Armor**: Strengthen your defense.
     - **Drink Strength Potion**: Enhance attack power.

## Characters & Enemies
### Houses
- **House Targaryen**: Known for their fiery spirit and ability to deal extra damage.
- **House Lannister**: Wealthy and powerful, possessing stronger armor.
- **House Stark**: Resilient and enduring, starting with higher HP.

### Enemies
You will face the following enemies across different locations:
- **Wildling** (Haunted Forest) – Attack Damage: 6-10
- **Giant** (Fist of the First Men) – Attack Damage: 7-13
- **White Walker** (Frostfangs) – Attack Damage: 8-16
- **Night King** (Land of Always Winter) – Attack Damage: 11-23

## How to Play
1. Run the game and follow the on-screen prompts.
2. Make decisions wisely—each choice affects your survival.
3. Progress through levels by defeating enemies.
4. Use the tavern strategically before facing the Night King.

## Installation & Running the Game
1. Clone or download the repository.
2. Ensure you have Java installed.
3. Compile and run the game using:
   ```bash
   javac -d ../out $(Get-ChildItem -Recurse -Filter *.java | Select-Object -ExpandProperty FullName)
   java java -cp ../out game.Main
   ```
