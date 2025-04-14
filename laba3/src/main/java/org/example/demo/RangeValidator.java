package org.example.demo;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("rangeValidator")

public class RangeValidator implements Validator<Object> {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            throw new ValidatorException(new FacesMessage("Неверный формат данных"));

        }

        if (!(value instanceof Double)) {
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Ошибка валидации",
                    "Неподдерживаемый тип значения."
            ));
        }

        Double number = (Double) value;

        if (number < -5 || number > 5) {
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Число уходит за рамки допустимого",
                    "Значение должно быть в диапазоне от -5 до 5 для y."
            ));
        }

    }

}