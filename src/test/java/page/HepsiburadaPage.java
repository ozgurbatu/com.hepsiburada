package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.ConfigFiles;
import utilities.Driver;
import utilities.ReusableMethods;

import java.util.List;

public class HepsiburadaPage {

    public static WebDriver driver = Driver.getDriver();

    String sepeteEklenenUrunbasligi;

    public HepsiburadaPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//button[@id='onetrust-accept-btn-handler']")
    public WebElement cookieAcceptBbutton;

    @FindBy(xpath = "//div[@class='FacetList-Tjc_YybT1hcM66r5WELb FacetList-cG7_pIC3gwvKZwV3IUUM']")
    public List<WebElement> markaFiltremeAlani;

    @FindBy(xpath = "//label[@class='horizontalSortingBar-Ce404X9mUYVCRa5bjV4D']")
    public WebElement siralamaBari;

    @FindBy(xpath = "//div[@class='horizontalSortingBar-PkoDOH7UsCwBrQaQx9bn']")
    public List<WebElement> siralamaSekilleri;

    @FindBy(xpath = "//div[contains(text(),'Sepete ekle')]")
    public WebElement sepeteEkleButonu;

    @FindBy(xpath = "//div[@class='hb-toast-text']")
    public WebElement sepeteUrunEklendiTexti;

    @FindBy(xpath = "//a[@class='hb-toast-link']")
    public WebElement sepeteGitButonu;

    @FindBy(xpath = "//div[@class='product_name_3Lh3t']/a")
    public WebElement sepeteEklenenUrununTexti;

    @FindBy(xpath = "//button[@id='continue_step_btn']")
    public WebElement alisverisiTamamlaButonu;

    @FindBy(xpath = "//div[@class='_2q4oJzGUsyLIOBhRdWWO9D']")
    public WebElement loginEkraniGirisButonu;

    /*
    By girisyap_ekrani_text=By.xpath("//div[@role='presentation']/div[contains(text(),'Giriş yap')]");
*/

    @Step("HepsiBurada sayfasına gidilir")
    public void anasayfa(String url) {
        driver.get(ConfigFiles.getProperty(url));
        ReusableMethods.waitForPageToLoad(10);
        ReusableMethods.waitFor(5);
        if (cookieAcceptBbutton.isDisplayed()) {
            cookieAcceptBbutton.click();
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
        ReusableMethods.hover(driver.findElement(By.xpath("//span[starts-with(@class,'sf-MenuItems')]/span[text()='" + kategori + "']")));
        ReusableMethods.hover(driver.findElement(By.xpath("//div[starts-with(@class,'sf-ChildMenuItems')]//a[text()='" + altKategori + "']")));
        driver.findElement(By.xpath("//span[text()='" + secilenUrun + "']")).click();
    }

    @Step("Notebook markaları arasında filtreleme işlemi yapılır")
    public void markaFiltremeAlani(String marka) {
        ReusableMethods.scrollIntoView(markaFiltremeAlani.get(0));
        ReusableMethods.waitFor(3);
        WebElement istenenMarka = driver.findElement(By.cssSelector("[value=" + marka.toLowerCase() + "]"));
        ReusableMethods.waitForClickAblility(istenenMarka, 3);
        istenenMarka.click();
        driver.navigate().refresh();
    }


    @Step("Apple marka ürünler Çok Satanlara göre sıralama yapılır")
    public void siralamaYapma(String siralmaSekli) {
        siralamaBari.click();
        List<WebElement> siralama = siralamaSekilleri;
        for (int i = 0; i < siralama.size(); i++) {
            if (siralama.get(i).getText().equals(siralmaSekli)) {
                WebElement siralamadaIstenen = driver.findElement(By.xpath("(//div[@class='horizontalSortingBar-PkoDOH7UsCwBrQaQx9bn'])[" + (i + 1) + "]"));
                ReusableMethods.waitForClickAblility(siralamadaIstenen, 5);
                siralamadaIstenen.click();
            }
        }
        driver.navigate().refresh();
    }

    @Step("Çok Satanlara göre sıralanan ürünler arasında 2. sırada bulunan sepete eklenir")
    public void sepeteEklenecekUrun(int sayi) {
        ReusableMethods.waitFor(3);
        WebElement urun = driver.findElement(By.xpath("(//h3[@data-test-id='product-card-name'])[" + sayi + "]"));
        ReusableMethods.scrollIntoView(urun);
        ReusableMethods.waitForVisibility(urun, 5);
        sepeteEklenenUrunbasligi = urun.getText();
        ReusableMethods.hover(urun);
        sepeteEkleButonu.click();
    }

    @Step("Sepete ürün eklendiği doğrulanır")
    public void sepeteUrunEklenmesiDogrulama() {
        ReusableMethods.waitForVisibility(sepeteUrunEklendiTexti, 5);
        Assert.assertTrue(sepeteUrunEklendiTexti.isDisplayed());
    }

    @Step("Sepet sayfasına gidilir")
    public void sepetSayfasi() {
        ReusableMethods.waitForVisibility(sepeteGitButonu, 5);
        sepeteGitButonu.click();
    }

    @Step("Sepete eklenen ürünün doğrulanır")
    public void eklenenUrununSepetteDogrulanmasi() {
        ReusableMethods.waitForVisibility(sepeteEklenenUrununTexti, 5);
        Assert.assertTrue(sepeteEklenenUrununTexti.getText().contains(sepeteEklenenUrunbasligi));
    }

    @Step("Alışveriş tamamlanır ve Login ekranına gidilir")
    public void alisverisTamamlanirVeLoginEkraniDogrulanir() {
        alisverisiTamamlaButonu.click();
        Assert.assertTrue(loginEkraniGirisButonu.isDisplayed());
        driver.quit();
    }

}
