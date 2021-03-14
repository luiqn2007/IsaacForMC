package lq2007.mcmod.isaacformc.register;

public interface IAutoApply {
    
    default int getPriority() {
        return 0;
    }
}
