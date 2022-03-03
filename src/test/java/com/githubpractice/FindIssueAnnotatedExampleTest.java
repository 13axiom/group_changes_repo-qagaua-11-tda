package com.githubpractice;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FindIssueAnnotatedExampleTest {

    private static final String user = "DmitriyTQC",
            userRepo = "allurereports-hw-qaguru-11-tda",
            issueName = "in_name: кириллица && symbols like '%Iñtërnâtiônàlizætiøn'";

    com.githubpractice.GithubSteps steps = new com.githubpractice.GithubSteps();

    @BeforeEach
    @DisplayName("Preconditions")
    void preconditions() {
        steps.openMainPage();
        steps.clearBrowser();
    }

    @Test
    @Owner("dimtok")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Обучение на репе " + userRepo)
    @DisplayName("Проверяем что существует Issue с заданным неймингом (AnnotatedTest)")
    public void findFullIssueName() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        Allure.parameter("user", "DmitriyTQC");
        Allure.parameter("userRepository", "allurereports-hw-qaguru-11-tda");
        Allure.parameter("issueName", "in_name: кириллица && symbols like '%Iñtërnâtiônàlizætiøn'");

        steps.searchByUser(user);
        steps.filterResultsByUser();
        steps.goToUserPage(user);
        steps.goToUserWidgetRepo();
        steps.searchForUserRepo(userRepo);
        steps.goToUserRepo(userRepo, user);
        steps.goToUserWidgetIssue();
        steps.chechNameOfIssue(issueName);
    }
}
