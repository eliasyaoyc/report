package yyc.open.framework.microants.components.kit.thirdpart.qrcode;

import java.awt.*;

/**
 * {@link MatrixToLogoImageConfig}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/31
 */
public class MatrixToLogoImageConfig {
  /**
   * Logo Default border color
   */
  public static final Color DEFAULT_BORDERCOLOR = Color.RED;
  /**
   * Logo Default border width
   */
  public static final int DEFAULT_BORDER = 2;
  /**
   * The default logo size is 1/5 of the photo
   */
  public static final int DEFAULT_LOGOPART = 5;

  private final int border = DEFAULT_BORDER;
  private final Color borderColor;
  private final int logoPart;

  public MatrixToLogoImageConfig() {
    this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
  }

  public MatrixToLogoImageConfig(Color borderColor, int logoPart) {
    this.borderColor = borderColor;
    this.logoPart = logoPart;
  }

  public Color getBorderColor() {
    return this.borderColor;
  }

  public int getBorder() {
    return this.border;
  }

  public int getLogoPart() {
    return this.logoPart;
  }
}
