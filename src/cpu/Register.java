package cpu;

public class Register {
    private String name;
    private String address;
    private String value;

    public Register(String name, String address, String value) {
        this.name = name;
        this.address = address;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // opcionalno: toString() radi lakšeg ispisa
    @Override
    public String toString() {
        return "Register{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public int getValueAsInt() {
        return Integer.parseInt(value);
    }
}
