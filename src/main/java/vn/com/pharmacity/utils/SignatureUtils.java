package vn.com.pharmacity.utils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;

import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for handling signature-related operations.
 * This class provides methods to build default text for a layer 2 signature
 * appearance in a PDF document.
 * * @author Bac
 * * @date 2025/6/15
 * * @version 1.0
 */
public class SignatureUtils {

    public static String buildDefaultLayer2Text(PdfSignatureAppearance appearance) {
        StringBuilder text = new StringBuilder();

        String signer = appearance.getSignatureCreator();
        if (signer == null || signer.isEmpty()) {
            signer = "Unknown Signer";
        }

        String reason = appearance.getReason();
        String location = appearance.getLocation();

        text.append("Digitally signed by ").append(signer).append("\n");
        text.append("Date: ").append(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date())).append("\n");

        if (reason != null && !reason.isBlank()) {
            text.append("Reason: ").append(reason).append("\n");
        }

        if (location != null && !location.isBlank()) {
            text.append("Location: ").append(location);
        }

        return text.toString();
    }
    
    public static String extractCommonNameFromCertificate(Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            X509Certificate cert = (X509Certificate) certificate;
            String dn = cert.getSubjectX500Principal().getName(); // Ví dụ: CN=Le Van Bac, OU=IT, O=Hospital, C=VN

            for (String part : dn.split(",")) {
                if (part.trim().startsWith("CN=")) {
                    return part.trim().substring(3); // Trả về giá trị sau "CN="
                }
            }
        }
        return "Unknown";
    }
    
    /**
     * Tính toán vùng chữ ký (Rectangle) sao cho vừa đủ rộng để chứa dòng chữ "Digitally signed by [name]"
     * @param reader     PdfReader
     * @param pageNum    Trang số để ký
     * @param signerName Tên người ký (đã set sẵn vào appearance)
     * @param fontSize   Kích thước font chữ
     * @param bottomY    Tọa độ Y (bottom) của vùng chữ ký
     * @return Rectangle phù hợp
     */
    public static Rectangle calculateSignatureRectangle(PdfReader reader, int pageNum, String signerName, float fontSize, float bottomY) throws IOException, DocumentException {
        Rectangle pageSize = reader.getPageSize(pageNum);
        float pageWidth = pageSize.getWidth();

        String fullLine = "Digitally signed by " + signerName;

        // Dự đoán chiều rộng cần thiết cho dòng chữ đó
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        float textWidth = bf.getWidthPoint(fullLine, fontSize);

        // Thêm 10–20pt đệm
        float padding = 20;
        float fieldWidth = textWidth + padding;

        // Canh phải cách mép phải 40pt
        float right = pageWidth - 40;
        float left = right - fieldWidth;

        float top = bottomY + 80; // Chiều cao 60pt

        return new Rectangle(left, bottomY, right, top);
    }

}
