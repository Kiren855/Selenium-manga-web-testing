package org.triangle;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.event.MouseEvent;
import java.time.Duration;
import java.util.List;

public class Main {
    public static void readManga(WebElement manga, WebDriver driver) {

        manga.click();

        WebElement menu = driver.findElement(By.className("story-detail-menu"));

        WebElement startChapter = menu.findElement(By.className("li01"));

        startChapter.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));


        JavascriptExecutor js = (JavascriptExecutor) driver;
        long windowHeight = (long) js.executeScript("return window.innerHeight");
        long documentHeight = (long) js.executeScript("return document.body.scrollHeight");
        long scrollHeight = documentHeight - windowHeight;
        long scrollStep = scrollHeight / 500;

        for (int i = 0; i < 500; i++) {
            long scrollTo = i * scrollStep;
            js.executeScript("window.scrollTo(0, " + scrollTo + ")");
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        WebElement control = driver.findElement(By.className("chapter_control"));

        List<WebElement> chapterControl = control.findElements(By.tagName("a"));

        chapterControl.get(0).click();
    }

    public static void loginFunc(WebElement login, WebDriver driver) {
        login.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement emailLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_login")));
        WebElement passwordLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password_login")));

        emailLogin.sendKeys("nguyenvana123@email.co.ee");
        passwordLogin.sendKeys("123456789");

        WebElement buttonLogin = driver.findElement(By.className("button_login"));

        buttonLogin.click();

        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();
        alert.accept();

        WebElement buttonCancel = driver.findElement(By.className("no"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        buttonCancel.click();

    }

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com");

        WebElement searchBox = driver.findElement(By.name("q"));

        searchBox.sendKeys("truyenqq");

        searchBox.sendKeys(Keys.ENTER);

        WebElement truyenqqvnLink = driver.findElement(By.xpath("//a[@href='https://truyenqqvn.com/']"));

        truyenqqvnLink.click();

        WebElement listGrids = driver.findElement(By.id("list_new"));

        List<WebElement> listManga = listGrids.findElements(By.tagName("li"));

        readManga(listManga.get(0), driver);

        List<WebElement> menu = driver.findElements(By.cssSelector(".right li"));

        menu.get(0).click();

        WebElement search = driver.findElement(By.id("search_input"));

        search.sendKeys("Doraemon");

        search.sendKeys(Keys.ENTER);

        List<WebElement> listMangaSearched = driver.findElements(By.cssSelector(".list_grid li"));

        WebElement mangaSearched = listMangaSearched.get(0);

        readManga(mangaSearched, driver);

        WebElement login = menu.get(2);

        loginFunc(login, driver);
    }
}

