import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.Keys.HOME;
import static org.openqa.selenium.Keys.SHIFT;


public class CardDeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldOrderCard() { //Успешная отправка формы
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Смирнова Виктория");
        $("[data-test-id=phone] input").setValue("+79944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $(withText("Успешно")).shouldHave(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldFormSubmissionWithoutCheckbox() { //следует отправить форму без флажка
        open("http://localhost:9999");
        $("[data-test-id=city] input ").setValue("Москва");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Смирнова Виктория");
        $("[data-test-id=phone] input ").setValue("+79944232365");
        $(".button").click();
    }

    @Test
    void shouldEnterInvalidCity() { //следует указать неверный город
        open("http://localhost:9999");
        $("[data-test-id=city] input ").setValue("Кандалакша");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Смирнова Виктория");
        $("[data-test-id=phone] input").setValue("+79944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    void shouldEnterInvalidCityLatin() { //следует указать город на латинице
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Moscow");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Смирнова Виктория");
        $("[data-test-id=phone] input").setValue("+79944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    void shouldLeaveEmptyFieldCity() { //следует оставить поле город пустым
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Смирнова Виктория");
        $("[data-test-id=phone] input").setValue("+79944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldEnterSpacesFieldCity() { //следует ввести пробелы в поле город
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("           ");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Смирнова Виктория");
        $("[data-test-id=phone] input ").setValue("+79944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldEnterNumbersFieldCity() { //следует ввести цифры в поле город
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("123456987");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Смирнова Виктория");
        $("[data-test-id=phone] input").setValue("+79944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));

    }
    @Test
    void shouldEnterSpecialCharactersFieldCity() { //следует ввести специальные знаки в поле город
        open("http://localhost:9999");
        $("[data-test-id=city].input .input__control").setValue("%№$$$");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Смирнова Виктория");
        $("[data-test-id=phone] input").setValue("+79944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));

    }
    @Test
    void shouldEnterInvalidNameLatin() { //следует ввести имя, фамилия на латинице
        open("http://localhost:9999");
        $("[data-test-id=city].input .input__control").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Smirnova Viktoria");
        $("[data-test-id=phone] input").setValue("+79944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldEnterInvalidNameNumbers() { //следует ввести цифры в поле имя
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("12366547989");
        $("[data-test-id=phone] input").setValue("+79944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldEnterInvalidNameSpecialCharacters() { //следует ввести специальные знаки в поле имя
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("12366547989");
        $("[data-test-id=phone] input").setValue("+79944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldLeaveEmptyFieldName() { //следует оставить поле имя пустым
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldEnterSpacesFieldName() { //следует ввести пробелы в поле имя
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("            ");
        $("[data-test-id=phone] input").setValue("+79944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldEnterNameWithADash() { //следует ввести имя через дефис
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Анна-Мария Иванова");
        $("[data-test-id=phone] input").setValue("+79944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $(withText("Успешно")).shouldHave(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldEnterNumberThrough8() { //следует ввести номер через 8
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Анна-Мария Иванова");
        $("[data-test-id=phone] input").setValue("89944232365");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldEnter12Numbers() { //следует ввести 12 цифр
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Анна-Мария Иванова");
        $("[data-test-id=phone] input").setValue("+799442323655");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldEnter1Numbers() { //следует ввести 1 цифру
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Анна-Мария Иванова");
        $("[data-test-id=phone] input").setValue("+799442323655");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldEnter10Numbers() { //следует ввести 10 цифр
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Анна-Мария Иванова");
        $("[data-test-id=phone] input").setValue("+799442323655");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldEmptyFieldNumber() { //следует оставить поле пустым
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Анна-Мария Иванова");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldEnterSpacesFieldNumber() { //следует ввести пробелы в поле номер
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Анна-Мария Иванова");
        $("[data-test-id=phone] input").setValue("         ");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldEnterLatinNumber() { //следует ввести латиницу в поле номер
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Анна-Мария Иванова");
        $("[data-test-id=phone] input").setValue("Viktoria");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void shouldEnterCyrillicNumber() { //следует ввести кириллицу в поле номер
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Анна-Мария Иванова");
        $("[data-test-id=phone] input").setValue("Виктория");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldEnterSpecialCharactersNumber() { //следует ввести специальные знаки в поле номер
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Анна-Мария Иванова");
        $("[data-test-id=phone] input").setValue("#%$%%$#%");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldEnterPlusSignAtTheEndNumber() { //следует ввести плюс в конце номера
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3,"dd/MM/yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Анна-Мария Иванова");
        $("[data-test-id=phone] input").setValue("79119695381+");
        $("[data-test-id=agreement").click();
        $(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}
