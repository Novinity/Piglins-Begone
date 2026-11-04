package dev.novinity.piglins_begone;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Piglins_begone.MODID)
public class Piglins_begone {

    public static final String MODID = "piglins_begone";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Piglins_begone() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onFinalizeSpawn(MobSpawnEvent.FinalizeSpawn event) {
        if (event.getSpawnType() != MobSpawnType.STRUCTURE || (event.getEntity().getType() != EntityType.ZOMBIFIED_PIGLIN && event.getEntity().getType() != EntityType.ZOGLIN)) return;

        ServerLevel level = event.getLevel().getLevel();
        if (level.dimension() != Level.OVERWORLD) return;

        BlockPos pos = BlockPos.containing(event.getX(), event.getY(), event.getZ());
        if (!level.getBlockState(pos).is(Blocks.NETHER_PORTAL)) return;

        event.setSpawnCancelled(true);
    }
}
