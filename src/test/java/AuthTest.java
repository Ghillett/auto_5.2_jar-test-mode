import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @ParameterizedTest
    @CsvSource({
            "vasya, password, active," +
                    "vasya, password"
    })
    public void shouldTestExistingUser(String login, String password, String status, String actualLogin, String actualPassword) {

        CreateUser newUser = new CreateUser(login, password, status);
        $x("//*[@data-test-id='login']//input").setValue(actualLogin);
        $x("//*[@data-test-id='password']//input").setValue(actualPassword);
        $x("//*[@data-test-id='action-login']").click();

        $x("//*[contains(text(), 'Личный кабинет')]").shouldBe(Condition.visible);
    }

    @ParameterizedTest
    @CsvSource({
            "vasya, password, active," +
                    "petya, password"
    })
    public void shouldTestWrongLogin(String login, String password, String status, String actualLogin, String actualPassword) {

        CreateUser newUser = new CreateUser(login, password, status);
        $x("//*[@data-test-id='login']//input").setValue(actualLogin);
        $x("//*[@data-test-id='password']//input").setValue(actualPassword);
        $x("//*[@data-test-id='action-login']").click();

        $x("//*[@data-test-id='error-notification']//div[text() = 'Неверно указан логин или пароль']").shouldBe(Condition.visible);
    }

    @ParameterizedTest
    @CsvSource({
            "vasya, password, active," +
                    "vasya, wrongpassword"
    })
    public void shouldTestWrongPassword(String login, String password, String status, String actualLogin, String actualPassword) {

        CreateUser newUser = new CreateUser(login, password, status);
        $x("//*[@data-test-id='login']//input").setValue(actualLogin);
        $x("//*[@data-test-id='password']//input").setValue(actualPassword);
        $x("//*[@data-test-id='action-login']").click();

        $x("//*[@data-test-id='error-notification']//div[text() = 'Неверно указан логин или пароль']").shouldBe(Condition.visible);
    }

    @ParameterizedTest
    @CsvSource({
            "vasya, password, blocked," +
                    "vasya, password"
    })
    public void shouldTestBlockedStatus(String login, String password, String status, String actualLogin, String actualPassword) {

        CreateUser newUser = new CreateUser(login, password, status);
        $x("//*[@data-test-id='login']//input").setValue(actualLogin);
        $x("//*[@data-test-id='password']//input").setValue(actualPassword);
        $x("//*[@data-test-id='action-login']").click();

        $x("//*[@data-test-id='error-notification']//div[text() = 'Пользователь заблокирован']").shouldBe(Condition.visible);
    }
}