package com.githubpractice;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.addAttachment;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GithubSteps {

    @Step("Открываем главную страницу")
    public void openMainPage() {
        open("https://github.com/");
    }

    @Step("Чистим куку браузера, а то github говорит, что я надоел к нему стучаться")
    public void clearBrowser() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

    @Step("Ищем в основном поиске юзера {user}")
    public void searchByUser(String user) {
        $(".header-search-input").click();
        $(".header-search-input").setValue(user).pressEnter();
        String currentUrl = getWebDriver().getCurrentUrl();
        assertTrue(currentUrl.equals("https://github.com/search?q=" + user + "&type="));
    }

    @Step("В результате поиска фильтруем по фильтру \"User\"")
    public void filterResultsByUser() {
        $(".col-12.col-md-3.float-left.px-md-2").$(byText("Users")).click();
    }

    @Step("Переходим на страницу юзера {user}")
    public void goToUserPage(String user) {
        $(".d-flex.hx_hit-user.px-0.Box-row").$(byText(user)).click();
        String currentUrl = getWebDriver().getCurrentUrl();
        assertTrue(currentUrl.equals("https://github.com/" + user));
    }

    @Step("Переходим ко вкладке \"Repositories\"")
    public void goToUserWidgetRepo() {
        $("[data-tab-item=repositories]").click();

    }

    @Step("Ищем репозиторий \"{userRepo}\"")
    public void searchForUserRepo(String userRepo) {
        $("#your-repos-filter").setValue(userRepo).pressEnter();
    }

    @Step("Переходим в найденный репозиторий")
    public void goToUserRepo(String userRepo, String user) {
        $("[data-filterable-for=your-repos-filter]").$(byText(userRepo)).click();
        String currentUrl = getWebDriver().getCurrentUrl();
        assertTrue(currentUrl.equals("https://github.com/" + user + "/" + userRepo));
    }

    @Step("Переходим ко вкладке \"Issue\"")
    public void goToUserWidgetIssue() {
        $("[data-content=Issues]").click();
    }

    @Step("Ищем issue c неймингом \"{issueName}\"")
    public void chechNameOfIssue(String issueName) {
        $$(".js-issue-row").findBy(text(issueName)).shouldHave(text(issueName));
        takeScreenshot();
        attPageSource();
    }

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public void attPageSource() {
        addAttachment("Page Source", "text/html", WebDriverRunner.source(), "html");
    }

}