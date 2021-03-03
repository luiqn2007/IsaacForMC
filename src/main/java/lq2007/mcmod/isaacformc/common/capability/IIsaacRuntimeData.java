package lq2007.mcmod.isaacformc.common.capability;

public interface IIsaacRuntimeData {

    static IIsaacRuntimeData dummy() {
        return DummyData.INSTANCE;
    }

    class DummyData implements IIsaacRuntimeData {

        private static final DummyData INSTANCE = new DummyData();
    }
}
