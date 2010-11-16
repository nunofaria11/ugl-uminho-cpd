/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.VisitorsDecorators;

/**
 *
 * @author nuno
 */
public class ColorDecorator extends Decorator implements Comparable<ColorDecorator> {

    ColorEnum color;

    public ColorDecorator(ColorEnum color) {
        super.dec_id = "color";
        this.color = color;
    }

    public ColorDecorator() {
        super.dec_id = "color";
        this.color = ColorEnum.WHITE;
    }

    public int compareTo(ColorDecorator cd) {
        if (this.color == ColorEnum.WHITE) {
            return -1;
        }
        if (this.color == ColorEnum.BLACK) {
            return 1;
        }
        if (this.color == ColorEnum.GREY) {
            if (cd.color == ColorEnum.WHITE) {
                return 1;
            }
            if (cd.color == ColorEnum.BLACK) {
                return -1;
            }
        }
        return 0;
    }
}
