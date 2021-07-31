package yyc.open.framework.microants.components.kit.thirdpart;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.thirdpart.qrcode.BufferedImageLuminanceSource;
import yyc.open.framework.microants.components.kit.thirdpart.qrcode.MatrixToImageWriter;
import yyc.open.framework.microants.components.kit.thirdpart.qrcode.MatrixToLogoImageConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.util.Map;

/**
 * {@link QRCodeKit}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/31
 */
public class QRCodeKit {
    /**
     * Generates the default side length of the qr code, which is square and has the same height and width
     */
    private static final int DEFAULT_LENGTH = 400;
    /**
     * Generates the format of the QR code
     */
    private static final String FORMAT = "jpg";

    private static Logger logger = LoggerFactory.getLogger(QRCodeKit.class);

    /**
     * Generate QR code data based on content
     *
     * @param content Qr code text content [in order to information security, generally the data should be encrypted first]
     * @param length  Qr code image width and height
     */
    private static BitMatrix createQrcodeMatrix(String content, int length) {
        Map<EncodeHintType, Object> hints = Maps.newEnumMap(EncodeHintType.class);
        // Setting character encoding
        hints.put(EncodeHintType.CHARACTER_SET, Charsets.UTF_8.name());
        // Specifies the level of error correction
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        try {
            return new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, length, length, hints);
        } catch (Exception e) {
            logger.warn("[QR-CODE] build qr-code failed .", e);
            return null;
        }
    }

    /**
     * Creates the generated qr code based on the specified side length
     *
     * @param content  Content of TWO-DIMENSIONAL code
     * @param length   Height and width of qr code
     * @param logoFile Logo file object, which can be empty
     * @return
     */
    public static byte[] createQrcode(String content, int length, File logoFile) {
        if (logoFile != null && !logoFile.exists()) {
            throw new IllegalArgumentException("[QR-CODE] logo file must be exist .");
        }

        BitMatrix qrCodeMatrix = createQrcodeMatrix(content, length);
        if (qrCodeMatrix == null) {
            return null;
        }
        try {
            File file = Files.createTempFile("qrcode_", "." + FORMAT).toFile();
            logger.debug(file.getAbsolutePath());

            MatrixToImageWriter.writeToFile(qrCodeMatrix, FORMAT, file);
            if (logoFile != null) {
                // To add the logo image, you must re-read it here,
                // instead of using the BufferedImage object of the QR code directly
                BufferedImage img = ImageIO.read(file);
                overlapImage(img, FORMAT, file.getAbsolutePath(), logoFile, new MatrixToLogoImageConfig());
            }

            return toByteArray(file);
        } catch (Exception e) {
            logger.warn("build qr-code failed .", e);
            return null;
        }
    }

    /**
     * Create a qr code image that generates a default height (400) to specify whether the logo is lent
     *
     * @param content  Content of TWO-DIMENSIONAL code
     * @param logoFile Logo file object, which can be empty
     * @return
     */
    public static byte[] createQrcode(String content, File logoFile) {
        return createQrcode(content, DEFAULT_LENGTH, logoFile);
    }

    /**
     * Converting files to byte arrays, using the MappedByteBuffer, can improve performance when processing large files
     *
     * @param file
     * @return
     */
    private static byte[] toByteArray(File file) {
        try (FileChannel fc = new RandomAccessFile(file, "r").getChannel();) {
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (Exception e) {
            logger.warn("[QR-CODE] convert file stream exception ", e);
            return null;
        }
    }

    /**
     * Add the logo to the middle of the QR code
     *
     * @param image     Generated TWO-DIMENSIONAL code picture object
     * @param imagePath Picture Saving path
     * @param logoFile  Logo File object
     * @param format    Image format
     */
    private static void overlapImage(
            BufferedImage image,
            String format,
            String imagePath,
            File logoFile,
            MatrixToLogoImageConfig logoConfig)
            throws IOException {
        try {
            BufferedImage logo = ImageIO.read(logoFile);
            Graphics2D g = image.createGraphics();

            // Considering that the logo picture is attached to the QR code,
            // it is recommended that the size should not exceed 1/5 of the QR code.
            int width = image.getWidth() / logoConfig.getLogoPart();
            int height = image.getHeight() / logoConfig.getLogoPart();

            // The starting position of the logo. This purpose is to center the logo
            int x = (image.getWidth() - width) / 2;
            int y = (image.getHeight() - height) / 2;

            // Drawing a graph
            g.drawImage(logo, x, y, width, height, null);

            // Draw a border around the logo
            // Construct a solid BasicStroke with the specified line width and default values for cap and JOIN styles
            g.setStroke(new BasicStroke(logoConfig.getBorder()));
            g.setColor(logoConfig.getBorderColor());
            g.drawRect(x, y, width, height);

            g.dispose();
            // Write the logo picture to the QR code
            ImageIO.write(image, format, new File(imagePath));
        } catch (Exception e) {
            throw new IOException("[QR-CODE] build qr-code logo exception", e);
        }
    }

    /**
     * Parsing TWO-DIMENSIONAL code
     *
     * @param file Content of TWO-DIMENSIONAL code file
     * @return Content of QR code
     */
    public static String decodeQrcode(File file) throws IOException, NotFoundException {
        BufferedImage image = ImageIO.read(file);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map<DecodeHintType, Object> hints = Maps.newEnumMap(DecodeHintType.class);
        hints.put(DecodeHintType.CHARACTER_SET, Charsets.UTF_8.name());
        return new MultiFormatReader().decode(binaryBitmap, hints).getText();
    }
}
