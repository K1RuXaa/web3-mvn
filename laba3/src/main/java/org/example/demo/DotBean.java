package org.example.demo;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;


import java.io.Serializable;

@Named
@SessionScoped
public class DotBean implements Serializable {

    private Double x = 0.0;
    private Double y;
    private Double r;

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getR() {
        return r;
    }

    public static boolean checkX(Double value) {
        return (value <= 5 && value >= -3);
    }
    public static boolean checkY(Double value) {
        return (value <= 3 && value >= -5);
    }

    public void validateCoordinates() {
        if (y < -5 || y > 3) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ошибка координат",
                    "X должен быть в диапазоне от -3 до 5, а Y — от -5 до 3.");
            FacesContext.getCurrentInstance().addMessage("formGraph:Y", message);
        }
    }
}