package sss.com.sss_backend_core.profile.identity;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Locale;

public final class NameNormalizer {
    private NameNormalizer() {}

    /** "Nguyễn Văn An" và "an van NGUYEN" cho cùng một kết quả. */
    public static String normalize(String name) {
        if (name == null) return "";
        String s = name.replace('đ', 'd').replace('Đ', 'D');
        s = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        s = s.toLowerCase(Locale.ROOT).replaceAll("[^a-z\\s]", " ").trim();
        if (s.isEmpty()) return "";
        String[] tokens = s.split("\\s+");
        Arrays.sort(tokens);                       // bỏ qua thứ tự từ (đảo họ/tên)
        return String.join(" ", tokens);
    }
}