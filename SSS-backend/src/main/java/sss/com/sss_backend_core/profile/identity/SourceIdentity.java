package sss.com.sss_backend_core.profile.identity;

/** Thông tin một nguồn cung cấp để thử khớp. Trường nào không có thì để null. */
public record SourceIdentity(String sourceSystem, String sourceId,
                             String studentId, String nationalId, String fullName) {}