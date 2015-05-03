package com.github.ShinChven.lib.CommonLib.tool;

/**
 * Created by ShinChven on 15/4/30.
 * 请查看 demo 方法
 */
public class ValidateHandler {

    public static void demo() {
        new ValidateHandler(yourValidateMethod(), new Runnable() {
            @Override
            public void run() {
                // todo something after validation returns true
            }
        }).run();
    }

    /**
     * demo method
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
     * @param validate
     * @param onGoing
     */
    public ValidateHandler(boolean validate, Runnable onGoing) {
        this.onGoing = onGoing;
        this.validate = validate;
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
