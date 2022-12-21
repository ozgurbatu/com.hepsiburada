package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import page.HepsiburadaPage;
import utilities.AllureListener;

@Listeners({AllureListener.class})
public class HepsiburadaTest extends HepsiburadaPage {

    @Story("Hepsiburada Test Case Çalışması")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Üye olmadan Apple marka notebook sepete ekleme")
    public void test(){
        anasayfa("Url");
        sayfaDogrulama();
        urunsec("Elektronik","Bilgisayar/Tablet","Notebook");
        markaFiltremeAlani("Apple");
        siralamaYapma("Çok satanlar");
        sepeteEklenecekUrun(2);
        sepeteUrunEklenmesiDogrulama();
        sepetSayfasi();
        eklenenUrununSepetteDogrulanmasi();
        alisverisTamamlanirVeLoginEkraniDogrulanir();
    }


}
