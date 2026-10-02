package az.edu.itbrains.fruitables.helpers;

public class SeoHelper {

    public static String createSeoUrl(String name){
        String seoUrl = name.toLowerCase()
                .replace(" ", "-")
                .replace("ə", "e")
                .replace("ü", "u")
                .replace("ö", "o")
                .replace("ğ", "g")
                .replace("ç", "c")
                .replace("ş", "s")
                .replace("ı", "i")
                .replaceAll("[^a-z0-9-]", "")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");

        return seoUrl;
    }
}
