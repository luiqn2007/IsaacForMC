package lq2007.mcmod.isaacmod.register;

public interface IAutoApply {
    
    default int getPriority() {
        return 0;
    }
}
