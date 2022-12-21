package crossBrowser;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.ConfigFiles;
import utilities.Driver;
import utilities.ReusableMethods;

import java.time.Duration;
import java.util.List;

public class HepsiburadaPageCB extends TestBaseCross {

    String sepeteEklenenUrunbasligi;
    By cookieAcceptBbutton=By.xpath("//button[@id='onetrust-accept-btn-handler']");
    By markaFiltremeAlani= By.xpath("//div[@class='FacetList-Tjc_YybT1hcM66r5WELb FacetList-cG7_pIC3gwvKZwV3IUUM']");

    By siralamaBari=By.xpath("//label[@class='horizontalSortingBar-Ce404X9mUYVCRa5bjV4D']");
    By siralamaSekilleri=By.xpath("//div[@class='horizontalSortingBar-PkoDOH7UsCwBrQaQx9bn']");

    By sepeteEkleButonu=By.xpath("//div[contains(text(),'Sepete ekle')]");
    By sepeteUrunEklendiTexti=By.xpath("//div[@class='hb-toast-text']");
    By sepeteGitButonu=By.xpath("//a[@class='hb-toast-link']");
    By sepeteEklenenUrununTexti=By.xpath("//div[@class='product_name_3Lh3t']/a");
    By alisverisiTamamlaButonu=By.xpath("//button[@id='continue_step_btn']");
    By loginEkraniGirisButonu=By.xpath("//div[@class='_2q4oJzGUsyLIOBhRdWWO9D']");

    @Step("HepsiBurada sayfasına gidilir")
    public void anasayfa(String url) {
        driver.get(ConfigFiles.getProperty(url));
        TestBaseCross.waitForPageToLoad(10);
        TestBaseCross.waitFor(3);
        if (driver.findElement(cookieAcceptBbutton).isDisplayed()) {
            driver.findElement(cookieAcceptBbutton).click();
        }
    }

    @Step("HepsiBurada sayfasına gidildiği doğrulanır")
    public void sayfaDogrulama() {
        String actualData = driver.getCurrentUrl();
        String expectedData = "https://www.hepsiburada.com/";
        Assert.assertEquals(actualData, expectedData);
    }

    @Step("Elektronik kategorisinden Bilgisayar/Tablet alt kategorisi daha sonra Notebook seçilir")
    public void urunsec(String kategori, String altKategori, String secilenUrun) {
        TestBaseCross.hover(driver.findElement(By.xpath("//span[starts-with(@class,'sf-MenuItems')]/span[text()='" + kategori + "']")));
        TestBaseCross.hover(driver.findElement(By.xpath("//div[starts-with(@class,'sf-ChildMenuItems')]//a[text()='" + altKategori + "']")));
        driver.findElement(By.xpath("//span[text()='" + secilenUrun + "']")).click();
    }

    @Step("Notebook markaları arasında filtreleme işlemi yapılır")
    public void markaFiltremeAlani(String marka) {
        TestBaseCross.scrollIntoView(driver.findElements(markaFiltremeAlani).get(0));
        TestBaseCross.waitFor(3);
        WebElement istenenMarka = driver.findElement(By.cssSelector("[value=" + marka.toLowerCase() + "]"));
        TestBaseCross.waitForClickAblility(istenenMarka, 3);
        istenenMarka.click();
        driver.navigate().refresh();
    }

    @Step("Apple marka ürünler Çok Satanlara göre sıralama yapılır")
    public void siralamaYapma(String siralmaSekli) {
        driver.findElement(siralamaBari).click();
        List<WebElement> siralama = driver.findElements(siralamaSekilleri);
        for (int i = 0; i < siralama.size(); i++) {
            if (siralama.get(i).getText().equals(siralmaSekli)) {
                WebElement siralamadaIstenen = driver.findElement(By.xpath("(//div[@class='horizontalSortingBar-PkoDOH7UsCwBrQaQx9bn'])[" + (i + 1) + "]"));
                TestBaseCross.waitForClickAblility(siralamadaIstenen, 5);
                siralamadaIstenen.click();
            }
        }
        driver.navigate().refresh();
    }

    @Step("Çok Satanlara göre sıralanan ürünler arasında 2. sırada bulunan sepete eklenir")
    public void sepeteEklenecekUrun(int sayi) {
        TestBaseCross.waitFor(3);
        WebElement urun = driver.findElement(By.xpath("(//h3[@data-test-id='product-card-name'])[" + sayi + "]"));
        TestBaseCross.scrollIntoView(urun);
        TestBaseCross.waitForVisibility(urun, 5);
        sepeteEklenenUrunbasligi = urun.getText();
        TestBaseCross.hover(urun);
        driver.findElement(sepeteEkleButonu).click();
    }

    @Step("Sepete ürün eklendiği doğrulanır")
    public void sepeteUrunEklenmesiDogrulama() {
        ReusableMethods.waitForVisibility(driver.findElement(sepeteUrunEklendiTexti), 5);
        Assert.assertTrue(driver.findElement(sepeteUrunEklendiTexti).isDisplayed());
    }

    @Step("Sepet sayfasına gidilir")
    public void sepetSayfasi() {
        ReusableMethods.waitForVisibility(driver.findElement(sepeteGitButonu), 5);
        driver.findElement(sepeteGitButonu).click();
    }

    @Step("Sepete eklenen ürünün doğrulanır")
    public void eklenenUrununSepetteDogrulanmasi() {
        ReusableMethods.waitForVisibility(driver.findElement(sepeteEklenenUrununTexti), 5);
        Assert.assertTrue(driver.findElement(sepeteEklenenUrununTexti).getText().contains(sepeteEklenenUrunbasligi));
    }

    @Step("Alışveriş tamamlanır ve Login ekranına gidilir")
    public void alisverisTamamlanirVeLoginEkraniDogrulanir() {
        driver.findElement(alisverisiTamamlaButonu).click();
        Assert.assertTrue(driver.findElement(loginEkraniGirisButonu).isDisplayed());
        driver.close();
    }

}
