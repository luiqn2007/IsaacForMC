package lq2007.mcmod.isaacmod.common.entity.friend.manager;

import lq2007.mcmod.isaacmod.common.util.serializer.ISerializer;
import lq2007.mcmod.isaacmod.common.util.serializer.Serializer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Serializer(FriendType.Serializer.class)
public abstract class FriendType {

    public static Map<String, FriendType> TYPES = new HashMap<>();

    public static FriendType get(String name) {
        return TYPES.getOrDefault(name, NONE);
    }

    public static FriendType getOrDefault(String name, FriendType defaultValue) {
        return TYPES.getOrDefault(name, defaultValue);
    }

    public static FriendType create(String name, Supplier<IFriendManager> supplier) {
        return new FriendType(name) {
            @Override
            public IFriendManager newInstance() {
                return supplier.get();
            }
        };
    }

    protected String name;

    public FriendType(String name) {
        this.name = name;
        TYPES.put(name, this);
    }

    public String getName() {
        return name;
    }

    public abstract IFriendManager newInstance();

    /**
     * empty. use for null-safe.
     */
    public static final FriendType NONE = create("none", () -> IFriendManager.EMPTY);

    /**
     * friends following the owner
     */
    public static final FriendType FOLLOWING = create("following", FollowingFriend::new);

    /**
     * friends around the owner
     */
    public static final FriendType SURROUND = create("surround", SurroundFriend::new);

    /**
     * use custom path
     */
    public static final FriendType FREEDOM = create("freedom", FreedomFriend::new);

    /**
     * blue fly, white fly, etc...
     */
    public static final FriendType FLY = create("fly", FlyFriend::new);

    public static class Serializer implements ISerializer<FriendType> {

        @Override
        public FriendType read(CompoundNBT nbt, String key) {
            return get(nbt.getString(key));
        }

        @Override
        public CompoundNBT write(CompoundNBT nbt, String key, FriendType item) {
            nbt.putString(key, item.name);
            return nbt;
        }

        @Override
        public FriendType read(PacketBuffer buffer) {
            return get(buffer.readString());
        }

        @Override
        public PacketBuffer write(FriendType item, PacketBuffer buffer) {
            return buffer.writeString(item.name);
        }
    }
}
