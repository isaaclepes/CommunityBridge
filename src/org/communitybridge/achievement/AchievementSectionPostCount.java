/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.communitybridge.achievement;

import org.bukkit.entity.Player;

/**
 *
 * @author Iain E. Davis <iain@ruhlendavis.org>
 */
public class AchievementSectionPostCount extends AchievementPostCount
{
	private String sectionID;
	
	@Override
	public boolean playerQualifies(Player player)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public String getSectionID()
	{
		return sectionID;
	}

	public void setSectionID(String sectionName)
	{
		this.sectionID = sectionName;
	}

}