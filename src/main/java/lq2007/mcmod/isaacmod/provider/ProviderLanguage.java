package lq2007.mcmod.isaacmod.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lq2007.mcmod.isaacmod.Isaac;
import lq2007.mcmod.isaacmod.block.ModBlocks;
import lq2007.mcmod.isaacmod.item.ModGroups;
import lq2007.mcmod.isaacmod.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Supplier;

public class ProviderLanguage implements IDataProvider {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final Map<String, String> dataZh = new TreeMap<>();
    private final Map<String, String> dataEn = new TreeMap<>();
    private final DataGenerator gen;

    public ProviderLanguage(DataGenerator gen) {
        this.gen = gen;
    }

    private void addTranslations() {
        ModGroups.REGISTER.entries.values().forEach(entry -> addGroup(entry, entry.getLanguageZh(), entry.getLanguageEn()));
        ModBlocks.REGISTER.entries.values().forEach(entry -> addBlock(entry, entry.getLanguageZh(), entry.getLanguageEn()));
        ModItems.REGISTER.entries.values().forEach(entry -> addItem(entry.getObject(), entry.getLanguageZh(), entry.getLanguageEn()));
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        addTranslations();
        if (!dataZh.isEmpty())
            save(cache, dataZh, this.gen.getOutputFolder().resolve("assets/" + Isaac.ID + "/lang/zh_cn.json"));
        if (!dataEn.isEmpty())
            save(cache, dataEn, this.gen.getOutputFolder().resolve("assets/" + Isaac.ID + "/lang/en_us.json"));
    }

    @Override
    public String getName() {
        return "Languages: zh_cn and en_us";
    }

    private void save(DirectoryCache cache, Object object, Path target) throws IOException {
        String data = GSON.toJson(object);
        data = JavaUnicodeEscaper.outsideOf(0, 0x7f).translate(data); // Escape unicode after the fact so that it's not double escaped by GSON
        String hash = IDataProvider.HASH_FUNCTION.hashUnencodedChars(data).toString();
        if (!Objects.equals(cache.getPreviousHash(target), hash) || !Files.exists(target)) {
            Files.createDirectories(target.getParent());
            try (BufferedWriter bufferedwriter = Files.newBufferedWriter(target)) {
                bufferedwriter.write(data);
            }
        }

        cache.recordHash(target, hash);
    }

    public void addBlock(Supplier<? extends Block> key, String nameZh, String nameEn) {
        add(key.get(), nameZh, nameEn);
    }

    public void add(Block key, String nameZh, String nameEn) {
        add(key.getTranslationKey(), nameZh, nameEn);
    }

    public void addItem(Supplier<? extends Item> key, String nameZh, String nameEn) {
        add(key.get(), nameZh, nameEn);
    }

    public void add(Item key, String nameZh, String nameEn) {
        add(key.getTranslationKey(), nameZh, nameEn);
    }

    public void addEntityType(Supplier<? extends EntityType<?>> key, String nameZh, String nameEn) {
        add(key.get(), nameZh, nameEn);
    }

    public void add(EntityType<?> key, String nameZh, String nameEn) {
        add(key.getTranslationKey(), nameZh, nameEn);
    }

    public void addGroup(ItemGroup group, String nameZh, String nameEn) {
        add(((TranslationTextComponent) group.getGroupName()).getKey(), nameZh, nameEn);
    }

    public void add(String key, String nameZh, String nameEn) {
        if (dataZh.put(key, nameZh) != null)
            throw new IllegalStateException("Duplicate translation key " + key + " in zh_cn");
        if (dataEn.put(key, nameEn) != null)
            throw new IllegalStateException("Duplicate translation key " + key + " in en_us");
    }
}
