package lq2007.mcmod.isaacformc.client.util.obj2.objmodel;

public interface IModelCustom {

    String getType();

    void renderAll();

    void renderOnly(String... as);

    void renderPart(String s);

    void renderAllExcept(String... as);

    default void renderWeapon() {
        renderPart("weapon");
    }
}
