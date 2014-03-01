package dom.intelihouse.model;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

import dom.intelihouse.IntelihouseApp;

import static dom.intelihouse.IntelihouseApp.*;

/**
 * Created by Lucho on 15/02/14.
 */
@Table(name="AC")
public class AC extends Entity {

    @TableField(name = "arduinoId", required = true, maxLength = 3, datatype = Entity.DATATYPE_STRING)
    private String arduinoId;
    @TableField(name = "descripcion", required = true, maxLength = 100, datatype = Entity.DATATYPE_STRING)
    private String descripcion;
    @TableField(name = "nodoArduino", required = true, maxLength = 2, datatype = Entity.DATATYPE_STRING)
    private String nodoArduino;
    @TableField(name = "power", datatype = Entity.DATATYPE_BOOLEAN)
    private boolean power;
    @TableField(name = "temp", datatype = Entity.DATATYPE_INTEGER)
    private int temp;
    @TableField(name = "fan", datatype = Entity.DATATYPE_INTEGER)
    private int fan;
    @TableField(name = "autoFan", datatype = Entity.DATATYPE_BOOLEAN)
    private boolean autoFan;
    @TableField(name = "mode", datatype = Entity.DATATYPE_STRING)
    private String mode;

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getFan() {
        return fan;
    }

    public void setFan(int fan) {
        this.fan = fan;
    }

    public boolean isAutoFan() {
        return autoFan;
    }

    public void setAutoFan(boolean autoFan) {
        this.autoFan = autoFan;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public AC_MODES getModeEnum() {
        AC_MODES modeP = AC_MODES.valueOf(mode);
        return modeP;
    }

    public String getModeShort() {
        switch (AC_MODES.valueOf(mode)) {
            case COLD:
                return "C";
            case HOT:
                return "H";
            case DES:
                return "D";
        }
        return "";
    }

    public void setModeEnum(AC_MODES modeP) {
        this.mode = modeP.toString();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getArduinoId() {
        return arduinoId;
    }

    public void setArduinoId(String arduinoId) {
        this.arduinoId = arduinoId;
    }

    public String getNodoArduino() {
        return nodoArduino;
    }

    public void setNodoArduino(String nodoArduino) {
        this.nodoArduino = nodoArduino;
    }


}
