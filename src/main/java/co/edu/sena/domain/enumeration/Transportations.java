package co.edu.sena.domain.enumeration;

/**
 * The Transportations enumeration.
 */
public enum Transportations {
    NO("trasnporte"),
    SI("notrasnporte");

    private final String value;

    Transportations(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
