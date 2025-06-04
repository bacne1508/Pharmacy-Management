package vn.com.pharmacity.utils;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;

public class BarcodeUtil {
    public static String generateBarcodeBase64(String code) throws Exception {
        BitMatrix matrix = new MultiFormatWriter().encode(code, BarcodeFormat.CODE_128, 300, 100);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        return "data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
