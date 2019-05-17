# Hero_of_Mythhaven

William Ritchie 815829203
Kristi Werry 823386935

Description:  This is a 2D side scrolling android rpg game. Embark on quests, defeat monsters, aquire gold, and level up.
Use gold to unlock cool new character skins. Can you beat the final level?

There are no special instructions required to run the game.  The game will be locked in landscape orientation. No 3rd party libraries are 
needed. 

List of known issue:
 - We could not finish every thing we wanted:
	- Did not save the character or game state when the app is closed
	- Did not implement Monster AI
	- Did implement projectiles
	- Did not implement actual use of gold to purchase character skins
	- Did not implement moving obstacles
	- Did not implement moving monster animation
	- Did not implement an HP bar, instead its a number
 - Known Bugs:
	- If you get hit by a monster and then press jump you will fall through the floor
	- In some instances when jumping you randomly will fall through the floor
	- You can instantly die from monsters if you fall on top of them
	- Sometimes when colliding with terrain you "phase" which for a brief time looks like their are multiple of the character
	- when the player loses you will notice the hp likely stops at one

 - Final comments:
	- We spent a surprising amount of time handling game bugs, which took away from development time of other features
	- We attempted to write well designed code but we ran out of time and favored functionality over well written code (sorry but youre going to see alot of booleans)
	- Given a few more days we would have likely finished the remaining features and definitely refactored our code 
