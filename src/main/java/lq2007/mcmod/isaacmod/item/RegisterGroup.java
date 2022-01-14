package lq2007.mcmod.isaacmod.item;

import lq2007.mcmod.isaacmod.util.StringHelper;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class RegisterGroup {

    public final Map<String, GroupEntry<?>> entries = new HashMap<>();

    public StaticEntry registerStatic(String name) {
        StaticEntry entry = new StaticEntry(name);
        entries.put(name, entry);
        return entry;
    }

    public DynamicEntry registerDynamic(String name) {
        DynamicEntry entry = new DynamicEntry(name);
        entries.put(name, entry);
        return entry;
    }

    public CycleEntry registerCycle(String name, int keepTick) {
        CycleEntry entry = new CycleEntry(name, Math.max(1, keepTick));
        entries.put(name, entry);
        return entry;
    }

    public static abstract class GroupEntry<T extends GroupEntry<T>> extends ItemGroup {
        String en, zh;
        String name, uName;

        public GroupEntry(String name) {
            super(name);
            this.name = name;
            this.uName = StringHelper.toUCamelCase(name);
            this.en = uName;
            this.zh = uName;
        }

        public String getName() {
            return name;
        }

        public T lang(String en, String zh) {
            this.en = en;
            this.zh = zh;
            return (T) this;
        }

        public String getLanguageEn() {
            return en;
        }

        public String getLanguageZh() {
            return zh;
        }
    }

    public static class StaticEntry extends GroupEntry<StaticEntry> {
        Supplier<ItemStack> sup = () -> ItemStack.EMPTY;
        ItemStack iconStack = ItemStack.EMPTY;

        public StaticEntry(String name) {
            super(name);
            this.name = name;
        }

        public StaticEntry icon(Supplier<? extends IItemProvider> sup) {
            this.sup = () -> new ItemStack(sup.get());
            return this;
        }

        public StaticEntry iconItem(String iconName) {
            sup = () -> new ItemStack(ModItems.REGISTER.get(iconName));
            return this;
        }

        public StaticEntry iconStack(Supplier<ItemStack> sup) {
            this.sup = sup;
            return this;
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return sup.get();
        }
    }

    public static class DynamicEntry extends GroupEntry<DynamicEntry> {

        public List<IconFrame> frames = new ArrayList<>();
        private long lastTime = -1;
        private int tick = 0;
        private int framePtr = 0;

        public DynamicEntry(String name) {
            super(name);
        }

        public DynamicEntry addFrame(Supplier<? extends IItemProvider> sup, int keepTick) {
            frames.add(new IconFrame(() -> new ItemStack(sup.get()), keepTick));
            return this;
        }

        public DynamicEntry addFrame(String iconName, int keepTick) {
            frames.add(new IconFrame(() -> new ItemStack(ModItems.REGISTER.get(iconName)), keepTick));
            return this;
        }

        public DynamicEntry addStackFrame(Supplier<ItemStack> sup, int keepTick) {
            frames.add(new IconFrame(sup, keepTick));
            return this;
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return frames.isEmpty() ? ItemStack.EMPTY : frames.get(0).getIcon();
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack getIcon() {
            if (frames.isEmpty()) {
                return ItemStack.EMPTY;
            }
            if (frames.size() == 1) {
                return frames.get(0).getIcon();
            }
            World world = net.minecraft.client.Minecraft.getInstance().world;
            if (world == null) {
                return ItemStack.EMPTY;
            }
            IconFrame frame;
            if (lastTime < 0) {
                frame = frames.get(0);
                lastTime = world.getGameTime();
                tick = frame.tick;
                framePtr = 0;
            } else {
                long currentTime = world.getGameTime();
                tick -= (int) (currentTime - lastTime);
                lastTime = currentTime;
                if (tick <= 0) {
                    framePtr = (framePtr + 1) % frames.size();
                    frame = frames.get(framePtr);
                    tick = frame.tick;
                } else {
                    frame = frames.get(framePtr);
                }
            }
            return frame.getIcon();
        }
    }

    public static class CycleEntry extends GroupEntry<CycleEntry> {

        private NonNullList<ItemStack> items = NonNullList.create();
        private long lastTime = -1;
        private int tick = 0;
        private int frame = 0;
        private final int keepTick;

        public CycleEntry(String name, int tick) {
            super(name);
            this.keepTick = tick;
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            if (items.size() == 0) {
                return ItemStack.EMPTY;
            }
            return items.get(0);
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack getIcon() {
            if (items.size() == 0) {
                return ItemStack.EMPTY;
            }
            if (items.size() == 1) {
                return items.get(0);
            }
            World world = net.minecraft.client.Minecraft.getInstance().world;
            if (world == null) {
                return ItemStack.EMPTY;
            }
            if (lastTime < 0) {
                frame = 0;
                lastTime = world.getGameTime();
                tick = keepTick;
            } else {
                tick -= (int) (world.getGameTime() - lastTime);
                if (tick <= 0) {
                    frame++;
                    tick = keepTick;
                }
            }
            frame = frame % items.size();
            return items.get(frame);
        }

        @Override
        public void fill(NonNullList<ItemStack> items) {
            super.fill(items);
            this.items = items;
        }
    }

    public static class IconFrame {
        ItemStack stack = null;
        Supplier<ItemStack> icon;
        int tick;

        public IconFrame(Supplier<ItemStack> icon, int tick) {
            this.icon = icon;
            this.tick = tick;
        }

        public ItemStack getIcon() {
            if (stack == null) {
                stack = icon.get();
            }
            return stack;
        }
    }
}
