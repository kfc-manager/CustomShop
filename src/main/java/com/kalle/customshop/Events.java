package com.kalle.customshop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null) return;
        if (clicked.getItemMeta() == null) return; //quick fix
        if (!(event.getWhoClicked() instanceof Player)) return; //just to be sure
        if (clicked.isSimilar(GuiController.getPlaceholder())) {
            event.setCancelled(true);
            return;
        }
        for (Category category : Category.getCategories()) {
            if (clicked.isSimilar(category.getIcon())) {
                event.setCancelled(true); //so the player can't remove the item from the gui
                GuiController.offerGui(category, (Player) event.getWhoClicked());
                return;
            }
            for (Offer offer : category.getOffers()) {
                if (clicked.isSimilar(offer.getIcon())) {
                    event.setCancelled(true); //so the player can't remove the item from the gui
                    Player player = (Player) event.getWhoClicked();
                    player.closeInventory();
                    if (!offer.buyOffer(player)) return;
                    GuiController.offerGui(category, player);
                    return;
                }
            }
        }
    }
}
