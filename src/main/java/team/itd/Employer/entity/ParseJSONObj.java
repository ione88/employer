package team.itd.Employer.entity;

import org.json.JSONObject;

public class ParseJSONObj {

    private static JSONObject employer;

    public static Employer getEmployer(JSONObject item) {
        employer = item.getJSONObject("employer");
        if (employer == null) {
            System.out.println("Поле работодатель не найдено для объекта " + item);
            return null;
        }
        Long id = getId();
        if (id == 0) {
            System.out.println("Id работодателя не найдено для объекта " + item);
            return null;
        }
        return Employer.builder()
                .contact(getContact(item))
                .id(getId())
                .url(getUrl())
                .alternativeUrl(getAlternativeUrl())
                .name(getName())
                .logo(getLogo())
                .build();
    }

    private static String getName() {
        try {
            return employer.getString("name");
        } catch (Exception e) {
            return "";
        }
    }

    private static String getLogo() {
        try {
            return employer.getJSONObject("logo_urls").getString("original");
        } catch (Exception e) {
            return "";
        }
    }

    private static String getAlternativeUrl() {
        try {
            return employer.getString("alternate_url");
        } catch (Exception e) {
            return "";
        }
    }

    private static String getUrl() {
        try {
            return employer.getString("url");
        } catch (Exception e) {
            return "";
        }
    }

    private static Long getId() {
        try {
            return Long.valueOf(employer.getString("id"));
        } catch (Exception e) {
            return 0L;
        }
    }

    private static Contact getContact(JSONObject item) {
        try {
            JSONObject contact = item.getJSONObject("contacts");
            return Contact.builder()
                    .name(contact.getString("name"))
                    .email(contact.getString("email"))
                    .phone(getPhone(contact.getJSONObject("phones")))
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    private static Phone getPhone(JSONObject item) {
        return Phone.builder()
                .country(item.getString("country"))
                .city(item.getString("city"))
                .number(item.getString("number"))
                .comment(item.getString("comment"))
                .build();
    }
}
