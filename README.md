# CustomShop

This plugin makes use of the [VaultAPI](https://github.com/MilkBowl/VaultAPI) and lets you create your own custom shop. Via Vault it connects to your economy plugin and makes the offers purchasable with the currency of the economy. The shop is customizable by commands. Within the shop categories and offers can be added. The shop can then be accessed by every player with the command "/cshop". The plugin is written with the [spigot-1.8.8 api](https://getbukkit.org/get/hNiHm0tuqAg1Xg7w7zudk63uHr0xo48D) and is also only tested for Minecraft version 1.8.8.

## Installation

The [compiled .jar file](https://www.spigotmc.org/resources/customshop.109627/download?version=495341) can be dragged in the plugin folder of your server and after reloading the server the console should say that the plugin has been enabled. Make sure that you installed a plugin that implements an economy (for example [EssentialsX](https://essentialsx.net/downloads.html)), a chat plugin (for example [EssentialsChat](https://essentialsx.net/downloads.html)) and of course [Vault](https://www.spigotmc.org/resources/vault.34315/), without it the plugin won't work and disable itself! The plugin creates a config.yml file in its data folder where the data (categories and offers) are stored. I highly recommend that you only work with the commands and **not** make changes to the config manually as it could corrupt the file and break the plugin.

## Commands

**/ccategory**

- with this command a category can be created

- up to 54 categories can be added

- the command requires exactly two arguments

- first argument: name of the category (must be unique)

- second argument: material of the icon (case sensitive and [these](https://helpch.at/docs/1.8/org/bukkit/Material.html) are available)

- the command can only be executed with operator rights

**/dcategory**

- with this command a category can be deleted (also deletes all its offers)

- the command requires exactly one argument

- first argument: name of the category (category must exist and name is also case sensitive)

- the command can only be executed with operator rights

**/coffer**

- with this command an offer can be created

- up to 54 offers can be created per category

- the command requires exactly four arguments

- frist argument: name of the offer (must be unique, also among categories)

- second argument: material of the item that is being offered (case sensitive and [these](https://helpch.at/docs/1.8/org/bukkit/Material.html) are available)

- third argument: price of the offer (can't be lower than 0)

- fourth argument: name of its category (case sensitive)

- the command can only be executed with operator rights

**/doffer**

- with this command an offer can be deleted

- the command requires exactly one argument

- first argument: name of the offer (case sensitive)

- the command can only be executed with operator rights

**/cshop**

- with this command a GUI of the shop gets opened

- every player has access to this command

- can't be executed by the server console
