package eyeq.whiteskating;

import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import eyeq.whiteskating.item.ItemArmorSkating;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.io.File;

import static eyeq.whiteskating.WhiteSkating.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class WhiteSkating {
    public static final String MOD_ID = "eyeq_whiteskating";

    @Mod.Instance(MOD_ID)
    public static WhiteSkating instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Item skatingBoots;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        skatingBoots = new ItemArmorSkating(0, EntityEquipmentSlot.FEET).setUnlocalizedName("skatingBoots");

        GameRegistry.register(skatingBoots, resource.createResourceLocation("skating_boots"));
    }

    public static void addRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(skatingBoots), "X X", "Y Y",
                'X', UOreDictionary.OREDICT_DIAMOND, 'Y', Items.SNOWBALL));
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(skatingBoots);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-WhiteSkating");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, skatingBoots, "Ice Skates");
        language.register(LanguageResourceManager.JA_JP, skatingBoots, "スケート靴");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, skatingBoots, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
    }
}
