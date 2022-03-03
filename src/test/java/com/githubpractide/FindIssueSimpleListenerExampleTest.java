package com.githubpractide;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindIssueSimpleListenerExampleTest {

    @Test
    public void findFullIssueName() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com");

        $(".header-search-input").click();
        $(".header-search-input").setValue("DmitriyTQC").pressEnter();
        String currentUrl = getWebDriver().getCurrentUrl();
        assertTrue(currentUrl.equals("https://github.com/search?q=DmitriyTQC&type="));

        $(".col-12.col-md-3.float-left.px-md-2").$(byText("Users")).click();
        $(".d-flex.hx_hit-user.px-0.Box-row").$(byText("DmitriyTQC")).click();
        currentUrl = getWebDriver().getCurrentUrl();
        assertTrue(currentUrl.equals("https://github.com/DmitriyTQC"));

        $("[data-tab-item=repositories]").click();
        $("#your-repos-filter").setValue("allurereports-hw-qaguru-11-tda").pressEnter();
        $("[data-filterable-for=your-repos-filter]").$(byText("allurereports-hw-qaguru-11-tda")).click();
        currentUrl = getWebDriver().getCurrentUrl();
        assertTrue(currentUrl.equals("https://github.com/DmitriyTQC/allurereports-hw-qaguru-11-tda"));

        $("[data-content=Issues]").click();
        $$(".js-issue-row").findBy(text("in_name: кириллица && symbols like '%Iñtërnâtiônàlizætiøn'")).shouldHave(text("in_name: кириллица && symbols like '%Iñtërnâtiônàlizætiøn'"));

    }
}
