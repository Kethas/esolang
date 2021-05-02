package kethas.esolang.interpreter;

/**
 * This class represents a runtime object used by kethas.esolang.interpreter.Interpreter
 * @author Kethas
 */
public class Obj {

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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        if (isConstant())
            return; //except when exceptions are implemented
        this.value = value;
    }


    @Override
    public String toString() {
        return value.toString();
    }

    public Obj add(Obj with) {
        if (value instanceof String || with.getValue() instanceof String) {
            return new Obj(value.toString() + with.getValue().toString());
        } else if (value instanceof Integer && with.getValue() instanceof Integer) {
            return new Obj((Integer) value + (Integer) with.getValue());
        }
        return NULL;
    }

    public Obj subtract(Obj with) {
        if (value instanceof Integer && with.value instanceof Integer) {
            return new Obj((Integer) value - (Integer) with.value);
        }
        return NULL;
    }

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

    public Obj divide(Obj with) {
        if (value instanceof Integer && with.value instanceof Integer) {
            return new Obj((Integer) value / (Integer) with.value);
        }
        return NULL;
    }

    public boolean isTruthy() {
        if (value == null) {
            return false;
        } else if (value instanceof Integer) {
            return (int) value != 0;
        } else if (value instanceof String) {
            return !((String) value).isEmpty() && !value.equals("false");
        } else if (value instanceof Function) {
            return true;
        }

        return false;
    }

    public boolean isConstant() {
        return constant;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    public boolean isReference() {
        return reference;
    }

    public void setReference(boolean reference) {
        this.reference = reference;
    }
}
