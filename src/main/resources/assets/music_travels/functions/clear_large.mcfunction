summon minecraft:armor_stand ~12 ~12 ~12 {ShowArms:1b,Invisible:1b,Marker:1b}
summon minecraft:armor_stand ~12 ~-12 ~12 {ShowArms:1b,Invisible:1b,Marker:1b}
summon minecraft:armor_stand ~-12 ~12 ~12 {ShowArms:1b,Invisible:1b,Marker:1b}
summon minecraft:armor_stand ~-12 ~-12 ~12 {ShowArms:1b,Invisible:1b,Marker:1b}
summon minecraft:armor_stand ~12 ~12 ~-12 {ShowArms:1b,Invisible:1b,Marker:1b}
summon minecraft:armor_stand ~12 ~-12 ~-12 {ShowArms:1b,Invisible:1b,Marker:1b}
summon minecraft:armor_stand ~-12 ~12 ~-12 {ShowArms:1b,Invisible:1b,Marker:1b}
summon minecraft:armor_stand ~-12 ~-12 ~-12 {ShowArms:1b,Invisible:1b,Marker:1b}
execute at @e[type=armor_stand] run function scan:clear
function scan:clear
kill @e[distance=..64,type=armor_stand]