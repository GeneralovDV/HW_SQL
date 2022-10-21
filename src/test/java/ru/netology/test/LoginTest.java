package ru.netology.test;

import ru.netology.data.DataHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import lombok.val;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

class LoginTest {
    @BeforeEach
    void login() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void clearDB() {
        DataHelper.clearDB();
    }


    @Test
    void shouldLoginFirstRegisteredUser() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoFirstUser();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.validVerify(DataHelper.getVerificationCodeFor());
        val dashboardPage = new DashboardPage();

    }

    @Test
    void shouldLoginSecondRegisteredUser() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoSecondUser();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.validVerify(DataHelper.getVerificationCodeFor());
        val dashboardPage = new DashboardPage();
    }

    @Test
    void notShouldWithWrongLogin() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoWrongLogin();
        loginPage.validLogin(authInfo);
        loginPage.getLoginNotification();

    }

    @Test
    void notShouldWithWrongPassword() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoWrongPassword();
        loginPage.validLogin(authInfo);
        loginPage.getLoginNotification();

    }

    @Test
    void shouldBlockedVerificationPage() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoFirstUser();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.validVerify(DataHelper.getWrongCode());
        login();
        loginPage.validLogin(authInfo);
        verificationPage.validVerify(DataHelper.getWrongCode());
        login();
        loginPage.validLogin(authInfo);
        verificationPage.validVerify(DataHelper.getWrongCode());
        login();
        loginPage.validLogin(authInfo);
        verificationPage.validVerify(DataHelper.getWrongCode());
        verificationPage.getBlockedVerificationNotification();
    }

}
