package com.github.ShinChven.lib.CommonLib.utils;

/**
 * Created by ShinChven on 15/4/30.
 * 结构工具
 * 请查看 demo 方法
 */
public class ValidateHandler {

    /**
     * demo，不要使用这里的代码
     */
    public static void demo() {
        new ValidateHandler(yourValidateMethod(), new Runnable() {
            @Override
            public void run() {
                // todo something after validation returns true
            }
        }).run();
    }

    /**
     * demo method，不要使用这里的方法
     * @return
     */
    private static boolean yourValidateMethod() {
        // todo validate here
        return false;
    }

    protected Runnable onGoing;
    protected boolean validate;

    /**
     *
     * @param validation 传入完全人验证，建议传入返回BOOLEAN 值的方法
     * @param onGoing
     */
    public ValidateHandler(boolean validation, Runnable onGoing) {
        this.onGoing = onGoing;
        this.validate = validation;
    }

    public Runnable getOnGoing() {
        return onGoing;
    }

    public void setOnGoing(Runnable onGoing) {
        this.onGoing = onGoing;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public void run() {

        if (!validate) {
            return;
        }

        onGoing.run();

    }

}
