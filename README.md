# JGameEngine
##### By Aaron Carson

#### Quick Links:
* [Description](https://github.com/aaroncarsonart/JGameEngine/blob/update/README.md#1-description)
* [Demo](https://github.com/aaroncarsonart/JGameEngine/blob/update/README.md#2-demo)
* [Features](https://github.com/aaroncarsonart/JGameEngine/blob/update/README.md#3-features)
* [Controls](https://github.com/aaroncarsonart/JGameEngine/blob/update/README.md#4-controls)
* [Screenshots](https://github.com/aaroncarsonart/JGameEngine/blob/update/README.md#5-screenshots)

![Image of JGameEngine](https://raw.githubusercontent.com/aaroncarsonart/JGameEngine/update/demo/01.png)

_A screenshot demonstrating the procedurally generated map with lighting effects, the hero, and some collectable items, as well as the mini-map and the vital stat display._

### 1. Description

JGameEngine is a computer game that is inspired by 16-bit era rpgs and roguelikes.   This is currently in production in my free time.  Aside from any standard Java libraries being used, all code and art assets are written and developed by myself.

I was inspired to create a game with open-ended gameplay that relies heavily on procedurally generated content.  That way, no one ever gets bored.  Thus, many elements of the game will be randomized to ensure a unique experience very play through.

This game also allows me a space to express my many creative interests.  I will continue to use the pixel art and animation assets that I design using the open-source software [GIMP](http://www.gimp.org/).  I also write MIDI-based music with [Cakewalk Sonar](https://www.cakewalk.com/products/SONAR/) that I will eventually include.  However, the primary focus of this project is the serious coding challenge of how to design and implement a cross-platform computer game logic system from the ground up.

### 2. Demo

I have a mostly up-to-date demo in my repository, `demo/JGameEngine.jar`, as a runnable jar file.  At this time, is not available as a separate download.  You must clone my repository or [download it as a zip file](https://github.com/aaroncarsonart/JGameEngine/archive/update.zip) to play my demo.  Of course you can also open the Eclipse project and run, build or modify it yourself!  The main class is `main/JGameEngine.java`.

### 3. Features

So far, JGameEngine has some implemented features to enjoy, including:
* Navigate a procedurally generated map as an explorer in a fantasy setting.
* Manage your Hunger, Damage, Thirst, and Stress to avoid losing the game.
* Time only advances when you move.
* Collect items you encounter, adding them to your inventory.
* Manage your inventory by inspecting, sorting, or trashing your items.
* View a mini-map that shows your location and the location of items.
* Lighting effects hide or reveal parts of the map as you explore.
* Switch between fullscreen and windowed mode.

Features that are yet to be implemented:
* Explore multiple levels, delving deeper underground
* Encounter enemies you have to fight with combat
* Use items from your inventory for various effects
* Equip some items as armor or weapons, to increase your Stats.
* Placeholder menu items will be implemented

### 4. Controls
Command                   | Keys                      | Notes
--------------------------|---------------------------|-------------------------
movement, menu navigation | hjkl, aswd, arrow keys
action, select, confirm   | enter, space, x
rest                      | r                         | time passes, reducing damage but increasing hunger
run                       | z                         | hold while moving
toggle in-game time       | t
toggle minimap            | m
toggle vital stats        | v
open main menu            | esc        
to previous menu          | esc, z
inventory menu shortcut   | i
fullscreen                | ctrl + f, `fullscreen` from settings menu
quit game                 | ctrl + q, `quit` from main menu

### 5. Screenshots

Here are more screenshots demonstrating some of the game's Inventory features:

![Too many items ...](https://raw.githubusercontent.com/aaroncarsonart/JGameEngine/update/demo/04.png)

_Trying to collect that sword, but the player's inventory is full._


![A custom in-game menu.](https://raw.githubusercontent.com/aaroncarsonart/JGameEngine/update/demo/02.png)

_A view of the Main Menu.  `Inventory`, `Settings`, and `Quit` are functional._


![The Inventory screen. Use, order, sort, and trash your items here!](https://raw.githubusercontent.com/aaroncarsonart/JGameEngine/update/demo/03.png)

_A view of the Inventory screen.  `Use` an item removes it from the inventory, but it doesn't have an effect.  `Inspect` will give some detailed info about the item.  `Order` allows you to manually sort and order the items by switching their positions.  `Sort` will algorithmically sort the items alphabetically and stack like items to the maximum allowed amount.  `Trash` trashes the item, removing it from the Inventory._

