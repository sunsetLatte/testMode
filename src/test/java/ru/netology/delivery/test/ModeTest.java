package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import com.codeborne.selenide.Configuration;

public class ModeTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test                                              // Позитивный тест
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
//        Configuration.holdBrowserOpen = true;
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[id='root']").shouldHave(Condition.text("Личный кабинет")).shouldBe(visible);
    }

    @Test                                     // Негативный тест, нельзя войти, еслипользователь не зарегистрирован
    void shouldGetErrorIfNotRegisteredUser() {
//        Configuration.holdBrowserOpen = true;
        var notRegisteredUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(visible);
    }

    @Test                               // Негативный тест, нельзя войти, тк пользователь заблокирован
    void shouldGetErrorIfBlockedUser() {
//        Configuration.holdBrowserOpen = true;
        var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Пользователь заблокирован"))
                .shouldBe(visible);

    }

    @Test                              // Негативный тест, нельзя войти с неверным логином
    void shouldGetErrorIfWrongLogin() {
//        Configuration.holdBrowserOpen = true;
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongLogin = DataGenerator.getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(visible);

    }

    @Test                                 // Негативный тест, нельзя войти с неверным паролем
    void shouldGetErrorIfWrongPassword() {
//        Configuration.holdBrowserOpen = true;
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongPassword = DataGenerator.getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(visible);
    }
}
