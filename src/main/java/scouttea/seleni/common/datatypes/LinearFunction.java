package scouttea.seleni.common.datatypes;

//UNUSED
public class LinearFunction {
    public static final LinearFunction CONSTANT_ONE = new LinearFunction();

    private final float scale;
    private final float constant;

    public LinearFunction() {
        this(0, 1);
    }

    public LinearFunction(float scale, float constant) {
        this.scale = scale;
        this.constant = constant;
    }

    public float getScale() {
        return scale;
    }

    public float getConstant() {
        return constant;
    }

    public float compute(float x) {
        return scale * x + constant;
    }
}
