package team.itd.Employer.controller;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.itd.Employer.entity.Employer;
import team.itd.Employer.entity.ParseJSONObj;
import team.itd.Employer.entity.Url;
import team.itd.Employer.service.EmployerService;

@RestController
public class LoadEmployer {
    private static Set<Employer> employerSet;
    public EmployerService employerService;

    public LoadEmployer(EmployerService employerService) {
        this.employerService = employerService;
    }

    @GetMapping({"/greeting"})
    public String greeting(@RequestParam(name = "text",required = false,defaultValue = "python") String text, @RequestParam(name = "period",required = false,defaultValue = "10") Integer period, @RequestParam(name = "per_page",required = false,defaultValue = "100") Integer perPage, @RequestParam(name = "page",required = false,defaultValue = "0") Integer firstPage, @RequestParam(name = "last_page",required = false,defaultValue = "19") Integer lastPage, Map<String, Object> model) {
        employerSet = new HashSet();
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setLogLevel(ChromeDriverLogLevel.fromLevel(Level.OFF));
        WebDriver driver = new ChromeDriver(chromeOptions);
        Url url = Url.builder().period(period).text(text).per_page(perPage).page(firstPage).build();

        for(int i = firstPage; i < lastPage; ++i) {
            url.nextPage();
            driver.get(url.toString());
            String json = driver.getPageSource().split(">")[6].split("<")[0];
            JSONObject obj = new JSONObject(json);
            JSONArray items = obj.getJSONArray("items");
            this.process(items);
        }

        driver.quit();
        this.output(text);
        model.put("name", text);
        return "greeting";
    }

    private void process(JSONArray items) {
        for(int i = 0; i < items.length(); ++i) {
            Employer employer = ParseJSONObj.getEmployer(items.getJSONObject(i));
            if (employer != null && employer.getUrl().length() != 0) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setLogLevel(ChromeDriverLogLevel.fromLevel(Level.WARNING));
                WebDriver driver = new ChromeDriver(chromeOptions);
                driver.get(employer.getUrl());
                String json = driver.getPageSource().split(">")[6].split("<")[0];
                JSONObject obj = new JSONObject(json);
                employer.setSite_url(obj.getString("site_url"));
                driver.quit();
                employerSet.add(employer);
            }
        }

    }

    private void output(String text) {
        ZonedDateTime now = ZonedDateTime.now();
        String name = text + " - " + now.getYear() + "." + now.getMonth().getValue() + "." + now.getDayOfMonth() + "_" + now.getHour() + ":" + now.getMinute();
        String format = ".csv";

        try {
            PrintWriter writer = new PrintWriter(name + format);

            try {
                Iterator var6 = employerSet.iterator();

                while(var6.hasNext()) {
                    Employer employer = (Employer)var6.next();
                    writer.write(employer.csvRow());
                    writer.write("\n");
                    this.employerService.update(employer);
                }
            } catch (Throwable var9) {
                try {
                    writer.close();
                } catch (Throwable var8) {
                    var9.addSuppressed(var8);
                }

                throw var9;
            }

            writer.close();
        } catch (FileNotFoundException var10) {
            System.out.println(var10.getMessage());
        }

    }

    @GetMapping
    public String main(Map<String, Object> model) {
        model.put("some", "hello, letsCode!");
        return "main";
    }
}
