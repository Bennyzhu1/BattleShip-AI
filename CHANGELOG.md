PA03 to PA04 Changes
- Added a `BetterAiPlayer` to play against the server in the model (separate from `AiPlayer`) (this may not be a PA03 to PA04 change since it's an extension but listed just in case).
- `AbstractPlayer` had a two new fields added to assist with making a better "AI" player.
- `Coord` record obtained JSON property annotations for serialization (inevitable change)
- Driver adjusted to also run the local vs. server game (as required).