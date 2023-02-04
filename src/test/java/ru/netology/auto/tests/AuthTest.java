package ru.netology.auto.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.auto.util.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.auto.util.DataGenerator.Registration.getUser;
import static ru.netology.auto.util.DataGenerator.getRandomLogin;
import static ru.netology.auto.util.DataGenerator.getRandomPassword;

import ru.netology.auto.util.*;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    public void shouldTestRegisteredUser() {

        var registeredUser = getRegisteredUser("active");
        $x("//*[@data-test-id='login']//input").setValue(registeredUser.getLogin());
        $x("//*[@data-test-id='password']//input").setValue(registeredUser.getPassword());
        $x("//*[@data-test-id='action-login']").click();

        $x("//*[contains(text(), 'Личный кабинет')]").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    public void shouldTestUnregisteredUser() {

        var unregisteredUser = getUser("active");
        $x("//*[@data-test-id='login']//input").setValue(unregisteredUser.getLogin());
        $x("//*[@data-test-id='password']//input").setValue(unregisteredUser.getPassword());
        $x("//*[@data-test-id='action-login']").click();

        $x("//*[@data-test-id='error-notification']//div[text() = 'Неверно указан логин или пароль']").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    public void shouldTestWrongLogin() {

        var registeredUser = getRegisteredUser("active");
        var wrongLogin =getRandomLogin();
        $x("//*[@data-test-id='login']//input").setValue(wrongLogin);
        $x("//*[@data-test-id='password']//input").setValue(registeredUser.getPassword());
        $x("//*[@data-test-id='action-login']").click();

        $x("//*[@data-test-id='error-notification']//div[text() = 'Неверно указан логин или пароль']").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    public void shouldTestWrongPassword() {

        var registeredUser = getRegisteredUser("active");
        var wrongPassword =getRandomPassword();
        $x("//*[@data-test-id='login']//input").setValue(registeredUser.getLogin());
        $x("//*[@data-test-id='password']//input").setValue(wrongPassword);
        $x("//*[@data-test-id='action-login']").click();

        $x("//*[@data-test-id='error-notification']//div[text() = 'Неверно указан логин или пароль']").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    public void shouldTestBlockedStatus() {

        var registeredUser = getRegisteredUser("blocked");
        $x("//*[@data-test-id='login']//input").setValue(registeredUser.getLogin());
        $x("//*[@data-test-id='password']//input").setValue(registeredUser.getPassword());
        $x("//*[@data-test-id='action-login']").click();

        $x("//*[@data-test-id='error-notification']//div[text() = 'Пользователь заблокирован']").shouldBe(Condition.visible);
    }
}