package ru.netology.patterns.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.patterns.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class AuthTest {

    @BeforeAll
    static void setUpAll() {
        // Увеличиваем таймауты для CI
        Configuration.timeout = 20000;
        Configuration.browserSize = "1280x800";

        // Настройки для стабильной работы в CI
        Configuration.headless = true;
        Configuration.browserCapabilities = new ChromeOptions()
                .addArguments("--no-sandbox")
                .addArguments("--disable-dev-shm-usage")
                .addArguments("--disable-gpu")
                .addArguments("--remote-allow-origins=*")
                .addArguments("--window-size=1280,800");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        $("[data-test-id=login]").shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = DataGenerator.getRegisteredUser("active");

        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();

        $("[data-test-id=dashboard]").shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = DataGenerator.getRegisteredUser("blocked");

        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();

        // Ждем появления уведомления об ошибке
        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .$(".notification__content")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = DataGenerator.getRandomUser();

        $("[data-test-id=login] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id=action-login]").click();

        // Ждем появления уведомления об ошибке
        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .$(".notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = DataGenerator.getRegisteredUser("active");

        $("[data-test-id=login] input").setValue("wrong");
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();

        // Ждем появления уведомления об ошибке
        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .$(".notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = DataGenerator.getRegisteredUser("active");

        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue("wrong");
        $("[data-test-id=action-login]").click();

        // Ждем появления уведомления об ошибке
        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .$(".notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}