Behaviour Goals to Implement
===========================

**Format:**

* Goal Name (NMS Goal Type) -> Planned changes to be made + extra info

**Stuff to Note:**

* Events will need to be made for the majority of these to allow maximum control over entities
    * Wherever you see an event fits, put it there
    * Some of these events can possibly include params such as particle effects, sounds etc.
        * Might be a good idea to add customisation for this type of thing.
    * Would be nice to avoid doubling up on events that already exist in CraftBukkit too
        * Unless of course the custom event can add something new
* Completely new goals are marked in *italics*
* We will also need docs on exactly what each of these do
* Also have in mind that CraftBukkit may override some of the NMS pathfinder goals. Make sure to check both repos to see which one to use (NMS or CB). Only some are included/edited in CB

:)

**Goal Types:**

* TODO
    * Eight different goal types (some aren’t compatible with each other)
    * (0 - 7), as per below
* TODO -> More behaviours that are only implemented once

**Already Implemented**:

* ArrowAttack (3) -> Change to RangedAttack (similar to MeleeAttack)
* AvoidPlayer (1) -> Change to AvoidEntity class type
* Beg (2) -> Change to beg for specific item (NMS only activates for wolfs and bones)
* BreakDoor **(extends DoorInteract)**
* Breed (3) -> Add breeding event (this is a must!)
* DefendVillage (1) **(extends Target)**
* DoorInteract (0)
* EatTile (7) -> Change to EatGrass, cause apparently, this is all that this one achieves.
* FleeSun (1)
* Float (4)
* FollowParent (0) -> Treats the ‘parent’ as the closest adult entity of the same type
* *FollowExact (3) -> Similar to FollowParent, but follows a specific entity instance*
* FollowOwner (3) -> Change to FollowTamer
* HurtByTarget (1) **(extends GoalTarget)**
* Interact (3) **(extends LookAtPlayer)** -> Same functionality as LookAtPlayer, except it is compatible with different behaviours
* JumpOnBlock (5) -> Change to SitOnBlock to match “Sit” goal
* LeapAtTarget (5)
* LookAtPlayer (2) -> Change to LookAtNearestEntity, as this is what this class actually does
* LookAtTradingPlayer **(extends LookAtPlayer)**
* MakeLove (3) -> how cute
* MeleeAttack (3)
* MoveIndoors (1)
* MoveThroughVillage (1)
* MoveTowardsRestriction (1)
* NearestAttackableTarget (1) **(extends Target)** -> Change to MoveTowardsNearestAttackableTarget. Looks like we need to implement Entity Selector interfaces.
* OwnerHurtByTarget (1) **(extends Target)** -> Change to DefendTamer
* OwnerHurtTarget (1) **(extends Target)** -> Change to TamerHurtTarget
* Panic (1)
* Sit (5)
* Swell (1)
* TakeFlower (3) -> Not just Villagers should be able to take flowers
* Tame (1) -> Change to TameByRiding
* Target (0)
* Tempt (3)
* TradeWithPlayer (5)

**To be Implemented:**

* MoveTowardsTarget (1)
* *MoveTowardsLocation (1) -> Removed when it’s finished implementing (A way to do this needs to be added)*
* OcelotAttack (3)
* OfferFlower (3) -> Instead of just offering it to villagers, allow the iron golem to give it to all entity types (change constructor)
* OpenDoor **(extends DoorInteract)**
* PassengerCarrotStick (7) -> Change to FollowCarrotStick
* Play (1) -> wut. Didn’t know this existed. Change to VillagerPlay
* RandomLookaround (3) -> Change to LookAtRandom
* RandomStroll (1)
* RandomTargetNonTamed **(extends NearestAttackableTarget)**
* RestrictOpenDoor (0)
* RestrictSun (0)