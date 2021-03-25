package lq2007.mcmod.isaacmod.register;

import lq2007.mcmod.isaacmod.register.registers.IRegister;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Appoint {
    Class<? extends IRegister>[] value() default {};
}
