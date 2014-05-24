package org.communitybridge.permissionhandlers;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PermissionHandlerTest
{
	private	String name = "someplugin";
	private String pluginVersion = "1.1";
	private Player player = mock(Player.class);
	private Plugin plugin = mock(Plugin.class);
	private PluginDescriptionFile description = new PluginDescriptionFile(name, pluginVersion, null);
	private final String playerName = "somePlayer";
	private final String groupOne = "groupOne";
	private final String groupTwo = "groupTwo";

	TestablePermissionHandler permissionHandler = new TestablePermissionHandler();

	@Before
	public void setUp()
	{
		permissionHandler = new TestablePermissionHandler();
	}

	@Test
	public void switchGroupCallsRemoveGroup()
	{
		permissionHandler.switchGroup(player, groupOne, groupTwo);
		assertEquals(playerName, permissionHandler.removePlayer);
		assertEquals(groupOne, permissionHandler.removeGroup);
	}

	@Test
	public void switchGroupCallsAddGroup()
	{
		permissionHandler.switchGroup(player, groupOne, groupTwo);
		assertEquals(playerName, permissionHandler.addPlayer);
		assertEquals(groupTwo, permissionHandler.addGroup);
	}

	@Test
	public void validateHandlerDoesNotThrowErrorWithValidPlugin()
	{
		when(plugin.isEnabled()).thenReturn(true);
		when(plugin.getDescription()).thenReturn(description);

		try
		{
			permissionHandler.validate(plugin, name, pluginVersion);
		}
		catch (Exception exception)
		{
			fail("Caught an exception with valid plugin: " + exception.getMessage());
		}
	}

	@Test
	public void validateHandlerDoesNotThrowErrorWithValidPluginNewerVersion()
	{
		when(plugin.isEnabled()).thenReturn(true);
		when(plugin.getDescription()).thenReturn(description);

		try
		{
			permissionHandler.validate(plugin, name, "1.0");
		}
		catch (Exception exception)
		{
			fail("Caught an exception with valid plugin: " + exception.getMessage());
		}
	}

	@Test
	public void validateHandlerDoesThrowErrorWithValidPluginOlderVersion()
	{
		when(plugin.isEnabled()).thenReturn(true);
		when(plugin.getDescription()).thenReturn(description);
		String version = "1.2";

		try
		{
			permissionHandler.validate(plugin, name, version);
			fail("Failed to throw an exception.");
		}
		catch (IllegalStateException exception)
		{
			assertEquals(name + permissionHandler.WRONG_VERSION + version, exception.getMessage());
		}
		catch (Exception exception)
		{
			fail("Threw incorrect exception: " + exception.getMessage());
		}
	}

	@Test
	public void validateHandlerDoesThrowErrorWithNullPlugin()
	{
		try
		{
			permissionHandler.validate(null, name, pluginVersion);
			fail("Failed to throw an exception.");
		}
		catch (IllegalStateException exception)
		{
			assertEquals(name + permissionHandler.NOT_FOUND, exception.getMessage());
		}
		catch (Exception exception)
		{
			fail("Threw incorrect exception: " + exception.getMessage());
		}
	}

	@Test
	public void validateHandlerDoesThrowErrorWithDisabledPlugin()
	{
		when(plugin.isEnabled()).thenReturn(false);

		String name = "someplugin";
		try
		{
			permissionHandler.validate(plugin, name, pluginVersion);
			fail("Failed to throw an exception.");
		}
		catch (IllegalStateException exception)
		{
			assertEquals(name + permissionHandler.NOT_ENABLED, exception.getMessage());
		}
		catch (Exception exception)
		{
			fail("Threw incorrect exception." + exception.getMessage());
		}
	}

	public class TestablePermissionHandler extends PermissionHandler
	{
		public String addPlayer;
		public String addGroup;
		public String removePlayer;
		public String removeGroup;

		@Override
		public boolean addToGroup(Player player, String groupName)
		{
			addPlayer = playerName;
			addGroup = groupName;
			return true;
		}

		@Override
		public boolean removeFromGroup(Player player, String groupName)
		{
			removePlayer = playerName;
			removeGroup = groupName;
			return true;
		}

		@Override
		public List<String> getGroups(Player player)
		{
			throw new UnsupportedOperationException("No implementation needed for tests.");
		}

		@Override
		public List<String> getGroupsPure(Player player)
		{
			throw new UnsupportedOperationException("No implementation needed for tests.");
		}

		@Override
		public String getPrimaryGroup(Player player)
		{
			throw new UnsupportedOperationException("No implementation needed for tests.");
		}

		@Override
		public boolean isMemberOfGroup(Player player, String groupName)
		{
			throw new UnsupportedOperationException("No implementation needed for tests.");
		}

		@Override
		public boolean isPrimaryGroup(Player player, String groupName)
		{
			throw new UnsupportedOperationException("No implementation needed for tests.");
		}

		@Override
		public boolean setPrimaryGroup(Player player, String groupName, String formerGroupName)
		{
			throw new UnsupportedOperationException("No implementation needed for tests.");
		}

		@Override
		public boolean supportsPrimaryGroups()
		{
			throw new UnsupportedOperationException("No implementation needed for tests.");
		}
	}
}