package kethas.esolang.interpreter;

/**
 * Created by Kethas on 14/04/2017.
 */
public class Obj {

    private final Object value;

    public Obj(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Obj add(Obj with) {
        if (value instanceof String) {
            return new Obj(value + with.toString());
        } else if (value instanceof Integer && with.value instanceof Integer) {
            return new Obj((Integer) value + (Integer) with.value);
        }
        return null;
    }

    public Obj subtract(Obj with) {
        if (value instanceof Integer && with.value instanceof Integer) {
            return new Obj((Integer) value - (Integer) with.value);
        }
        return null;
    }

    public Obj multiply(Obj with) {
        if (value instanceof Integer && with.value instanceof Integer) {
            return new Obj((Integer) value * (Integer) with.value);
        }
        return null;
    }

    public Obj divide(Obj with) {
        if (value instanceof Integer && with.value instanceof Integer) {
            return new Obj((Integer) value / (Integer) with.value);
        }
        return null;
    }

}
