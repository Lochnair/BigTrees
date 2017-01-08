package karob.bigtrees;

import karob.bigtrees.compat.BlockPos;
import karob.bigtrees.generators.TreeWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Constants.ModId, name = Constants.ModName, version = Constants.ModVersion)
public class BigTrees {

	@Instance(Constants.ModId)
	public static BigTrees instance;
	
	private final TreeWorldGenerator treeWorldGenerator = new TreeWorldGenerator();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;

		
		KTreeCfg.init(event.getModConfigurationDirectory());
		
		GameRegistry.registerWorldGenerator(treeWorldGenerator, 1);
		
		if (KTreeCfg.disableVanillaTrees) {
			MinecraftForge.EVENT_BUS.register(this);
			MinecraftForge.TERRAIN_GEN_BUS.register(this);
		}
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {

	}

	// moved most of decoration to a new file
	@SubscribeEvent
	public boolean decorate(DecorateBiomeEvent.Decorate evt) {
		if (evt.getType() == DecorateBiomeEvent.Decorate.EventType.TREE) {
			evt.setResult(Result.DENY);
			return true;
		}

		return false;
	}
	
	private BlockPos getBlockPos(DecorateBiomeEvent evt) {
		return new BlockPos(evt.getPos());
	}

}
