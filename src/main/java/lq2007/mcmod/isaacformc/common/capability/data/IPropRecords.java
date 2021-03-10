package lq2007.mcmod.isaacformc.common.capability.data;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public interface IPropRecords {

    boolean hasSecondaryProp();

    @Nullable
    PropRecord getMainActiveProp();

    @Nullable
    PropRecord getSecondaryActiveProp();

    ImmutableList<PropRecord> getPassiveRecords();

    ImmutableList<PropRecord> getActiveRecords();

    void iterPassiveProps(Consumer<PropRecord> records);

    void iterActiveProps(Consumer<PropRecord> records);
}
