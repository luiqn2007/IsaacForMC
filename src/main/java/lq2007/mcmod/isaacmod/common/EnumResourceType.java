package lq2007.mcmod.isaacmod.common;

public enum EnumResourceType {

    TEXTURE("textures", "png"),
    MODEL("models", "obj");

    public final String type, ext;

    EnumResourceType(String type, String ext) {
        this.type = type;
        this.ext = ext;
    }
}
