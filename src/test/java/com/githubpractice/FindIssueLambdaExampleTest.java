package com.githubpractice;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindIssueLambdaExampleTest {

    private static final String user = "DmitriyTQC",
            baseUrl = "https://github.com/",
            userRepo = "allurereports-hw-qaguru-11-tda",
            issueName = "in_name: кириллица && symbols like '%Iñtërnâtiônàlizætiøn'";

    String currentUrl;

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @BeforeEach
    @DisplayName("Preconditions")
    void preconditions() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу", () -> {
            open(baseUrl);
        });

        step("Чистим куку браузера, а то github говорит, что я надоел к нему стучаться", () -> {
            Selenide.clearBrowserCookies();
            Selenide.clearBrowserLocalStorage();
        });
    }

    @Test
    @Owner("dimtok")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Обучение на репе " + userRepo)
    @DisplayName("Проверяем что существует Issue с заданным неймингом ")
    public void findFullIssueName() {

        step("Ищем в основном поиске юзера " + user, () -> {
            $(".header-search-input").click();
            $(".header-search-input").setValue(user).pressEnter();
            currentUrl = getWebDriver().getCurrentUrl();
            assertTrue(currentUrl.equals(baseUrl + "search?q=" + user + "&type="));
        });

        step("В результате поиска фильтруем по фильтру \"User\"", () -> {
            $(".col-12.col-md-3.float-left.px-md-2").$(byText("Users")).click();
        });

        step("Переходим на страницу юзера " + user, () -> {
            $(".d-flex.hx_hit-user.px-0.Box-row").$(byText(user)).click();
            currentUrl = getWebDriver().getCurrentUrl();
            assertTrue(currentUrl.equals(baseUrl + user));
        });

        step("Переходим ко вкладке \"Repositories\"", () -> {
            $("[data-tab-item=repositories]").click();
        });

        step("Ищем репозиторий \"" + userRepo + "\"", () -> {
            $("#your-repos-filter").setValue(userRepo).pressEnter();
        });

        step("Переходим в найденный репозиторий", () -> {
            $("[data-filterable-for=your-repos-filter]").$(byText(userRepo)).click();
            currentUrl = getWebDriver().getCurrentUrl();
            assertTrue(currentUrl.equals(baseUrl + user + "/" + userRepo));
        });

        step("Переходим ко вкладке \"Issue\"", () -> {
            $("[data-content=Issues]").click();
        });

        step("Ищем issue c неймингом \"" + issueName + "\"", () -> {
            $$(".js-issue-row").findBy(text(issueName)).shouldHave(text(issueName));
            takeScreenshot();
        });
    }
}
