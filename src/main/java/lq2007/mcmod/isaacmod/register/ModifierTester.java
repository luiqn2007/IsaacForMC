package lq2007.mcmod.isaacmod.register;

import java.lang.reflect.Modifier;

public class ModifierTester {

    public static ModifierTester notAbstract = new ModifierTester().ABSTRACT().not();
    public static ModifierTester instantiable = new ModifierTester().PUBLIC().and(notAbstract);

    private int mask = 0;
    private boolean not = false;
    private ModifierTester and = null;

    public ModifierTester PUBLIC() {
        mask |= Modifier.PUBLIC;
        return this;
    }

    public ModifierTester PRIVATE() {
        mask |= Modifier.PRIVATE;
        return this;
    }

    public ModifierTester PROTECTED() {
        mask |= Modifier.PROTECTED;
        return this;
    }

    public ModifierTester STATIC() {
        mask |= Modifier.STATIC;
        return this;
    }

    public ModifierTester FINAL() {
        mask |= Modifier.FINAL;
        return this;
    }

    public ModifierTester SYNCHRONIZED() {
        mask |= Modifier.SYNCHRONIZED;
        return this;
    }

    public ModifierTester VOLATILE() {
        mask |= Modifier.VOLATILE;
        return this;
    }

    public ModifierTester TRANSIENT() {
        mask |= Modifier.TRANSIENT;
        return this;
    }

    public ModifierTester NATIVE() {
        mask |= Modifier.NATIVE;
        return this;
    }

    public ModifierTester INTERFACE() {
        mask |= Modifier.INTERFACE;
        return this;
    }

    public ModifierTester ABSTRACT() {
        mask |= Modifier.ABSTRACT;
        return this;
    }

    public ModifierTester STRICT() {
        mask |= Modifier.STRICT;
        return this;
    }

    public ModifierTester not() {
        not = true;
        return this;
    }

    public ModifierTester and(ModifierTester tester) {
        and = tester;
        return this;
    }

    public boolean test(int modifiers) {
        boolean andResult = and == null || and.test(modifiers);
        return not != ((modifiers & mask) == mask && andResult);
    }

    public boolean test(Class<?> aClass) {
        return test(aClass.getModifiers());
    }
}
