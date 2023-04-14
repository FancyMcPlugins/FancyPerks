[![Generic badge](https://img.shields.io/badge/version-1.0.0_dev-green.svg)](https://shields.io/)

# Fancy Perks
A simple plugin that adds some fancy perks to your server.

**Only supported for 1.19.4** _(might work in other version too tho)_<br>
_Using [paper](https://papermc.io/downloads) is highly recommended_

## Get the plugin

You can download the latest versions at the following places:

- TODO: spigotmc url
- TODO: modrinth url
- https://github.com/FancyMcPlugins/FancyPerks/releases
- Build from source

## Commands

/perks - Opens the perks gui<br>
/perks activate (perk | *) - Activates the perk<br>
/perks deactivate (perk | *) - Deactivates the perk<br>
/fancyperks verison - _Shows the current version_<br>
/fancyperks reload - _Reloads the config_<br>


## Permissions

To use a perk - ``fancyperks.perk.(perk name)``<br>

## Build from source
1. Clone [FancyLib](https://github.com/FancyMcPlugins/FancyLib) repo and run `gradlew publishToMavenLocal`
2. Clone this repo and run `gradlew reobfJar`
3. The jar file will be in `build/libs/FancyPerks-<version>.jar`
