package pl.coderslab.charity.donation;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DonationValidationResult {
    INVALID_QUANTITY("Ilość worków musi być większa od 0"),
    NO_INSTITUTION("Wybierz instytucję"),
    NO_CATEGORY("Wybierz kategorię"),
    INVALID_ZIP_CODE("Wprowadź kod pocztowy w formacie: XX-XXX"),
    INVALID_STREET("Podaj prawidłową ulicę"),
    INVALID_CITY("Podaj prawidłowe miasto"),
    INVALID_DATE("Podaj datę odbioru z conajmniej 3-dniowym wyprzedzeniem"),
    INVALID_TIME("Podaj czas pomiędzy 6:00 a 19:00"),
    NO_DATA("Wprowadź dane"),
    SUCCESS("success");

    public final String msg;
    public final boolean success;

    DonationValidationResult(String msg){
        this.msg = msg;
        success = msg.equalsIgnoreCase("success");
    }
}
