package hu.szatomi.diakok;

public record Student(
        Integer id,
        String lastName,
        String firstName,
        String sex,
        String studentClass,
        String birthDate,
        String city,
        Integer height
) {

    @Override
    public String toString() {
        return "%s %s, %s (%s %s, %d cm)".formatted(lastName, firstName, studentClass, city, birthDate, height);
    }
}
