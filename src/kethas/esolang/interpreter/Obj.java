package kethas.esolang.interpreter;

/**
 * This class represents a runtime object used by kethas.esolang.interpreter.Interpreter
 * @author Kethas
 */
public class Obj {

    /**
     * The null value Obj. Always use this instead of new Obj(null) in order to conserve allocations.
     */
    public static final Obj NULL = new Obj(null);

    private Object value;

    private boolean constant = false;

    private boolean reference = false;

    public Obj(Object value) {
        this.value = value;
    }

    public Obj(Object value, boolean constant) {
        this(value);
        this.constant = constant;
    }

    /**
     * @return The underlying value of this Obj.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Set the Obj's value to a new value. Does nothing if this Obj is constant.
     * @param value The new value.
     */
    public void setValue(Object value) {
        if (isConstant())
            return; //except when exceptions are implemented
        this.value = value;
    }


    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Add two Objs. If either is a string, it will concatenate their stringified values. If they are both integers then
     * it will add them. Otherwise does nothing and returns Obj.NULL.
     * @return The result of the operation.
     */
    public Obj add(Obj with) {
        if (value instanceof String || with.getValue() instanceof String) {
            return new Obj(value.toString() + with.getValue().toString());
        } else if (value instanceof Integer && with.getValue() instanceof Integer) {
            return new Obj((Integer) value + (Integer) with.getValue());
        }
        return NULL;
    }

    /**
     * Subtract two Objs. If both are integers, it will subtract them. Otherwise does nothing and returns Obj.NULL.
     * @return The result of the operation.
     */
    public Obj subtract(Obj with) {
        if (value instanceof Integer && with.value instanceof Integer) {
            return new Obj((Integer) value - (Integer) with.value);
        }
        return NULL;
    }

    /**
     * Multiply two Objs. If both are integers, it will multiply them. If one is a string and the other is an integer,
     * it will multiply the string python-style (the string will be reduplicated as many times as the integer.)
     * Otherwise does nothing and returns Obj.NULL.
     * @return The result of the operation.
     */
    public Obj multiply(Obj with) {
        if (value instanceof Integer && with.value instanceof Integer) {
            return new Obj((Integer) value * (Integer) with.value);
        } else if (value instanceof String && with.value instanceof Integer) {
            String s = "";
            for (int i = 0; i < (Integer) with.value; i++) {
                s += value;
            }
            return new Obj(s);
        } else if (value instanceof Integer && with.value instanceof String) {
            String s = "";
            for (int i = 0; i < (Integer) value; i++) {
                s += with.value;
            }
            return new Obj(s);
        }
        return NULL;
    }

    /**
     * Multiply two Objs. If both are integers, it will divide them.
     * Otherwise does nothing and returns Obj.NULL.
     * @return The result of the operation.
     */
    public Obj divide(Obj with) {
        if (value instanceof Integer && with.value instanceof Integer) {
            return new Obj((Integer) value / (Integer) with.value);
        }
        return NULL;
    }

    /**
     * Discerns whether this Obj's value is truthy. `null` and 0 are always false, as well as the empty string,
     * or a string of "false". Functions and everything else are true.
     * @return
     */
    public boolean isTruthy() {
        if (value == null) {
            return false;
        } else if (value instanceof Integer) {
            return (int) value != 0;
        } else if (value instanceof String) {
            return !((String) value).isEmpty() && !value.equals("false");
        } else if (value instanceof Function || value instanceof ExternalFunction) {
            return true;
        }

        return false;
    }

    /**
     * @return Whether this Obj is constant or not.
     */
    public boolean isConstant() {
        return constant;
    }

    /**
     * Set whether this Obj is constant or not.
     * @param constant The new setting.
     */
    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    /**
     * @return Whether this Obj is treated by-reference in function arguments.
     */
    public boolean isReference() {
        return reference;
    }

    /**
     * Set whether this Obj is treated by-reference in function arguments.
     * @param reference The new setting.
     */
    public void setReference(boolean reference) {
        this.reference = reference;
    }
}
