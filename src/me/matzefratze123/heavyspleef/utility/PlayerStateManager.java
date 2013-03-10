/**
 *   HeavySpleef - The simple spleef plugin for bukkit
 *   
 *   Copyright (C) 2013 matzefratze123
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package me.matzefratze123.heavyspleef.utility;

import java.util.HashMap;
import java.util.Map;

import me.matzefratze123.heavyspleef.core.GameCuboid;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PlayerStateManager {

	public static Map<String, PlayerState> states = new HashMap<String, PlayerState>();
	
	@SuppressWarnings("deprecation")
	public static void savePlayerState(Player p) {
		states.put(p.getName(), new PlayerState(p.getInventory().getContents(), p.getInventory().getHelmet(), p.getInventory().getChestplate(),
				  p.getInventory().getLeggings(), p.getInventory().getBoots(),p.getExhaustion(), p.getSaturation(),
				  p.getFoodLevel(), p.getHealth(),p.getGameMode(), p.getActivePotionEffects(), p.getExp(), p.getLevel()));

		p.getInventory().clear();
		p.getInventory().setArmorContents(new ItemStack[] {null, null, null, null});
		p.setLevel(0);
		p.setExp(0);
		p.setGameMode(GameMode.SURVIVAL);
		p.setFireTicks(0);
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setAllowFlight(false);
		
		for (PotionEffect effect : p.getActivePotionEffects()) {
			p.removePotionEffect(effect.getType());
		}
		
		p.updateInventory();
	}
	
	@SuppressWarnings("deprecation")
	public static void restorePlayerState(Player p) {
		PlayerState state = states.get(p.getName());
		if (state == null) {
			p.sendMessage(GameCuboid._("errorOnState"));
			return;
		}
		
		p.getInventory().clear();
		p.getInventory().setContents(state.getContents());
		p.getInventory().setHelmet(state.getHelmet());
		p.getInventory().setChestplate(state.getChestplate());
		p.getInventory().setLeggings(state.getLeggings());
		p.getInventory().setBoots(state.getBoots());
		p.setLevel(state.getLevel());
		p.setExp(state.getExp());
		p.setGameMode(state.getGm());
		p.setHealth(state.getHealth());
		p.setFoodLevel(state.getFoodLevel());
		p.addPotionEffects(state.getPotioneffects());
		p.setExhaustion(state.getExhaustion());
		p.setSaturation(state.getSaturation());
		p.sendMessage(GameCuboid._("stateRestored"));
		p.updateInventory();
		states.remove(p.getName());
	}
	
	public static Map<String, PlayerState> getPlayerStates() {
		return states;
	}

}
